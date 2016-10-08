package nenov.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dijkstra {

	class Vertex {
		int id;

		public Vertex(int id) {
			this.id = id;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime * id;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (id != other.id) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return "" + id;
		}
	}

	class Edge {
		Vertex start;
		Vertex end;
		int weight;

		public Edge(Vertex start, Vertex end, int weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return prime * start.id * end.id;
		}

		@Override
		public boolean equals(Object o) {
			if (o != null && o instanceof Edge) {
				Edge other = (Edge) o;
				if ((this.start.id == other.start.id && this.end.id == other.end.id) || (this.start.id == other.end.id && this.end.id == other.start.id)) {
					return true;
				}
			}
			return false;
		}
	}

	public static List<Edge> edges;
	public static Set<Vertex> visited;
	public static Set<Vertex> unvisited;
	public static Map<Vertex, Integer> distance;

	private static void runAlgorithm(Vertex startingVertex) {
		visited = new HashSet<Vertex>();
		unvisited = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();

		distance.put(startingVertex, 0);
		unvisited.add(startingVertex);

		while (unvisited.size() > 0) {
			Vertex current = getMinimum(unvisited);
			visited.add(current);
			unvisited.remove(current);
			findMinimalDistances(current);
		}
	}

	private static Vertex getMinimum(Set<Vertex> vertices) {
		Vertex minVertex = null;

		for (Vertex vertex : vertices) {
			if (minVertex == null) {
				minVertex = vertex;
			} else {
				if (getDistance(minVertex) > getDistance(vertex)) {
					minVertex = vertex;
				}
			}
		}

		return minVertex;
	}

	private static int getDistance(Vertex vertex) {
		Integer dist = distance.get(vertex);
		if (dist == null) {
			return Integer.MAX_VALUE;
		} else {
			return dist;
		}
	}

	private static List<Vertex> getNeighbours(Vertex vertex) {
		List<Vertex> neighbours = new ArrayList<Vertex>();

		for (Edge edge : edges) {
			if (edge.start.equals(vertex) && !visited.contains(edge.end)) {
				neighbours.add(edge.end);
			} else if (edge.end.equals(vertex) && !visited.contains(edge.start)) {
				neighbours.add(edge.start);
			}
		}

		return neighbours;
	}

	private static int getWeight(Vertex start, Vertex end) {
		for (Edge edge : edges) {
			if (edge.start.equals(start) && edge.end.equals(end)) {
				return edge.weight;
			} else if (edge.start.equals(end) && edge.end.equals(start)) {
				return edge.weight;
			}
		}
		return -1;
	}

	private static void findMinimalDistances(Vertex vertex) {
		List<Vertex> neighbours = getNeighbours(vertex);

		for (Vertex neighbour : neighbours) {
			if (getDistance(neighbour) > getDistance(vertex) + getWeight(vertex, neighbour)) {
				distance.put(neighbour, getDistance(vertex) + getWeight(vertex, neighbour));
				unvisited.add(neighbour);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int testCases = Integer.parseInt(in.readLine());
		int numberOfNodes;
		int numberOfEdges;
		Edge edge;

		String[] infoToSplit;
		for (int i = 0; i < testCases; i++) {
			infoToSplit = in.readLine().split(" ");
			numberOfNodes = Integer.parseInt(infoToSplit[0]);
			numberOfEdges = Integer.parseInt(infoToSplit[1]);
			edges = new ArrayList<Edge>(numberOfEdges);

			for (int j = 0; j < numberOfEdges; j++) {
				infoToSplit = in.readLine().split(" ");
				edge = new Dijkstra().new Edge(new Dijkstra().new Vertex(Integer.parseInt(infoToSplit[0])), new Dijkstra().new Vertex(Integer.parseInt(infoToSplit[1])), Integer.parseInt(infoToSplit[2]));
				// if there is another edge with the same start and end vertices,
				// then the one with smaller weight should remain in the list of edges
				if (edges.contains(edge)) {
					for (int k = 0; k < edges.size(); k++) {
						if (edges.get(k).equals(edge) && edges.get(k).weight > edge.weight) {
							edges.remove(k);
							edges.add(edge);
						}
					}
				} else {
					edges.add(edge);
				}
			}

			Vertex start = new Dijkstra().new Vertex(Integer.parseInt(in.readLine()));
			runAlgorithm(start);

			// j varies between 1 and 3000
			Integer result;
			for (int j = 1; j < numberOfNodes + 1; j++) {
				if (j != start.id) {
					result = distance.get(new Dijkstra().new Vertex(j));
					System.out.print(result == null ? "-1 " : result + " ");
				}
			}
			System.out.println();
		}
		in.close();
	}
}