package yunju.com.huiqitian.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/25 0025.
 */
public class TagList implements Serializable {
    /**
     * 编号
     */
    private int id;

    /**
     * 标签描述
     */
    private String tag;

    /**
     * 数量
     */
    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
