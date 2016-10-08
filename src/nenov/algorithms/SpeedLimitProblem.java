package nenov.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpeedLimitProblem {

	class Tuple {
		public final Integer x;
		public final Integer y;

		public Integer getX() {
			return this.x;
		}

		public Integer getY() {
			return this.y;
		}

		public Tuple(Integer x, Integer y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int miles = 0;

		List<Tuple> tuples = new ArrayList<Tuple>();

		int setsNumber = scanner.nextInt();
		while (setsNumber != -1) {
			for (int i = 0; i < setsNumber; i++) {
				Tuple map = new SpeedLimitProblem().new Tuple(Integer.valueOf(scanner.nextInt()), Integer.valueOf(scanner.nextInt()));
				tuples.add(map);
			}

			for (int i = 0; i < tuples.size(); i++) {
				if (i == 0) {
					miles += ((Integer) ((Tuple) tuples.get(i)).getX()).intValue() * ((Integer) ((Tuple) tuples.get(i)).getY()).intValue();
				} else {
					miles += ((Integer) ((Tuple) tuples.get(i)).getX()).intValue() * (((Integer) ((Tuple) tuples.get(i)).getY()).intValue() - ((Integer) ((SpeedLimitProblem.Tuple) tuples.get(i - 1)).getY()).intValue());
				}
			}

			System.out.println(miles + " miles");
		}
		scanner.close();
	}
}
