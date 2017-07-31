package yunju.com.huiqitian.vm.classify.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.entity.ClassifyGoodsReq;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.ClassityGoodsAdapter;
import yunju.com.huiqitian.vm.classify.model.ClassifyResultModel;
import yunju.com.huiqitian.vm.details.view.DetailsActivity;
import yunju.com.huiqitian.vm.menu.model.MenuModel;
import yunju.com.huiqitian.vm.menu.view.MainMenu;
import yunju.com.huiqitian.vm.widget.MyGridView;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;

/**
 * 分类中。点击条目分类结果查询的页面
 * Created by CCTV-1 on 2016/11/28 0028.
 */
public class ClassifyResultActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ivFindBack;
    private TextView tvClassifyTitle;
    private LinearLayout ivFindSearch;
    private LinearLayout lyResultNum;
    private TextView tvResultNum;
    private ImageView imgResultNum;
    private LinearLayout lyResultSend;
    private TextView tvResultSend;
    private ImageView imgResultSend;
    private LinearLayout lyResultEvaluate;
    private TextView tvResultEvaluate;
    private ImageView imgResultEvaluate;
    private LinearLayout lyResultMoney;
    private TextView tvResultMoney;
    private ImageView imgResultMoney;
    private PullToRefreshLayout pullRefreshResult;
    private String name;
    private int goodsId;
    private ClassifyResultModel classifyResultModel;
    private ClassifyGoodsReq classifyGoodsReq;
    private List<ClassifyGoodsReq.ObjBean> list;

    private boolean isUpSales = false;
    private boolean isUpCount = true;
    private boolean isUpPrice = true;
    private boolean isUpScreen = true;

    private int num = 10;
    private int num1 = 10;
    private int num2 = 10;
    private int num3 = 10;
    private int num4 = 10;
    private int num5 = 10;
    private int num6 = 10;
    private int num7 = 10;
    private int num8 = 10;
    private MyGridView pvFindGoods;
    private LinearLayout llNoData;
    public static ClassifyResultActivity instance;

    private MenuModel menuModel;

    private int saveCurpage;
    private int saveRp;
    private String saveCortname;
    private String saveSortorder;
    private int saveCatId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_classify_result);
        instance = this;
    }

    @Override
    public void initBoot() {
        classifyResultModel = new ClassifyResultModel(activity);
        menuModel = new MenuModel(activity);
        /*获取不到当前位置，从新调取经纬度接口重新加载*/
        menuModel.setLocateInterface(new MenuModel.LocateInterface() {
            @Override
            public void LocateSuccess(String mes) {
                classifyResultModel.getClassifyGoods(saveCurpage, saveRp, saveCortname, saveSortorder, saveCatId);
            }

            @Override
            public void LocateFailure(String mes) {

            }
        });
    }

    @Override
    public void initViews() {
        ivFindBack = findView(R.id.iv_find_back);//返回图标
        tvClassifyTitle = findView(R.id.tv_classify_title);//分类的商品名称
        ivFindSearch = findView(R.id.iv_find_search);//加入购物车的图标

        lyResultNum = findView(R.id.ly_result_num);//销量的整个点击的模块
        tvResultNum = findView(R.id.tv_result_num);//销量的文字
        imgResultNum = findView(R.id.img_result_num);//销量的图标

        lyResultSend = findView(R.id.ly_result_send);//口碑的整个点击的模块
        tvResultSend = findView(R.id.tv_result_send);//口碑的文字
        imgResultSend = findView(R.id.img_result_send);//口碑的箭头

        lyResultEvaluate = findView(R.id.ly_result_evaluate);//价格的整个点击的模块
        tvResultEvaluate = findView(R.id.tv_result_evaluate);//价格的文字
        imgResultEvaluate = findView(R.id.img_result_evaluate);//价格的箭头

        lyResultMoney = findView(R.id.ly_result_money);//最新的整个点击的模块
        tvResultMoney = findView(R.id.tv_result_money);//最新的文字
        imgResultMoney = findView(R.id.img_result_money);//最新的箭头

        llNoData = findView(R.id.ll_no_data);//无数据页面

        /*下拉刷新控件*/
        pullRefreshResult = findView(R.id.pull_refresh_result);
        //GridView
        pvFindGoods = findView(R.id.pv_find_goods);

        /*设置点击事件*/
        ivFindBack.setOnClickListener(this);
        ivFindSearch.setOnClickListener(this);
        lyResultNum.setOnClickListener(this);
        lyResultSend.setOnClickListener(this);
        lyResultEvaluate.setOnClickListener(this);
        lyResultMoney.setOnClickListener(this);


        tvResultNum.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_e51c24));


    }

    @Override
    public void initData(Bundle bundle) {
        /*取出从分类模块条目中携带的数据*/
        name = bundle.getString(Constant.GOODS_NAME);
        tvClassifyTitle.setText(name);
        goodsId = bundle.getInt("goodsId");
        LogUtils.error(ClassifyResultActivity.class, "id:" + goodsId + "----" + name);
        classifyResultModel.getClassifyGoods(1, 10, "qty", "asc", goodsId);
        saveReq(1, 10, "qty", "asc", goodsId);
        setAdapter();

        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                MyUtils.setRefresh(pullToRefreshLayout);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                num += 10;
                classifyResultModel.getClassifyGoods(1, num, "qty", "asc", goodsId);
                saveReq(1, num, "qty", "asc", goodsId);
                MyUtils.setLoadMore(pullToRefreshLayout);
                setAdapter();

            }
        });
    }


    @Override
    public void initEvents() {
        /*条目的点击事件*/
        pvFindGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.START_NEXT_KEY, Constant.START_BY_CLASSIFY);
                bundle.putInt(Constant.ITEM_POSITION, position);
                bundle.putSerializable(Constant.GOODS_PROP, (Serializable) list);
                startActivity(DetailsActivity.class, bundle);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_find_back://返回
                finish();
                break;

            case R.id.iv_find_search://查看购物车
                Intent intent = new Intent(ClassifyResultActivity.this, MainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constant.SHOPCART, 3);
                startActivity(intent);
                break;

            case R.id.ly_result_num://销量
                if (isUpSales) {
                    RestoreTextColor();
                    imgResultNum.setImageResource(R.mipmap.img_find_red_down);
                    classifyResultModel.getClassifyGoods(1, 10, "qty", "asc", goodsId);
                    saveReq(1, 10, "qty", "asc", goodsId);
                    /*下拉刷新操作*/
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num1 += 10;
                            classifyResultModel.getClassifyGoods(1, num1, "qty", "asc", goodsId);
                            saveReq(1, num1, "qty", "asc", goodsId);
                            setAdapter();
                            LogUtils.error(ClassifyResultActivity.class, "销量升序---" + num1);
                            MyUtils.setLoadMore(pullToRefreshLayout);

                        }
                    });

                } else {
                    RestoreTextColor();
                    imgResultNum.setImageResource(R.mipmap.img_find_red_up);
                    classifyResultModel.getClassifyGoods(1, 10, "qty", "desc", goodsId);
                    saveReq(1, 10, "qty", "desc", goodsId);
                    /*下拉刷新操作*/
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num2 += 10;
                            classifyResultModel.getClassifyGoods(1, num2, "qty", "asc", goodsId);
                            saveReq(1, num2, "qty", "asc", goodsId);
                            setAdapter();
                            LogUtils.error(ClassifyResultActivity.class, "销量降序+++" + num2);
                            MyUtils.setLoadMore(pullToRefreshLayout);

                        }
                    });
                }
                tvResultNum.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_e51c24));
                setAdapter();
                isUpSales = !isUpSales;
                /*防止导航栏图片二次点击切换*/
                isUpCount = true;
                isUpPrice = true;
                isUpScreen = true;
                break;

            case R.id.ly_result_send://口碑

                if (isUpCount) {
                    /*设置其他图片都是灰色的向下*/
                    RestoreTextColor();
                    imgResultSend.setImageResource(R.mipmap.img_find_red_down);
                    classifyResultModel.getClassifyGoods(1, 10, "score", "asc", goodsId);
                    saveReq(1, 10, "score", "asc", goodsId);
                    /*下拉刷新操作*/
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num3 += 10;
                            classifyResultModel.getClassifyGoods(1, num3, "score", "asc", goodsId);
                            saveReq(1, num3, "score", "asc", goodsId);
                            setAdapter();
                            LogUtils.error(ClassifyResultActivity.class, "---口碑升序" + num3);
                            MyUtils.setLoadMore(pullToRefreshLayout);

                        }
                    });
                } else {
                    RestoreTextColor();
                    imgResultSend.setImageResource(R.mipmap.img_find_red_up);
                    classifyResultModel.getClassifyGoods(1, 10, "score", "desc", goodsId);
                    saveReq(1, 10, "score", "desc", goodsId);
                    /*下拉刷新操作*/
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num4 += 10;
                            classifyResultModel.getClassifyGoods(1, num4, "score", "desc", goodsId);
                            saveReq(1, num4, "score", "desc", goodsId);
                            setAdapter();
                            LogUtils.error(ClassifyResultActivity.class, "---口碑降序" + num4);
                            MyUtils.setLoadMore(pullToRefreshLayout);

                        }
                    });

                }
                tvResultSend.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_e51c24));
                setAdapter();
                isUpCount = !isUpCount;
                isUpSales = true;
                isUpScreen = true;
                isUpPrice = true;

                break;

            case R.id.ly_result_evaluate://评价

                if (isUpPrice) {
                    /*设置其他图片都是灰色的向下*/
                    RestoreTextColor();
                    imgResultEvaluate.setImageResource(R.mipmap.img_find_red_down);
                    classifyResultModel.getClassifyGoods(1, 10, "price", "asc", goodsId);
                    saveReq(1, 10, "price", "asc", goodsId);

                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num5 += 10;
                            classifyResultModel.getClassifyGoods(1, num5, "price", "asc", goodsId);
                            saveReq(1, num5, "price", "asc", goodsId);
                            setAdapter();
                            LogUtils.error(ClassifyResultActivity.class, "？？？评价升序" + num5);
                            MyUtils.setLoadMore(pullToRefreshLayout);

                        }
                    });

                } else {
                    RestoreTextColor();
                    imgResultEvaluate.setImageResource(R.mipmap.img_find_red_up);
                    classifyResultModel.getClassifyGoods(1, 10, "price", "desc", goodsId);
                    saveReq(1, 10, "price", "desc", goodsId);
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num6 += 10;
                            classifyResultModel.getClassifyGoods(1, num6, "price", "desc", goodsId);
                            saveReq(1, num6, "price", "desc", goodsId);
                            setAdapter();
                            LogUtils.error(ClassifyResultActivity.class, "？？？评价降序" + num6);
                            MyUtils.setLoadMore(pullToRefreshLayout);

                        }
                    });
                }
                tvResultEvaluate.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_e51c24));
                setAdapter();
                isUpPrice = !isUpPrice;
                isUpCount = true;
                isUpSales = true;
                isUpScreen = true;

                break;
            case R.id.ly_result_money://最新

                if (isUpScreen) {
                    /*设置其他图片都是灰色的向下*/
                    RestoreTextColor();
                    imgResultMoney.setImageResource(R.mipmap.img_find_red_down);
                    classifyResultModel.getClassifyGoods(1, 10, "shelve", "asc", goodsId);
                    saveReq(1, 10, "shelve", "asc", goodsId);

                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num7 += 10;
                            classifyResultModel.getClassifyGoods(1, num7, "shelve", "asc", goodsId);
                            saveReq(1, num7, "shelve", "asc", goodsId);
                            setAdapter();
                            LogUtils.error(ClassifyResultActivity.class, "起送--升序" + num7);
                            MyUtils.setLoadMore(pullToRefreshLayout);

                        }
                    });

                } else {
                    RestoreTextColor();
                    imgResultMoney.setImageResource(R.mipmap.img_find_red_up);
                    classifyResultModel.getClassifyGoods(1, 10, "shelve", "desc", goodsId);
                    saveReq(1, 10, "shelve", "desc", goodsId);

                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num8 += 10;
                            classifyResultModel.getClassifyGoods(1, num8, "shelve", "desc", goodsId);
                            saveReq(1, num8, "shelve", "desc", goodsId);
                            setAdapter();
                            LogUtils.error(ClassifyResultActivity.class, "起送--降序" + num8);
                            MyUtils.setLoadMore(pullToRefreshLayout);
                        }
                    });
                }
                tvResultMoney.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_e51c24));
                setAdapter();
                isUpScreen = !isUpScreen;
                isUpPrice = true;
                isUpCount = true;
                isUpSales = true;
                break;
        }

    }

    /*请求网络成功后返回的成功的接口，显示适配器*/
    private void setAdapter() {
        classifyResultModel.ClassifyInterface(new ClassifyResultModel.ClassifyInterfaceModel() {

            @Override
            public void addSuccess(String obj) {
                classifyGoodsReq = parseObject(obj, ClassifyGoodsReq.class);
                list = classifyGoodsReq.getObj();
                        /*设置适配器*/
                if (list != null && list.size() == 0) {
                    llNoData.setVisibility(View.VISIBLE);
                } else {
                    llNoData.setVisibility(View.GONE);
                    pvFindGoods.setAdapter(new ClassityGoodsAdapter(activity, list));
                }
            }

            @Override
            public void addFailure(String msg) {
                llNoData.setVisibility(View.VISIBLE);
            }

            @Override
            public void LocateError(String mes) {
                menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
            }

            @Override
            public void noMarket(String mes) {
                LogUtils.error(ClassifyResultActivity.class,"9999999999999999999999999999999999999");
                llNoData.setVisibility(View.VISIBLE);
            }
        });

    }


    /*设置顶部导航字体的颜色，和图标的状态*/
    private void RestoreTextColor() {
        //设置顶部的图片资源q全部成未选中的状态
        imgResultNum.setImageResource(R.mipmap.up);
        imgResultSend.setImageResource(R.mipmap.up);
        imgResultEvaluate.setImageResource(R.mipmap.up);
        imgResultMoney.setImageResource(R.mipmap.up);

        //设置文字的颜色
        tvResultNum.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_333333));
        tvResultSend.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_333333));
        tvResultEvaluate.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_333333));
        tvResultMoney.setTextColor(ClassifyResultActivity.this.getResources().getColor(R.color.color_333333));
    }

    /**
     * 保存之前调用接口的参数
     */
    public void saveReq(int curpage, int rp, String sortname, String sortorder, int catId) {
        saveCurpage = curpage;
        saveRp = rp;
        saveCortname = sortname;
        saveSortorder = sortorder;
        saveCatId = catId;
    }
}
