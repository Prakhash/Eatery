/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjaws;
import edu.smu.tspell.wordnet.*;

/**
 *
 * @author user
 */
public class TestHyponyms {

public static void main(String args[]){
NounSynset nounSynset; 
NounSynset[] hyponyms;

System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict");
WordNetDatabase database = WordNetDatabase.getFileInstance(); 
Synset[] synsets = database.getSynsets("ambience", SynsetType.NOUN); 
    for(int i = 0; i < synsets.length; i++) {
     System.out.println(synsets[i].getWordForms()[0]+"-------------");
     /*Synset[] synsets1 = database.getSynsets(synsets[i].getWordForms()[0], SynsetType.NOUN); 
        for(int j =0; j< synsets1.length; j++){
            System.out.print(synsets1[j].getWordForms()[0]+" ** ");
            }
        System.out.println("");*/
        
    nounSynset = (NounSynset)(synsets[i]); 
    hyponyms = nounSynset.getHyponyms(); 
    
        for (NounSynset hyp : hyponyms) {
            System.out.println(hyp.getWordForms()[0] +" : "+hyp.getDefinition());
        }
        System.out.println("");
}
}
}
