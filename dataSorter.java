import java.util.*;
import java.io.*;

public class dataSorter {

	public static void main(String[] args) throws IOException{
			
		String inputFileName = args[0];
		String outputFileName = args[1];
		Integer probPrimFail = Integer.parseInt(args[2]);
		Integer probSecFail = Integer.parseInt(args[3]);
		Integer timeLimit = Integer.parseInt(args[4]);

		int[] dataList = readFile(inputFileName);

		//Multi threaded elements
		//Primary variant: Java Heapsort
		//Backup variant: Insertion sort algorithm in C
		//Executive variant:
		//WatchdogTimer
		//Adjudicator (Executive thread)

		heapSort(dataList);
			
		writeFile(dataList, outputFileName);
	}

	// The following three methods heapSort, heapify, and sift sort the data using
	// the heapsort algorithm
	private static void heapSort(int[] data){
		int count = data.length;
		heapify(data, count);
		int end = count - 1;
		while (end > 0) {
			int temp = data[end];
			data[end] = data[0];
			data[0] = temp;
			sift(data, 0, end - 1);
			end--;

		}
	}

	public static void heapify(int[] unsorted, int count) {
		int start = (count - 2) / 2;
			while (start >= 0) {
				sift(unsorted, start, count - 1);
				start --;
			}	
	}

	public static void sift(int[] unsorted, int start, int end) {
		int root = start;
		while ((root * 2 + 1) <= end) {
			int leftchild = root * 2 + 1;

			if ((leftchild + 1 <= end) && (unsorted[leftchild]) < unsorted[leftchild + 1]) {
				leftchild = leftchild + 1;
			}
			if (unsorted[root] < unsorted[leftchild]) {
				int temp = unsorted[root];
				unsorted[root] = unsorted[leftchild];
				unsorted[leftchild] = temp;
				root = leftchild;
			}
			else return;
		}
	}

	private static int[] readFile(String filename) throws FileNotFoundException{

		int[] dataList = new int[1000];
		int i = 0;
		Scanner s = new Scanner(new File(filename));
		while (s.hasNext()){
			dataList[i] = Integer.parseInt(s.next());
			i ++;
		}

		return dataList;
	}


    private static void writeFile(int[] data, String filename) throws IOException {

      File file = new File(filename);

      if(!file.exists()){
        file.createNewFile();
      }

      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      for(int i = 0; i < data.length; i++){
        bw.write(data[i]);
        bw.write(" ");
      }

      bw.flush();
      bw.close();

    }

}