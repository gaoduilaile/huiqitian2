package yunju.com.huiqitian.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zcq on 2016-08-16.
 * <p/>
 * 订单详情实体类
 */
public class OrderDetailed {

    /**
     * 支付单号
     */
    private String paymentId;

    /**
     * 支付方式
     */
    private Byte payMode;

    /**
     * 下单时间
     */
    private String strOrderTime;

    /**
     * 付款时间
     */
    private String strPayTime;

    /**
     * 接单时间
     */
    private String strReceiveTime;

    /**
     * 配送时间
     */
    private String strDispatchTime;

    /**
     * 完成时间
     */
    private String strFinishTime;

    /**
     * 商品总价
     */
    private BigDecimal amount;

    /**
     * 代金券面额
     */
    private BigDecimal voucherAmount;

    /**
     * 收货地址id
     */
    private int deliveryInfoId;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 收货人性别
     */
    private Byte sex;

    /**
     * 收货人电话
     */
    private String spareTel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    /*收货人电话*/
    private String tel;

    /**
     * 收货人地址信息
     */
    private String addressInfo;

    /**
     * 收货人地址明细
     */
    private String detailAddr;


    /**
     * 订单号
     */
    private String id;

    /**
     * 超市Id
     */
    private int marketId;

    /**
     * 超市名
     */
    private String marketName;

    /**
     * 配送费
     */
    private BigDecimal dispatchFee;

    /**
     * 免费送费金额
     */
    private BigDecimal freeDispatchLimit;

    /**
     * 订单状态
     */
    private Byte orderState;

    /**
     * 评价状态
     */
    private Byte evalState;

    /**
     * 实际付款总价
     */
    private BigDecimal totalPrice;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 实际付款总价
     */
    private BigDecimal payFee;

    /**
     * 订单内所有的商品
     */
    private List<Goods> appOrderGoodsList;


    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }
    public BigDecimal getPayFee() {
        return payFee;
    }

    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Byte getPayMode() {
        return payMode;
    }

    public void setPayMode(Byte payMode) {
        this.payMode = payMode;
    }

    public String getStrOrderTime() {
        return strOrderTime;
    }

    public void setStrOrderTime(String strOrderTime) {
        this.strOrderTime = strOrderTime;
    }

    public String getStrPayTime() {
        return strPayTime;
    }

    public void setStrPayTime(String strPayTime) {
        this.strPayTime = strPayTime;
    }

    public String getStrReceiveTime() {
        return strReceiveTime;
    }

    public void setStrReceiveTime(String strReceiveTime) {
        this.strReceiveTime = strReceiveTime;
    }

    public String getStrDispatchTime() {
        return strDispatchTime;
    }

    public void setStrDispatchTime(String strDispatchTime) {
        this.strDispatchTime = strDispatchTime;
    }

    public String getStrFinishTime() {
        return strFinishTime;
    }

    public void setStrFinishTime(String strFinishTime) {
        this.strFinishTime = strFinishTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(BigDecimal voucherAmount) {
        this.voucherAmount = voucherAmount;
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

    public Byte getEvalState() {
        return evalState;
    }

    public void setEvalState(Byte evalState) {
        this.evalState = evalState;
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

    public int getDeliveryInfoId() {
        return deliveryInfoId;
    }

    public void setDeliveryInfoId(int deliveryInfoId) {
        this.deliveryInfoId = deliveryInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getSpareTel() {
        return spareTel;
    }

    public void setSpareTel(String spareTel) {
        this.spareTel = spareTel;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }
}
