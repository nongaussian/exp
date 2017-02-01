package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Graph {
	public Node[] nodes = null;
	public int size = 0;
	
	public int infosource = -1;
	
	public Graph (int n_nodes) {
		nodes = new Node[n_nodes];
		size = n_nodes;
		
		for (int i=0; i<n_nodes; i++) {
			nodes[i] = new Node (i);
		}
	}
	
	/*
	 * Graph load & output
	 */
	
	public void set_neighbor_size (int n, int size) {
		nodes[n].init_neighbor(size);
	}
	
	/*
	 * Graph traverse
	 */
	
	// traverse the graph with BFS; calling traverse with a node visitor t executes t.visit for every node
	public void traverse (Node root, SimpleQueue queue, NodeVisitor t) {
		if (root == null) {
			System.err.println("error: root is not set");
			System.exit(1);
		}
		
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
			if (!t.visit(node, queue.depth)) break;
		}
	}
	
	/*
	 * Data load
	 */
	
	public void read_graph_from_file(String filename) {
		String thisLine = null;
		try {
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
				else {
					System.err.println("error: inconsistent number of neighboring nodes");
					System.exit(1);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void read_state_from_file(String filename) {
		String thisLine = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			while ((thisLine = br.readLine()) != null) {
				String[] arr = thisLine.split("\t");
				int id = Integer.parseInt(arr[0]);
				nodes[id].is_infected = Integer.parseInt(arr[1]);
				//nodes[id].is_source = Integer.parseInt(arr[2]);
				if (Integer.parseInt(arr[2]) == 1) {
					infosource = id;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Debug
	 */
	
	public void print_graph() {
		for (int i=0; i<size; i++) {
			System.out.print(nodes[i].id + "\t" + nodes[i].neighbors.length);
			for (Node neigh: nodes[i].neighbors) {
				System.out.print("\t" + neigh.id);
			}
			System.out.println();
		}
	}
}
