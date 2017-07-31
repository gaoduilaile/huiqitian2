package yunju.com.huiqitian.entity;

import java.util.List;

/**
 * Created by胡月 on 2016/8/17 0017.
 */
public class SinIn {

    List<SignInData>settings;

    /**
     * 当前签到或未签到
     */
    private Integer currId;

    /**
     * 用户剩余金币
     */
    private Integer leftCoins;

    /**
     * 标志是否签到
     */
    private Integer isSigin;

    /**
     * 签到总天数
     */
    private Integer seriesDay;

    public List<SignInData> getSettings() {
        return settings;
    }

    public void setSettings(List<SignInData> settings) {
        this.settings = settings;
    }

    public Integer getCurrId() {
        return currId;
    }

    public void setCurrId(Integer currId) {
        this.currId = currId;
    }

    public Integer getLeftCoins() {
        return leftCoins;
    }

    public void setLeftCoins(Integer leftCoins) {
        this.leftCoins = leftCoins;
    }

    public Integer getIsSigin() {
        return isSigin;
    }

    public void setIsSigin(Integer isSigin) {
        this.isSigin = isSigin;
    }

    public Integer getSeriesDay() {
        return seriesDay;
    }

    public void setSeriesDay(Integer seriesDay) {
        this.seriesDay = seriesDay;
    }
}
