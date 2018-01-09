package com.lw.iot.pbj.core.base.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.lw.iot.pbj.common.constant.SysConst;
import com.lw.iot.pbj.common.util.DateUtil;
import com.lw.iot.pbj.common.util.FileUtil;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.common.util.StringUtil;

/**
 * 文件操作Action
 * @author 胡礼波-Andy
 * @2015年2月9日下午2:45:31
 */
@Controller("FileAction")
@RequestMapping(value="/manage/file")
public class FileAction extends BaseAction {
	
	/**
	 * 公共的文件上传
	 * 如果指定上传路径则上传到指定路径否则上传到系统资源临时目录
	 * @author 胡礼波
	 * @2015年2月9日下午2:45:31
	 * @return 返回上传路径的相对路径
	 */
	@RequestMapping(value="/upload/general")
	public String uploadFile(HttpServletResponse response)throws Exception
	{
		HttpServletRequest request=getRequest();
		Part part=request.getPart("file");
		
		JSONObject jobj=new JSONObject();
		float size=(float)part.getSize();
		//最大为2M
		if(size>SysConst.MAX_IMG_SIZE)
		{
			ResponseUtil.writeHtml(response,"LW.message.show('图片最大为2M')");
			return null;
		}
		
		String base=FileUtil.getResourceServer(request);
		String filePath=getFilePath(base)+"/";
		String name=part.getSubmittedFileName();
		String targetPath=base+"/"+filePath+name;
		while(FileUtil.checkExist(targetPath))
		{
			//重名的文件处理
			name=StringUtil.getRandomString(10)+"."+StringUtils.substringAfter(name,".");
			targetPath=base+"/"+filePath+name;
		}
		part.write(targetPath);
		jobj.put("url",filePath+name);
		jobj.put("name",name);
		ResponseUtil.writeHtml(response,jobj.toJSONString());
		return null;
	}
	
	/**
	 * 公共的图片上传
	 * @author 胡礼波-Andy
	 * @2015年2月9日下午2:59:44
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/upload/img")
	public void uploadImage(HttpServletResponse response)throws Exception
	{
		HttpServletRequest request=getRequest();
		Part part=request.getPart("imgFile");
		float size=(float)part.getSize();
		if(size>SysConst.MAX_IMG_SIZE)
		{
			throw new RuntimeException("图片最大为2M");
		}
		
		String name=part.getSubmittedFileName();
		String targetPath=null;
		do
		{
			name=StringUtil.getRandomString(10)+"."+StringUtils.substringAfter(name,".");
			targetPath=SysConst.tempPath+File.separator+name;
		}
		while(FileUtil.checkExist(targetPath));
		
		part.write(targetPath);
		JSONObject jobj=new JSONObject();
		jobj.put("url",name);
		jobj.put("fullUrl",SysConst.picServer+"temp/"+name);
		ResponseUtil.writeJson(response, jobj.toJSONString());
	}
	
	/**
	 * 文件上传路径文件夹
	 * 返回相对路径
	 * @author 胡礼波-Andy
	 * @2015年2月9日下午3:10:45
	 * @param request
	 * @return
	 */
	public String getFilePath(String base)
	{
		String filePath="images/"+DateUtil.getCurrentDate();
		base=base+File.separator+filePath;
		File file=new File(base);
		if(!file.exists())
		{
			file.mkdirs();
		}
		return filePath;
	}
}
