package com.ikamobile.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangcheng on 2016/8/25.
 */
@Slf4j
public class HttpsUtils {

    public static final HttpClientConnectionManager httpClientConnectionManager = getHttpClientConnectionManager();

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    private static final String DEFAULT_ENCODE="UTF-8";

    public static String post(String url, HttpEntity entity,String contentType) throws Exception{
        HttpClient client= getHttpClient();
        HttpResponse response=null;
        try {
            HttpPost post=new HttpPost(url);
            post.setEntity(entity);
            if(contentType.equals("JSON")){
                post.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            }
            response=client.execute(post);
            if(response.getStatusLine().getStatusCode() != 200){

                log.error("oops ,the http request return back the response[{}] unexpectedly",response);
                log.error("invoke obt [POST] remote service occurred some ex,http status code==>{}",response.getStatusLine().getStatusCode());
                log.error("the follow info maybe is useful,url[{}],params[{}]",url,EntityUtils.toString(entity));
                log.error("the response body===>{}", EntityUtils.toString(response.getEntity()));
                throw new RemoteException("远程调用错误");
            }
            HttpEntity responseEntity = response.getEntity();
            String result= EntityUtils.toString(responseEntity);
            return result;
        }catch (Exception e){
            log.error("HttpUtils [POST] method occurred some ex==>",e);
            throw e;
        }
    }

    public static String postForm(String url, Map<String,String> params) throws Exception {
        List<NameValuePair> nvps=new ArrayList<NameValuePair>();
        for(String key:params.keySet())
        {
            if(params.get(key) == null){
                continue;
            }
            nvps.add(new BasicNameValuePair(key,params.get(key).toString()));
        }
        return post(url,new UrlEncodedFormEntity(nvps, DEFAULT_ENCODE),"FORM");
    }
    public static String postJSON(String url, String jsonString) throws Exception {

        StringEntity entity = new StringEntity(jsonString);
        entity.setContentType(CONTENT_TYPE_TEXT_JSON);
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        return post(url, entity, "JSON");
    }

    public static String get(String url,Map<String,String> params) throws Exception {
        log.debug("http get url:"+url);
        log.debug("http get params:"+params);
        StringBuilder paramStr=new StringBuilder("");
        try {
            if(params!=null) {
                for (String key : params.keySet()) {
                    if (params.get(key) == null) {
                        continue;
                    }
                    String val = params.get(key).toString();
                    if (!StringUtils.isEmpty(val)) {
                        paramStr.append("&").append(key).append("=").append(URLEncoder.encode(val, DEFAULT_ENCODE));
                    }
                }
            }
            String s = StringUtils.replaceOnce(paramStr.toString(), "&", "?");
            String reqUrl = url+s;
            log.info("reqUrl---------->{}",reqUrl);
            HttpGet httpGet=new HttpGet(reqUrl);
            HttpClient client=getHttpClient();

            HttpResponse response = client.execute(httpGet);
            if(response.getStatusLine().getStatusCode() != 200){
                log.error("oops ,the http request return back the response[{}] unexpectedly",response);
                log.error("invoke obt [GET] remote service occurred some ex,http status code==>{}",response.getStatusLine().getStatusCode());
                log.error("the follow info maybe is useful,url[{}],params[{}],urlEncode[{}]",url,params,DEFAULT_ENCODE);
                log.error("the response body===>{}", EntityUtils.toString(response.getEntity()));
                throw new RemoteException("远程调用错误");
            }

            log.debug("get请求response: "+response);
            HttpEntity entity = response.getEntity();
            String result=EntityUtils.toString(entity);
            log.debug("http get result:"+result);
            return result;
        }catch (Exception e){
            log.error("HttpUtils [GET] method occurred some ex==>",e);
            throw e;
        }
    }

    public static HttpClientConnectionManager getHttpClientConnectionManager() {
        // 创建TrustManager
        try {
            X509TrustManager x509TrustManager = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                    chain[0].getPublicKey();
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            };
            SSLContext ctx = SSLContext.getInstance("SSL");

            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[]{x509TrustManager}, null);

            // LayeredConnectionSocketFactory
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(ctx,
                    NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory()) //使用http协议请解开该注释
                    .register("https", sf)
                    .build();
            HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
            return cm;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static HttpClientBuilder getHttpClientBuilder(){
        HttpClientBuilder clientBuilder = HttpClients.custom().setConnectionManager(httpClientConnectionManager);
//        clientBuilder.setMaxConnTotal(100);
        return clientBuilder;
    }

    public static CloseableHttpClient getHttpClient(){
        return getHttpClientBuilder().build();
    }





}
