package yunju.com.huiqitian.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by gao on 2016/8/16 0016.
 * 历史记录
 */
@Table(name = "history")
public class History implements Serializable{
    @Column(name = "goodsId",isId = true)
    private int goodsId;
    @Column(name = "imgUrl")
    private String imgUrl;
    @Column(name = "goodsName")
    private String goodsName;
    @Column(name = "goodsMoney")
    private String goodsMoney;
    @Column(name = "shopName")
    private String shopName;
    @Column(name = "shopDistance")
    private String shopDistance;
    @Column(name = "type")
    private Byte type;
    @Column(name = "freeDispatchLimit")
    private String freeDispatchLimit;
    @Column(name = "marketPrice")
    private String marketPrice;
    @Column(name="id")
    private int id;
    @Column(name ="marketId")
    private int marketId;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(String goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDistance() {
        return shopDistance;
    }

    public void setShopDistance(String shopDistance) {
        this.shopDistance = shopDistance;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getFreeDispatchLimit() {
        return freeDispatchLimit;
    }

    public void setFreeDispatchLimit(String freeDispatchLimit) {
        this.freeDispatchLimit = freeDispatchLimit;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }
}
