package utils;

import java.util.Random;

import common.Graph;
import common.Node;
import common.NodeVisitor;

/*
 * 1) read the input network
 * 2) randomly select a root node
 * 3) probabilistically determine the infection states of neighbors
 */

public class SimulatePropagation {
	private Graph g = null;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("usage: <input / output file prefix> <# of vertexes> <prob. of propagation");
			System.exit(1);
		}
		int n = Integer.parseInt(args[1]);
		double p = Double.parseDouble(args[2]);
		
		SimulatePropagation obj = new SimulatePropagation();
		obj.run(args[0], n, p);
	}
	
	private double loglikelihood = 0;
	
	private class PropagateInfection implements NodeVisitor {
		public void visit(Node thisnode) {
			
		}
	}

	private void run(String prefix, int n, double p) {
		// read graph
		g = new Graph(n);
		g.read_from_file(prefix + ".dat");
		
		Random rand = new Random();
		
		// select a source
		int root = rand.nextInt(n);
		
		// infect neighbors
		loglikelihood = 0;
		
		g.set_root(root);
		g.traverse(new PropagateInfection());
		
		// print out
	}

}
