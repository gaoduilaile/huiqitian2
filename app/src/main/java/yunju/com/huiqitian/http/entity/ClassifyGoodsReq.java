package yunju.com.huiqitian.http.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import yunju.com.huiqitian.base.BaseResp;

/**
 * Created by CCTV-1 on 2016/11/28 0028.
 */
public class ClassifyGoodsReq extends BaseResp implements Serializable{
    private int curpage;
    private int rp;
    private String sortname;
    private String sortorder;
    private int catId;
    /**
     * agentId : 1
     *
     * marketId : 3
     * picStoredName : 201608011120026521870.jpg
     * picId : 4069
     * marketPrice : 4.0
     * leftQty : 1000
     * usedQty : 0
     * marketName : 超市2
     * retailPrice : 4.0
     * picUrl : /data/upload/shop/goods/repository/201608011120026521870.jpg
     * repositoryId : 949
     * memberPrice : 5.0
     * brandName : 乐天
     * dispatchFee : 5.0
     * freeDispatchLimit : 30.0
     * distance : 0.57
     * gCategoryId : 405
     * brandId : 169
     * shelve : 1479870770000
     * score : 0
     * warnQty : null
     * subTitle : null
     * describe : 乐天芒果饮料商品说明
     * limit : -1
     * name : 乐天芒果饮料
     * id : 2
     * type : 0
     */

    private List<ObjBean> obj;

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

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }


    public static class ObjBean  implements Serializable{
        private int agentId;//代理商编号 (Integer)
        private int marketId;//超市编号 (Integer)
        private String picStoredName;//图片存储的名称 (String)
        private int picId;
        private BigDecimal marketPrice;
        private int leftQty;//剩余数量 (Integer)
        private int usedQty;//销售数量 (Integer)
        private String marketName;//超市名称(String)



        private BigDecimal retailPrice;//零售价 (BigDecimal)
        private String picUrl;//图片全路径 (String)
        private int repositoryId;//商品库商品编号 (Integer)
        private BigDecimal memberPrice;//会员价 (BigDecimal)
        private String brandName;//品牌名称 (String)
        private double dispatchFee;
        private BigDecimal freeDispatchLimit;
        private double distance;//距离(double)
        private int gCategoryId;//商品分类编号 (Integer)
        private int brandId;//商品品牌编号 (Integer)
        private long shelve;//上架时间 (Date)
        private int score;//口碑分数 (Integer)
        private Object warnQty;
        private Object subTitle;//商品副标题 (String)
        private String describe;//商品描述  (String)
        private int limit;// -1标识不限制 (Integer)
        private String name;//商品名 (String)
        private int id;//商品编号(Integer)

        public BigDecimal getFreeDispatchLimit() {
            return freeDispatchLimit;
        }

        public void setFreeDispatchLimit(BigDecimal freeDispatchLimit) {
            this.freeDispatchLimit = freeDispatchLimit;
        }
        public BigDecimal getRetailPrice() {
            return retailPrice;
        }

        public BigDecimal getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(BigDecimal marketPrice) {
            this.marketPrice = marketPrice;
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

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }

        private Byte type;//商品类型(Byte)

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getMarketId() {
            return marketId;
        }

        public void setMarketId(int marketId) {
            this.marketId = marketId;
        }

        public String getPicStoredName() {
            return picStoredName;
        }

        public void setPicStoredName(String picStoredName) {
            this.picStoredName = picStoredName;
        }

        public int getPicId() {
            return picId;
        }

        public void setPicId(int picId) {
            this.picId = picId;
        }



        public int getLeftQty() {
            return leftQty;
        }

        public void setLeftQty(int leftQty) {
            this.leftQty = leftQty;
        }

        public int getUsedQty() {
            return usedQty;
        }

        public void setUsedQty(int usedQty) {
            this.usedQty = usedQty;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }



        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getRepositoryId() {
            return repositoryId;
        }

        public void setRepositoryId(int repositoryId) {
            this.repositoryId = repositoryId;
        }



        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public double getDispatchFee() {
            return dispatchFee;
        }

        public void setDispatchFee(double dispatchFee) {
            this.dispatchFee = dispatchFee;
        }



        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getGCategoryId() {
            return gCategoryId;
        }

        public void setGCategoryId(int gCategoryId) {
            this.gCategoryId = gCategoryId;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public long getShelve() {
            return shelve;
        }

        public void setShelve(long shelve) {
            this.shelve = shelve;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Object getWarnQty() {
            return warnQty;
        }

        public void setWarnQty(Object warnQty) {
            this.warnQty = warnQty;
        }

        public Object getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(Object subTitle) {
            this.subTitle = subTitle;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


    }
}
