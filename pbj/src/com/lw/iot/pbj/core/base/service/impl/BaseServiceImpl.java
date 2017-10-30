package com.lw.iot.pbj.core.base.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lw.iot.pbj.common.util.ContextUtil;
import com.lw.iot.pbj.core.annotation.ActionModel;
import com.lw.iot.pbj.core.annotation.LogRecord;
import com.lw.iot.pbj.core.annotation.RecordType;
import com.lw.iot.pbj.core.base.persistence.BaseMapper;
import com.lw.iot.pbj.core.base.service.IBaseService;

@Service("BaseServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="公共业务接口")
@LogRecord(record=RecordType.IGNORE)
public class BaseServiceImpl<T> implements IBaseService<T> {

	@Autowired
	private BaseMapper<T> baseMapper;
	
	@Override
	public T get(int id) {
		return baseMapper.getById(id);
	}

	@Override
	@Transactional
	@ActionModel(description="删除对象")
	public int del(Integer... id) {
		if(ArrayUtils.isEmpty(id))
		{
			return 0;
		}
		return baseMapper.delById(id);
	}

	@Override
	@Transactional
	@ActionModel(description="编辑对象")	
	public int edit(T t) {
		Assert.notNull(t,"对象为空,编辑失败");
		return baseMapper.editById(t);
	}

	@Override
	@Transactional
	@ActionModel(description="保存对象")
	public int save(T t) {
		Assert.notNull(t,"对象为空,保存失败");
		return baseMapper.save(t);
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
