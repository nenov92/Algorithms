package nenov.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class ModifiedFibonacci {

	private static BigInteger modifiedFibonacci(BigInteger t1, BigInteger t2, int n) {
		BigInteger[] sequence = new BigInteger[n];
		sequence[0] = t1;
		sequence[1] = t2;
		for (int i = 2; i < sequence.length; i++) {
			sequence[i] = sequence[i - 2].add(sequence[i - 1].multiply(sequence[i - 1]));
		}
		return sequence[n - 1];
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] input = in.readLine().split(" ");
		in.close();
		
		System.out.println(modifiedFibonacci(BigInteger.valueOf(Integer.parseInt(input[0])), BigInteger.valueOf(Integer.parseInt(input[1])), Integer.parseInt(input[2])));
	}
}