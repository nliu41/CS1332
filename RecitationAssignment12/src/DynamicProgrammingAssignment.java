/**
 * Assignment to teach dynamic programming using 3 simple example problems:
 * 1. Fibonacci numbers
 * 2. Longest common subsequence
 * 3. Edit distance
 * 
 * @author Julia Ting (julia.ting@gatech.edu)
 */
public class DynamicProgrammingAssignment {
	public static int num_calls = 0; //DO NOT TOUCH

	/**
	 * Calculates the nth fibonacci number: fib(n) = fib(n-1) + fib(n-2).
	 * Remember that fib(0) = 0 and fib(1) = 1.
	 * 
	 * This should NOT be done recursively - instead, use a 1 dimensional
	 * array so that intermediate fibonacci values are not re-calculated.
	 * 
	 * The running time of this function should be O(n).
	 * 
	 * @param n A number 
	 * @return The nth fibonacci number
	 */
	public static int fib(int n) {
		num_calls++; //DO NOT TOUCH

		if (n < 1) {
			return 0;
		}

		int[] cache = new int[n + 1];

		cache[0] = 0;
		cache[1] = 1;

		for (int i = 2; i < n + 1; i++) {
			cache[i] = cache[i - 1] + cache[i - 2];
		}

		return cache[n];
	}
	
	/**
	 * Calculates the length of the longest common subsequence between a and b.
	 * 
	 * @param a
	 * @param b
	 * @return The length of the longest common subsequence between a and b
	 */
	public static int lcs(String a, String b) {
		num_calls++; //DO NOT TOUCH

		int[][] cache = new int[a.length() + 1][b.length() + 1];

		// base case
		// first row, first column zero'ed
		for (int i = 1; i < cache.length; i++) {
			for (int j = 1; j < cache[i].length; j++) {
				int partial = cache[i - 1][j - 1] + ((a.charAt(i - 1) == b.charAt(j - 1)) ? 1 : 0);
				cache[i][j] = Math.max(cache[i - 1][j], Math.max(cache[i][j - 1], partial));
			}
		}

		return cache[a.length()][b.length()];
	}

	/**
	 * Calculates the edit distance between two strings.
	 * 
	 * @param a A string
	 * @param b A string
	 * @return The edit distance between a and b
	 */
	public static int edit(String a, String b) {
		num_calls++; //DO NOT TOUCH

		int[][] cache = new int[a.length() + 1][b.length() + 1];

		for (int i = 0; i < cache.length; i++) {
			cache[i][0] = i;
		}

		for (int i = 0; i < cache[0].length; i++) {
			cache[0][i] = i;
		}

		for (int i = 1; i < cache.length; i++) {
			for (int j = 1; j < cache[i].length; j++) {
				int ops = ((a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1);
				cache[i][j] = Math.min(cache[i - 1][j] + 1,
									Math.min(cache[i][j - 1] + 1, cache[i - 1][j - 1] + ops));
			}
		}

		return cache[cache.length - 1][cache[0].length - 1];
	}
}

