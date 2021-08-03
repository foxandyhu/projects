package com.bfly.common.ipseek.ip2region;

import com.bfly.common.ipseek.IPLocation;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.nio.file.Paths;

/**
 * ip2region.db 本地IP数据库
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/26 16:32
 */
public class Ip2RegionUtil {

    private static Logger logger = LoggerFactory.getLogger(Ip2RegionUtil.class);

    /**
     * ip-api ip查询地址
     *
     * @date 2019/7/26 13:27
     */
    private static final String URL = "ip2region.db";
    private static DbSearcher searcher;

    static {
        try {
            ApplicationHome home = new ApplicationHome(Ip2RegionUtil.class);
            String path = home.getDir().getPath();

            String targetFilePath = Paths.get(path, URL).toString();
            File file = new File(targetFilePath);
            if (!file.exists()) {
                // 针对java源代码打成单独的jar文件后的路径问题处理
                path = home.getDir().getParent();
                targetFilePath = Paths.get(path, "classes", URL).toString();
            }
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, targetFilePath);

        } catch (Exception e) {
            logger.error("初始化IP库出错!", e);
        }
    }

    /**
     * 根据IP得到地址对象 获取失败返回null
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/26 13:28
     */
    public static IPLocation getLocation(String ip) {
        String NoData = "0";
        try {
            DataBlock block = searcher.memorySearch(ip);
            String result = block.getRegion();
            String[] data = result.split("\\|");
            IPLocation location = new IPLocation();
            if (data[0].equals(NoData) || data[2].equals(NoData)) {
                return null;
            }
            location.setCountry(data[0]);
            location.setArea(data[2]);
            return location;
        } catch (Exception e) {
            logger.error("ip2region解析IP出错", e);
        }
        return null;
    }
}
