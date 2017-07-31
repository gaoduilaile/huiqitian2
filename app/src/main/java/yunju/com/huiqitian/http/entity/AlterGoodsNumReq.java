package yunju.com.huiqitian.http.entity;

/**
 * Created by liuGang on 2016/8/4 0004.
 */
public class AlterGoodsNumReq {
    public void setId(int id) {
        this.id = id;
    }

    public void setGoodsQty(Byte goodsQty) {
        this.goodsQty = goodsQty;
    }

    public AlterGoodsNumReq(int id, Byte goodsQty) {
        this.id = id;
        this.goodsQty = goodsQty;
    }

    @Override
    public String toString() {
        return "AlterGoodsNumReq{" +
                "id=" + id +
                ", goodsQty=" + goodsQty +
                '}';
    }

    public int getId() {
        return id;
    }

    public Byte getGoodsQty() {
        return goodsQty;
    }

    /**
     * 商品编号（Integer,必须
     */
    private int id;//）
    /**
     * 修改后的数量（Byte,大于0必须）
     */
    private Byte goodsQty;
}
