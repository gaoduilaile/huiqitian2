package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gao on 2016/7/26 0026.
 */
public class GoodsInfo implements Serializable {
    /**
     * 商品类型
     */
    private byte type;

    /**
     * 商品编号
     */
    private int id;

    /**
     * 商品库商品编号
     */
    private int repositoryId;

    /**
     * 超市编号
     */
    private int marketId;

    /**
     * 代理商编号
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
     * 销售数量
     */
    private int useQty;

    /**
     * 剩余数量
     */
    private int leftQty;

    /**
     * 标示不限定
     */
    private int limit;

    /**
     * 商品副标题
     */
    private String subTitle;

    /**
     * 商品描述
     */
    private String describe;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品分类编号
     */
    private  int gCategoryId;

    /**
     * 商品品牌编号
     */
    private int brandId;

    /**
     * 图片存储的名称
     */
    private String picStoredName;

    /**
     * 图片存储路径
     */
    private  String picPath;

    /**
     * 图片全路径
     */
    private String picUrl;

    /**
     * 口碑分数
     */
    private int score;

    /**
     * 上架时间
     */
    private Date shelve;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 距离
     */
    private double distance;

    /**
     * 超市名称
     */
    private String marketName;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 配送费
     */
    private BigDecimal dispatchFee;

    /**
     * 满额免费送费
     */
    private BigDecimal freeDispatchLimit;

    /**
     * 图片的id
     */
    private int picId;

    public byte getType() {
        return type;
    }

    public BigDecimal getDispatchFee() {
        return dispatchFee;
    }

    public void setDispatchFee(BigDecimal dispatchFee) {
        this.dispatchFee = dispatchFee;
    }

    public BigDecimal getFreeDispatchLimit() {
        return freeDispatchLimit;
    }

    public void setFreeDispatchLimit(BigDecimal freeDispatchLimit) {
        this.freeDispatchLimit = freeDispatchLimit;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUseQty() {
        return useQty;
    }

    public void setUseQty(int useQty) {
        this.useQty = useQty;
    }

    public int getLeftQty() {
        return leftQty;
    }

    public void setLeftQty(int leftQty) {
        this.leftQty = leftQty;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getgCategoryId() {
        return gCategoryId;
    }

    public void setgCategoryId(int gCategoryId) {
        this.gCategoryId = gCategoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getPicStoredName() {
        return picStoredName;
    }

    public void setPicStoredName(String picStoredName) {
        this.picStoredName = picStoredName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getShelve() {
        return shelve;
    }

    public void setShelve(Date shelve) {
        this.shelve = shelve;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "type=" + type +
                ", id=" + id +
                ", repositoryId=" + repositoryId +

                '}';
    }
}
