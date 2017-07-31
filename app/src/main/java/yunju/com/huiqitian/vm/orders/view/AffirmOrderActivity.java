package yunju.com.huiqitian.vm.orders.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.AffirmOrderMarket;
import yunju.com.huiqitian.entity.AffirmOrderVouchers;
import yunju.com.huiqitian.entity.CartGoods;
import yunju.com.huiqitian.entity.Order;
import yunju.com.huiqitian.entity.ReceiveAddress;
import yunju.com.huiqitian.entity.Voucher;
import yunju.com.huiqitian.http.entity.CartSettleResp;
import yunju.com.huiqitian.http.entity.NowSubmitOrderReq;
import yunju.com.huiqitian.http.entity.ReceiverAddressResp;
import yunju.com.huiqitian.http.entity.SubmitOrderReq;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.utils.pay.AliPayUtils;
import yunju.com.huiqitian.vm.adapter.AffirmOrderAdapter;
import yunju.com.huiqitian.vm.address.view.ReceiverAddressActivity;
import yunju.com.huiqitian.vm.orders.model.AffirmOrderModel;
import yunju.com.huiqitian.vm.pay.model.PayModel;
import yunju.com.huiqitian.vm.pay.view.PayActivity;

/**
 * Created by liuGang on 2016/8/10 0010.
 */
public class AffirmOrderActivity extends BaseActivity implements AffirmOrderModel.OrderGoPayInterface ,AffirmOrderAdapter.MoneyCallBackLister{

    /*判断那个页面跳转过来的*/
    private int skip_Int;

    private int isDefault = 0;//判断有没有默认地址或者有没有收货地址
    private int addressUsAble=100;//当没有默认地址不在送货范围之内时
    private List<Byte> usAbleList=new ArrayList<>();//保存之前地址的usable;
    private int addressId;//选中收货地址的id
    private boolean addressType=false;//判断选择的收货地址有没有被删除
    private int position=0;//最终选择的收货地址的position

    /*多家超市购物车中选择代金券存放选择的代金券的id*/
    private List voucherListId = new ArrayList();
    /*多家超市购物车中选择代金券存放选择的代金券的marketId*/
    private List voucherListMarketId = new ArrayList();
    /*多家超市购物车中选择代金券存放选择代金券的position*/
    private List voucherListPosition = new ArrayList();

    /*购物车选择代金券的list*/
    private List<Voucher> listVoucher = new ArrayList<>();

    /*判断提交订单的方式*/
    private int remittance;

    /*立即购买数据*/
    private CartSettleResp nowBuyResp;
    /*收货地址数据*/
    private ReceiverAddressResp receiverAddressResp;
    /*订单列表*/
    private ListView lvAffirmOrder;
    /*去结算返回的订单数据*/
    private String settleResponse;
    /*去结算返回的地址数据*/
    private String addressResponse;
    /*订单总数量*/
    private TextView tvAllOrderNum;
    /*所有点单总价格大体字部分*/
    private TextView tvOrderBigNum;
    /*所有订单总价格小体字部分*/
    private TextView tvOrderSmallNum;
    /*顶部导航标题*/
    private TextView tvTitle;
    /*顶部返回图片按钮*/
    private LinearLayout ivTitleBack;
    /*提交订单*/
    private TextView tvSubmitOrder;
    /*确认订单的Model层对象*/
    private AffirmOrderModel affirmOrderModel;
    /*装载所有订单商品的购物车id集合*/
    private List<Integer> cartGoodsIds;
    /*总价格*/
    private String[] priceStr;

    /*支付宝起调model*/
    private PayModel payModel;

    /*支付宝支付*/
    private AliPayUtils aliPayUtils;

    private ImageView ivAffirmChoCoupon;


    /*优惠券集合*/
    private List<AffirmOrderVouchers> vouchersList;

    private RelativeLayout rlAffirmOrderAddress;
    private LinearLayout llAddressDef;
    private TextView tvAffirmAddAddress;//添加收货地址
    private ImageView ivAffirmAnonymity;

    /*购物车中的地址*/
    private TextView tvAffirmName;
    private TextView tvAffirmNumber;
    private TextView tvAffirmReceiveAddress;
    private ImageView ivAffirmChooseAddress;//更换收货地址
    private TextView tvHintMessageAddress;//收货地址提示
    private TextView tvDefaultAddress;//显示是不是默认地址;


    private ReceiverAddressResp addressResp;
    private List<ReceiveAddress> addressList;

    private CartSettleResp settleResp;
    private List<AffirmOrderMarket> orderMarkets;

    //购买账单的总金额
    BigDecimal bdPrice = new BigDecimal(Double.valueOf(0.00));
    //已送所需要的配送费
    BigDecimal sendPrice = new BigDecimal(Double.valueOf(0.00));
    private EditText etAffirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_affirm_order);
    }

    @Override
    public void initBoot() {
        /*实例化对象*/
        affirmOrderModel = new AffirmOrderModel(activity);
        cartGoodsIds = new ArrayList<>();
        payModel = new PayModel(activity);
        aliPayUtils = new AliPayUtils(activity);

    }

    @Override
    public void initViews() {
        /*初始化控件*/
        lvAffirmOrder = findView(R.id.lv_affirmOrder);
        tvAllOrderNum = findView(R.id.tv_affirm_allOrderNum);
        tvOrderBigNum = findView(R.id.tv_affirm_all_bigPrice);
        tvOrderSmallNum = findView(R.id.tv_affirm_all_smallPrice);
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        tvSubmitOrder = findView(R.id.tv_affirm_submitOrder);
        //ivAffirmChoCoupon = findView(R.id.img_affirmBtm_chooseCoupon);
        rlAffirmOrderAddress = findView(R.id.rl_affirmOrder_address);
        llAddressDef = findView(R.id.ll_address_def);
        tvAffirmAddAddress = findView(R.id.tv_affirm_addAddress);
        ivAffirmAnonymity = findView(R.id.iv_affirm_anonymity);

        /*购物车地址*/
        tvAffirmName = findView(R.id.tv_affirm_name);
        tvAffirmNumber = findView(R.id.tv_affirm_number);
        tvAffirmReceiveAddress = findView(R.id.tv_affirm_receiveAddress);
        ivAffirmChooseAddress = findView(R.id.iv_affirm_chooseAddress);
        tvHintMessageAddress = findView(R.id.tv_hint_message_address);
        tvDefaultAddress = findView(R.id.tv_default_address);

        /*买家留言*/
        etAffirm = findView(R.id.et_affirm_leaveMsg);
    }

    @Override
    public void initData(Bundle bundle) {
        //设置顶部导航标题
        tvTitle.setText(R.string.str_affirm_orderTitle);
        /*数据处理*/
        skip_Int = bundle.getInt(Constant.START_NEXT_KEY);
        if (skip_Int == 0) {
            settleResponse = bundle.getString(Constant.AFFIRM_INFO);
            /*addressResponse = bundle.getString(Constant.ADDRESS_INFO);*/
            //解析确认订单中的json数据
            settleResp = parseObject(settleResponse, CartSettleResp.class);
            orderMarkets = settleResp.getObj().getMarketCartGoodsList();
            /*收货地址数据解析*/
            addressList = settleResp.getObj().getAppAddress();
            /*addressResp = parseObject(addressResponse, ReceiverAddressResp.class);
            addressList = addressResp.getObj();*/
            remittance = 0;

        } else if (skip_Int == 1) {
            /*解析确认订单中的json数据*/
            nowBuyResp = (CartSettleResp) bundle.getSerializable(Constant.AFFIRM_INFO);
            /*提交订单商品数据，代金券数据*/
            orderMarkets = nowBuyResp.getObj().getMarketCartGoodsList();
            LogUtils.error(AffirmOrderActivity.class, "2222222222222222" + nowBuyResp.getObj());
            /*收货地址*/
            addressList = nowBuyResp.getObj().getAppAddress();
            remittance = 1;
        }

        if (!addressList.isEmpty() && addressList != null) {
            //设置收货地址信息以显示
            for (int i=0;i<addressList.size();i++ ) {
                usAbleList.add(addressList.get(i).getUseable());
                if (addressList.get(i).getUseable() == 1) {
                    addressUsAble=i;
                    if ((int) addressList.get(i).getIsDefault() == (int) Constant.IS_DEFAULT_ADDRESS) {
                        position=i;
                        isDefault = 1;
                        addressId=addressList.get(i).getId();
                        tvAffirmName.setText(addressList.get(i).getName());
                        tvAffirmNumber.setText(addressList.get(i).getTel());
                        tvAffirmReceiveAddress.setText(addressList.get(i).getAddressInfo());
                    }
                }
            }
            if(addressUsAble!=100){
                if(isDefault!=1){
                    position=addressUsAble;
                    addressId=addressList.get(addressUsAble).getId();
                    tvAffirmName.setText(addressList.get(addressUsAble).getName());
                    tvAffirmNumber.setText(addressList.get(addressUsAble).getTel());
                    tvAffirmReceiveAddress.setText(addressList.get(addressUsAble).getAddressInfo());
                    tvDefaultAddress.setVisibility(View.GONE);
                }

            }else{
                rlAffirmOrderAddress.setVisibility(View.GONE);
                llAddressDef.setVisibility(View.VISIBLE);
                tvHintMessageAddress.setText("亲，你收货地址不在送货范围哦");
                tvAffirmAddAddress.setText("请设置地址");
            }

        } else {
            rlAffirmOrderAddress.setVisibility(View.GONE);
            llAddressDef.setVisibility(View.VISIBLE);
        }

    }

    private int isAnonymity = 1;

    @Override
    public void initEvents() {
        /*匿名购买*/
        ivAffirmAnonymity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnonymity == 1) {
                    ivAffirmAnonymity.setImageResource(R.mipmap.img_affirm_anonymity_close);
                    isAnonymity = 0;
                } else {
                    ivAffirmAnonymity.setImageResource(R.mipmap.img_indent_button_yes);
                    isAnonymity = 1;
                }

            }
        });

        /*添加收货地址*/
        tvAffirmAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(ReceiverAddressActivity.class);*/
                Intent intent = new Intent(AffirmOrderActivity.this, ReceiverAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.START_NEXT_KEY, "order");
                bundle.putSerializable(Constant.SHARED_USER_ADDRESS, (Serializable) addressList);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);


            }
        });



        /*更换收货地址的点击事件*/
        ivAffirmChooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffirmOrderActivity.this, ReceiverAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.START_NEXT_KEY, "order");
                bundle.putSerializable(Constant.SHARED_USER_ADDRESS, (Serializable) addressList);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

        /*提交订单的点击事件*/
        tvSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*获取买家留言的内容*/
                String msgBody = etAffirm.getText().toString().trim();

                if (remittance == 0) {
                    /*购物车购买*/
                    if (addressUsAble==100) {
                        Toast.makeText(activity, "请添加用户收货地址或这是默认地址", Toast.LENGTH_LONG).show();
                    } else {
                        SubmitOrderReq submitOrderReq = new SubmitOrderReq(cartGoodsIds, Constant.ORDER_ORIGIN, addressList.get(position).getId(),msgBody);
                        if (voucherListId != null) {
                            for (int i = 0; i < voucherListId.size(); i++) {
                                Voucher voucher = new Voucher();
                                voucher.setId((Integer) voucherListId.get(i));
                                voucher.setMarketId((Integer) voucherListMarketId.get(i));
                                listVoucher.add(voucher);
                            }
                            submitOrderReq.setVoucher(listVoucher);
                            submitOrderReq.setMessage(msgBody);
                        }
                        affirmOrderModel.submitOrder(submitOrderReq);

                    }

                } else if (remittance == 1) {
                    /*立即购买*/
                    if (addressUsAble==100) {
                        Toast.makeText(activity, "请添加用户收货地址或默认地址", Toast.LENGTH_LONG).show();

                    } else if (tvAffirmName.getText() != null && !(tvAffirmName.getText().equals(""))) {
                        NowSubmitOrderReq nowSubmitOrderReq = new NowSubmitOrderReq();
                        nowSubmitOrderReq.setGoodsId(orderMarkets.get(0).getAppCartGoodsList().get(0).getGoodsId());
                        nowSubmitOrderReq.setType(orderMarkets.get(0).getAppCartGoodsList().get(0).getType());
                        nowSubmitOrderReq.setDeliveryInfoId(addressList.get(position).getId());
                        nowSubmitOrderReq.setOrderOrigin((byte) 1);
                        /*设置留言*/
                        nowSubmitOrderReq.setMessage(msgBody);

                        if (MyUtils.getVoucherId() != null && !MyUtils.getVoucherId().equals("")) {
                            nowSubmitOrderReq.setVoucherId(Integer.parseInt(MyUtils.getVoucherId()));
                            affirmOrderModel.nowSubmitOrder(nowSubmitOrderReq);
                        } else {
                            affirmOrderModel.nowSubmitOrder(nowSubmitOrderReq);
                        }

                    }
                }

            }
        });


        //设置顶部返回的点击事件
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //给listView设置适配器
        AffirmOrderAdapter affirmOrderAdapter = new AffirmOrderAdapter(activity, orderMarkets, voucherListId, voucherListMarketId, voucherListPosition, this);
        lvAffirmOrder.setAdapter(affirmOrderAdapter);


        //遍历计算订单总数和订单总价格
        int orderNum = 0;
        for (AffirmOrderMarket orderMarket : orderMarkets) {
            //单一家商超下商品所需金额
            BigDecimal payFreePrice=new BigDecimal(Double.valueOf(0.00));
            for (CartGoods cartGoods : orderMarket.getAppCartGoodsList()) {
                BigDecimal b = new BigDecimal(Byte.valueOf(cartGoods.getQty()));
                bdPrice = ((cartGoods.getRetailPrice()).multiply(b)).add(bdPrice);
                payFreePrice=((cartGoods.getRetailPrice()).multiply(b)).add(payFreePrice);
                orderNum = (byte) (cartGoods.getQty() + orderNum);
                cartGoodsIds.add(cartGoods.getId());
            }
            if(payFreePrice.compareTo(orderMarket.getFreeDispatchLimit())==-1){
                sendPrice = sendPrice.add(orderMarket.getDispatchFee());
            }

            /*for (AffirmOrderVouchers voucher:orderMarket.getAppVouchers()) {
                vouchersList.add(voucher);
            }*/
        }
        //设置订单总数和价格
        tvAllOrderNum.setText(String.valueOf(orderNum));
        priceStr = (bdPrice.add(sendPrice)).toString().split("\\.");
        LogUtils.error(AffirmOrderActivity.class,"配送费..........."+sendPrice);
        tvAllOrderNum.setText(String.valueOf("共" + orderNum) + "件");
        // String[] priceStr = bdPrice.toString().split("\\.");
        tvOrderBigNum.setText(priceStr[0]);
        tvOrderSmallNum.setText("." + priceStr[1]);

        /*支付宝回调接口*/
        payModel.setPayInterface(new PayModel.PayInterface() {
            @Override
            public void payResult(String result) {
                Log.e("setPayInterface", "请求结果：" + result);
                  /*起调支付宝支付*/
                aliPayUtils.payGood(result, new AliPayUtils.AliPlayInterface() {
                    @Override
                    public void fail() {

                    }

                    @Override
                    public void success(String result, HashMap<String, String> map) {
                        Log.e("setPayInterface", "支付结果：" + result);
                        Log.e("setPayInterface", "支付集合：" + map.size());
                        /*如果用户需要支付另一个超市的时候，需要重新起调支付宝*/
                    }

                    @Override
                    public void ensureing() {

                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int byteExtra = data.getByteExtra(Constant.START_ISDEFAULT, (byte) -1);
            if (byteExtra==1){
                tvDefaultAddress.setVisibility(View.VISIBLE);
            }else {
                tvDefaultAddress.setVisibility(View.GONE);
            }
            rlAffirmOrderAddress.setVisibility(View.VISIBLE);
            llAddressDef.setVisibility(View.GONE);
            tvAffirmName.setText(data.getStringExtra(Constant.START_NAME));
            tvAffirmReceiveAddress.setText(data.getStringExtra(Constant.START_ADDRESS));
            tvAffirmNumber.setText(data.getStringExtra(Constant.START_TEL));
            isDefault = 1;
            position=data.getIntExtra(Constant.START_POSITION,addressUsAble);
        } else {
            LogUtils.error(AffirmOrderActivity.class, "....................................");
            finish();
        }
    }

    /*订单创建以后跳转到支付选择界面*/
    @Override
    public void orderCreatSuccess(List<Order> orderIds) {
        //购买使我的界面要刷新
        MyUtils.clearVoucher();
        AppApplication.putBoolean("person_fragment_refresh", true);
        AppApplication.putBoolean("shop_fragment_refresh", true);
        /*传递到支付页面的实际支付价格*/
        String[] payFee;
        /*提交订单的数组*/
        String[] ordersId = new String[orderIds.size()];
        BigDecimal pay = new BigDecimal(Double.valueOf(0.00));
        for (int i = 0; i < orderIds.size(); i++) {
            ordersId[i] = orderIds.get(i).getId().toString();
            pay = pay.add(orderIds.get(i).getPayFee());
        }
        payFee = pay.toString().split("\\.");
        Bundle bundle = new Bundle();
        bundle.putStringArray(Constant.PAY_PRICE, payFee);
        bundle.putStringArray(Constant.PAY_ORDERS, ordersId);
        bundle.putInt(Constant.PAY_LENGTH, orderIds.size());
        startActivity(PayActivity.class, bundle);
        finish();
    }

    @Override
    public void orderCreatFailure(String msg) {

    }


    @Override
    public void voucherMonet(List<BigDecimal> list) {
        BigDecimal practicalPrice = new BigDecimal(Double.valueOf(0.00));
        practicalPrice = bdPrice.add(sendPrice);
        for (int i = 0; i < list.size(); i++) {
            practicalPrice = practicalPrice.subtract(list.get(i));
        }
        String str[] = practicalPrice.toString().split("\\.");
        tvOrderBigNum.setText(str[0]);
        tvOrderSmallNum.setText("." + str[1]);
    }

 /*   @Override
    public void addressDateSuccess(List<ReceiveAddress> receiverAddressResps) {
        addressList = receiverAddressResps;
        *//*选择地址并添加了地址,设置添加的地址usable为1*//*
        for(int i=0;i<addressList.size();i++){
            if(i+1>usAbleList.size()){
                addressList.get(i).setUseable((byte)1);
                usAbleList.add((byte)1);
            }else {
                addressList.get(i).setUseable(usAbleList.get(i));
            }

            if(addressList.get(i).getId()==addressId){
                *//*addressType*//*
                rlAffirmOrderAddress.setVisibility(View.GONE);
                llAddressDef.setVisibility(View.VISIBLE);
                tvHintMessageAddress.setText("亲，收货地址呗删除了");
                tvAffirmAddAddress.setText("请重新选择收货地址");
            }
        }

        *//*在选择地址时删除地址订单页面因不显示地址*//*

    }

    @Override
    public void addressDateFailure(String msg) {

    }*/
}
