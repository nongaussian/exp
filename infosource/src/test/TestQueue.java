package test;

import java.util.Random;

import common.Node;
import common.SimpleQueue;

public class TestQueue {

	public static void main(String[] args) {
		Random rand = new Random();
		
		SimpleQueue q = new SimpleQueue(100);
		for (int i=0; i<1000 && !q.is_full(); ) {
			if (rand.nextInt(2) == 0) {
				q.add(new Node(i++), 0);
			}
			else {
				if (!q.is_empty()) System.out.println(q.shift().id);
			}
		}
		
		Node n = null;
		while ( (n = q.shift()) != null) {
			System.out.println(n.id);
		}
		
		while ( (n = q.pop()) != null) {
			System.out.println(n.id);
		}
	}

}
