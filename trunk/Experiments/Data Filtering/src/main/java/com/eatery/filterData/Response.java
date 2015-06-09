package com.eatery.filterData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruntha on 6/6/15.
 */
public class Response {
    private ServiceHandler serviceHandler=new ServiceHandler();

    public void getResponse()
    {
        String url="http://wck2.companieshouse.gov.uk//companysearch?cnumb=5111522&stype=A&live=on&cosearch=1";

        HttpResponse httpResponse=serviceHandler.makeServiceCallJSon(url, 1);
        System.out.println("is = "+httpResponse.toString());

        try {
            System.out.println(inputStreamToString(httpResponse.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fast Implementation
    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return full string
        return total;
    }

    // Slow Implementation
//    private String inputStreamToString(InputStream is) {
//        String s = "";
//        String line = "";
//
//        // Wrap a BufferedReader around the InputStream
//        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//
//        // Read response until the end
//        while ((line = rd.readLine()) != null) { s += line; }
//
//        // Return full string
//        return s;
//    }
}
