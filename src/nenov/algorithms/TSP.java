package nenov.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TSP {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
		// long startTime = System.nanoTime();

		int numberOfPoints = scanner.nextInt();
		List<Coordinate> points = new ArrayList<Coordinate>();
		List<Integer> result = new ArrayList<Integer>();

		for (int i = 0; i < numberOfPoints; i++) {
			points.add(new TSP().new Coordinate(scanner.nextDouble(), scanner.nextDouble(), i));
		}
		scanner.close();

		result.add(0);
		Coordinate currentPoint = points.get(0);
		points.remove(0);

		int minDistance = Integer.MAX_VALUE;
		int localDistance = Integer.MAX_VALUE;
		int index = -1;

		while (points.size() > 0) {
			for (int i = 0; i < points.size(); i++) {
				localDistance = (int) Math.round(Math.sqrt(Math.pow(currentPoint.getX() - points.get(i).getX(), 2) + Math.pow(currentPoint.getY() - points.get(i).getY(), 2)));
				if (localDistance < minDistance) {
					minDistance = localDistance;
					index = (int) points.get(i).getIndex();
				}
			}

			if (index >= 0) {
				result.add(index);
				currentPoint = new TSP().getByIndex(points, index);
				points.remove(currentPoint);

				// refresh values
				minDistance = Integer.MAX_VALUE;
				localDistance = Integer.MAX_VALUE;
				index = -1;
			}
		}

		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}
		// double seconds = (double)(System.nanoTime() - startTime) / 1000000000.0;
		// System.out.println("Time: " + seconds);
	}

	public Coordinate getByIndex(List<Coordinate> points, int index) {
		for (Coordinate coordinate : points) {
			if (coordinate.getIndex() == index) {
				return coordinate;
			}
		}
		return null;
	}

	class Coordinate {
		public final double x;
		public final double y;
		public final int index;

		public Coordinate(double x, double y, int index) {
			this.x = x;
			this.y = y;
			this.index = index;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getIndex() {
			return index;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}
}
