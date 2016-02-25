import java.util.*;

public class InsertionSort extends Thread {
	
	private int[] data;
	private boolean isSorted;
	private double probability;

	public InsertionSort(int[] buf, double probSecFail) {
		data = buf;
		isSorted = false;
		probSecFail = probability;
	}

	public void run() {
		try {
			System.loadLibrary("insertionSort");
			data = insertionSort(data, probability);
			// If the array is empty than we know that with the probability 
			// the sort has failed
			if (data.length == 0){
				isSorted = false;
			} else {
				isSorted = true;
			}
		} catch(ThreadDeath td) {
			throw td;
		}
	}

	/* Getters and Setters */
	public boolean isSorted() {
		return isSorted;
	}

	public int[] getData() {
		return data;
	}

	/* Initialization of native method to be called in C */
	public native int[] insertionSort(int[] buf, double p);

}
