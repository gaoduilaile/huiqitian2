package yunju.com.huiqitian.vm.map.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.ReceiveAddress;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.AddressPoiAdapter;
import yunju.com.huiqitian.vm.adapter.MyAddressAdapter;
import yunju.com.huiqitian.vm.address.model.ReceiverAddressModel;
import yunju.com.huiqitian.vm.city.vm.CityList;
import yunju.com.huiqitian.vm.login.view.LoginActivity;
import yunju.com.huiqitian.vm.map.model.AddressModel;
import yunju.com.huiqitian.vm.widget.MyListView;

public class AddressActivity extends BaseActivity implements OnGetPoiSearchResultListener, ReceiverAddressModel.ReceiverAddressInderface {

    /*title控件实例化*/
    private ImageView ivAddressBack;//返回键
    private TextView tvAddressTitle;//标题

    /*控件实例化*/
    private EditText etAddressFind;//输入搜索
    private TextView ivAddressHint;//提示文字
    private TextView tvAddressStreet;//街道
    private ImageView ivAddressLocation; //定位
    private TextView tvAddressMy;//我的地址
    private TextView tvAddressMyOne;//推荐地址一
    private TextView tvAddressMyTwo;//推荐地址二
    private RelativeLayout rlAddressMore;//更多地址
    private TextView tvAddressSearch;//搜索
    private ScrollView scrollviewAddress;
    private ListView lvAddress;
    private TextView tvSearchNoAddress;

    /*个人收货地址*/
    private MyListView lvAddressTake;

    /*地址点击时间*/
    private LinearLayout lyAddressOne;
    private LinearLayout lyAddressTwo;
    private LinearLayout lyAddressThree;

    /*广播*/
    private MyStreetReceiver myStreetReceiver;

    /*检索*/
    private PoiSearch poiSearch;
    private double latitude;
    private double longitude;

    /*检索出来的PoiResult*/
    private PoiResult poiResults;

    /*数据的model*/
    private AddressModel addressModel;
    private ReceiverAddressModel receiverAddressModel;

    /*adapter*/
    private MyAddressAdapter myAddressAdapter;

    /*初始定位城市*/
    private String startCity;

    /*所选的位置 街道*/
    private String addressNow;

    private boolean searchInCity=false;

    private int num=10;
    private int time=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_address);
    }

    @Override
    public void initBoot() {
        poiSearch = PoiSearch.newInstance();//实例化搜索
        poiSearch.setOnGetPoiSearchResultListener(this);
        addressModel = new AddressModel(activity);
        receiverAddressModel = new ReceiverAddressModel(activity);
    }

    @Override
    public void initViews() {
        /*title控件实例化*/
        ivAddressBack = findView(R.id.iv_address_back);
        tvAddressTitle = findView(R.id.tv_address_title);

        /*控件实例化*/
        etAddressFind = findView(R.id.et_address_find);
        ivAddressHint = findView(R.id.iv_address_hint);
        tvAddressStreet = findView(R.id.tv_address_street);
        ivAddressLocation = findView(R.id.iv_address_location);
        tvAddressMy = findView(R.id.tv_address_my);
        tvAddressMyOne = findView(R.id.tv_address_my_one);
        tvAddressMyTwo = findView(R.id.tv_address_my_two);
        rlAddressMore = findView(R.id.rl_address_more);
        tvAddressSearch = findView(R.id.tv_address_search);
        scrollviewAddress=findView(R.id.scrollview_address);
        lvAddress=findView(R.id.lv_address);
        tvSearchNoAddress=findView(R.id.tv_search_no_address);

        /*实例化点击布局*/
        lyAddressOne = findView(R.id.ly_address_one);
        lyAddressTwo = findView(R.id.ly_address_two);
        lyAddressThree = findView(R.id.ly_address_three);

        /*显示个人收货地址*/
        lvAddressTake = findView(R.id.lv_address_take);

    }

    @Override
    public void initData(Bundle bundle) {
        openLocation();//打开定位
        openBroadCast();//打开广播
        startCity= (String) bundle.get("address");
        tvAddressTitle.setText(startCity);

        receiverAddressModel.getAddress();
          /*软键盘的搜索*/
        etAddressFind.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(AddressActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }
                return false;
            }
        });

    }

    @Override
    public void initEvents() {
        tvAddressTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, CityList.class);
                startActivityForResult(intent, 1);
            }
        });

        /*返回键*/
        ivAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*输入框的点击时间*/
        etAddressFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollviewAddress.setVisibility(View.GONE);
                ivAddressHint.setVisibility(View.GONE);
                etAddressFind.setCursorVisible(true);
            }
        });

        /*更多地址*/
        rlAddressMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddressActivity.this,MapActivity.class);
                intent.putExtra("longitude", poiResults.getAllPoi().get(0).location.longitude);
                intent.putExtra("latitude", poiResults.getAllPoi().get(0).location.latitude);
                intent.putExtra("address",etAddressFind.getText().toString());
                if(!startCity.equals(tvAddressTitle.getText().toString())){
                    intent.putExtra("city",tvAddressTitle.getText().toString());
                }
                startActivity(intent);
                finish();
            }
        });

        /*定位（重新定位）*/
        ivAddressLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocation();//打开定位
            }
        });

        /*搜索*/
        tvAddressSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAddressFind.getText()!=null){
                    num=10;
                    time=2;
                    nearbySearchteo(1, tvAddressTitle.getText().toString(), etAddressFind.getText().toString());
                    searchInCity=true;

                }

            }
        });

        /*地址一的点击事件*/
        lyAddressOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poiResults.getAllPoi().get(0).location!=null){
                    MyUtils.putAddress(getText(tvAddressMy));//存放地址
                    addressNow = tvAddressMy.getText().toString().trim();
               /* dataCallBackInter.getData("这是地址页面传来的参数");*/
                    Intent intent = new Intent();
                    intent.putExtra("longitude", poiResults.getAllPoi().get(0).location.longitude);
                    intent.putExtra("latitude", poiResults.getAllPoi().get(0).location.latitude);
                    intent.putExtra("address", addressNow);
                    intent.putExtra(Constant.MY_CITY,tvAddressTitle.getText().toString());
                    setResult(1, intent);
                    finish();
                }
            }
        });

        /*地址二的点击事件*/
        lyAddressTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poiResults.getAllPoi().get(0).location!=null){
                    MyUtils.putAddress(getText(tvAddressMyOne));//存放地址
                    addressNow = tvAddressMyOne.getText().toString().trim();
                    Intent intent = new Intent();
                    intent.putExtra("longitude", poiResults.getAllPoi().get(0).location.longitude);
                    intent.putExtra("latitude", poiResults.getAllPoi().get(0).location.latitude);
                    intent.putExtra("address", addressNow);
                    intent.putExtra(Constant.MY_CITY,tvAddressTitle.getText().toString());
                    setResult(1, intent);
                    finish();
                }
            }
        });

        /*地址三的点击事件*/
        lyAddressThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poiResults.getAllPoi().get(0).location != null) {
                    MyUtils.putAddress(getText(tvAddressMyTwo));//存放地址
                    addressNow = tvAddressMyTwo.getText().toString().trim();
                    Intent intent = new Intent();
                    intent.putExtra("longitude", poiResults.getAllPoi().get(0).location.longitude);
                    intent.putExtra("latitude", poiResults.getAllPoi().get(0).location.latitude);
                    intent.putExtra("address",addressNow);
                    intent.putExtra(Constant.MY_CITY, tvAddressTitle.getText().toString());
                    setResult(1, intent);
                    finish();
                }
            }
        });

    }


    /**
     * 打开广播
     */
    private void openBroadCast() {
        myStreetReceiver = new MyStreetReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction(Constant.MY_BROADCAST_ACTION);
        registerReceiver(myStreetReceiver, itFilter);
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        poiResults = poiResult;
        if (poiResult == null ||
                poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            LogUtils.error(AddressActivity.class, "未搜索到结果2232323233");
            num=5;
            time--;
            if(time==0){
                tvSearchNoAddress.setVisibility(View.VISIBLE);
                lvAddress.setVisibility(View.GONE);
            }else {
                nearbySearchteo(1, tvAddressTitle.getText().toString(), etAddressFind.getText().toString());
            }
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            tvSearchNoAddress.setVisibility(View.GONE);
            lvAddress.setVisibility(View.VISIBLE);
            LogUtils.error(AddressActivity.class, "搜索到结果了");
            int pager = poiResult.getTotalPageNum();
            for(int i=0;i<poiResult.getAllPoi().size();i++){
                LogUtils.error(AddressActivity.class,poiResult.getAllPoi().get(i).name+"........"+poiResult.getAllPoi().get(i).address+"...."+poiResult.getAllPoi().get(i).phoneNum+"...."+poiResult.getAllPoi().get(i).uid);
            }
           if(searchInCity){
               lvAddress.setAdapter(new AddressPoiAdapter(activity,poiResult.getAllPoi()));
           }
            LogUtils.error(AddressActivity.class, poiResult.getAllPoi().size() + "..................");
            if (poiResult.getAllPoi().size() >= 3) {
                tvAddressMy.setText(poiResult.getAllPoi().get(0).name);//显示我的位置
                tvAddressMyOne.setText(poiResult.getAllPoi().get(1).name);
                tvAddressMyTwo.setText(poiResult.getAllPoi().get(2).name);

            } else if (poiResult.getAllPoi().size()== 2) {
                tvAddressMy.setText(poiResult.getAllPoi().get(0).name);
                tvAddressMyOne.setText(poiResult.getAllPoi().get(1).name);
                tvAddressMyTwo.setText("没有更多结果");
            } else if(poiResult.getAllPoi().size() == 1){
                tvAddressMy.setText(poiResult.getAllPoi().get(0).name);
                tvAddressMyOne.setText("没有更多结果");
                tvAddressMyTwo.setText("没有更多结果");
            }else {
                tvAddressMy.setText("没有更多结果");
                tvAddressMyOne.setText("没有更多结果");
                tvAddressMyTwo.setText("没有更多结果");
            }

            lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyUtils.putAddress(getText(tvAddressMyOne));//存放地址
                    Intent intent = new Intent();
                    intent.putExtra("longitude", poiResults.getAllPoi().get(position).location.longitude);
                    intent.putExtra("latitude", poiResults.getAllPoi().get(position).location.latitude);
                    intent.putExtra("address", poiResults.getAllPoi().get(position).name);
                    intent.putExtra(Constant.MY_CITY, tvAddressTitle.getText().toString());
                    setResult(1, intent);
                    finish();
                }
            });

            LogUtils.error(AddressActivity.class, pager + "");
            LogUtils.error(AddressActivity.class, poiResult.getAllPoi().get(0).address);
            LogUtils.error(AddressActivity.class, poiResult.getAllPoi().get(0).name);
            /*LogUtils.error(AddressActivity.class, poiResult.getAllPoi().get(1).name);*/
            return;
        }
        tvSearchNoAddress.setVisibility(View.VISIBLE);
        lvAddress.setVisibility(View.GONE);
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            //详情检索失败
            // result.error请参考SearchResult.ERRORNO
            LogUtils.error(AddressActivity.class, "检索失败");
        } else {
            LogUtils.error(AddressActivity.class, "onGetPoiDetailResult.............latitude=" + poiDetailResult.getLocation().latitude);
        }

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        LogUtils.error(AddressActivity.class, "333333333333333333");
    }

    @Override
    public void addressDateSuccess(List<ReceiveAddress> receiverAddressResps) {
        myAddressAdapter = new MyAddressAdapter(activity, receiverAddressResps);
        lvAddressTake.setAdapter(myAddressAdapter);
    }

    @Override
    public void addressDateFailure(String msg) {
        startActivity(LoginActivity.class);
        finish();
    }

    /**
     * 广播接受，用来接收服务中发来的地址信息
     */
    public class MyStreetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String myLocationStreet = intent.getStringExtra(Constant.MY_LOCATION_MSG_STREET);
            String myLocation = intent.getStringExtra(Constant.MY_LOCATION_MSG_KEY);
            tvAddressStreet.setText(myLocationStreet + myLocation);//显示对应的街道
            latitude = intent.getDoubleExtra(Constant.MY_LATITUDE, 0.0);
            longitude = intent.getDoubleExtra(Constant.MY_Longitude, 0.0);

            /*设置搜索的参数*/
            nearbySearch(1, latitude, longitude, myLocationStreet + myLocation);
            closeLocation();//关闭广播
        }
    }

    /**
     * 打开定位向服务中发送广播
     */
    public void openLocation() {
        MyUtils.saveLocationType("two");
        Intent intent = new Intent(Constant.MY_CONTROL_ACTION);
        intent.putExtra(Constant.MY_CONTROL_MSG_KEY, Constant.OPEN);
        intent.putExtra(Constant.LOCATION_TYPE,2);
        sendBroadcast(intent);
    }

    /**
     * 关闭定位 向服务中发送广播
     */
    public void closeLocation() {
        Intent intent = new Intent(Constant.MY_CONTROL_ACTION);
        intent.putExtra(Constant.MY_CONTROL_MSG_KEY, Constant.CLOSE);
        intent.putExtra(Constant.LOCATION_TYPE,2);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*注销广播*/
        if (myStreetReceiver != null) {
            unregisterReceiver(myStreetReceiver);
        }
        if (poiSearch != null) {
            poiSearch.destroy();
        }

    }

    /**
     * 附近检索
     */
    private void nearbySearch(int page, double latitude, double longtitude, String keyWords) {
        LogUtils.error(AddressActivity.class, latitude + ".................." + longtitude);
        LogUtils.error(AddressActivity.class,keyWords);
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(new LatLng(latitude, longtitude));
        nearbySearchOption.keyword(keyWords);
        nearbySearchOption.radius(20000);
        nearbySearchOption.pageNum(page);
        poiSearch.searchNearby(nearbySearchOption);

       /* poiSearch.searchInCity(new PoiCitySearchOption()
        *//*.city(tvAddressTitle.getText().toString())*//*
                .city(tvAddressTitle.getText().toString())
                .keyword(keyWords)
                .pageCapacity(3)
                .pageNum(page));
        poiSearch.setOnGetPoiSearchResultListener(this);*/
        /*poiSearch.searchPoiDetail();*/
    }

    private void nearbySearchteo(int page, String city, String keyWords) {
        LogUtils.error(AddressActivity.class, "................走了吗啊啊。。。。。。。。。。。。。。。。。");
        /*PoiCitySearchOption citySearchOption = new PoiCitySearchOption();*/
        LogUtils.error(AddressActivity.class, city + "..................." + keyWords);
        /*citySearchOption.city(city);
        citySearchOption.keyword(keyWords);
        citySearchOption.pageCapacity(10);
        citySearchOption.pageNum(page);
        poiSearch.searchInCity(citySearchOption);*/
        poiSearch.searchInCity(new PoiCitySearchOption()
                .city(city)
                .keyword(keyWords)
                .pageCapacity(num)
                .pageNum(page));
        poiSearch.setOnGetPoiSearchResultListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
            switch (resultCode) {
                case 2:
                    tvAddressTitle.setText(data.getStringExtra("city"));
                    break;

                default:
                    break;
            }
    }

    public GeoPoint getGeoPointBystr(String str) {
        GeoPoint gpGeoPoint = null;
        LogUtils.error(AddressActivity.class,str);
        if (str != null) {
            Geocoder gc = new Geocoder(AddressActivity.this, Locale.CHINA);
            List<Address> addressList = null;
            try {
                addressList = gc.getFromLocationName(str, 1);
                if (!addressList.isEmpty()) {
                    Address address_temp = addressList.get(0);

                    //计算经纬度
                    double Latitude = address_temp.getLatitude() ;
                    double Longitude = address_temp.getLongitude() ;
                    System.out.println("经度：" + Latitude);
                    System.out.println("纬度：" + Longitude);
                    //生产GeoPoint
                    gpGeoPoint = new GeoPoint( Latitude,  Longitude);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gpGeoPoint;
    }
}
