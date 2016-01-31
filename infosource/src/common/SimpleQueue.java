package common;

public class SimpleQueue {
	private Node[] Q = null;
	public int[] depth = null;
	private int first = 0;
	private int last = 0;
	
	public SimpleQueue (int size) {
		Q = new Node[size];
		depth = new int [size];
		for (int i=0; i<size; i++) depth[i] = -1;
	}
	
	public void add(Node n, int d) {
		Q[last++] = n;
		depth[n.id] = d;
	}
	
	public int size () { return last; }
	
	public boolean is_full () {
		return last == Q.length;
	}
	
	public boolean is_empty () {
		return last == first;
	}

	public boolean is_visited (Node  n) {
		return is_visited(n.id);
	}
	
	public boolean is_visited (int n) {
		if (depth[n] < 0) return false;
		return true;
	}
	
	public int depth (Node  n) {
		return depth(n.id);
	}
	
	public int depth (int n) {
		return depth[n];
	}
	
	public Node shift () {
		if (first == last) return null;
		return Q[first++];
	}
	
	public Node pop () {
		if (last == 0) return null;
		return Q[--last];
	}
}
