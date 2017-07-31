package yunju.com.huiqitian.vm.find.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.GoodsResultAdapter;
import yunju.com.huiqitian.vm.details.view.DetailsActivity;
import yunju.com.huiqitian.vm.find.model.FoodStuffModel;
import yunju.com.huiqitian.vm.menu.model.MenuModel;
import yunju.com.huiqitian.vm.menu.view.MainMenu;
import yunju.com.huiqitian.vm.widget.MyGridView;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;

public class FoodstuffActivity extends BaseActivity implements FoodStuffModel.StuffInterface {

    /*title*/
    private LinearLayout llStuffBack;//返回键
    private ImageView ivStuffCart;//购物车

    /*按键*/
    private LinearLayout lyStuffNum;//销量
    private LinearLayout lyStuffSend;//配送距离
    private LinearLayout lyStuffEvaluate;//评价
    private LinearLayout lyStuffMoney;//起送

    /*图片切换*/
    private ImageView imgStuffNum;//销量
    private ImageView imgStuffSend;//配送距离
    private ImageView imgStuffEvaluate;//评价
    private ImageView imgStuffMoney;//起送

    /*图片旁边的文字效果*/
    private TextView tvStuffNum;//销量
    private TextView tvStuffSend;//配送距离
    private TextView tvStuffEvaluate;//评价
    private TextView tvStuffMoney;//起送


    /*点击排序的标示*/
    private boolean isUpSales = false;
    private boolean isUpCount = true;
    private boolean isUpPrice = true;
    private boolean isUpScreen = true;

    /*数据显示的listview*/
    private MyGridView pvStuffGoods;
    /*加载数据的adapter*/
    private GoodsResultAdapter adapter;

    /*购物车数量通知*/
    private TextView tvCount;
    /*购物车商品的数量*/
    private int count = 1;

    /*商品的resp*/
    private String goodsResp;

    /*加载数据相关*/
    private int pager = 1;//当前页
    private int num = 10;
    private int num1 = 10;
    private int num2 = 10;
    private int num3 = 10;
    private int num4 = 10;
    private int num5 = 10;
    private int num6 = 10;
    private int num7 = 10;
    private int num8 = 10;
    private int num9 = 10;

    private int saveCurpage;//当前页数
    private int saveRp;//每页多少条
    private String saveSortname;//排序字段
    private String saveSortorder;//排序方式

    public static FoodstuffActivity foodstuffActivity = null;


    /*数据的model*/
    private FoodStuffModel foodStuffModel;
    private MenuModel menuModel;
    private PullToRefreshLayout pullRefreshResult;
    private List<GoodsInfo> goodsInfos;
    private RelativeLayout relativeLayout;
    private LinearLayout tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_foodstuff);
        foodstuffActivity=this;
    }

    @Override
    public void initBoot() {
        foodStuffModel = new FoodStuffModel(activity);
        menuModel=new MenuModel(activity);
    }

    @Override
    public void initViews() {

         /*title*/
        llStuffBack = findView(R.id.ll_stuff_back);
        ivStuffCart = findView(R.id.iv_stuff_cart);
        relativeLayout = findView(R.id.relativeLayout);

        /*无数据页面*/
        tvNoData = findView(R.id.tv_no_data);

        /*按键*/
        lyStuffNum = findView(R.id.ly_stuff_num);
        lyStuffSend = findView(R.id.ly_stuff_send);
        lyStuffEvaluate = findView(R.id.ly_stuff_evaluate);
        lyStuffMoney = findView(R.id.ly_stuff_money);

         /*图片切换*/
        imgStuffNum = findView(R.id.img_stuff_num);
        imgStuffSend = findView(R.id.img_stuff_send);
        imgStuffEvaluate = findView(R.id.img_stuff_evaluate);
        imgStuffMoney = findView(R.id.img_stuff_money);

        /*图片旁边的文字*/
        tvStuffNum = findView(R.id.tv_stuff_num);
        tvStuffSend = findView(R.id.tv_stuff_send);
        tvStuffEvaluate = findView(R.id.tv_stuff_evaluate);
        tvStuffMoney = findView(R.id.tv_stuff_money);

        pvStuffGoods = findView(R.id.pv_stuff_goods);
        pullRefreshResult = findView(R.id.pull_refresh_result);

        /*刚进来的时候默认选择的是第一的页面*/
        imgStuffNum.setImageResource(R.mipmap.img_find_red_down); //展开
        tvStuffNum.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_e51c23));
    }

    @Override
    public void initData(Bundle bundle) {
        foodStuffModel.stuffGoods(pager, 10, "qty", "asc");
        saveReq(pager, 10, "qty", "asc");

        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                foodStuffModel.stuffGoods(pager, 10, "qty", "asc");
                saveReq(pager, 10, "qty", "asc");
                MyUtils.setRefresh(pullToRefreshLayout);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                num += 10;
                foodStuffModel.stuffGoods(pager, num, "qty", "asc");
                saveReq(pager, num, "qty", "asc");
                MyUtils.setLoadMore(pullToRefreshLayout);
            }
        });
    }

    @Override
    public void initEvents() {

        /*返回键*/
        llStuffBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*销量*/
        lyStuffNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isUpSales) {
                    /*设置其他图片都是灰色的向下，文字是黑色的*/
                    selectImg();
                    imgStuffNum.setImageResource(R.mipmap.img_find_red_down); //展开
                    foodStuffModel.stuffGoods(pager, 10, "qty", "asc");
                    saveReq(pager, 10, "qty", "asc");
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            foodStuffModel.stuffGoods(pager, 10, "qty", "asc");
                            saveReq(pager, 10, "qty", "asc");
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num1 += 10;
                            foodStuffModel.stuffGoods(pager, num1, "qty", "asc");
                            saveReq(pager, num1, "qty", "asc");
                            MyUtils.setLoadMore(pullToRefreshLayout);
                        }
                    });
                } else {
                    selectImg();
                    foodStuffModel.stuffGoods(pager, 10, "qty", "desc");
                    saveReq(pager, 10, "qty", "desc");
                    imgStuffNum.setImageResource(R.mipmap.img_find_red_up);
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            foodStuffModel.stuffGoods(pager, 10, "qty", "desc");
                            saveReq(pager, 10, "qty", "desc");
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num2 += 10;
                            foodStuffModel.stuffGoods(pager, num2, "qty", "desc");
                            saveReq(pager, num2, "qty", "desc");
                            MyUtils.setLoadMore(pullToRefreshLayout);

                        }
                    });
                }
                tvStuffNum.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_e51c23));
                isUpSales = !isUpSales;
                /*防止导航栏图片二次点击切换*/
                isUpCount = true;
                isUpPrice = true;
                isUpScreen = true;
            }
        });



        /*口碑*/
        lyStuffSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpCount) {
                    selectImg();
                    /*设置其他图片都是灰色的向下*/
                    imgStuffSend.setImageResource(R.mipmap.img_find_red_down); //升序
                    setNewDate(imgStuffSend, pager, 10, "score", "desc", R.mipmap.img_find_red_down);//降序
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            setNewDate(imgStuffSend, pager, 10, "score", "desc", R.mipmap.img_find_red_down);//降序
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num3 += 10;
                            setNewDate(imgStuffSend, pager, num3, "score", "desc", R.mipmap.img_find_red_down);//降序
                            MyUtils.setLoadMore(pullToRefreshLayout);
                        }
                    });

                } else {
                    selectImg();
                    setNewDate(imgStuffSend, pager, 10, "score", "asc", R.mipmap.img_find_default_up);//降序
                    imgStuffSend.setImageResource(R.mipmap.img_find_red_up);
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            setNewDate(imgStuffSend, pager, 10, "score", "asc", R.mipmap.img_find_default_up);//降序
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num4 += 10;
                            setNewDate(imgStuffSend, pager, num4, "score", "asc", R.mipmap.img_find_default_up);//降序
                            MyUtils.setLoadMore(pullToRefreshLayout);
                        }
                    });
                }
                tvStuffSend.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_e51c23));
                isUpCount = !isUpCount;
                isUpSales = true;
                isUpScreen = true;
                isUpPrice = true;
            }
        });


        /*价格*/
        lyStuffEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isUpPrice) {
                    /*设置其他图片都是灰色的向下*/
                    selectImg();
                    imgStuffEvaluate.setImageResource(R.mipmap.img_find_red_down); //升序
                    setNewDate(imgStuffEvaluate, pager, 10, "price", "desc", R.mipmap.img_find_red_down);//降序
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            setNewDate(imgStuffEvaluate, pager, 10, "price", "desc", R.mipmap.img_find_red_down);//降序
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num5 += 10;
                            setNewDate(imgStuffEvaluate, pager, num5, "price", "desc", R.mipmap.img_find_red_down);//降序
                            MyUtils.setLoadMore(pullToRefreshLayout);
                        }
                    });

                } else {
                    selectImg();
                    setNewDate(imgStuffEvaluate, pager, 10, "price", "asc", R.mipmap.img_find_default_up);//降序
                    imgStuffEvaluate.setImageResource(R.mipmap.img_find_red_up);
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            setNewDate(imgStuffEvaluate, pager, 10, "price", "asc", R.mipmap.img_find_default_up);//降序
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num6 += 10;
                            setNewDate(imgStuffEvaluate, pager, num6, "price", "asc", R.mipmap.img_find_default_up);//降序
                        }
                    });
                }
                tvStuffEvaluate.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_e51c23));
                isUpPrice = !isUpPrice;
                isUpCount = true;
                isUpSales = true;
                isUpScreen = true;
            }
        });
          
        

        /*最新*/
        lyStuffMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpScreen) {
                    /*设置其他图片都是灰色的向下*/
                    selectImg();
                    imgStuffMoney.setImageResource(R.mipmap.img_find_red_down); //升序
                    setNewDate(imgStuffMoney, pager, 10, "shelve", "desc", R.mipmap.img_find_red_down);//降序
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            setNewDate(imgStuffMoney, pager, 10, "shelve", "desc", R.mipmap.img_find_red_down);//降序
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num7 += 10;
                            setNewDate(imgStuffMoney, pager, num7, "shelve", "desc", R.mipmap.img_find_red_down);//降序
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }
                    });
                } else {
                    selectImg();
                    setNewDate(imgStuffMoney, pager, 10, "shelve", "asc", R.mipmap.img_find_default_up);//降序
                    imgStuffMoney.setImageResource(R.mipmap.img_find_red_up);
                    pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                            setNewDate(imgStuffMoney, pager, 10, "shelve", "asc", R.mipmap.img_find_default_up);//降序
                            MyUtils.setRefresh(pullToRefreshLayout);
                        }

                        @Override
                        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                            num8 += 10;
                            setNewDate(imgStuffMoney, pager, num8, "shelve", "asc", R.mipmap.img_find_default_up);//降序
                            MyUtils.setLoadMore(pullToRefreshLayout);
                        }
                    });
                }
                tvStuffMoney.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_e51c23));
                isUpScreen = !isUpScreen;
                isUpPrice = true;
                isUpCount = true;
                isUpSales = true;
            }
        });
             
        /*查看购物车*/
        ivStuffCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodstuffActivity.this, MainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constant.STUFF_GOODS_TO_CART, 1);
                startActivity(intent);
            }
        });


        //进入商品详情
        pvStuffGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(position, goodsInfos);
            }
        });

         /*获取不到当前位置，从新调取经纬度接口重新加载*/
        menuModel.setLocateInterface(new MenuModel.LocateInterface() {
            @Override
            public void LocateSuccess(String mes) {
                foodStuffModel.stuffGoods(saveCurpage, saveRp, saveSortname, saveSortorder);
                saveReq(saveCurpage, saveRp, saveSortname, saveSortorder);
            }

            @Override
            public void LocateFailure(String mes) {

            }
        });

    }


    /**
     * @param img       设置的图片
     * @param curpage   当前页
     * @param rp        每页多少条
     * @param storname  排序字段 (销量："qty",口碑："score",价格："price",距离："distance")
     * @param sortorder 排序方式（升序："asc" ，倒序："desc"）
     */

    private void setNewDate(ImageView img, int curpage, int rp, String storname, String sortorder, int id) {
        saveReq(curpage,rp,storname,sortorder);
        selectImg();
        foodStuffModel.stuffGoods(curpage, rp, storname, sortorder);
        img.setImageResource(id);
    }

    /**
     * 设置图片位向下，灰色
     */
    public void selectImg() {
        imgStuffNum.setImageResource(R.mipmap.img_find_default_up);
        imgStuffSend.setImageResource(R.mipmap.img_find_default_up);
        imgStuffEvaluate.setImageResource(R.mipmap.img_find_default_up);
        imgStuffMoney.setImageResource(R.mipmap.img_find_default_up);

        /*设置文字的颜色*/
        tvStuffNum.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_333333));
        tvStuffSend.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_333333));
        tvStuffEvaluate.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_333333));
        tvStuffMoney.setTextColor(FoodstuffActivity.this.getResources().getColor(R.color.color_333333));

    }


    @Override
    public void hasStuff(String resp) {
        setGoodsView(resp);
    }

    @Override
    public void noStuff(String msg) {

    }

    @Override
    public void LocateError(String mes) {
        menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
    }

    /**
     * 设置显示
     *
     * @param resp
     */
    private void setGoodsView(String resp) {
        SearchResp searchResp = parseObject(resp, SearchResp.class);
        goodsInfos = searchResp.getObj();
        if (goodsInfos.size() == 0 || goodsInfos == null) {
            tvNoData.setVisibility(View.VISIBLE);
            pvStuffGoods.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.GONE);
            pvStuffGoods.setVisibility(View.VISIBLE);
            adapter = new GoodsResultAdapter(activity, goodsInfos);
            pvStuffGoods.setAdapter(adapter);
         /*设置缓存*/
            AppApplication.setGoodsInfos(goodsInfos);
        }
    }

    /**
     * 页面跳转 到商品详情s
     */
    public void start(int position, List<GoodsInfo> goodsInfo) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.START_NEXT_KEY, Constant.START_BY_TUFF);
        bundle.putInt(Constant.ITEM_POSITION, position);
        bundle.putSerializable(Constant.GOODS_PROP, (Serializable) goodsInfo);
        startActivity(DetailsActivity.class, bundle);

    }

    /**
     * 保存调用接口的参数
     */
    public void saveReq(int curpage, int rp, String sortname, String sortorder){
        saveCurpage = curpage;
        saveRp = rp;
        saveSortname = sortname;
        saveSortorder =sortorder;
    }
}