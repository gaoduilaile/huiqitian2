package yunju.com.huiqitian.http.entity;

/**
 * Created by 胡月 on 2016/8/24 0024.
 */
public class NowSubmitOrderReq {

    /**
     * 商品id
     */
    private int goodsId;

    /**
     * 商品类型
     */
    private Byte type;

    /**
     * 收货信息id
     */
    private int deliveryInfoId;

    /**
     * 订单来源
     */
    private Byte orderOrigin;

    /**
     * 代金券id
     */
    private int voucherId;

    /**
     * 留言内容
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public int getDeliveryInfoId() {
        return deliveryInfoId;
    }

    public void setDeliveryInfoId(int deliveryInfoId) {
        this.deliveryInfoId = deliveryInfoId;
    }

    public Byte getOrderOrigin() {
        return orderOrigin;
    }

    public void setOrderOrigin(Byte orderOrigin) {
        this.orderOrigin = orderOrigin;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }
}
