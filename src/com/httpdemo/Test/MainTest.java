package com.httpdemo.Test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MainTest {
    public static void main(String[] args) throws IOException {
        //我们可以使用一个Builder来设置UA字段，然后再创建HttpClient对象
        HttpClientBuilder builder = HttpClients.custom();

        //对照UA字串的标准格式理解一下每部分的意思
        builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");
        final CloseableHttpClient httpclient = builder.build();

        HttpPost httpPost = new HttpPost("http://103.247.176.245:8081/tool/parser/");

        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpPost.setHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryJ2CGHPDI7uG9Omow");
        httpPost.setHeader("Cache-Control", "max-age=0");
        httpPost.setHeader("Referer","http://103.247.176.245:8081/tool/parser/");
        httpPost.setHeader("Cookie","csrftoken=DX6CznKDmH4Y3YKlVHMtyavKYB0KmNFx");

        StringBody token = new StringBody("DX6CznKDmH4Y3YKlVHMtyavKYB0KmNFx", ContentType.TEXT_PLAIN);
        StringBody input = new StringBody("ལྒང་ལིའི་སྦྱོང་བརྡར་དུས་ཚོད་གསུམ་གྱི་སྟེང་འགོ་རྩོམ་རྒྱུ་རེད།", ContentType.TEXT_PLAIN);
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("csrfmiddlewaretoken", token)
                .addPart("input_text", input).build();
        httpPost.setEntity(reqEntity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
            }
            EntityUtils.consume(resEntity);
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            response.close();
        }
    }
}
