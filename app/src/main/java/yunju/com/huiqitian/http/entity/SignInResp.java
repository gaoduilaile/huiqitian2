package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.SinIn;

/**
 * Created by 胡月 on 2016/8/17 0017.
 */
public class SignInResp extends BaseResp{
    /**
     * 签到初始化
     */
    private SinIn obj;

    public SinIn getObj() {
        return obj;
    }

    public void setObj(SinIn obj) {
        this.obj = obj;
    }
}
