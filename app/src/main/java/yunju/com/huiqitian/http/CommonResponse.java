package yunju.com.huiqitian.http;

/**
 * CommonResponse
 */
public class CommonResponse {
    /**
     * 错误ID
     */
    private byte state;
    /**
     * 错误信息
     */
    private String msg;

    private String obj;

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

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
