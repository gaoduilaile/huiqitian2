package yunju.com.huiqitian.vm.find.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.GoodsResultAdapter;
import yunju.com.huiqitian.vm.details.view.DetailsActivity;
import yunju.com.huiqitian.vm.find.model.FindModel;
import yunju.com.huiqitian.vm.menu.model.MenuModel;
import yunju.com.huiqitian.vm.menu.view.MainMenu;
import yunju.com.huiqitian.vm.widget.MyGridView;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;

/**
 * Created by liuGang on 2016/7/18 0018.
 */
public class FindResultActivity extends BaseActivity implements FindModel.GoodsInterface {

    /*model*/
    private FindModel findModel;
    private MenuModel menuModel;

    /*title*/
    private LinearLayout ivResultBack;//返回键
    private LinearLayout ivResultCart; //购物车

    /*按键*/
    private LinearLayout lyResultNum;//销量
    private LinearLayout lyResultSend;//配送距离
    private LinearLayout lyResultEvaluate;//评价
    private LinearLayout lyResultMoney;//起送

    /*输入框*/
    private EditText etResultSearch;

    /*是否是第一次点击*/
    private boolean numFirst = true;//销量第一次点击
    private boolean sendFirst = true;//配送距离第一次点击
    private boolean evaluateFirst = true;//评价第一次点击
    private boolean moneyFirst = true;//起送第一次点击

    /*图片切换*/
    private ImageView imgResultNum;//销量
    private ImageView imgResultSend;//配送距离
    private ImageView imgResultEvaluate;//评价
    private ImageView imgResultMoney;//起送

    /*数据显示的listview*/
    private MyGridView pvFindGoods;
    /*加载数据的adapter*/
    private GoodsResultAdapter adapter;

    /*购物车数量通知*/
    // private  TextView tvCount;
    /*购物车商品的数量*/
    private int count = 1;

    /*商品的resp*/
    private String goodsResp;

    /*加载数据相关*/
    private int pager = 1;//当前页
    private String name;//商品名称

    private PullToRefreshLayout pullRefreshResult;
    private int marketId;
    private int tag;
    private TextView tvResultNum;
    private TextView tvResultSend;
    private TextView tvResultEvaluate;
    private TextView tvResultMoney;
    private int num0=10;
    private int num=10;
    private int num1=10;
    private int num2=10;
    private int num3=10;
    private int num4=10;
    private int num5=10;
    private int num6=10;
    private int num7=10;
    private int num8=10;
    private int num9=10;
    private int num10=10;
    private int num11=10;
    private int num12=10;
    private int num13=10;
    private int num14=10;
    private int num15=10;
    private int num16=10;
    public static FindResultActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_findresult);
        instance = this;
    }

    @Override
    public void initBoot() {
        findModel = new FindModel(activity);
        menuModel=new MenuModel(activity);
    }

    @Override
    public void initViews() {
        /*title*/
        ivResultBack = findView(R.id.iv_result_back);
        ivResultCart = findView(R.id.iv_result_cart);

        /*按键*/
        lyResultNum = findView(R.id.ly_result_num);
        lyResultSend = findView(R.id.ly_result_send);
        lyResultEvaluate = findView(R.id.ly_result_evaluate);
        lyResultMoney = findView(R.id.ly_result_money);

        /*文字*/
        tvResultNum = findView(R.id.tv_result_num);
        tvResultNum.setTextColor(getResources().getColor(R.color.color_e51c24));
        tvResultSend = findView(R.id.tv_result_send);
        tvResultEvaluate = findView(R.id.tv_result_evaluate);
        tvResultMoney = findView(R.id.tv_result_money);

        /*搜索*/
        etResultSearch = findView(R.id.et_result_search);

        /*图片切换*/
        imgResultNum = findView(R.id.img_result_num);
        imgResultSend = findView(R.id.img_result_send);
        imgResultEvaluate = findView(R.id.img_result_evaluate);
        imgResultMoney = findView(R.id.img_result_money);

        /*购物车图片显示*/
        //tvCount = findView(R.id.tv_count);

        /*显示数据控件*/
        pvFindGoods = findView(R.id.pv_find_goods);

        /*刷新和加载更多*/
        pullRefreshResult = findView(R.id.pull_refresh_result);

       /* pvFindGoods.canPullUp();
        pvFindGoods.canPullDown();*/
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            tag = bundle.getInt("tag");
            /*tag 如果是1 标示从主页搜索过来的
            * 如果tag是2.表示从附近超市过来的
            * */
            if (tag == 1) {
                //取出resp
                goodsResp = bundle.getString(Constant.GOODS_INFO);
                //取出商品名
                name = bundle.getString(Constant.GOODS_NAME);
                LogUtils.error(FindResultActivity.class, "商品的名称是：" + name + " ,tag " + tag+" ,ressss");
        /*设置显示*/
                setGoodsView(goodsResp);
        /*EditText默认失去焦点*/
                //  etResultSearch.clearFocus();
                // etResultSearch.requestFocus();
                findModel.searchGoods(pager, 10, name, "qty", "desc");//销量降序
                pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                        MyUtils.setRefresh(pullToRefreshLayout);
                    }

                    @Override
                    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                        num0 += 10;
                        findModel.searchGoods(1, num0, name, "qty", "desc");//销量倒叙
                        MyUtils.setLoadMore(pullToRefreshLayout);

                    }
                });
            } else if (tag == 2) {
                /*取出输入框的内容,商品名称*/
                name = bundle.getString("editData");
                marketId = bundle.getInt("marketId");
                LogUtils.error(FindResultActivity.class, "商品的名称是：" + name + " , 超市的编号是： " + marketId);
                /*请求接口*/
                findModel.nearSearchGoods(pager, 10, name, "qty", "asc", marketId);
                pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                        MyUtils.setRefresh(pullToRefreshLayout);
                    }

                    @Override
                    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                        num += 10;
                        findModel.nearSearchGoods(pager, num, name, "qty", "asc", marketId);
                        MyUtils.setLoadMore(pullToRefreshLayout);

                    }
                });
            }
        }


    }


    @Override
    public void initEvents() {


        /*软键盘的监听事件*/
        editListener();


        /*返回键*/
        ivResultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* *//*加入购物车*//*
        adapter.setClickInterface(new GoodsResultAdapter.ClickInterface() {
            @Override
            public void btnClick(int id, byte type) {
                findModel.addCart(id, type);//添加购物车
            }
        });
*/
        /*点击获得详情*/
        pvFindGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.START_NEXT_KEY, Constant.START_BY_FIND);
                bundle.putInt(Constant.ITEM_POSITION, position);
                startActivity(DetailsActivity.class, bundle);
            }
        });

        /*销量*/
        lyResultNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (numFirst) {
                    selectImg();//设置其他图片都是向下的
                    imgResultNum.setImageResource(R.mipmap.img_find_red_up); //升序
                    if (tag == 1) {
                        findModel.searchGoods(pager, 10, name, "qty", "asc");//销量升序
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num1+=10;
                                findModel.searchGoods(pager, num1, name, "qty", "asc");//销量升序
                                MyUtils.setLoadMore(pullToRefreshLayout);

                            }
                        });
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(pager, 10, name, "qty", "asc", marketId);
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num2+=10;
                                findModel.nearSearchGoods(pager, num2, name, "qty", "asc", marketId);
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });

                    }
                } else {
                    if (tag == 1) {
                        findModel.searchGoods(pager, 10, name, "qty", "desc");//销量降序
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num3+=10;
                                findModel.searchGoods(pager, num3, name, "qty", "desc");//销量降序
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(pager, 10, name, "qty", "desc", marketId);
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num4+=10;
                                findModel.nearSearchGoods(pager, num4, name, "qty", "desc", marketId);
                                MyUtils.setLoadMore(pullToRefreshLayout);

                            }
                        });
                    }
                    selectImg();//设置其他图片都是向下的
                    imgResultNum.setImageResource(R.mipmap.img_find_red_down); //降序
                }
                tvResultNum.setTextColor(getResources().getColor(R.color.color_e51c24));
                numFirst = !numFirst;
                sendFirst = true;
                evaluateFirst = true;
                moneyFirst = true;
            }
        });

        /*配送距离*/
        lyResultSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendFirst) {
                    selectImg();
                    imgResultSend.setImageResource(R.mipmap.img_find_red_up);
                    if (tag == 1) {
                        setNewDate(pager, 10, name, "distance", "asc");
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num5+=10;
                                setNewDate(pager, num5, name, "distance", "asc");
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(pager, 10, name, "distance", "asc", marketId);
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num6+=10;
                                findModel.nearSearchGoods(pager, num6, name, "distance", "asc", marketId);
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    }

                } else {

                    if (tag == 1) {
                        setNewDate(pager, 10, name, "distance", "desc");
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num7+=10;
                                setNewDate(pager, num7, name, "distance", "desc");
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(pager, 10, name, "distance", "desc", marketId);
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num8+=10;
                                findModel.nearSearchGoods(pager, num8, name, "distance", "desc", marketId);
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    }
                    selectImg();
                    imgResultSend.setImageResource(R.mipmap.img_find_red_down);
                }
                tvResultSend.setTextColor(getResources().getColor(R.color.color_e51c24));
                sendFirst = !sendFirst;
                numFirst = true;
                evaluateFirst = true;
                moneyFirst = true;
            }
        });

        /*评价*/
        lyResultEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evaluateFirst) {

                    if (tag == 1) {
                        setNewDate(pager, 10, name, "score", "asc");//降序
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num9+=10;
                                setNewDate(pager, num9, name, "score", "asc");//降序
                                MyUtils.setLoadMore(pullToRefreshLayout);

                            }
                        });
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(pager, 10, name, "score", "asc", marketId);
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num10+=10;
                                findModel.nearSearchGoods(pager, num10, name, "score", "asc", marketId);
                                MyUtils.setLoadMore(pullToRefreshLayout);

                            }
                        });
                    }
                    selectImg();
                    imgResultEvaluate.setImageResource(R.mipmap.img_find_red_up);
                } else {
                    if (tag == 1) {
                        setNewDate(pager, 10, name, "score", "desc");//降序
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num11+=10;
                                setNewDate(pager, num11, name, "score", "desc");//降序
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(pager, 10, name, "score", "desc", marketId);
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);

                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num12+=10;
                                findModel.nearSearchGoods(pager, num12, name, "score", "desc", marketId);
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    }
                    selectImg();
                    imgResultEvaluate.setImageResource(R.mipmap.img_find_red_down);
                }
                tvResultEvaluate.setTextColor(getResources().getColor(R.color.color_e51c24));
                evaluateFirst = !evaluateFirst;
                numFirst = true;
                sendFirst = true;
                moneyFirst = true;
            }
        });

        /*起送*/
        lyResultMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moneyFirst) {
                    if (tag == 1) {
                        setNewDate(pager, 10, name, "price", "asc");//降序
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num13+=10;
                                setNewDate(pager, num13, name, "price", "asc");//降序
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(pager, 10, name, "price", "asc", marketId);
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num14+=10;
                                findModel.nearSearchGoods(pager, num14, name, "price", "asc", marketId);
                                MyUtils.setLoadMore(pullToRefreshLayout);

                            }
                        });
                    }
                    selectImg();
                    imgResultMoney.setImageResource(R.mipmap.img_find_red_up);
                } else {
                    if (tag == 1) {
                        setNewDate(pager, 10, name, "price", "desc");//降序
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);
                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num15+=10;
                                setNewDate(pager, num15, name, "price", "desc");//降序
                                MyUtils.setLoadMore(pullToRefreshLayout);

                            }
                        });
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(pager, 10, name, "price", "desc", marketId);
                        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                                MyUtils.setRefresh(pullToRefreshLayout);

                            }

                            @Override
                            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                                num16+=10;
                                findModel.nearSearchGoods(pager, num16, name, "price", "desc", marketId);
                                MyUtils.setLoadMore(pullToRefreshLayout);
                            }
                        });
                    }
                    selectImg();
                    imgResultMoney.setImageResource(R.mipmap.img_find_red_down);
                }
                tvResultMoney.setTextColor(getResources().getColor(R.color.color_e51c24));
                moneyFirst = !moneyFirst;
                numFirst = true;
                sendFirst = true;
                evaluateFirst = true;
            }
        });

        /*查看购物车*/
        ivResultCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindResultActivity.this, MainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constant.SHOPCART, 3);
                startActivity(intent);
            }
        });

        /*输入框搜索*/
        etResultSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    if (tag == 1) {
                        findModel.searchGoods(1, 20, etResultSearch.getText().toString(), "qty", "desc");//销量倒叙
                    } else if (tag == 2) {
                        findModel.nearSearchGoods(1, 20, etResultSearch.getText().toString(), "qty", "desc", marketId);
                    }
                }
                return false;
            }
        });
    }



    //搜索到商品
    @Override
    public void goodsInfo(String resp, String name) {
        setGoodsView(resp);
    }

    //没有商品
    @Override
    public void noGoods(String msg) {

    }

    //添加成功
    @Override
    public void addSuccess() {
        // tvCount.setVisibility(View.VISIBLE);
        //tvCount.setText(count+"");
        count++;//数量加1
    }

    //添加失败
    @Override
    public void addFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void LocateError(String mes) {
        menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
    }

    /**
     * @param curpage   当前页
     * @param rp        每页多少条
     * @param name      商品名称
     * @param storname  排序字段 (销量："qty",口碑："score",价格："price",距离："distance")
     * @param sortorder 排序方式（升序："asc" ，倒序："desc"）
     */
    private void setNewDate(int curpage, int rp, String name, String storname, String sortorder) {
        findModel.searchGoods(curpage, rp, name, storname, sortorder);
    }

    /**
     * 设置图片位向下，灰色
     */
    private void selectImg() {
        imgResultNum.setImageResource(R.mipmap.img_find_default_down);
        imgResultSend.setImageResource(R.mipmap.img_find_default_down);
        imgResultEvaluate.setImageResource(R.mipmap.img_find_default_down);
        imgResultMoney.setImageResource(R.mipmap.img_find_default_down);

        /*设置文字的颜色*/
        tvResultNum.setTextColor(getResources().getColor(R.color.color_999999));
        tvResultEvaluate.setTextColor(getResources().getColor(R.color.color_999999));
        tvResultSend.setTextColor(getResources().getColor(R.color.color_999999));
        tvResultMoney.setTextColor(getResources().getColor(R.color.color_999999));

    }

    /**
     * 设置显示
     *
     * @param resp
     */
    private void setGoodsView(String resp) {
        SearchResp searchResp = parseObject(resp, SearchResp.class);
        List<GoodsInfo> goodsInfos = searchResp.getObj();
        LogUtils.error(FindResultActivity.class, "集合长度是+"+goodsInfos.size());
        adapter = new GoodsResultAdapter(activity, goodsInfos);
        pvFindGoods.setAdapter(adapter);
         /*设置缓存*/
        AppApplication.setGoodsInfos(goodsInfos);
    }

    /**
     * Created by gaoqiong on 2016/9/7 0007 13:09
     * <p>
     * editText的各种事件
     */
    private void editListener() {

        etResultSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {//点击软键盘完成控件时触发的行为
                    //关闭光标并且关闭软键盘
                    etResultSearch.setCursorVisible(false);
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;//消费掉该行为
            }
        });

        etResultSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //获取到焦点显示光标
                etResultSearch.setCursorVisible(true);
                return false;
            }
        });

    }
}
