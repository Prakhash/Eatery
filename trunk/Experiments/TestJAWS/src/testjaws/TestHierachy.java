/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjaws;
import edu.smu.tspell.wordnet.*;
import java.util.List;

/**
 *
 * @author user
 */
public class TestHierachy {
  
public static void main(String args[]){
WordNet wordnet = new WordNet();
List<NounSynset[]> list = wordnet.getNounHypernym("pizza");

while(list.size()>0){
    NounSynset[] synset = list.get(0);
    list.remove(0);
      for(int j=0; j<synset.length; j++){
          System.out.println(synset[j].getWordForms()[0]); 
          List<NounSynset[]> list1 = wordnet.getNounHypernym(synset[j].getWordForms()[0]);
          
          for(int k=0; k<list1.size(); k++){
          list.add(list1.get(k));
          }
        }
    }
}

}
