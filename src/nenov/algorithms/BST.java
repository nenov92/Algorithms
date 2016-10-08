package nenov.algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BST {
	class Node {
		int value;
		Node left;
		Node right;

		public Node(int value) {
			this.value = value;
			left = null;
			right = null;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node node) {
			this.left = node;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node node) {
			this.right = node;
		}
	}

	private static Node root;
	private static int counter = 0;
	private static int localCounter = 0;

	public static Node getRoot() {
		return root;
	}

	public static void setRoot(Node root) {
		BST.root = root;
	}

	public static int insertNode(int value, Node currentNode, Node newNode) {
		localCounter += 1;
		if (currentNode.getValue() > newNode.getValue()) {
			if (currentNode.getLeft() == null) {
				currentNode.setLeft(newNode);
			} else {
				insertNode(value, currentNode.getLeft(), newNode);
			}
		} else if (currentNode.getValue() < newNode.getValue()) {
			if (currentNode.getRight() == null) {
				currentNode.setRight(newNode);
			} else {
				insertNode(value, currentNode.getRight(), newNode);
			}
		}
		return localCounter;
	}

	public static int insertNode(int value) {
		Node newNode = new BST().new Node(value);

		Node current = root;
		Node parent = null;
		while (true) {
			localCounter += 1;
			parent = current;
			if (value < current.getValue()) {
				current = current.left;
				if (current == null) {
					parent.left = newNode;
					return localCounter;
				}
			} else {
				current = current.right;
				if (current == null) {
					parent.right = newNode;
					return localCounter;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int size = Integer.parseInt(in.readLine());

		root = new BST().new Node(Integer.parseInt(in.readLine()));
		out.write(counter + "\n");
		int newValue;
		for (int i = 1; i < size; i++) {
			newValue = Integer.parseInt(in.readLine());
			counter += insertNode(newValue);
			localCounter = 0;
			out.write(counter + "\n");
		}

		in.close();
		out.flush();
		out.close();
	}
}
