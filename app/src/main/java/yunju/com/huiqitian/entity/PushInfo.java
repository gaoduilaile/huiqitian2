package yunju.com.huiqitian.entity;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class PushInfo {
    /**
     * 消息类型
     */
    private int type;

    /**
     * 消息类容
     */
    private Object obj;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
