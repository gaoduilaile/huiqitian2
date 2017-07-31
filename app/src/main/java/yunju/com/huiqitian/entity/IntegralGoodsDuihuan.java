package yunju.com.huiqitian.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class IntegralGoodsDuihuan {
  /*积分商品实体类  积分兑换*/

    private int state;
    private String msg;

    private List<IntegralGoods2> list;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<IntegralGoods2> getObj() {
        return list;
    }

    public void setObj(List<IntegralGoods2> list) {
        this.list = list;
    }

    public static class IntegralGoods2 {
        private int id;
        private String picStoredName;
        private String picPath;
        private int picId;
        private String picUrl;
        private String stExchangeTime;
        private String stExchangeExpireTime;
        private int motionGoodsId;
        private String motionGoodsName;
        private String marketName;
        private byte exchangeState;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getPicId() {
            return picId;
        }

        public void setPicId(int picId) {
            this.picId = picId;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getStExchangeTime() {
            return stExchangeTime;
        }

        public void setStExchangeTime(String stExchangeTime) {
            this.stExchangeTime = stExchangeTime;
        }

        public String getStExchangeExpireTime() {
            return stExchangeExpireTime;
        }

        public void setStExchangeExpireTime(String stExchangeExpireTime) {
            this.stExchangeExpireTime = stExchangeExpireTime;
        }

        public int getMotionGoodsId() {
            return motionGoodsId;
        }

        public void setMotionGoodsId(int motionGoodsId) {
            this.motionGoodsId = motionGoodsId;
        }

        public String getMotionGoodsName() {
            return motionGoodsName;
        }

        public void setMotionGoodsName(String motionGoodsName) {
            this.motionGoodsName = motionGoodsName;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }

        public byte getExchangeState() {
            return exchangeState;
        }

        public void setExchangeState(byte exchangeState) {
            this.exchangeState = exchangeState;
        }
    }
}
