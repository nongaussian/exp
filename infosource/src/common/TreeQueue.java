package common;

import java.util.ArrayList;

public class TreeQueue {
	private ArrayList<QueueEntry> Q = new ArrayList<QueueEntry> ();
	
	public TreeQueue () {
		
	}
	
	public void add(Node n, int depth) {
		Q.add(new QueueEntry (n, depth));
	}
	
	public int size () { return Q.size(); }
}
