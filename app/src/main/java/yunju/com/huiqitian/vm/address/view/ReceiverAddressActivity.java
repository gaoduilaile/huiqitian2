package yunju.com.huiqitian.vm.address.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.ReceiveAddress;
import yunju.com.huiqitian.vm.adapter.ReceiverAddressAdapter;
import yunju.com.huiqitian.vm.address.model.ReceiverAddressModel;
import yunju.com.huiqitian.vm.widget.MyListView;

/**
 * @author 张超群
 *         <p/>
 *         管理收货地址界面
 */
public class ReceiverAddressActivity extends BaseActivity implements ReceiverAddressModel.ReceiverAddressInderface {

    /*判断从哪个页面跳转过来的*/
    private String type;
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private TextView tvTitleShare;//用来设置右边标题
    private RelativeLayout rlTitleShare;//设置点击事件

    /*添加我的地址布局控件*/
    private MyListView mlvReceiver;//收货地址列表
    private TextView tvReceiverAddress;//添加收货地址按钮

    /*适配器*/
    private ReceiverAddressAdapter receiverAddressAdapter;

    /*收货地址model*/
    private ReceiverAddressModel receiverAddressModel;

    /*收货地址的信息集合*/
    private List<ReceiveAddress> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_receiver_address);
    }

    @Override
    public void initBoot() {
        receiverAddressModel = new ReceiverAddressModel(activity);

    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
        tvTitleShare = findView(R.id.tv_title_share);
        rlTitleShare = findView(R.id.rl_title_share);

        mlvReceiver = findView(R.id.mlv_receiver);
        tvReceiverAddress = findView(R.id.tv_receiver_address);
    }

    @Override
    public void initData(Bundle bundle) {
        type = (String) bundle.get(Constant.START_NEXT_KEY);

        tvTitle.setText(R.string.receiver_address);//头部标题：商品详细
        tvTitleShare.setVisibility(View.VISIBLE);//用来设置右边标题
        tvTitleShare.setText(R.string.add_receiver_address);

        if (type.equals("order")) {
            addressList = (List<ReceiveAddress>) bundle.getSerializable(Constant.SHARED_USER_ADDRESS);
            dataDispose(addressList, 1);

        } else {
            //获取收货地址
            receiverAddressModel.getAddress();
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

        /*点击添加跳转到添加页面*/
        rlTitleShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016-08-12 跳转添加页面
                if (type.equals("order")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.START_NEXT_KEY, "order_address");
                    startActivity(AddAddressActivity.class,bundle);
                    finish();
                } else {
                    Intent intent = new Intent(ReceiverAddressActivity.this, AddAddressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.START_NEXT_KEY, "add_address");
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
            }
        });

        tvReceiverAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016-08-12 跳转添加页面
                if (type.equals("order")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.START_NEXT_KEY, "order_address");
                    startActivity(AddAddressActivity.class, bundle);
                    finish();
                } else {
                    Intent intent = new Intent(ReceiverAddressActivity.this, AddAddressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.START_NEXT_KEY, "add_address");
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
            }
        });

        mlvReceiver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equals("order")) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.START_NAME, addressList.get(position).getName());
                    intent.putExtra(Constant.START_ADDRESS, addressList.get(position).getAddressInfo());
                    intent.putExtra(Constant.START_TEL, addressList.get(position).getTel());
                    intent.putExtra(Constant.START_ISDEFAULT,addressList.get(position).getIsDefault());
                    intent.putExtra(Constant.START_POSITION,position);
                    //设置返回数据
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void addressDateSuccess(final List<ReceiveAddress> receiveAddressList) {
        if (receiveAddressList != null) {
            dataDispose(receiveAddressList, 2);
        }
    }

    @Override
    public void addressDateFailure(String msg) {
        LogUtils.error(ReceiverAddressActivity.class, "错误:" + msg);
    }

    /**
     * @收货地址的list集合 receiveAddressList
     * @订单过来的地址还是查看收货地址 addressType
     */
    public void dataDispose(final List<ReceiveAddress> receiveAddressList, int addressType) {
        if (receiveAddressList != null && receiveAddressList.size() > 0) {
            addressList = receiveAddressList;
            receiverAddressAdapter = new ReceiverAddressAdapter(ReceiverAddressActivity.this, receiveAddressList, addressType);
            mlvReceiver.setAdapter(receiverAddressAdapter);
                /*数据修改后改变显示*/
            receiverAddressAdapter.setDefAndDelSuccessChangList(new ReceiverAddressAdapter.DefAndDelSuccessChangList() {
                @Override
                public void changListShowA(int position, int type) {
                    if (type == 1) {
                        receiveAddressList.remove(position);
                        receiverAddressAdapter.notifyDataSetChanged();
                    } else if (type == 2) {
                        for (int i = 0; i < mlvReceiver.getChildCount(); i++) {
                            View view = mlvReceiver.getChildAt(i);
                            ImageView imageView = (ImageView) view.findViewById(R.id.iv_default_address);
                            imageView.setBackgroundResource(R.mipmap.weixuanzhong);
                        }
                        View view = mlvReceiver.getChildAt(position);
                        ImageView imageView = (ImageView) view.findViewById(R.id.iv_default_address);
                        imageView.setBackgroundResource(R.mipmap.xuanzhong);
                        /*receiverAddressAdapter.notifyDataSetChanged();*/
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            LogUtils.error(ReceiverAddressActivity.class,"55555555555555555555555555555");
            receiverAddressModel.getAddress();
        }
    }
}
