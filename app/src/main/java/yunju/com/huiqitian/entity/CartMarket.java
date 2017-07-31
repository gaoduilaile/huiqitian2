package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liuGang on 2016/7/28 0028.
 */
public class CartMarket implements Serializable{

    @Override
    public String toString() {
        return "CartMarket{" +
                "marketId=" + marketId +
                ", marketName='" + marketName + '\'' +
                ", dispatchFee=" + dispatchFee +
                ", freeDispatchLimit=" + freeDispatchLimit +
                ", AppCartGoodsList=" + AppCartGoodsList +
                '}';
    }

    private int marketId;//超市id(Integer)
    private String marketName;//超市名(String)
    private BigDecimal dispatchFee;//配送费(BigDecimal)
    private  BigDecimal freeDispatchLimit;//免配送费金额(BigDecimal)
    private int maxrange;//配送距离
    private double distance;//超市距离
    private List<CartGoods> AppCartGoodsList;//购物车中的产品列表(list)

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

    public List<CartGoods> getAppCartGoodsList() {
        return AppCartGoodsList;
    }

    public void setAppCartGoodsList(List<CartGoods> appCartGoodsList) {
        AppCartGoodsList = appCartGoodsList;
    }

    public int getMaxrange() {
        return maxrange;
    }

    public void setMaxrange(int maxrange) {
        this.maxrange = maxrange;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
