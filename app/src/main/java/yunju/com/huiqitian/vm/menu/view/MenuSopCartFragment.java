package yunju.com.huiqitian.vm.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseFragment;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.CartGoods;
import yunju.com.huiqitian.entity.CartMarket;
import yunju.com.huiqitian.http.entity.AlterGoodsNumReq;
import yunju.com.huiqitian.http.entity.LookCartResp;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.ShopCartAdapter;
import yunju.com.huiqitian.vm.adapter.ShopCartGoodsAdapter;
import yunju.com.huiqitian.vm.login.view.LoginActivity;
import yunju.com.huiqitian.vm.menu.model.ShopCartModel;
import yunju.com.huiqitian.vm.orders.view.AffirmOrderActivity;
import yunju.com.huiqitian.vm.widget.PopShowUtils;

/**
 * Created by liuGang on 2016/7/18 0018.
 */
public class MenuSopCartFragment extends BaseFragment {

    //记住位置
    private List<Integer> number = new ArrayList<>();
    //声明控件
    private RelativeLayout rlBtmDel;//底部全选删除的布局
    private RelativeLayout rlBtmSettle;//底部全选结算的布局
    ////底部全选结算中的控件
    private TextView tvTitleNum;//顶部标题的 购物车物品总数量
    private TextView tvAllEdit;//顶部 全部编辑
    private CheckBox cbSettleAllCho;//底部全选结算布局中的 全选
    private TextView tvBigNum;//底部全选结算布局中的合计 价格大体字部分
    private TextView tvSmallNum;//底部全选结算布局中的合计 价格小体字部分
    private TextView tvSettleNum;//底部全选结算布局中选中的 结算数量
    //底部全选删除中的控件
    private CheckBox cbDelAllCho;//底部全选删除布局中的 全选
    private TextView tvMoveCollect;//底部全选删除布局中的移入 收藏夹
    private TextView tvBtmDel;//底部全选删除布局中的 删除
    private ListView lvCart;//购物车显示产品的列表(因购物车修改暂时去掉)
    private TextView tvMarketName;//购物车名称
    private Spinner spinner;
    private int positions=0;//用位置区分超市
    private TextView tvShopTitleOver;//购物车无数据时显示

    private ListView lvCartShop;//购物车超市下商品列表
    //数据model
    private ShopCartModel shopCartModel;
    //控制顶部编辑0/完成1 的int值
    private int EDIT_CLICK;
    //控制是否点击修改的Boolean值
    private Boolean isClickAlter;
    //声明shopCartAdapter----之前版本预留
    private ShopCartAdapter shopCartAdapter;
    //显示一家超市购物车详情
    private ShopCartGoodsAdapter shopCartGoodsAdapter;
    //后台返回购物车数据response
    private String response;
    //声明承装CartMarket和CartGoods的List
    private List<Object> marketAndGoodsList;
    //声明装载购物车所有CartGoods的List
    private List<CartGoods> choGoodsList;
    //声明并实例化承装删除的CartGoods的List
    private List<CartGoods> seletorGoods = new ArrayList<CartGoods>();
    //解析购物车列表json数据
    private LookCartResp lookCartResp;
    //购物车去结算
    private LinearLayout llSettle;

    private LayoutInflater layoutInflater;

    List<CartMarket> cartMarketsData;//购物车接口回调数据

    /*购物车地址数据*/
    private String addressResp;
    private RelativeLayout rlNOGoods;
    private Button btnToClassFragment;
    private int titleNum;//商品数量
    private int titleNumType = 0;//判断商品种类的type

    /*购物车超市*/
    private List<String> marketNameList;
    private ArrayAdapter<String> arr_adapter;
    private RelativeLayout rlNoLogin;

    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.layoutInflater = layoutInflater;
        return layoutInflater.inflate(R.layout.fragment_menu_sopcart, viewGroup, false);
    }


    @Override
    public void initBoot() {
        EDIT_CLICK = 0;
        //实例化购物车的数据层shopCartModel
        shopCartModel = new ShopCartModel(activity);
    }

    @Override
    public void initViews() {
        //初始化控件
        spinner=findView(R.id.spinner);
        //底部结算和删除的布局控件
        rlBtmSettle = findView(R.id.rl_cart_btm_settle);
        rlBtmDel = findView(R.id.rl_cart_btm_del);

        //超市名称
        /*tvMarketName = findView(R.id.tv_market_name);*/
        /*（编辑）中*/
        //购物车所有产品数量
        tvTitleNum = findView(R.id.tv_cart_title_num);
        //编辑/完成
        tvAllEdit = findView(R.id.tv_cart_all_edit);
        //(编辑)中的全选checkBox
        cbSettleAllCho = findView(R.id.cb_cart_settle_allChoose);
        //(编辑)中核算价格的大体字部分
        tvBigNum = findView(R.id.tv_cart_big_num);
        //(编辑)中核算价格的小体字部分
        tvSmallNum = findView(R.id.tv_cart_small_num);
        //(编辑)选中的产品总数
        tvSettleNum = findView(R.id.tv_cart_settle_num);
        //去结算
        llSettle = findView(R.id.ll_settle);

        /*完成*/
        //（完成）中的全选checkBox
        cbDelAllCho = findView(R.id.cb_cart_del_allChoose);
        //(完成)中的 移入购物车
        tvMoveCollect = findView(R.id.tv_cart_move_collect);
        //（完成）中的 删除
        tvBtmDel = findView(R.id.tv_cart_btm_del);

        //初始化listView
        /*lvCart = findView(R.id.lv_cart);*/
        lvCartShop = findView(R.id.lv_cart_shop);

        /*购物车为空*/
        rlNOGoods = findView(R.id.rl_no_goods);
        btnToClassFragment = findView(R.id.btn_toClassFragment);
        tvShopTitleOver=findView(R.id.tv_shop_title_over);

         /*未登陆*/
        rlNoLogin = findView(R.id.rl_no_login);


    }

    @Override
    public void initData(Bundle bundle) {
        cbSettleAllCho.setChecked(false);
        tvBigNum.setText("00");
        tvSmallNum.setText(".00");
        tvSettleNum.setText("0");

        //加载购物车列表
        if (MyUtils.checkUser()) {
            shopCartModel.lookShopCart();
        } else {
            //跳转至登录页面
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra(Constant.FROM_SHOP_CART, 1);
            getActivity().startActivity(intent);

        }

    }

    @Override
    public void initEvents() {

        /*选择超市显示购物车数据*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = position;
                shopCartGoodsAdapter = new ShopCartGoodsAdapter(activity, lookCartResp.getObj().get(positions).getAppCartGoodsList());
                lvCartShop.setAdapter(shopCartGoodsAdapter);

                shopCartGoodsAdapter.setCartAdapterInterfaces(new ShopCartGoodsAdapter.CartAdapterInterfaces() {
                    @Override
                    public void alterObjList(List<CartGoods> cartGoodsList) {
                        //修改购物车产品数量
                        if (!cartGoodsList.isEmpty()) {
                            List<AlterGoodsNumReq> goodsNumList = new ArrayList<AlterGoodsNumReq>();
                            for (int i = 0; i < cartGoodsList.size(); i++) {
                                //封装请求的request
                                AlterGoodsNumReq alterGoodsNumReq = new AlterGoodsNumReq((cartGoodsList.get(i)).getId(), (cartGoodsList.get(i)).getQty());
                                //将请求参数添加到集合
                                goodsNumList.add(alterGoodsNumReq);
                            }
                            shopCartModel.alterGoodsNum(goodsNumList);
                        }
                    }

                    @Override
                    public void selectorGoodsNum(List<CartGoods> selectorGoods) {
                        //设置 全选/取消全选 两种状态
                        if (lookCartResp.getObj().get(positions).getAppCartGoodsList().size() == selectorGoods.size()) {
                            cbSettleAllCho.setChecked(true);
                            cbDelAllCho.setChecked(true);
                        } else {
                            cbSettleAllCho.setChecked(false);
                            cbDelAllCho.setChecked(false);
                        }

                        seletorGoods = selectorGoods;
                        LogUtils.error(MenuSopCartFragment.class, "长度====" + selectorGoods.size());
                        //初始化购物车价格
                        BigDecimal bdNum = new BigDecimal(Double.valueOf(0.00));
                        //设置购物车“去结算”的数量
                        int selNum = 0;
                        //如果购物车的选中的CartGoods的List的size不为0则计算价格
                        if (selectorGoods.size() != 0) {
                            for (CartGoods goods : selectorGoods) {
                                selNum = goods.getQty() + selNum;
                                BigDecimal b = new BigDecimal(Byte.valueOf(goods.getQty()));
                                bdNum = ((goods.getRetailPrice()).multiply(b)).add(bdNum);
                            }
                            tvSettleNum.setText(String.valueOf(selNum));

                            String[] priceStr = bdNum.toString().split("\\.");
                            tvBigNum.setText(priceStr[0]);
                            tvSmallNum.setText("." + priceStr[1]);
                            //否则设置为默认价格
                        } else {
                            tvSettleNum.setText(String.valueOf(0));
                            tvBigNum.setText("0");
                            tvSmallNum.setText(".00");
                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*购物车为空时，点击*/
        btnToClassFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu activity = (MainMenu) getActivity();
                activity.initFragment(1);
            }
        });

        //编辑/完成 监听
        tvAllEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EDIT_CLICK == 0) {
                    rlBtmDel.setVisibility(View.VISIBLE);
                    rlBtmSettle.setVisibility(View.GONE);
                    tvAllEdit.setText("完成");
                    isClickAlter = false;
                    EDIT_CLICK = 1;

                } else {
                    rlBtmSettle.setVisibility(View.VISIBLE);
                    rlBtmDel.setVisibility(View.GONE);
                    tvAllEdit.setText("编辑");
                    isClickAlter = true;
                    EDIT_CLICK = 0;
                }
                //每次点击将当前所处（编辑/完成）的类型值传递到shopCartAdapter中
                if (shopCartGoodsAdapter != null) {
                    shopCartGoodsAdapter.showBtnOrNum(EDIT_CLICK, isClickAlter);
                }
            }
        });


        //购物车 去结算 监听
        llSettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!seletorGoods.isEmpty()) {
                    shopCartModel.goSettle(seletorGoods);

                } else {
                    Toast.makeText(activity, "请选择要结算的商品", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //购物车产品 删除 监听
        tvBtmDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!seletorGoods.isEmpty()) {

                    View view = layoutInflater.inflate(R.layout.pop_cart_alldel, null);
                    PopShowUtils.showPopwindow(view, activity.getWindow(), tvBtmDel, true);
                    TextView pop_affirm_del = (TextView) view.findViewById(R.id.pop_affirm_del);
                    TextView pop_cancel_del = (TextView) view.findViewById(R.id.pop_cancel_del);
                    TextView pop_PromptNum_del = (TextView) view.findViewById(R.id.pop_PromptNum_del);

                    pop_PromptNum_del.setText("亲，确定要删除这" + seletorGoods.size() + "种商品吗？");

                    pop_affirm_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shopCartModel.delCartGoods(seletorGoods);
                            PopShowUtils.closePopWindowPage();
                            rlBtmDel.setVisibility(View.GONE);
                            rlBtmSettle.setVisibility(View.VISIBLE);
                        }
                    });


                    pop_cancel_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopShowUtils.closePopWindowPage();
                            rlBtmDel.setVisibility(View.GONE);
                            rlBtmSettle.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    Toast.makeText(activity, "请选择要删除的商品", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //购物车修改中的全选 监听
        cbDelAllCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*compileCheckAll();*/
                if (!lookCartResp.getObj().isEmpty() && lookCartResp.getObj() != null) {
                    for (int i = 0; i < lookCartResp.getObj().get(positions).getAppCartGoodsList().size(); i++) {
                        //将选中状态记录到map中
                        ShopCartGoodsAdapter.getIsSelected().put(i, true);
                    }
                    //将购物车所有产品添加到已选择list中进行价格计算
                    shopCartGoodsAdapter.addChoGoods(lookCartResp.getObj().get(positions).getAppCartGoodsList());
                }else {
                    //取消选中  遍历将所有的checkBox设置为未选中
                    for (int i = 0; i < lookCartResp.getObj().get(positions).getAppCartGoodsList().size(); i++) {
                        shopCartGoodsAdapter.getIsSelected().put(i, false);
                    }
                    //将核算价格的list清空
                    shopCartGoodsAdapter.clearChooseGoods();
                }
                shopCartGoodsAdapter.notifyDataSetChanged();
            }
        });


        //购物车去结算中的全选 监听
        cbSettleAllCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ClearingCheckAll();*/
                if (!lookCartResp.getObj().isEmpty() && lookCartResp.getObj() != null) {
                    if (cbSettleAllCho.isChecked()) {
                        cbDelAllCho.setChecked(true);
                        for (int i = 0; i < lookCartResp.getObj().get(positions).getAppCartGoodsList().size(); i++) {
                            //将选中状态记录到map中
                            ShopCartGoodsAdapter.getIsSelected().put(i, true);
                        }
                        shopCartGoodsAdapter.addChoGoods(lookCartResp.getObj().get(positions).getAppCartGoodsList());
                    } else {
                        cbDelAllCho.setChecked(false);
                        //取消选中  遍历将所有的checkBox设置为未选中
                        for (int i = 0; i < lookCartResp.getObj().get(positions).getAppCartGoodsList().size(); i++) {
                            shopCartGoodsAdapter.getIsSelected().put(i, false);
                        }
                        //将核算价格的list清空
                        shopCartGoodsAdapter.clearChooseGoods();
                    }
                    shopCartGoodsAdapter.notifyDataSetChanged();
                }
            }
        });

        //实例化CartGoodsInterface接口
        shopCartModel.setCartGoodsInterface(new ShopCartModel.CartGoodsInterface() {
            @Override
            public void cartGoods(String resp) {
                response = resp;
                //解析购物车列表json数据
                lookCartResp = parseObject(resp, LookCartResp.class);
                /*SopCartData();*/
                showNoGoods(lookCartResp.getObj());//当购物车为空时
                if(lookCartResp.getObj()!=null&&lookCartResp.getObj().size()>0){
                    if (titleNumType == 0) {
                        titleNum += lookCartResp.getObj().get(0).getAppCartGoodsList().size();
                    }
                    titleNumType = 1;//防止商品增加导致商品种类增加
                    //数据
                    marketNameList = new ArrayList<String>();
                    for(int i=0;i<lookCartResp.getObj().size();i++){
                        marketNameList.add(lookCartResp.getObj().get(i).getMarketName());
                    }
                    //适配器
                    arr_adapter= new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, marketNameList);
                    //设置样式
                    arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //加载适配器
                    spinner.setAdapter(arr_adapter);
                }

            }

            @Override
            public void noCartGoods(String msg) {

            }


            @Override
            public void alterGoodsSuccess() {
                //修改成功重新加载购物车列表
                shopCartModel.lookShopCart();
            }

            @Override
            public void startToLoginAct() {
                getActivity().finish();
                //跳转至登录页面
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra(Constant.FROM_SHOP_CART, 1);
                getActivity().startActivity(intent);
            }

            @Override
            public void delGoodsSuccess() {
                //删除成功重新加载购物车列表
                shopCartModel.lookShopCart();
                //删除成功后清除内存中的数据
                seletorGoods.clear();
            }

            @Override
            public void settleData(String resp) {
                /*去结算*/
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.START_NEXT_KEY, 0);
                bundle.putString(Constant.AFFIRM_INFO, resp);
                startActivity(AffirmOrderActivity.class, bundle);
            }

            /*@Override
            public void receiverAddress(String resp) {
                addressResp=resp;
                *//*去结算所有选中的购物车商品*//*


            }*/
        });
    }

    /*购物车没有商品时 购物车为空时的界面*/
    public void showNoGoods(List<CartMarket> cartMarkets) {
        if (cartMarkets == null || cartMarkets.size() == 0) {
            lvCartShop.setVisibility(View.GONE);
            rlBtmSettle.setVisibility(View.GONE);
            tvTitleNum.setVisibility(View.GONE);
            tvAllEdit.setVisibility(View.GONE);
            rlNOGoods.setVisibility(View.VISIBLE);
            rlBtmDel.setVisibility(View.GONE);
            tvTitleNum.setText("(0)");
            tvShopTitleOver.setVisibility(View.VISIBLE);
        } else {
            rlBtmSettle.setVisibility(View.VISIBLE);
            tvAllEdit.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            //设置购物车的产品总数量
            tvTitleNum.setText("(" + String.valueOf(titleNum) + ")");
        }
    }


    @Override
    public void onResume() {
        LogUtils.error(MenuSopCartFragment.class,"111111111111111111111111111111111");
        super.onResume();
        LogUtils.error(MenuSopCartFragment.class, "222222222229999999999999999999999");
       /* if (AppApplication.getBoolean("shop_fragment_refresh")) {
            lvCartShop.setVisibility(View.VISIBLE);
            rlBtmSettle.setVisibility(View.VISIBLE);
            tvTitleNum.setVisibility(View.VISIBLE);
            tvAllEdit.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            rlBtmSettle.setVisibility(View.VISIBLE);
            rlNOGoods.setVisibility(View.GONE);
            tvShopTitleOver.setVisibility(View.GONE);
            cbSettleAllCho.setChecked(false);//结算全选取消选中
            cbDelAllCho.setChecked(false);//删除全选未选中
            //设为默认价格
            tvSettleNum.setText(String.valueOf(0));
            tvBigNum.setText("0");
            tvSmallNum.setText(".00");
            tvAllEdit.setText("编辑");
            isClickAlter = true;
            EDIT_CLICK = 0;
            shopCartModel.lookShopCart();
            LogUtils.error(MenuSopCartFragment.class,"2222222222222222222222222");
            AppApplication.putBoolean("shop_fragment_refresh", false);
        }*/
        if (AppApplication.getBoolean("shop_fragment_refresh")) {
            MainMenu mainMenu= (MainMenu) getActivity();
            mainMenu.initFragment(2);
            LogUtils.error(MenuSopCartFragment.class,"6666666666666666666666666666666666666");
            AppApplication.putBoolean("shop_fragment_refresh",false);
        }
        if(AppApplication.getBoolean("shop_no_login")){
            MainMenu mainMenu= (MainMenu) getActivity();
            mainMenu.initFragment(0);
            AppApplication.putBoolean("shop_no_login", false);
        }

    }
    /**
     * 当界面重新展示时（fragment.show）,调用onrequest刷新界面
     */
   /* @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        LogUtils.error(MenuSopCartFragment.class,"........................................");
        if (!hidden) {



        }
    }*/

    /**
     * 购物车初始化
     */
    public void SopCartData() {
        List<Integer> listPosition = new ArrayList<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        //获取CartMarket对象列表
        List<CartMarket> cartMarkets = lookCartResp.getObj();
        cartMarketsData = cartMarkets;

        LogUtils.error(MenuSopCartFragment.class, "超市数量" + cartMarkets.size());
        int cartMarketLength = cartMarkets.size();

        for (int i = 0; i < cartMarketLength; i++) {
            listPosition.add(cartMarkets.get(i).getAppCartGoodsList().size());
        }
        //创建承装所有CartGoods的List
        choGoodsList = new ArrayList<CartGoods>();
        //创建承载所有CartMarket和CartGoods的List
        marketAndGoodsList = new ArrayList<Object>();
        //初始化价格
        int marketId = 0;
        int num = 0;
        for (CartMarket cartMarket : cartMarkets) {
            marketId = cartMarket.getMarketId();
            marketAndGoodsList.add(cartMarket);
            LogUtils.error(MenuSopCartFragment.class, "num====" + num);
            map.put(num, marketAndGoodsList.size() - 1);
            num++;
            number.add((Integer) (marketAndGoodsList.size() - 1));
            List<CartGoods> cartGoodses = cartMarket.getAppCartGoodsList();
            if (titleNumType == 0) {
                titleNum += cartGoodses.size();
            }

            for (CartGoods cartGoods : cartGoodses) {
                choGoodsList.add(cartGoods);
                if (cartGoods.getMarketId() == marketId) {
                    marketAndGoodsList.add(cartGoods);
                }
                LogUtils.error(MenuSopCartFragment.class, "size======" + marketAndGoodsList.size());
            }
        }
        titleNumType = 1;//防止商品增加导致商品种类增加

        showNoGoods(cartMarkets);//当购物车为空时

        //实例化ShopCartAdapter
        shopCartAdapter = new ShopCartAdapter(activity, marketAndGoodsList, number, listPosition, map);
        lvCart.setAdapter(shopCartAdapter);
        //实现CartAdapterInterface接口
        shopCartAdapter.setCartAdapterInterface(new ShopCartAdapter.CartAdapterInterface() {

            @Override
            public void alterObjList(List<Object> marketAndGoodsList) {
                //修改购物车产品数量
                if (!marketAndGoodsList.isEmpty()) {
                    List<AlterGoodsNumReq> goodsNumList = new ArrayList<AlterGoodsNumReq>();
                    for (int i = 0; i < marketAndGoodsList.size(); i++) {
                        //遍历集合如果是产品就取出修改后的产品数量和对应的购物车id
                        if (marketAndGoodsList.get(i) instanceof CartGoods) {
                            //封装请求的request
                            AlterGoodsNumReq alterGoodsNumReq = new AlterGoodsNumReq(((CartGoods) marketAndGoodsList.get(i)).getId(), ((CartGoods) marketAndGoodsList.get(i)).getQty());
                            //将请求参数添加到集合
                            goodsNumList.add(alterGoodsNumReq);
                        }
                    }
                    shopCartModel.alterGoodsNum(goodsNumList);
                }
            }

            @Override
            public void selectorGoodsNum(List<CartGoods> selectorGoods) {
                //设置 全选/取消全选 两种状态
                if (choGoodsList.size() == selectorGoods.size()) {
                    cbSettleAllCho.setChecked(true);
                    cbDelAllCho.setChecked(true);
                } else {
                    cbSettleAllCho.setChecked(false);
                    cbDelAllCho.setChecked(false);
                }
                seletorGoods = selectorGoods;
                LogUtils.error(MenuSopCartFragment.class, "长度====" + selectorGoods.size());
                //初始化购物车价格
                BigDecimal bdNum = new BigDecimal(Double.valueOf(0.00));
                //设置购物车“去结算”的数量
                int selNum = 0;
                //如果购物车的选中的CartGoods的List的size不为0则计算价格
                if (selectorGoods.size() != 0) {
                    for (CartGoods goods : selectorGoods) {
                        selNum = goods.getQty() + selNum;
                        BigDecimal b = new BigDecimal(Byte.valueOf(goods.getQty()));
                        bdNum = ((goods.getRetailPrice()).multiply(b)).add(bdNum);
                    }
                    tvSettleNum.setText(String.valueOf(selNum));

                    String[] priceStr = bdNum.toString().split("\\.");
                    tvBigNum.setText(priceStr[0]);
                    tvSmallNum.setText("." + priceStr[1]);
                    //否则设置为默认价格
                } else {
                    tvSettleNum.setText(String.valueOf(0));
                    tvBigNum.setText("0");
                    tvSmallNum.setText(".00");
                }
            }
        });
    }

    /**
     * 购物车去结算全选监听
     */
    public void  ClearingCheckAll() {
        if (!marketAndGoodsList.isEmpty() && marketAndGoodsList != null) {
            //选中  遍历将所有的checkBox设置为选中
            if (cbSettleAllCho.isChecked()) {
                       /* BigDecimal bdNum = new BigDecimal(Double.valueOf(0.00));
                        byte selNum = 0;*/
                cbDelAllCho.setChecked(true);
                for (int i = 0; i < marketAndGoodsList.size(); i++) {
                            /*if (marketAndGoodsList.get(i) instanceof CartGoods) {
                                BigDecimal b = new BigDecimal(Byte.valueOf(((CartGoods) marketAndGoodsList.get(i)).getQty()));
                                bdNum = ((((CartGoods) marketAndGoodsList.get(i)).getRetailPrice()).multiply(b)).add(bdNum);
                                selNum = (byte) (((CartGoods) marketAndGoodsList.get(i)).getQty() + selNum);
                            }*/
                    //将选中状态记录到map中
                    ShopCartAdapter.getIsSelected().put(i, true);
                }
                //将购物车所有产品添加到已选择list中进行价格计算
                LogUtils.error(MenuSopCartFragment.class, "selNum" + choGoodsList.size());
                shopCartAdapter.addChoGoods(choGoodsList);
            } else {
                cbDelAllCho.setChecked(false);
                //取消选中  遍历将所有的checkBox设置为未选中
                for (int i = 0; i < marketAndGoodsList.size(); i++) {
                    shopCartAdapter.getIsSelected().put(i, false);
                }
                //将核算价格的list清空
                shopCartAdapter.clearChooseGoods();
                       /* tvSettleNum.setText(String.valueOf(0));   
                        tvBigNum.setText("0");
                        tvSmallNum.setText(".00");*/
            }

            shopCartAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 购物车全选
     */
    public void compileCheckAll(){
        if (!marketAndGoodsList.isEmpty() && marketAndGoodsList != null) {
            //选中  遍历将所有的checkBox设置为选中
            if (cbDelAllCho.isChecked()) {
                for (int i = 0; i < marketAndGoodsList.size(); i++) {
                    //将选中状态记录到map中
                    ShopCartAdapter.getIsSelected().put(i, true);
                }
                //将购物车所有产品添加到已选择list中进行价格计算
                shopCartAdapter.addChoGoods(choGoodsList);
            } else {
                //取消选中  遍历将所有的checkBox设置为未选中
                for (int i = 0; i < marketAndGoodsList.size(); i++) {
                    shopCartAdapter.getIsSelected().put(i, false);
                }
                //将核算价格的list清空
                shopCartAdapter.clearChooseGoods();
            }
            shopCartAdapter.notifyDataSetChanged();
        }
    }
}
