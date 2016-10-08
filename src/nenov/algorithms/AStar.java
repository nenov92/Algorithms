package nenov.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {
	public static final String PACMAN = "P";
	public static final String FOOD = ".";
	public static final String WALL = "%";
	public static final String EMPTY_SPACE = "-";

	class Node {
		private int x;
		private int y;
		private Node parent;
		private int Gscore;
		private int Hscore;
		private int Fscore;
		private String nodeType;

		public Node(int x, int y, String nodeType) {
			this.x = x;
			this.y = y;
			this.nodeType = nodeType;
			this.parent = null;
			this.Gscore = Integer.MAX_VALUE;
			this.Hscore = Integer.MAX_VALUE;
			this.Fscore = Integer.MAX_VALUE;
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

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public int getGscore() {
			return Gscore;
		}

		public void setGscore(int gscore) {
			Gscore = gscore;
		}

		public int getHscore() {
			return Hscore;
		}

		public void setHscore(int hscore) {
			Hscore = hscore;
		}

		public int getFscore() {
			return Fscore;
		}

		public void setFscore(int fscore) {
			Fscore = fscore;
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
			// return "Node at x: " + getX() + " y: " + getY() + " with type " +
			// getNodeType();
			return getX() + " " + getY();
		}
	}

	private static Node pacMan;
	private static Node food;
	private static Node[][] grid;
	private static int rows;
	private static int columns;
	private static Set<Node> closedList;

	private static List<Node> aStarAlgorithm() {
		Comparator<Node> comparator = new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if (o1.getFscore() == o2.getFscore()) {
					return 0;
				} else if (o1.getFscore() > o2.getFscore()) {
					return 1;
				} else {
					return -1;
				}
			}
		};

		PriorityQueue<Node> openList = new PriorityQueue<Node>(comparator);
		closedList = new HashSet<Node>();
		openList.add(pacMan);

		pacMan.setGscore(0);
		pacMan.setFscore(pacMan.getGscore() + manhattanHeruistic(pacMan, food));

		while (!openList.isEmpty()) {
			Node current = openList.poll(); // retrieve the node with lowest f
											// score & remove it from openList
			if (current.equals(food)) {
				return constructPath(current);
			}

			closedList.add(current);
			for (Node neighbour : getNeighbours(current)) {
				if (!closedList.contains(neighbour)) {
					neighbour.setHscore(manhattanHeruistic(neighbour, food));
					neighbour.setFscore(neighbour.getGscore() + neighbour.getHscore());
					if (!openList.contains(neighbour)) {
						openList.add(neighbour);
					} else {
						Node localNode = findNode(neighbour, openList);
						if (localNode != null && localNode.getGscore() > neighbour.getGscore()) {
							localNode.setGscore(neighbour.getGscore());
							localNode.setParent(neighbour.getParent());
						}
					}
				}
			}
		}
		return null;
	}

	private static Node findNode(Node node, PriorityQueue<Node> queue) {
		for (Node localNode : queue) {
			if (localNode.equals(node)) {
				return localNode;
			}
		}
		return null;
	}

	private static int manhattanHeruistic(Node start, Node end) {
		return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
	}

	private static Set<Node> getNeighbours(Node node) {
		Set<Node> neighboursLocal = new HashSet<Node>();
		Set<Node> neighbours = new HashSet<Node>();

		// check for corner cases
		if (node.getX() + 1 < rows) {
			neighboursLocal.add(grid[node.getX() + 1][node.getY()]); // up
		}
		if (node.getX() - 1 > 0) {
			neighboursLocal.add(grid[node.getX() - 1][node.getY()]); // down
		}
		if (node.getY() - 1 > 0) {
			neighboursLocal.add(grid[node.getX()][node.getY() - 1]); // left
		}
		if (node.getY() + 1 < columns) {
			neighboursLocal.add(grid[node.getX()][node.getY() + 1]); // right
		}

		for (Node neighbour : neighboursLocal) {
			if (!neighbour.getNodeType().equals(WALL) && !neighbour.getNodeType().equals(PACMAN) && !closedList.contains(neighbour)) {
				neighbour.setGscore(node.getGscore() + 1);
				neighbour.setParent(node);
				neighbours.add(neighbour);
			}
		}
		return neighbours;
	}

	private static List<Node> constructPath(Node node) {
		List<Node> path = new ArrayList<Node>();
		path.add(food);
		while (node.getParent() != null) {
			// System.out.println(node);
			path.add(node.getParent());
			node = node.getParent();
		}
		// System.out.println(pacMan);
		// path.add(pacMan);
		return path;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String[] infoToSplit = in.readLine().split(" ");
		pacMan = new AStar().new Node(Integer.parseInt(infoToSplit[0]), Integer.parseInt(infoToSplit[1]), PACMAN);

		infoToSplit = in.readLine().split(" ");
		food = new AStar().new Node(Integer.parseInt(infoToSplit[0]), Integer.parseInt(infoToSplit[1]), FOOD);

		infoToSplit = in.readLine().split(" ");
		rows = Integer.parseInt(infoToSplit[0]);
		columns = Integer.parseInt(infoToSplit[1]);

		grid = new Node[rows][columns];
		for (int i = 0; i < rows; i++) {
			infoToSplit = in.readLine().split("");
			for (int j = 0; j < columns; j++) {
				grid[i][j] = new AStar().new Node(i, j, infoToSplit[j]);
			}
		}
		in.close();

		List<Node> result = aStarAlgorithm();
		if (result != null) {
			System.out.println(result.size() - 1);
			for (int i = result.size() - 1; i >= 0; i--) {
				System.out.println(result.get(i));
			}
		}
	}
}
