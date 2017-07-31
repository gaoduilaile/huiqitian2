package yunju.com.huiqitian.http.entity;

import java.sql.Timestamp;

/**
 * Created by 胡月 on 2016/8/16 0016.
 */
public class GoldRecord {



    /**
     * 编号
     */
    private Integer id;

    /**
     * 动作类型
     */
    private byte motionType;

    /**
     * 动作编号
     */
    private Integer motionId;

    /**
     * 会员编号
     */
    private Integer memberId;

    /**
     * 会员名称
     */
    private String nickName;

    /**
     * 商品编号
     */
    private Integer shakeGoodId;

    /**
     * 商品名称
     */
    private String shakeGoodName;

    /**
     * 中奖时间
     */
    private Timestamp luckyTime;

    /**
     * 兑换截止时间
     */
    private Timestamp exchangeExpireTime;

    /**
     * 兑换状态
     */
    private byte exchangeState;

    /**
     * 兑换时间
     */
    private Timestamp exchangeTime;

    /**
     * 兑换编号
     */
    private String exchangeId;

    /**
     * 图片编号
     */
    private Integer picId;

    /**
     * 图片存储的名称
     */
    private String picStoredName;

    /**
     * 图片存储路径
     */
    private String picPath;

    /**
     * 图片全路径
     */
    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicStoredName() {
        return picStoredName;
    }

    public void setPicStoredName(String picStoredName) {
        this.picStoredName = picStoredName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte getMotionType() {
        return motionType;
    }

    public void setMotionType(byte motionType) {
        this.motionType = motionType;
    }

    public Integer getMotionId() {
        return motionId;
    }

    public void setMotionId(Integer motionId) {
        this.motionId = motionId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getShakeGoodId() {
        return shakeGoodId;
    }

    public void setShakeGoodId(Integer shakeGoodId) {
        this.shakeGoodId = shakeGoodId;
    }

    public String getShakeGoodName() {
        return shakeGoodName;
    }

    public void setShakeGoodName(String shakeGoodName) {
        this.shakeGoodName = shakeGoodName;
    }

    public Timestamp getLuckyTime() {
        return luckyTime;
    }

    public void setLuckyTime(Timestamp luckyTime) {
        this.luckyTime = luckyTime;
    }

    public Timestamp getExchangeExpireTime() {
        return exchangeExpireTime;
    }

    public void setExchangeExpireTime(Timestamp exchangeExpireTime) {
        this.exchangeExpireTime = exchangeExpireTime;
    }

    public byte getExchangeState() {
        return exchangeState;
    }

    public void setExchangeState(byte exchangeState) {
        this.exchangeState = exchangeState;
    }

    public Timestamp getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Timestamp exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }
}
