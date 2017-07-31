package yunju.com.huiqitian.http.entity;

/**
 * Created by 张超群 on 2016-08-10.
 * <p/>
 * 这是订单请求的参数
 */
public class OrdersReq {

    /**
     * 订单类型
     *
     * 0：全部订单
     * 1：未付款订单
     * 3：待发货订单（需要后台确认）
     * 4：待收货订单
     * 5：待评价订单
     */
    private Byte type;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }


}
