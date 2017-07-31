package yunju.com.huiqitian.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class OrderMessageInfo implements Serializable{

    /**
     * 消息
     */
    private String msg;

    /**
     * 类型
     */
    private Byte type;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String meg) {
        this.msg = meg;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
