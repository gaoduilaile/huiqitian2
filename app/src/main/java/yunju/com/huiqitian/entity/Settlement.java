package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 胡月 on 2016/8/24 0024.
 */
public class Settlement implements Serializable{
    /**
     * 订单列表抵用券集合
     */
    private List<AffirmOrderMarket>marketCartGoodsList;

    /**
     * 收货地址
     */
    private List<ReceiveAddress>appAddress;

    public List<AffirmOrderMarket> getMarketCartGoodsList() {
        return marketCartGoodsList;
    }

    public void setMarketCartGoodsList(List<AffirmOrderMarket> marketCartGoodsList) {
        this.marketCartGoodsList = marketCartGoodsList;
    }

    public List<ReceiveAddress> getAppAddress() {
        return appAddress;
    }

    public void setAppAddress(List<ReceiveAddress> appAddress) {
        this.appAddress = appAddress;
    }
}
