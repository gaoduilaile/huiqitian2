package yunju.com.huiqitian.http;

/**
 * http常量
 */
public class HttpConstant {
    //public static final String ROOT_PATH = "http://121.199.18.39";
    public static final String ROOT_PATH = "http://121.199.18.39:8089/com.yunju.huiqitian";
    //public static final String ROOT_PATH = "http://192.168.1.104:8080/com.yunju.huiqitian";
    private static final String END_PATH = ".do";
    //  微信支付接口
    public static final String PATH_WEI_XIN = ROOT_PATH + "/app/buyer/order/wechatpay" + END_PATH;

    //检测app版本信息
    public static final String PATH_CHECK_VERSION = ROOT_PATH + "/app/version/check" + END_PATH;
    //登陆
    public static final String PATH_LOGIN = ROOT_PATH + "/app/buyer/login" + END_PATH;

    //退出账号

    public static final String PATH_LOGIN_OUT = ROOT_PATH + "/app/logout" + END_PATH;

    //注册
    public static final String PATH_REGISTER = ROOT_PATH + "/app/buyer/reg" + END_PATH;

    //验证手机是否已经被注册
    public static final String PATH_CHICK_MOBILE = ROOT_PATH + "/app/buyer/ckmobile" + END_PATH;

    //验证手机验证码
    public static final String PATH_PIN_VERIFY = ROOT_PATH + "/app/pin/verify" + END_PATH;

    //获取手机验证码
    public static final String PATH_GET_PHONE_CODE = ROOT_PATH + "/app/pin/gen" + END_PATH;

    //密码重置
    public static final String PATH_BUYER_RESET_PW = ROOT_PATH + "/app/buyer/resetpw" + END_PATH;

    //定位地址的接口
    public static final String PATH_BUYER_LOCATE = ROOT_PATH + "/app/buyer/locate" + END_PATH;

    //搜索接口
    public static final String PATH_GOOD_SEARCH = ROOT_PATH + "/app/buyer/good/search" + END_PATH;

    //分类接口
    // (通过level检索商品分类)
    public static final String PATH_CLASSIFY_LEVEL = ROOT_PATH + "/app/buyer/category/get" + END_PATH;

    /*分类接口*/
    /*左边的父控件接口*/
    public static final String PATH_CLASSIFY_FU = ROOT_PATH + "/app/buyer/category/list" + END_PATH;

    //(获取商品分类的子分类)
    public static final String PATH_CLASSIFY_PARENT_ID = ROOT_PATH + "/app/buyer/category/childs" + END_PATH;

    //商品属性
    public static final String PATH_GOOD_PROP = ROOT_PATH + "/app/buyer/good/prop" + END_PATH;

    //加入购物车
    public static final String PATH_CART_ADD = ROOT_PATH + "/app/buyer/cart/add" + END_PATH;

    //查看购物车接口
    public static final String PATH_CART_LIST = ROOT_PATH + "/app/buyer/cart/list" + END_PATH;

    //通过分类和排序获取商品分类
    public static final String PATH_CLASS_SORT = ROOT_PATH + "/app/buyer/good/listbycat" + END_PATH;

    //获取用户收货地址
    public static final String PATH_BUYER_ADDRESS_LIST = ROOT_PATH + "/app/buyer/address/list" + END_PATH;


    //个人信息接口
    public static final String PATH_PERSON_MSG = ROOT_PATH + "/app/buyer/getuser" + END_PATH;

    //修改昵称接口
    public static final String PATH_PERSON_CHANGE_NICKNAME = ROOT_PATH + "/app/buyer/modify" + END_PATH;

    //修改绑定手机号接口
    public static final String PATH_PERSON_SETMOBILE = ROOT_PATH + "/app/buyer/setmobile" + END_PATH;

    //购物车修改产品数量
    public static final String PATH_CART_ALTERQTY = ROOT_PATH + "/app/buyer/cart/alterqty" + END_PATH;

    //购物车删除产品
    public static final String PATH_CART_DEL = ROOT_PATH + "/app/buyer/cart/del" + END_PATH;

    //购物车去结算
    public static final String PATH_CART_ESTIMATE = ROOT_PATH + "/app/buyer/cart/estimate" + END_PATH;

    /*我的钱包*/
    //金币
    public static final String PATH_MY_GETCOIN = ROOT_PATH + "/app/buyer/getcoin" + END_PATH;
    //积分
    public static final String PATH_MY_GETPOINT = ROOT_PATH + "/app/buyer/getpoint" + END_PATH;

    //代金券
    public static final String PATH_MY_VOUCHER = ROOT_PATH + "/app/buyer/voucher/list" + END_PATH;

    /*订单相关*/
    public static final String PATH_ALL_ORDERS = ROOT_PATH + "/app/buyer/order/list" + END_PATH;

    /*评价订单*/
    public static final String PATH_EVALUATION_ORDERS = ROOT_PATH + "/app/buyer/order/eval/init" + END_PATH;

    /*每日精选*/
    public static final String PATH_GOOD_DAILY_PICK = ROOT_PATH + "/app/buyer/good/dailypick" + END_PATH;

    /*附近商铺*/
    public static final String PATH_BUYER_MARKER_LIST = ROOT_PATH + "/app/buyer/market/list" + END_PATH;
    /*提交评价订单*/
    public static final String PATH_COMMIT_EVALUATION_ORDERS = ROOT_PATH + "/app/buyer/order/eval/add" + END_PATH;

    /*查看订单配送信息*/
    public static final String PATH_LOOK_DISTRIBUTION = ROOT_PATH + "/app/buyer/order/viewdispatch" + END_PATH;

    /*订单删除*/
    public static final String PATH_DELETE_ORDER = ROOT_PATH + "/app/buyer/order/del" + END_PATH;
    /*取消订单*/
    public static final String PATH_CANCEL_ORDER = ROOT_PATH + "/app/buyer/order/cancel" + END_PATH;
    /*代金券*/
    public static final String PATH_VOUCHER = ROOT_PATH + "/app/buyer/voucher/list" + END_PATH;

    /*搜索商店热门商品*/
    public static final String PATH_GOOD_HOST = ROOT_PATH + "/app/buyer/good/hot" + END_PATH;

    /*今日上新*/
    public static final String PATH_GOOD_LATEST = ROOT_PATH + "/app/buyer/good/latest" + END_PATH;

    /*为你推荐*/
    public static final String PATH_GOOD_RECOMMEND = ROOT_PATH + "/app/buyer/good/recommend" + END_PATH;

    /*提交订单*/
    public static final String PATH_ORDER_ADD = ROOT_PATH + "/app/buyer/order/add" + END_PATH;

    /*首页进口食品*/
    public static final String PATH_IMPORT_FOOD = ROOT_PATH + "/app/buyer/good/import" + END_PATH;

    /*热卖接口*/
    public static final String PATH_HOT_GOODS = ROOT_PATH + "/app/buyer/good/hot" + END_PATH;

    /*收货地址*/
    public static final String PATH_RECEIVER_ADDRESS = ROOT_PATH + "/app/buyer/address/list" + END_PATH;

    /*设置默认收货地址*/
    public static final String PATH_SETDEFAULT_ADDRESS = ROOT_PATH + "/app/buyer/address/setdefault" + END_PATH;

    /*删除收货地址*/
    public static final String PATH_DELETE_ADDRESS = ROOT_PATH + "/app/buyer/address/del" + END_PATH;

    /*添加收货地址*/
    public static final String PATH_ADD_ADDRESS = ROOT_PATH + "/app/buyer/address/add" + END_PATH;

    /*意见反馈*/
    public static final String PATH_OPINION_ADD = ROOT_PATH + "/app/buyer/opinion/add" + END_PATH;

    /*支付宝支付接口地址*/
    public static final String PATH_PAY_URL = ROOT_PATH + "/app/buyer/order/alipay.do";

    /*查看所有积分商品*/
    public static final String PATH_POINT_GOOD_LIST = ROOT_PATH + "/app/buyer/pointgood/list" + END_PATH;

    /*订单详情页面*/
    public static final String PATH_ORDER_DETAIL = ROOT_PATH + "/app/buyer/order/view" + END_PATH;

    /*订单详情页面的地址获取*/
    public static final String PATH_ORDER_DETAIL_ADDRESS = ROOT_PATH + "/app/buyer/address/view" + END_PATH;


    /*中奖记录接口*/
    public static final String PATH_GOLD_RECORD = ROOT_PATH + "/app/buyer/lucky/list" + END_PATH;

    /*金币明细*/
    public static final String PATH_GOLD_DETAILS = ROOT_PATH + "/app/buyer/coin/list" + END_PATH;

    /*签到初始化接口*/
    public static final String PATH_SIGN_IN = ROOT_PATH + "/app/buyer/signin/init" + END_PATH;

    /*签到接口*/
    public static final String PATH_GET_SIGN_IN = ROOT_PATH + "/app/buyer/signin/signin" + END_PATH;

    /*接口名称：积分商品兑换记录分页列表*/
    public static final String PATH_EXCHANGE_LIST = ROOT_PATH + "/app/buyer/exchange/list" + END_PATH;

    /*接口名称：积分商品兑换*/
    public static final String PATH_POINT_GOODEXCHANGE_LIST = ROOT_PATH + "/app/buyer/pointgood/exchange" + END_PATH;

    /*立即购买*/
    public static final String PATH_NEW_BUY = ROOT_PATH + "/app/buyer/good/buying" + END_PATH;

    public static final String PATH_HEAD_PIC = ROOT_PATH + "/app/buyer/modifyPic" + END_PATH;

    /*跟新地址*/
    public static final String PATH_UP_DATE_ADDRESS = ROOT_PATH + "/app/buyer/address/update" + END_PATH;

    /*立即购买提交订单接口*/
    public static final String PATH_NOW_BUY_SUBMITORDER = ROOT_PATH + "/app/buyer/order/buying" + END_PATH;

    /*轮播图*/
    public static final String PATH_CAROUSEL = ROOT_PATH + "/app/buyer/carousel" + END_PATH;

    /*商品的详细评价*/
    public static final String PATH_GOODS_EVAL = ROOT_PATH + "/app/buyer/good/eval/summary" + END_PATH;

    /*获取好评中评差评数据*/
    public static final String PATH_EVAL_LIST = ROOT_PATH + "/app/buyer/good/eval/list" + END_PATH;

    /*绑定cid*/
    public static final String PATH_BIND_CID = ROOT_PATH + "/app/cid/bind" + END_PATH;

    /*确认收货*/
    public static final String PATH_AFFIRM_ORDER = ROOT_PATH + "/app/buyer/order/confirm" + END_PATH;

    /*根基id查询商品接口*/
    public static final String PATH_GOOD_DETAILS = ROOT_PATH + "/app/ buyer/ good/ list" + END_PATH;

}