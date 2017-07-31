package yunju.com.huiqitian.entity;

/**
 * Created by 张超群 on 2016-08-13.
 * <p/>
 * 评价等级
 */
public class EvalLevel {

    /**
     * 设置id
     */
    private int id;

    /**
     * 设置组别
     */
    private String groupId;

    /**
     * 设置关键字
     */
    private String settingsKey;

    /**
     * 设置名称
     */
    private String settingsName;

    /**
     * 设置值
     */
    private String settingsValue;

    /**
     * 是否使用
     */
    private Byte inUse;

    /**
     * 排序
     */
    private Byte seq;

    public Byte getSeq() {
        return seq;
    }

    public void setSeq(Byte seq) {
        this.seq = seq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Byte getInUse() {
        return inUse;
    }

    public void setInUse(Byte inUse) {
        this.inUse = inUse;
    }

}
