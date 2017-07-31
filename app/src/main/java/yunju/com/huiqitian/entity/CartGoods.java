package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by liuGang on 2016/7/27 0027.
 */
public class CartGoods implements Serializable{

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

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Override
    public String toString() {
        return "CartGoods{" +
                "type=" + type +
                ", qty=" + qty +
                ", id=" + id +
                ", goodsId=" + goodsId +
                ", repositoryId=" + repositoryId +
                ", marketId=" + marketId +
                ", agentId=" + agentId +
                ", retailPrice=" + retailPrice +
                ", memberPrice=" + memberPrice +
                ", name='" + name + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", brandName='" + brandName + '\'' +
                ", marketName='" + marketName + '\'' +
                '}';
    }

    private Byte type;//:商品类型（正常商品、每日精选）()
    private Byte qty;//:购物车商品数量(Byte)
    private int id;//:购物车商品表id(Integer)
    private int goodsId;//:商品id(Integer)
    private int repositoryId;//:商品库id(Integer)
    private int marketId;//:商品所属商超Id(Integer)
    private int agentId;//:商品所属代理商Id(Integer)
    private BigDecimal retailPrice;//:零售价()
    private BigDecimal memberPrice;//:会员价(BigDecimal)
    private String name;//:商品名称()
    private String picUrl;//:图片路径(String)
    private String brandName;//:品牌名称(String)
    private String marketName;//:超市名称(String)
    private BigDecimal marketPrice;//市场价
}
