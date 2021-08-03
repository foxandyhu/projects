package com.bfly.core.base.action;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.service.ISysWaterMarkService;
import com.bfly.common.*;
import com.bfly.core.config.ResourceConfigure;
import com.bfly.core.security.ActionModel;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;


/**
 * 文件上传操作Action
 *
 * @author 胡礼波
 * 2013-8-19 下午4:32:39
 */
@RestController("FileUploadController")
@RequestMapping(value = "/manage/file")
public class FileUploadController extends BaseController {

    @Autowired
    private ISysWaterMarkService waterMarkService;

    /**
     * 公共的文件上传
     *
     * @return 返回上传文件的文件名
     * @author 胡礼波
     * 2013-8-19 下午4:36:01
     */
    @PostMapping(value = "/upload")
    @ActionModel(value = "文件上传")
    public void uploadImage(@RequestPart MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws Exception {
        String targetDir;
        boolean isImg = false;
        String fileName = FileUtil.getRandomName() + FileUtil.getFileSuffix(file.getOriginalFilename());

        if (ValidateUtil.isImage(fileName)) {
            targetDir = ResourceConfigure.getConfig().getImageRootPath();
            isImg = true;
        } else if (ValidateUtil.isMedia(fileName)) {
            targetDir = ResourceConfigure.getConfig().getMediaRootPath();
        } else if (ValidateUtil.isDoc(fileName)) {
            targetDir = ResourceConfigure.getConfig().getDocRootPath();
        } else {
            targetDir = ResourceConfigure.getConfig().getFileRootPath();
        }

        String date = DateUtil.getCurrentDate();
        //每天创建一个文件夹
        targetDir = targetDir + File.separator + date;
        FileUtil.mkdir(targetDir);

        String targetFile = targetDir + File.separator + fileName;
        boolean result = FileUtil.writeFile(file.getInputStream(), targetFile);
        if (!result) {
            throw new RuntimeException("文件上传失败!");
        }
        if (isImg) {
            String waterMark = request.getParameter("watermark");
            if ("true".equalsIgnoreCase(waterMark)) {
                waterMarkService.waterMarkFile(targetFile);
            }
        }

        String size = getFileSize(targetFile);

        String relativePath = FilenameUtils.separatorsToUnix("/" + Paths.get(ResourceConfigure.getConfig().getResourceRootPath()).relativize(Paths.get(targetFile)));
        JSONObject json = new JSONObject();
        json.put("path", relativePath);
        json.put("url", ResourceConfigure.getResourceHttpUrl(relativePath));
        json.put("size", size);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 获得文件大小，多媒体文件是获得播放时长，其他文件是文件大小
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/25 10:40
     */
    private String getFileSize(String filePath) {
        String size;
        if (ValidateUtil.isMedia(filePath)) {
            size = FileUtil.getMediaPlayTime(new File(filePath));
        } else {
            size = FileUtil.getFileSize(new File(filePath));
        }
        return size;
    }
}