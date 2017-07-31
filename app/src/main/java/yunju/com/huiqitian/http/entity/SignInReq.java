package yunju.com.huiqitian.http.entity;

/**
 * Created by 胡月on 2016/8/17 0017.
 */
public class SignInReq {
    /**
     * 签到地址
     */
    private String signinAddress;

    /**
     * 签到精度
     */
    private double lbslng;

    /**
     * 签到维度
     */
    private double lbslat;

    /**
     * 签到当前的settingId
     */
    private int currId;

    public String getSigninAddress() {
        return signinAddress;
    }

    public void setSigninAddress(String signinAddress) {
        this.signinAddress = signinAddress;
    }

    public double getLbslng() {
        return lbslng;
    }

    public void setLbslng(double lbslng) {
        this.lbslng = lbslng;
    }

    public double getLbslat() {
        return lbslat;
    }

    public void setLbslat(double lbslat) {
        this.lbslat = lbslat;
    }

    public int getCurrId() {
        return currId;
    }

    public void setCurrId(int currId) {
        this.currId = currId;
    }
}
