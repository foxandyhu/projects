import com.bfly.common.DateUtil;
import com.bfly.common.IDEncrypt;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.ipseek.ip2region.Ip2RegionUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class T3 {
    public static void main(String[] args) {
//        String idStr= IDEncrypt.encode(1000);
//        System.out.println(idStr);
//
//        long id=IDEncrypt.decode(idStr+"e");
//        System.out.println(id);

//        IPLocation location = Ip2RegionUtil.getLocation("175.7.65.176");
//        System.out.println(location.getCountry());

        Date date=DateUtil.getCurrentFirstDayOfWeek();
        System.out.println(DateUtil.formatterDateTimeStr(date));

        date=DateUtil.getCurrentLastDayOfWeek();
        System.out.println(DateUtil.formatterDateTimeStr(date));
    }
}
