package yunju.com.huiqitian.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;

/**
 * Created by 张超群 on 2016-08-15.
 *
 * 响应拿到的订单id数组
 */
public class OrdersIds extends BaseResp{

   private List<Order>obj;

    public List<Order> getObj() {
        return obj;
    }

    public void setObj(List<Order> obj) {
        this.obj = obj;
    }
}
