package com.lw.iot.pbj.common.json;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;


/**
 * Json工具类
 * @author 胡礼波-Andy
 * @2014年11月10日上午9:24:39
 *
 */
public class JsonUtil {

	private static SerializeConfig config=SerializeConfig.getGlobalInstance();
	static
	{
		config.put(Date.class,new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 返回Json对象
	 * @author 胡礼波-Andy
	 * @2014年11月10日上午9:24:18
	 * 
	 * @param object
	 * @param properties
	 * @return
	 */
	public static JSONObject toJsonStringFilterPropter(Object object,String... properties)
	{
		String jsonStr = filter(object,properties);
		JSONObject jb=JSON.parseObject(jsonStr);
		return jb;
	}
	
	
	/**
	 * 返回Json对象
	 * @author 胡礼波-Andy
	 * @2014年11月10日上午9:24:11
	 * 
	 * @param object
	 * @param properties
	 * @return
	 */
	public static JSONArray toJsonStringFilterPropterForArray(Object object,String... properties)
	{
		String jsonStr = filter(object,properties);
		JSONArray jb=JSON.parseArray(jsonStr);
		return jb;
	}


	/**
	 *  过滤掉指定的属性
	 * @author 胡礼波-Andy
	 * @2014年11月10日上午9:24:05
	 * 
	 * @param object
	 * @param properties
	 * @return
	 */
	private static String filter(Object object,final String ...properties){
		String jsonStr=null;
		try {
			PropertyFilter filter=new PropertyFilter()
			{
				public boolean apply(Object source, String name, Object value) {
					for (String pro : properties) {
						if(name.equals(pro))
						{
							return false;
						}
					}
					return true;
				}
			};
			SerializeWriter writer=new SerializeWriter();
			JSONSerializer serializer=new JSONSerializer(writer,config);
			serializer.getPropertyFilters().add(filter);
			serializer.write(object);
			jsonStr = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("json过滤属性出错！"); 
		}
		return jsonStr;
	}
	
	/**
	 * Json转换为java bean
	 * @author 胡礼波-Andy
	 * @2014年11月10日上午9:23:58
	 * 
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static <T> T parse(String jsonStr,Class<T> cls)
	{
		T obj=null;
		try
		{
			obj=JSON.parseObject(jsonStr,cls);
		}catch (Exception e) {
		}
		return obj;
	}

	/**
	 * Json转换为List java bean
	 * @author 胡礼波-Andy
	 * @2015年1月19日上午11:41:03
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static <E> List<E> parseStrToList(String jsonStr,Class<E> cls)
	{
		List<E> list=null;
		try
		{
			list=JSON.parseArray(jsonStr,cls);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
