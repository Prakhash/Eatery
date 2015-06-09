package com.eatery.filterData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bruntha on 5/27/15.
 */

public class JsonReader {
    final static Charset ENCODING = StandardCharsets.UTF_8;
    final static String filePath = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "yelp_academic_dataset_review_restaurants.json";
    final static String businessID = "A4q9jha6NNbbcuF5RFy7sg";
    static ArrayList<String> businessIDs = new ArrayList<>();
    static ArrayList<Integer> randomNumber = new ArrayList<>();


    static int noOfRestaurants = 0;
    static int noOfRestaurantsReviews = 0;

    public void readLargerTextFileAlternate(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                //process each line in some way
                System.out.println(line);
            }
        }
    }

    public static void writePrintStream(String line) {
        PrintStream fileStream = null;
        File file = new File("/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/review_100_D.json");

        try {
            fileStream = new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileStream.println(line);
        fileStream.close();


    }

    public static void write() {
        try {

            String content = "This is the content to write into file";

            File file = new File("/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/new.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void readLinesUsingFileReader() throws IOException {
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        int noOfLines = 0;
        int written=0;
        while ((line = br.readLine()) != null) {
            noOfLines++;
//            System.out.println(noOfLines+" "+line);
            if (randomNumber.contains(noOfLines)) {
                written++;
                System.out.println(written + " " + line);
                writePrintStream(line);
            }
//            getJSON(line);
        }
        System.out.println("NoOfBussiness = " + noOfLines);
//        System.out.println("NoOfRestaurants = "+noOfRestaurants);
//        System.out.println(noOfRestaurantsReviews);
        br.close();
        fr.close();
    }

    private static void readLinesUsingFileReader(String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        int noOfLines = 0;
        while ((line = br.readLine()) != null) {
//            System.out.println(line);
            getBussinessIDS(line);
//            noOfLines++;
        }
//        System.out.println("NoOfBussiness = "+noOfLines);
//        System.out.println("NoOfRestaurants = "+noOfRestaurants);
//        System.out.println(noOfRestaurantsReviews);
        br.close();
        fr.close();
    }

    private static void extractRestaurantReviews(String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        int noOfLines = 0;
        while ((line = br.readLine()) != null) {
//            System.out.println(line);
            writeRestaurantReviews(line);
            noOfLines++;
        }
        System.out.println("NoOfBussiness = " + noOfLines);
//        System.out.println("NoOfRestaurants = "+noOfRestaurants);
        System.out.println(noOfRestaurantsReviews);
        br.close();
        fr.close();
    }


    public static void main(String[] args) {

        JsonReader jsonReader = new JsonReader();
        try {
            for (int i = 0; i < 100; i++) {
                randomNumber.add(generateRandomNumbers());
            }
            jsonReader.readLinesUsingFileReader();
//            jsonReader.extractRestaurantReviews("/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_review.json");
//            System.out.println("No Of Business IDS = " + businessIDs.size());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int generateRandomNumbers() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(990627);
    }

    public void getJSON() {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("/home/bruntha/Documents/FYP/Data/" +
                    "yelp_dataset_challenge_academic_dataset/sample.json"));

            JSONObject jsonObject = (JSONObject) obj;

            String name = (String) jsonObject.get("type");
            System.out.println(name);

            String age = (String) jsonObject.get("business_id");
            System.out.println(age);

//            String aa = (String) jsonObject.get("checkin_info");
            System.out.println(jsonObject.get("checkin_info"));

//            // loop array
//            JSONArray msg = (JSONArray) jsonObject.get("checkin_info");
//            Iterator<String> iterator = msg.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public static void getJSON(String json) {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            String bID = (String) jsonObject.get("business_id");

            businessIDs.add(bID);

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

    public static void getBussinessIDS(String json) {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            String bID = (String) jsonObject.get("business_id");
            System.out.println("Bussiness ID = " + bID);
            businessIDs.add(bID);

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

    public static void writeRestaurantReviews(String json) {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            String bID = (String) jsonObject.get("business_id");

            System.out.println("BussinessID Review = " + bID);

            if (businessIDs.contains(bID)) {
                System.out.println(json);
                noOfRestaurantsReviews++;

                writePrintStream(json);
            }


        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

}