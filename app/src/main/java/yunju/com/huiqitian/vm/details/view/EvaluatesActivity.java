package yunju.com.huiqitian.vm.details.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.http.entity.EvalTextResp;
import yunju.com.huiqitian.http.entity.GoodEvalResp;
import yunju.com.huiqitian.vm.adapter.DetailEvaluateAdapter;
import yunju.com.huiqitian.vm.details.model.EvaluatesModel;
import yunju.com.huiqitian.vm.widget.MyListView;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;
import yunju.com.huiqitian.vm.widget.TagCloudView;

public class EvaluatesActivity extends BaseActivity implements EvaluatesModel.EvalTextInterface ,PullToRefreshLayout.OnRefreshListener{
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private ImageView ivTitleShare;//分享

    /*所有级别评价描述和数量*/
    private TextView tvEvaluate0;
    private TextView tvallEvaluateTotal0;
    private TextView tvEvaluate1;
    private TextView tvallEvaluateTotal1;
    private TextView tvEvaluate2;
    private TextView tvallEvaluateTotal2;
    private TextView tvEvaluate3;
    private TextView tvallEvaluateTotal3;
    private RelativeLayout rlTitleOverEvaluate;//全部评论
    private RelativeLayout rlTitleGoodEvaluate;//好评
    private RelativeLayout rlTitleCentreEvaluate;//中评
    private RelativeLayout rlTitleDifferEvaluate;//差评

    /*显示评价类容*/
    private MyListView lvEvaluate;
    /*显示暂无评论*/
    private TextView tvNoEval;

    /*下拉刷新*/
    private PullToRefreshLayout pullToRefreshLayout;
    private LinearLayout llScrollViewSon;
    private boolean hasAnimation = false;

    /*x详情页传递过来的数据*/
    private int id;
    private byte type;
    private GoodEvalResp goodEvalResp;

    /*区分全部评价、好评、中评、差评四种评价状态*/
    private int evalType=0;//0:全部评价  1:好评  2:中评  3:差评
    /*加载的数据的数量*/
    private int num=0;

    /*云标签*/
    private TagCloudView normalTap;
    private List<String> goodTag = new ArrayList<>();//整个标签存放集合
    private List<Integer> total = new ArrayList<>();//整个标签数量集合

    /*数据model*/
    private EvaluatesModel evaluatesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_evaluate2);
    }

    @Override
    public void initBoot() {
        evaluatesModel = new EvaluatesModel(activity);
    }

    @Override
    public void initViews() {

        pullToRefreshLayout = findView(R.id.refresh_view);

          /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
        ivTitleShare = findView(R.id.iv_title_share);

         /*所有级别评价描述和数量*/
        tvEvaluate0 = findView(R.id.tv_evaluate0);
        tvallEvaluateTotal0 = findView(R.id.tv_all_evaluate_total0);
        tvEvaluate1 = findView(R.id.tv_evaluate1);
        tvallEvaluateTotal1 = findView(R.id.tv_all_evaluate_total1);
        tvEvaluate2 = findView(R.id.tv_evaluate2);
        tvallEvaluateTotal2 = findView(R.id.tv_all_evaluate_total2);
        tvEvaluate3 = findView(R.id.tv_evaluate3);
        tvallEvaluateTotal3 = findView(R.id.tv_all_evaluate_total3);
        rlTitleOverEvaluate = findView(R.id.rl_title_over_evaluate);
        rlTitleGoodEvaluate = findView(R.id.rl_title_good_evaluate);
        rlTitleCentreEvaluate = findView(R.id.rl_title_centre_evaluate);
        rlTitleDifferEvaluate = findView(R.id.rl_title_differ_evaluate);
        /*评价类容*/
        lvEvaluate = findView(R.id.lv_evaluate);
        /*暂无评论*/
        tvNoEval = findView(R.id.tv_no_eval);
        /*云标签*/
        normalTap = findView(R.id.normalTag);
        llScrollViewSon = findView(R.id.ll_scrollView_son);
    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText("评价");
        id = (int) bundle.get("id");
        type = (byte) bundle.get("type");
        goodEvalResp = (GoodEvalResp) bundle.getSerializable("list");

        SetTextColorAndSelect(tvEvaluate0, tvallEvaluateTotal0);
        /*全部评价*/
        tvEvaluate0.setText(goodEvalResp.getObj().getLevelList().get(0).getEvalDesc());
        if (goodEvalResp.getObj().getLevelList().get(0).getTotal() > 999) {
            tvallEvaluateTotal0.setText("999+");
        } else {
            tvallEvaluateTotal0.setText(goodEvalResp.getObj().getLevelList().get(0).getTotal() + "");
        }

        /*好评*/
        tvEvaluate1.setText(goodEvalResp.getObj().getLevelList().get(1).getEvalDesc());
        if (goodEvalResp.getObj().getLevelList().get(1).getTotal() > 999) {
            tvallEvaluateTotal1.setText("999+");
        } else {
            tvallEvaluateTotal1.setText(goodEvalResp.getObj().getLevelList().get(1).getTotal() + "");
        }

        /*中评*/
        tvEvaluate2.setText(goodEvalResp.getObj().getLevelList().get(2).getEvalDesc());
        if (goodEvalResp.getObj().getLevelList().get(2).getTotal() > 999) {
            tvallEvaluateTotal2.setText("999+");
        } else {
            tvallEvaluateTotal2.setText(goodEvalResp.getObj().getLevelList().get(2).getTotal() + "");
        }

        /*差评*/
        tvEvaluate3.setText(goodEvalResp.getObj().getLevelList().get(3).getEvalDesc());
        if (goodEvalResp.getObj().getLevelList().get(3).getTotal() > 999) {
            tvallEvaluateTotal3.setText("999+");
        } else {
            tvallEvaluateTotal3.setText(goodEvalResp.getObj().getLevelList().get(3).getTotal() + "");
        }

         /*评价标签*/
        for (int i = 0; i < goodEvalResp.getObj().getTagList().size(); i++) {
            goodTag.add(goodEvalResp.getObj().getTagList().get(i).getTag());
            total.add(goodEvalResp.getObj().getTagList().get(i).getTotal());
        }
        normalTap.setCommentTags(goodTag, total);

        lvEvaluate.setAdapter(new DetailEvaluateAdapter(activity, goodEvalResp.getObj().getEvalList()));
        num=num+10;
        if (hasAnimation) {
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }


    @Override
    public void initEvents() {
          /*返回键响应事件*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*全部评价*/
        rlTitleOverEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestoreIconSelect();
                RestoreTextColor();
                SetTextColorAndSelect(tvEvaluate0, tvallEvaluateTotal0);
                evaluatesModel.EvaluateList(id, type, 1, 10, goodEvalResp.getObj().getLevelList().get(0).getEvalLevelId());
                tvNoEval.setVisibility(View.GONE);
                lvEvaluate.setVisibility(View.VISIBLE);
                evalType=0;
                num=0;
            }
        });
        /*好评*/
        rlTitleGoodEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestoreIconSelect();
                RestoreTextColor();
                SetTextColorAndSelect(tvEvaluate1, tvallEvaluateTotal1);
                evaluatesModel.EvaluateList(id, type, 1, 10, goodEvalResp.getObj().getLevelList().get(1).getEvalLevelId());
                tvNoEval.setVisibility(View.GONE);
                lvEvaluate.setVisibility(View.VISIBLE);
                evalType=1;
                num=0;
            }
        });
        /*中评*/
        rlTitleCentreEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestoreIconSelect();
                RestoreTextColor();
                SetTextColorAndSelect(tvEvaluate2, tvallEvaluateTotal2);
                evaluatesModel.EvaluateList(id, type, 1, 10, goodEvalResp.getObj().getLevelList().get(2).getEvalLevelId());
                tvNoEval.setVisibility(View.GONE);
                lvEvaluate.setVisibility(View.VISIBLE);
                evalType=2;
                num=0;
            }
        });
        /*差评*/
        rlTitleDifferEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestoreIconSelect();
                RestoreTextColor();
                SetTextColorAndSelect(tvEvaluate3, tvallEvaluateTotal3);
                evaluatesModel.EvaluateList(id, type, 1, 10, goodEvalResp.getObj().getLevelList().get(3).getEvalLevelId());
                tvNoEval.setVisibility(View.GONE);
                lvEvaluate.setVisibility(View.VISIBLE);
                evalType=3;
                num=0;
            }
        });

        pullToRefreshLayout.setOnRefreshListener(this);

    }

    /**
     * 设置头部导航图片全部为为未选中状态
     */
    private void RestoreIconSelect() {
        tvEvaluate0.setSelected(false);
        tvallEvaluateTotal0.setSelected(false);
        tvEvaluate1.setSelected(false);
        tvallEvaluateTotal1.setSelected(false);
        tvEvaluate2.setSelected(false);
        tvallEvaluateTotal2.setSelected(false);
        tvEvaluate3.setSelected(false);
        tvallEvaluateTotal3.setSelected(false);
    }

    /**
     * 设置头部导航字体全部为未选中时的状态
     */
    private void RestoreTextColor() {
        //设置导航字体的颜色为初始颜色
        tvEvaluate0.setTextColor(ContextCompat.getColor(activity, R.color.color_menu_btm_default));
        tvallEvaluateTotal0.setTextColor(ContextCompat.getColor(activity, R.color.color_menu_btm_default));
        tvEvaluate1.setTextColor(ContextCompat.getColor(activity, R.color.color_menu_btm_default));
        tvallEvaluateTotal1.setTextColor(ContextCompat.getColor(activity, R.color.color_menu_btm_default));
        tvEvaluate2.setTextColor(ContextCompat.getColor(activity, R.color.color_menu_btm_default));
        tvallEvaluateTotal2.setTextColor(ContextCompat.getColor(activity, R.color.color_menu_btm_default));
        tvEvaluate3.setTextColor(ContextCompat.getColor(activity, R.color.color_menu_btm_default));
        tvallEvaluateTotal3.setTextColor(ContextCompat.getColor(activity, R.color.color_menu_btm_default));
    }

    public void SetTextColorAndSelect(TextView textViewOne, TextView textViewTwo) {
        textViewOne.setSelected(true);
        textViewTwo.setSelected(true);
        textViewOne.setTextColor(ContextCompat.getColor(activity, R.color.color_e51c23));
        textViewTwo.setTextColor(ContextCompat.getColor(activity, R.color.color_e51c23));
    }


    @Override
    public void evalTextSuccess(EvalTextResp evalTextResp) {
        lvEvaluate.setAdapter(new DetailEvaluateAdapter(activity, evalTextResp.getObj()));
        num=num+10;
        if (hasAnimation) {
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }

    }

    @Override
    public void evalTextFailure(String mes) {
        lvEvaluate.setVisibility(View.GONE);
        tvNoEval.setVisibility(View.VISIBLE);
    }

    /*下拉刷新*/
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        if(evalType==0){
            evaluatesModel.EvaluateList(id, type, 1, 10, goodEvalResp.getObj().getLevelList().get(0).getEvalLevelId());
        }else if(evalType==1){
            evaluatesModel.EvaluateList(id, type, 1, 10, goodEvalResp.getObj().getLevelList().get(1).getEvalLevelId());
        }else if(evalType==2){
            evaluatesModel.EvaluateList(id, type, 1, 10, goodEvalResp.getObj().getLevelList().get(2).getEvalLevelId());
        }else if(evalType==3){
            evaluatesModel.EvaluateList(id, type, 1, 10, goodEvalResp.getObj().getLevelList().get(3).getEvalLevelId());
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    /*下拉加载*/
    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        hasAnimation=true;
        if(evalType==0){
            evaluatesModel.EvaluateList(id, type, 1, num, goodEvalResp.getObj().getLevelList().get(0).getEvalLevelId());
        }else if(evalType==1){
            evaluatesModel.EvaluateList(id, type, 1, num, goodEvalResp.getObj().getLevelList().get(1).getEvalLevelId());
        }else if(evalType==2){
            evaluatesModel.EvaluateList(id, type, 1, num, goodEvalResp.getObj().getLevelList().get(2).getEvalLevelId());
        }else if(evalType==3){
            evaluatesModel.EvaluateList(id, type, 1, num, goodEvalResp.getObj().getLevelList().get(3).getEvalLevelId());
        }

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                /*pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);*/
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 3000);

    }
}
