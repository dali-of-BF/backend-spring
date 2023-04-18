package com.backend.utils.http;

import cn.hutool.core.io.IoUtil;
import com.backend.constants.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * http请求工具类
 *
 * @author chenjp
 */
@Slf4j
@SuppressWarnings("unchecked")
public final class HttpHelper {

    private HttpHelper() {

    }

    /**
     * json方式调用GET
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T getJson(String url, Map<String, Object> params, Class<T> tClass) {
        return requestJson(new HttpGet(urlAddParams(url, params)), null, tClass);
    }

    /**
     * json方式调用GET
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T getJson(String url, Map<String, Object> params, Map<String, Object> header, Class<T> tClass) {
        return requestJson(new HttpGet(urlAddParams(url, params)), header, tClass);
    }

    /**
     * json方式调用POST
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T postJson(String url, Map<String, Object> params, Map<String, Object> header, Class<T> tClass) {
        return requestJsonByEnclosing(new HttpPost(url), params, header, tClass);
    }

    public static <T> T postJson(String url, List<Map<String, Object>> params, Map<String, Object> header, Class<T> tClass) {
        return requestJsonByEnclosing(new HttpPost(url), params, header, tClass);
    }

    /**
     * json方式调用POST
     *
     * @param url    请求地址
     * @param params 请求参数\
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T postJson(String url, Map<String, Object> params, Class<T> tClass) {
        return requestJsonByEnclosing(new HttpPost(url), params, null, tClass);
    }

    /**
     * 表单方式调用POST
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T post(String url, Map<String, Object> params, Map<String, Object> header, Class<T> tClass) {
        return requestByEnclosing(new HttpPost(url), params, header, tClass);
    }

    /**
     * 普通的get请求
     *
     * @param url    请求地址
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T get(String url, Class<T> tClass) {
        return get(url, null, tClass);
    }

    /**
     * 普通的get请求
     *
     * @param url    请求地址
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T get(String url, Map<String, Object> header, Class<T> tClass) {
        return request(new HttpGet(url), header, tClass);
    }

    /**
     * 普通的get请求
     *
     * @param url    请求地址
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T get(String url, Map<String, Object> params, Map<String, Object> header, Class<T> tClass) {
        return request(new HttpGet(urlAddParams(url, params)), header, tClass);
    }

    /**
     * header请求
     *
     * @param url    请求地址
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T header(String url, Map<String, Object> header, Class<T> tClass) {
        return request(new HttpHead(url), header, tClass);
    }

    /**
     * trace请求
     *
     * @param url    请求地址
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T trace(String url, Map<String, Object> header, Class<T> tClass) {
        return request(new HttpTrace(url), header, tClass);
    }

    /**
     * delete请求
     *
     * @param url    请求地址
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T delete(String url, Map<String, Object> header, Class<T> tClass) {
        return request(new HttpDelete(url), header, tClass);
    }

    /**
     * json方法调用delete
     *
     * @param url    请求地址
     * @param header 请求头
     * @param params 请求参数
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T deleteJson(String url, Map<String, Object> header, Map<String, Object> params, Class<T> tClass) {
        return requestJsonByEnclosing(new HttpDeleteWithBody(url), params, header, tClass);
    }

    /**
     * 表单方式调用put
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T put(String url, Map<String, Object> params, Map<String, Object> header, Class<T> tClass) {
        return requestByEnclosing(new HttpPut(url), params, header, tClass);
    }

    /**
     * patch请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T patch(String url, Map<String, Object> params, Map<String, Object> header, Class<T> tClass) {
        return requestByEnclosing(new HttpPatch(url), params, header, tClass);
    }

    /**
     * options请求
     *
     * @param url    请求地址
     * @param header 请求头
     * @param tClass 转换类型
     * @return 指定的类型
     */
    public static <T> T options(String url, Map<String, Object> header, Class<T> tClass) {
        return request(new HttpOptions(url), header, tClass);
    }


    private static <T> T request(HttpRequestBase requestBase, Map<String, Object> header, Class<T> tClass) {
        try {
            HttpClient httpClient = HttpConnectionManager.getHttpClient();
            addHeader(requestBase, header);
            HttpResponse response = httpClient.execute(requestBase);
            return getResponse(response, tClass);
        } catch (IOException e) {
            log.error("http response error", e);
        }
        return null;
    }

    private static <T> T requestJson(HttpRequestBase requestBase, Map<String, Object> header, Class<T> tClass) {
        try {
            HttpClient httpClient = HttpConnectionManager.getHttpClient();
            requestBase.addHeader("Content-Type", "application/json;charset=UTF-8");
            addHeader(requestBase, header);
            HttpResponse response = httpClient.execute(requestBase);
            return getResponse(response, tClass);
        } catch (IOException e) {
            log.error("http response error", e);
        }
        return null;
    }

    private static <T> T requestJsonByEnclosing(HttpEntityEnclosingRequestBase requestBase,
                                                Object params, Map<String, Object> header, Class<T> tClass) {
        try {
            HttpClient httpClient = HttpConnectionManager.getHttpClient();
            addHeader(requestBase, header);
            setJsonEntity(requestBase, params);
            HttpResponse response = httpClient.execute(requestBase);
            return getResponse(response, tClass);
        } catch (IOException e) {
            log.error("http response error", e);
        }
        return null;
    }


    private static void addHeader(HttpRequestBase requestBase, Map<String, Object> header) {
        if (MapUtils.isNotEmpty(header)) {
            header.forEach((key, value) -> {
                if (!requestBase.containsHeader(key)) {
                    requestBase.addHeader(key, value != null ? value.toString() : Constant.EMPTY);
                }
            });
        }
    }

    /**
     * 支持params的value是数组的形式。比如MultiValueMap
     */
    private static String urlAddParams(String url, Map<String, ?> params) {
        if (MapUtils.isNotEmpty(params)) {
            try {
                URIBuilder uriBuilder = new URIBuilder(url);
                for (String key : params.keySet()) {
                    Object v = params.get(key);
                    if (v instanceof List) {
                        for (Object it : (List<?>) v) {
                            uriBuilder.addParameter(key, String.valueOf(it));
                        }
                    } else {
                        if (Objects.nonNull(v)){
                            uriBuilder.addParameter(key, String.valueOf(v));
                        }
                    }
                }
                return uriBuilder.toString();
            } catch (URISyntaxException e) {
                log.error("URISyntaxException，message：{}", e.getMessage(), e);
            }
        }
        return url;
    }

    private static void setJsonEntity(HttpEntityEnclosingRequestBase requestBase, Object params) {
        try {
            String json = Objects.nonNull(params)?new JsonMapper().writeValueAsString(params):null;
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            requestBase.setEntity(entity);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException，message：{}", e.getMessage(), e);
        }
    }

    private static <T> T requestByEnclosing(HttpEntityEnclosingRequestBase requestBase,
                                            Map<String, Object> params, Map<String, Object> header, Class<T> tClass) {
        try {
            HttpClient httpClient = HttpConnectionManager.getHttpClient();
            addHeader(requestBase, header);
            setUrlEncodedFormEntity(requestBase, params);
            HttpResponse response = httpClient.execute(requestBase);
            return getResponse(response, tClass);
        } catch (IOException e) {
            log.error("http response error", e);
        }
        return null;
    }

    private static void setUrlEncodedFormEntity(HttpEntityEnclosingRequestBase requestBase, Map<String, Object> params) {
        List<NameValuePair> formParams = new ArrayList<>();
        if (MapUtils.isNotEmpty(params)) {
            params.forEach((key, value) -> {
                if (value != null) {
                    formParams.add(new BasicNameValuePair(key, value.toString()));
                } else {
                    formParams.add(new BasicNameValuePair(key, null));
                }

            });
        }
        try {
            requestBase.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException, message: {}", e.getMessage(), e);
        }
    }

    private static <T> T getResponse(HttpResponse response, Class<T> tClass) {
        if (response.getEntity() == null) {
            return null;
        }
        try {
            InputStream resp = response.getEntity().getContent();
            T v;
            if (tClass == String.class) {
                v = (T) IoUtil.read(resp, StandardCharsets.UTF_8);
            } else if (tClass == HttpResponse.class) {
                v = (T) response;
            } else {
                v = Objects.nonNull(resp)?new JsonMapper().readValue(resp, tClass):null;
            }
            return v;
        } catch (IOException e) {
            log.error("http response error", e);
        } finally {
            if (tClass != HttpResponse.class) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error("http response error", e);
                }
            }
        }
        return null;
    }


}
