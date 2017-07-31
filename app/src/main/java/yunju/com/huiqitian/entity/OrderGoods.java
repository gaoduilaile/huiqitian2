package yunju.com.huiqitian.entity;

import java.math.BigDecimal;

/**
 * Created by 张超群 on 2016-08-13.
 * <p/>
 * 订单商品集合
 */
public class OrderGoods {

    /**
     * 订单id
     */
    private String id;

    /**
     * 订单商品id
     */
    private int orderGoodsId;

    /**
     * 商品id
     */
    private int goodsId;

    /**
     * 商品类型（正常商品、每日精选）(Byte)
     */
    private Byte type;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 图片路径
     */
    private String picUrl;

    /**
     * 图片名
     */
    private String picStoredName;

    /**
     * 图片路径
     */
    private String picPath;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(int orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

}
