/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjaws;
import edu.smu.tspell.wordnet.*;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author user
 */
public class TestHypernym_vs_hyponym {

  public static void main(String args[]){
  WordNet wordnet = new WordNet();
  
  List<NounSynset[]> list = wordnet.getNounHyponyms("drink");
  
  System.out.println("------------Hyponyms of drink: ");
  for(int i=0; i<list.size(); i++){
      NounSynset[] synset = list.get(i);
      for(int j=0; j<synset.length; j++){
          System.out.println(synset[j].getWordForms()[0]); 
        }
    }
  
  list = wordnet.getNounHypernym("drink");
  System.out.println("--------------Hypernyms of drink: ");
  for(int i=0; i<list.size(); i++){
      NounSynset[] synset = list.get(i);
      for(int j=0; j<synset.length; j++){
          System.out.println(synset[j].getWordForms()[0]); 
        }
    }
  }
}

