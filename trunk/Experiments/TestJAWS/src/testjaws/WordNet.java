package testjaws;

import java.util.ArrayList;
import java.util.List;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class WordNet {
        
        private WordNetDatabase database;
        
        public WordNet() {
                System.setProperty("wordnet.database.dir","C:\\Program Files (x86)\\WordNet\\2.1\\dict");
                database = WordNetDatabase.getFileInstance();
        }

        public List<NounSynset[]> getNounHyponyms(String noun) {
                List<NounSynset[]> result = new ArrayList<NounSynset[]>();
                Synset[] synsets = database.getSynsets(noun, SynsetType.NOUN);
                for(Synset synset : synsets) {
                        result.add(((NounSynset)synset).getHyponyms());
                }
                return result;
        }
        
        
        public List<NounSynset[]> getNounHypernym(String noun) {
                List<NounSynset[]> result = new ArrayList<NounSynset[]>();
                Synset[] synsets = database.getSynsets(noun, SynsetType.NOUN);
                for(Synset synset : synsets) {
                        result.add(((NounSynset)synset).getHypernyms());
                }
                return result;
        }
        public List<NounSynset[]> getNounHyponyms(Synset[] synset) {
                List<NounSynset[]> result = new ArrayList<NounSynset[]>();
                for(Synset s : synset) {
                        result.add(((NounSynset)s).getHyponyms());
                }
                return result;
        }
        
        public List<String> getNounSynonyms(String noun) {
                List<String> result = new ArrayList<String>();
                Synset[] synsets = database.getSynsets(noun, SynsetType.NOUN);
                for(Synset synset : synsets) {
                        String[] synonyms =((NounSynset)synset).getWordForms();
                        for(String synonym : synonyms) {
                                if(!result.contains(synonym)) {
                                        result.add(synonym);
                                }
                        }
                }
                return result;
        }
}