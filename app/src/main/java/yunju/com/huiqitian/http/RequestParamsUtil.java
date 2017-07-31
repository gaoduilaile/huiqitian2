package yunju.com.huiqitian.http;

import com.loopj.android.http.RequestParams;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Parse Object to RequestParams
 *
 * @see RequestParams
 */
public class RequestParamsUtil {

    public static RequestParams toRequestParams(Object object){
        RequestParams params = new RequestParams();

        if(object == null){
            return params;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        if(fields != null && fields.length > 0){
            for (Field field : fields){
                params.put(field.getName(), getFieldValueByName(field.getName(), object));
            }
        }

        return params;
    }

    /**
     * 根据对象的get方法获取相关属性的值
     *
     * @param fieldName
     * @param o
     * @return
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
