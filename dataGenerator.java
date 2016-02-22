import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/* Java code to generate a random list of integers valued from 0-1000 */
/* and then print those values to a filename.txt file                 */
/* Jacob Charlebois, February 2016 */
public class dataGenerator {

    public static void main(String[] args) throws IOException{
      String filename = args[0];
   		ArrayList<Integer> dataList = new ArrayList<Integer>();
      generateList(dataList, Integer.parseInt(args[1]));
      writeFile(dataList, filename);
    }

    private static void generateList(ArrayList<Integer> data, int numValues){
      
      Random random = new Random();
      // Generates a list of "numValues" random integers from 1-1000
      for(int i = 0; i < numValues; i++){
        data.add(i, random.nextInt(1000));
      }

    }

    /* Writes the list of integers to a file with the name given as an argument */
    private static void writeFile(ArrayList<Integer> data, String filename) throws IOException {

      File file = new File(filename);

      if(!file.exists()){
        file.createNewFile();
      }

      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      for(int i = 0; i < data.size(); i++){
        bw.write(data.get(i).toString());
        bw.write(" ");
      }

      bw.flush();
      bw.close();

    }

}