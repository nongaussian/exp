package common;

public class Node {
	public int id;
	public Node[] neighbors = null;
	public int idx = 0;
	
	public int is_infected = 0;
	public int is_source = 0;
	
	public Node (int id) { this.id = id; }
	
	public void init_neighbor (int size) {
		neighbors = new Node[size];
		idx = 0;
	}
	
	public void add_neighbor (Node n) {
		neighbors[idx++] = n;
	}
	
	public void set_state (int st) {
		this.is_infected = st;
	}
	
	public void set_source (int st) {
		this.is_source = st;
	}
}
