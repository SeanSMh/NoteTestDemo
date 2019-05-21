package sean.com.example.notetest;

import android.util.Log;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sean
 * @data 2019/5/21
 */
public class TimeUtil {

    private static volatile TimeUtil instance;

    private TimeUtil() {}

    public static TimeUtil getInstance() {
        if(instance == null) {
            synchronized (TimeUtil.class) {
                if(instance == null) {
                    instance = new TimeUtil();
                }
            }
        }
        return instance;
    }

    public long compute(String end) {
        long diff = 0;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            //获取当前时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String now = simpleDateFormat.format(date);

            Date startTime = df.parse(now);
            Date endTime = df.parse(end);

            diff = endTime.getTime()-startTime.getTime();
            /*long days = diff / (1000 * 60 * 60 * 24);
            long hours =  diff / (1000 * 60 * 60 );
            long minutes = diff / (1000*60);*/

            //Log.d("TAG剩余时间----->", diff + ":" + days + ":" + hours + ":" + minutes + "");
        } catch (Exception e) {
            Log.d("TAG----->", e.toString());
        }
        return diff;
    }

}
