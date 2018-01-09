package com.lw.iot.pbj.core.base.service;

import java.util.List;
import java.util.Map;

/**
 * 基本的业务接口
 * @author 胡礼波-Andy
 * @2014年11月11日下午3:24:29
 *
 */
public interface IBaseService<T> {

	/**
	 * 根据主键ID获得对应的对象
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:13:02
	 * 
	 * @param id
	 * @return
	 */
	public T get(int id);
	
	/**
	 * 根据组建删除对象
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:13:48
	 * 
	 * @param id
	 * @return
	 */
	public int del(Integer... id);
	
	/**
	 * 以主键为条件修改对象
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:20:48
	 * 
	 * @param t
	 * @return
	 */
	public int edit(T t);
	
	/**
	 * 保存对象
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:27:30
	 * 
	 * @param t
	 * @return
	 */
	public int save(T t);
	
	/**
	 * 获得所有的对象
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:14:38
	 * 
	 * @return
	 */
	public List<T> getList();
	
	/**
	 * 根据不同的条件获得集合
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:15:48
	 * 
	 * @param params
	 * @return
	 */
	public List<T> getList(Map<String,Object> params);
	
	/**
	 * 获得所有的总数
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:14:56
	 * 
	 * @return
	 */
	public int getCount();
	
	/**
	 * 根据不同的查询条件查询总数
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:16:21
	 * 
	 * @param params
	 * @return
	 */
	public int getCount(Map<String,Object> params);	
}
