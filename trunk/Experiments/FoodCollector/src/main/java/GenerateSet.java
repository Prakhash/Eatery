import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by prakhash on 6/4/15.
 */

public class GenerateSet {

    public static void main(String[] args) {

        BufferedReader br = null;

        try {

            String sCurrentLine;

            String[] filepaths = new String[]{"src/main/Files/Foodnames1.txt", "src/main/Files/Foodnames2.txt"};

            for (int i = 0; i < filepaths.length; i++) {

                br = new BufferedReader(new FileReader(filepaths[i]));

                while ((sCurrentLine = br.readLine()) != null) {
                    System.out.println(sCurrentLine);
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
}
