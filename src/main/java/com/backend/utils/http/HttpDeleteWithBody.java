package com.backend.utils.http;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * @author FPH
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

    public static final String METHOD_NAME = "DELETE";

    public HttpDeleteWithBody() {
        super();
    }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}
