package yunju.com.huiqitian.vm.address.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;

import java.math.BigDecimal;

import cn.trinea.android.common.util.LogUtils;
import cn.trinea.android.common.util.ToastUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.ReceiveAddress;
import yunju.com.huiqitian.http.entity.AddAddressReq;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.address.model.AddAddressModel;
import yunju.com.huiqitian.vm.address.model.DefAndDelModel;
import yunju.com.huiqitian.vm.city.vm.CityList;
import yunju.com.huiqitian.vm.map.view.MapActivity;

public class AddAddressActivity extends BaseActivity implements AddAddressModel.AddAddressInterface{

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    /*添加用户姓名*/
    private EditText etAddressName;
    /*添加手机号*/
    private EditText etAddressPhone;
    /*添加手机号*/
    private ImageView ivAddressPhone;
    /*备用手机号*/
    private EditText etAddressOtherPhone;
    /*地址*/
    private RelativeLayout rlAddress;
    /*城市*/
    private RelativeLayout rlCity;
    /*显示城市*/
    private TextView tvCity;
    /*显示收货地址*/
    private TextView tvAddressItem;
    /*详细地址*/
    private EditText etAddressOther;
    /*添加提交*/
    private TextView tvAddressCommit;
    /*选择男*/
    private LinearLayout llAddressMen;
    private ImageView ivAddressMen;
    private int menMark;
    /*选择女*/
    private LinearLayout llAddressWomen;
    private ImageView ivAddressWomen;
    private int womenMark;
    private byte sex = 0;

    /*选择城市*/
    private String city;
    /*判断那个此页面干嘛的*/
    private String type = "";//add_address  产看收货地址并添加


    private double lat;
    private double lot;
    private String adre;

    /*具体地址*/
    private String address;

    /*精度纬度*/
    private LatLng latLng;

    /*添加收货地址model*/
    private AddAddressModel addAddressModel;

    /*当前地址对象*/
    private ReceiveAddress receiveAddress;

    private DefAndDelModel defAndDelMode;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_address);
    }

    @Override
    public void initBoot() {
        addAddressModel = new AddAddressModel(activity);
        defAndDelMode = new DefAndDelModel(activity);
    }

    @Override
    public void initViews() {
        /*标题栏*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);

        etAddressName = findView(R.id.et_address_name);
        etAddressPhone = findView(R.id.et_address_phone);
        ivAddressPhone = findView(R.id.iv_address_phone);
        etAddressOtherPhone = findView(R.id.et_address_other_phone);
        rlAddress = findView(R.id.rl_address);
        tvAddressItem = findView(R.id.tv_address_item);
        etAddressOther = findView(R.id.et_address_other);
        tvAddressCommit = findView(R.id.tv_address_commit);
        rlCity = findView(R.id.rl_city);
        tvCity = findView(R.id.tv_city);

        llAddressMen = findView(R.id.ll_address_men);
        ivAddressMen = findView(R.id.iv_address_men);
        llAddressWomen = findView(R.id.ll_address_women);
        ivAddressWomen = findView(R.id.iv_address_women);
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            type = (String) bundle.get(Constant.START_NEXT_KEY);

            if ("edit_address".equals(type)) {
                receiveAddress = (ReceiveAddress) bundle.get(Constant.START_NEXT_REQ);
                position = (Integer) bundle.get(Constant.START_NEXT_INTEGER);

                //设置标题栏
                tvTitle.setText(R.string.edit_address);

                if (receiveAddress != null) {
                    Byte sex = receiveAddress.getSex();
                    if (sex == 1) {
                        ivAddressWomen.setBackgroundResource(R.mipmap.weixuanzhong);
                        ivAddressMen.setBackgroundResource(R.mipmap.xuanzhong);
                    } else if (sex == 0) {
                        ivAddressMen.setBackgroundResource(R.mipmap.weixuanzhong);
                        ivAddressWomen.setBackgroundResource(R.mipmap.xuanzhong);
                    }
                    etAddressName.setText(receiveAddress.getName());
                    etAddressPhone.setText(receiveAddress.getTel());
                    etAddressOtherPhone.setText(receiveAddress.getSpareTel());
                    tvAddressItem.setText(receiveAddress.getAddressInfo());
                    etAddressOther.setText(receiveAddress.getDetailAddr());
                }

            } else {
                //设置标题栏
                tvTitle.setText(R.string.new_address);

            }
        }

    }

    @Override
    public void initEvents() {

        defAndDelMode.setUpDateAddressInterface(new DefAndDelModel.UpDateAddressInterface() {
            @Override
            public void upDateAddressSuccess(int position) {
                ToastUtils.show(activity, "编辑成功");

                if (type.equals("edit_address")) {
                    LogUtils.error(AddAddressActivity.class, "..........................666666666");
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
                finish();

            }

            @Override
            public void upDateAddressFailure(String mes) {
                ToastUtils.show(activity, "编辑失败");
            }
        });

         /*返回键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*获取用户地址调到map界面*/
        rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MapActivity.class);
                intent.putExtra("add_address", Constant.ONE);
                intent.putExtra("city", city);
                startActivityForResult(intent, Constant.ZERO);
            }
        });

        /*设置选中男*/
        llAddressMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menMark++;
                sex = 1;
                if (menMark == 1) {
                    ivAddressWomen.setBackgroundResource(R.mipmap.weixuanzhong);
                    ivAddressMen.setBackgroundResource(R.mipmap.xuanzhong);
                    womenMark = 0;
                } else {
                    ivAddressMen.setBackgroundResource(R.mipmap.weixuanzhong);
                    menMark = 0;
                }

            }
        });

        /*设置选中女*/
        llAddressWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                womenMark++;
                sex = 0;
                if (womenMark == 1) {
                    ivAddressMen.setBackgroundResource(R.mipmap.weixuanzhong);
                    ivAddressWomen.setBackgroundResource(R.mipmap.xuanzhong);
                    menMark = 0;
                } else {
                    ivAddressWomen.setBackgroundResource(R.mipmap.weixuanzhong);
                    womenMark = 0;
                }
            }
        });

        /*添加地址提交*/
        tvAddressCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/* adre = data.getStringExtra("add_address");
            lat = data.getDoubleExtra("add_lat", 0.00);
            lot = data.getDoubleExtra("add_lot", 0.00);*/
                String name = etAddressName.getText().toString();//姓名
                String addressInfo = address;//详细地址
                String tel = etAddressPhone.getText().toString();//电话
                String spareTel = etAddressOtherPhone.getText().toString();//备用电话
                adre=tvAddressItem.getText().toString();//地址
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.show(activity, "请输入姓名");
                    return;
                } else if (!TextUtils.isEmpty(addressInfo)) {
                    ToastUtils.show(activity, "请重新选择地址");
                    return;
                } else if (!MyUtils.isMobileNum(tel)) {
                    ToastUtils.show(activity, "请输入正确的手机号");
                    return;
                }/* else if (!MyUtils.isMobileNum(spareTel)) {
                    ToastUtils.show(activity, "请输入正确的备用手机号");
                    return;
                }*/ else if (adre == "" || adre == null) {
                    ToastUtils.show(activity, "请选择地址");
                    return;
                } else {

                    if ("edit_address".equals(type)) {
                        //编辑

                        defAndDelMode.upDateAddress(receiveAddress.getId(), name, receiveAddress.getIsDefault(), sex,
                                receiveAddress.getAreaId(), receiveAddress.getCityId(), receiveAddress.getProvinceId(),
                                tel, spareTel, receiveAddress.getPostCode(), adre , etAddressOther.getText().toString(),
                                new BigDecimal(lat).setScale(6, BigDecimal.ROUND_HALF_UP), new BigDecimal(lot).setScale(6, BigDecimal.ROUND_HALF_UP), position);

                    } else {
                        //添加
                        LogUtils.error(AddAddressActivity.class, "..........................添加");
                        AddAddressReq addAddressReq = new AddAddressReq();
                        addAddressReq.setName(name);
                        addAddressReq.setSex(sex);
                        addAddressReq.setAddressInfo(adre);
                        addAddressReq.setDetailAddr(etAddressOther.getText().toString());
                        addAddressReq.setTel(tel);
                        addAddressReq.setIsDefault((byte) 0);
                        addAddressReq.setSpareTel(spareTel + "");
                        addAddressReq.setLbslat(new BigDecimal(lat).setScale(6, BigDecimal.ROUND_HALF_UP));
                        addAddressReq.setLbslng(new BigDecimal(lot).setScale(6, BigDecimal.ROUND_HALF_UP));
                    /*添加收货地址*/
                        addAddressModel.addAddress(addAddressReq);
                    }
                }
            }
        });

        /*选择城市*/
        rlCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAddressActivity.this, CityList.class);
                startActivityForResult(intent, Constant.ZERO);
            }
        });
    }

    @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*设置用户地址*/
        /*f (resultCode == Constant.ZERO) {*/
        if (resultCode == RESULT_OK) {
                /*PoiInfo poiInfo = JsonUtils.parseObject(data.getStringExtra("add_address"), PoiInfo.class);
                LogUtils.error(AddAddressActivity.class, "poiResult精度和纬度：" + poiInfo.location);*/
                /*设置用户地址*/
            adre = data.getStringExtra("add_address");
            lat = data.getDoubleExtra("add_lat", 0.00);
            lot = data.getDoubleExtra("add_lot", 0.00);
               /* String latLng=data.getStringExtra("add_loa");*/
            if (adre != null) {
                tvAddressItem.setText(adre);
            } else {
                ToastUtils.show(activity, "添加地址失败！");
            }


               /* if (poiInfo != null) {
                    tvAddressItem.setText(poiInfo.address);
                    address = poiInfo.address;
                    latLng = poiInfo.location;
                    *//*LogUtils.error(AddAddressActivity.class,"55555555555555555555555555555555555"+latLng.latitude);*//*
                } else {
                    ToastUtils.show(activity, "添加地址失败！");
                }*/

        } else if (resultCode == 2) {
            city = data.getStringExtra("city");
            tvCity.setText(data.getStringExtra("city"));
        }
        /*}*/
    }

    @Override
    public void addAddressSuccess(String obj) {
        ToastUtils.show(activity, "添加成功");
        if (type.equals("add_address")) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void addAddressFailure(String msg) {
        ToastUtils.show(activity, "添加失败");
        LogUtils.error(AddAddressActivity.class, "添加失败" + msg);
    }


}
