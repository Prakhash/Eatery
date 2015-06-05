package com.eatery.filterData;

import java.io.InputStream;

/**
 * Created by bruntha on 6/4/15.
 */
public class LanguageDetector {
    ServiceHandler serviceHandler=new ServiceHandler();
    public boolean isEnglish(String review)
    {
        String token="73bc07b472b0ab36b055f0a56d3eb4c9";
        String url="http://ws.detectlanguage.com/0.2/detect?q="+review+"&key="+token;
        InputStream inputStream=serviceHandler.makeServiceCall(url, 1);
        System.out.println("is = "+inputStream);
        return false;
    }
}
