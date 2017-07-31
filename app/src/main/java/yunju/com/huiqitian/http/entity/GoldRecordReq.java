package yunju.com.huiqitian.http.entity;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class GoldRecordReq {
    /**
     * 当前页数
     */
    private Integer curpage;

    /**
     * 当页多少条
     */
    private Integer rp;

    public Integer getCurpage() {
        return curpage;
    }

    public void setCurpage(Integer curpage) {
        this.curpage = curpage;
    }

    public Integer getRp() {
        return rp;
    }

    public void setRp(Integer rp) {
        this.rp = rp;
    }
}
