package yunju.com.huiqitian.http.entity;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class LoginReq {
    /**
     * username
     */
    private String user_name;

    /**
     * password
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
