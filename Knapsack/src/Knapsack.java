import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Knapsack {

	public static void main(String[] args) {
		// Variable
		int option;										// option the user input
		int capacity;									// capacity of knapsack from user
		int numberOfItems;								// number of items enter by user
		ArrayList<Item> items = new ArrayList<Item>();	// the arrayList the stores all item
		int[][] matrix = null;
		boolean endProgram = false;						// a switch to continue or end program
		char yesOrNo;									// a user input for yes or no
		Scanner scan = new Scanner(System.in);			// scanner for user input
		
		// Menu
		while(!endProgram) {
			
			// Prompt
			System.out.println();
			System.out.println("*************************");
			System.out.println("** Knapsack Calculator **");
			System.out.println("*************************");
			System.out.println();
			System.out.print("Please enter a knapsack capacity(integer): ");
			capacity = scan.nextInt();	// get capacity from user
			System.out.print("Would you like to enter your own data?(y/n)? ");
			yesOrNo = scan.next().charAt(0);	// yes or no from user
			
			// If yes, get items from user via input
			if(yesOrNo == 'y' || yesOrNo == 'Y') {
				// Prompt for number of items
				System.out.print("Please enter the number of items: ");
				numberOfItems = scan.nextInt();

				// get weight and profit for each item and store in arrayList.
				for(int i = 0; i < numberOfItems; i++) {
					System.out.print("Please enter the weight and the profit: ");
					Item item = new Item(scan.nextInt(), scan.nextInt(), i);
					items.add(item);
				}
				System.out.println("Data has been stored.");
				
			// otherwise, get items from file
			} else {
				
				// open file for input
				try {
					File file = new File("input.txt");
					Scanner input = new Scanner(file);
					int count = 0;
					
					// check for all data in file and add them to arrayList
					while(input.hasNext()) {
						Item item = new Item(input.nextInt(), input.nextInt(), count);
						items.add(item);
						count++;
					}
					input.close();
					System.out.println("The data has been imported from \"input.txt\".");
				} 
				
				// Give error message if error occur when accessing file
				catch (IOException e) {
					System.err.println("File import was unsuccessful.");
					System.exit(1);
				}
				
			}
			
			// Prompt for using different methods
			System.out.println();
			System.out.println("1. Fractional knapsack.");
			System.out.println("2. 0/1 knapsack.");
			System.out.println("3. Exit program.");
			System.out.print("Please select an option above: ");
			option = scan.nextInt();	// get option from user
			System.out.println();
			
			// select methods
			switch(option) {
				
				// Fractional knapsack selected
				case 1:
					System.out.println("------Fractional Knapsack------");
					System.out.println();
					fractional(items, capacity);
					break;
					
				// 0/1 knapsack selected
				case 2:
					int[][] matrixFinal = zeroOne(items, capacity, matrix);			// matrix that is return from zeroOne
					ArrayList<Integer> itemsInBag = findItemInMatrix(matrixFinal);	// the arraylist that is in the knapsack
					System.out.println("------0/1 Knapsack------");
					System.out.println();
					System.out.println("Items that are in the knapsack:");
					
					// Print out the result of the 0/1 knapsack
					for(int i = 0; i < itemsInBag.size(); i++) {
						System.out.println("Item: " + items.get(itemsInBag.get(i)));	// get the items in the bag
					}
					System.out.println("Max Profit: " + matrixFinal[items.size()][capacity]);
					break;
				
				// Exit selected
				case 3:
					System.out.println("Exit Program.");
					endProgram = true;
					break;
				default:
					break;
			}
			System.out.println("Would you like to continue?(y/n)");
			yesOrNo = scan.next().charAt(0);	// yes or no from user
			if(yesOrNo == 'y' || yesOrNo == 'Y') {
				items.clear();
			} else {
				System.out.println("Exit Program.");
				endProgram = true;
			}
		}
		scan.close();

	}
	
	/**
	 * A static class that creates an Item, which can store its id, weight, profit, and ratio.
	 */
	public static class Item{
		int itemID;
		int weight;
		int profit;
		double ratio;
		
		/**
		 * A constructor that initialize Item.
		 * @param w is the weight
		 * @param p is the profit
		 * @param i is the id number
		 */
		public Item(int w, int p, int i) {
			itemID = i;
			weight = w;
			profit = p;
			ratio = (double) weight / profit;
		}

		/**
		 *  A static comparator that is used for the sort function for the Item class
		 */
		public static Comparator<Item> itemComparator = new Comparator<Item>() {
			
			public int compare(Item i1, Item i2) {
				double ratio1 = i1.ratio;
				double ratio2 = i2.ratio;
				return ratio1 > ratio2 ? 1 : -1;
			}
		};
		
		/**
		 * An override method of toString
		 * @return a string that outputs all the data in Item
		 */
		@Override
		public String toString() {
			return "{Id: " + itemID + " Weight: " + weight + " Profit: " + profit + "}";
		}
	}
	
	/**
	 * A void method that is calculates fractional knapsack problem
	 * @param items, an ArrayList the has all the Items
	 * @param capacity, the capacity of the knapsack
	 */
	public static void fractional(ArrayList<Item> items, int capacity) {
		double totalProfit = 0;	// the sum of all profit in knapsack
		double portion = 0;		// the fractional number for the last item that is 
		int index = 0;			// the index for iteration of the ArrayList
		Item currentItem;		// the Item that is at each iteration
		
		// Sort the array of Item to descending order
		Collections.sort(items, Item.itemComparator);
		
		// iteration thru array of Items and 
		while(capacity != 0 || index < items.size()) {
			currentItem = items.get(index);
			
			// If the weight can fit in the capacity at the time
			if(capacity >= currentItem.weight) {
				capacity -= currentItem.weight;	
				index++;
				totalProfit += currentItem.profit;	// add the profit of the current Item to total

			// If the weight is larger than the current capacity
			} else {
				portion = (double)capacity/currentItem.weight;
				totalProfit += currentItem.profit * portion;
				capacity = 0;
			}
		}
		
		// Result printed to the screen
		System.out.println("Items that are in the knapsack:");
		for(int i = 0; i < index; i++) {
			System.out.println(" 100% of Item: " + items.get(i));
		}
		System.out.printf("%2.1f%% of Item: %s\n", portion * 100, items.get(index));
		System.out.println("Total Profit: " + totalProfit);
		
	}
	
	/**
	 * A static method the uses 0/1 knapsack approach to find maximum profit
	 * @param items, the array of Item
	 * @param capacity, the knapsack capacity user give
	 * @return the maximum profit
	 */
	public static int[][] zeroOne(ArrayList<Item> items, int capacity, int[][] matrix) {
		int i, j;
		int n = items.size();
		matrix = new int[n+1][capacity+1];
		for(i = 1; i <= n; i++) {
			for(j = 1; j <= capacity; j++) {
				if(i == 0 || j == 0) {
					matrix[i][j] = 0;
				} else if(items.get(i-1).weight > j) {
					matrix[i][j] = matrix[i-1][j];
				} else {
					matrix[i][j] = Math.max(matrix[i-1][j], items.get(i-1).profit + matrix[i-1][j - items.get(i-1).weight]);
				}
			}
		}
		return matrix;
	}
	
	/**
	 * A method that finds the item that is put in the knapsack through the matrix
	 * @param matrix, the matrix from the 0/1 knapsack method
	 * @return a arrayList of the index of the Item
	 */
	public static ArrayList<Integer> findItemInMatrix(int[][] matrix) {
		int i = matrix.length-1;
		int j = matrix[0].length-1;
		ArrayList<Integer> itemsInBag = new ArrayList<Integer>();
		
		while(i > 0 && j > 0) {
			if(matrix[i][j] == matrix[i][j-1]) {
				j--;
			} else {
				if(!itemsInBag.contains(i-1)) {
					itemsInBag.add(i-1);
				}
				i--;
			}
		}
		return itemsInBag;
		
	}
	
	/**
	 * A void method that prints the array for debugging
	 * @param items ,the ArrayList of Items
	 */
	public static void printArray(ArrayList<Item> items) {
		for(int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).toString());
		}
	}
}
