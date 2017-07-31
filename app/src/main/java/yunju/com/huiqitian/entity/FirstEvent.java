package yunju.com.huiqitian.entity;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class FirstEvent {

    //mMsg为传过来的数据 也可以是一个对象
    //private String mMsg;
    private Map<String,Object> list;

    public FirstEvent(Map list) {

        this.list = list;
    }

    public Map getMsg() {
        return list;
    }
}
