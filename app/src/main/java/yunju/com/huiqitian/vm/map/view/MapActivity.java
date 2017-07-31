package yunju.com.huiqitian.vm.map.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.AddressAdapter;
import yunju.com.huiqitian.vm.adapter.AddressPoiAdapter;
import yunju.com.huiqitian.vm.city.vm.CityList;
import yunju.com.huiqitian.vm.map.model.api.PoiOverlay;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;
import yunju.com.huiqitian.vm.widget.PullableListView;

public class MapActivity extends BaseActivity implements OnGetPoiSearchResultListener, PullToRefreshLayout.OnRefreshListener {
    /*title*/
    private ImageView ivTitleBack;//返回键
    private EditText etMapFind;//搜索
    private TextView ivMapHint;//提示

    /*地图*/
    private MapView bmapView;//地图控件
    private BaiduMap mBaiduMap;//地图
    private TextView tvCity;

    /*文字按钮*/
    private TextView tvMapAll;//全部
    private TextView tvMapOffice;//写字楼
    private TextView tvMapHouse;//小区
    private TextView tvMapSchool;//学校

    /*下划线*/
    private View viewMapAll;//全部下划线
    private View viewMapOffice;//写字楼下划线
    private View viewMapHouse;//小区下划线
    private View viewMapSchool;//学校下划线

    /*搜索*/
    private TextView tvMapSearch;

    /*显示列表*/
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView lstMapAddress;

    /*定位*/
    private LocationReceiver locationReceiver;
    private double latitude;
    private double longitude;

    /*搜索*/
    private PoiSearch poiSearch;
    private List<PoiInfo> poiInfos;

    /*adapter*/
    private AddressAdapter addressAdapter;

    /*刷新的数量*/
    private int num = 5;
    private boolean hasAnimation = false;

    private int keyWords = 1;

    private String myLocationStreet;
    private String myLocation;
    private String keyWordString;

    private String address;
    /*获取从添加地址页面传过来的值*/
    private int addAddressMark = 0;
    /*在哪个城市搜索*/
    private String city;

    private BitmapDescriptor bitMap;

    /*判断用户是否手动点击地址选择位置*/
    private boolean type = false;
    /*用户选择收货地址判断是否是当前城市还是其他城市*/
    private boolean cityType = false;
    /*判断是选择收货地址还是用户选择超市地址*/
    private int flag = 0;
    /*判断是否定位*/
    private boolean isLocation = false;
    private LinearLayout llOver;
    private ListView lvAddress;
    private TextView tvSearchNoAddress;
    private  boolean typeImport=false;
    private int time=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_map);
    }

    @Override
    public void initBoot() {
        poiSearch = PoiSearch.newInstance();//实例化搜索

    }

    @Override
    public void initViews() {
         /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        etMapFind = findView(R.id.et_map_find);
        ivMapHint = findView(R.id.iv_map_hint);

        /*地图*/
        llOver=findView(R.id.ll_over);
        bmapView = findView(R.id.bmapView);
        mBaiduMap = bmapView.getMap();
        tvCity = findView(R.id.tv_city);
        lvAddress=findView(R.id.lv_address);
        tvSearchNoAddress=findView(R.id.tv_search_no_address);

        /*文字按键*/
        tvMapAll = findView(R.id.tv_map_all);
        tvMapOffice = findView(R.id.tv_map_office);
        tvMapHouse = findView(R.id.tv_map_house);
        tvMapSchool = findView(R.id.tv_map_school);

        /*搜索*/
        tvMapSearch = findView(R.id.tv_map_search);

        /*下划线*/
        viewMapAll = findView(R.id.view_map_all);
        viewMapOffice = findView(R.id.view_map_office);
        viewMapHouse = findView(R.id.view_map_house);
        viewMapSchool = findView(R.id.view_map_school);

        /*显示列表*/
        lstMapAddress = findView(R.id.lst_map_address);
        pullToRefreshLayout = findView(R.id.pr_address);
        lstMapAddress.canPullUp();
    }

    @Override
    public void initData(Bundle bundle) {
        /*选中第一个*/
        tvMapAll.setSelected(true);//第一个字变了
        setViewInvisible();//隐藏线
        viewMapAll.setVisibility(View.VISIBLE);

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        pullToRefreshLayout.setOnRefreshListener(this);


        /*获取传值*/
        addAddressMark = getIntent().getIntExtra("add_address", 0);

        openBroadCast();
        openLocation();
        /*if (addAddressMark == Constant.ONE) {
            *//*选择收货地址过来的*//*
            flag = 1;
            if (getIntent().getStringExtra("city") != null && !getIntent().getStringExtra("city").equals("")) {
                city = getIntent().getStringExtra("city");
                nearbySearchteo(1, city, "市政府");
                cityType = true;
            } else {
                openBroadCast();
                openLocation();
            }

        } else {
            *//*选择不同地方的超市*//*
            flag = 2;
            latitude = getIntent().getDoubleExtra("latitude", 0.00);
            longitude = getIntent().getDoubleExtra("longitude", 0.00);
            myLocationStreet = getIntent().getStringExtra("address");
            address = getIntent().getStringExtra("address");
            city = getIntent().getStringExtra("city");
            LogUtils.error(MapActivity.class,"000000000000000000000000000"+city);
            if(city!=null){
                nearbySearchteo(1, city, "市政府");
            }else {
               *//* keyWords = 6;
                LogUtils.error(MapActivity.class,"121212121212122121212121212121212212121212");
                nearbySearch(1, num, latitude, longitude, keyWords);*//*
                openBroadCast();
                openLocation();
            }


        }*/

    }

    @Override
    public void initEvents() {


          /*软键盘的搜索*/
        etMapFind.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MapActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });


        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type) {
                    if (flag == 1) {
                        Intent intent = new Intent(MapActivity.this, AddressActivity.class);
                        //把返回数据存入Intent
                        intent.putExtra("add_address", address);
                        intent.putExtra("add_lot", longitude);
                        intent.putExtra("add_lat", latitude);
                        //设置返回数据
                        setResult(RESULT_OK, intent);
                    } else if (flag == 2) {
                        MyUtils.saveLag(String.valueOf(latitude), String.valueOf(longitude), city, address);
                    }

                }
                finish();
            }
        });

        etMapFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMapHint.setVisibility(View.GONE);
                etMapFind.setCursorVisible(true);
            }
        });

        /*全部*/
        tvMapAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  /*选中第三个*/
                setTextViewSelected();
                tvMapAll.setSelected(true);//第一个字变了
                setViewInvisible();//隐藏线
                viewMapAll.setVisibility(View.VISIBLE);
                keyWords = 1;
                if (cityType) {
                    nearbySearchteo(1, city, keyWords);
                } else {
                    nearbySearch(1, num, latitude, longitude, keyWords);
                }
            }
        });

        /*写字楼*/
        tvMapOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*选中第二个*/
                setTextViewSelected();
                tvMapOffice.setSelected(true);//第一个字变了
                setViewInvisible();//隐藏线
                viewMapOffice.setVisibility(View.VISIBLE);
                keyWords = 2;
                if (cityType) {
                    nearbySearchteo(1, city, keyWords);
                } else {
                    nearbySearch(1, num, latitude, longitude, keyWords);
                }
            }
        });

        /*小区*/
        tvMapHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*选中第三个*/
                setTextViewSelected();
                tvMapHouse.setSelected(true);//第一个字变了
                setViewInvisible();//隐藏线
                viewMapHouse.setVisibility(View.VISIBLE);
                keyWords = 3;
                if (cityType) {
                    nearbySearchteo(1, city, keyWords);
                } else {
                    nearbySearch(1, num, latitude, longitude, keyWords);
                }
            }
        });

        /*学校*/
        tvMapSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*选中第三个*/
                setTextViewSelected();
                tvMapSchool.setSelected(true);//第一个字变了
                setViewInvisible();//隐藏线
                viewMapSchool.setVisibility(View.VISIBLE);
                keyWords = 4;
                if (cityType) {
                    nearbySearchteo(1, city, keyWords);
                } else {
                    nearbySearch(1, num, latitude, longitude, keyWords);
                }
            }
        });

        /*搜索*/
        tvMapSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMapFind.getText() != null) {
                    lvAddress.setVisibility(View.VISIBLE);
                    typeImport = true;
                    nearbySearchteo(1, city, 0);
                    LogUtils.error(MapActivity.class, "999999999999999999999999999999999");
                }
            }
        });



        /*输入框的点击时间*/
        etMapFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llOver.setVisibility(View.GONE);
                ivMapHint.setVisibility(View.GONE);
                etMapFind.setCursorVisible(true);
            }
        });

        /*选择城市*/
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, CityList.class);
                startActivityForResult(intent, Constant.ZERO);
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // 设置marker图标
                bitMap = BitmapDescriptorFactory.fromResource(R.mipmap.sendtocar_balloon);
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                LogUtils.error(MapActivity.class, "latitude=====" + latitude + "..............." + "longitude=====" + longitude);
                mBaiduMap.clear();
                num=5;
                cityType=false;
                nearbySearch(1, num, latitude, longitude, keyWords);
                // 定义Maker坐标点
                LatLng point = new LatLng(latitude, longitude);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point).icon(bitMap);
                // 在地图上添加Marker，并显示
                mBaiduMap.addOverlay(options);

                //实例化一个地理编码查询对象
                GeoCoder geoCoder = GeoCoder.newInstance();
                //设置反地理编码位置坐标
                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                op.location(latLng);
                //发起反地理编码请求(经纬度->地址信息)
                geoCoder.reverseGeoCode(op);
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                        LogUtils.error(MapActivity.class, "..............." + geoCodeResult.getAddress());
                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                        //获取点击的坐标地址
                        LogUtils.error(MapActivity.class, reverseGeoCodeResult.getPoiList().get(0).name + "....." + reverseGeoCodeResult.getPoiList().size());
                        keyWordString = reverseGeoCodeResult.getPoiList().get(0).name;
                        ReverseGeoCodeResult.AddressComponent addressComponent = reverseGeoCodeResult.getAddressDetail();
                        LogUtils.error(MapActivity.class, addressComponent.city + ".........." + addressComponent.district + "......" + addressComponent.street + "....." + reverseGeoCodeResult.getAddress());
                        address = addressComponent.district + addressComponent.street + addressComponent.streetNumber;
                        LogUtils.error(MapActivity.class, "address=" + address);
                        type = true;
                    }
                });
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        /*listView的点击事件*/
        lstMapAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyUtils.putAddress(poiInfos.get(position).name);
                 /*判断是否是从添加页面跳转过来的*/
                if (addAddressMark == Constant.ONE) {
                    //数据是使用Intent返回
                    Intent intent = new Intent(MapActivity.this, AddressActivity.class);
                    //把返回数据存入Intent
                    intent.putExtra("add_address", (poiInfos.get(position).name));
                    intent.putExtra("add_lot", poiInfos.get(position).location.longitude);
                    intent.putExtra("add_lat", poiInfos.get(position).location.latitude);
                    //设置返回数据
                    setResult(RESULT_OK, intent);

                } else {
                    MyUtils.saveLagType("yes");
                    MyUtils.saveLag(String.valueOf(poiInfos.get(position).location.latitude), String.valueOf(poiInfos.get(position).location.longitude), city, poiInfos.get(position).name);
                }
                finish();
            }
        });

        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyUtils.putAddress(poiInfos.get(position).name);
                 /*判断是否是从添加页面跳转过来的*/
                if (addAddressMark == Constant.ONE) {
                    //数据是使用Intent返回
                    Intent intent = new Intent(MapActivity.this, AddressActivity.class);
                    //把返回数据存入Intent
                    intent.putExtra("add_address", (poiInfos.get(position).name));
                    intent.putExtra("add_lot", poiInfos.get(position).location.longitude);
                    intent.putExtra("add_lat", poiInfos.get(position).location.latitude);
                    //设置返回数据
                    setResult(RESULT_OK, intent);

                } else {
                    MyUtils.saveLagType("yes");
                    MyUtils.saveLag(String.valueOf(poiInfos.get(position).location.latitude), String.valueOf(poiInfos.get(position).location.longitude), city, poiInfos.get(position).name);
                }
                finish();
            }
        });


    }

    /**
     * 重置所有选择框的状态（点击换颜色）
     */
    private void setTextViewSelected() {
        tvMapAll.setSelected(false);
        tvMapOffice.setSelected(false);
        tvMapHouse.setSelected(false);
        tvMapSchool.setSelected(false);
    }

    /**
     * 让所有线隐藏
     */
    private void setViewInvisible() {
        viewMapAll.setVisibility(View.INVISIBLE);
        viewMapSchool.setVisibility(View.INVISIBLE);
        viewMapHouse.setVisibility(View.INVISIBLE);
        viewMapOffice.setVisibility(View.INVISIBLE);
    }

    /**
     * 定位到我的位置
     */
    private void centerToMyLocation() {

        LatLng latLng = new LatLng(latitude, longitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);

        /*设置显示我的位置*//*
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_map_dingwei);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);*/
    }


    /**
     * 打开广播
     */
    private void openBroadCast() {

        locationReceiver = new LocationReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction(Constant.MY_BROADCAST_ACTION);
        registerReceiver(locationReceiver, itFilter);
    }

    @Override
    public void onGetPoiResult( PoiResult poiResult) {
        if (poiResult == null ||
                poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            LogUtils.error(MapActivity.class, "没有搜到222222222222222222222222");
            if(typeImport){

                time--;
                if(time==0){
                    tvSearchNoAddress.setVisibility(View.VISIBLE);
                    lvAddress.setVisibility(View.GONE);
                }else {
                    nearbySearchteo(1,city,0);
                }

            }
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            poiInfos = poiResult.getAllPoi();
            if(typeImport){
                lvAddress.setVisibility(View.VISIBLE);
                tvSearchNoAddress.setVisibility(View.GONE);
                lvAddress.setAdapter(new AddressPoiAdapter(activity,poiResult.getAllPoi()));
            }else {
                mBaiduMap.clear();
                MyPoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap);
                poiOverlay.setData(poiResult);
                mBaiduMap.setOnMarkerClickListener(poiOverlay);
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();


                tvCity.setText(poiInfos.get(0).city);
                city=poiInfos.get(0).city;
                addressAdapter = new AddressAdapter(activity, poiInfos);
                lstMapAddress.setAdapter(addressAdapter);
            }
            return;
        }
        if(typeImport){
            tvSearchNoAddress.setVisibility(View.VISIBLE);
            lvAddress.setVisibility(View.GONE);
        }
        if (hasAnimation) {
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    /*刷新相关*/
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        /*hasAnimation = true;
        nearbySearch(1, num, latitude, longitude, keyWords);*/
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        hasAnimation = true;
        num = num + 5;
        if(cityType){
            nearbySearchteo(1, city, keyWords);
        }else {
            nearbySearch(1, num, latitude, longitude, keyWords);
        }
        // 加载超时操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 5000);
    }


    /**
     * 广播接受，用来接收服务中发来的地址信息
     */
    public class LocationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            isLocation = true;
            myLocationStreet = intent.getStringExtra(Constant.MY_LOCATION_MSG_STREET);
            myLocation = intent.getStringExtra(Constant.MY_LOCATION_MSG_KEY);
            keyWordString = myLocationStreet + myLocation;
            latitude = intent.getDoubleExtra(Constant.MY_LATITUDE, 0.0);
            longitude = intent.getDoubleExtra(Constant.MY_Longitude, 0.0);

            /*设置搜索的参数*/
            centerToMyLocation();
            nearbySearch(1, num, latitude, longitude, 1);
            closeLocation();
        }
    }

    /**
     * 打开定位向服务中发送广播
     */
    public void openLocation() {
        MyUtils.saveLocationType("two");
        Intent intent = new Intent(Constant.MY_CONTROL_ACTION);
        intent.putExtra(Constant.MY_CONTROL_MSG_KEY, Constant.OPEN);
        intent.putExtra(Constant.LOCATION_TYPE, Constant.START_BY_FIND);
        sendBroadcast(intent);
    }

    /**
     * 关闭定位 向服务中发送广播
     */
    public void closeLocation() {
        Intent intent = new Intent(Constant.MY_CONTROL_ACTION);
        intent.putExtra(Constant.MY_CONTROL_MSG_KEY, Constant.CLOSE);
        sendBroadcast(intent);
    }

    /**
     * 从选择收货地址页面过来带城市
     */
    private void nearbySearchteo(int page, String city, int keyWords) {
        String keyWordsString="";
        LogUtils.error(MapActivity.class,city);
        switch (keyWords) {
            case 0:
                keyWordsString=etMapFind.getText().toString();
                LogUtils.error(MapActivity.class,keyWordsString);
                break;
            //全部
            case 1:
                keyWordsString="美食";
                break;
            //写字楼
            case 2:
                keyWordsString="写字楼";
                break;
            //小区
            case 3:
                keyWordsString="小区";

                break;
            //学校
            case 4:
                keyWordsString="学校";
                break;
        }
        if(typeImport){
            num=10;
        }
        if (time==1){
            num=5;
        }
        poiSearch.searchInCity(new PoiCitySearchOption()
                .city(city)
                .keyword(keyWordsString)
                .pageCapacity(num)
                .pageNum(page));
        poiSearch.setOnGetPoiSearchResultListener(this);
    }

    /**
     * 附近检索
     */
    private void nearbySearch(int page, int count, double latitude, double longtitude, int keyWords) {

        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();

        nearbySearchOption.location(new LatLng(latitude, longtitude));
        switch (keyWords) {
            //全部
            case 1:
                LogUtils.error(MapActivity.class, keyWordString);
                nearbySearchOption.keyword(keyWordString);
                break;
            //写字楼
            case 2:
                nearbySearchOption.keyword("写字楼");
                break;
            //小区
            case 3:
                nearbySearchOption.keyword("小区");
                break;
            //学校
            case 4:
                nearbySearchOption.keyword("学校");
                break;
            case 5:
                nearbySearchOption.keyword(etMapFind.getText().toString());
                break;
            case 6:
                LogUtils.error(MapActivity.class, address);
                nearbySearchOption.keyword(address);

                break;
        }

        nearbySearchOption.radius(5000);
        nearbySearchOption.pageNum(page);
        nearbySearchOption.pageCapacity(count);
        poiSearch.setOnGetPoiSearchResultListener(this);
        poiSearch.searchNearby(nearbySearchOption);
    }

    /**
     * 添加标记
     */
    class MyPoiOverlay extends PoiOverlay {
        /**
         * 构造函数
         *
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            super.onPoiClick(i);
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            poiSearch.searchPoiDetail(new PoiDetailSearchOption()
                    .poiUid(poiInfo.uid));
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*设置用户地址*/
        /*f (resultCode == Constant.ZERO) {*/
        if (resultCode == 2) {
            city = data.getStringExtra("city");
            tvCity.setText(data.getStringExtra("city"));
            city=data.getStringExtra("city");
            cityType=true;
            nearbySearchteo(1, city, keyWords);
        }
        /*}*/
    }

}
