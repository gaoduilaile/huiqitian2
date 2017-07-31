package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.CartGoods;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by huyeu on 2017/1/9 0009.
 */
public class ShopCartGoodsAdapter extends CommonAdapter<CartGoods>{

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();
    //判断 编辑/完成 的类型
    private int mType;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    //用来添加checkBox选中的产品
    private List<CartGoods> choosedGoodsList= new ArrayList<CartGoods>();

    //声明接口变量
    CartAdapterInterfaces cartAdapterInterfaces;

    public void setCartAdapterInterfaces(CartAdapterInterfaces cartAdapterInterfaces) {
        //接口外部set实例化
        this.cartAdapterInterfaces = cartAdapterInterfaces;
        //实例化被选中的CartGoods列表集合

    }

    public ShopCartGoodsAdapter(Activity activity, List<CartGoods> list) {
        super(activity, list);
        //实例化控制checkBox
        this.isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    //接收fragment中全选传递过来的所有CartGoods集合
    public void addChoGoods(List<CartGoods> goodses) {
        //每次全选前先移除当前被选中的CartGoods
        choosedGoodsList.clear();
        this.choosedGoodsList.addAll(goodses);
        cartAdapterInterfaces.selectorGoodsNum(choosedGoodsList);
    }

    //清除所有选中的CartGoods集合
    public void clearChooseGoods() {
        choosedGoodsList.clear();
        cartAdapterInterfaces.selectorGoodsNum(choosedGoodsList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=getLayoutInflater().inflate(R.layout.item_shopcart_goods, parent, false);
            viewHolder.cbCartItem =findView(convertView, R.id.cb_cart_item);
            viewHolder.ivCartGoods =findView(convertView, R.id.iv_cart_item_goods);
            viewHolder.tvCartGoodsName =findView(convertView,R.id.tv_cart_item_goodsName);
            viewHolder.tvCartPrice =findView(convertView,R.id.tv_cart_item_price);
            viewHolder.tvCartSub =findView(convertView,R.id.tv_cart_item_sub);
            viewHolder.tvCartNum =findView(convertView,R.id.tv_cart_item_num);
            viewHolder.tvCartAdd =findView(convertView,R.id.tv_cart_item_add);
            viewHolder.tvGoodsNum =findView(convertView,R.id.tv_cart_goodsNum);
            viewHolder.llBuyNum =findView(convertView,R.id.ll_item_buyNum);
            viewHolder.llChangeNum =findView(convertView,R.id.ll_item_changeNum);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //给控件设置内容
        viewHolder.tvCartGoodsName.setText(getItem(position).getName());
        //设置产品价格
        viewHolder.tvCartPrice.setText(getItem(position).getRetailPrice().toString());
        //设置产品数量
        viewHolder.tvGoodsNum.setText(getItem(position).getQty().toString());
        //加载图片
        imageLoader.displayImage(HttpConstant.ROOT_PATH +getItem(position).getPicUrl(), viewHolder.ivCartGoods, ImageOptions.getDefaultOptions());
        //设置checkBox的状态
        viewHolder.cbCartItem.setChecked(getIsSelected().get(position));

        //（编辑）
        if (mType == 0) {
            viewHolder.llBuyNum.setVisibility(View.VISIBLE);
            viewHolder.llChangeNum.setVisibility(View.GONE);

        } else {
            //（完成）
            viewHolder.llBuyNum.setVisibility(View.GONE);
            viewHolder.llChangeNum.setVisibility(View.VISIBLE);
            viewHolder.tvCartNum.setText((getItem(position)).getQty().toString());
        }

        //点击增加购物车产品数量
        viewHolder.tvCartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        /*if (((CartGoods) getItem(position)).getQty() < 10) {*/

                (getItem(position)).setQty((byte) ((getItem(position)).getQty() + 1));
                notifyDataSetChanged();

                        /*} else {
                            Toast.makeText(mContent, "最多可以购买" + ((CartGoods) getItem(position)).getQty() + "个", Toast.LENGTH_SHORT).show();
                        }*/
            }
        });

        //点击减少购物车产品数量
        viewHolder.tvCartSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (( getItem(position)).getQty() > 1) {
                    (getItem(position)).setQty((byte) (( getItem(position)).getQty() - 1));
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "购买数量不能小" + (getItem(position)).getQty() + "个", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.cbCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果当前是选中状态 将其设置为未选状态并添加到控制checkbox的map对象里
                if (isSelected.get(position)) {
                    isSelected.put(position,false);
                    setIsSelected(isSelected);
                    choosedGoodsList.remove(getItem(position));
                }else {
                    //如果当前是未选状态 将其设置为选中状态并添加到控制checkbox的map对象里
                    isSelected.put(position, true);
                    setIsSelected(isSelected);
                    choosedGoodsList.add(getItem(position));
                }
                //回调接口显示计算选中的所有产品价格的总和
                cartAdapterInterfaces.selectorGoodsNum(choosedGoodsList);
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

    public class ViewHolder{
        private CheckBox cbCartItem;
        private ImageView ivCartGoods;
        private TextView tvCartGoodsName;
        private TextView tvCartPrice;
        private TextView tvCartSub;
        private TextView tvCartNum;
        private TextView tvCartAdd;
        private TextView tvGoodsNum;
        private LinearLayout llBuyNum;
        private LinearLayout llChangeNum;
    }

    //接收（编辑）/（完成）类型和点击时间
    public void showBtnOrNum(int type, Boolean isAlter) {
        this.mType = type;
        notifyDataSetChanged();
        if (isAlter) {
            cartAdapterInterfaces.alterObjList(getList());
            clearChooseGoods();
            notifyDataSetChanged();
        }
    }

    //ShopCartAdapter中的公开接口
    public interface CartAdapterInterfaces{
        //修改购物车产品数量
        void alterObjList(List<CartGoods> cartGoodsList);

        //计算合计选中产品的总价
        void selectorGoodsNum(List<CartGoods> select1rGoods);
    }

    //管理checkBox的get方法
    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    //管理checkBox的set方法
    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        ShopCartGoodsAdapter.isSelected = isSelected;
    }
}
