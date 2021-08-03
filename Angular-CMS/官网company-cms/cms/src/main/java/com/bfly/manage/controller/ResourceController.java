package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.common.FileUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.StringUtil;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.config.ResourceConfigure;
import com.bfly.core.security.ActionModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * 系统资源管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:20
 */
@RestController
@RequestMapping(value = "/manage/resource")
public class ResourceController extends BaseController {

    /**
     * 资源列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 11:41
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "系统资源列表", recordLog = false)
    public void listResource(HttpServletResponse response) {
        String target = getRequest().getParameter("target");
        if (target.contains("..")) {
            throw new RuntimeException("文件地址不存在!");
        }
        String root = ResourceConfigure.getConfig().getResourceRootPath();
        Path path = Paths.get(root, target);
        root = path.toAbsolutePath().toString();
        File file = new File(root);
        if (!file.exists()) {
            throw new RuntimeException("文件地址不存在!");
        }
        JSONArray array = new JSONArray();
        if (file.exists()) {
            File[] files = file.listFiles();
            JSONObject json;
            for (File f : files) {
                json = new JSONObject();
                json.put("name", f.getName());
                json.put("date", new Date(f.lastModified()));
                json.put("size", StringUtil.getHumanSize(FileUtils.sizeOf(f)));
                json.put("isDir", f.isDirectory());

                //相对资源根目录的相对路径
                String relativePath = "/" + FilenameUtils.separatorsToUnix(ResourceConfigure.getResourceRelativePath(f.getPath()));
                json.put("path", relativePath);
                if (f.isDirectory()) {
                    json.put("children", new String[]{});
                } else {
                    json.put("link", ResourceConfigure.getResourceHttpUrl(relativePath));
                }
                array.add(json);
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }

    /**
     * 检查路径是否合法
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:13
     */
    private void checkPath(String path) {
        boolean flag = FileUtil.checkExist(ResourceConfigure.getResourceAbsolutePath(path));
        if (!flag) {
            throw new RuntimeException("文件夹路径不存在!");
        }
    }

    /**
     * 创建目录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:01
     */
    @PostMapping("/mkdir")
    @ActionModel(value = "新建文件夹!")
    public void mkdir(HttpServletResponse response) {
        String path = getRequest().getParameter("path");
        String name = getRequest().getParameter("name");
        checkPath(path);
        File file = Paths.get(ResourceConfigure.getConfig().getResourceRootPath(), path, name).toFile();
        file.mkdirs();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:15
     */
    @PostMapping("/del")
    @ActionModel(value = "删除文件!")
    public void delResource(HttpServletResponse response, @RequestBody String... paths) {
        for (String path : paths) {
            String fullPath = ResourceConfigure.getResourceAbsolutePath(path);
            File file = new File(fullPath);
            File root = new File(ResourceConfigure.getConfig().getResourceRootPath());
            if (file.exists()) {
                if (file.getPath().equals(root.getPath())) {
                    throw new RuntimeException("不能删除根目录!");
                }
                FileUtil.deleteFiles(fullPath);
            } else {
                throw new RuntimeException("目标文件或文件夹不存在!");
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 上传文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 19:26
     */
    @PostMapping(value = "/upload")
    @ActionModel("上传文件")
    public void uploadFile(@RequestPart MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getParameter("path");
        String fullPath = ResourceConfigure.getResourceAbsolutePath(path);
        if (path == null || !FileUtil.checkExist(fullPath)) {
            throw new RuntimeException("目标文件夹不存在!");
        }
        fullPath = fullPath + File.separator + file.getOriginalFilename();
        boolean result = FileUtil.writeFile(file.getInputStream(), fullPath);
        if (!result) {
            throw new RuntimeException("文件上传失败!");
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }


    /**
     * 获得操作系统字体集合
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/29 17:55
     */
    @GetMapping(value = "/fonts")
    public void getFonts(HttpServletResponse response) {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String fonts[] = e.getAvailableFontFamilyNames();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(fonts));
    }
}
