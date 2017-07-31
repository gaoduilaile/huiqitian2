package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.GoodsInfo;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class SelectionResp extends BaseResp {
    /**
     * 每日精选的商品
     */
    List<GoodsInfo> obj;

    public List<GoodsInfo> getObj() {
        return obj;
    }

    public void setObj(List<GoodsInfo> obj) {
        this.obj = obj;
    }
}
