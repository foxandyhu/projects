package com.bfly.core.config;

import com.bfly.core.context.ContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 资源配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/3 16:09
 */
@Configuration
public class ResourceConfigure {

    private static ResourceConfigure config;

    @PostConstruct
    public void init() {
        config = this;
    }


    /**
     * 资源服务器地址
     *
     * @author andy_hulibo@163.com
     * @date 2020/3/26 20:44
     */
    @Value("${resource.server.url:http://localhost/}")
    private String resourceServerUrl;

    /**
     * 系统资源存放路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/4 10:53
     */
    @Value("${resource.root-path-linux}")
    private String resourceRootPathLinux;

    @Value("${resource.root-path-windows}")
    private String resourceRootPathWindows;

    /**
     * 完整的资源uri------例如:http://xxxx.com/xxx.jpg
     *
     * @author andy_hulibo@163.com
     * @date 2020/3/26 17:15
     */
    public static String getResourceHttpUrl(String uri) {
        return ResourceConfigure.getConfig().getResourceServerUrl().concat(uri);
    }

    /**
     * 获得资源目录根路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/4/21 22:03
     */
    public String getResourceRootPath() {
        boolean isWindowOs = System.getProperty("os.name").toLowerCase().contains("windows");
        return isWindowOs ? getResourceRootPathWindows() : getResourceRootPathLinux();
    }

    /**
     * 完整的资源本地路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/11 17:13
     */
    public static String getResourceAbsolutePath(String uri) {
        return ResourceConfigure.getConfig().getResourceRootPath().concat(uri);
    }

    /**
     * 获得相对资源根路径的相对路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/17 8:45
     */
    public static String getResourceRelativePath(String path) {
        Path p = Paths.get(ResourceConfigure.getConfig().getResourceRootPath()).relativize(Paths.get(path));
        return p.toString();
    }

    /**
     * 获得模板根路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/17 9:03
     */
    public static String getTemplateRootPath() {
        return ContextUtil.getWebApp() + File.separator + "template";
    }

    /**
     * 获得模板的绝对路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/17 9:05
     */
    public static String getTemplateAbsolutePath(String tplPath) {
        return getTemplateRootPath() + File.separator + tplPath;
    }

    /**
     * 获得相对模板根路径的相对路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/17 9:10
     */
    public static String getTemplateRelativePath(String path) {
        Path p = Paths.get(ResourceConfigure.getTemplateRootPath()).relativize(Paths.get(path));
        return p.toString();
    }

    /**
     * 索引库目录
     *
     * @author andy_hulibo@163.com
     * @date 2020/5/3 17:16
     */
    public String getIndexRootPath() {
        Path path = Paths.get(getResourceRootPath(), "index");
        return path.toFile().getPath();
    }

    /**
     * 系统图标目录
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/5 14:04
     */
    public String getIconsRootPath() {
        Path path = Paths.get(getResourceRootPath(), "icon");
        return path.toFile().getPath();
    }

    /**
     * 系统临时文件夹目录
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/9 18:53
     */
    public String getTempRootPath() {
        Path path = Paths.get(getResourceRootPath(), "temp");
        return path.toFile().getPath();
    }

    /**
     * 存放Image目录路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/17 9:20
     */
    public String getImageRootPath() {
        Path path = Paths.get(getResourceRootPath(), "images");
        return path.toFile().getPath();
    }

    /**
     * 存放文件目录路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/17 9:21
     */
    public String getDocRootPath() {
        Path path = Paths.get(getResourceRootPath(), "docs");
        return path.toFile().getPath();
    }

    /**
     * 存放多媒体路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/17 9:22
     */
    public String getMediaRootPath() {
        Path path = Paths.get(getResourceRootPath(), "medias");
        return path.toFile().getPath();
    }

    /**
     * 存放未知类型的文件路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/22 13:39
     */
    public String getFileRootPath() {
        Path path = Paths.get(getResourceRootPath(), "files");
        return path.toFile().getPath();
    }

    public String getResourceRootPathLinux() {
        return StringUtils.isEmpty(resourceRootPathLinux) ? ContextUtil.getWebApp() + "resource" : resourceRootPathLinux;
    }

    public void setResourceRootPathLinux(String resourceRootPathLinux) {
        this.resourceRootPathLinux = resourceRootPathLinux;
    }

    public String getResourceRootPathWindows() {
        return StringUtils.isEmpty(resourceRootPathWindows) ? ContextUtil.getWebApp() + "resource" : resourceRootPathWindows;
    }

    public void setResourceRootPathWindows(String resourceRootPathWindows) {
        this.resourceRootPathWindows = resourceRootPathWindows;
    }

    public String getResourceServerUrl() {
        return resourceServerUrl;
    }

    public void setResourceServerUrl(String resourceServerUrl) {
        this.resourceServerUrl = resourceServerUrl;
    }

    public static ResourceConfigure getConfig() {
        return config;
    }
}
