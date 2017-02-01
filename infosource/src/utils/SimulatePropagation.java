package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

import common.Graph;
import common.Node;
import common.NodeVisitor;
import common.Precal;
import common.SimpleQueue;

/*
 * 1) read the input network
 * 2) randomly select a root node
 * 3) probabilistically determine the infection states of neighbors
 */

public class SimulatePropagation {
	private Graph g = null;
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("usage: <input / output file prefix> <# of vertexes> <prob. of propagation>");
			System.exit(1);
		}
		int n = Integer.parseInt(args[1]);
		double p = Double.parseDouble(args[2]);
		
		//Precal.init(p);
		//for (int i=0; i<10; i++) System.out.println(Precal.log_prob[i]);
		
		SimulatePropagation obj = new SimulatePropagation();
		obj.run(args[0], n, p);
	}
	
	private class PropagateInfection implements NodeVisitor {
		private double p;
		public PropagateInfection(double p) {
			this.p = p;
		}
		private Random rand = new Random();
		public boolean visit(Node thisnode, int[] depth) {
			//int count = 0;
			for (Node neigh: thisnode.neighbors) {
				if (depth[neigh.id] <= depth[thisnode.id] 
						&& neigh.is_infected == 1) {
					if (rand.nextDouble() < p) {
						thisnode.is_infected = 1;
						break;
					}
				}
			}
			
			/*if (Math.log(rand.nextDouble()) < Precal.log_prob_pos[count]) {
				thisnode.is_infected = 1;
			}
			else {
				stt[thisnode.id] = 0;
			}*/
			
			return true;
		}
	}

	private void run(String prefix, int n, double p) {
		// read graph
		g = new Graph(n);
		g.read_graph_from_file(prefix + ".dat");
		
		Random rand = new Random();
		
		// select a source
		int root = rand.nextInt(n);
		g.nodes[root].is_infected = 1;
		
		g.traverse(g.nodes[root], new SimpleQueue(g.size), new PropagateInfection(p));
		
		// print out: <node id> tab <infected or not> tab <root or not>
		try {
			PrintStream out = new PrintStream(new FileOutputStream(prefix + ".stt"));
			for (Node v: g.nodes) {
				if (v.id == root)
					out.println(v.id + "\t" + v.is_infected + "\t1");
				else
					out.println(v.id + "\t" + v.is_infected + "\t0");
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
