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

    static String sentence = "Having just arrived to San Francisco we were looking for a quick meal in Chinatown. This place met our needs, as it had good food and decent prices. We got the orange chicken and beef with broccoli which were good but nothing noteworthy. The place fit the bill at the time but not a place I feel the need to revisit soon.";

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