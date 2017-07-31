package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;
import yunju.com.huiqitian.utils.MyUtils;

/**
 * Created by 高英祥 on 2016/7/26 0026.
 */
public class GoodsResultAdapter extends CommonAdapter<GoodsInfo> {

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    /*点击时间的回调接口*/
    private ClickInterface clickInterface;

    public void setClickInterface(ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    public GoodsResultAdapter(Activity activity, List<GoodsInfo> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_findresult, parent, false);
            viewHolder.ivResultGoodsDef = get(convertView, R.id.iv_result_goods_def);
            viewHolder.tvResultGoodsName = get(convertView, R.id.tv_result_goods_name);
            viewHolder.tvResultShopName = get(convertView, R.id.tv_result_shop_name);
            viewHolder.tvResultDis = get(convertView, R.id.tv_result_shop_dis);
            viewHolder.tvResultGoodsMoney = get(convertView, R.id.tv_result_goods_money);
            viewHolder.tvResultMoneyPoint = get(convertView, R.id.tv_result_money_point);
            //  viewHolder.btnResultInCar = get(convertView, R.id.btn_result_in_car);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
          /*内容赋值*/
        viewHolder.tvResultGoodsName.setText(getItem(position).getName());//商品名称
        viewHolder.tvResultShopName.setText(getItem(position).getMarketName());//店铺名称
        viewHolder.tvResultDis.setText(getItem(position).getDistance() + "米");
        viewHolder.tvResultGoodsMoney.setText(getItem(position).getRetailPrice().intValue() + "");//小数点之前的钱
        viewHolder.tvResultMoneyPoint.setText(MyUtils.pointMoney(getItem(position).getRetailPrice()));//小数点后面的数
        viewHolder.tvResultDis.setText(getItem(position).getDistance() + "");//设置距离



        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(), viewHolder.ivResultGoodsDef, ImageOptions.getDefaultOptions());//加载图片

        /*点击进入购物车*/
//        viewHolder.btnResultInCar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickInterface.btnClick(getItem(position).getId(),getItem(position).getType());
//            }
//        });

        return convertView;
    }

    //自定义的ViewHolder,持有每个Item的所有界面元素
    public class ViewHolder {

        private ImageView ivResultGoodsDef;//图片
        private TextView tvResultGoodsName;//商品名称
        private TextView tvResultShopName;//店名
        private TextView tvResultDis;//距离
        private TextView tvResultGoodsMoney;//小数点前面的钱
        private TextView tvResultMoneyPoint;//小数点后面的钱
        //  private Button btnResultInCar;//加入购物车
    }

    /**
     * 点击事件的监听回调
     */
    public interface ClickInterface {
        void btnClick(int id, byte type);
    }


}
