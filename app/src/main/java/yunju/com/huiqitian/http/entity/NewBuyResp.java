package yunju.com.huiqitian.http.entity;

import java.io.Serializable;

import yunju.com.huiqitian.entity.NewBuy;

/**
 * Created by 胡月 on 2016/8/22 0022.
 */
public class NewBuyResp implements Serializable{
    /**
     * 立即购买初始化
     */
    private NewBuy obj;

    public NewBuy getObj() {
        return obj;
    }

    public void setObj(NewBuy obj) {
        this.obj = obj;
    }
}
