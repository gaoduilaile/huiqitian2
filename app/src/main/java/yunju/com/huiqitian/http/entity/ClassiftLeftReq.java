package yunju.com.huiqitian.http.entity;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class ClassiftLeftReq {
    /**
     * 分类级别
     */
    private int level;

    private int curpage;

    private int rp;

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
