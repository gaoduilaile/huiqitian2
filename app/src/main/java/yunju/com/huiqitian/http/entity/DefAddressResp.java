package yunju.com.huiqitian.http.entity;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.ReceiveAddress;

/**
 * Created by liuGang on 2016/8/12 0012.
 */
public class DefAddressResp extends BaseResp{
    /*接收默认地址的resp实体类*/
    public ReceiveAddress getObj() {
        return obj;
    }

    public void setObj(ReceiveAddress obj) {
        this.obj = obj;
    }

    private ReceiveAddress obj;

}
