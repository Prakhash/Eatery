package com.eatery;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by bruntha on 7/10/15.
 */
public class Convertion {
    final static String filePathRead = "/home/bruntha/Documents/FYP/Data/convertion/" +
            "test.txt";
    final static String filePathAnnotation = "/home/bruntha/Documents/FYP/Data/convertion/" +
            "test.ann";
    final static String newFilePathAnnotation = "/home/bruntha/Documents/FYP/Data/convertion/" +
            "open.txt";

    static int test = 0;
    static int tagCount = 0;
    static ArrayList<String> listOfItems = new ArrayList<>();
    static ArrayList<Tag> tags = new ArrayList<>();


    public static void main(String[] args) {

        try {
            loadAllTags();
            convertBratToOpennlp();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static synchronized void loadAllTags() throws IOException {
        File fileAnnotation = new File(filePathAnnotation);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] annotations = line.split("[ \t]");
            if (!listOfItems.contains(annotations[4])) {
                Tag tag = new Tag(annotations[1], annotations[4]);
                tags.add(tag);
                listOfItems.add(annotations[4]);
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
            String line=lineRead;
            for (int i = 0; i < tags.size(); i++) {
                int oldIndex = 0;
                int currentIndex = line.indexOf(tags.get(i).getWord());
                String open = "";
                while (currentIndex != -1) {
                    open = open + line.substring(oldIndex, currentIndex) + "<START:" + tags.get(i).getKey() + "> " + tags.get(i).getWord() + " <END>";
//                    System.out.println(open);
                    oldIndex = currentIndex + tags.get(i).getWord().length();
                    currentIndex = line.indexOf(tags.get(i).getWord(), currentIndex + 1);
                }
                if (!open.equals(""))
                    line = open+line.substring(oldIndex,line.length());
            }
            writePrintStream(line, true);
            System.out.println(line);
        }
        br.close();
        fr.close();
        System.out.println("Done");

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
