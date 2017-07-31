package yunju.com.huiqitian.http.entity;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class IntegralListReq {
    /*积分商品列表分页*/

    public IntegralListReq(Integer curpage, Integer rp) {
        this.curpage = curpage;
        this.rp = rp;
    }

    /**
     * 当前多少页（Integer，非空）
     */
    private Integer curpage;
    /**
     * 每页多少条（Integer，非空）
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
