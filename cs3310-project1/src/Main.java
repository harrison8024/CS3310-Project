import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;


public class Main {
//	public static final long timeLimit = 1000;
	public static final long timeLimit = 10 * 60 * 1000; // Time limit = 10 minutes

	public static void main(String[] args) {
//		task1();
//		task2();
		task3();

	}
	
	private static int[] randomArr(int size) {
		Random ran = new Random();
		return ran.ints(size).toArray();
	}
	
	private static int[][] randomMatrix(int size) {
		Random ran = new Random();
		int[][] matrix = new int[size][size];
		for(int i = 0 ;i < size; i++) {
			for(int j = 0; j < size; j++) {
				matrix[i][j] = ran.nextInt(10);	//the number in the matrix will be from 0-9
			}
		}
		return matrix;
	}
	
	private static void task1() {
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		ArrayList<Long> times = new ArrayList<Long>();
		int n = 10000;
		while(Sort.getFinishStatus()) {
			sizes.add(n);
			int[] arr = randomArr(n);
			long time = Sort.runMergeSort(arr);
			times.add(time);
			n *= 2;
		}
		createCSV("sort", sizes, times);
		ArrayList<Integer> sizes2 = new ArrayList<Integer>();
		ArrayList<Long> times2 = new ArrayList<Long>();
		n = 10000;
		while(Sort.getFinishStatus2()) {
			sizes2.add(n);
			int[] arr2 = randomArr(n);
			long time2 = Sort.runQuickSort(arr2);
			times2.add(time2);
			n *= 2;
		}
		appendCSV("sort", sizes2, times2);
	}

	private static void printArray(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+ ", ");
		}
		System.out.println();
	}
	
	private static void task2() {
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		ArrayList<Long> times = new ArrayList<Long>();
		int n = 2;
		while(Hanoi.getFinishStatus()) {
			long time = Hanoi.hanoi(n);
			sizes.add(n);
			times.add(time);
			n *= 2;
		}
		createCSV("Hanoi",sizes, times);
	}
	
	private static void task3() {
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		ArrayList<Long> times = new ArrayList<Long>();
		int n = 2;
		while(Matrix.getFinishStatus()) {
			sizes.add(n);
			int[][] matrix1 = randomMatrix(n);
			int[][] matrix2 = randomMatrix(n);
			long time = Matrix.runClassicMult(matrix1, matrix2, n);
			times.add(time);
			n *= 2;
		}
		createCSV("matrix", sizes, times);
		ArrayList<Integer> sizes2 = new ArrayList<Integer>();
		ArrayList<Long> times2 = new ArrayList<Long>();
		n = 2;
		while(Matrix.getFinishStatus2()) {
			sizes2.add(n);
			int[][] matrix1 = randomMatrix(n);
			int[][] matrix2 = randomMatrix(n);
			long time2 = Matrix.runStrassenMult(matrix1, matrix2);
			times2.add(time2);
			n *= 2;
		}
		appendCSV("matrix", sizes2, times2);
	}
	
	
	private static void createCSV(String fileName, ArrayList<Integer> sizes, ArrayList<Long> times) {
		FileWriter csv;
		try {
			csv = new FileWriter(fileName + ".csv");
			csv.append("Size(n)");
			for(int i = 0; i < sizes.size(); i++) {
				csv.append("," + sizes.get(i));
				
			}
			csv.append("\nTimes(ms)");
			for(Long time : times) {
				csv.append("," + time);
			}
			csv.flush();
			csv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void appendCSV(String fileName, ArrayList<Integer> sizes, ArrayList<Long> times) {
		try {
			BufferedWriter csv = new BufferedWriter(new FileWriter(fileName + ".csv", true));
			csv.newLine();
			csv.append("2\nSize(n)");
			for(int i = 0; i < sizes.size(); i++) {
				csv.append("," + sizes.get(i));
				
			}
			csv.append("\nTimes(ms)");
			for(Long time : times) {
				csv.append("," + time);
			}
			csv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Private method used to test out matrix.
	 * @param matrix, matrix to be printed.
	 */
	private static void printMatrix(int[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++){
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
