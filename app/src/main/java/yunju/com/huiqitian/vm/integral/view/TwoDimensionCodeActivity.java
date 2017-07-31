package yunju.com.huiqitian.vm.integral.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.utils.EncodingHandler;

/**
 * Created by 胡月 on 2016/11/14 0014.
 */
public class TwoDimensionCodeActivity extends BaseActivity{

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    /*二维码*/
    private ImageView imageTwoDimension;

    /*生成二维码的编号*/
    private String  number;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_two_dimension);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
          /*标题栏*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        imageTwoDimension=findView(R.id.image_two_dimension);
        btn = findView(R.id.btn_cancel);

    }

    @Override
    public void initData(Bundle bundle) {
        number=bundle.getString("id");
        try {
            imageTwoDimension.setImageBitmap(EncodingHandler.createQRCode(number, 270));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initEvents() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
