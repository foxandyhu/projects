package com.lw.iot.pbj.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.util.NetUtil.ContentTypeEnum;


public class MapUtil {
	private static final Logger logger = Logger.getLogger(MapUtil.class);
	private static final String AK = "1LrIn254h4UEd72vrMVFBsFf";//百度地图ak
	private static Map<String, String> addressCache = new ConcurrentHashMap<String, String>();
	
	/**
	 * 获取某点所在位置
	 * @author sunships
	 * @date 2015年12月10日上午11:56:31
	 * @param lat
	 * @param lng
	 * @return
	 * @throws Exception
	 */
	public static String getLocationDesc(String lat,String lng){
		String addr = "";
		try {
//			addr = addressCache.get(lat+"_"+lng);
			if(StringUtils.isBlank(addr)){
				String url = "http://api.map.baidu.com/geocoder/v2/?ak="+AK+"&output=json&location="+lat+","+lng;
				String result =  NetUtil.getHttpResponseData(url,"","POST",false,ContentTypeEnum.JSON);
				JSONObject json = JsonUtil.parse(result,JSONObject.class);
				logger.info(json);
				if(0==json.getIntValue("status")){
					addr =  json.getJSONObject("result").getString("formatted_address");
					addressCache.put(lat+"_"+lng, addr);
				}
			}
		} catch (Exception e) {
			logger.error("根据坐标获取地址失败");
		}
		return addr;
	}
	
}
