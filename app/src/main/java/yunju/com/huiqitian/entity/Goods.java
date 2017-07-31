package yunju.com.huiqitian.entity;

import java.math.BigDecimal;

/**
 * Created by 张超群 on 2016-08-08.
 * <p/>
 * 订单中的商品
 */
public class Goods {

    /**
     * 商品类型（正常商品、每日精选）
     */
    private Byte type;

    /**
     * 购物车商品数量
     */
    private Byte qty;

    /**
     * 商品id
     */
    private int goodsId;

    /**
     * 商品库id
     */
    private int repositoryId;

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
     * 商品所属商超Id
     */
    private int marketId;

    /**
     * 超市名称
     */
    private String marketName;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

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

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

}
