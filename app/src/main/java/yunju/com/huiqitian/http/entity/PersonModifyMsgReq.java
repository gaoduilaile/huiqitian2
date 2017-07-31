package yunju.com.huiqitian.http.entity;

/**
 * Created by 张超群 on 2016-08-04.
 *
 * 个人信息修改请求
 */
public class PersonModifyMsgReq {

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private Byte sex;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }


}
