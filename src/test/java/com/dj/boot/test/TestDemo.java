package com.dj.boot.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dj.boot.btp.common.util.noUtil.Constant;
import com.dj.boot.btp.exception.SafJosExceptionBuilder;
import com.dj.boot.combine.dto.Result;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.util.ErrorUitl;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.controller.bill.domain.BillExceptionDto;
import com.dj.boot.pojo.*;
import com.dj.boot.test.domain.AppMsg;
import com.dj.boot.test.domain.OrderWeChatCondition;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.misc.Regexp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestDemo extends BaseController {

    private static DecimalFormat df = new DecimalFormat("#.000");



    public static void main(String[] args) {
        String[] warehouseNoList = null;
        List<String> list2 = Arrays.asList(warehouseNoList);

        KdnTrackReqDto kdnTrackReqDto = new KdnTrackReqDto();
        kdnTrackReqDto.setCustomerName("22222");
        kdnTrackReqDto.setOrderCode("33333");
        final String json = JSONObject.toJSONString(kdnTrackReqDto);
        System.out.println(json);

        String s = transformUpperCase(json);


        Map<String,Object> header = new HashMap<String, Object>();
        header.put("supplyArgs",true);
        header.put("APIKey","xxxxxxxxxxxxxxxxxxxx");
        header.put("EBusinessID","000111222");
        header.put("RequestType","8002");
        header.put("DataType","2");

        Object dataType = header.get("DataType1");
        String dataType1 = header.get("DataType1").toString();


        System.out.println(System.nanoTime());

        String a = null;

        List<String> l = new ArrayList<>();
        l.add("111");
        if (l.contains(a)) {
            System.out.println(111);
        }


        List<User> list1 = new ArrayList<>();
//        User user1 = new User();
//        user1.setPassword("2111");
//        User user2 = new User();
//        user2.setPassword("2111");
//        list1.add(user1);
//        list1.add(user2);

        List<User> list = new ArrayList<>();
        User user = new User();
        user.setPassword("2111333");list.add(user);


        list.addAll(list1);


        String url = "我叫%s,今年%s岁。";
        String name = "";
        String age = "23";
        url = String.format(url,name,age);
        System.out.println(url);
    }


    // 将map的Key全部转换为大写
    public static String transformUpperCase(String json) {
        Map<String,Object> orgMap = JSONObject.parseObject(json, Map.class);
        Map<String, Object> resultMap = new HashMap<>();
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            resultMap.put(Character.toUpperCase(key.charAt(0)) + key.substring(1), orgMap.get(key));
        }
        return JSONObject.toJSONString(resultMap);
    }


    @Test
    public void StringLengthTest(){


        List<String> errMsgs = new ArrayList<>();
        errMsgs.add("防重码为空");
        errMsgs.add("仓库名称为空");

        errMsgs.add("物资名称与物资别名二选一必填");
        errMsgs.add("机构名称为空");

        errMsgs.add("采购类型为空");

        errMsgs.add("单据类型名称为空");

        errMsgs.add("物资数量为空");

        errMsgs.add("计划完成日期为空");

        String join = String.join(", ", errMsgs);

        System.out.println(join);

        String str = "qwertyuiop";
        String substring = str.substring(1);
        System.out.println(substring);
    }


    @Test
    public void test2() {

        //List<RequireItem> requireItems = requireItemList.stream().filter(requireItem -> requireItem.getRealCount() != null && requireItem.getRealCount().compareTo(new BigDecimal(0)) > 0).collect(Collectors.toList());

        if (new BigDecimal(-1).compareTo(new BigDecimal(0)) > 0) {

            System.out.println("大于0");
        }
        System.out.println(new BigDecimal(-1).compareTo(new BigDecimal(0)) );
    }
    @Test
    public void test1() {

        equalsTest();
        stringTest();
        test();

    }


    // java中%d %s的含义
    private void test () {
        //%s 替换符
        System.out.println(String.format("第%s条, 一共出现了%s个异常", 2,4));
        System.out.println(String.format("第%10d条出现异常", 2+ 1));

        List<String> errMsg = Lists.newArrayList();
        errMsg.add("java");
        errMsg.add("is");
        errMsg.add("cool");
        String join = String.join("-", errMsg);
        System.out.println(join);
    }

    private void equalsTest() {
        int a = 1;
        String b = "";
        String c = null;
        if (String.valueOf(a).equals(b)) {

            System.out.println(111);
        }
        if (String.valueOf(a).equals(c)) {
            System.out.println(222);
        }
    }

    private void stringTest() {
    /*@Override
    public synchronized StringBuffer append(String str) {
        toStringCache = null;
        super.append(str);
        return this;
    }*/
        StringBuffer stringBuffer = new StringBuffer("111");
        stringBuffer.append("222");

        StringBuilder stringBuilder = new StringBuilder("333");
        short s1 = 1;
        //s1 = s1 + 1;
        s1 += 1;
    }

    @Test
    public void testSet() {
        Byte [] staffs = new Byte[]{3, 4, 5,6,7};

        Set<Byte> staffsSet = new HashSet<>(Arrays.asList(staffs));
        System.out.println(staffsSet);

        Set<Byte> set = new HashSet<>();
        set.add((byte)3);
        set.add((byte)4);
        set.add((byte)5);
        set.add((byte)6);
        set.add((byte)7);
        System.out.println(set);
    }




    @Test
    public void test6(){
        User u = new User();
        u.setSex(11);
        u.setUserName("wj");
        String s = JSONObject.toJSONString(u);

        User u1 = new User();
        u1.setUserName("wwwwwwwww");
        u1.setEmail(s);
        System.out.println(u1);
        String s1 = JSONObject.toJSONString(u1);
        System.out.println(s1);

        User user = new User().setUserName("zs").setSalt("222").setSex(1).setPassword("123");
        //Map<String, Object> map1 = JSONObject.parseObject(JSONObject.toJSONString(user));
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(user), Map.class);
        HashMap hashMap = new HashMap<>(BeanMap.create(user));
        System.out.println(111);
    }




    @Test
    public void test11(){
        User user = new User();
        user.setEmail("100");
        User user1 = new User();
        user1.setEmail("100");

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        StringBuilder res = null;
        for(User info : list) {
            //二选一
            //net.sf.cglib.beans.BeanMap map = net.sf.cglib.beans.BeanMap.create(info);
            HashMap hashMap = new HashMap<>(BeanMap.create(info));
            String value = (String)hashMap.get("email");
            switch(value) {
                case "100":
                    value="1";
                    break;
                case "200":
                    value="2";
                    break;
                default:
                    value="";
                    break;
            }
            if(res != null) {
                res.append(","+ value);
            }else {
                res = new StringBuilder(value);
            }
        }

        System.out.println(res);

    }


    /**
     * java的八大基本类型
     * byte(字节类型)	1字节	-2^7 ~ 2^7-1
     * short(短整形)	2字节	-2^15 ~ 2^15-1
     * int(整形)	    4字节	-2^31 ~ 2^31-1
     * long(长整型)	8字节	-2^63 ~ 2^63-1
     * float(单精度浮点数)	4字节	-2^128 ~ 2^128
     * double(双精度浮点数)	8字节	-2^1024 ~ 2^1024
     * char(字符型)	1字节
     * Boolean(布尔类型)	2字节
     *
     * 注意:
     * 	(1)整数字面值是默认int类型来存储；如果整数字面值超过int类型，将类型转换为long类型，需要在数值后面加L或l后缀。但如果没超过int类型的范围，又需要用long类型来接收，则可以不加后缀.
     * 	(2)浮点数字面值默认为double，如果想要使用float类型来存储浮点数字面值，则需要在其后加F或者f.
     * 	   double类型是浮点数常用的类型；double类型的字面值后面可以加D或者d，也可以不加.
     * 	   浮点型存储数据可能有精度损失.
     * 	(3)字符类型的数据可以通过int类型的数字来表示,通过ASCII码表转换为对应的值.
     * 	(4)Boolean类型的值只能是true或者false,默认为true.
     * 	(5)基本数据类型都是关键字.
     *
     * 	1、**自动类型转换**
     * 	数值型:从小到大
     * 		整数:byte->short->int->long
     * 		浮点型:float->double
     * 			float自动转为double类型可能有精度损失.
     * 		整形->浮点数
     * 			直接在后面补上 .0
     * 			byte->short->int->long---->float->double(虚线的转换有精度损失)
     * 		字符型:
     * 			char->int->long---->float->double
     * 			char->int
     * 			根据码表将字符对应的ASCII值转为int类型.
     * **2、强制类型转换**
     * 	从大到小:编译会报错
     * 	大的类型转换为小的类型可能会溢出，为了确保程序的安全，编译时就会报错.
     * 	大的数据类型的数据值在小数据类型的范围之内，可以使用强制类型转换.
     *
     * 	格式:
     * 		(type)数据
     * 		将数据的类型强制转换为小括号的数据类型.
     * 	注意:
     * 		1、浮点数强制转换为整数存在精度损失无论小数部分直接舍弃.
     * 		2、类似于Boolean类型无法通过强制类型转换转换为数值型.
     * 		3、当大的数据类型超过小的数据类型范围时，从最低位开始拿对应的位数的二进制.
     */
    @Test
    public void test12(){
        byte b = 1;
        short s = 2;
        int i = 3;
        long l = 4;


        byte be = (byte) s;
        long l1 = s;
        int i1 = (int) l;

        float f = 2.22f;
        double d = 33.55d;
        char c = 5;
        boolean b1 = true;




        long l3 = c;
        int i8 = c;
        double d4 = c;
        float f6 = c;
        byte b7 = (byte) c;
        char c2 = (char) b;
        char c3 = (char) l;
        char c4 = (char) d;



        float f4 = i;
        System.out.println(f4);


        long l9 = (long) d;
        System.out.println(l9);


    }




    @Test
    public void test20(){

        Integer code = 0;
        String msg = "";
        switch (code) {
            case 0:
                System.out.println("0当前code值是:["+code+"]");
                msg="当前code值是:["+code+"]";
                break;
            case 1:
                System.out.println("1当前code值是:["+code+"]");
                msg="当前code值是:["+code+"]";
                break;
            case 2:
                System.out.println("2当前code值是:["+code+"]");
                msg="当前code值是:["+code+"]";
                break;
        }


        System.out.println(msg);

    }


    /**
     * 根据文件URL 获取文件的大小
     */
    @Test
    public void test21(){
        String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201601%2F03%2F20160103225650_Ptv5L.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620530878&t=acc2e8738912d07db791d84b43f73afd";
        System.out.println(getRemoteFileSize(url));

    }

    public static long getRemoteFileSize(String url) {
        long size = 0;
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            size = conn.getContentLength();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }





    @Test
    public void test22(){
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        System.out.println(year);

    }

    private static final Integer BATCH_NUM = 2;
    @Test
    public void test23(){
        ArrayList<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        int len = list.size();
        // 计算size
        int size = len / 2;
        // 计算余数
        int remainder = len % 2;
        if (remainder > 0) {
            size = size + 1;
        }
        double num = 2;
        Double length = (double) len;
        double d  = length/num;
        int i = (int) Math.ceil(d);

        int s = (int) Math.ceil((double) len/(double) BATCH_NUM);
        System.out.println(i);
    }





    @Test
    public void test24(){
        User user = new User();
        user.setId(10010);
        user.setUserName("wj");
        OrderWeChatCondition orderWeChatCondition = buildWeChatPushBody(user);
        System.out.println(111111);
        AppMsg appMsg = pushAppMsg(user);
        System.out.println(JSONObject.toJSONString(appMsg));
    }

    private OrderWeChatCondition buildWeChatPushBody(User user){
        OrderWeChatCondition orderWeChatCondition = new OrderWeChatCondition();
        OrderWeChatCondition.MiniProgram miniProgram = orderWeChatCondition.getMiniProgram();
        miniProgram.setAppId("APP_ID");
        String code = "2222222222222222222";
        StringBuilder sb = new StringBuilder("p/i/c/c?eoNo=");
        sb.append(user.getId()).append("&type=1&toUser=").append(user.getUserName()).append("&code=").append(code);
        miniProgram.setPagePath(sb.toString());
        //模板ID
        orderWeChatCondition.setTemplate_id("TEMPLATE_ID");
        OrderWeChatCondition.Data data = orderWeChatCondition.getData();
        //详细内容
        data.setFirst("您好，您的订单" + user.getId() + "有新的回复：");
        //服务类型
        data.setHandleType("1");
        //处理状态
        data.setStatus("1");
        //提交时间
        data.setRowCreateDate("2020-02-02");
        //当前进度
        data.setLogType("type");
        //说明
        data.setRemark("Remark");

        return orderWeChatCondition;
    }

    private AppMsg pushAppMsg(User user){
        Map<String, Object> extrasMap = new HashMap<String, Object>();
        extrasMap.put("noType", 1);
        extrasMap.put("data", JsonUtil.toJson(user));
        String extras = JsonUtil.toJson(extrasMap);
        AppMsg appMsg = new AppMsg.Builder()
                .resType("0")
                .platform(0)
                .extras(extras)
                .pinStr(user.getUserName())
                .build();

        return appMsg;
    }



    @Test
    public void test25(){
        BigDecimal bigDecimal = new BigDecimal(3);
        BigDecimal abs = bigDecimal.abs();
        if (bigDecimal.compareTo(new BigDecimal(0)) < 0) {
            System.out.println(1);
        }

        User user = new User();
        user.setUserName("CWO22222");
        System.out.println(user.getUserName().substring(0, 3));
        validateParam(user);
        UserDto userDto = new UserDto();
        userDto.setUserName(2);
        validateParam(userDto);

        User user1=null;
        validateParam(user1);


    }

    private void validateParam(Object o){
        if (null == o) {
            System.out.println(22);
        }
        if (o instanceof User) {
            User user = ((User) o);
            if (!user.getUserName().startsWith("CWO") && !user.getUserName().startsWith("CCM") ) {
                System.out.println("false");
            }
            System.out.println(user.getUserName());
        }
        if (o instanceof UserDto) {
            UserDto user = ((UserDto) o);
            System.out.println(user.getUserName());
        }


    }


    @Test
    public void test26(){
        int a = 13;
        int b = 2;
        //这里a和b都是int型，a/b的值就是a除以b的商，a%b就是a除以b的余数，商是没有小数点的
        int s = a/b;
        int s1 = a%b;

        System.out.println(s);
        System.out.println(s1);


    }


    @Test
    public void test27(){

        BigDecimal b = null;
        String s = String.valueOf(b);
        Integer status = 3;
        Integer checkStockType = 1;
        Integer reserve2 = 1;

        validate(status, checkStockType, reserve2);



    }


    private void validate(Integer status, Integer checkStockType, Integer reserve2){
        if ( (reserve2 != 2 && (status != 4 || checkStockType == 1)) || (reserve2 == 2 && (checkStockType == 1 || status == 5 || status == 7) ) ) {
            System.out.println(1);
        }
    }



    @Test
    public void test28(){ //此处的i只能是0-9之间的字符
        //https://blog.csdn.net/seableble/article/details/103172412?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1.no_search_link

        //https://www.cnblogs.com/summerdata/p/10722144.html 二进制 八进制 十进制 十六进制转化
        String str = "123406789";
        //char c = str.charAt(4);
        //int b = c-'0';


        char c1 = 'a';
        int num = c1 - '0';
        System.out.println(num);



        int num2 = 49;
        char c2 = (char)(num2 +'0');

        System.out.println(c2);


        int i = 5;
        char c = (char)(i+'0');
        if (i+'0' == c) {
            System.out.println(33333333);
        }


        char c3 = '5';
        int i3 = c3-'0';
        if (c3-'0' == i3) {
            System.out.println(1111111);
        }


    }

    @Test
    public void test29(){
        String nameAttr = "aaa,bbb,ccc,ddd";
        String[] nameArr = StringUtils.tokenizeToStringArray(nameAttr, ",");
        List<String> list = Arrays.asList(nameArr);

        String[] aliasesArray = StringUtils.toStringArray(list);



    }


    @Test
    public void test30(){

        User user = null;
        if (null == user || org.apache.commons.lang3.StringUtils.isBlank(user.getUserName())) {
            System.out.println(1);
        }

        Warehouse warehouse = new Warehouse();
        List<User> userList = Lists.newArrayList();
        User user1 = new User();
        user1.setUserName("11111");
        user1.setPassword("11111111111");
        User user2 = new User();
        user2.setUserName("222222");
        user2.setPassword("2222222222");
        userList.add(user1);
        userList.add(user2);
        warehouse.setUsers(userList);
        List<User> users = warehouse.getUsers();
        for (User user3 : users) {
            user3.setPassword("3333333333333");
        }
        List<User> users1 = warehouse.getUsers();
        System.out.println(1111111);
    }

    @Test
    public void test31(){
        String aa = "";
        String aaa = null;
        String trim = aa.trim();
        //String trim1 = aaa.trim();
        System.out.println(trim);
        String key = "123";
        int code = key.hashCode();
        int h;
        //int s = h >>> 16;
        int s1 = code >>> 16;
        int s2 = code ^ s1;
        int res = (h = key.hashCode()) ^ (h >>> 16);

        Map<Object, Object> map1 = new HashMap<>(3);
        map1.put("key","value");
        map1.put("key1","value1");
        map1.put("key2","value2");
        map1.put("key3","value3");
        map1.put("key4","value4");
        map1.put("key5","value5");
        map1.put("key2","value2");
        Map<Object, Object> map = new HashMap<>();
        map.put("key","value");

        Set<Object> set = new HashSet<>();
        set.add(1);
    }

    @Test
    public void test32(){
        Response<Object> response = Response.success();
        Map<String, String> map = new HashMap<>();
        response.setData(map);


        map.put("key", "value");

        System.out.println(JSONObject.toJSONString(response));


        Integer integer = acquireDeliveryMode();
        System.out.println(integer);
    }

    private Integer acquireDeliveryMode() {
        String no = "jDs";
        return "jid".equalsIgnoreCase(no) ? 1 : 2;
    }

    @Test
    public void test33(){
        Integer code = 2;
        String queryName = "";
        Response<Object> success = Response.success();
        List<User> list = new ArrayList<User>();
        switch (code) {
            case 1:
                doQueryPartnerPageList(list, queryName);
                break;
            case 2:
                doQueryPartnerPageList1(list, queryName);
                break;
        }
        success.setData(list);
        System.out.println(JSONObject.toJSONString(success));
    }

    private void doQueryPartnerPageList(List<User> list, String partnerName) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setId(i);
            user.setUserName("name"+i);
            userList.add(user);
        }
        for (User user : userList) {
            list.add(translateResult(user));
        }
    }

    private void doQueryPartnerPageList1(List<User> list, String partnerName) {
        List<UserDto> userList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UserDto dto = new UserDto();
            dto.setId(i);
            dto.setUserName(i);
            userList.add(dto);
        }
        for (UserDto dto : userList) {
            list.add(translateResult(dto));
        }
    }

    private User translateResult(Object o) {
        User masterInfo = new User();
        if (o instanceof User) {
            User user = ((User) o);
            masterInfo.setUserName(user.getUserName()+"update");
        }
        if (o instanceof UserDto) {
            UserDto user = ((UserDto) o);
            masterInfo.setUserName(user.getUserName()+"dto");
        }
        return masterInfo;
    }


    @Test
    public void test34(){
        //byte、short、int、long、float、double、boolean、char。
        //小到大 隐式转换
        byte b = 1;
        short s = b;
        //大到小 显式转换
        long l = 1L;
        int i = (int) l;

        //short s1=1;s1=s1+1来说，在s1+1运算时会自动提升表达式的类型为int，那么将int赋予给short类型的变量s1会出现类型转换错误。
        //对于short s1=1;s1+=1来说 +=是java语言规定的运算符，java编译器会对它进行特殊处理，因此可以正确编译。
        short s1=1;
        //s1=s1+1;
        short s2=1;
        s2+=1;

        String a = "1";
        String aa = a + 1;
        System.out.println(aa);
        // byte的存储范围小于int，可以向int类型进行隐式转换，所以switch可以作用在byte上
        // long的存储范围大于int，不能向int进行隐式转换，只能强制转换，所以switch不可以作用在long上
        // string在1.7版本之前不可以，1.7版本之后switch就可以作用在string上了
        char type = '2';
        switch (type) { // char byte short int  Byte Short Integer String
            case '1' :
                System.out.println(111);
                break;
            case '2' :
                System.out.println(222);
                break;
        }
    }

    @Test
    public void test35(){
        //它的作用是：如果字符串常量池中已经包含一个等于此String对象的字符串，则返回代表池中这个字符串的String对象；
        // 否则，将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用
        String str = "333";
        String intern = str.intern();
    }

    @Test
    public void test36(){//快速排序
        int [] arr = {2,5,7,33,5,77,22,8,0,3};
        int[] sortArr = quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(sortArr));
    }

    public int [] quickSort(int [] arr, int start, int end){
        if (start < 0 || end > arr.length || start > end) {
            return null;
        }
        int position = getPosition(arr, start, end);
        if (position > start) {
            quickSort(arr, start, position-1);
        }
        if (position < end) {
            quickSort(arr, position+1, end);
        }
        return arr;
    }

    private int getPosition(int[] arr, int start, int end) {
        int pid = end - start;
        int p = start + (int)(Math.random()* (pid + 1));
        swap(arr, p, end);
        int sIndex = start-1;
        for (int i = start; i <= end; i++) {
            if (arr[i] <= arr[end]) {
                sIndex++;
                if (i > sIndex) {
                    swap(arr, i, sIndex);
                }
            }
        }
        return sIndex;
    }

    private void swap(int[] arr, int p, int end) {
        int tmp = arr[p];
        arr[p] = arr[end];
        arr[end] = tmp;
    }



    @Test
    public void test37(){
        String str = "333";
        Long l = Long.valueOf(str);
        System.out.println(l);

        User user = new User();
        user.setUserName("wj");
        try {
            validatorParam("wj", user);
        } catch (Exception e) {
            logger.error("异常:{}", e.getMessage());
        }
    }


    private void validatorParam(String pin, User user) {
        if (org.apache.commons.lang3.StringUtils.isBlank(user.getPassword())){
            throw SafJosExceptionBuilder.build("default.system.cs", "3100", new String[]{"事业部编码"});
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(user.getPassword())) {
            throw SafJosExceptionBuilder.build("用户[" + pin + "]不具有事业部[" + user.getUserName()+ "]的权限");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(user.getPassword())){
            throw SafJosExceptionBuilder.build("default.system.cs", "3100", new String[]{"拦截人"});
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(user.getPassword())) {
            throw SafJosExceptionBuilder.build("default.system.cs", "3100", new String[]{"ERP订单编码或CLPS订单编码"});
        }
    }


    @Test
    public void test38(){
        User user = new User();

        List<Integer> ids = Lists.newArrayList();
//        ids.add(1);
//        ids.add(2);
//        ids.add(3);

//        user.setIds(ids);

       List<Integer> ids1 = user.getIds();
       if (CollectionUtils.isEmpty(ids1)) {
           ids1 = new ArrayList<>();
           user.setIds(ids1);
       }
        if (!ids1.contains(1)) {
            ids1.add(1);
        }
        ids1.add(9);

        logger.error(JSONObject.toJSONString(user));
    }


    @Test
    public void test39(){
        double skuVolume = Double.parseDouble(this.countGoodsVolume());
        BigDecimal totalVolume = new BigDecimal(skuVolume);
        //保留三位小数
        BigDecimal finalTotalVolume = totalVolume.setScale(3, BigDecimal.ROUND_HALF_UP);//保留3位小数，向上舍入
        BigDecimal decimal3 = totalVolume.setScale(3, BigDecimal.ROUND_DOWN);//保留3位小数，默认用四舍五入
        BigDecimal decimal4 = totalVolume.setScale(3, BigDecimal.ROUND_HALF_DOWN);//保留3位小数，向下舍入
        logger.error("总体积:{}", finalTotalVolume);
    }

    private String countGoodsVolume(){
        BigDecimal length = new BigDecimal("2.45066");
        BigDecimal width = new BigDecimal("0.1");
        BigDecimal height = new BigDecimal("1");
        BigDecimal qty = new BigDecimal("1");
        BigDecimal volume = length.multiply(width).multiply(height).multiply(qty);
        return df.format(volume);
    }



    @Test
    public void test40(){
        String no = "TTT01990020011414200";
        long id = 1990020011414200L;
        long l = Long.parseLong(no.substring(3));
        System.out.println(l == id);
    }



    @Test
    public void test41(){
        String str = "编号[XXXXXXXX]不存在，查询结果为空\"信息不存在\"";
        String str1 = "库存不足(商品编码:XXXXXXXX;编码:XXXXXXXX;）库存不足";
        String str2 = "库存不足(商品编码:[111111];编码:[6666666];）库存不足";

        String[] splits = str1.split("XXXXXXXX");
        for (String split : splits) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(split) && str2.contains(split)) {
                System.out.println(split);
                break;
            }
        }

        System.out.println(str1);
    }


    @Test
    public void test42(){
        User user = new User().setUserName("wj").setPassword("123456").setId(2);
        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            String fieldName = field.getName();
            Object fieldValue = getFieldValueByName(fieldName, user);
            if (fieldValue == null || org.apache.commons.lang3.StringUtils.isBlank(fieldValue.toString())) {
                continue;
            }
            logger.error("字段名:{},值:{}", fieldName, fieldValue);
        }
    }

    private Object getFieldValueByName(String fieldName, Object o) {
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

    @Test
    public void test43(){


        SoMainCondition soMainCondition = new SoMainCondition();


        List<String> deptList = Lists.newArrayList();
        deptList.add("1");
        deptList.add("2");
        deptList.add("3");
        deptList.add("4");
        deptList.add("5");

        String[] deptIds3 = deptList.toArray(new String[deptList.size()]);

        logger.error("before");
        buildParam();
        logger.error("after");

        BillExceptionDto param = new BillExceptionDto();
        param.setDeptList(deptList.stream().map(t->Long.parseLong(t)).collect(Collectors.toList()));

        logger.error(JSONObject.toJSONString(param));


        soMainCondition.setDeptList(deptList);




        String[] deptIds = deptList.toArray(new String[deptList.size()]);

        Map<Long,String> map = deptList.stream().collect(Collectors.toMap(t->Long.parseLong(t), t->t,(x, y)->y));
    }

    private void buildParam() {
        try {
            String str = "";
            if (StringUtils.isEmpty(str)) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            logger.error("execption");
        }
    }

    @Test
    public void test44(){
        List<User> list = new ArrayList<>();
        User user;
        for (int i = 0; i < 5; i++) {
            user = new User();
            user.setUserName(String.valueOf(i));
            list.add(user);
        }
        System.out.println(JSONObject.toJSONString(list));
    }


    @Test
    public void test45(){

        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", "");
        Object code1 = map1.get("code");
        String codeStr = "";
        if (code1 != null) {
            codeStr = code1.toString();
        }
        System.out.println(codeStr);

        String s = "{\"code\":200,\"data\":\"{\\n    \\\"create_response\\\": {\\n        \\\"access_token\\\": \\\"16d4-e4f-8\\\",\\n        \\\"expires_at\\\": 0,\\n        \\\"expires_in\\\": 1000000,\\n        \\\"owner_id\\\": \\\"0000\\\",\\n        \\\"owner_name\\\": \\\"3号店\\\",\\n        \\\"r1_expires_at\\\": 0,\\n        \\\"expires_in\\\": 100000,\\n        \\\"r2_expires_at\\\": 0,\\n        \\\"r2_expires_in\\\": 100000,\\n        \\\"refresh_token\\\": \\\"28d4e4f8-a312-\\\",\\n        \\\"refresh_token_expires_at\\\": 0,\\n        \\\"refresh_token_expires_in\\\": 0,\\n        \\\"scope\\\": [\\n            \\\"str\\\"\\n        ],\\n        \\\"w1_expires_at\\\": 0,\\n        \\\"w1_expires_in\\\": 1000000,\\n        \\\"w2_expires_at\\\": 0,\\n        \\\"w2_expires_in\\\": 1000000\\n    }\\n}\",\"message\":\"成功\",\"success\":true}";
        Result result = JSONObject.parseObject(s, Result.class);
        String data = (String) result.getData();
        Map parseObject = JSONObject.parseObject(data, Map.class);

        JSONObject jsonObject = JSONObject.parseObject(s);
        String dataStr = jsonObject.getString("data");
        JSONObject jsonData = JSONObject.parseObject(dataStr);


        User user = new User();
        String wpCode = "4444444";
        //获取token
        String code = "{\"code\":\"code\",\"grant_type\":\"authorization_code\"}";
        //刷新token
        String refreshToken = "{\"refresh_token\":\"refreshToken\",\"grant_type\":\"refresh_token\"}";
        //获取电子面单发货地址 shipNo
        String wpCode1 = "{\"wp_code\":\"wpCode\"}";
        //获取电子面单模板
        String wpCode2 = "{\"wp_code\":\"wpCode\"}";
        //获取自定义电子面单模板
        String template = "{\"template_id\":\"templateId\"}";

        user.setUserName("{\"wp_code\":\"" + wpCode + "\"}");

        Map map = JSONObject.parseObject(user.getUserName(), Map.class);
        Object wp_code = map.get("wp_code");
        System.out.println(wp_code.toString());
    }

    @Test
    public void test46(){
        Integer num = getNum(5);
        System.out.println(num);

    }

    public Integer getNum (Integer i) {
        if (i < 2) {
            return i;
        } else {
            return getNum(i-1)+getNum(i-2);
        }
    }

    public final static String SO_NO_REGEX = "^"+ "CSL"+"[0-9]{9,15}";//由于订单分库增加至14位

    @Test
    public void test47(){
        ErrorUitl.ifBlank("pin", "授权码pin");
        ErrorUitl.ifInValid(SO_NO_REGEX, "CSL00000000000000", "原始出库单号OrderCode");
        Integer[] disposeType = {6,4,2,5};
        Arrays.sort(disposeType);
        System.out.println(Arrays.toString(disposeType));
        List<Integer> list = Arrays.asList(disposeType);
        if (list.contains(4) && list.contains(5)) {
            Collections.swap(list, list.indexOf(4),list.indexOf(5));
        }
        disposeType = list.toArray(new Integer[0]);
        System.out.println(Arrays.toString(disposeType));
        Arrays.sort(disposeType);
        for (int k = disposeType.length-1; k >= 0; k--) {
            System.out.println(disposeType[k]);
        }
    }



    @Test
    public void test48(){
        User user = new User();
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> carrierMap = new HashMap<String, String>(4);
        /*carrierMap.put("carriercode", "carriercode");
        carrierMap.put("waybill", "waybill");
        carrierMap.put("carriername", "carriername");
        carrierMap.put("carrierid", "carrierid");*/
        list.add(carrierMap);

        String s = JSONObject.toJSONString(list);
        //JSONArray jsonArray = JSONObject.parseArray(s);
        user.setUserName(s);
        System.out.println(JSONObject.toJSONString(user));


        String wpCode = "";
        String wpCode1 = "{\"wp_code\":\"" + wpCode + "\"}";
        Map map = JSONObject.parseObject(wpCode1, Map.class);
        if (map != null && map.size() > 0) {
            System.out.println(1);
        }
    }


    @Test
    public void test49(){
        //String consignee = "大**";
        //String consigneeAddr = "常*镇广东省东莞市**镇横江厦水**号**大厦***室";

        String consignee = "小**";
        String consigneeAddr = "桃*街道冠庄**村**号";
        boolean isEncryptInfo = checkConsigneeAndAddr(consignee, consigneeAddr);
        System.out.println(isEncryptInfo);
    }


    public static  boolean checkConsigneeAndAddr(String consignee,String consigneeAddr){
        if(org.apache.commons.lang3.StringUtils.isBlank(consignee) || org.apache.commons.lang3.StringUtils.isBlank(consigneeAddr)){
            return false;
        }
        String check = "*";
        int i=(consigneeAddr.length()-consigneeAddr.replace(check, "").length())/check.length();
        if(consignee.contains(check) && i>=1){
            return true;
        }
        return false;
    }


    @Test
    public void test50(){
        String xml = "<businessMessages xmlns=\"http://boot/BusinessMessages\"><businessMessage><uuid>17077777777777</uuid><source>C</source><topic>11111</topic><bussinessNo>222222222222</bussinessNo><header><vmiFlag>true</vmiFlag><carrierInfo>[{\"carriercode\":\"O\",\"waybill\":\"903\",\"carrierid\":\"3\",\"carriername\":\"圆通-顺丰-11\"}]</carrierInfo></header><body><![CDATA[<pivotFlow xmlns=\"http://boot/PivotFlow\"><orderId>6666666</orderId><operPersonId>c</operPersonId><operTime>2022-05-10T09:16:20.000+08:00</operTime><status>10017</status><appendix></appendix></pivotFlow>]]></body></businessMessage></businessMessages>";
        String statusXml = convertOrderStatusXml(xml);
        System.out.println(statusXml);
    }


    public static String convertOrderStatusXml(String body) {
        String regex = "<carrierInfo>(.*)</carrierInfo>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(body);
        String info = "";
        if (matcher.find()) {
            //info = JSONObject.toJSONString(matcher.group(1));
            info = matcher.group(1);
            info = info.replace("\"", "&quot;");
        }

        body = parseBody(body);
        body = body.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replaceFirst("businessMessages", "businessMessages xmlns=\"http://boot/BusinessMessages\"").replace("<pivotFlow>", "<![CDATA[<pivotFlow xmlns=\"http://boot/PivotFlow\">\r").replace("<vmiAppendix>", "<vmiAppendix>\n" +
                "<![CDATA[").replace("</vmiAppendix>", "]]]]><![CDATA[></vmiAppendix>").replace("</pivotFlow>", "<appendix></appendix></pivotFlow>]]>").replace("<vmiFlag>false</vmiFlag>", "").replace("<vmiFlag>true</vmiFlag>", "<property name=\"vmiFlag\" value=\"true\" />").replace("<carrierInfo>carrierInfoValue</carrierInfo>", "<property name=\"carrierInfo\" value=\""+info+"\" />");
        return body.replace("\n", "").replace("\r", "");
    }

    private static String parseBody(String body) {
        System.out.println(body);
        String startStr="<carrierInfo>";
        String endStr="</carrierInfo>";
        String element="carrierInfoValue";
        String newBody =getNewProcess(startStr, endStr, body, element);
        System.out.println(newBody);
        return newBody;
    }


    public static String getNewProcess(String startStr,String endStr,String oldProcess,String element){
        String process="";
        //获取已经去掉内容的字符串
        String surplusProcess=getSurplusProcess(oldProcess, startStr, endStr);
        //插入特定字符串到特定字符串后面
        process=insertElementToProcess(startStr, surplusProcess,element);
        return process;
    }

    //在特定的字符串后面添加数据
    private static String  insertElementToProcess(String startStr,String surplusProcess,String element) {
        // 原字符串
        StringBuffer s = new StringBuffer(surplusProcess);
        // 要插入的字符串
        String insertStart =element ;
        // 插入位置
        Pattern p = Pattern.compile(startStr);
        Matcher m = p.matcher(s.toString());
        // 插入字符串
        if (m.find()) {
            s.insert((m.start() + startStr.length()), insertStart);
        }
        return s.toString();
    }


    //将两个字符串间的内容去掉
    private static String getSurplusProcess(String oldProcess,String startStr,String endStr) {
        String surplusString="";
        //获取开始子串的索引
        int index1 = oldProcess.indexOf(startStr);
        if (index1 != -1) {
            //获取结束字符的索引
            int index2 = oldProcess.indexOf(endStr, index1);
            if (index2 != -1) {
                //将才分的字符串进行拼接
                String str3 = oldProcess.substring(0, index1+startStr.length());
                String str4=oldProcess.substring(index2, oldProcess.length());
                surplusString=str3+str4;
            }else {
                return oldProcess;

            }
        }else {
            return oldProcess;

        }
        return surplusString;
    }





}
