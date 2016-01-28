package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Graph {
	private Node root = null;
	public Node[] nodes = null;
	public int size = 0;
	
	public Graph (int n_nodes) {
		nodes = new Node[n_nodes];
		size = n_nodes;
		
		for (int i=0; i<n_nodes; i++) {
			nodes[i] = new Node (i);
		}
	}
	
	/*
	 * Graph loading
	 */
	
	public void set_neighbor_size (int n, int size) {
		nodes[n].init_neighbor(size);
	}
	
	/*
	 * Graph traverse
	 */
	
	// in advance to the traverse of graph, we set the root node
	public void set_root(int n) {
		set_root(nodes[n]);
	}
	public void set_root(Node n) {
		root = n;
	}
	
	// traverse the graph with BFS
	public void traverse (NodeVisitor t) {
		if (root == null) {
			System.err.println("error: root is not set");
			System.exit(1);
		}
		SimpleQueue queue = new SimpleQueue(size);
		
		int depth = 0;
		queue.add(root, 0);
		
		Node node = null;
		while ((node = queue.shift()) != null) {
			depth = queue.depth(node.id);
			for (Node child: node.neighbors) {
				if (!queue.is_visited(child)) {
					queue.add(child, depth + 1);
				}
			}
			
			// do something for the current visiting node
			t.visit(node);
		}
	}
	
	/*
	 * Data load
	 */
	
	public void read_from_file(String filename) {
		String thisLine = null;
		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			while ((thisLine = br.readLine()) != null) {
				String[] arr = thisLine.split("\t");
				int id = Integer.parseInt(arr[0]);
				int n = Integer.parseInt(arr[1]);
				
				if (n == arr.length - 2) {
					nodes[id].init_neighbor(n);
					for (int i=2; i<arr.length; i++) {
						nodes[id].add_neighbor(nodes[Integer.parseInt(arr[i])]);
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
