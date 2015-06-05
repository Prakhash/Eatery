/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjaws;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 *
 * @author user
 */
public class TestHypernym {
    public static void main(String args[]){
NounSynset nounSynset; 
NounSynset[] hyponyms;

System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict");
WordNetDatabase database = WordNetDatabase.getFileInstance(); 
Synset[] synsets = database.getSynsets("drink", SynsetType.NOUN); 
    for(int i = 0; i < synsets.length; i++) {
     System.out.println(synsets[i].getWordForms()[0]+"-------------");
 
    nounSynset = (NounSynset)(synsets[i]); 
    hyponyms = nounSynset.getHypernyms(); 
    System.err.println(nounSynset.getWordForms()[0] + 
            ": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hypernyms"); 
    
        for (NounSynset hyp : hyponyms) {
            System.out.println(hyp.getWordForms()[0] +" : "+hyp.getDefinition());
        }
        System.out.println("");
}
}
}
