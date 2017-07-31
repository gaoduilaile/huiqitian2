package yunju.com.huiqitian.http.entity;

/**
 * Created by Administrator on 2016/11/25 0025.
 */
public class EvalSummaryReq {
    /**
     * 商品类型
     */
    private byte type;

    /**
     * 商品id
     */
    private int id;

    /**
     * 多少条
     */
    private int rp;
    /**
     * 多少页
     */
    private int curpage;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
