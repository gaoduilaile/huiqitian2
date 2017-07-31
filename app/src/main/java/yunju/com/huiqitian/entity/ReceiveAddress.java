package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by liuGang on 2016/8/12 0012.
 */
public class ReceiveAddress implements Serializable{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSpareTel() {
        return spareTel;
    }

    public void setSpareTel(String spareTel) {
        this.spareTel = spareTel;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
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

    public Byte getUseable() {
        return useable;
    }

    public void setUseable(Byte useable) {
        this.useable = useable;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    /**
     * 收货信息编号
     */
    private int id;

    /**
     * 会员编号
     */
    private int memberId;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 收货人性别
     */
    private Byte sex;

    /**
     * 地区编号
     */
    private int areaId;

    /**
     * 城市编号
     */
    private int cityId;

    /**
     * 省份编号
     */
    private int provinceId;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 备用联系电话
     */
    private String spareTel;

    /**
     * 邮编
     */
    private String postCode;

    /**
     *具体地址
     */
    private String addressInfo;

    /**
     * 是否默认
     */
    private Byte isDefault;

    /**
     * 地址经度
     */
    private BigDecimal lbslng;

    /**
     * 地址纬度
     */
    private BigDecimal lbslat;

    /**
     * 是否可用
     */
    private Byte useable;

    /**
     * 详细地址
     */
    private String detailAddr;


}
