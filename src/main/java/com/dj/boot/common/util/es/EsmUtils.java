package com.dj.boot.common.util.es;

import com.dj.boot.common.util.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 16-10-28
 * Time: 下午1:12
 * To change this template use File | Settings | File Templates.
 */
public class EsmUtils {

    protected static final Logger log = LoggerFactory.getLogger(EsmUtils.class);
    private static final String date_formate = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String date_formate2 = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<Map> threadLocal = new ThreadLocal<Map>();

    /**
     * Eight-bit UCS Transformation Format
     */
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static void prepareEsQueryParam(Object object,Class clazz, Field[] fields,Map<String,Object> queryParam) throws Exception {
        for(Field field:fields){
            String fieldName = field.getName();
            try {
                if( Modifier.isFinal(field.getModifiers()) ){
                    continue;
                }
                Object fieldValue = getFieldValue(clazz, object,fieldName);
                if( null != fieldValue && StringUtils.isNotBlank(String.valueOf(fieldValue)) ){
                    queryParam.put(translateFieldNameForEs(fieldName),fieldValue);
                }
            } catch (Exception e) {
                log.error("Field["+fieldName+"]获取其值，转换至ES的查询参数出现异常",e);
                throw new Exception("Field["+fieldName+"]获取其值，转换至ES的查询参数出现异常",e);
            }
        }
        log.error(JsonUtil.toJson(queryParam));
    }

    public static String translateFieldNameForEs(String fieldName){
        StringBuffer sb = new StringBuffer();
        int i;
        for(i = 0; i <= fieldName.length()-1;i ++){//遍历字符串
            char ch;
            //通过str.charAt(i)遍历出字符串中每个字符
            if(fieldName.charAt(i) >= 'a' && fieldName.charAt(i) <= 'z'){//判断字符是否在a-z之间（小写）
                ch = (char) (fieldName.charAt(i));               //为小写,赋值给ch
            }else if(fieldName.charAt(i) >= 'A' && fieldName.charAt(i) <= 'Z'){//判断字符是否在A-Z之间（大写）
                ch = (char) (fieldName.charAt(i)+32);               //为大写则转换为相应小写,赋值给ch
                if(i != 0){                                         //如果大写不为首位
                    sb.append("_");                                     // 大写则转换为相应小写拼接之前，先拼接“_”
                }
            }else if(fieldName.charAt(i)>='0'&&fieldName.charAt(i)<='9'){//判断字符是否在0-9之间（数字）
                ch = fieldName.charAt(i);                         //如果为数字,将原数字赋值给ch
            }else {
                ch = '_';                                   //如果为其他则转为*号
            }
            sb.append(ch);                                    //将字符追加到sb序列
        }
        return sb.toString();
    }

    public static Object getFieldValue(Class clazz,Object object,String fieldName) throws Exception{
        Field field = clazz.getDeclaredField(fieldName);
        Object value = null;
        if( field!= null){
            if(Modifier.isPrivate(field.getModifiers())){
                String methodName = "get"+fieldName.replaceFirst(fieldName.substring(0, 1),fieldName.substring(0, 1).toUpperCase());
                Method method = clazz.getDeclaredMethod(methodName);
                if(null != method){
                    value = method.invoke(object);
                }
            }else{
                value = field.get(object);
            }
        }
        return value;
    }

    /**
     * 从es取到的数据为UTC时间转换为正常时间
     * @param time utcTime
     */
    public static String formatDate(String time)  {
        try {
            time  = time.replaceAll("Z$","+0000");
            time  = time.replaceAll("z$","+0000");
            return getSdf().format(getSimpleDF().parse(time));
        } catch (Exception e) {
            log.error("ES查询结果时间转换异常",e);
            return "";
        }
    }

    /**
     * 将页面的正常时间转换为es里面查询需要的UTC时间
     * @param time utcTime
     */
    public static String parseDate(String time)  {
        try {
            return getSimpleDF().format(getSdf().parse(time));
        } catch (Exception e) {
            log.error("ES查询结果时间转换异常",e);
            return "";
        }
    }

    public static DateFormat getSdf(){
        Map map = threadLocal.get();
        if (null == map){
            map = new HashMap<String,DateFormat>();
        }
        if (null==map.get("sdf")){
            DateFormat sdf = new SimpleDateFormat(date_formate2);
            map.put("sdf",sdf);
            threadLocal.set(map);
        }
        return (DateFormat) map.get("sdf");
    }

    public static DateFormat getSimpleDF(){
        Map map = threadLocal.get();
        if (null == map){
            map = new HashMap<String,DateFormat>();
        }
        if (null==map.get("simpleDF")){
            DateFormat sdf = new SimpleDateFormat(date_formate);
            map.put("simpleDF",sdf);
            threadLocal.set(map);
        }
        return (DateFormat) map.get("simpleDF");
    }

    /**
     * 基础的base64生成
     * @param username 用户名
     * @param passwd 密码
     * @return
     */
    public static String basicAuthHeaderValue(String username, String passwd) {
        CharBuffer chars = CharBuffer.allocate(username.length() + passwd.length() + 1);
        byte[] charBytes = null;
        try {
            chars.put(username).put(':').put(passwd.toCharArray());
            charBytes = toUtf8Bytes(chars.array());

            //base64编码
            String basicToken = new sun.misc.BASE64Encoder().encode(charBytes);
            return "Basic " + basicToken;
        } finally {
            Arrays.fill(chars.array(), (char) 0);
            if (charBytes != null) {
                Arrays.fill(charBytes, (byte) 0);
            }
        }
    }

    public static byte[] toUtf8Bytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }

}
