package com.dj.boot.common.util.es.test;

import com.dj.boot.common.util.date.DateUtils;
import com.dj.boot.common.util.es.*;
import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.common.util.es.test
 * @Author: wangJia
 * @Date: 2021-04-23-17-57
 */
public class EsUtilTest extends BaseController {

    private static final String ES_USER_INDEX_PRE = "user";
    private static final String ES_USER_TYPE = "user";
    private static final Map<String, Field> USR_FIELD_MAP;

    static {
        USR_FIELD_MAP = extractField(User.class);
    }

    private static Map<String, Field> extractField(Class<?> clz) {
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        Field[] fields = clz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            fieldMap.put(f.getName(), f);
        }
        return fieldMap;
    }

    @Test
    public void test() throws Exception {
        User user = new User();
        user.setPhone("183XXXXX");
        user.setUserName("1");
        user.setPassword("2");
        String params = generateQueryParamQuery(user);
        logger.error("通过es查询运单列表，查询参数为：" + params);

        String wbYear = "2022";
        String url = ES_USER_INDEX_PRE+"_"+wbYear+"/"+ES_USER_TYPE+"/_search";
        logger.error("通过es查询运单列表，查询参数为：{}，url：{}",params,url);
        String v2 = EsClientWbMain.clientRest(ElasticStrategy.Random, HttpMethod.POST, url, params);//正确参数
        logger.error("通过es查询运单列表，查询结果:"+v2);
        EsResultEntity resultsFromHttp = JsonUtil.fromJson(v2, EsResultEntity.class);
        EsResultEntity.Hits hits = resultsFromHttp.getHits();
        if (hits != null && hits.getHits() != null) {
            List<EsResultEntity.Record> records = hits.getHits();
            List<User> list = new ArrayList<User>();
            for (EsResultEntity.Record record : records) {
                User user1 = new User();
                Map<String, String> sourceEs = record.get_source();
                list.add(parseEsResult(user1, sourceEs, USR_FIELD_MAP));
            }
            Integer total = hits.getTotal();
        }
    }


    private <T> T parseEsResult(T target, Map<String, String> raw, Map<String, Field> fieldMap) throws IllegalAccessException {
        final Set<String> fieldsKey = raw.keySet();
        Field field;
        Object value;
        for (String fieldKey : fieldsKey) {
            field = fieldMap.get(translateField(fieldKey));
            if (field != null) {
                value = getValue(field, raw.get(fieldKey));
                if(value!=null) {
                    field.set(target, value);
                }
            }
        }
        return target;
    }


    private String translateField(String fieldKey) {
        final String[] split = fieldKey.split("_");
        final StringBuilder builder = new StringBuilder(split[0]);
        String temp;
        for (int i = 1; i < split.length; i++) {
            temp = split[i];
            builder.
                    append(temp.substring(0, 1).toUpperCase())
                    .append(temp.substring(1));
        }
        return builder.toString();
    }


    private Object getValue(Field field, String rawVal) {
        if ("ts".equals(field.getName())) {
            return null;
        }
        if (StringUtils.isEmpty(rawVal)) {
            return rawVal;
        }
        try {
            if (field.getType() == String.class) {
                return rawVal;
            } else if (field.getType() == Date.class) {
                return DateUtils.addHours(DateUtils.parseDate(rawVal, "yyyy-MM-dd'T'HH:mm:ss"), 8);
            } else if (field.getType() == BigDecimal.class) {
                return new BigDecimal(rawVal);
            } else if(field.getType() == int.class) {
                return Integer.valueOf(rawVal);
            }else {
                return field.getType().getDeclaredMethod("valueOf", String.class).invoke(null, rawVal);
            }
        } catch (Exception e) {
            logger.error("反射获取数据出错", e);
            return null;
        }
    }

    private String generateQueryParamQuery(User user) throws Exception {

        return generateQueryParam(user, new QueryCondition(){
            @Override
            public void setupCondition(EsQueryParam esQueryParam, Map<String, Object> queryParam) {
                Set<Map.Entry<String,Object>> entrySet=queryParam.entrySet();
                EsQueryParam.Query query=esQueryParam.getQuery();
                EsQueryParam.Bool bool=query.getBool();
                if(queryParam.containsKey("id")){
                    queryParam.remove("id");
                }
                for (Map.Entry<String,Object> entry:entrySet) {
                    if(StringUtils.equals(entry.getKey(),"id")) {
                        bool.addNewTermsToFilter("id", (List) entry.getValue());
                        continue;
                    }
                    bool.addNewTermToMust(entry.getKey(), entry.getValue());
                }
            }
        });
    }


    private <T> String generateQueryParam(Object condition, QueryCondition queryCondition) throws Exception {
        EsQueryParam esQueryParam = new EsQueryParam();

        Map<String, Object> queryParam = new HashMap<String, Object>();
        EsmUtils.prepareEsQueryParam(condition, condition.getClass(), condition.getClass().getDeclaredFields(), queryParam);
        return JsonUtil.toJson(esQueryParam);
    }








    public interface QueryCondition {
        void setupCondition(EsQueryParam esQueryParam, Map<String, Object> queryParam);
    }
}
