package yunju.com.huiqitian.entity;

import java.io.Serializable;

/**
 * Created by 胡月 on 2016/8/9 0009.
 */
public class GoodsProp implements Serializable{
    /**
     *属性名
     */
    private String name;

    /**
     *属性值
     */
    private String propValue;

    /**
     *属性单位
     */
    private String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
