package nenov.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DFS {
	public static final String PACMAN = "P";
	public static final String FOOD = ".";
	public static final String WALL = "%";
	public static final String EMPTY_SPACE = "-";

	class Node {
		private int x;
		private int y;
		private String nodeType;

		public Node(int x, int y, String nodeType) {
			this.x = x;
			this.y = y;
			this.nodeType = nodeType;
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
	private static Set<Node> discovered;
	private static List<Node> wholePath;

	private static List<Node> dfs() {
		Stack<Node> stack = new Stack<Node>();
		discovered = new HashSet<Node>();
		List<Node> path = new ArrayList<Node>();
		wholePath = new ArrayList<Node>();
		stack.push(pacMan);

		while (!stack.isEmpty()) {
			Node current = stack.pop(); // retrieve and remove the node from the
										// top of the stack
			if (!wholePath.contains(current)) {
				wholePath.add(current);
			}

			if (current.equals(food)) {
				path.add(current);
				return path;
			}

			if (!discovered.contains(current)) {
				discovered.add(current);
				path.add(current);
				addAdjacentNodes(current, stack);
			}

		}
		return null;
	}

	private static void addAdjacentNodes(Node node, Stack<Node> stack) {
		Node retrievedFromGrid;
		// check for corner cases
		if (node.getX() - 1 >= 0) { // up
			retrievedFromGrid = grid[node.getX() - 1][node.getY()];
			if (!retrievedFromGrid.getNodeType().equals(WALL) && !retrievedFromGrid.getNodeType().equals(PACMAN)) {
				stack.push(retrievedFromGrid);
			}
		}
		if (node.getY() - 1 >= 0) { // left
			retrievedFromGrid = grid[node.getX()][node.getY() - 1];
			if (!retrievedFromGrid.getNodeType().equals(WALL) && !retrievedFromGrid.getNodeType().equals(PACMAN)) {
				stack.push(retrievedFromGrid);
			}
		}
		if (node.getY() + 1 < columns) { // right
			retrievedFromGrid = grid[node.getX()][node.getY() + 1];
			if (!retrievedFromGrid.getNodeType().equals(WALL) && !retrievedFromGrid.getNodeType().equals(PACMAN)) {
				stack.push(retrievedFromGrid);
			}
		}
		if (node.getX() + 1 < rows) { // down
			retrievedFromGrid = grid[node.getX() + 1][node.getY()];
			if (!retrievedFromGrid.getNodeType().equals(WALL) && !retrievedFromGrid.getNodeType().equals(PACMAN)) {
				stack.push(retrievedFromGrid);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String[] infoToSplit = in.readLine().split(" ");
		pacMan = new DFS().new Node(Integer.parseInt(infoToSplit[0]), Integer.parseInt(infoToSplit[1]), PACMAN);

		infoToSplit = in.readLine().split(" ");
		food = new DFS().new Node(Integer.parseInt(infoToSplit[0]), Integer.parseInt(infoToSplit[1]), FOOD);

		infoToSplit = in.readLine().split(" ");
		rows = Integer.parseInt(infoToSplit[0]);
		columns = Integer.parseInt(infoToSplit[1]);

		grid = new Node[rows][columns];
		for (int i = 0; i < rows; i++) {
			infoToSplit = in.readLine().split("");
			for (int j = 0; j < columns; j++) {
				grid[i][j] = new DFS().new Node(i, j, infoToSplit[j]);
			}
		}
		in.close();

		List<Node> result = dfs();
		if (result != null) {
			System.out.println(wholePath.size());
			for (int i = 0; i < wholePath.size(); i++) {
				System.out.println(wholePath.get(i));
			}
			System.out.println(result.size() - 1);
			for (int i = 0; i < result.size(); i++) {
				System.out.println(result.get(i));
			}
		}
	}
}
