package com.eatery.filterData;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * @author Bruntha
 */
public class ServiceHandler {    // to connect the local server

    InputStream is = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;
    public final static int DELETE = 4;
    private static ServiceHandler serviceInstance = null;

    public static ServiceHandler getInstance(){
        if(serviceInstance==null){
            serviceInstance=new ServiceHandler();
        }
        return serviceInstance;
    }

    public InputStream makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    /**
     * Making service call
     *
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     */
    public InputStream makeServiceCall(String url, int method,
                                       List<NameValuePair> params) {
        try {
            // Http client
            HttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                    //// Log.d("HTTP", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    //// Log.d("HTTP POST", httpPost.toString());
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "&" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                //httpGet.setHeader("Authorization", "Bearer NTdkZDE0MWMyYzFkNzRiN2U1YmM5ZjQwNzFhZDc4OTUwNWRmNmJkNzNlNDRlMDM1Y2M4NjhmNGE0NzAwNGMxMQ");
                //// Log.d("HTTP", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                //// Log.d("HTTP POST", httpGet.toString());
                httpResponse = httpClient.execute(httpGet);
                //// Log.d("HTTP POST", httpGet.toString());
            } else if (method == PUT) {
                HttpPut httpPut = new HttpPut(url);
                if (params != null) {
                    httpPut.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpPut);
            }
            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    //use this method in order to check status code
    public HttpResponse makeServiceCallJson(String url, int method, List<NameValuePair> params) {
        HttpResponse httpResponse = null;

        try {
            // Http client
            HttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            final HttpParams httpParams = httpClient.getParams();
            //HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            //HttpConnectionParams.setSoTimeout(httpParams, 10000);

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // Log.d("HTTP POST URL", url);
                // adding post params
                if (params != null) {
                    // Log.d("HTTP PARAMS", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // Log.d("HTTP GET URL", url);
                // appending params to url
                if (params != null) {
                    // Log.d("HTTP PARAMS", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "/" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                //httpGet.setHeader("Authorization", "Bearer ZDdmZTdmNWRjMjZmZmI0YTQwZWYxMzRiOTc0ZDE4ZDgyZWZiNmFhN2YxM2UzZjAyMWQ1NzM0NzI2ZDY2ZjA4NQ");
                httpResponse = httpClient.execute(httpGet);
            } else if (method == PUT) {
                // Log.d("HTTP PUT URL", url);
                HttpPut httpPut = new HttpPut(url);
                if (params != null) {
                    // Log.d("HTTP PARAMS", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    httpPut.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpPut);
            } else if (method == DELETE) {
                // Log.d("HTTP DELETE URL", url);
                HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
                if (params != null) {
                    // Log.d("HTTP PARAMS", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    httpDeleteWithBody.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpDeleteWithBody);

            }
            // Log.d("HTTP RESPOSE CODE", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            // Log.d("HTTP RESPOSE MESSAGE", httpResponse.getStatusLine().getReasonPhrase());
            // Log.d("HTTP RESPOSE ", httpResponse.toString());


        } catch (UnsupportedEncodingException e) {
            // Log.e("ServiceHandler", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // Log.e("ServiceHandler", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // Log.e("ServiceHandler", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    public HttpResponse makeServiceCallJson(String url, int method, List<NameValuePair> params, String accessToken) {
        HttpResponse httpResponse = null;
        try {
            // Http client
            HttpClient httpClient = new DefaultHttpClient();

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Authorization", "Bearer " + accessToken);
                // Log.d("HTTP POST URL", url);
                // adding post params
                if (params != null) {
                    // Log.d("HTTP PARAMS", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpPost);
            } else if (method == GET) {
                // Log.d("HTTP GET URL", url);
                // appending params to url
                if (params != null) {
                    // Log.d("HTTP PARAMS", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "/" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Authorization", "Bearer " + accessToken);
                httpResponse = httpClient.execute(httpGet);
            } else if (method == PUT) {
                // Log.d("HTTP PUT URL", url);
                HttpPut httpPut = new HttpPut(url);
                httpPut.setHeader("Authorization", "Bearer " + accessToken);
                if (params != null) {
                    // Log.d("HTTP PARAMS", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    httpPut.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpPut);
            } else if (method == DELETE) {
                // Log.d("HTTP DELETE URL", url);
                HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
                httpDeleteWithBody.setHeader("Authorization", "Bearer " + accessToken);
                if (params != null) {
                    // Log.d("HTTP PARAMS", EntityUtils.toString(new UrlEncodedFormEntity(params)));
                    httpDeleteWithBody.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpDeleteWithBody);
            }
            // Log.d("HTTP RESPOSE CODE", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            // Log.d("HTTP RESPOSE MESSAGE", httpResponse.getStatusLine().getReasonPhrase());
        } catch (UnsupportedEncodingException e) {
            // Log.e("ServiceHandler", e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // Log.e("ServiceHandler", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // Log.e("ServiceHandler", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }
}


