package yunju.com.huiqitian.http.entity;

/**
 * Created by 张超群 on 2016-08-04.
 *
 * 修改手机号
 */
public class PersonMsgSetMobile {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String pin;

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
