package yunju.com.huiqitian.entity;

/**
 * Created by liuGang on 2016/8/16 0016.
 */
public class IntegralGoods {
    /*积分商品实体类*/


    /**
     * 编号
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 消耗金币(Integer)
     */
    private Integer consumption;
    /**
     * 商品图片的ID(Integer)
     */
    private Integer picId;
    /**
     * 文件路径(String)
     */
    private String picPath;
    /**
     * 重命名后的文件名(String)
     */
    private String picStoredName;
    /**
     * 商品图片的url(String)
     */
    private String picUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicStoredName() {
        return picStoredName;
    }

    public void setPicStoredName(String picStoredName) {
        this.picStoredName = picStoredName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


}
