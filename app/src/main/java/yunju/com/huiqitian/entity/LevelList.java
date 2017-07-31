package yunju.com.huiqitian.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/25 0025.
 */
public class LevelList implements Serializable{

    //evalLevelId：级别评级Id（Integer）
    //evalDesc：级别评价描述（String）
    //total：数量（Integer）
    private int total;
    private int evalLevelId;
    private String evalDesc;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getEvalLevelId() {
        return evalLevelId;
    }

    public void setEvalLevelId(int evalLevelId) {
        this.evalLevelId = evalLevelId;
    }

    public String getEvalDesc() {
        return evalDesc;
    }

    public void setEvalDesc(String evalDesc) {
        this.evalDesc = evalDesc;
    }
}
