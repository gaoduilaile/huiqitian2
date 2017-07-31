package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 胡月 on 2016/8/18 0018.
 */
public class GoldDetail implements Serializable{
    /**
     * 签到记录编号
     */
    private Integer id;

    /**
     * 会员编号
     */
    private Integer memberId;

    /**
     * 签到时间
     */
    private Date signinTime;

    /**
     * 签到地址
     */
    private String signinAddress;

    /**
     * 地址经度
     */
    private BigDecimal lbslng;

    /**
     * 地址纬度
     */
    private BigDecimal lbslat;

    /**
     * 获得金币
     */
    private Integer coins;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Date getSigninTime() {
        return signinTime;
    }

    public void setSigninTime(Date signinTime) {
        this.signinTime = signinTime;
    }

    public String getSigninAddress() {
        return signinAddress;
    }

    public void setSigninAddress(String signinAddress) {
        this.signinAddress = signinAddress;
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

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }
}
