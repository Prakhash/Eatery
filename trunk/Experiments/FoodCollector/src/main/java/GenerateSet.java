import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by prakhash on 6/4/15.
 */
public class GenerateSet {

    BufferedReader br = null;
    BufferedWriter bw=null;
    SortedSet<String> foodnames;

    public void ReadFiles() {
        try {
            String sCurrentLine;
            String[] filepaths = new String[]{"src/main/Files/Foodnames1.txt", "src/main/Files/Foodnames2.txt","src/main/Files/foodnamelarge.txt"};

            foodnames = new TreeSet<String>();
            for (int i = 0; i < filepaths.length; i++) {
                br = new BufferedReader(new FileReader(filepaths[i]));
                while ((sCurrentLine = br.readLine()) != null) {
                    foodnames.add(sCurrentLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void WriteToFIle()
    {
        try{

         bw =new BufferedWriter(new FileWriter("src/main/Files/FinalFoodnames.txt"));

            Iterator<String> itr = foodnames.iterator();
            while(itr.hasNext()){;
                bw.write(itr.next()+"\n");
                //System.out.println(itr.next());
            }

        }catch (Exception e){

        }finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        GenerateSet gs=new GenerateSet();
        gs.ReadFiles();
        gs.WriteToFIle();
    }
}
