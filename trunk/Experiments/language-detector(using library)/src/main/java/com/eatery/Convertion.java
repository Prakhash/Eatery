package com.eatery;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bruntha on 7/10/15.
 */
public class Convertion {

    final static String filePathRead = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "review.txt";  //the file path that need to be annotated
    final static String filePathAnnotation = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "review.ann";  //annotation of the file that need to be annotated

    //    final static String filePathRead = "/home/bruntha/Documents/FYP/Data/convertion/" +
//            "test.txt";
//    final static String filePathAnnotation = "/home/bruntha/Documents/FYP/Data/convertion/" +
//            "test.ann";
    final static String newFilePathAnnotation = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "openNBN.txt";

    final static String testSTRING = "\"I loved  <START:Restaurant> this   place   <END>  and I can't believe it was just by accident that I discovered it. I was walking by on St Catherine's last night and saw a guy making crepes in a window. I thought to myself, this is a  <START:Restaurant> place <END>  to go for  <START:  food  > breakfast <END>  tomorrow and that's what I did today. For less than $11, I got a giant  <START:F_Drinks> coffee <END>  and a  <START:P_F_FI_Size> huge <END>   <START:F_FoodItem> spinach <END>  and  <START:F_FoodItem> egg   crepe   <END> . I arrived at 8am and  <START:Restaurant> there <END>  were hardly any customers. I was surprised and then concerned but not for long.  <START:Service> service <END>  was  <START:P_Service> fast <END>  and  <START:P_Service> polite <END> , and the  <START:F_FoodItem> crepe <END>  was  <START:P_F_FI_Taste> perfect <END> ! I plan to come back tomorrow and try a  <START:F_FoodItem> crepe <END>  from their sweet  <START:S_Menu> menu <END> - <START:F_FoodItem> strawberries <END>  and  <START:F_FoodItem> dark chocolate <END> . Mmm!\"\n";

    static int test = 0;
    static int tagCount = 0;
    static ArrayList<String> listOfItems = new ArrayList<>();
    static ArrayList<String> listOfItemsNumbers = new ArrayList<>();
    static ArrayList<Tag> tags = new ArrayList<>();
    static ArrayList<Tag> tagsNumbers = new ArrayList<>();
    static int loopTimes = 0;


    public static void main(String[] args) {

//        try {
//            loadAllTagsByNumber();
//            convertBratToOpennlpByNumber();
////            System.out.println("Loop Time: " + loopTimes);
////            convertBratToOpennlp();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            loadAllTags();
            System.out.println("Loop Time: " + loopTimes);
            convertBratToOpennlp();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(removeInnerTags(testSTRING));
//        System.out.println(removeSpaceWITags(testSTRING));

    }

    private static synchronized void loadAllTags() throws IOException {
        File fileAnnotation = new File(filePathAnnotation);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] annotations = line.split("[ \t]");
            String word = line.substring(line.lastIndexOf("\t") + 1);

            String[] words = word.split(" ");
            if (words.length > loopTimes + 1)
                loopTimes = words.length - 1;

            if (!listOfItems.contains(word)) {
                Tag tag = new Tag(annotations[1], word);
                tags.add(tag);
                System.out.println(tag);
                listOfItems.add(word);
            }
        }

//        ArrayList<Tag> item = new ArrayList<>();
//        for (int i = 0; i < tags.size(); i++) {
//            if (!tags.get(i).getKey().equals("Food")) {
//                item.add(tags.get(i));
//            }
//        }
//
//        for (int i = 0; i < item.size(); i++) {
//            tags.add(item.get(i));
//        }
        br.close();
        fr.close();
    }

    private static synchronized void loadAllTagsByNumber() throws IOException {
        File fileAnnotation = new File(filePathAnnotation);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] annotations = line.split("[ \t]");
            String index = annotations[2]+"-"+annotations[3];

            if (!listOfItemsNumbers.contains(index)) {
                Tag tag = new Tag(Integer.parseInt(annotations[2]),Integer.parseInt(annotations[3]),annotations[1]);
                tagsNumbers.add(tag);
                System.out.println(tag);
                listOfItemsNumbers.add(index);
            }
        }

        br.close();
        fr.close();
    }

    private static synchronized void convertBratToOpennlp() throws IOException {
        File fileAnnotation = new File(filePathRead);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String lineRead;

        while ((lineRead = br.readLine()) != null) {
            String line = lineRead;
            for (int i = 0; i < tags.size(); i++) {
                int oldIndex = 0;
//                int currentIndex = line.indexOf(tags.get(i).getWord());
                Pattern pattern = Pattern.compile("\\b(" + tags.get(i).getWord() + ")\\b");
                Matcher matcher = pattern.matcher(line.toLowerCase());

                String open = "";
                while (matcher.find()) {

                    int currentIndex = matcher.start();

                    open = open + line.substring(oldIndex, currentIndex) + " <START:" + tags.get(i).getKey() + "> " + tags.get(i).getWord() + " <END> ";
//                    System.out.println(open);
                    oldIndex = currentIndex + tags.get(i).getWord().length();
//                    currentIndex = line.indexOf(tags.get(i).getWord(), currentIndex + 1);
                }

//                while (currentIndex != -1) {
//                    open = open + line.substring(oldIndex, currentIndex) + "<START:" + tags.get(i).getKey() + "> " + tags.get(i).getWord() + " <END>";
////                    System.out.println(open);
//                    oldIndex = currentIndex + tags.get(i).getWord().length();
//                    currentIndex = line.indexOf(tags.get(i).getWord(), currentIndex + 1);
//                }
                if (!open.equals(""))
                    line = open + line.substring(oldIndex, line.length());
            }
            for (int i = 0; i < loopTimes; i++) {
                line = removeInnerTags(line);
            }
            writePrintStream(removeSpaceWITags(line), true);
//            System.out.println(line);
        }
        br.close();
        fr.close();
        System.out.println("Done");

    }

    private static synchronized void convertBratToOpennlpByNumber() throws IOException {
        File fileAnnotation = new File(filePathRead);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String lineRead;
        int totalIndex=0;

        while ((lineRead = br.readLine()) != null) {
            int lineLenght=lineRead.length();
            for (int i = 0; i < tagsNumbers.size(); i++) {
                if (tagsNumbers.get(i).getStartIndex()>= totalIndex && tagsNumbers.get(i).getEndIndex()<=totalIndex+lineLenght) {
                    String fistPart=lineRead.substring(0,tagsNumbers.get(i).getStartIndex()-totalIndex);
                    String middlePart=lineRead.substring(tagsNumbers.get(i).getStartIndex()-totalIndex,tagsNumbers.get(i).getEndIndex()-totalIndex);
                    String lastPart=lineRead.substring(tagsNumbers.get(i).getEndIndex()-totalIndex);

                    String newLine=fistPart+" <START:"+tagsNumbers.get(i).getKey()+"> "+middlePart+" <END> "+lastPart;
                    System.out.println(newLine);
                    writePrintStream(newLine,true);
                }
            }
            totalIndex+=lineLenght;
        }
        br.close();
        fr.close();
        System.out.println("Done");

    }

    public static String removeInnerTags(String line) {
        int currentIndexStart = line.indexOf("<START:");
        int currentIndexEnd = line.indexOf("<END>");
//        boolean taggedCorrectly;

        while (currentIndexStart != -1) {
            int nextIndexStart = line.indexOf("<START:", currentIndexStart + 1);

            if (nextIndexStart < currentIndexEnd && nextIndexStart >= 0) {
                int startSymbol = line.indexOf("<", nextIndexStart);
                int endSymbol = line.indexOf(">", startSymbol + 1);

                String firstPart = line.substring(0, startSymbol);
                String endPart = line.substring(endSymbol + 1, line.length());

                line = firstPart + endPart;

                startSymbol = line.indexOf("<", nextIndexStart);
                endSymbol = line.indexOf(">", startSymbol + 1);

                firstPart = line.substring(0, startSymbol);
                endPart = line.substring(endSymbol + 1, line.length());

                line = firstPart + endPart;

                nextIndexStart = currentIndexEnd + 1;
            }
            currentIndexStart = line.indexOf("<START:", currentIndexStart + 1);
            currentIndexEnd = line.indexOf("<END>", currentIndexEnd + 1);
        }
        return line;
    }

    public static String removeSpaceWITags(String line) {
        int startTagStart = line.indexOf("<START:");
        int startTagEnd = line.indexOf(">",startTagStart);

        String firstPart;
        String endPart ;
        String startTagPart;

        while (startTagStart != -1) {
             firstPart = line.substring(0, startTagStart);
             endPart = line.substring(startTagEnd + 1, line.length());
             startTagPart=line.substring(startTagStart,startTagEnd+1);
            if (startTagPart.contains(" ")) {
                startTagPart= startTagPart.replace(" ","");
                int index=startTagPart.indexOf(":");
                char changeChar=startTagPart.charAt(index+1);
                StringBuilder tag = new StringBuilder(startTagPart);
                tag.replace(startTagPart.indexOf(changeChar),startTagPart.indexOf(changeChar)+1,String.valueOf(Character.toUpperCase(changeChar)));
                startTagPart=tag.toString();
            }




            line=firstPart+startTagPart+endPart;

            startTagStart=line.indexOf("<START:",startTagEnd);
            startTagEnd=line.indexOf(">",startTagStart);
        }

        System.out.println(line);

        return line;
    }



    /*int currentIndex = line.indexOf(item);
    String newAnnotation = "";
    while (currentIndex != -1) {
        String open=line.substring(oldIndex, currentIndex) + "<START:" + tag + "> " + item + " <END>";
        System.out.print(open);
        writePrintStream(open,false);
        oldIndex = currentIndex+item.length();
        currentIndex = line.indexOf(item, currentIndex + 1);
    }
    indexTotal += line.length() + 1;
    test++;
    System.out.println();
    writePrintStream("",true);*/


    public static synchronized void writePrintStream(String line, boolean isNextReview) {
        PrintStream fileStream = null;
        File file = new File(newFilePathAnnotation);

        try {
            fileStream = new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (isNextReview)
            fileStream.println(line);
        else
            fileStream.print(line);

        fileStream.close();


    }


}
