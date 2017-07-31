package yunju.com.huiqitian.http.entity;

/**
 * 检测版本信息的实体类
 * Created by CCTV-1 on 2017/1/4 0004.
 */
public class VersionCheckReq {
    private Byte type;
    private String version;
    private Byte audience;



    /**
     * state : 1
     * obj : 0
     * msg : 请求成功
     */

    private Integer state;
    private Integer obj;
    private String msg;

    public Byte getAudience() {
        return audience;
    }

    public void setAudience(Byte audience) {
        this.audience = audience;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public Integer getObj() {
        return obj;
    }

    public void setObj(Integer obj) {
        this.obj = obj;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
