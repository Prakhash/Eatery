package com.eatery;

import org.json.simple.JSONObject;

import java.io.*;

/**
 * Created by bruntha on 6/4/15.
 */
public class FilterEnglishReviews {
    final static String filePathRead = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "review_400.json";
    final static String filePathWrite = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "test.json";
    static int count = 0;
    static int noOfNonEngRev = 0;
    static int noOfEngRev = 0;
    static int totalReviewsViewed = 0;

    static LanguageDetect languageDetect=new LanguageDetect();

    public static void main(String[] args) {

        FilterEnglishReviews filterEnglishReviews = new FilterEnglishReviews();
        try {
            filterEnglishReviews.readLinesUsingFileReader(); //reading json line one by one
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static synchronized void readLinesUsingFileReader() throws IOException {
        File file = new File(filePathRead);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
                splitJson(line);    //splitting each json into ...
                totalReviewsViewed++;
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

    public static void splitJson(String json) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            String review = (String) jsonObject.get("text");    // get review text from json
            boolean isEnglish=languageDetect.isEnglish(review); //checking whether review is english or not
            System.out.println("Review "+(totalReviewsViewed+1)+" is English = "+isEnglish);
            if(isEnglish){
                writePrintStream(json); //write review as json if it is english
            }

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}
