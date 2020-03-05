
public class Hanoi {
	
	
	private static long startTime;
	
	private static boolean finish = true;	//finish
	/**
	 * Public method that initialized the Hanoi tower simulation.
	 * @param n, the number of disk
	 */
	public static long hanoi(int n) {
		finish = true;
		startTime = System.currentTimeMillis();
		move(n, 1, 3, 2);
		System.out.println(finish);
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * Private method that is recursive and moves the disks following the rules of Hanoi tower.
	 * @param n,  the number of disk
	 * @param from, the tower the disk is moving from
	 * @param to, the tower the disk is moving to
	 * @param help, the tower that helps the moving process
	 */
	private static void move(int n, int from, int to, int help) {
		
		// Check for time limit
		if(!finish || System.currentTimeMillis() - startTime > Main.timeLimit) {
			finish = false;
			return;
		} else {
			if(n == 1) {
				System.out.println("Move disk 1 from tower " + from + " to  tower " + to);
				return;
			}
			move(n - 1, from, help, to);
			System.out.println("Move disk " + n + " from tower " + from + " to  tower " + to);
			move(n - 1, help, to, from);
			return;
		}
	}
	
	/**
	 * Public method to get the finish status
	 * @return a boolean of finish 
	 */
	public static boolean getFinishStatus() {
		return finish;
	}
}
