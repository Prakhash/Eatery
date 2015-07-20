package com.eatery;

import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by bruntha on 6/4/15.
 */
public class FilterEnglishReviews {
    final static String filePathRead = "/home/bruntha/Documents/FYP/Data/English/" +
            "filteredEnglishReviews.json";
    final static String filePathWrite = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "test.json";
    final static String filePathWriteIndex = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "index.txt";
    static int count = 0;
    static int noOfNonEngRev = 0;
    static int noOfEngRev = 0;
    static int totalReviewsViewed = 0;
    static ArrayList<String> reviewIDS =new ArrayList<>();

    static LanguageDetect languageDetect = new LanguageDetect();

    public static void main(String[] args) {

        FilterEnglishReviews filterEnglishReviews = new FilterEnglishReviews();
        try {
//            filterEnglishReviews.readLinesUsingFileReader(1,3); //reading json line one by one
            count();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static synchronized void readLinesUsingFileReader(int startPosition,int endPosition) throws IOException {
        File file = new File(filePathRead);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            if (startPosition <= totalReviewsViewed+1)
                splitJson(line);    //splitting each json into ...
            totalReviewsViewed++;
            if (startPosition < totalReviewsViewed)
                writePrintStream(totalReviewsViewed);

            if(endPosition==totalReviewsViewed)
                break;
        }
        System.out.println("English reviews = " + noOfEngRev);
        br.close();
        fr.close();
    }

    private static synchronized void count() throws IOException {
        File file = new File(filePathRead);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
//            System.out.println(line);
                splitJsonCount(line);    //splitting each json into ...
        }
        System.out.println("English reviews = " + noOfEngRev);
        br.close();
        fr.close();
    }

    private static void readLinesUsingFileReaderAndPrintJSON() throws IOException {
        File file = new File(filePathRead);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            count++;
            System.out.println(line);

//            if(line.contains("6w6gMZ3iBLGcUM4RBIuifQ"))
//            {
//                System.out.println("yes");
//            }
//            if(count==300)
//                break;
        }
        System.out.println("Reviews = " + count);
        br.close();
        fr.close();
    }

    public static synchronized void writePrintStream(String line) {
        PrintStream fileStream = null;
        File file = new File(filePathWrite);

        try {
            fileStream = new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileStream.println(line);
        fileStream.close();


    }

    public static synchronized void writePrintStream(int line) {
        PrintStream fileStream = null;
        File file = new File(filePathWriteIndex);

        try {
            fileStream = new PrintStream(new FileOutputStream(file, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileStream.println(line);
        fileStream.close();


    }

    public static void splitJson(String json) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            String review = (String) jsonObject.get("text");    // get review text from json
            boolean isEnglish = languageDetect.isEnglish(review); //checking whether review is english or not
            System.out.println("Review " + (totalReviewsViewed + 1) + " is English = " + isEnglish);
            if (isEnglish) {
                writePrintStream(json); //write review as json if it is english
            }

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public static void splitJsonCount(String json) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

//        System.out.println(json);
        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

//            String review = (String) jsonObject.get("text");    // get review text from json
            String reviewID= (String) jsonObject.get("review_id");

            if (reviewIDS.contains(reviewID)) {
                System.out.println("COME OVER   "+reviewID);
            }else {
                noOfEngRev++;
                reviewIDS.add(reviewID);
            }

            System.out.println(noOfEngRev);

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}
