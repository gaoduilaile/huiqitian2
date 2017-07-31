package yunju.com.huiqitian.http.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9 0009.
 * 所有级别的评论
 *
 * {"state":1,"obj":[{"total":3,"evalLevelId":1,"evalDesc":null}],"msg":"请求成功"}
 */
public class AllGradeEvaluates {
    /**
     * state : 1
     * obj : [{"total":3,"evalLevelId":1,"evalDesc":null}]
     * msg : 请求成功
     */

    private int state;
    private String msg;
    /**
     * total : 3
     * evalLevelId : 1
     * evalDesc : null
     */

    private List<ObjBean> obj;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private int total;
        private int evalLevelId;
        private String evalDesc;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getEvalLevelId() {
            return evalLevelId;
        }

        public void setEvalLevelId(int evalLevelId) {
            this.evalLevelId = evalLevelId;
        }

        public String getEvalDesc() {
            return evalDesc;
        }

        public void setEvalDesc(String evalDesc) {
            this.evalDesc = evalDesc;
        }
    }

   /* 接口名称：获取商品的所有级别评价
    接口Url：http://root_path/app/buyer/good/grade/list.do
    需要登录：否
    接口参数：{
        id：商品编号（Integer）
        type：商品类型（byte，0：正常商品，2：每日精选，3：秒杀）
    }
    返回obj{
        [{
            evalLevelId：级别评级Id（Integer）
            evalDesc：级别评价描述（String）
            total：数量（Integer）
        }]}*/


}
