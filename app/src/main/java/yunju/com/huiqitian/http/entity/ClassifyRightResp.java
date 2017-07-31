package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.ClassifyInfo;

/**
 * Created by 胡月 on 2016/7/27 0027.
 */
public class ClassifyRightResp extends BaseResp{
    private List<ClassifyInfo> obj;

    public List<ClassifyInfo> getObj() {
        return obj;
    }

    public void setObj(List<ClassifyInfo> obj) {
        this.obj = obj;
    }
}
