package com.bfly.common.ipseek;

import com.bfly.common.ipseek.ip2region.Ip2RegionUtil;
import com.bfly.common.ipseek.ipapi.IpApiIpUtil;
import com.bfly.common.ipseek.ipip.IpIpIpUtil;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/29 10:20
 */
public class IpSeekerUtil {

    private static int LENGTH = 7;

    /**
     * 根据IP获得地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/2 12:14
     */
    public static IPLocation getIPLocation(String ip) {
        if (ip == null || ip.length() < LENGTH) {
            return null;
        }
        IPLocation location = Ip2RegionUtil.getLocation(ip);
        if (location == null) {
            location = IpIpIpUtil.getLocation(ip);
        }
        if (location == null) {
            location = IpApiIpUtil.getLocation(ip);
        }
        return location;
    }
}
