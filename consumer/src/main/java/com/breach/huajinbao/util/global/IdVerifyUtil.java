package com.breach.huajinbao.util.global;

import com.breach.api.idno.IdCard;
import com.breach.api.message.HttpUtils;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class IdVerifyUtil {

    public static String verify(String appCode, String name, String idNo) {
        String host = "https://idenauthen.market.alicloudapi.com";
        String path = "/idenAuthentication";
        String method = "POST";
        String appcode = appCode;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("idNo", idNo);
        bodys.put("name", name);
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //获取response的body
            String x = EntityUtils.toString(response.getEntity());
            System.out.println(x);
            return x;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IdCard jsonStringToObject(String idInfo) {
        // String idInfo = "{\"name\":\"xxx\",\"idNo\":\"xxx\",\"respMessage\":\"xxx\",\"respCode\":\"xxx\",\"province\":\"xxx\",\"city\":\"xxx\",\"county\":\"xxx\",\"birthday\":\"xxx\",\"sex\":\"xxx\",\"age\":\"xxx\"}";
        // 把 json 字符串转为 json
        JSONObject jsonObject = JSONObject.fromObject(idInfo);
        // 把 json 对象转为 bean 对象
        IdCard idCard = (IdCard) JSONObject.toBean(jsonObject, IdCard.class);

        return idCard;
    }

}