package yunju.com.huiqitian.http.entity;

/**
 * Created by 高英祥 on 2016/7/15 0015.
 */
public class CheckCodeReq {
    /**
     * 验证码
     */
    private String pin;
    /**
     * 手机号码
     */
    private String mobile;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
