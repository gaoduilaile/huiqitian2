package yunju.com.huiqitian.http.entity;

import java.io.Serializable;
import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.vm.find.model.FindModel;

/**
 * Created by 高英祥 on 2016/7/26 0026.
 */
public class SearchResp extends BaseResp implements Serializable {

    private List<GoodsInfo> obj;

    public List<GoodsInfo> getObj() {
        return obj;
    }

    public void setObj(List<GoodsInfo> obj) {
        this.obj = obj;
    }
}
