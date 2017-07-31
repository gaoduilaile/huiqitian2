package yunju.com.huiqitian.entity;

import java.util.List;

/**
 * Created by zcq on 2016-08-13.
 *
 * 订单评价响应数据
 */
public class EvaluationOrder {

    /**
     * 订单商品集合
     */
    private List<OrderGoods> appOrderGoods;

    /**
     * 评价等级
     */
    private List<EvalLevel> evalLevel;

    /**
     * 云标签
     */
    private List<EvalTag> evaltag;

    public List<OrderGoods> getAppOrderGoods() {
        return appOrderGoods;
    }

    public void setAppOrderGoods(List<OrderGoods> appOrderGoods) {
        this.appOrderGoods = appOrderGoods;
    }

    public List<EvalLevel> getEvalLevel() {
        return evalLevel;
    }

    public void setEvalLevel(List<EvalLevel> evalLevel) {
        this.evalLevel = evalLevel;
    }

    public List<EvalTag> getEvaltag() {
        return evaltag;
    }

    public void setEvaltag(List<EvalTag> evaltag) {
        this.evaltag = evaltag;
    }

}
