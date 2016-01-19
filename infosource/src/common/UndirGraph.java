package common;

public class UndirGraph {
	private Node root = null;
	private Node[] nodes = null;
	
	public UndirGraph (int n_nodes) {
		nodes = new Node[n_nodes];
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

	public void add_edge(int n1, int n2) {
		nodes[n1].add_neighbor(n2);
		nodes[n2].add_neighbor(n1);
	}
	
	/*
	 * Graph traverse
	 */
	
	// in advance to the traverse of graph, we set the root node
	public void set_root() { 
		
	}
	
	// traverse the graph with BFS & compute log-likelihood of each case
	public void traverse (Traveler t) {
		
	}
}
