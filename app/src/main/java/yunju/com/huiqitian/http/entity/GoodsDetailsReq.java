package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.entity.FlexiSimpleQuery;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class GoodsDetailsReq {
    private int curpage;
    private int rp;
    private List<FlexiSimpleQuery> querys;

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public List<FlexiSimpleQuery> getQuerys() {
        return querys;
    }

    public void setQuerys(List<FlexiSimpleQuery> querys) {
        this.querys = querys;
    }
}
