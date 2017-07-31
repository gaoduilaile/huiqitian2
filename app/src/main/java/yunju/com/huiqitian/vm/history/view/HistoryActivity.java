package yunju.com.huiqitian.vm.history.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.ex.DbException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.db.dao.CollectDao;
import yunju.com.huiqitian.db.dao.HistoryDao;
import yunju.com.huiqitian.db.entity.Collect;
import yunju.com.huiqitian.db.entity.History;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.vm.adapter.CollectAdapter;
import yunju.com.huiqitian.vm.adapter.HistoryAdapter;
import yunju.com.huiqitian.vm.details.view.DetailsActivity;
import yunju.com.huiqitian.vm.widget.PopShowUtils;

public class HistoryActivity extends BaseActivity {

    private int a;

    /*title*/
    private LinearLayout ivTitleBack;
    private TextView tvTitle;//标题
    private TextView tvTitleShare;//清除的按钮
    private LinearLayout llNoHistory;
    private TextView tvNoHistory;



    /*显示历史记录*/
    private ListView lvHistoryCollect;

    /*dao类*/

    private HistoryDao historyDao;
    private List<History> historyList;
    private Collect collect=new Collect();

    private CollectDao collectDao;
    private List<Collect> collectList;

    /*数据的集合*/
    private  List<GoodsInfo> goodsInfo;

    /*弹框提示布局*/
    private LayoutInflater layoutInflater;

    public static HistoryActivity historyActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_history);
        historyActivity=this;
    }

    @Override
    public void initBoot() {
        historyDao = new HistoryDao();
        historyList = new ArrayList<History>();

        goodsInfo = new ArrayList<GoodsInfo>();

        collectDao = new CollectDao();
        collectList = new ArrayList<Collect>();
    }

    @Override
    public void initViews() {

        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
        tvTitleShare = findView(R.id.tv_title_share);
        tvNoHistory = findView(R.id.tv_no_history);
        llNoHistory=findView(R.id.ll_no_history);

        /*显示相关*/
        lvHistoryCollect = findView(R.id.lv_history_collect);
    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(R.string.history_title);
        tvTitleShare.setVisibility(View.VISIBLE);
        tvTitleShare.setText(R.string.history_clear);

        LogUtils.error(HistoryActivity.class,bundle.getInt(Constant.START_WHERE)+"");
        switch (bundle.getInt(Constant.START_WHERE)){
            case 1:
                a=1;
                /*浏览记录*/
                try {
                    historyList =  historyDao.select();
                    if(historyList!=null){
                        if (historyList.size()>0){
                            tvNoHistory.setVisibility(View.GONE);
                            llNoHistory.setVisibility(View.GONE);
                            lvHistoryCollect.setVisibility(View.VISIBLE);
                            lvHistoryCollect.setAdapter(new HistoryAdapter(activity,historyList));
                        }else {
                            lvHistoryCollect.setVisibility(View.GONE);
                            tvNoHistory.setVisibility(View.VISIBLE);
                            llNoHistory.setVisibility(View.VISIBLE);
                        }
                    }else {
                        lvHistoryCollect.setVisibility(View.GONE);
                        tvNoHistory.setVisibility(View.VISIBLE);
                        llNoHistory.setVisibility(View.VISIBLE);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                a=0;
                /*收藏*/
                tvTitle.setText(R.string.collect_title);
                tvNoHistory.setText("没有收藏记录");
                try {
                    collectList =  collectDao.select();
                    if(collectList!=null){
                        if (collectList.size()>0){
                            tvNoHistory.setVisibility(View.GONE);
                            llNoHistory.setVisibility(View.GONE);
                            lvHistoryCollect.setVisibility(View.VISIBLE);
                            lvHistoryCollect.setAdapter(new CollectAdapter(activity,collectList));
                        }else {
                            lvHistoryCollect.setVisibility(View.GONE);
                            tvNoHistory.setVisibility(View.VISIBLE);
                            llNoHistory.setVisibility(View.VISIBLE);
                        }
                    }else {
                        lvHistoryCollect.setVisibility(View.GONE);
                        tvNoHistory.setVisibility(View.VISIBLE);
                        llNoHistory.setVisibility(View.VISIBLE);
                    }

                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void initEvents() {

        /*返回键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*清除按钮*/
        tvTitleShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a==1){
                    View view=View.inflate(activity,R.layout.pop_cart_alldel,null);
                    PopShowUtils.showPopwindow(view, activity.getWindow(), tvTitleShare, true);
                    TextView pop_affirm_del = (TextView) view.findViewById(R.id.pop_affirm_del);
                    TextView pop_cancel_del = (TextView) view.findViewById(R.id.pop_cancel_del);
                    TextView pop_PromptNum_del = (TextView) view.findViewById(R.id.pop_PromptNum_del);

                    pop_PromptNum_del.setText("亲，确定要删除这这些历史足迹吗？");

                    pop_affirm_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                historyDao.delete();
                                tvNoHistory.setVisibility(View.VISIBLE);
                                lvHistoryCollect.setVisibility(View.GONE);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            PopShowUtils.closePopWindowPage();
                        }
                    });
                    pop_cancel_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopShowUtils.closePopWindowPage();
                        }
                    });

                }else if(a==0){
                    View view=View.inflate(activity,R.layout.pop_cart_alldel,null);
                    PopShowUtils.showPopwindow(view, activity.getWindow(), tvTitleShare, true);
                    TextView pop_affirm_del = (TextView) view.findViewById(R.id.pop_affirm_del);
                    TextView pop_cancel_del = (TextView) view.findViewById(R.id.pop_cancel_del);
                    TextView pop_PromptNum_del = (TextView) view.findViewById(R.id.pop_PromptNum_del);

                    pop_PromptNum_del.setText("亲，确定要删除这这些收藏吗？");

                    pop_affirm_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                collectDao.delete();
                                tvNoHistory.setVisibility(View.VISIBLE);
                                lvHistoryCollect.setVisibility(View.GONE);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            PopShowUtils.closePopWindowPage();
                        }
                    });
                    pop_cancel_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopShowUtils.closePopWindowPage();
                        }
                    });

                }
                }

        });

        /*历史记录的查询*/
        lvHistoryCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(a==1){
                    start(position,historyList);
                }else if(a==0){
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.START_NEXT_KEY, Constant.START_BY_COLLECT);
                    bundle.putInt(Constant.ITEM_POSITION, position);
                    bundle.putSerializable(Constant.GOODS_PROP, (Serializable) collectList);
                    startActivity(DetailsActivity.class, bundle);
                }

            }
        });
    }


    /**
     * 页面跳转
     */
    public void start(int position, List<History> histories) {

        Bundle bundle = new Bundle();
        bundle.putInt(Constant.START_NEXT_KEY, Constant.START_BY_HISTORY);
        bundle.putInt(Constant.ITEM_POSITION, position);
        bundle.putSerializable(Constant.GOODS_PROP, (Serializable) histories);
        startActivity(DetailsActivity.class, bundle);
    }
}
