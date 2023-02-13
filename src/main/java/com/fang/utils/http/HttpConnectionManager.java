package com.fang.utils.http;


import com.fang.constants.HeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.UUID;

/**
 * http连接管理
 *
 * @author chenjp
 */
@Slf4j
public final class HttpConnectionManager {

    private static final String HTTPS = "https";
    private static final String HTTP = "http";

    private static final String TL_SV = "TLSv1.2";

    private HttpConnectionManager() {

    }

    private static final PoolingHttpClientConnectionManager httpManager = create(10000, 5000);

    public static HttpClientConnectionManager getDefault() {
        return httpManager;
    }

    public static PoolingHttpClientConnectionManager create(int maxTotal, int maxPerRoute) {
        SSLConnectionSocketFactory factory = getSSLFactory();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HTTPS, factory)
                .register(HTTP, PlainConnectionSocketFactory.INSTANCE)
                .build();
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        manager.setMaxTotal(maxTotal);
        manager.setDefaultMaxPerRoute(maxPerRoute);
        return manager;
    }

    public static SSLConnectionSocketFactory getSSLFactory() {
        SSLConnectionSocketFactory factory = null;
        try {
            //  支持无认证ssl
            SSLContext sslContext = SSLContext.getInstance(TL_SV);
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                }
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, null);
            factory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("get SSLConnectionSocketFactory error，message：{}", e.getMessage(), e);
        }
        return factory;
    }

    public static CloseableHttpClient getHttpClient(HttpClientConnectionManager clientConnectionManager) {
        return HttpClients.custom().setConnectionManager(clientConnectionManager)
                .addInterceptorLast((HttpRequestInterceptor) (httpRequest, httpContext) -> {
            //  将traceId加上（目前先随机uuid）
            headerAdd(httpRequest, HeaderConstant.TRACE_KEY, UUID.randomUUID().toString());
        }).build();
    }

    public static CloseableHttpClient getHttpClient() {
        return getHttpClient(getDefault());
    }

    private static void headerAdd(HttpRequest httpRequest, String key, String value) {
        if(httpRequest.getFirstHeader(key) == null || StringUtils.isBlank(httpRequest.getFirstHeader(key).getValue())) {
            if(StringUtils.isNotBlank(value)) {
                httpRequest.addHeader(key, value);
            }
        }
    }

}
