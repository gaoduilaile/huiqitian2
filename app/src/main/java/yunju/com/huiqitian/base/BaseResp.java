package yunju.com.huiqitian.base;

/**
 * Created by gao on 2016/7/26 0026.
 */
public class BaseResp {
    /**
     * 错误ID
     */
    private byte state;
    /**
     * 错误信息
     */
    private String msg;

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
