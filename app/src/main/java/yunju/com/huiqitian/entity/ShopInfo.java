package yunju.com.huiqitian.entity;

import java.math.BigDecimal;

/**
 * Created by gao on 2016/8/11 0011.
 */
public class ShopInfo {
    /**
     * 超市编号
     */
    private int id;

    /**
     * 超市名
     */
    private String name;

    /**
     * 经营范围编号
     */
    private int bScopeId;

    /**
     * 超市负责人
     */
    private String principalName;

    /**
     * 负责人手机
     */
    private String principalMobile;

    /**
     * 负责人电话
     */
    private String principalTel;

    /**
     * 超市地区编号
     */
    private int areaId;

    /**
     * 超市员城市编号
     */
    private int cityId;

    /**
     * 超市省份编号
     */
    private int provinceId;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 超市地址
     */
    private String address;

    /**
     * 地址经度
     */
    private BigDecimal lbslng;

    /**
     * 地址纬度
     */
    private BigDecimal lbslat;

    /**
     * 最大送货范围
     */
    private int maxrange;

    /**
     * 配送费
     */
    private BigDecimal dispatchFee;

    /**
     * 免配送费金额
     */
    private BigDecimal freeDispatchLimit;

    /**
     * 口碑分数
     */
    private int score;

    /**
     * 超市距离
     */
    private double distance;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * 超市图片
     */
    private String picUrl;



    /**
     * 五星级评分
     */
    private BigDecimal scoreStar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getbScopeId() {
        return bScopeId;
    }

    public void setbScopeId(int bScopeId) {
        this.bScopeId = bScopeId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalMobile() {
        return principalMobile;
    }

    public void setPrincipalMobile(String principalMobile) {
        this.principalMobile = principalMobile;
    }

    public String getPrincipalTel() {
        return principalTel;
    }

    public void setPrincipalTel(String principalTel) {
        this.principalTel = principalTel;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLbslng() {
        return lbslng;
    }

    public void setLbslng(BigDecimal lbslng) {
        this.lbslng = lbslng;
    }

    public BigDecimal getLbslat() {
        return lbslat;
    }

    public void setLbslat(BigDecimal lbslat) {
        this.lbslat = lbslat;
    }

    public int getMaxrange() {
        return maxrange;
    }

    public void setMaxrange(int maxrange) {
        this.maxrange = maxrange;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    public BigDecimal getScoreStar() {
        return scoreStar;
    }

    public void setScoreStar(BigDecimal scoreStar) {
        this.scoreStar = scoreStar;
    }


}
