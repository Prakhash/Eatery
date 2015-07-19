package com.eatery;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bruntha on 7/9/15.
 */
public class Annotation {

    final String filePathToAnnotate = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "review.txt";  //the file path that need to be annotated
    final String filePathToAlreadyAnnotated = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "review.ann";  //annotation of the file that need to be annotated
    final String filePathAnnotation = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "review.ann";
    final String filePathFoodNames = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "FinalFoodnames.txt";
    final String newFilePathAnnotation = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "review.ann";
    final String filePathDictionary = "/home/bruntha/Documents/Softwares/brat-v1.3_Crunchy_Frog/data/Eatery/" +
            "dictionary.txt";


    int test = 0;
    int tagCount = 0;
    int noOfTagsAlready = 0;
    int noOfTagsAlreadyMax = 0;
    ArrayList<String> listOfItems = new ArrayList<>();
    Hashtable<String, String> taggedItems = new Hashtable<>();
    Hashtable<String, String> dictionaryHashtable = new Hashtable<>(); //<key=word,value=tag>

    public static void main(String[] args) {

        Annotation annotation = new Annotation();
        annotation.annotate();

    }


    private synchronized void readFileForAnnotation() throws IOException {
        File fileAnnotation = new File(filePathDictionary);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] annotations = line.split("[ \t]");
            String word=line.substring(line.indexOf(" ")+1);
            annotate(annotations[0], word.toLowerCase());
        }
        br.close();
        fr.close();
        System.out.println("Done");
        print();
    }

    private void addToDictionary(String tag, String word) throws IOException {
        PrintStream fileStream = null;
        File file = new File(filePathDictionary);

        try {
            fileStream = new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileStream.println(tag + " " + word);
        fileStream.close();
    }

    private void loadDictionary() throws IOException {
        File fileAnnotation = new File(filePathDictionary);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] dic = line.split("[ \t]");
            String word=line.substring(line.indexOf(" ")+1);
            dictionaryHashtable.put(word, dic[0]);
            System.out.println("Loading dictionary : Word: "+word+"\tTag: "+dic[0] );
        }
        br.close();
        fr.close();
        System.out.println("Dictionary Loaded");
    }

    private void upgradeDictionary() throws IOException {
        File fileAnnotation = new File(filePathAnnotation);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] annotations = line.split("[ \t]");
            String word = line.substring(line.lastIndexOf("\t") + 1);
            if (dictionaryHashtable.containsKey(word)) {
                if (!dictionaryHashtable.get(word).equals(annotations[1])) {
                    System.out.println("Conflict : word: " + word + " Dictionary Tag: " + dictionaryHashtable.get(word) + " New Tag: " + annotations[1]);
                }
            } else {
                dictionaryHashtable.put(word, annotations[1]);
                System.out.println("Updating Dictionary : Word: " + word + "\tTag: " + annotations[1]);
                addToDictionary(annotations[1],word);
            }

        }
        br.close();
        fr.close();
        System.out.println("Done");
        print();
    }

    public void updateDictionary() {
        try {
            loadDictionary();
            upgradeDictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void annotate() {
        try {
            loadTags();
            tagCount = noOfTagsAlreadyMax;
            readFileForAnnotation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void annotateFoodNames() {
        try {
            loadTags();
            tagCount = noOfTagsAlreadyMax;
            tagFoodNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tagFoodNames() throws IOException {
        File fileAnnotation = new File(filePathFoodNames);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            annotate("F_FoodItem", line);
        }
        br.close();
        fr.close();
        System.out.println("Done tagging food names");
    }

    public void print() {
        for (int i = 0; i < listOfItems.size(); i++) {
            System.out.println(listOfItems.get(i));
        }
    }

    private void loadTags() throws IOException {
        File fileAnnotation = new File(filePathToAlreadyAnnotated);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] annotations = line.split("[ \t]");
//            if(!listOfItems.contains(annotations[4])){
            String word=line.substring(line.lastIndexOf('\t')+1);
            String[] words=word.split(" ");

            int start=Integer.parseInt(annotations[2]);
            if (words.length == 0) {
                taggedItems.put(annotations[2] + "-" + annotations[3], annotations[1]);

            }else {
                for (int i = 0; i < words.length; i++) {
//                    words[i]=words[i].trim();
                    int end= start+words[i].length();
//                    System.out.println(start+" "+words[i].length()+" "+end+"#"+words[i]+"#");
                    taggedItems.put(start + "-" + end, annotations[1]);
                    start=end+1;
                }

            }
            noOfTagsAlready=Integer.parseInt(annotations[0].substring(1));
            if(noOfTagsAlreadyMax < noOfTagsAlready)
                noOfTagsAlreadyMax=noOfTagsAlready;
//                listOfItems.add(annotations[4]);
//            }
        }
        br.close();
        fr.close();
        System.out.println("Loaded tags");
    }

    private synchronized void annotate(String tag, String item) throws IOException {
        File fileAnnotation = new File(filePathToAnnotate);
        FileReader fr = new FileReader(fileAnnotation);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int indexTotal = 0;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
            Pattern pattern = Pattern.compile("\\b(" + item + ")\\b");
            Matcher matcher = pattern.matcher(line.toLowerCase());
            while (matcher.find()) {

                int currentIndex = matcher.start();

                if (!alreadyAnnotated((currentIndex + indexTotal) + "-" + (currentIndex + indexTotal + item.length()), tag, line.substring(currentIndex, matcher.end()))) {
                    String newAnnotation = "T" + ++tagCount + "\t" + tag + " " + (currentIndex + indexTotal) + " " + (currentIndex + indexTotal + item.length()) + "\t" + line.substring(currentIndex, matcher.end());
                    System.out.println(newAnnotation);
                    writePrintStream(newAnnotation);
                }
//                currentIndex=line.indexOf(item,currentIndex+1);
            }
            indexTotal += line.length() + 1;
            test++;

            /*int currentIndex=line.indexOf(item);
            while (currentIndex!=-1){
                String newAnnotation="T"+ ++tagCount +"\t"+tag+" "+(currentIndex+indexTotal)+" "+(currentIndex+indexTotal+item.length())+"\t"+item;
                System.out.println(newAnnotation);
                if(!alreadyAnnotated((currentIndex+indexTotal)+"-"+(currentIndex+indexTotal+item.length()),tag,item))
                {
                    writePrintStream(newAnnotation);
                }
                currentIndex=line.indexOf(item,currentIndex+1);
            }
            indexTotal+=line.length()+1;
            test++;*/
        }
        br.close();
        fr.close();
    }

    public boolean alreadyAnnotated(String indeces, String tag, String word) {
        if (taggedItems.containsKey(indeces)) {
            if (!tag.equals(taggedItems.get(indeces)))
                System.out.println("Conflict : " + word + " Old tag: " + tag + " New tag: " + taggedItems.get(indeces));
            return true;
        } else
            return false;
    }

    public synchronized void writePrintStream(String line) {
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

//    public void removeLineFromFile(String file, String lineToRemove) {
//
//        try {
//
//            File inFile = new File(file);
//
//            if (!inFile.isFile()) {
//                System.out.println("Parameter is not an existing file");
//                return;
//            }
//
//            //Construct the new file that will later be renamed to the original filename.
//            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
//
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
//
//            String line = null;
//
//            //Read from the original file and write to the new
//            //unless content matches data to be removed.
//            while ((line = br.readLine()) != null) {
//
//                if (!line.trim().substring(0,4).equals("T"+lineToRemove)) {
//                    System.out.println("found");
//                    pw.println(line);
//                    pw.flush();
//                }
//            }
//            pw.close();
//            br.close();
//
//            //Delete the original file
//            if (!inFile.delete()) {
//                System.out.println("Could not delete file");
//                return;
//            }
//
//            //Rename the new file to the filename the original file had.
//            if (!tempFile.renameTo(inFile))
//                System.out.println("Could not rename file");
//
//        }
//        catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

}
