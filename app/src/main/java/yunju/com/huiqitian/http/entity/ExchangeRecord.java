package yunju.com.huiqitian.http.entity;

/**
 * Created by liuGang on 2016/8/18 0018.
 */
public class ExchangeRecord {

    /*积分商城兑换记录request实体*/

    public ExchangeRecord(Integer curpage, Integer rp, Byte state) {
        this.curpage = curpage;
        this.rp = rp;
        this.state = state;
    }

    /**
     * 当前多少页（Integer，非空）
     */

    private Integer curpage;
    /**
     * 每页多少条（Integer，非空）
     */
    private Integer rp;
    /**
     * 兑换状态 （Byte ,1未兑换 2已兑换 3已过期）
     */
    private Byte state;

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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }


}
