
public class Matrix {
	
	private static long startTime;
	
	private static boolean finish = true;	//finish
	
	private static boolean finish2 = true;	//finish
	
	/**
	 * Public method that runs classicMult and records the time spent.
	 * @param firstMatrix, matrix to multiply.
	 * @param secondMatrix, matrix to multiply.
	 * @param size, the dimension of matrix.
	 * @return time spent running the program.
	 */
	public static long runClassicMult(int[][] firstMatrix, int[][] secondMatrix, int size) {
		finish = true;
		startTime = System.currentTimeMillis();
		classicMult(firstMatrix, secondMatrix, size);
		System.out.println(finish);
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Public method that multiplies two matrices and return the product
	 * @param firstMatrix, matrix to multiply.
	 * @param secondMatrix, matrix to multiply.
	 * @param size, the dimension of matrix.
	 * @return a matrix of product
	 */
	public static int[][] classicMult(int[][] firstMatrix, int[][] secondMatrix, int size) {
		int[][] product = new  int[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				for(int k = 0; k < size; k++) {
					if(!finish || System.currentTimeMillis() - startTime > Main.timeLimit) {
						finish = false;
						return product;
					}
					product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
				}
			}
		}
		return product;
	}
	
	/**
	 * Public method that runs the strassen's Matrix multiplication, and records time spent
	 * @param firstMatrix, matrix to multiply.
	 * @param secondMatrix, matrix to multiply.
	 * @return time spent running the program.
	 */
	public static long runStrassenMult(int[][] firstMatrix, int[][] secondMatrix) {
		finish2 = true;
		startTime = System.currentTimeMillis();
		strassenMult(firstMatrix, secondMatrix);
		System.out.println(finish2);
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * Public method that multiplies two matrices with Strassen's matrix multiplication
	 * @param firstMatrix, matrix to multiply.
	 * @param secondMatrix, matrix to multiply.
	 * @return a matrix of the product
	 */
	public static int[][] strassenMult(int[][] firstMatrix, int[][] secondMatrix){
		
		int size = firstMatrix.length;
		int[][] product = new  int[size][size];
		
		if(!finish2 || System.currentTimeMillis() - startTime > Main.timeLimit) {
			finish2 = false;
			return product;
		}
		
		if(size == 1) {
			product[0][0] = firstMatrix[0][0] * secondMatrix[0][0];
		} else {
			int [][] f11 = new int[size / 2][size / 2];
			int [][] f12 = new int[size / 2][size / 2];
			int [][] f21 = new int[size / 2][size / 2];
			int [][] f22 = new int[size / 2][size / 2];
			int [][] s11 = new int[size / 2][size / 2];
			int [][] s12 = new int[size / 2][size / 2];
			int [][] s21 = new int[size / 2][size / 2];
			int [][] s22 = new int[size / 2][size / 2];
			
			split(firstMatrix, f11, 0, 0);
			split(firstMatrix, f12, 0 , size / 2);
            split(firstMatrix, f21, size / 2, 0);
            split(firstMatrix, f22, size / 2, size / 2);
            
            split(secondMatrix, s11, 0 , 0);
            split(secondMatrix, s12, 0 , size / 2);
            split(secondMatrix, s21, size / 2, 0);
            split(secondMatrix, s22, size / 2, size / 2);
            
            int[][] m1 = strassenMult(add(f11, f22), add(s11, s22));
            int[][] m2 = strassenMult(add(f21, f22), s11);
            int[][] m3 = strassenMult(f11, sub(s12, s22));
            int[][] m4 = strassenMult(f22, sub(s21, s11));
            int[][] m5 = strassenMult(add(f11, f12), s22);
            int[][] m6 = strassenMult(sub(f21, f11), add(s11, s12));
            int[][] m7 = strassenMult(sub(f12, f22), add(s21, s22));
            
            int[][] c11 = add(sub(add(m1, m4), m5), m7);
            int[][] c12 = add(m3, m5);
            int[][] c21 = add(m2, m4);
            int[][] c22 = add(sub(add(m1, m3), m2), m6);
            
            join(c11, product, 0 , 0);
            join(c12, product, 0 , size / 2);
            join(c21, product, size / 2, 0);
            join(c22, product, size / 2, size / 2);
		}
		
		return product;
		
	}
	
	/**
	 * Private method that subtracts two matrices 
	 * @param firstMatrix, matrix being subtracted from
	 * @param secondMatrix, matrix subtracting
	 * @return the result of the subtraction 
	 */
	private static int[][] sub(int[][] firstMatrix, int[][] secondMatrix){
		int size = firstMatrix.length;
		int[][] result = new int[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				result[i][j] = firstMatrix[i][j] - secondMatrix[i][j];
			}
		}
		return result;
	}
	
	/**
	 * Private method that adds two matrices
	 * @param firstMatrix, matrix being added to
	 * @param secondMatrix, matrix adding
	 * @return the result of the addition
	 */
	private static int[][] add(int[][] firstMatrix, int[][] secondMatrix){
		int size = firstMatrix.length;
		int[][] result = new int[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				result[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
			}
		}
		return result;
	}
	
	/**
	 * Private method that splits a larger matrix(parent) into smaller matrix(child)
	 * @param parent, the matrix being split
	 * @param child, the matrix being stored in
	 * @param ib, the index where the split starts in row
	 * @param jb, the index where the split starts in col
	 */
	private static void split(int[][] parent, int[][] child, int ib, int jb) {
		for(int i1 = 0, i2 = ib; i1 < child.length; i1++, i2++) {
			for(int j1 = 0, j2 = jb; j1 < child.length; j1++, j2++) {
				child[i1][j1] = parent[i2][j2];
			}
		}
	}
	
	/**
	 * Private method that joins smaller matrix (child) into larger matrix(parent);
	 * @param child, the matrix joining
	 * @param parent, the matrix where the child joins to
	 * @param ib, the start of the index of row
	 * @param jbm the start of the index of col
	 */
	private static void join(int[][] child, int[][] parent, int ib, int jb) {
		for(int i1 = 0, i2 = ib; i1 < child.length; i1++, i2++) {
			for(int j1 = 0, j2 = jb; j1 < child.length; j1++, j2++) {
				parent[i2][j2] = child[i1][j1];
			}
		}
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
