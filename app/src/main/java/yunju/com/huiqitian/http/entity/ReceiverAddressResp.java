package yunju.com.huiqitian.http.entity;

import java.io.Serializable;
import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.ReceiveAddress;

/**
 * Created by 张超群 on 2016-08-12.
 * <p/>
 * 收货地址集合
 */
public class ReceiverAddressResp extends BaseResp implements Serializable{

    private List<ReceiveAddress> obj;

    public List<ReceiveAddress> getObj() {
        return obj;
    }

    public void setObj(List<ReceiveAddress> obj) {
        this.obj = obj;
    }

}
