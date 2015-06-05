/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjaws;

import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Resnik;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class TestCategorizationResnik {

    public static void main(String args[]){
    String dir = "C:/Program Files (x86)/WordNet";
    JWS ws = new JWS(dir,"2.1");
    
    Resnik jcn = ws.getResnik();
    String[] aspects = {"food", "ambinece", "service", "discount", "worthiness"};
    double[] similarity = new double[5];
    Scanner in = new Scanner(System.in);
    System.out.print("Enter the word: ");
    String word = in.next();
    
    while(!"end".equals(word)){
        similarity[0] = jcn.max(word, "food","n");
        similarity[1] = jcn.max(word, "ambience","n");
        similarity[2] = jcn.max(word, "service","n");
        similarity[3] = jcn.max(word, "discount","n");
        similarity[4] = jcn.max(word, "worthiness","n");
        
        double max = similarity[0];
        int maxIndex = 0;
        System.out.println(similarity[0]+" ** "+similarity[1]+" ** "+similarity[2]);
        for(int i=1; i<5; i++){
            if(max<similarity[i]){
                max = similarity[i];
                maxIndex = i;
                }
            }
        
        System.out.println(word +" belongs to "+aspects[maxIndex]);
        System.out.print("Enter the word: ");
        word = in.next();
        }
    in.close();
    }
}
