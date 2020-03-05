

public class Sort{
	
	
	private static long startTime;
	
	private static boolean finish = true;	//finish
	
	private static boolean finish2 = true;	//finish2
	
	/**
	 * Private method, used by mergeSort to merge arrays.
	 * @param array, The array to merge.
	 * @param start, The starting index.
	 * @param mid, The mid point of the whole array, also the end of left array.
	 * @param end, The end point of the right array.
	 */
	private static void merge(int[] array, int start, int mid, int end) {
		
		// Initializing variable
		int i = start;
		int j = mid + 1;
		int k = 0;
		
		// Create temp array
		int[] temp = new int[end - start +1];
		
		//
		while(i <= mid && j <= end) {
			if(array[i] <= array[j]) {
				temp[k] = array[i];
				i++;
			} else {
				temp[k] = array[j];
				j++;
			}
			k++;
		}
		
		// add remaining elements in first interval
		while(i <= mid) {
			temp[k] = array[i];
			k++;
			i++;
		}
		
		// add remaining elements in second interval
		while(j <= end) {
			temp[k] = array[j];
			k++;
			j++;
		}
		
		// copy temp to original interval
		for(i = start; i <= end; i++) {
			array[i] = temp[i - start];
		}
	}
	
	/**
	 * Public method that runs mergeSort and records the time spent
	 * @param array, the array to be sort
	 * @return a long, the time spent 
	 */
	public static long runMergeSort(int[] array) {
		finish = true;
		startTime = System.currentTimeMillis();
		mergeSort(array, 0, array.length - 1);
		System.out.println(finish);
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * Public recursive method to perform merge sort
	 * @param array, The array to be sorted
	 * @param start, The starting index
	 * @param end, The end index
	 */
	public static void mergeSort(int[] array, int start, int end) {
		if(!finish || System.currentTimeMillis() - startTime > Main.timeLimit) {
			finish = false;
			return;
		} else {
			if(start < end) {
				int mid = (start + end) / 2;
				mergeSort(array, start, mid);
				mergeSort(array, mid + 1, end);
				merge(array, start, mid, end);
			}
		}
	}
	
	/**
	 * Private method takes last element as pivot and places all smaller elements to left and greater elements to right of the pivot
	 * @param array, The array to be sorted
	 * @param low, The starting index
	 * @param high, The ending index
	 * @return an int
	 */
	private static int partition(int[] array, int low, int high) {
		int pivot = array[high];
		int i = (low - 1);
		for(int j = low; j < high; j++) {
			if(array[j] <= pivot) {
				i++;
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		int temp = array[i + 1];
		array[i + 1] = array[high];
		array[high] = temp;
		
		return i + 1;
	}
	
	/**
	 * Public method that is recursive and does quick sort
	 * @param array, array being sorted
	 * @param low, the first index
	 * @param high, the last index
	 */
	public static void quickSort(int[] array, int low, int high) {
		if(!finish2 || System.currentTimeMillis() - startTime > Main.timeLimit) {
			finish2 = false;
			return;
		} else {
			if(low < high) {
				int pi = partition(array, low, high);
				quickSort(array, low, pi - 1);
				quickSort(array, pi + 1, high);
			}
		}
	}
	
	/**
	 * Public method that runs quickSort and record time spent
	 * @param array, array being sorted
	 * @return a long, the time spent
	 */
	public static long runQuickSort(int[] array) {
		finish2 = true;
		startTime = System.currentTimeMillis();
		quickSort(array, 0, array.length - 1);
		System.out.println(finish2);
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * Public method to get the finish status
	 * @return a boolean of finish 
	 */
	public static boolean getFinishStatus() {
		return finish;
	}
	
	/**
	 * Public method to get the finish2 status
	 * @return a boolean of finish2
	 */
	public static boolean getFinishStatus2() {
		return finish2;
	}
	
}
