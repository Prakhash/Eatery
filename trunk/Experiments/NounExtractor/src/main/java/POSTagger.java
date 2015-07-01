import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nazick on 7/1/15.
 */
public class POSTagger {

    static String sentence = "The restaurant has a nice setting and pleasant decor, but the food is seriously lacking.  I always know it's going to be trouble when the menu is excessively long... that says to me that the restaurant does many things, but really nothing well.  And that's exactly what I got at Cafe Sam.  I had a pulled pork sandwhich that was bland, dry, and not particularly well put together.  My girlfriend had a similarly bland meal.  It was fine for lunch while we killed time waiting on the dealership to finish working on her car, but I won't be back by choice.  Toast!  down the street is a better option - wish they were open for lunch.";
    static List allNouns = new ArrayList<String>();
    static List allAdjectives = new ArrayList<String>();
    public static void main(String arg[]){

        try{
            POSTag();
            System.out.println("NOUNS #################");
            for(Object noun: allNouns){
                System.out.println(noun.toString());

            }

            System.out.println("Adjectives #################");
            for(Object noun: allAdjectives){
                System.out.println(noun.toString());
            }
        }catch(IOException e){

        }
    }

    public static void POSTag() throws IOException {
        POSModel model = new POSModelLoader()
                .load(new File("src/main/resources/en-pos-maxent.bin"));
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(model);

        String input = "Hi. How are you? This is Mike.";
        ObjectStream<String> lineStream = new PlainTextByLineStream(
                new StringReader(sentence));

        perfMon.start();
        String line;
        while ((line = lineStream.read()) != null) {

            String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            String[] tags = tagger.tag(whitespaceTokenizerLine);

            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            System.out.println(sample.toString());
            extractNouns(sample.toString());
            extractAdjectives(sample.toString());
            perfMon.incrementCounter();
        }
        perfMon.stopAndPrintFinalResult();
    }

    public static void extractNouns(String sentenceWithTags) {
        // Split String into array of Strings whenever there is a tag that starts with "._NN"
        // followed by zero, one or two more letters (like "_NNP", "_NNPS", or "_NNS")
        String[] nouns = sentenceWithTags.split("_NN\\w?\\w?\\b");
        // remove all but last word (which is the noun) in every String in the array
        for(int index = 0; index < nouns.length; index++) {
            nouns[index] = nouns[index].substring(nouns[index].lastIndexOf(" ") + 1)
                    // Remove all non-word characters from extracted Nouns
                    .replaceAll("[^\\p{L}\\p{Nd}]", "");
        }
        Collections.addAll(allNouns, nouns);
    }

    public static void extractAdjectives(String sentenceWithTags) {
        // Split String into array of Strings whenever there is a tag that starts with "._NN"
        // followed by zero, one or two more letters (like "_NNP", "_NNPS", or "_NNS")
        String[] nouns = sentenceWithTags.split("_JJ\\w?\\w?\\b");
        // remove all but last word (which is the noun) in every String in the array
        for(int index = 0; index < nouns.length; index++) {
            nouns[index] = nouns[index].substring(nouns[index].lastIndexOf(" ") + 1)
                    // Remove all non-word characters from extracted Nouns
                    .replaceAll("[^\\p{L}\\p{Nd}]", "");
        }
        Collections.addAll(allAdjectives, nouns);
    }
}
