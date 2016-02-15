import java.util.ArrayList;
import java.util.Random;

public class dataGenerator {

    public static void main(String[] args) {
        String filename = args[0];
   		int numValues = Integer.parseInt(args[1]);
   		Random random = new Random();
   		ArrayList<Integer> data = new ArraList<Integer>();

   		// Generates a list of "numValues" random integers from 1-10000
   		for(int i = 0; i < numValues; i++){
   			data.add(i, random.nextInt(10000));
   		}

   		
    }
}