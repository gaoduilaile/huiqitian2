package yunju.com.huiqitian.http.entity;

import java.util.List;

/**
 * Created by 张超群 on 2016-08-10.
 * <p/>
 * 评价订单提交
 */
public class EvaluationCommitReq {

    /**
     * 图片二进制数组
     */
    private List<String> files;
    /**
     * 评价内容
     */
    private String evalText;

    /**
     * 评价等级
     */
    private int evalLevelId;

    /**
     * 标签id集合
     */

    private List<Integer> tagIds;

    /**
     * 订单商品id
     */

    private int orderGoodsId;

    /**
     * 是否匿名评价
     */
    private Byte cryptonym;

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public int getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(int orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public String getEvalText() {
        return evalText;
    }

    public void setEvalText(String evalText) {
        this.evalText = evalText;
    }

    public int getEvalLevelId() {
        return evalLevelId;
    }

    public void setEvalLevelId(int evalLevelId) {
        this.evalLevelId = evalLevelId;
    }

    public Byte getCryptonym() {
        return cryptonym;
    }

    public void setCryptonym(Byte cryptonym) {
        this.cryptonym = cryptonym;
    }

}
