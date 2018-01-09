package com.lw.iot.pbj.core.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * 缓存工具类
 * @author 胡礼波-Andy
 * @2015年4月28日上午10:25:53
 */
public class CacheUtil {

private static final CacheManager CACHE_MANAGER=CacheManager.create();
	
	/**
	 * 缓存ID
	 */
	private static String id="PBJ_Ehcache";
	
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		CacheUtil.id = id;
	}
	
	static
	{
		if(id==null)
		{
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		if(!CACHE_MANAGER.cacheExists(id))
		{
			CACHE_MANAGER.addCache(id);
		}
	}
	
	/**
	 * 获得缓存容器
	 * @author 胡礼波
	 * 2013-3-11 下午4:00:56
	 * @return
	 */
	public static Ehcache getCache()
	{
		return CACHE_MANAGER.getCache(id);
	}
	
	/**
	 * 清空缓存容器
	 * @author 胡礼波
	 * 2013-3-11 下午4:03:57
	 */
	public static void clear()
	{
		getCache().removeAll();
	}
	
	/**
	 * 判断指定的缓存对象是否存在 true存在  false不存在
	 * @author 胡礼波
	 * 2013-3-11 下午4:41:04
	 * @param key
	 * @return
	 */
	public static boolean isExist(Object key)
	{
		Element element=getCache().get(key.hashCode());
		if(element==null)
		{
			return false;
		}
		return !getCache().isExpired(element);
	}
	
	/**
	 * 从缓存容器中获得数据
	 * @author 胡礼波
	 * 2013-3-11 下午4:06:25
	 * @param key
	 * @return
	 */
	public static Object getData(Object key)
	{
		try
		{
			Element element=getCache().get(key.hashCode());
			if(element==null)
			{
				return null;
			}
			return element.getObjectValue();
		}catch (Exception e) {
			throw new CacheException(e);
		}
	}
	
	/**
	 * 获得缓存容器中的缓存对象数
	 * @author 胡礼波
	 * 2013-3-11 下午4:07:55
	 * @return
	 */
	public static int getSize()
	{
		try
		{
			return getCache().getSize();
		}catch (Exception e) {
			throw new CacheException(e);
		}
	}
	
	/**
	 * 添加数据到缓存容器中如果存在则更新
	 * @author 胡礼波
	 * 2013-3-11 下午4:11:02
	 * @param key 键值
	 * @param value 值
	 * @param seconds 过期时间 单位秒
	 */
	public static void addData(Object key,Object value,int seconds)
	{
		try
		{
			Element element=getCache().get(key.hashCode());
			if(element==null)
			{
				getCache().put(new Element(key.hashCode(),value,seconds,seconds));
			}else
			{
				element.setTimeToIdle(seconds);
				element.setTimeToLive(seconds);
				getCache().replace(element);
			}
		}catch (Exception e) {
			throw new CacheException(e);
		}
	}
	
	/**
	 * 移除指定key的对象
	 * @author 胡礼波
	 * 2013-3-11 下午4:12:18
	 * @param key
	 * @return
	 */
	public static Object removeData(Object key)
	{
		try
		{
			Object obj=getData(key);
			getCache().remove(key.hashCode());
			return obj;
		}catch (Exception e) {
			throw new CacheException(e);
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this==obj)
		{
			return true;
		}
		if(obj==null)
		{
			return false;
		}
		if(!(obj instanceof Ehcache))
		{
			return false;
		}
		Cache otherCache=(Cache)obj;
		return id.equals(otherCache.getName());
	}
	
	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
	
	@Override
	public String toString()
	{
		return "EHCache {"+ id+ "}";
	}
}
