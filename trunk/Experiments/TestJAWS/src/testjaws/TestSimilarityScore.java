/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjaws;

import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Resnik;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author user
 */
public class TestSimilarityScore {

    
    /**
     * @param args
     */
     public static void main(String args[]) {
         String dir = "C:/Program Files (x86)/WordNet";
        JWS ws = new JWS(dir,"2.1");
    
        Resnik jcn = ws.getResnik();
         System.out.println("Resnik");
         // all senses
         TreeMap scores1 = jcn.res("wifi", "service", "n"); // all senses
         //TreeMap scores1 = jcn.jcn("apple", 1, "banana", "n");
         // fixed;all
         //TreeMap scores1 = jcn.jcn("apple", "banana", 2, "n");
         // all;fixed
         for(Object s : scores1.keySet())
         System.out.println(s + "\t" + scores1.get(s));
         // specific senses
         System.out.println("\nspecific pair\t=\t" + jcn.res("wifi", 1, "service",
         1, "n") + "\n");
         // max.
         System.out.println("\nhighest score\t=\t" + jcn.max("wifi", "service",
         "n") + "\n\n\n");
         }
}