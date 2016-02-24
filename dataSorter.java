import java.util.*;
import java.io.*;

/* Program that sorts the given inputFileName.txt integer list using a heapsort */
/* algorithm and stores the values in a given outputFileName.txt                */
/**/
/**/
/**/
/* Jacob Charlebois, February 2016 */
public class DataSorter {

	public static void main(String[] args) throws IOException {
			
		String inputFileName = args[0];
		String outputFileName = args[1];
		Double probPrimFail = Double.parseDouble(args[2]);
		Double probSecFail = Double.parseDouble(args[3]);
		Integer timeLimit = Integer.parseInt(args[4]);

		ArrayList<Integer> dataList = readFile(inputFileName);

		Boolean primaryPass = null;
		Boolean secondaryPass = null;

		HeapSort primary = new HeapSort(dataList, probPrimFail);
		Timer t = new Timer();
		Watchdog fido = new Watchdog(primary);
		t.schedule(fido, timeLimit.longValue());
		try {
			primary.start();
		} catch (ThreadDeath td) {
			System.out.println("Primary variant timed out.\n");
			primaryPass = false;
		}

		ArrayList<Integer> sortedData = primary.getData();

		if(!acceptanceTest(sortedData)) {
			System.out.println("Primary variant failed, attempting secondary . . .");
			// Run secondary variant
			int[] list = makeIntArray(dataList);
			InsertionSort secondary = new InsertionSort (list, probSecFail);
			t = new Timer();
			fido = new Watchdog(secondary);
			t.schedule(fido, timeLimit.longValue());
			try {
				secondary.start();
			} catch (ThreadDeath td) {
				System.out.println("Secondary variant timed out.\n");
				secondaryPass = false;					
			}
			sortedData = makeArrayList(secondary.getData());
		}

		if(acceptanceTest(sortedData)) {
			writeFile(sortedData, outputFileName);
		} else {
			System.out.println("Both variants have failed \nTerminating program.");
			File file = new File(outputFileName);
			if(file.exists()) {
				file.delete();
			}
		}
		//Multi threaded elements
		//Primary variant: Java Heapsort
		//Backup variant: Insertion sort algorithm in C
		//Executive variant:
		//WatchdogTimer
		//Adjudicator (Executive thread)
	}

	/* Method to pass an int array to our JNI called method */
	private static int[] makeIntArray(ArrayList<Integer> data) {
		int[] array = new int[data.size()];
		for(int i = 0; i < data.size(); i++){
			array[i] = data.get(i);
		}
		return array;
	}

	/* Method to pass an int array to our JNI called method */
	private static ArrayList<Integer> makeArrayList(int[] data) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		for(int i = 0; i < data.length; i++) {
			array.set(i, data[i]);
		}
		return array;
	}


	/* This method ensures that the data we have sorted is in ascending order */
	private static boolean acceptanceTest(ArrayList<Integer> data) {
		for(int i = 0; i < data.size() - 1; i++) {
			if (data.get(i) > data.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	/* This method calculates whether or not our program while run the primary or */
	/* secondary variant given the input of the probability of failure */
	private static boolean calculateFailure(Double hazard) {
		// My program only accesses memory once (when reading the file) and so,
		// according assignment sheet, failure is calculated as follows
		Double probFailure = Math.random();//Math.random();

		if(probFailure > 0.5 && probFailure < (0.5 + hazard)) {
			return false; // Variant has failed
		} else {
			return true; //variant has succeeded
		}
	}

	/* Reads the list of integers from the given filename and stores them in an ArrayList */
	private static ArrayList<Integer> readFile(String filename) throws FileNotFoundException {
		ArrayList<Integer> dataList = new ArrayList<Integer>();
		Scanner s = new Scanner(new File(filename));
		while (s.hasNext()) {
			dataList.add(Integer.parseInt(s.next()));
		}

		return dataList;
	}

 	/* Writes the list of integers to a specified file */
    private static void writeFile(ArrayList<Integer> data, String filename) throws IOException {
      File file = new File(filename);

      if(!file.exists()) {
        file.createNewFile();
      }

      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      for(int i = 0; i < data.size(); i++) {
        bw.write(data.get(i).toString());
        bw.write(" ");
      }
      bw.flush();
      bw.close();
    }
}