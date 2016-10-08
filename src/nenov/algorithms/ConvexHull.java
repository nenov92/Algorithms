package nenov.algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ConvexHull {

	private ArrayList<Point> quickHull(ArrayList<Point> points) {
		ArrayList<Point> convexHull = new ArrayList<Point>();

		if (points.size() <= 3) {
			for (int i = 0; i < points.size(); i++) {
				if (!convexHull.contains(points.get(i))) {
					convexHull.add(points.get(i));
				}
			}
			return convexHull;
		}

		int minIndex = -1;
		int maxIndex = -1;
		int leftmostPoint = Integer.MAX_VALUE;
		int rightmostPoint = Integer.MIN_VALUE;

		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).x < leftmostPoint) {
				leftmostPoint = points.get(i).x;
				minIndex = i;
			}
			if (points.get(i).x > rightmostPoint) {
				rightmostPoint = points.get(i).x;
				maxIndex = i;
			}
		}

		Point A = points.get(minIndex);
		Point B = points.get(maxIndex);

		points.remove(A);
		points.remove(B);

		convexHull.add(A);
		if (!A.equals(B)) {
			convexHull.add(B);
		}

		ArrayList<Point> leftSet = new ArrayList<Point>();
		ArrayList<Point> rightSet = new ArrayList<Point>();

		Point P;
		for (int i = 0; i < points.size(); i++) {
			P = points.get(i);
			if (pointLocation(A, B, P) == 1 && (!rightSet.contains(P) && !convexHull.contains(P))) {
				rightSet.add(P);
			} else if (pointLocation(A, B, P) == -1 && (!leftSet.contains(P) && !convexHull.contains(P))) {
				leftSet.add(P);
			}
		}

		hullSet(A, B, rightSet, convexHull);
		hullSet(B, A, leftSet, convexHull);

		return convexHull;
	}

	public int pointLocation(Point A, Point B, Point P) {
		int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
		return (cp1 > 0) ? 1 : -1;
	}

	public int distance(Point A, Point B, Point P) {
		return Math.abs((B.x - A.x) * (A.y - P.y) - (B.y - A.y) * (A.x - P.x));
	}

	public void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull) {
		int insertPosition = hull.indexOf(A);
		Point P;

		if (set.size() == 0) {
			return;
		}
		if (set.size() == 1) {
			P = set.get(0);
			set.remove(P);
			hull.add(insertPosition, P);
			return;
		}

		int maxDistance = Integer.MIN_VALUE;
		int localDistance;
		int furthestPoint = -1;

		for (int i = 0; i < set.size(); i++) {
			P = set.get(i);
			localDistance = distance(A, B, P);
			if (localDistance > maxDistance) {
				maxDistance = localDistance;
				furthestPoint = i;
			}
		}

		P = set.get(furthestPoint);
		set.remove(P);
		hull.add(insertPosition, P);

		ArrayList<Point> leftAP = new ArrayList<Point>();
		ArrayList<Point> leftPB = new ArrayList<Point>();

		for (int i = 0; i < set.size(); i++) {
			if (pointLocation(A, P, set.get(i)) == 1) {
				leftAP.add(set.get(i));
			}
			if (pointLocation(P, B, set.get(i)) == 1) {
				leftPB.add(set.get(i));
			}
		}

		hullSet(A, P, leftAP, hull);
		hullSet(P, B, leftPB, hull);
	}

	public static void main(String args[]) throws IOException {
		// long start = System.nanoTime();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int size = Integer.parseInt(in.readLine());

		String[] infoToSplit;
		int x;
		int y;
		while (size != 0) {
			ArrayList<Point> points = new ArrayList<Point>();
			for (int i = 0; i < size; i++) {
				infoToSplit = in.readLine().split(" ");
				x = Integer.parseInt(infoToSplit[0]);
				y = Integer.parseInt(infoToSplit[1]);
				Point e = new Point(x, y);
				points.add(i, e);
			}

			ArrayList<Point> p = new ConvexHull().quickHull(points);
			out.write(p.size() + "\n");
			for (int i = 0; i < p.size(); i++) {
				out.write(p.get(i).x + " " + p.get(i).y + "\n");
			}
			size = Integer.parseInt(in.readLine());
		}
		in.close();
		out.flush();
		out.close();
		// System.out.println("TIME " + (double) ((System.nanoTime() - start) / 1000000000.0));
	}
}
