package yunju.com.huiqitian.http.entity;

import com.google.gson.annotations.SerializedName;

import yunju.com.huiqitian.base.BaseResp;

/**
 * 微信支付的返回值对象
 * Created by CCTV-1 on 2017/1/17 0017.
 */
public class WeiXinReq extends BaseResp {

    /**
     * sign : 2D0106B232CB5DE249AE77A35E456042
     * timestamp : 1484728442
     * noncestr : 7oow1gVKGuB9jZFPPO07Go1hjjXvEYJ1
     * partnerid : 1433460702
     * prepayid : wx201701181634028ee6347b7a0862308095
     * package : Sign=WXPay
     * appid : wx432ceec6c2fa0341
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private String sign;
        private String timestamp;
        private String noncestr;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String appid;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}
