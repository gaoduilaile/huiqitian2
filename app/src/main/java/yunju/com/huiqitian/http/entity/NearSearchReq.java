package yunju.com.huiqitian.http.entity;

/**
 * 附近超市搜索
 * Created by CCTV-1 on 2016/7/25 0025.
 */
public class NearSearchReq {
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

    /**
     * 搜索商品名称
     */
    private String name;

    /**
     * 超市id
     */
    private int marketId;

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
