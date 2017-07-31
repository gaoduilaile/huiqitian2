package yunju.com.huiqitian.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by 胡月 on 2016/11/10 0010.
 * 讲推送的消息存入数据库
 */
@Table(name="message")
public class Message implements Serializable{
    @Column(name="type" ,isId = true)
    private int type;
    @Column(name="title")
    private String title;
    @Column(name="url")
    private String url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
