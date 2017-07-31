package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.EvalList;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class EvalTextResp extends BaseResp{
    private List<EvalList> obj;

    public List<EvalList> getObj() {
        return obj;
    }

    public void setObj(List<EvalList> obj) {
        this.obj = obj;
    }
}
