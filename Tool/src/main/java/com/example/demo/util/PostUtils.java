package com.example.demo.util;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import wiremock.org.apache.http.HttpEntity;
import wiremock.org.apache.http.HttpResponse;
import wiremock.org.apache.http.NameValuePair;
import wiremock.org.apache.http.client.ClientProtocolException;
import wiremock.org.apache.http.client.HttpClient;
import wiremock.org.apache.http.client.ResponseHandler;
import wiremock.org.apache.http.client.entity.UrlEncodedFormEntity;
import wiremock.org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.client.methods.HttpPost;
import wiremock.org.apache.http.entity.StringEntity;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;
import wiremock.org.apache.http.message.BasicNameValuePair;
import wiremock.org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostUtils {
    private static final transient Logger log = (Logger) LoggerFactory.getLogger(PostUtils.class);

    /**
     * HttpRequest
     */

    //Post x-www-form-urlencoded
    static void xFormPost(String url, JSONObject obj) {

        //obj放進params
        List<NameValuePair> params = new ArrayList<>();
        for (String name : obj.keySet()) {
            params.add(new BasicNameValuePair(name, obj.get(name).toString()));
        }

        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String s;
        String Location;
        try {

            log.info("RequestParams[{}]", obj);
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(httpPost);



            //get response.getEntity
            HttpEntity entity = response.getEntity();
            s = EntityUtils.toString(entity);
            log.info("ContentType:[{}]", String.valueOf(entity.getContentType()));
            log.info("Response[{}]", s);

            //get StatusCode
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("statusCode:[{}]", String.valueOf(statusCode));
//            log.info(String.valueOf(statusCode));

            //get Url
//            Location = response.getFirstHeader("Location").toString().replace("Location: ", "");
//            log.info("Location:[{}]", Location);

//            return (null != entity.getContentType()) ? s : Location;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Post JSON
    private static String jsonPost(String url, JSONObject obj) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");

            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {//
                int status = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (status < 200 || status >= 300) {
                    log.info("status:[{}]", status + "");
                }
                return entity != null ? EntityUtils.toString(entity) : null;
            };
            return httpclient.execute(httpPost, responseHandler);

        } catch (Exception e) {
            log.warn(e.toString());
        }
        return null;
    }

    //Get
    private static String httpGet(String url, JSONObject obj) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);

            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                //200~300
                if (status >= 200 && status < 300) {

                    HttpEntity entity = response.getEntity();

                    return entity != null ? EntityUtils.toString(entity) : null;
                }
                //400
                else {
                    throw new ClientProtocolException(
                            "Unexpected response status: " + status);
                }
            };

            return httpclient.execute(httpGet, responseHandler);

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return null;
    }

}
