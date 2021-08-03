package com.bfly.common;

import com.alibaba.fastjson.JSON;
import com.bfly.common.json.JsonUtil;
import com.bfly.core.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * HttpServletResponse响应工具类
 *
 * @author 胡礼波
 * 2013-6-26 上午11:23:53
 */
public class ResponseUtil {

    private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 向客户端输出javascript脚本
     *
     * @param response
     * @param jsText
     * @author 胡礼波-andy
     * @2013-6-23下午3:49:10
     */
    public static void writeJavascript(HttpServletResponse response, String jsText) {
        writeData(response, jsText, "application/javascript");
    }

    /**
     * 输出文本字符串
     *
     * @param response
     * @param text
     * @author 胡礼波-andy
     * @2013-6-22下午8:33:35
     */
    public static void writeHtml(HttpServletResponse response, Object text) {
        writeData(response, String.valueOf(text), "text/html");
    }

    /**
     * 输出文本字符串
     *
     * @param response
     * @param text
     * @author 胡礼波-andy
     * @2013-6-22下午8:33:35
     */
    public static void writeText(HttpServletResponse response, Object text) {
        writeData(response, String.valueOf(text), "text/plain");
    }

    /**
     * 输出xml
     *
     * @param response
     * @param xml
     * @author 胡礼波-andy
     * @2013-6-22下午8:34:29
     */
    public static void writeXml(HttpServletResponse response, String xml) {
        writeData(response, xml, "application/xmlapplication/xml");
    }

    /**
     * 输出Json
     *
     * @param response
     * @param data
     * @author 胡礼波-andy
     * @2013-6-22下午8:35:08
     */
    public static void writeJson(HttpServletResponse response, Object data) {
        String dataStr;
        if (data instanceof JSON) {
            dataStr = ((JSON) data).toJSONString();
        } else if (data instanceof String) {
            dataStr = (String) data;
        } else {
            dataStr = JsonUtil.toJsonFilter(data).toJSONString();
        }
        writeData(response, dataStr, "application/json");
    }

    /**
     * 文件下载
     * @author andy_hulibo@163.com
     * @date 2019/9/7 17:36
     */
    public static void writeDownload(HttpServletResponse response, File file){
        response.setCharacterEncoding(Constants.ENCODE_UTF8);
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.setHeader("Content-Length", "" + file.length());
        response.setHeader("Content-Type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream())){
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            toClient.write(buffer);
            toClient.flush();
        }catch(Exception e){
            logger.error("下载文件异常",e);
        }
    }

    /**
     * 输出数据 通常是Ajax调用
     *
     * @author 胡礼波
     * 2012-4-28 下午09:11:53
     */
    private static void writeData(HttpServletResponse response, String data, String contentType) {
        response.setCharacterEncoding(Constants.ENCODE_UTF8);
        response.setContentType(contentType);
        try (PrintWriter out = response.getWriter()) {
            out.write(data);
            out.flush();
        } catch (Exception e) {
            logger.warn("数据流输入有错:" + e);
        }
    }

}
