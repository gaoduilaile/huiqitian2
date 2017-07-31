package yunju.com.huiqitian.http.entity;

import java.util.Date;

import yunju.com.huiqitian.base.BaseResp;

/**
 * Created by zcq on 2016-08-10.
 * <p/>
 * 物流信息获取
 */
public class DistributionDateResp extends BaseResp {

    /**
     * 订单编号
     */
    private String id;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 接单时间
     */
    private Date receiveTime;

    /**
     * 配送时间
     */
    private Date dispatchTime;

    /**
     * 订单完成时间
     */

    private Date finishTime;

    /**
     * 订单状态
     */
    private Byte orderState;

    /**
     * 配送员id
     */
    private int dispatchUser;

    /**
     * 配送人员账号
     */
    private String  name;

    /**
     * 配送人员真实姓名
     */
    private String realName;

    /**
     * 配送人员手机号
     */
    private String  mobile;

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Byte getOrderState() {
        return orderState;
    }

    public void setOrderState(Byte orderState) {
        this.orderState = orderState;
    }

    public int getDispatchUser() {
        return dispatchUser;
    }

    public void setDispatchUser(int dispatchUser) {
        this.dispatchUser = dispatchUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
