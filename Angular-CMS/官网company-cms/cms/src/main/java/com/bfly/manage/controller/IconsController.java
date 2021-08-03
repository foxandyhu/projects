package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.ValidateUtil;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.config.ResourceConfigure;
import com.bfly.core.security.ActionModel;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统图标Controller
 *
 * @author andy_hulibo@163.com
 * @date 2020/6/5 14:02
 */
@RestController
@RequestMapping(value = "/manage/icons")
public class IconsController extends BaseController {

    /**
     * 系统图标列表
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/5 14:03
     */
    @GetMapping(value = "/list")
    @ActionModel(recordLog = false)
    public void list(HttpServletResponse response) {
        String path = ResourceConfigure.getConfig().getIconsRootPath();
        File dirFile = new File(path);

        List<JSONObject> list = new ArrayList<>();
        getIcon(dirFile, list);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    private static void getIcon(File file, List<JSONObject> list) {
        JSONObject json;
        if (file.isFile()) {
            if (ValidateUtil.isImage(file)) {
                File dir = file.getParentFile();
                File fs[] = dir.listFiles((dir1, name) -> {
                    return name.endsWith(".txt");
                });
                String categoryName = fs.length > 0 ? fs[0].getName() : "";
                json = new JSONObject();
                String path = Paths.get(ResourceConfigure.getConfig().getResourceRootPath()).relativize(Paths.get(file.getPath())).toString();
                path = "/" + FilenameUtils.separatorsToUnix(path);
                json.put("name", categoryName);
                json.put("path", path);
                json.put("url", ResourceConfigure.getConfig().getResourceServerUrl().concat(path));
                list.add(json);
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            Arrays.stream(files).forEach(f -> {
                getIcon(f, list);
            });
        }
    }
}
