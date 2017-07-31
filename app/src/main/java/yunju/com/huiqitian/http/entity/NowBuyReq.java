package yunju.com.huiqitian.http.entity;

/**
 * Created by 胡月 on 2016/8/22 0022.
 */
public class NowBuyReq {
    /**
     * 商品id
     */
    private int id;

    /**
     * 商品类型
     */
    private Byte type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
