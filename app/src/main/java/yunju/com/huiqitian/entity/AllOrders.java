package yunju.com.huiqitian.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 张超群 on 2016-08-13.
 * <p/>
 * 获取所有的订单
 */
public class AllOrders {

    public BigDecimal getPayFee() {
        return payFee;
    }

    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }

    /**
     * 支付金额
     */
    private BigDecimal payFee;

    /**
     * 订单ID
     */
    private String id;

    /**
     * 超市ID
     */
    private int marketId;

    /**
     * 超市名字
     */
    private String marketName;

    /**
     * 配送费
     */
    private BigDecimal dispatchFee;

    /**
     * 免配送费金额
     */
    private BigDecimal freeDispatchLimit;

    /**
     * 评价状态
     */
    private Byte evalState;

    /**
     * 订单状态
     */
    private Byte orderState;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 运费
     */

    private BigDecimal freight;

    public Byte getEvalState() {
        return evalState;
    }

    public void setEvalState(Byte evalState) {
        this.evalState = evalState;
    }

    /**
     * 订单内的所有商品
     */

    private List<Goods> appOrderGoodsList;

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public BigDecimal getDispatchFee() {
        return dispatchFee;
    }

    public void setDispatchFee(BigDecimal dispatchFee) {
        this.dispatchFee = dispatchFee;
    }

    public BigDecimal getFreeDispatchLimit() {
        return freeDispatchLimit;
    }

    public void setFreeDispatchLimit(BigDecimal freeDispatchLimit) {
        this.freeDispatchLimit = freeDispatchLimit;
    }

    public Byte getOrderState() {
        return orderState;
    }

    public void setOrderState(Byte orderState) {
        this.orderState = orderState;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Goods> getAppOrderGoodsList() {
        return appOrderGoodsList;
    }

    public void setAppOrderGoodsList(List<Goods> appOrderGoodsList) {
        this.appOrderGoodsList = appOrderGoodsList;
    }

    @Override
    public String toString() {
        return "AllOrders{" +
                "id='" + id + '\'' +
                ", marketId=" + marketId +
                ", marketName='" + marketName + '\'' +
                ", dispatchFee=" + dispatchFee +
                ", freeDispatchLimit=" + freeDispatchLimit +
                ", orderState=" + orderState +
                ", totalPrice=" + totalPrice +
                ", freight=" + freight +
                ", appOrderGoodsList=" + appOrderGoodsList +
                '}';
    }
}
