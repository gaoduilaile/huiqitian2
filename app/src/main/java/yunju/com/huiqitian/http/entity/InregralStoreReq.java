package yunju.com.huiqitian.http.entity;

/**
 * Created by Administrator on 2016/9/27 0027.
 * 兑换积分 实体类
 */
public class InregralStoreReq {

    /**
     * 商品编号id
     */

    private int id;

    public InregralStoreReq(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
