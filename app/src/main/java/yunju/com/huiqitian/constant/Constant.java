package yunju.com.huiqitian.constant;

/**
 * Created by 高英祥 on 2016/7/11 0011.
 */
public class Constant {
    /**
     * 支付之后保存的id
     */
    public static final String PAY_ID = "pay_id";
    /**
     * 下单的留言
     */
    public static final String MSG_BODY = "msg_body";
    /**
     * 应用SharedPreference 名称
     */
    public static final String SHARED_PREFERENCE_NAME = "hui_Qi_Tian_shared_preference";

    /**
     * 微信支付的app id
     *
     */
    public static final String WECHAT_APPID="wx432ceec6c2fa0341";

    /**
     * app版本信息相关
     */
    public static final Byte VERSION_TYPE_ANDROID=1;
    public static final Byte VERSION_TYPE_ANDROID_SELLER=2;
    public static final Byte VERSION_TYPE_IOS=3;
    public static final Byte VERSION_TYPE_IOS_SELLER=4;
    /*数据相关*/
    //用户名（电话）
    public static final String SHARED_USER_CELL_PHONE = "shared_user_cell_phone";

    //密码
    public static final String SHARED_USER_PASSWORD = "shared_user_password";

    //cid
    public static final String SHARED_USER_CID="shared_user_cid";
    
    //经纬度地址城市
    public static final String SHARED_USER_LAG="shared_user_lag";
    public static final String SHARED_USER_LOG="shared_user_log";
    public static final String SHARED_USER_ADDRESS="shared_user_address";
    public static final String SHARED_USER_CITY="shared_user_city";

    //是否保存经纬度的状态
    public static final String SHARED_USER_LOG_TYPE="type";
    
    //能否接受消息推送的状态
    public static final String SHARED_USER_PUSH="push_type";

    //消息通知是否有声音
    public static final String SHARED_USER_PUSH_SOUND="push_sound";

    //立即购买代金券id
    public static final String SHARED_VOUCHER_ID="voucherId";

    //yes
    public static final String YES="yes";

    //no
    public static final String NO="no";

    //第一次使用
    public static final String SHARED_FIRST_USE = "first_use";

    //开始的类型
    public static final int WELCOME_TYPE_START = 1;

    /*广播相关*/
    //打开和关闭广播的标记
    public static final int OPEN = 0;
    public static final int CLOSE = 1;

    /*请求成功和失败*/
    public static final int LOGIN_SUCCESS = 0;//登陆失败
    public static final int LOGIN_FAILURE = 1;//登陆成功
    public static final int LOGIN_NONE = 2;//没有登录
    public static final int LOGIN_THREE=3;//附近没有超市
    public static final int LOACATION_ERROR=5;


    /*区分是搜索界面还是主界面的跳转详情页*/
    public static final int START_BY_FIND = 0;//代表的是从搜索界面的跳转
    public static final int START_BY_HOME = 1;//代表的是从主界面跳转
    public static final int START_BY_HISTORY = 2;//历史记录跳转来的
    public static final int START_BY_COLLECT = 4;//收藏页面跳转过来
    public static final int START_BY_SHOP_CART = 5;//购物车页面跳转
    public static final int START_BY_TUFF = 3;//进口食物跳转来的
    public static final int START_BY_ORDER=6;//确认订单页面
    public static final int START_BY_CLASSIFY=7;//代表着是从分类条目中跳转过来的
    public static final int START_BY_IMPORT=8;//代表着是从进口条目中跳转过来的

    //区分是从哪个界面跳转的key
    public static final String START_NEXT_KEY = "next_key";
    public static final String START_NEXT_INTEGER = "next_int";
    public static final String START_NEXT_REQ = "next_req";

    //设置从service发送出去的action
    public static final String MY_BROADCAST_ACTION_HOME = "location_service_home";//发送到主界面
    public static final String MY_BROADCAST_ACTION="location_service";//发送到选择位置和收货地址页面


    //设置从广播中获得数据的msg key
    public static final String MY_LOCATION_MSG_KEY = "location";

    //设置控制打开关闭定位的action
    public static final String MY_CONTROL_ACTION = "action_control";
    public static final String MY_CONTROL_ACTION_OPEN = "action_control_OPEN";

    //设置从广播中获得打开和关闭广播的消息
    public static final String MY_CONTROL_MSG_KEY = "control_msg";

    //设置接受从广播中发送来的街道信息
    public static final String MY_LOCATION_MSG_STREET = "location_street";

    //区分广播类型set
    public static final String MY_LOCATION_TYPE = "service";//代表的是从定位服务传过来的
    public static final String MY_LOCATION_TYPE_FOR_ADDRESS = "address";//代表的是从地址选择来的
    public static final String LOCATION_TYPE="type";//表示从哪个页面发送广播定位

    /*消息推送*/
    public static final String MY_MESSAGE_FROM_PUS = "push_message";  //从消息推送过来的
    public static final String MY_MESSAGE_FROM_TYPE= "push_TYPE";  //从消息推送的是什么类型的消息

    //经度
    public static final String MY_LATITUDE = "latitude";

    //维度
    public static final String MY_Longitude = "longitude";

    //城市
    public static final String MY_CITY="city";

    //搜索商品的商品信息
    public static final String GOODS_INFO = "goods_info";

    //商品属性信息
    public static final String GOODS_PROP = "goods_prop";
    //商品所属超市信息
    public static final String GOODS_MARKET = "goods_market";

    //搜索的商品名称
    public static final String GOODS_NAME = "goods_name";

    //传递点击的item的位置
    public static final String ITEM_POSITION = "item_position";
    public static final String POSITION = "position";

    /*下级标题*/
    public static final String NEXT_TITLE = "设置密码";
    public static final String NEXT_TITLE_FOR_RESET = "密码重置";

    /*sharedpreference保存部分个人信息*/
    //用户名
    public static final String SHARED_PERSON_NAME = "person_name";
    //昵称
    public static final String SHARED_PERSON_NICKNAME = "person_nickname";
    //性别
    public static final String SHARED_PERSON_SEX = "person_name_sex";
    //电话
    public static final String SHARED_PERSON_PHONE = "person_phone";
    //图片地址
    public static final String SHARED_PERSON_PICURL = "person_picUrl";

    /*用户修改跳转常量*/
    public static final String MODIFY_KEY = "modify";
    //修改昵称
    public static final int MODIFY_NICKNAME = 1;
    //修改账号
    public static final int MODIFY_ACCOUNT = 2;
    //修改绑定手机号
    public static final int MODIFY_BIND_PHONE = 3;

    /*性别显示*/
    //男
    public static final int SEX_MAN = 1;
    //女
    public static final int SEX_WOMAN = 2;
    //保密
    public static final int SEX_SECRET = 0;

    /*获取用户代金券状态*/
    //未使用代金券
    public static final int UNUSE_VOUCHER = 1;
    //已使用代金券
    public static final int USE_VOUCHER = 2;
    //已过期代金券
    public static final int OVERDUE_VOUCHER = 3;

    /*订单页面*/
    //传值的标志
    public static final String ORDERS_MARKS = "orders";
    //全部订单
    public static final int ALL_ORDERS = 1;
    //待付款
    public static final int WAITE_PAY = 2;
    //待发货
    public static final int WAITE_SEND = 3;
    //待收货
    public static final int WAITE_GET = 4;
    //待评价
    public static final int WAITE_EVALUATE = 5;

    /*订单状态*/
    public static final String NO_PAY = "未付款";//未付款
    public static final String PAY = "已付款";//已付款
    public static final String WAITE_SEND_GOOD = "待发货";//待发货
    public static final String WAITE_GET_GOOD = "待收货";//待收货
    public static final String HAS_COMPLET = "已完成";//已完成
    public static final String HAS_CANCLE = "已取消";//已取消
    public static final String LOOK_DISTRIBUTIONS = "配送查看";
    public static final String COUNTERSIGN = "确认收货";

    /*订单的操作相关*/
    public static final String EVALUATION_ORDER = "evaluation_order_id";//评价订单id
    public static final String ORDER_GOOD_ID = "order_good_id";//商品订单id
    public static final String DELETE_ORDER = "delete_order";//删除订单
    public static final String LOOK_DISTRIBUTION = "look_distribution";//配送查看

    /*相机选择相关*/
    public static final int OPEN_CAMERA_CODE = 10;//拍照
    public static final int OPEN_GALLERY_CODE = 11; //选择

    /*代金券页面*/
    //传值的标志
    public static final String VOUCHER_MARKS = "voucher";

    /*通用的int常量判断*/
    public static final int NEGATIVE_ONE = -1;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN=7;
    public static final int RIGHT=8;//好评---(未完成订单删除)
    public static final int NINE=9;//中评----(已完成订单删除)
    public static final int TEN=10;//差评
    /*从去结算中获取的确认订单数据*/
    //搜索商品的商品信息
    public static final String AFFIRM_INFO = "affirm_info";
    public static final String ADDRESS_INFO = "address_info";

    /*店铺*/
    public static final String SHOP_NAME = "shop_name";
    public static final String SHOP_DISTANCE = "shop_distance";
    public static final String SHOP_SEND_MONEY = "shop_send_money";
    public static final String SHOP_MARKET_ID = "shop_market_id";
    /*订单来源*/
    public static final Byte ORDER_ORIGIN = 1;

    /*消息推送相关的action*/
    public static final String PUSH_ACTION = "push_action";


    /*历史记录*/
    public static final String START_WHERE = "start_where";
    public static final int START_FROM_PERSON = 0;
    public static final int START_FORM_HISTORY = 1;

    public static final String GOODS_LIST_TO_CART = "goods_list_to_cart";
    public static final String GOODS_DETAIL_TO_CART = "goods_detail_to_cart";
    public static final String AFFIRM_ORDER_TO_CART = "affirm_order_to_cart";
    public static final String STUFF_GOODS_TO_CART = "stuff_goods_to_cart";

    public static final String EXIT_LOGIN_TO_PERSONAL = "exit_login_to_personal";
    public static final String LOGIN_TO_HOME="login_to_home";
    public static final String SHOPCART="shop_cart";


    /*确认订单页面*/
    public static final String PAY_PRICE = "price";
    public static final String PAY_ORDERS = "orders";
    public static final String PAY_LENGTH = "length";

    /*兑换记录*/
    public static final Byte NO_EXCHANGE = 1;
    public static final Byte YET_EXCHANGE = 2;
    public static final Byte YET_OVERDUE = 3;

    /*从购物车跳转到登录页面*/
    public static final String FROM_SHOP_CART = "from_shop_cart";

    /*是否默认地址*/
    public static final Byte IS_DEFAULT_ADDRESS = 1;

    /*积分商城*/
    public static final String TO_INTEGRAL_DETAIL = "to_integral_detail";

    public static String MY_TOTAL_STORE="my_total_store";

    /*保存用户的头像path*/
    public static final String PIC_USER_HEAD = "pic_user_hear";

    /*金币明细跳转*/
    public static final String GOLD_DETAILS = "gold_details";

    /*从退出账号界面 跳到登陆界面*/
    public static final int COM_FROM_EXIT = 110;

    /*收货地址*/
    public static final String START_NAME = "name";
    public static final String START_ADDRESS = "address";
    public static final String START_TEL = "tel";
    public static final String START_ISDEFAULT = "IsDefault";
    public static final String START_POSITION="position";



    /*推送消息url那个Intent*/
    public static final String MESSAGE_URL = "message_url";

    /*从首页界面进入定位地址界面 requestcode*/
    public static final int HOME_FREGMENT_TO_ADDRESS = 0;
    /*从定位地址界面到首页 resultCode*/
    public static final int ADDRESS_TO_HOME_FRAGMENT = 3;

    /*从定位地址界面到首页 返回数据*/
    public static final String ADDRESS_TO_HOME_FRAGMENT_DATA = "address_now";

    /*再按一次退出*/
    public static int DOUBLE_CLICK = 0;

    /*从购物车跳到分类界面*/
    public static String TO_CLASS_FRAGMENT="to_class_fragment";


}
