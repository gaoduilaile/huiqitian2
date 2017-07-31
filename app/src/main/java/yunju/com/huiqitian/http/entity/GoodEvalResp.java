package yunju.com.huiqitian.http.entity;

import java.io.Serializable;

import yunju.com.huiqitian.base.BaseResp;

import yunju.com.huiqitian.entity.GoodEval;


/**
 * Created by 胡月 on 2016/9/30 0030.
 */
public class GoodEvalResp extends BaseResp implements Serializable {

    private GoodEval obj;

    public GoodEval getObj() {
        return obj;
    }

    public void setObj(GoodEval obj) {
        this.obj = obj;
    }
}
