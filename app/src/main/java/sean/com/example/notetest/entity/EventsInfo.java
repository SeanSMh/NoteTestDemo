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
    @Generated(hash = 2084793639)
    public EventsInfo(Long id, String title, String content, String data) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.data = data;
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
    
}
