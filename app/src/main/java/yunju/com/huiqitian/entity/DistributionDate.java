package yunju.com.huiqitian.entity;

/**
 * Created by zcq on 2016-08-13.
 * <p/>
 * 物流信息获取
 */
public class DistributionDate {

    /**
     * 订单编号
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStOrderTime() {
        return stOrderTime;
    }

    public void setStOrderTime(String stOrderTime) {
        this.stOrderTime = stOrderTime;
    }

    public String getStPayTime() {
        return stPayTime;
    }

    public void setStPayTime(String stPayTime) {
        this.stPayTime = stPayTime;
    }

    public String getStReceiveTime() {
        return stReceiveTime;
    }

    public void setStReceiveTime(String stReceiveTime) {
        this.stReceiveTime = stReceiveTime;
    }

    public String getStdispatchTime() {
        return stdispatchTime;
    }

    public void setStdispatchTime(String stdispatchTime) {
        this.stdispatchTime = stdispatchTime;
    }

    public String getStFinishTime() {
        return stFinishTime;
    }

    public void setStFinishTime(String stFinishTime) {
        this.stFinishTime = stFinishTime;
    }

    public Byte getOrderState() {
        return orderState;
    }

    public void setOrderState(Byte orderState) {
        this.orderState = orderState;
    }

    /**
     * 下单时间
     */
    private String stOrderTime;

    /**
     * 支付时间
     */
    private String stPayTime;

    /**
     * 接单时间
     */
    private String stReceiveTime;

    /**
     * 配送时间
     */
    private String stdispatchTime;

    /**
     * 订单完成时间
     */

    private String stFinishTime;

    /**
     * 订单状态
     */
    private Byte orderState;



}
