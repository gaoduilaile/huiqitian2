package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.entity.Voucher;

/**
 * Created by liuGang on 2016/8/12 0012.
 */
public class SubmitOrderReq {

    /*提交订单的请求response实体类*/

    public SubmitOrderReq(List<Integer> cartGoodsIds, Byte orderOrigin, Integer deliveryInfoId,String message) {
        this.cartGoodsIds = cartGoodsIds;
        this.orderOrigin = orderOrigin;
        this.deliveryInfoId = deliveryInfoId;
        this.message = message;
    }

    /**
     * 购物车id集合
     */
    private List<Integer> cartGoodsIds;
    /**
     * 订单来源
     */
    private Byte orderOrigin;
    /**
     * 收货信息编号
     */
    private Integer deliveryInfoId;

    /**
     * 代金券
     */
    private List<Voucher> voucher;

    /**
     * 留言信息
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getCartGoodsIds() {
        return cartGoodsIds;
    }

    public void setCartGoodsIds(List<Integer> cartGoodsIds) {
        this.cartGoodsIds = cartGoodsIds;
    }

    public Byte getOrderOrigin() {
        return orderOrigin;
    }

    public void setOrderOrigin(Byte orderOrigin) {
        this.orderOrigin = orderOrigin;
    }

    public Integer getDeliveryInfoId() {
        return deliveryInfoId;
    }

    public void setDeliveryInfoId(Integer deliveryInfoId) {
        this.deliveryInfoId = deliveryInfoId;
    }

    public List<Voucher> getVoucher() {
        return voucher;
    }

    public void setVoucher(List<Voucher> voucher) {
        this.voucher = voucher;
    }
}
