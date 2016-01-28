package common;

public class Node {
	public int id;
	public Node[] neighbors = null;
	public int idx = 0;
	public NodeState state;
	
	public Node (int id) { this.id = id; }
	
	public void init_neighbor (int size) {
		neighbors = new Node[size];
		idx = 0;
	}
	
	public void add_neighbor (Node n) {
		neighbors[idx++] = n;
	}
	
	public void set_state (NodeState st) {
		this.state = st;
	}
	
	public int n_infected_parents (SimpleQueue queue) {
		int count = 0;
		for (Node n: neighbors) {
			if (queue.depth(n.id) <= queue.depth(id))
				count++;
		}
		return count;
	}
}
