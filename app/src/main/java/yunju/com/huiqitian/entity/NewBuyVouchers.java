package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 胡月 on 2016/8/22 0022.
 */
public class NewBuyVouchers implements Serializable{

    /**
     * 代金券编号
     */
    private int id;

    /**
     * 代金券类型
     */
    private Byte type;

    /**
     * 代金券活动类型
     */
    private Byte activityType;

    /**
     * 代金券数额
     */
    private BigDecimal amount;

    /**
     * 商品总价
     */
    private BigDecimal totalPric;

    /**
     * 代金券满额使用
     */
    private BigDecimal amountLimit;

    /**
     * 是否为可用状态
     */
    private Byte useable;

    /**
     * 开始时间
     */
    private String strAffectDate;

    /**
     * 结束时间
     */
    private String strExpireDate;

    /**
     * 代金券指定商品库id
     */
    private int repositoryId;

    /**
     * 商超id
     */
    private int marketId;

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

    public Byte getActivityType() {
        return activityType;
    }

    public void setActivityType(Byte activityType) {
        this.activityType = activityType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalPric() {
        return totalPric;
    }

    public void setTotalPric(BigDecimal totalPric) {
        this.totalPric = totalPric;
    }

    public BigDecimal getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(BigDecimal amountLimit) {
        this.amountLimit = amountLimit;
    }

    public Byte getUseable() {
        return useable;
    }

    public void setUseable(Byte useable) {
        this.useable = useable;
    }

    public String getStrAffectDate() {
        return strAffectDate;
    }

    public void setStrAffectDate(String strAffectDate) {
        this.strAffectDate = strAffectDate;
    }

    public String getStrExpireDate() {
        return strExpireDate;
    }

    public void setStrExpireDate(String strExpireDate) {
        this.strExpireDate = strExpireDate;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }
}
