package com.eatery;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by bruntha on 7/9/15.
 */
public class Annotation {
    final static String filePathToAnnotate = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "review_100_D_Review.txt";
    final static String filePathAnnotation = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "review.ann";
    final static String newFilePathAnnotation = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "NewAnnotation.ann";

    static int test=0;
    static int tagCount=0;
    static ArrayList<String> listOfItems=new ArrayList<>();

    public static void main(String[] args) {

        try {
            readFileAnnotation();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static synchronized void readFileAnnotation() throws IOException {
        File fileAnnotation = new File(filePathAnnotation);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] annotations=line.split("[ \t]");
            if(!listOfItems.contains(annotations[4])){
                annotate(annotations[1],annotations[4]);
                listOfItems.add(annotations[4]);
            }
        }
        br.close();
        fr.close();
        System.out.println("Done");
        print();
    }

    public static void print()
    {
        for (int i = 0; i < listOfItems.size(); i++) {
            System.out.println(listOfItems.get(i));
        }
    }
    private static synchronized void annotate(String tag, String item) throws IOException {
        File fileAnnotation = new File(filePathToAnnotate);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int indexTotal=0;

        while ((line = br.readLine()) != null) {
            int currentIndex=line.indexOf(item);
            while (currentIndex!=-1){
                String newAnnotation="T"+ ++tagCount +"\t"+tag+" "+(currentIndex+indexTotal)+" "+(currentIndex+indexTotal+item.length())+"\t"+item;
                System.out.println(newAnnotation);
                writePrintStream(newAnnotation);
                currentIndex=line.indexOf(item,currentIndex+1);
            }
            indexTotal+=line.length()+1;
            test++;
        }
        br.close();
        fr.close();
    }

    public static synchronized void writePrintStream(String line) {
        PrintStream fileStream = null;
        File file = new File(newFilePathAnnotation);

        try {
            fileStream = new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileStream.println(line);
        fileStream.close();


    }

}
