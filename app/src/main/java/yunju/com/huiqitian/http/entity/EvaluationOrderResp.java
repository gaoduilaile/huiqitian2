package yunju.com.huiqitian.http.entity;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.EvaluationOrder;

/**
 * Created by 张超群 on 2016-08-09.
 * <p/>
 * 订单评价响应数据
 */
public class EvaluationOrderResp extends BaseResp {

    public EvaluationOrder getObj() {
        return obj;
    }

    public void setObj(EvaluationOrder obj) {
        this.obj = obj;
    }

    private EvaluationOrder obj;

//    /**
//     * 订单商品集合
//     */
//    private List<OrderGoods> appOrderGoods;
//
//    /**
//     * 评价等级
//     */
//    private List<EvalLevel> evalLevel;
//
//    /**
//     * 云标签
//     */
//    private List<EvalTag> evaltag;
//
//    public List<OrderGoods> getAppOrderGoods() {
//        return appOrderGoods;
//    }
//
//    public void setAppOrderGoods(List<OrderGoods> appOrderGoods) {
//        this.appOrderGoods = appOrderGoods;
//    }
//
//    public List<EvalLevel> getEvalLevel() {
//        return evalLevel;
//    }
//
//    public void setEvalLevel(List<EvalLevel> evalLevel) {
//        this.evalLevel = evalLevel;
//    }
//
//    public List<EvalTag> getEvaltag() {
//        return evaltag;
//    }
//
//    public void setEvaltag(List<EvalTag> evaltag) {
//        this.evaltag = evaltag;
//    }
//
//    /**
//     * 商品实体类
//     */
//    public class OrderGoods{
//
//        /**
//         *订单id
//         */
//        private String id;
//
//        /**
//         *订单商品id
//         */
//        private int orderGoodsId;
//
//        /**
//         *商品id
//         */
//        private int goodsId;
//
//        /**
//         *商品类型（正常商品、每日精选）(Byte)
//         */
//        private Byte type;
//
//        /**
//         *商品名称
//         */
//        private String name;
//
//        /**
//         *图片路径
//         */
//        private String picUrl;
//
//        /**
//         *图片名
//         */
//        private String picStoredName;
//
//        /**
//         *图片路径
//         */
//        private String picPath;
//
//        /**
//         *零售价
//         */
//        private BigDecimal retailPrice;
//
//        public BigDecimal getRetailPrice() {
//            return retailPrice;
//        }
//
//        public void setRetailPrice(BigDecimal retailPrice) {
//            this.retailPrice = retailPrice;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public int getOrderGoodsId() {
//            return orderGoodsId;
//        }
//
//        public void setOrderGoodsId(int orderGoodsId) {
//            this.orderGoodsId = orderGoodsId;
//        }
//
//        public int getGoodsId() {
//            return goodsId;
//        }
//
//        public void setGoodsId(int goodsId) {
//            this.goodsId = goodsId;
//        }
//
//        public Byte getType() {
//            return type;
//        }
//
//        public void setType(Byte type) {
//            this.type = type;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getPicUrl() {
//            return picUrl;
//        }
//
//        public void setPicUrl(String picUrl) {
//            this.picUrl = picUrl;
//        }
//
//        public String getPicStoredName() {
//            return picStoredName;
//        }
//
//        public void setPicStoredName(String picStoredName) {
//            this.picStoredName = picStoredName;
//        }
//
//        public String getPicPath() {
//            return picPath;
//        }
//
//        public void setPicPath(String picPath) {
//            this.picPath = picPath;
//        }
//
//
//    }
//
//    /**
//     * 评价等级
//     */
//    public class EvalLevel{
//
//        /**
//         * 设置id
//         */
//        private int  id;
//
//        /**
//         * 设置组别
//         */
//        private String groupId;
//
//        /**
//         * 设置关键字
//         */
//        private String settingsKey;
//
//        /**
//         * 设置名称
//         */
//        private String settingsName;
//
//        /**
//         * 设置值
//         */
//        private String settingsValue;
//
//        /**
//         * 是否使用
//         */
//        private Byte inUse;
//
//        /**
//         * 排序
//
//         */
//        private Byte seq;
//
//        public Byte getSeq() {
//            return seq;
//        }
//
//        public void setSeq(Byte seq) {
//            this.seq = seq;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getGroupId() {
//            return groupId;
//        }
//
//        public void setGroupId(String groupId) {
//            this.groupId = groupId;
//        }
//
//        public String getSettingsKey() {
//            return settingsKey;
//        }
//
//        public void setSettingsKey(String settingsKey) {
//            this.settingsKey = settingsKey;
//        }
//
//        public String getSettingsName() {
//            return settingsName;
//        }
//
//        public void setSettingsName(String settingsName) {
//            this.settingsName = settingsName;
//        }
//
//        public String getSettingsValue() {
//            return settingsValue;
//        }
//
//        public void setSettingsValue(String settingsValue) {
//            this.settingsValue = settingsValue;
//        }
//
//        public Byte getInUse() {
//            return inUse;
//        }
//
//        public void setInUse(Byte inUse) {
//            this.inUse = inUse;
//        }
//
//    }
//
//    /**
//     * 云标签
//     */
//    public class EvalTag{
//
//        /**
//         * 标签编号
//         */
//        private int id;
//
//        /**
//         * 标签名称
//         */
//        private String tag;
//
//        /**
//         * 标签描述
//         */
//        private String tagDesc;
//
//        /**
//         * 标签类型
//         */
//        private Byte tagType;
//
//        /**
//         * 排序号
//         */
//        private int sort;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getTag() {
//            return tag;
//        }
//
//        public void setTag(String tag) {
//            this.tag = tag;
//        }
//
//        public String getTagDesc() {
//            return tagDesc;
//        }
//
//        public void setTagDesc(String tagDesc) {
//            this.tagDesc = tagDesc;
//        }
//
//        public Byte getTagType() {
//            return tagType;
//        }
//
//        public void setTagType(Byte tagType) {
//            this.tagType = tagType;
//        }
//
//        public int getSort() {
//            return sort;
//        }
//
//        public void setSort(int sort) {
//            this.sort = sort;
//        }
//
//
//    }
}
