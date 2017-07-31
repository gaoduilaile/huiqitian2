package yunju.com.huiqitian.http.entity;

/**
 * Created by 胡月 on 2016/8/12 0012.
 */
public class ImportReq {
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
   /* private String sortname;*/

    /**
     * 排序方式
     升序："asc"
     倒序："desc"
     */
    /*private String sortorder;*/

    /**
     * 分类Id
     */
    /*private int catId;*/

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


}
