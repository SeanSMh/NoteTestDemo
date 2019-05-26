package sean.com.example.notetest.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author Sean
 * @data 2019/5/20
 * 记录用户待办事项
 */
@Entity
public class EventsInfo {
    @Id(autoincrement = true)
    private Long id;
    private String title;   //标题
    private String content;  //内容
    private String data;   //时间

    private long day;
    private long hour;
    private long minute;
    private long second;
    private long diff;

    private int backgroundColor;


    @Generated(hash = 1168781775)
    public EventsInfo(Long id, String title, String content, String data, long day,
            long hour, long minute, long second, long diff, int backgroundColor) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.data = data;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.diff = diff;
        this.backgroundColor = backgroundColor;
    }
    @Generated(hash = 1146244956)
    public EventsInfo() {
    }


    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public long getDay() {
        return this.day;
    }
    public void setDay(long day) {
        this.day = day;
    }
    public long getHour() {
        return this.hour;
    }
    public void setHour(long hour) {
        this.hour = hour;
    }
    public long getMinute() {
        return this.minute;
    }
    public void setMinute(long minute) {
        this.minute = minute;
    }
    public long getSecond() {
        return this.second;
    }
    public void setSecond(long second) {
        this.second = second;
    }

    public long getDiff() {
        return this.diff;
    }

    public void setDiff(long diff) {
        this.diff = diff;
    }
    public int getBackgroundColor() {
        return this.backgroundColor;
    }
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
