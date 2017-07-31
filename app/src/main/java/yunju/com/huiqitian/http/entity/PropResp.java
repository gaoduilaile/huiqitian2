package yunju.com.huiqitian.http.entity;

import java.io.Serializable;
import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.GoodsProp;

/**
 * Created by 胡月 on 2016/8/9 0009.
 */
public class PropResp extends BaseResp implements Serializable {
    /**
     * 商品属性
     */
    private List<GoodsProp> obj;

    public List<GoodsProp> getObj() {
        return obj;
    }

    public void setObj(List<GoodsProp> obj) {
        this.obj = obj;
    }
}
