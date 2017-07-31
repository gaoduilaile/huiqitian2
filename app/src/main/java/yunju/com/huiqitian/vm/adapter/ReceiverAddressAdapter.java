package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.ReceiveAddress;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.vm.address.model.DefAndDelModel;
import yunju.com.huiqitian.vm.address.view.AddAddressActivity;
import yunju.com.huiqitian.vm.widget.PopShowUtils;

/**
 * Created by 张超群 on 2016-08-12.
 * <p/>
 * 收货地址适配器
 */
public class ReceiverAddressAdapter extends CommonAdapter<ReceiveAddress> {

    private DefAndDelModel defAndDelMode;
    private Activity activity;

    /*删除或设置默认成功后*/
    private DefAndDelSuccessChangList defAndDelSuccessChangList;
    private int type;//判断是那个页面传过来的1:支付页面选择收货 2:查看收货地址

    /*set接口*/
    public void setDefAndDelSuccessChangList(DefAndDelSuccessChangList defAndDelSuccessChangList) {
        this.defAndDelSuccessChangList = defAndDelSuccessChangList;
    }


    public ReceiverAddressAdapter(Activity activity, List<ReceiveAddress> list, int type) {
        super(activity, list);
        this.activity = activity;
        defAndDelMode = new DefAndDelModel(activity);
        this.type = type;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.reciver_address_list_item, parent, false);
            viewHolder.tvReciverAddressName = findView(convertView, R.id.tv_reciver_address_name);
            viewHolder.tvReciverAddressPhone = findView(convertView, R.id.tv_reciver_address_phone);
            viewHolder.tvReciverAddress = findView(convertView, R.id.tv_reciver_address);
            viewHolder.llDefaultAddress = findView(convertView, R.id.ll_default_address);
            viewHolder.llDeletetAddress = findView(convertView, R.id.ll_delete_address);
            viewHolder.llEditAddress = findView(convertView, R.id.ll_edit_address);
            viewHolder.ivDefaultAddress = findView(convertView, R.id.iv_default_address);
            viewHolder.tvAddressScopeOut = findView(convertView, R.id.tv_address_scope_out);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*设置名字*/
        viewHolder.tvReciverAddressName.setText(getItem(position).getName());
        /*设置电话*/
        viewHolder.tvReciverAddressPhone.setText(getItem(position).getTel());
        /*设置收货地址*/
        viewHolder.tvReciverAddress.setText(getItem(position).getAddressInfo());
        /*默认地址设置(0否，1是)*/
        final int isDefault = (int) getItem(position).getIsDefault();
        if (isDefault == 1) {
            viewHolder.ivDefaultAddress.setBackgroundResource(R.mipmap.xuanzhong);
        } else {
            viewHolder.ivDefaultAddress.setBackgroundResource(R.mipmap.weixuanzhong);
        }

        if (type == 1) {
            if (getItem(position).getUseable() == 0) {
                viewHolder.tvAddressScopeOut.setVisibility(View.VISIBLE);
                convertView.setOnClickListener(null);
                convertView.setBackgroundResource(R.color.color_dddddd);
            }
        }

        /*点击设置默认地址监听（可以设置一个弹窗提示）*/
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ivDefaultAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDefault == 0) {
                    defAndDelMode.setAlterAddress(HttpConstant.PATH_SETDEFAULT_ADDRESS,
                            getItem(position).getId(), position);


                } else if (isDefault == 1) {
                    defAndDelMode.upDateAddress(getItem(position).getId(), getItem(position).getName(), (byte) 0, getItem(position).getSex(),
                            getItem(position).getAreaId(), getItem(position).getCityId(), getItem(position).getProvinceId(),
                            getItem(position).getTel(), getItem(position).getSpareTel(), getItem(position).getPostCode(), getItem(position).getAddressInfo(),getItem(position).getDetailAddr(),
                            getItem(position).getLbslng(), getItem(position).getLbslat(), position);
                    defAndDelMode.setAlterAddress(HttpConstant.PATH_SETDEFAULT_ADDRESS,
                            getItem(position).getId(), position);
                    /*finalViewHolder.ivDefaultAddress.setBackgroundResource(R.mipmap.weixuanzhong);*/
                }
            }
        });
        /*删除收货地址（可以设置一个弹窗提示）*/
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.llDeletetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = View.inflate(getActivity(), R.layout.pop_cart_alldel, null);
                PopShowUtils.showPopwindow(view, getActivity().getWindow(), finalViewHolder1.llDeletetAddress, true);
                TextView pop_affirm_del = (TextView) view.findViewById(R.id.pop_affirm_del);
                TextView pop_cancel_del = (TextView) view.findViewById(R.id.pop_cancel_del);
                TextView pop_PromptNum_del = (TextView) view.findViewById(R.id.pop_PromptNum_del);

                pop_PromptNum_del.setText("亲，确定要删除这这条地址吗？");

                pop_affirm_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        defAndDelMode.setDefAndDelAddress(HttpConstant.PATH_DELETE_ADDRESS,
                                getItem(position).getId(), position);
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
        });
        defAndDelMode.setDefAndDelAddressDateSuccess(new DefAndDelModel.DefAndDelReceiverAddressInderface() {
            /*删除收货地址*/
            @Override
            public void defAndDelAddressDateSuccess(String obj, int position) {
                defAndDelSuccessChangList.changListShowA(position, 1);

            }

            @Override
            public void defAndDelAddressDateFailure(String msg) {

            }
        });

        defAndDelMode.setUpDateAddressInterface(new DefAndDelModel.UpDateAddressInterface() {
            /*跟新收货地址*/
            @Override
            public void upDateAddressSuccess( int position) {
                defAndDelSuccessChangList.changListShowA(position, 2);
            }

            @Override
            public void upDateAddressFailure(String mes) {

            }
        });

        defAndDelMode.setSetAlterAddressInterface(new DefAndDelModel.SetAlterAddressInterface() {
            @Override
            public void setAlterAddressSuccess(String obj, int position) {
                defAndDelSuccessChangList.changListShowA(position, 2);
            }

            @Override
            public void setAlterAddressFailure(String mes) {

            }
        });

        viewHolder.llEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.START_NEXT_KEY, "edit_address");
                bundle.putSerializable(Constant.START_NEXT_REQ, getItem(position));
                bundle.putInt(Constant.START_NEXT_INTEGER, position);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 0);
            }
        });


        return convertView;
    }

    /*删除或修改默认成功后*//*
    @Override
    public void defAndDelAddressDateSuccess(String obj, int postion) {
        ToastUtils.show(getActivity(),"成功信息："+obj);
        defAndDelSuccessChangList.changListShowA(postion);
    }

    @Override
    public void defAndDelAddressDateFailure(String msg) {
    }*/

    /*删除或修改成功后的回调接口*/
    public interface DefAndDelSuccessChangList {
        void changListShowA(int position, int type);
    }

    public class ViewHolder {
        private TextView tvReciverAddressName;//收货人姓名
        private TextView tvReciverAddressPhone;//收货人电话
        private TextView tvReciverAddress;//收货人地址

        private LinearLayout llDefaultAddress;//设置默认地址
        private ImageView ivDefaultAddress;//设置默认地址图片
        private LinearLayout llDeletetAddress;//删除收货地址
        private LinearLayout llEditAddress;//编辑收货地址
        private TextView tvAddressScopeOut;
    }
}
