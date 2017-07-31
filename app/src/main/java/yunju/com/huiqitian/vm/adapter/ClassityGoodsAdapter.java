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
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.ClassifyGoodsReq;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * 分类中查找商品的适配器
 * Created by CCTV-1 on 2016/11/28 0028.
 */
public class ClassityGoodsAdapter extends CommonAdapter<ClassifyGoodsReq.ObjBean> {

    private final List<ClassifyGoodsReq.ObjBean> list;

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ClassityGoodsAdapter(Activity activity, List<ClassifyGoodsReq.ObjBean> list) {
        super(activity,list);
        this.list = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_findresult, parent, false);
            viewHolder.ivResultGoodsDef = get(convertView, R.id.iv_result_goods_def);
            viewHolder.tvResultGoodsName = get(convertView, R.id.tv_result_goods_name);
            viewHolder.tvResultShopName = get(convertView, R.id.tv_result_shop_name);
            viewHolder.tvResultDis = get(convertView, R.id.tv_result_shop_dis);
            viewHolder.tvResultGoodsMoney = get(convertView, R.id.tv_result_goods_money);
            viewHolder.tvResultMoneyPoint = get(convertView, R.id.tv_result_money_point);
            //viewHolder.btnResultInCar = get(convertView, R.id.btn_result_in_car);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
          /*内容赋值*/
        viewHolder.tvResultGoodsName.setText(list.get(position).getName());//商品名称
        viewHolder.tvResultShopName.setText(list.get(position).getMarketName());//店铺名称
        viewHolder.tvResultDis.setText(list.get(position).getDistance() + "");
        viewHolder.tvResultGoodsMoney.setText(list.get(position).getRetailPrice() + "");//小数点之前的钱
        //viewHolder.tvResultMoneyPoint.setText(MyUtils.pointMoney(list.get(position).getRetailPrice()+""));//小数点后面的数

        imageLoader.displayImage(HttpConstant.ROOT_PATH + list.get(position).getPicUrl(), viewHolder.ivResultGoodsDef, ImageOptions.getDefaultOptions());//加载图片

        return convertView;
    }

    //自定义的ViewHolder,持有每个Item的所有界面元素
    public class MyViewHolder {
        private ImageView ivResultGoodsDef;//图片
        private TextView tvResultGoodsName;//商品名称
        private TextView tvResultShopName;//店名
        private TextView tvResultDis;//距离
        private TextView tvResultGoodsMoney;//小数点前面的钱
        private TextView tvResultMoneyPoint;//小数点后面的钱
        //private Button btnResultInCar;//加入购物车
    }
}
