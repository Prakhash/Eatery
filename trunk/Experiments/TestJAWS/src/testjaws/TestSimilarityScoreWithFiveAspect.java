/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjaws;

import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Resnik;

/**
 *
 * @author user
 */
public class TestSimilarityScoreWithFiveAspect {
    public static void main(String args[]) {
         String dir = "C:/Program Files (x86)/WordNet";
         JWS ws = new JWS(dir,"2.1");
    
         Resnik jcn = ws.getResnik();
         System.out.println("wifi vs service");
         System.out.println("\nhighest score\t=\t" + jcn.max("wifi", "automobile",
         "n"));
        
         System.out.println("wifi vs Food");
         System.out.println("\nhighest score\t=\t" + jcn.max("wifi", "food",
         "n"));
         
         System.out.println("wifi vs Ambience");
         System.out.println("\nhighest score\t=\t" + jcn.max("wifi", "ambience",
         "n"));
         
         System.out.println("wifi vs Discount");
         System.out.println("\nhighest score\t=\t" + jcn.max("wifi", "discount",
         "n"));
         
         System.out.println("wifi vs Worthiness");
         System.out.println("\nhighest score\t=\t" + jcn.max("wifi", "worthiness",
         "n"));
    }
}
