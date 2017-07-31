package yunju.com.huiqitian.http.entity;

/**
 * Created by gao on 2016/7/15 0015.
 */
public class RegisterReq {
    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 验证码
     */
    private String pin;

    /**
     * 密码
     */
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
