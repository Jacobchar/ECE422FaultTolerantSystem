import java.util.*;
import java.io.*;

/* Program that sorts the given inputFileName.txt integer list using either a heapsort */
/* algorithm or an insertion sort algorithm given the probabilities of the variants */
/* on the failure or success of the variants the sorted list is then stored in */
/* outputFileName.txt This program takes advantage of threads as well as a Watchdog timer */

/* Jacob Charlebois, February 2016 */
public class DataSorter {

	public static void main(String[] args) throws IOException {
		
		String inputFileName = args[0];
		String outputFileName = args[1];
		Double probPrimFail = Double.parseDouble(args[2]);
		Double probSecFail = Double.parseDouble(args[3]);
		Integer timeLimit = Integer.parseInt(args[4]);

		ArrayList<Integer> dataList = readFile(inputFileName);

		// Set these conditions so we know whether or not our variants "fail"
		Boolean primaryPass = null;
		Boolean secondaryPass = null;
		Boolean timeout = false;

		HeapSort primary = new HeapSort(dataList, probPrimFail);
		Timer t = new Timer();
		Watchdog fido = new Watchdog(primary);
		t.schedule(fido, timeLimit.longValue());
		
		try {
			primary.start();
			System.out.println("Sorting via primary variant.");
		} catch (ThreadDeath td) {
			System.out.println("Primary variant timed out.\n");
			timeout = true;
		}

		ArrayList<Integer> sortedData = primary.getData();
		
		try {
			primary.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		primaryPass = primary.isSorted();

		if(!acceptanceTest(sortedData) || timeout || !primaryPass) {
			System.out.println("Primary variant failed, attempting secondary . . .");
			// Run secondary variant
			int[] list = makeIntArray(dataList);
			InsertionSort secondary = new InsertionSort (list, probSecFail);
			t = new Timer();
			fido = new Watchdog(secondary);
			t.schedule(fido, timeLimit.longValue());
			try {
				secondary.start();
				System.out.println("Sorting via secondary variant.");
			} catch (ThreadDeath td) {
				System.out.println("Secondary variant timed out.\n");
				timeout = false;					
			}

			sortedData = makeArrayList(secondary.getData());

			try {
				secondary.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			secondaryPass = secondary.isSorted();
		}

		if(acceptanceTest(sortedData) && !timeout && (primaryPass || secondaryPass)) {
			writeFile(sortedData, outputFileName);
			System.out.println("File has been correctly sorted.");
		} else {
			System.out.println("Both variants have failed \nTerminating program.");
			File file = new File(outputFileName);
			if(file.exists()) {
				file.delete();
			}
		}
		System.exit(0);
	}

	/* Method to pass an int array to our JNI called method */
	private static int[] makeIntArray(ArrayList<Integer> data) {
		int[] array = new int[data.size()];
		for(int i = 0; i < data.size(); i++){
			array[i] = data.get(i);
		}
		return array;
	}

	/* Method to get an arraylist from our JNI native method */
	private static ArrayList<Integer> makeArrayList(int[] data) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		for(int i = 0; i < data.length; i++) {
			array.add(data[i]);
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
