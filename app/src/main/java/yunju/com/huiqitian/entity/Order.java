package yunju.com.huiqitian.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/6 0006.
 */
public class Order {
    /**
     * 订单id
     */
    private String id;
    /**
     * 订单会员id
     */
    private int memberId;
    /**
     * 订单超市id
     */
    private int marketId;
    /**
     * 订单代金券id
     */
    private int voucherId;
    /**
     * 订单地址id
     */
    private int deliveryInfoId;
    /**
     * 订单原始运费
     */
    private BigDecimal freightOrigin;
    /**
     * 订单运费
     */
    private BigDecimal freight;
    /**
     * 订单总价格
     */
    private BigDecimal totalPrice;
    /**
     * 订单实际需要支付价格
     */
    private BigDecimal payFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getDeliveryInfoId() {
        return deliveryInfoId;
    }

    public void setDeliveryInfoId(int deliveryInfoId) {
        this.deliveryInfoId = deliveryInfoId;
    }

    public BigDecimal getFreightOrigin() {
        return freightOrigin;
    }

    public void setFreightOrigin(BigDecimal freightOrigin) {
        this.freightOrigin = freightOrigin;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getPayFee() {
        return payFee;
    }

    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }
}
