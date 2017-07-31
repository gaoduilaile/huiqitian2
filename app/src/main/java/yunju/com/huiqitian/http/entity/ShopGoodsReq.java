package yunju.com.huiqitian.http.entity;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class ShopGoodsReq extends SearchReq {
    /**
     * 超市的id
     */
    private int marketId;//超市id

    /**
     * 当前页数
     */
    private int curpage;

    /**
     * 每页多少条
     */
    private int rp;

    /**
     * 排序字段
     销量："qty"
     口碑："score"
     价格："price"
     距离："distance"
     */
    private String sortname;

    /**
     * 排序方式
     升序："asc"
     倒序："desc"
     */
    private String sortorder;

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    @Override
    public int getCurpage() {
        return curpage;
    }

    @Override
    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    @Override
    public int getRp() {
        return rp;
    }

    @Override
    public void setRp(int rp) {
        this.rp = rp;
    }

    @Override
    public String getSortname() {
        return sortname;
    }

    @Override
    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    @Override
    public String getSortorder() {
        return sortorder;
    }

    @Override
    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }
}
