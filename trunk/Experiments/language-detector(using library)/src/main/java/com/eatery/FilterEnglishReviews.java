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
        //            filterEnglishReviews.readLinesUsingFileReader(1,3); //reading json line one by one
        getKeyJson("{\"batchcomplete\":\"\",\"query\":{\"normalized\":[{\"from\":\"bread\",\"to\":\"Bread\"}],\"pages\":{\"36969\":{\"pageid\":36969,\"ns\":0,\"title\":\"Bread\",\"revisions\":[{\"contentformat\":\"text/x-wiki\",\"contentmodel\":\"wikitext\",\"*\":\"{{About||the American rock band|Bread (band)|other uses}}\\n{{pp-move-indef}}\\n{{Multiple issues|\\n{{refimprove|date=June 2015}}\\n{{original research|date=February 2015}}\\n}}\\n\\n{{Use dmy dates|date=March 2015}}\\n{{Infobox prepared food\\n| name             = Bread\\n| image            = [[File:Korb mit Br\\u00f6tchen.JPG|250px]]\\n| caption          = Various leavened breads\\n| alternate_name   = \\n| country          = \\n| region           = \\n| creator          = \\n| course           = \\n| served           = \\n| main_ingredient  = [[Flour]], [[water]]\\n| variations       = \\n| calories         = \\n| other            = \\n}}\\n\\n'''Bread''' is a [[staple food]] prepared from a [[dough]] of [[flour]] and [[water]], usually by [[baking]]. Throughout recorded history it has been popular around the world and is one of the oldest artificial foods, having been of importance since the dawn of [[Agriculture#History|agriculture]].\\n\\nThere are many combinations and proportions of types of flour and other ingredients, and also of different traditional recipes and modes of preparation of bread. As a result, there are wide varieties of types, shapes, sizes, and textures of breads in various regions.  Bread may be [[leaven]]ed by many different processes ranging from the use of naturally occurring microbes (for example in [[sourdough]] recipes) to high-pressure artificial aeration methods during preparation or baking. However, some products are left unleavened, either for preference, or for traditional or religious reasons. Many non-cereal ingredients may be included, ranging from fruits and nuts to various fats. Commercial bread in particular, commonly contains additives, some of them non-nutritional, to improve flavor, texture, color, shelf life, or ease of manufacturing.\\n\\nDepending on local custom and convenience, bread may be served in various forms at any meal of the day. It also is eaten as a snack, or used as an ingredient in other culinary preparations, such as fried items coated in crumbs to prevent sticking, or the bland main component of a [[bread pudding]], or [[stuffing]]s designed to fill cavities or retain juices that otherwise might drip away.\\n\\nPartly because of its importance as a basic foodstuff, bread has a social and emotional significance beyond its importance in nutrition; it plays essential roles in religious rituals and secular culture. Its prominence in daily life is reflected in language, where it appears in proverbs, colloquial expressions (\\\"He stole the bread from my mouth\\\"), in prayer (\\\"Give us this day our daily bread\\\") and even in the etymology of words, such as \\\"[[Wiktionary:companion|companion]]\\\" and \\\"[[Wiktionary:company|company]]\\\" (literally those who eat/share bread with you).\"}]}}}}");

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
        boolean isEnglish=false;
        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            String review = (String) jsonObject.get("text");    // get review text from json
            Pattern pattern=Pattern.compile("[a-zA-Z]");
            Matcher matcher = pattern.matcher(review);
//            if(review.equals("..") ||review.equals("...") ||review.equals(".") || review.length()==1 ||( review.length()==2 && review.indexOf(':')==0)){
            if(!matcher.find()) {
                isEnglish=false;
            }else {
                isEnglish = languageDetect.isEnglish(review); //checking whether review is english or not
            }
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

            System.out.println(((JSONObject) obj).keySet());

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

    public static void getKeyJson(String json) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();

//        System.out.println(json);
        try {

            Object obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject query= (JSONObject) jsonObject.get("query");
            Object pages=query.get("pages");

            System.out.println(((JSONObject) pages).keySet());

//            String review = (String) jsonObject.get("text");    // get review text from json
//            String reviewID= (String) jsonObject.get("review_id");
//
//            if (reviewIDS.contains(reviewID)) {
//                System.out.println("COME OVER   "+reviewID);
//            }else {
//                noOfEngRev++;
//                reviewIDS.add(reviewID);
//            }
//
//            System.out.println(noOfEngRev);

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}
