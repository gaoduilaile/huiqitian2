package yunju.com.huiqitian.http.entity;

import java.io.Serializable;
import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.AffirmOrderMarket;
import yunju.com.huiqitian.entity.Settlement;

/**
 * Created by liuGang on 2016/8/10 0010.
 */
public class CartSettleResp extends BaseResp implements Serializable{
    /*购物车去结算返回的response解析后的实体类*/
    private Settlement obj;

    public Settlement getObj() {
        return obj;
    }

    public void setObj(Settlement obj) {
        this.obj = obj;
    }
}
