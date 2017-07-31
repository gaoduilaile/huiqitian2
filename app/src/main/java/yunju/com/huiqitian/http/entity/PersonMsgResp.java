package yunju.com.huiqitian.http.entity;

import java.util.Date;

/**
 * Created by 张超群 on 2016-08-03.
 *
 * 个人信息
 */
public class PersonMsgResp{

    /**
     * 用户ID
     */
    private int id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户真实姓名
     */
    private String  realName;

    /**
     * 用户性别
     */
    private Byte sex;

    /**
     * 用户生日
     */
    private Date birthday;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 图片路径

     */
    private String picUrl;

    /**
     * 路径
     */
    private String path;

    /**
     * 文件名字
     */
    private String storedName;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }




}
