package yunju.com.huiqitian.http.entity;

import java.io.Serializable;
import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.GoldDetail;

/**
 * Created by 胡月 on 2016/8/18 0018.
 */
public class GoldDetailResp extends BaseResp implements Serializable{
    List<GoldDetail>obj;

    public List<GoldDetail> getObj() {
        return obj;
    }

    public void setObj(List<GoldDetail> obj) {
        this.obj = obj;
    }
}
