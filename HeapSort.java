import java.util.*;

public class HeapSort extends Thread {

	private ArrayList<Integer> data;
	private double probability;
	private boolean isSorted;
	static double memAccesses;

	public HeapSort(ArrayList<Integer> unsorted, double probPrimFail) {
		data = unsorted;
		probability = probPrimFail;
		isSorted = false;
		memAccesses = 0;
	}

	public void run() {
		try {
			heapSort(data);
			double hazard = probability * memAccesses;
			double r = Math.random();
			if(r > 0.5 && r < (0.5 + hazard)){
				isSorted = false; // Variant has failed
			} else {
				isSorted = true; // Variant has succeeded
			}
		} catch (ThreadDeath td) {
			isSorted = false; // Variant thread has been killed before file was sorted
			throw td;
		}
	}

	/* Getters and Setters */
	public boolean isSorted() {
		return isSorted;
	}

	public ArrayList<Integer> getData() {
		return data;
	}

	
	/* The following three methods heapSort, heapify, and sift sort the data using */
	/* the heapsort algorithm (Grabbed from an old coding assignment) */
	private static void heapSort(ArrayList<Integer> data) {
		int count = data.size();
		memAccesses ++;
		heapify(data, count);
		int end = count - 1;
		while (end > 0) {
			int swap = data.get(0);
			data.set(0, data.get(end));
			data.set(end, swap);
			memAccesses += 3;
			sift(data, 0, end - 1);
			end--;

		}
	}

	public static void heapify(ArrayList<Integer> unsorted, int count) {
		memAccesses ++;
		int start = (count - 2) / 2;
			while (start >= 0) {
				sift(unsorted, start, count - 1);
				start --;
			}	
	}

	public static void sift(ArrayList<Integer> unsorted, int start, int end) {
		int root = start;
		while ((root * 2 + 1) <= end) {
			int leftchild = root * 2 + 1;

			if ((leftchild + 1 <= end) && (unsorted.get(leftchild) < unsorted.get(leftchild + 1))) {
				leftchild = leftchild + 1;
				memAccesses += 2;
			}
			if (unsorted.get(root) < unsorted.get(leftchild)) {
				int temp = unsorted.get(root);
				unsorted.set(root, unsorted.get(leftchild));
				unsorted.set(leftchild, temp);
				root = leftchild;
				memAccesses += 5;
			}
			else return;
		}
	}
}