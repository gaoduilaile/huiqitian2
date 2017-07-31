package yunju.com.huiqitian.http.entity;

/**
 * Created by gao on 2016/7/27 0027.
 */
public class AddCartReq {
    /**
     * 商品类型
     */
    private byte type;

    /**
     * 商品id
     */
    private int id;

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
}
