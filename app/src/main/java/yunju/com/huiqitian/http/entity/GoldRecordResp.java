package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;

/**
 * Created by 胡月 on 2016/8/16 0016.
 */
public class GoldRecordResp{

    /**
     * 错误ID
     */
    private byte state;

    private List<GoldRecord>obj;

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }



    public List<GoldRecord> getObj() {
        return obj;
    }

    public void setObj(List<GoldRecord> obj) {
        this.obj = obj;
    }
}
