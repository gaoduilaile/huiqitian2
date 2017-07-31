package yunju.com.huiqitian.http.entity;

/**
 * Created by 张超群 on 2016-08-05.
 *
 * 请求代金券数据
 */
public class VoucherReq {

    /**
     * 代金券请求状态标识
     * 1:未使用
     * 2:已使用
     * 3"已过期
     */
    private Byte status;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
