package com.lw.iot.pbj.core.base.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 数据层基本接口
 * @author 胡礼波-Andy
 * @2014年11月11日下午3:12:08
 *
 */
public interface BaseMapper<T> {

	/**
	 * 根据主键ID获得对应的对象
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:13:02
	 * 
	 * @param id
	 * @return
	 */
	public T getById(int id);
	
	/**
	 * 根据组建删除对象
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:13:48
	 * 
	 * @param id
	 * @return
	 */
	public int delById(@Param("ids") Integer... ids);
	
	/**
	 * 以主键为条件修改对象
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:20:48
	 * 
	 * @param t
	 * @return
	 */
	public int editById(T t);
	
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
	 * 根据不同的条件获得集合
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午3:15:48
	 * 
	 * @param params
	 * @return
	 */
	public List<T> getList(Map<String,Object> params);
	
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
