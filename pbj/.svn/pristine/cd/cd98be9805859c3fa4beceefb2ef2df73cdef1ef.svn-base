package com.lw.iot.pbj.common.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件处理工具类
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年10月30日 下午4:01:21
 */
public class FileUtil {
	
	/**
	 * 检查文件是否存在
	 * @author 胡礼波-Andy
	 * @2015年5月16日下午7:17:31
	 * @param filePath
	 * @return
	 */
	public static boolean checkExist(String filePath)
	{
		File file=new File(filePath);
		return file.exists();
	}
	
	/**
	 * 项目路径
	 * @author 胡礼波-Andy
	 * @2015年5月16日下午7:17:23
	 * @param request
	 * @return
	 */
	public static String getAppBase(HttpServletRequest request)
	{
		return request.getServletContext().getRealPath("/");
	}
	
	/**
	 * 图片项目地址
	 * @author 胡礼波-Andy
	 * @2015年5月15日上午11:00:22
	 * @param request
	 * @return
	 */
	public static String getResourceServer(HttpServletRequest request)
	{
		File file=new File(getAppBase(request));
		String parentPath=file.getParent();
		file=new File(parentPath+"/resources");
		if(!file.exists())
		{
			file.mkdirs();
		}
		return file.getPath();
	}
	
	public static String fileName(String originalFilename){
		originalFilename = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
		String fileName = DateUtil.getCurrentDateTime()+originalFilename;
		return fileName;
	}
	
	/**
	 * 获得系统临时目录
	 * @author 胡礼波-Andy
	 * @2015年5月10日下午8:30:13
	 * @return
	 */
	public static String getSysTempPath()
	{
		return System.getProperty("java.io.tmpdir");
	}
}
