package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.AllOrders;

/**
 * Created by 张超群 on 2016-08-08.
 * <p/>
 * 这个是所有订单数据
 */
public class AllOrdersResp extends BaseResp {

    /**
     * 获取所有的商品
     */
    private List<AllOrders> obj;

    public List<AllOrders> getObj() {
        return obj;
    }

    public void setObj(List<AllOrders> obj) {
        this.obj = obj;
    }

}
