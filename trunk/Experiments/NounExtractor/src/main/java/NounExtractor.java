import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

/**
 * Created by nazick on 6/7/15.
 */

//extract noun phrases from a single sentence using OpenNLP
public class NounExtractor {

    static String sentence = "One reason people lie is to achieve personal power. Achieving personal power is helpful for someone who pretends to be more confident than he really is. For example, one of my friends threw a party at his house last month. He asked me to come to his party and bring a date. However, I didn’t have a girlfriend. One of my other friends, who had a date to go to the party with, asked me about my date. I didn’t want to be embarrassed, so I claimed that I had a lot of work to do. I said I could easily find a date even better than his if I wanted to. I also told him that his date was ugly. I achieved power to help me feel confident; however, I embarrassed my friend and his date. Although this lie helped me at the time, since then it has made me look down on myself.The offer was great to its place. the pizza was in its best.";

    static Set<String> nounPhrases = new HashSet<String>();

    public static void main(String[] args) {

        InputStream modelInParse = null;
        try {
            //load chunking model
            modelInParse = new FileInputStream("src/main/resources/en-parser-chunking.bin");
            ParserModel model = new ParserModel(modelInParse);

            //create parse tree
            Parser parser = ParserFactory.create(model);
            Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

            //call subroutine to extract noun phrases
            for (Parse p : topParses)
                getNounPhrases(p);

            //print noun phrases
            for (String s : nounPhrases)
                System.out.println(s);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (modelInParse != null) {
                try {
                    modelInParse.close();
                }
                catch (IOException e) {
                }
            }
        }
    }

    //recursively loop through tree, extracting noun phrases
    public static void getNounPhrases(Parse p) {

        if (p.getType().equals("NP")) { //NP=noun phrase
            nounPhrases.add(p.getCoveredText());
        }
        for (Parse child : p.getChildren())
            getNounPhrases(child);
    }
}