package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.ClassifyInfo;

/**
 * Created by 胡月 on 2016/7/29 0029.
 */
public class ClassifyLifeResp extends BaseResp{
    private List<ClassifyInfo> obj;

    public List<ClassifyInfo> getObj() {
        return obj;
    }

    public void setObj(List<ClassifyInfo> obj) {
        this.obj = obj;
    }
}
