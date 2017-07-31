package yunju.com.huiqitian.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class MessageBean implements Serializable{
    /**
     * title : 才潇洒
     * url : /comp/platform/website/article/getComment?id=95
     */

    private String title;
    private String url;

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

    /**
     * type : 1
     * obj : {"url":"/comp/platform/website/article/getComment?id=79","title":"多大"}
     */

    /**
     * url : /comp/platform/website/article/getComment?id=79
     * title : 多大
     *
     * {"title":"才潇洒","url":"/comp/platform/website/article/getComment?id=95"}

     */


}
