package yunju.com.huiqitian.http.entity;

import java.util.List;

/**
 * Created by zcq on 2016-08-13.
 * <p/>
 * 提交反馈的参数
 */
public class OpinionCommitReq {

    /**
     * 评价内容
     */
    private String text;




    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
