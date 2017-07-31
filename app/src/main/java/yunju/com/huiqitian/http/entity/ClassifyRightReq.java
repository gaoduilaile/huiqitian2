package yunju.com.huiqitian.http.entity;

/**
 * Created by 胡月on 2016/7/29 0029.
 */
public class ClassifyRightReq {
    /**
     * 父分类编号
     */
    private int parentId;

    /**
     * 当前页数
     */
    private  int curpage;

    /**
     *每页多少条数据
     */
    private int rp;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }
}
