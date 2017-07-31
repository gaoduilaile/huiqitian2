package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.ShopInfo;

/**
 * Created by gao on 2016/8/11 0011.
 */
public class SearchShopResp extends BaseResp {
    List<ShopInfo> obj;

    public List<ShopInfo> getObj() {
        return obj;
    }

    public void setObj(List<ShopInfo> obj) {
        this.obj = obj;
    }
}
