package com.eatery.filterData;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
//import org.apache.http.annotation.NotThreadSafe;
import java.net.URI;

/**
 * Created by bruntha on 3/12/15.
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";
    public String getMethod() { return METHOD_NAME; }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }
    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }
    public HttpDeleteWithBody() { super(); }
}