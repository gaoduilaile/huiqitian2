package yunju.com.huiqitian.http.entity;

/**
 * Created by gao on 2016/8/11 0011.
 */
public class SearchShopReq {
    /**
     * 排序字段
     */
    private String sortname;

    /**
     * 排序方式
     */
    private String sortorder;

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }
}
