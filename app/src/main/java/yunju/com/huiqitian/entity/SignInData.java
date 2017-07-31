package yunju.com.huiqitian.entity;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class SignInData {

    /**
     * 设置id
     */
    private Integer id;

    /**
     * 签到组settings表类别
     */
    private String groupId;

    /**
     * 签到关键字
     */
    private String settingsKey;

    /**
     * 签到显示名称
     */
    private String settingsName;

    /**
     * 签到获得金币
     */
    private String settingsValue;

    /**
     * 签到顺序
     */
    private Byte seq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSettingsKey() {
        return settingsKey;
    }

    public void setSettingsKey(String settingsKey) {
        this.settingsKey = settingsKey;
    }

    public String getSettingsName() {
        return settingsName;
    }

    public void setSettingsName(String settingsName) {
        this.settingsName = settingsName;
    }

    public String getSettingsValue() {
        return settingsValue;
    }

    public void setSettingsValue(String settingsValue) {
        this.settingsValue = settingsValue;
    }

    public Byte getSeq() {
        return seq;
    }

    public void setSeq(Byte seq) {
        this.seq = seq;
    }
}
