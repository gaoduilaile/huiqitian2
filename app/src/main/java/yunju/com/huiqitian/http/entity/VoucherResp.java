package yunju.com.huiqitian.http.entity;

import java.math.BigDecimal;

import yunju.com.huiqitian.base.BaseResp;

/**
 * Created by 张超群 on 2016-08-05.
 * <p>
 * 代金券
 */
public class VoucherResp extends BaseResp {

    /**
     * 代金券编号
     */
    private int id;

    /**
     * 代金券类型
     */
    private Byte type;

    /**
     * 代金券数额
     */
    private BigDecimal amount;


    /**
     * 开始时间
     */
    private String strAffectDate;

    /**
     * 结束时间
     */
    private String strExpireDate;

    /**
     * 是否对所有商品使用
     */
    private Byte allGoods;

    /**
     * 超市名称
     */
    private String marketName;

    /**
     *满多少元使用
     */
    private BigDecimal amountLimit;

    /**
     * 代金券规则
     */
    private String ruleDescription;

    public String getStrAffectDate() {
        return strAffectDate;
    }

    public void setStrAffectDate(String strAffectDate) {
        this.strAffectDate = strAffectDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public String getStrExpireDate() {
        return strExpireDate;
    }

    public void setStrExpireDate(String strExpireDate) {
        this.strExpireDate = strExpireDate;
    }

    public Byte getAllGoods() {
        return allGoods;
    }

    public void setAllGoods(Byte allGoods) {
        this.allGoods = allGoods;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public BigDecimal getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(BigDecimal amountLimit) {
        this.amountLimit = amountLimit;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }
}
