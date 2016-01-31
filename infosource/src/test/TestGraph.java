package test;

import java.util.Random;

import common.Graph;
import common.Node;
import common.NodeVisitor;
import common.Precal;
import common.SimpleQueue;

public class TestGraph {
	
	public static class TestVisitor implements NodeVisitor {
		public boolean visit(Node thisnode, int[] depth) {
			int count = 0;
			for (Node neigh: thisnode.neighbors) {
				if (depth[neigh.id] < depth[thisnode.id]) {
					count++;
				}
			}

			System.out.println("visiting " + thisnode.id +
					": depth = " + depth[thisnode.id] + 
					", # of parants = " + count);
			
			return true;
		}
	}

	public static void main(String[] args) {
		Graph g = new Graph(8);
		g.read_graph_from_file("dat/test1.dat");
		
		System.out.println("*** graph ***");
		g.print_graph();
		
		for (int i=0; i<8; i++) {
			System.out.println("*** bread first search (root = " + i + ") ***");
			g.traverse(g.nodes[i], new SimpleQueue(8), new TestVisitor());
		}
	}

}
