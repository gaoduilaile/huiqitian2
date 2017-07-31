package yunju.com.huiqitian.vm.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.mirror.common.commondialog.Base.BaseDialog;
import yunju.com.huiqitian.R;

/**
 * Created by gao on 2016/8/8 0008.
 */
public class DeleteDialog extends BaseDialog {

    private Button lyDialogSure;
    private Button lyDialogCancel;

    private DeleteInterface deleteInterface;

    public DeleteDialog(Context context) {
        super(context, R.style.Dialog_Style);
        deleteInterface = (DeleteInterface) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.dialog_delete);
    }

    @Override
    public void initBoots() {

    }

    @Override
    public void initViews() {
        lyDialogSure = (Button) findViewById(R.id.ly_dialog_sure);
        lyDialogCancel = (Button) findViewById(R.id.ly_dialog_cancle);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvents() {
        /*确定删除*/
        lyDialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInterface.delete();
            }
        });

        /*取消删除*/
        lyDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /*删除毁掉时间*/
    public interface DeleteInterface {
        void delete();
    }
}
