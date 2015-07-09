package com.eatery;

import org.json.simple.JSONObject;

import java.io.*;

/**
 * Created by bruntha on 7/9/15.
 */
public class SeparateReviews {
    final static String filePathRead = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "review_100_D.json";
    final static String filePathWrite = "/home/bruntha/Documents/FYP/Data/yelp_dataset_challenge_academic_dataset/" +
            "review_100_D_Review_test_nonewline.txt";

    public static void main(String[] args) {

        SeparateReviews separateReviews = new SeparateReviews();
        separateReviews.separateReviews();

    }

    public void separateReviews() {
        try {
            readLinesUsingFileReader();
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
        }
        br.close();
        fr.close();
        System.out.println("Done...");
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
            String reviewWONewLine=review.replace("\n", "").replace("\r", "");
            System.out.println("R   "+reviewWONewLine);
            writePrintStream(reviewWONewLine); //write review as json if it is english

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}

