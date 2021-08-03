package com.bfly.core.base.service.impl;

import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.common.reflect.ClassUtils;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.cache.EhCacheUtil;
import com.bfly.core.context.PagerThreadLocal;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 13:32
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public abstract class BaseServiceImpl<T, ID> extends BaseJdbcServiceImpl implements IBaseService<T, ID> {

    private Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    private JpaRepositoryImplementation<T, ID> baseDao;
    @Autowired
    private EntityManager em;

    /**
     * 获得缓存 每一个实体类都有自己的缓存域
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/23 21:27
     */
    public EhCacheCache getCache() {
        String entityName = ClassUtils.getSuperClassGenricType(getClass()).getName();
        EhCacheCache cache = (EhCacheCache) EhCacheUtil.getCache(entityName);
        return cache;
    }

    /**
     * 查询参数转换为缓存的KEY
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/23 21:29
     */
    public String paramMapToCacheKey(Map<String, ?>... params) {
        if (params == null) {
            return "null:null";
        }
        StringBuilder sb = new StringBuilder();
        for (Map<String, ?> param : params) {
            if (MapUtils.isEmpty(param)) {
                sb.append("null:null".concat("|"));
            } else {
                for (String p : param.keySet()) {
                    sb.append(p.concat(":").concat(String.valueOf(param.get(p))).concat("|"));
                }
            }
            sb.delete(sb.length() - 1, sb.length() - 1);
        }
        return sb.toString();
    }


    @Autowired
    public void setBaseDao(JpaRepositoryImplementation<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public T get(ID id) {
        Cache cache = getCache();
        Object object = cache.get(id, ClassUtils.getSuperClassGenricType(getClass()));
        T t;
        if (object == null) {
            Optional<T> optional = baseDao.findById(id);
            t = optional.isPresent() ? optional.get() : null;
            if (t != null) {
                cache.put(id, t);
            }
        } else {
            t = (T) object;
        }
        return t;
    }

    @Override
    public T get(Map<String, Object> property) {
        String key = JsonUtil.toJsonFilter(property).toJSONString();
        Cache cache = getCache();
        Object object = cache.get(key, ClassUtils.getSuperClassGenricType(getClass()));
        T t;
        if (object == null) {
            Optional<T> optional = baseDao.findOne(getExactQuery(property));
            t = optional.isPresent() ? optional.get() : null;
            if (t != null) {
                cache.put(key, t);
            }
        } else {
            t = (T) object;
        }
        return t;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(ID... ids) {
        int count = 0;
        for (ID id : ids) {
            baseDao.deleteById(id);
            count++;
        }
        getCache().clear();
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(T t) {
        baseDao.save(t);

        Cache cache = getCache();
        cache.clear();
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T t) {
        baseDao.save(t);

        Cache cache = getCache();
        cache.clear();
        return true;
    }

    @Override
    public List<T> getList() {
        String key = "_list_";
        Cache cache = getCache();
        Object object = cache.get(key, ClassUtils.getSuperClassGenricType(List.class));
        List<T> list;
        if (object == null) {
            list = baseDao.findAll();
            if (CollectionUtils.isNotEmpty(list)) {
                cache.put(key, list);
            }
        } else {
            list = (List<T>) object;
        }
        return list;
    }

    private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass, CriteriaQuery<S> query) {
        Root<U> root = query.from(domainClass);
        if (spec == null) {
            return root;
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    /**
     * Hibernate 查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 14:08
     */
    protected <T> TypedQuery<T> getQuery(@Nullable Specification spec) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        Class cls = ClassUtils.getSuperClassGenricType(this.getClass());
        CriteriaQuery query = builder.createQuery(cls);

        Root<T> root = applySpecificationToCriteria(spec, cls, query);
        query.select(root);

        Pager pager = PagerThreadLocal.get();
        TypedQuery q = em.createQuery(query);
        if (pager != null) {
            PageRequest pageable = getPageRequest(pager);
            q.setFirstResult((int) pageable.getOffset());
            q.setMaxResults(pageable.getPageSize());
        }
        return q;
    }

    @Override
    public List<T> getList(Map<String, Object> exactQueryProperty) {
        return getList(exactQueryProperty, null, null);
    }

    @Override
    public List<T> getList(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        return getList(exactQueryProperty, unExactQueryProperty, sortQueryProperty, null);
    }

    @Override
    public List<T> getList(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty, Map<String, String> groupProperty) {
        String key = "list@" + paramMapToCacheKey(exactQueryProperty, unExactQueryProperty, sortQueryProperty, groupProperty);

        Cache cache = getCache();
        Object object = cache.get(key, ClassUtils.getSuperClassGenricType(List.class));
        List list;
        if (object == null) {
            Specification specification = getSpecification(exactQueryProperty, unExactQueryProperty, sortQueryProperty, groupProperty);
            list = getQuery(specification).getResultList();
            if (CollectionUtils.isNotEmpty(list)) {
                cache.put(key, list);
            }
        } else {
            list = (List) object;
        }
        return list;
    }

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty) {
        return getPage(exactQueryProperty, null, null);
    }

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        return getPage(exactQueryProperty, unExactQueryProperty, sortQueryProperty, null);
    }

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty, Map<String, String> groupProperty) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Map<String, Object> pagerMap = new HashMap<>();
        pagerMap.put("pageNo", pager.getPageNo());
        pagerMap.put("pageSize", pager.getPageSize());

        String key = "pager@" + paramMapToCacheKey(pagerMap, exactQueryProperty, unExactQueryProperty, sortQueryProperty, groupProperty);

        Cache cache = getCache();
        Object object = cache.get(key, ClassUtils.getSuperClassGenricType(Pager.class));
        if (object == null) {
            Specification specification = getSpecification(exactQueryProperty, unExactQueryProperty, sortQueryProperty, groupProperty);
            Page<T> page = baseDao.findAll(specification, getPageRequest(pager));
            pager = new Pager(pager.getPageNo(), pager.getPageSize(), page.getTotalElements());
            pager.setData(page.getContent());
            if (CollectionUtils.isNotEmpty(pager.getData())) {
                cache.put(key, pager);
            }
        } else {
            pager = (Pager) object;
        }
        return pager;
    }

    @Override
    public long getCount() {
        return getCount(null, null, null);
    }

    @Override
    public long getCount(Map<String, Object> property) {
        return getCount(property, null, null);
    }

    @Override
    public long getCount(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty) {
        return getCount(exactQueryProperty, unExactQueryProperty, null);
    }

    @Override
    public long getCount(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, String> groupProperty) {
        String key = "count@" + paramMapToCacheKey(exactQueryProperty, unExactQueryProperty, groupProperty);
        Cache cache = getCache();
        long count;
        Object object = cache.get(key, ClassUtils.getSuperClassGenricType(Long.class));
        if (object == null) {
            Specification specification = getSpecification(exactQueryProperty, unExactQueryProperty, null, groupProperty);
            count = baseDao.count(specification);
            if (count > 0) {
                cache.put(key, count);
            }
        } else {
            count = (long) object;
        }
        return count;
    }

    /**
     * 多条件查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 14:56
     */
    private Specification getSpecification(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty, Map<String, String> groupProperty) {
        Specification exactSpec = getExactQuery(exactQueryProperty);
        Specification unExactSpec = getUnExactQuery(unExactQueryProperty);
        Specification groupSpec = getGroupQuery(groupProperty);

        Specification sortSpec;
        if(MapUtils.isEmpty(sortQueryProperty)){
            sortSpec=getDefaultSpec();
        }else{
            sortSpec=getSortQuery(sortQueryProperty);
        }

        Specification specification = sortSpec.and(exactSpec).and(unExactSpec).and(groupSpec);
        return specification;
    }

    /**
     * 多条件精确查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:13
     */
    protected Specification getExactQuery(Map<String, Object> queryProperty) {
        if (MapUtils.isEmpty(queryProperty)) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : queryProperty.keySet()) {
                if (key.contains("_in")) {
                    // 属性字段名称 后半部分是查询条件参数 value必须是集合类型的
                    Object data = queryProperty.get(key);
                    if (!(data instanceof Collection) && !data.getClass().isArray()) {
                        throw new RuntimeException("in查询条件必须为集合或数组类型");
                    }
                    String filed = key.substring(0, key.indexOf("_"));
                    Path<Object> path = root.get(filed);
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                    if (data.getClass().isArray()) {
                        Arrays.asList((Object[]) data).stream().forEach(o -> {
                            in.value(o);
                        });
                    } else {
                        Stream.of(data).forEach(o -> {
                            in.value(o);
                        });
                    }
                    predicates.add(criteriaBuilder.and(criteriaBuilder.and(in)));
                    continue;
                }
                if (key.contains("_beginDate")) {
                    // 前端传来的时间参数 格式需要 createTime_beginTime 前半部分是真实的
                    // 属性字段名称 后半部分是查询条件参数
                    Date date = (Date) queryProperty.get(key);
                    String filed = key.substring(0, key.indexOf("_"));
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get(filed), date));
                    continue;
                }
                if (key.contains("_endDate")) {
                    // 前端传来的时间参数 格式需要 createTime_beginTime 前半部分是真实的
                    // 属性字段名称 后半部分是查询条件参数
                    Date date = (Date) queryProperty.get(key);
                    String filed = key.substring(0, key.indexOf("_"));
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get(filed), date));
                    continue;
                }
                if (queryProperty.get(key) == null) {
                    predicates.add(criteriaBuilder.isNull(root.get(key)));
                    continue;
                }
                if (queryProperty.get(key) instanceof String) {
                    if (queryProperty.get(key).toString().length() == 0) {
                        continue;
                    }
                }
                if (queryProperty.get(key) instanceof Collection) {
                    Expression<Collection> expression = root.join(key);
                    Predicate in = expression.in(queryProperty.get(key));
                    predicates.add(in);
                    continue;
                }
                predicates.add(criteriaBuilder.equal(root.get(key), queryProperty.get(key)));
            }
            return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
        };
    }

    /**
     * 多条件模糊查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:09
     */
    protected Specification getUnExactQuery(Map<String, String> queryProperty) {
        if (MapUtils.isEmpty(queryProperty)) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : queryProperty.keySet()) {
                if (queryProperty.get(key) == null || queryProperty.get(key).length() == 0) {
                    continue;
                }
                predicates.add(criteriaBuilder.like(root.get(key), "%" + queryProperty.get(key) + "%"));
            }
            return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
        };
    }

    /**
     * 多条件排序查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:14
     */
    protected Specification getSortQuery(Map<String, Sort.Direction> sortProperty) {
        if (MapUtils.isEmpty(sortProperty)) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Order> orders = new ArrayList<>();
            for (String key : sortProperty.keySet()) {
                if (sortProperty.get(key) == null) {
                    continue;
                }
                Order order=sortProperty.get(key).isAscending()?criteriaBuilder.asc(root.get(key)):criteriaBuilder.desc(root.get(key));
                orders.add(order);
            }
            // 当mysql排序字段出现相同的值是会出现混乱现象，mysql会随机排序返回数据，为确保数据不混乱需要加上
            // 一个唯一字段例如id
            orders.add(criteriaBuilder.desc(root.get("id")));
            criteriaQuery.orderBy(orders);
            return (Predicate) criteriaQuery.getSelection();
        };
    }

    /**
     * 多条件分组查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:14
     */
    protected Specification getGroupQuery(Map<String, String> groupProperty) {
        if (MapUtils.isEmpty(groupProperty)) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            for (String key : groupProperty.keySet()) {
                if (groupProperty.get(key) == null) {
                    continue;
                }
                criteriaQuery.groupBy(root.get(key));
            }
            return (Predicate) criteriaQuery.getSelection();
        };
    }

    /**
     * 默认Id降序
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 21:04
     */
    public Specification getDefaultSpec() {
        Specification specification = getSortQuery(new HashMap<String, Sort.Direction>(1) {
            private static final long serialVersionUID = -3371120713938289395L;

            {
                put("id", Sort.Direction.DESC);
            }
        });
        return specification;
    }
}