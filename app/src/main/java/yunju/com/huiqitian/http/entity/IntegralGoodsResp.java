package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.IntegralGoods;

/**
 * Created by liuGang on 2016/8/16 0016.
 */
public class IntegralGoodsResp extends BaseResp{
    /*接收积分商品的response实体类*/
    private List<IntegralGoods> obj;

    public List<IntegralGoods> getObj() {
        return obj;
    }

    public void setObj(List<IntegralGoods> obj) {
        this.obj = obj;
    }
}
