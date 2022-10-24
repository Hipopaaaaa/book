package ohj.utils;

import ohj.pojo.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author ohj
 * @Date 2022-07-17 11:00
 */
public class WebUtils {

    /**
     * 功能：把map中的值注入到对应的javaBean属性值
     * @param map
     * @param bean
     */
    public static <T> T  copyParamToBean(Map map, T bean){
        try {

            //往对象中注入参数
            BeanUtils.populate(bean,map);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    /**
     * 功能： 将字符串转化成int类型数据
     * @param strInt
     * @param defaultValue
     * @return
     */
    public static int parseInt(String strInt,int defaultValue){
        try {
            return Integer.parseInt(strInt);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
