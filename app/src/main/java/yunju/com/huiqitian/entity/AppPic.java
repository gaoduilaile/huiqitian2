package yunju.com.huiqitian.entity;

import java.io.Serializable;

/**
 * Created by 胡月 on 2016/9/30 0030.
 */
public class AppPic implements Serializable{
    /**
     * 图片编号
     */
    private int id;

    /**
     * 图片路径
     */
    private String picUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
