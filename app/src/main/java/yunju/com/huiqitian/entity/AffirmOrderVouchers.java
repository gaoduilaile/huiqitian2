package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by liuGang on 2016/8/10 0010.
 */
public class AffirmOrderVouchers implements Serializable{

    /*确认点单中的抵用券*/

    /**
     * id:代金券编号(Integer)
     * */
    private int id;

    /**
     * type:代金券类型(Byte)
     * */
    private Byte type;

    /**
     * 代金券数额(BigDecimal)
     * */
    private BigDecimal amount;

    /**
     * 商品总价(BigDecimal)
     * */
    private BigDecimal totalPric;

    /**
     * 代金券满额使用(BigDecimal)
     * */
    private BigDecimal amountLimit;

    /**
     * 是否为可用状态(Byte)
     * */
    private Byte useable;

    /**
     * 开始时间(String)
     * */
    private String strAffectDate;

    /**
     * 结束时间(String)
     * */
    private String strExpireDate;

    /**
     * 代金券指定商品库id(Integer)
     * */
    private int repositoryId;

    /**
     * 商品所属商超Id(Integer)
     * */
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
