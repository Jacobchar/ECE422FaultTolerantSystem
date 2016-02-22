import java.util.*;
import java.io.*;

/* Program that sorts the given inputFileName.txt integer list using a heapsort */
/* algorithm and stores the values in a given outputFileName.txt                */
/**/
/**/
/**/
/* Jacob Charlebois, February 2016 */
public class dataSorter {

	public static void main(String[] args) throws IOException{
			
		String inputFileName = args[0];
		String outputFileName = args[1];
		Integer probPrimFail = Integer.parseInt(args[2]);
		Integer probSecFail = Integer.parseInt(args[3]);
		Integer timeLimit = Integer.parseInt(args[4]);

		ArrayList<Integer> dataList = readFile(inputFileName);

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
	// the heapsort algorithm (Grabbed from an old coding assignment)
	private static void heapSort(ArrayList<Integer> data){
		int count = data.size();
		heapify(data, count);
		int end = count - 1;
		while (end > 0) {
			int swap = data.get(0);
			data.set(0, data.get(end));
			data.set(end, swap);
			sift(data, 0, end - 1);
			end--;

		}
	}

	public static void heapify(ArrayList<Integer> unsorted, int count) {
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
			}
			if (unsorted.get(root) < unsorted.get(leftchild)) {
				int temp = unsorted.get(root);
				unsorted.set(root, unsorted.get(leftchild));
				unsorted.set(leftchild, temp);
				root = leftchild;
			}
			else return;
		}
	}

	/* Reads the list of integers from the given filename and stores them in an ArrayList */
	private static ArrayList<Integer> readFile(String filename) throws FileNotFoundException{

		ArrayList<Integer> dataList = new ArrayList<Integer>();
		Scanner s = new Scanner(new File(filename));
		while (s.hasNext()){
			dataList.add(Integer.parseInt(s.next()));
		}

		return dataList;
	}

 	/* Writes the list of integers to a specified file */
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