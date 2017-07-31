package yunju.com.huiqitian.entity;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class Voucher {
    /**
     * 代金券id
     */
    private int id;

    /**
     * 代金券关联超市id
     */
    private int marketId;

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
