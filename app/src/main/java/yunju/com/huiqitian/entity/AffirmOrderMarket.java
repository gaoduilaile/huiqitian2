package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liuGang on 2016/8/10 0010.
 */
public class AffirmOrderMarket implements Serializable{

    /*确认订单中的商超*/

    /**
     * 超市id(Integer)
     * */
    private int marketId;

    /**
     * 超市名(String)
     * */
    private String marketName;

    /**
     * 配送费(BigDecimal)
     * */
    private BigDecimal dispatchFee;

    /**
     * 免配送费金额(BigDecimal)
     * */
    private  BigDecimal freeDispatchLimit;

    /**
     * 确认点单中的产品列表(list)
     * */
    private List<CartGoods> AppCartGoodsList;

    /**
     * 抵用券
     * */
    private List<AffirmOrderVouchers> appVouchers;

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

    public List<AffirmOrderVouchers> getAppVouchers() {
        return appVouchers;
    }

    public void setAppVouchers(List<AffirmOrderVouchers> appVouchers) {
        this.appVouchers = appVouchers;
    }

}
