package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 胡月 on 2016/8/22 0022.
 */
public class NewBuy implements Serializable{
    /**
     * 商品
     */
    private List<NewBuyGoods>AppCartGoodsList;

    /**
     * 代金券
     */
    private List<NewBuyVouchers>appVouchers;

    /**
     * 超市id
     */
    private int marketId;

    /**
     * 超市名
     */
    private String marketName;

    /**
     * 配送费
     */
    private BigDecimal dispatchFee;

    /**
     * 免配送费金额
     */
    private BigDecimal freeDispatchLimit;

    public List<NewBuyGoods> getAppCartGoodsList() {
        return AppCartGoodsList;
    }

    public void setAppCartGoodsList(List<NewBuyGoods> appCartGoodsList) {
        AppCartGoodsList = appCartGoodsList;
    }

    public List<NewBuyVouchers> getAppVouchers() {
        return appVouchers;
    }

    public void setAppVouchers(List<NewBuyVouchers> appVouchers) {
        this.appVouchers = appVouchers;
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
}
