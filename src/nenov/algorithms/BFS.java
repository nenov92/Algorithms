package nenov.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BFS {
	public static final String PACMAN = "P";
	public static final String FOOD = ".";
	public static final String WALL = "%";
	public static final String EMPTY_SPACE = "-";

	class Node {
		private int x;
		private int y;
		private String nodeType;
		private int distance;
		private Node parent;

		public Node(int x, int y, String nodeType) {
			this.x = x;
			this.y = y;
			this.nodeType = nodeType;
			this.setDistance(Integer.MAX_VALUE);
			this.setParent(null);
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public String getNodeType() {
			return nodeType;
		}

		public void setNodeType(String nodeType) {
			this.nodeType = nodeType;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			return prime * ((getX() + 1) * 17) * ((getY() + 1) * 37) * getNodeType().hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof Node) {
				Node other = (Node) obj;
				if (this.getX() == other.getX() && this.getY() == other.getY() && this.getNodeType().equals(other.getNodeType())) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String toString() {
			return getX() + " " + getY();
		}
	}

	private static Node pacMan;
	private static Node food;
	private static Node[][] grid;
	private static int rows;
	private static int columns;
	private static List<Node> discovered;
	private static Set<Node> processed;

	private static List<Node> bfs() {
		Queue<Node> queue = new LinkedList<Node>();
		discovered = new ArrayList<Node>();
		processed = new HashSet<Node>();
		pacMan.setDistance(0);
		queue.add(pacMan);

		while (!queue.isEmpty()) {
			Node current = queue.poll();
			discovered.add(current);
			processed.add(current);

			if (current.equals(food)) {
				return constructPath(current);
			}

			addAdjacentNodes(current, queue);
		}
		return null;
	}

	private static List<Node> constructPath(Node node) {
		List<Node> path = new ArrayList<Node>();
		path.add(node);
		while (node.getParent() != null) {
			path.add(node.getParent());
			node = node.getParent();
		}
		return path;
	}

	private static void addAdjacentNodes(Node node, Queue<Node> queue) {
		Node retrievedFromGrid;
		// check for corner cases
		if (node.getX() - 1 >= 0) { // up
			retrievedFromGrid = grid[node.getX() - 1][node.getY()];
			if (!retrievedFromGrid.getNodeType().equals(WALL) && !retrievedFromGrid.getNodeType().equals(PACMAN)
					&& retrievedFromGrid.getDistance() == Integer.MAX_VALUE) {
				retrievedFromGrid.setDistance(node.getDistance() + 1);
				retrievedFromGrid.setParent(node);
				queue.add(retrievedFromGrid);
			}
		}
		if (node.getY() - 1 >= 0) { // left
			retrievedFromGrid = grid[node.getX()][node.getY() - 1];
			if (!retrievedFromGrid.getNodeType().equals(WALL) && !retrievedFromGrid.getNodeType().equals(PACMAN)
					&& retrievedFromGrid.getDistance() == Integer.MAX_VALUE) {
				retrievedFromGrid.setDistance(node.getDistance() + 1);
				retrievedFromGrid.setParent(node);
				queue.add(retrievedFromGrid);
			}
		}
		if (node.getY() + 1 < columns) { // right
			retrievedFromGrid = grid[node.getX()][node.getY() + 1];
			if (!retrievedFromGrid.getNodeType().equals(WALL) && !retrievedFromGrid.getNodeType().equals(PACMAN)
					&& retrievedFromGrid.getDistance() == Integer.MAX_VALUE) {
				retrievedFromGrid.setDistance(node.getDistance() + 1);
				retrievedFromGrid.setParent(node);
				queue.add(retrievedFromGrid);
			}
		}
		if (node.getX() + 1 < rows) { // down
			retrievedFromGrid = grid[node.getX() + 1][node.getY()];
			if (!retrievedFromGrid.getNodeType().equals(WALL) && !retrievedFromGrid.getNodeType().equals(PACMAN)
					&& retrievedFromGrid.getDistance() == Integer.MAX_VALUE) {
				retrievedFromGrid.setDistance(node.getDistance() + 1);
				retrievedFromGrid.setParent(node);
				queue.add(retrievedFromGrid);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String[] infoToSplit = in.readLine().split(" ");
		pacMan = new BFS().new Node(Integer.parseInt(infoToSplit[0]), Integer.parseInt(infoToSplit[1]), PACMAN);

		infoToSplit = in.readLine().split(" ");
		food = new BFS().new Node(Integer.parseInt(infoToSplit[0]), Integer.parseInt(infoToSplit[1]), FOOD);

		infoToSplit = in.readLine().split(" ");
		rows = Integer.parseInt(infoToSplit[0]);
		columns = Integer.parseInt(infoToSplit[1]);

		grid = new Node[rows][columns];
		for (int i = 0; i < rows; i++) {
			infoToSplit = in.readLine().split("");
			for (int j = 0; j < columns; j++) {
				grid[i][j] = new BFS().new Node(i, j, infoToSplit[j]);
			}
		}
		in.close();

		List<Node> result = bfs();
		if (result != null) {
			System.out.println(discovered.size());
			for (int i = 0; i < discovered.size(); i++) {
				System.out.println(discovered.get(i));
			}
			System.out.println(result.size() - 1);
			for (int i = result.size() - 1; i >= 0; i--) {
				System.out.println(result.get(i));
			}
		}
	}
}
