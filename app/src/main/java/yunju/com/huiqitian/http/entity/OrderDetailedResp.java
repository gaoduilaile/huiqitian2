package yunju.com.huiqitian.http.entity;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.OrderDetailed;

/**
 * Created by zcq on 2016-08-16.
 * <p/>
 * 订单详细的响应数据
 */
public class OrderDetailedResp extends BaseResp {

    private OrderDetailed obj;

    public OrderDetailed getObj() {
        return obj;
    }

    public void setObj(OrderDetailed obj) {
        this.obj = obj;
    }
}
