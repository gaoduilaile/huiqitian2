package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by 胡月on 2016/8/22 0022.
 */
public class NewBuyGoods implements Serializable{
    /**
     * 商品类型
     */
    private Byte type;

    /**
     * 购物车商品数量
     */
    private Byte qty;

    /**
     * 购物车商品表id
     */
    private int id;

    /**
     * 商品id
     */
    private int goodsId;

    /**
     * 商品库id
     */
    private int repositoryId;

    /**
     * 商品所属商超Id
     */
    private int marketId;

    /**
     * 商品所属代理商Id
     */
    private int agentId;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 会员价
     */
    private BigDecimal memberPrice;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 图片路径
     */
    private String picUrl;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 超市名称
     */
    private String marketName;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getQty() {
        return qty;
    }

    public void setQty(Byte qty) {
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
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

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }
}
