package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/25 0025.
 */
public class EvalList implements Serializable{
    /**
     * tag编号
     */
    private int id;

    /**
     * 是否匿名
     */
    private byte cryptonym;

    /**
     * 用户昵称
     */
    private String evalUser;

    /**
     * 评价内容
     */
    private String evalText;

    /**
     * 用户头像
     */
    private String avatarPicUrl;

    /**
     * 级别评价编号
     */

    private int evalLevelId;

    /**
     * 级别评价描述
     */
    private String evalLevelDesc;

    /**
     * 评价时间
     */
    private String stCreateTime;

    /**
     * 评价图片
     */
    private List<AppPic> pics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getCryptonym() {
        return cryptonym;
    }

    public void setCryptonym(byte cryptonym) {
        this.cryptonym = cryptonym;
    }

    public String getEvalText() {
        return evalText;
    }

    public void setEvalText(String evalText) {
        this.evalText = evalText;
    }

    public List<AppPic> getPics() {
        return pics;
    }

    public void setPics(List<AppPic> pics) {
        this.pics = pics;
    }

    public String getEvalUser() {
        return evalUser;
    }

    public void setEvalUser(String evalUser) {
        this.evalUser = evalUser;
    }

    public String getAvatarPicUrl() {
        return avatarPicUrl;
    }

    public void setAvatarPicUrl(String avatarPicUrl) {
        this.avatarPicUrl = avatarPicUrl;
    }

    public int getEvalLevelId() {
        return evalLevelId;
    }

    public void setEvalLevelId(int evalLevelId) {
        this.evalLevelId = evalLevelId;
    }

    public String getEvalLevelDesc() {
        return evalLevelDesc;
    }

    public void setEvalLevelDesc(String evalLevelDesc) {
        this.evalLevelDesc = evalLevelDesc;
    }

    public String getStCreateTime() {
        return stCreateTime;
    }

    public void setStCreateTime(String stCreateTime) {
        this.stCreateTime = stCreateTime;
    }
}
