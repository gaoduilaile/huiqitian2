package yunju.com.huiqitian.vm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.CartGoods;
import yunju.com.huiqitian.entity.CartMarket;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;
import yunju.com.huiqitian.vm.details.view.DetailsActivity;

/**
 * Created by liuGang on 2016/7/28 0028.
 */
public class ShopCartAdapter extends BaseAdapter {

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();
    //装载CartMarket和CartGoods的list
    private List<Object> marketAndGoodsList;
    /*超市名称的位置*/
    private List<Integer> number;
    //布局填充器
    private LayoutInflater mInflater;
    //接收的上下文
    private Context mContent;
    //用来添加checkBox选中的产品
    private List<CartGoods> choosedGoodsList;
    //判断 编辑/完成 的类型
    private int mType;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    //购物车里面每个超市下面商品数量
    private List<Integer> listPosition = new ArrayList<>();
    //超市显示的位置
    private Map<Integer, Integer> map = new HashMap<>();

    //声明接口变量
    CartAdapterInterface cartAdapterInterface;

    public void setCartAdapterInterface(CartAdapterInterface cartAdapterInterface) {
        //接口外部set实例化
        this.cartAdapterInterface = cartAdapterInterface;
        //实例化被选中的CartGoods列表集合
        this.choosedGoodsList = new ArrayList<CartGoods>();
    }

    public ShopCartAdapter(Context context, List<Object> marketAndGoodsList, List<Integer> number, List<Integer> listPosition, Map<Integer, Integer> map) {
        this.marketAndGoodsList = marketAndGoodsList;
        this.mInflater = LayoutInflater.from(context);
        this.number = number;
        this.map = map;
        this.listPosition = listPosition;
        this.mContent = context;
        //实例化控制checkBox
        this.isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < marketAndGoodsList.size(); i++) {
            getIsSelected().put(i, false);
        }

    }

    //接收fragment中全选传递过来的所有CartGoods集合
    public void addChoGoods(List<CartGoods> goodses) {
        //每次全选前先移除当前被选中的CartGoods
        choosedGoodsList.clear();
        this.choosedGoodsList.addAll(goodses);
        cartAdapterInterface.selectorGoodsNum(choosedGoodsList);
    }

    //清除所有选中的CartGoods集合
    public void clearChooseGoods() {
        choosedGoodsList.clear();
        cartAdapterInterface.selectorGoodsNum(choosedGoodsList);
    }

    @Override
    public int getCount() {
        return marketAndGoodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return marketAndGoodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int marketId = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //当前item对象是CartMarket
        if (getItem(position) instanceof CartMarket) {
            CartMarketViewHolder viewHolder = null;
            if (convertView != null && convertView instanceof RelativeLayout) {
                viewHolder = (CartMarketViewHolder) convertView.getTag();
            } else {
                convertView = mInflater.inflate(R.layout.layout_title_position, null);
                viewHolder = new CartMarketViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            /*设置checkBox的状态*/
            viewHolder.cbMarket.setChecked(getIsSelected().get(position));
            // viewHolder.cbMarket.setVisibility(View.VISIBLE);
            viewHolder.tvShopName.setText(((CartMarket) getItem(position)).getMarketName());
            /*监听超市选中和取消的状态*/
            viewHolder.cbMarket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelected.get(position)) {
                        isSelected.put(position, false);
                        setIsSelected(isSelected);
                        LogUtils.error(ShopCartAdapter.class, "9999999999999999999999没选中啊");

                        for (int i = 0; i < map.size(); i++) {
                            if (position == map.get(i)) {
                                for (int j = 0; j < listPosition.get(i); j++) {
                                    LogUtils.error(ShopCartAdapter.class, "数量" + listPosition.get(i));
                                    getIsSelected().put(position + j + 1, false);
                                    choosedGoodsList.remove(getItem(position + j + 1));

                                }
                                cartAdapterInterface.selectorGoodsNum(choosedGoodsList);
                                notifyDataSetChanged();
                            }
                        }


                        //如果当前是未选状态 将其设置为选中状态并添加到控制checkbox的map对象里
                    } else {
                        isSelected.put(position, true);
                        setIsSelected(isSelected);
                        for (int i = 0; i < map.size(); i++) {
                            if (position == map.get(i)) {
                                for (int j = 0; j < listPosition.get(i); j++) {
                                    LogUtils.error(ShopCartAdapter.class, "数量" + listPosition.get(i));
                                    if (!getIsSelected().get(position + j + 1)) {
                                        getIsSelected().put(position + j + 1, true);
                                        choosedGoodsList.add((CartGoods) getItem(position + j + 1));
                                    }
                                }

                            }
                            cartAdapterInterface.selectorGoodsNum(choosedGoodsList);
                            notifyDataSetChanged();
                        }


                        /*ShopCartAdapter.getIsSelected().put(position + 1, true);
                        choosedGoodsList.add((CartGoods) getItem(position + 1));
                        *//*cartAdapterInterface.selectorGoodsNum(choosedGoodsList);*//*
                        notifyDataSetChanged();*/
                    }

                }
            });
            return convertView;

            //当前item对象是CartGoods
        } else if (getItem(position) instanceof CartGoods) {
            GoodsViewHolder holder = null;

            if (convertView != null && convertView instanceof LinearLayout) {
                holder = (GoodsViewHolder) convertView.getTag();
            } else {
                convertView = mInflater.inflate(R.layout.item_shopcart_goods, null);
                holder = new GoodsViewHolder(convertView);
                convertView.setTag(holder);
            }
            /*条目的点击事件*/
            holder.ivCartGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.START_NEXT_KEY, Constant.START_BY_SHOP_CART);
                    bundle.putInt(Constant.ITEM_POSITION, position);
                    bundle.putSerializable(Constant.GOODS_PROP, (CartGoods) getItem(position));
                    for (int i = 0; i < number.size(); i++) {
                        if (((CartMarket) getItem(number.get(i))).getMarketId() == ((CartGoods) getItem(position)).getMarketId()) {
                            bundle.putSerializable(Constant.GOODS_MARKET, ((CartMarket) getItem(number.get(i))));
                            bundle.putInt(Constant.POSITION, i);
                        }
                    }
                    Intent intent = new Intent(mContent, DetailsActivity.class);
                    intent.putExtras(bundle);
                    mContent.startActivity(intent);

                }
            });
            //给控件设置内容
            holder.tvCartGoodsName.setText(((CartGoods) getItem(position)).getName());
            //设置产品价格
            holder.tvCartPrice.setText(((CartGoods) getItem(position)).getRetailPrice().toString());
            //设置产品数量
            holder.tvGoodsNum.setText(((CartGoods) getItem(position)).getQty().toString());
            //加载图片
            imageLoader.displayImage(HttpConstant.ROOT_PATH + ((CartGoods) getItem(position)).getPicUrl(), holder.ivCartGoods, ImageOptions.getDefaultOptions());
            //设置checkBox的状态
            holder.cbCartItem.setChecked(getIsSelected().get(position));

            //（编辑）
            if (mType == 0) {
                holder.llBuyNum.setVisibility(View.VISIBLE);
                holder.llChangeNum.setVisibility(View.GONE);


            } else {
                //（完成）
                holder.llBuyNum.setVisibility(View.GONE);
                holder.llChangeNum.setVisibility(View.VISIBLE);
                holder.tvCartNum.setText(((CartGoods) getItem(position)).getQty().toString());

                //点击增加购物车产品数量
                holder.tvCartAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*if (((CartGoods) getItem(position)).getQty() < 10) {*/

                        ((CartGoods) getItem(position)).setQty((byte) (((CartGoods) getItem(position)).getQty() + 1));
                        notifyDataSetChanged();

                        /*} else {
                            Toast.makeText(mContent, "最多可以购买" + ((CartGoods) getItem(position)).getQty() + "个", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                });


                //点击减少购物车产品数量
                holder.tvCartSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CartGoods) getItem(position)).getQty() > 1) {
                            ((CartGoods) getItem(position)).setQty((byte) (((CartGoods) getItem(position)).getQty() - 1));
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContent, "购买数量不能小" + ((CartGoods) getItem(position)).getQty() + "个", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


            //checkBox点击监听
            holder.cbCartItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果当前是选中状态 将其设置为未选状态并添加到控制checkbox的map对象里
                    if (isSelected.get(position)) {
                        int marketPosition=0;//该商品所属超市的位置
                        isSelected.put(position, false);
                        /*设置商店未选中状态*/
                        for (int i = position; i >= 0; i--) {
                            for (int j = map.size(); j >0; j--) {
                                if (i == map.get(j - 1)) {
                                    if(marketPosition<=i){
                                        marketPosition=i;
                                        isSelected.put(marketPosition, false);
                                    }

                                }
                            }
                        }
                        setIsSelected(isSelected);
                        choosedGoodsList.remove(getItem(position));

                        //如果当前是未选状态 将其设置为选中状态并添加到控制checkbox的map对象里
                    } else {
                        isSelected.put(position, true);
                        setIsSelected(isSelected);
                        int marketPosition=0;//该商品所属超市的位置p
                        int mapPosition=0;//改超市位置在map集合中的position
                        /*获取该超市的位置*/
                        for (int i = position; i >= 0; i--) {
                            for (int j = map.size(); j >0; j--) {
                                if (i == map.get(j - 1)) {
                                    if(marketPosition<=i){
                                        marketPosition=i;
                                        mapPosition=j-1;
                                    }
                                }
                            }
                        }
                        LogUtils.error(ShopCartAdapter.class,"marketPosition="+marketPosition+"mapPosition="+mapPosition);
                        //这是该超市在购物车最下面一条时
                        if(mapPosition+1==map.size()){
                            int time=0;
                            for(int i=marketPosition+1;i<marketAndGoodsList.size();i++){
                                LogUtils.error(ShopCartAdapter.class,"进来了222");
                                if(getIsSelected().get(i)){
                                    LogUtils.error(ShopCartAdapter.class, "进来了333");
                                    time++;
                                }
                                if(time==marketAndGoodsList.size()-1-marketPosition){
                                    isSelected.put(marketPosition,true);
                                }

                            }
                        }else if(mapPosition+1<map.size()){//在购物车中该超市下面还有超市
                            int time=0;
                            for(int i=marketPosition+1;i<map.get(mapPosition+1);i++){
                                LogUtils.error(ShopCartAdapter.class,"进来了222");
                                if(getIsSelected().get(i)){
                                    LogUtils.error(ShopCartAdapter.class, "进来了333");
                                    time++;
                                }
                                if(time==map.get(mapPosition+1)-1-marketPosition){
                                    isSelected.put(marketPosition,true);
                                }

                            }
                        }
                        choosedGoodsList.add((CartGoods) getItem(position));
                    }

                    //回调接口显示计算选中的所有产品价格的总和
                    cartAdapterInterface.selectorGoodsNum(choosedGoodsList);
                    notifyDataSetChanged();
                    // cartAdapterInterface.cancelAllChoose();
                }
            });

            return convertView;
        }

        return null;
    }


    public class CartMarketViewHolder {

        CheckBox cbMarket;
        TextView tvShopName;

        public CartMarketViewHolder(View convertView) {
            cbMarket = (CheckBox) convertView.findViewById(R.id.choose_position);
            tvShopName = (TextView) convertView.findViewById(R.id.indent_tv_shop);
        }
    }

    //自定义存放CartGoods的内部类
    public class GoodsViewHolder {
        CheckBox cbCartItem;
        ImageView ivCartGoods;
        TextView tvCartGoodsName;
        TextView tvCartPrice;
        TextView tvCartSub;
        TextView tvCartNum;
        TextView tvCartAdd;
        TextView tvGoodsNum;
        LinearLayout llBuyNum;
        LinearLayout llChangeNum;

        public GoodsViewHolder(View convertView) {
            cbCartItem = (CheckBox) convertView.findViewById(R.id.cb_cart_item);
            ivCartGoods = (ImageView) convertView.findViewById(R.id.iv_cart_item_goods);
            tvCartGoodsName = (TextView) convertView.findViewById(R.id.tv_cart_item_goodsName);
            tvCartPrice = (TextView) convertView.findViewById(R.id.tv_cart_item_price);
            tvCartSub = (TextView) convertView.findViewById(R.id.tv_cart_item_sub);
            tvCartNum = (TextView) convertView.findViewById(R.id.tv_cart_item_num);
            tvCartAdd = (TextView) convertView.findViewById(R.id.tv_cart_item_add);
            tvGoodsNum = (TextView) convertView.findViewById(R.id.tv_cart_goodsNum);
            llBuyNum = (LinearLayout) convertView.findViewById(R.id.ll_item_buyNum);
            llChangeNum = (LinearLayout) convertView.findViewById(R.id.ll_item_changeNum);
        }

    }

    //接收（编辑）/（完成）类型和点击时间
    public void showBtnOrNum(int type, Boolean isAlter) {
        this.mType = type;
        notifyDataSetChanged();
        if (isAlter) {
            cartAdapterInterface.alterObjList(marketAndGoodsList);
            clearChooseGoods();
            notifyDataSetChanged();
        }
    }

    //ShopCartAdapter中的公开接口
    public interface CartAdapterInterface {
        //修改购物车产品数量
        void alterObjList(List<Object> marketAndGoodsList);

        //计算合计选中产品的总价
        void selectorGoodsNum(List<CartGoods> selectorGoods);
    }

    //管理checkBox的get方法
    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    //管理checkBox的set方法
    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        ShopCartAdapter.isSelected = isSelected;
    }
}
