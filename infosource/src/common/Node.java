package common;

public class Node {
	public int id;
	public int[] neighbors = null;
	public int idx = 0;
	
	public Node (int id) { this.id = id; }
	
	public void init_neighbor (int size) {
		neighbors = new int[size];
		idx = 0;
	}
	
	public void add_neighbor (int n) {
		neighbors[idx++] = n;
	}
}
