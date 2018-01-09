package com.bfly.trade.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.mapper.BaseMapper;
import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.util.ContextUtil;

/**
 * 
 * @author andy_hulibo@163.com
 * 2018年1月8日下午3:39:28
 * @param <T>
 */
@Service("BaseServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS,rollbackFor=Throwable.class)
@ActionModel("公共业务接口")
public class BaseServiceImpl<T> implements IBaseService<T> {

	@Autowired
	private BaseMapper<T> baseMapper;
	
	@Override
	public T get(int id) {
		return baseMapper.getById(id);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	@ActionModel("删除对象")
	public int del(Integer... id) {
		if(id==null || id.length==0)
		{
			return 0;
		}
		return baseMapper.delById(id);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	@ActionModel("编辑对象")	
	public boolean edit(T t) {
		Assert.notNull(t,"对象为空,编辑失败");
		return baseMapper.editById(t)>0;
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	@ActionModel("保存对象")
	public boolean save(T t) {
		Assert.notNull(t,"对象为空,保存失败");
		return baseMapper.save(t)>0;
	}

	@Override
	public List<T> getList() {
		return getList(null);
	}

	@Override
	public List<T> getList(Map<String, Object> params) {
		if(params!=null)
		{
			params.putAll(ContextUtil.getThreadLocalPagerMap());
		}else
		{
			params=ContextUtil.getThreadLocalPagerMap();
		}		
		return baseMapper.getList(params);
	}

	@Override
	public int getCount() {
		return getCount(null);
	}

	@Override
	public int getCount(Map<String, Object> params) {
		if(params!=null)
		{
			params.putAll(ContextUtil.getThreadLocalPagerMap());
		}else
		{
			params=ContextUtil.getThreadLocalPagerMap();
		}
		return baseMapper.getCount(params);
	}

}
