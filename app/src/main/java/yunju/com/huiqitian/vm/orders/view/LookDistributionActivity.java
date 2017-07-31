package yunju.com.huiqitian.vm.orders.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.DistributionDate;
import yunju.com.huiqitian.vm.orders.model.LookDistributionModel;

/**
 *
 * c查看配送状态 * @author 张超群
 *         <p/>
 *         配送查看页面
 */
public class LookDistributionActivity extends BaseActivity implements LookDistributionModel.DistributionInterface {


    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键


    private TextView tvLookNumber;//订单编号
    private TextView tvSendTime;//送达时间

    /*第一个lineLayout*/
    private LinearLayout llFirst;//总布局
    private TextView tvFirst;//显示时间

    /*第一个lineLayout*/
    private LinearLayout llTwo;//总布局
    private TextView tvTwo;//显示时间
    private TextView twoText;//显示配送人员

    /*第一个lineLayout*/
    private LinearLayout llThree;//总布局
    private TextView tvThree;//显示时间

    /*第一个lineLayout*/
    private LinearLayout llFour;//总布局
    private TextView tvFour;//显示时间


    /*配送订单的内容*/
    private LookDistributionModel lookDistributionModel;
    private TextView firstText;
    private TextView threeText;
    private TextView fourText;
    private View firstPoint;
    private View twoPoint;
    private View threePoint;
    private View fourPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_look_distribution);
    }

    @Override
    public void initBoot() {
        lookDistributionModel = new LookDistributionModel(activity);
    }

    @Override
    public void initViews() {
        /*标题栏*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);

        tvLookNumber = findView(R.id.tv_look_number);
        tvSendTime = findView(R.id.tv_send_time);

        llFirst = findView(R.id.ll_first);
        tvFirst = findView(R.id.tv_first);

        llTwo = findView(R.id.ll_two);
        tvTwo = findView(R.id.tv_two);


        llThree = findView(R.id.ll_three);
        tvThree = findView(R.id.tv_three);

        llFour = findView(R.id.ll_four);
        tvFour = findView(R.id.tv_four);

        /*四个点的状态*/
        firstPoint = findView(R.id.first_point);
        twoPoint = findView(R.id.two_point);
        threePoint = findView(R.id.three_point);
        fourPoint = findView(R.id.four_point);

        /*文本的物流文字状态*/
        firstText = findView(R.id.first_text);
        twoText = findView(R.id.tv_two_with_people);
        threeText = findView(R.id.three_text);
        fourText = findView(R.id.four_text);

    }

    @Override
    public void initData(Bundle bundle) {
        /*显示标题*/
        tvTitle.setText(R.string.look_distribution);
        //把获取到的订单编号传出去
        String id = bundle.getString(Constant.LOOK_DISTRIBUTION);
        if (!TextUtils.isEmpty(id)) {
            lookDistributionModel.getDistribution(id);
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

    }


    @Override
    public void distributionDateSuccess(DistributionDate distributionDate) {
        /*拿到物流数据加载数据显示*/
        if (distributionDate != null) {
            //获取订单的状态byte类型,    1 未付款 2 已付款 3.待发货 4。待收货 5.已完成 6.已取消
            byte order= distributionDate.getOrderState();
            int orderState = order & 0xff;
            //订单编号
            tvLookNumber.setText(distributionDate.getId());
            //配送时间
            tvSendTime.setText(distributionDate.getStdispatchTime());
            /*接单时间*/
            tvThree.setText(distributionDate.getStReceiveTime());
            /*下单时间*/
            tvFour.setText(distributionDate.getStOrderTime());
            /*配送时间*/
            tvTwo.setText(distributionDate.getStdispatchTime());
            /*订单完成时间*/
            String finishTime = distributionDate.getStFinishTime();

            if (TextUtils.isEmpty(finishTime)) {
                llFirst.setVisibility(View.GONE);
            }
            if (orderState == 5) {
                /*订单完成。四种状态全部显示，并完成订单页面文字显示为黑色*/
                setTextColorAndPoint();
                llFour.setVisibility(View.VISIBLE);
                llThree.setVisibility(View.VISIBLE);
                llTwo.setVisibility(View.VISIBLE);
                llFirst.setVisibility(View.VISIBLE);
                tvFirst.setText(finishTime);
                tvFirst.setTextColor(getResources().getColor(R.color.color_333333));
                firstText.setTextColor(getResources().getColor(R.color.color_333333));
                firstPoint.setBackgroundResource(R.drawable.rad_point);

            }

            /*订单配送时间*/
            String dispatchTime = distributionDate.getStdispatchTime();
            if (TextUtils.isEmpty(dispatchTime)) {
                llTwo.setVisibility(View.GONE);
            }
            if (orderState == 4) {
                /*待收货*/
                setTextColorAndPoint();
                llFour.setVisibility(View.VISIBLE);
                llThree.setVisibility(View.VISIBLE);
                llTwo.setVisibility(View.VISIBLE);
                tvTwo.setTextColor(getResources().getColor(R.color.color_333333));
                twoText.setTextColor(getResources().getColor(R.color.color_333333));
                twoPoint.setBackgroundResource(R.drawable.rad_point);

               /* tvTwoWithPeople.setText("商家已经打包完毕。配送员【"+distributionDate.getRealName()+"】已经出发，联系电话【"
                        +distributionDate.getMobile()+"】，感谢您耐心等待");*/
                 //LinkBuilder.on(tvTwo).addLink(changeTextColor(distributionDate.getMobile())).build();

            }

            /*接单时间*/
            String receiveTime = distributionDate.getStReceiveTime();
            if (TextUtils.isEmpty(receiveTime)) {
                llThree.setVisibility(View.GONE);
            }
            if (orderState == 3) {
                /*待发货*/
                setTextColorAndPoint();
                llFour.setVisibility(View.VISIBLE);
                llThree.setVisibility(View.VISIBLE);
                tvThree.setTextColor(getResources().getColor(R.color.color_333333));
                threeText.setTextColor(getResources().getColor(R.color.color_333333));
                threePoint.setBackgroundResource(R.drawable.rad_point);

            }

            /*下单时间*/
            String orderTime = distributionDate.getStOrderTime();
            if (TextUtils.isEmpty(orderTime)) {
                llFour.setVisibility(View.GONE);
            }
            if (orderState == 2) {
                setTextColorAndPoint();
                llFour.setVisibility(View.VISIBLE);
                tvFour.setTextColor(getResources().getColor(R.color.color_333333));
                fourText.setTextColor(getResources().getColor(R.color.color_333333));
                fourPoint.setBackgroundResource(R.drawable.rad_point);
            }

        }
    }
    private void setTextColorAndPoint(){
        /*初始值点的颜色全是灰色*/
        firstPoint.setBackgroundResource(R.drawable.huise_point);
        twoPoint.setBackgroundResource(R.drawable.huise_point);
        threePoint.setBackgroundResource(R.drawable.huise_point);
        fourPoint.setBackgroundResource(R.drawable.huise_point);

        /*文字的颜色*/
        firstText.setTextColor(getResources().getColor(R.color.color_999999));
        twoText.setTextColor(getResources().getColor(R.color.color_999999));
        threeText.setTextColor(getResources().getColor(R.color.color_999999));
        fourText.setTextColor(getResources().getColor(R.color.color_999999));

        /*时间的颜色*/
        tvFirst.setTextColor(getResources().getColor(R.color.color_999999));
        tvTwo.setTextColor(getResources().getColor(R.color.color_999999));
        tvThree.setTextColor(getResources().getColor(R.color.color_999999));
        tvFour.setTextColor(getResources().getColor(R.color.color_999999));

        /*时间轴整体的显示与隐藏*/
        llFirst.setVisibility(View.GONE);
        llTwo.setVisibility(View.GONE);
        llThree.setVisibility(View.GONE);
        llFour.setVisibility(View.GONE);

    }


    @Override
    public void distributionDateFailure(String msg) {
        LogUtils.error(LookDistributionActivity.class, "物流信息出错：" + msg);
    }


    /**
     * 设置text某些文字显示蓝色
     *
     * @param text 要显示蓝色的文本
     * @return
     */
    private Link changeTextColor(String text) {
        Link link = new Link(text)
                .setTextColor(R.color.color_1fa2ee)
                .setHighlightAlpha(.4f)
                .setUnderlined(false);
        return link;
    }

    /**
     * 把date类型数据转成String
     *
     * @param date 获取的date数据
     * @return
     */
    /*private String changeDateToString(Date date) {
        if(date!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String str = sdf.format(date);
            return str;
        }
        return "";
    }*/
}
