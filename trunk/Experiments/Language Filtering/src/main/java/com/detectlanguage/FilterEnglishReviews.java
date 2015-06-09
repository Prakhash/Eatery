package com.detectlanguage;

import com.detectlanguage.errors.APIError;
import org.json.simple.JSONObject;

import java.io.*;

/**
 * Created by bruntha on 6/4/15.
 */
public class FilterEnglishReviews {
    final static String filePath = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "yelp_academic_dataset_review_restaurants.json";
    static int count = 0;
    static int noOfNonEngRev = 0;
    static int noOfEngRev = 0;
    static int totalReviewsViewed = 0;

    public static void main(String[] args) {

        FilterEnglishReviews jsonReader = new FilterEnglishReviews();
        try {
            jsonReader.readLinesUsingFileReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readLinesUsingFileReader() throws IOException {
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            if (noOfEngRev >= 1000) {
                break;
            } else {
                getJSON(line);
                totalReviewsViewed++;
            }
        }
        System.out.println("Non english reviews = " + noOfNonEngRev);
        br.close();
        fr.close();
    }

    private static void readLinesUsingFileReaderAndPrintJSON() throws IOException {
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            count++;
            System.out.println(line);
        }
        System.out.println("Reviews = " + count);
        br.close();
        fr.close();
    }

    public static void writePrintStream(String line) {
        PrintStream fileStream = null;
        File file = new File("/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/first_1000_english_reviews.json");

        try {
            fileStream = new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileStream.println(line);
        fileStream.close();


    }

    public static void getJSON(String json) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            String review = (String) jsonObject.get("text");
//            System.out.println(review);

                try {
                    String language = DetectLanguage.simpleDetect(review);
                    System.out.println("Review No = "+(totalReviewsViewed+1)+", Language = " + language );
                    if (!language.equals("en")) {
                        noOfNonEngRev++;
                    }else {
                        writePrintStream(json);
                        noOfEngRev++;
                    }
                } catch (APIError apiError) {
                    apiError.printStackTrace();
                }

//            if(bID.equals(businessID))
//            {
//                noOfRestaurantsReviews++;
//            }
//            JSONArray categories = (JSONArray) jsonObject.get("categories");
//
//            if(categories.contains("Restaurants")){
//                noOfRestaurants++;
//                writePrintStream(json);
////                Long reviewCount= (Long) jsonObject.get("review_count");
////                noOfRestaurantsReviews+=reviewCount;
//            }


//            String age = (String) jsonObject.get("business_id");
//            System.out.println(age);
//
////            String aa = (String) jsonObject.get("checkin_info");
//            System.out.println(jsonObject.get("checkin_info"));

//            // loop array
//            JSONArray msg = (JSONArray) jsonObject.get("checkin_info");
//            Iterator<String> iterator = msg.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public void sortByDate()
    {

    }
}
