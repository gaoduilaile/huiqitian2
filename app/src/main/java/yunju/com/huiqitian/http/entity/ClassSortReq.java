package yunju.com.huiqitian.http.entity;

/**
 * Created by 胡月 on 2016/8/2 0002.
 */
public class ClassSortReq {

    /**
     * 当前页数
     */
    private int curpage;

    /**
     * 每页多少条
     */
    private int rp;

    /**
     * 排序字段
     销量："qty"
     口碑："score"
     价格："price"
     距离："distance"
     */
    private String sortname;

    /**
     * 排序方式
     升序："asc"
     倒序："desc"
     */
    private String sortorder;


    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

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
