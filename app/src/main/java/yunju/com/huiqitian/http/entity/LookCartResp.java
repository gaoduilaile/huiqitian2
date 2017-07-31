package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.CartMarket;

/**
 * Created by liuGang on 2016/7/27 0027.
 */
public class LookCartResp extends BaseResp{

    private List<CartMarket> obj;

    public List<CartMarket> getObj() {
        return obj;
    }

    public void setObj(List<CartMarket> obj) {
        this.obj = obj;
    }
}
