package detector;

import java.util.Comparator;
import java.util.PriorityQueue;

import common.Graph;
import common.Node;
import common.NodeVisitor;
import common.Precal;
import common.SimpleQueue;

public class NaiveInfosourceDetector {
	private Graph g = null;

	public NaiveInfosourceDetector(Graph g, double p) {
		Precal.init(p);
		this.g = g;
	}
	
	public class Entry {
		public int id;
		public double log_prob;
		public Entry(int id, double p) { this.id = id; this.log_prob = p; }
	}

	// visit every node with BFS
	public class TraverseGraph implements NodeVisitor {
		private Graph g = null;
		private int k = 0;
		
		// keep the most k likely info source nodes
		PriorityQueue<Entry> queue = null;

		public TraverseGraph(Graph g, int k) {
			// create a min-heap
			queue = new PriorityQueue<Entry> (k, new Comparator<Entry>() {
				@Override
				public int compare (Entry n1, Entry n2) {
					return Double.compare(n1.log_prob, n2.log_prob);
				}
			});
			
			this.k = k;
			this.g = g;
		}

		// for each node, compute the likelihood under the assumption that the node is the source
		public boolean visit(Node n, int[] depth) {
			ComputeLikelihood visitor = new ComputeLikelihood(n.id);
			g.traverse(n, 
					new SimpleQueue(g.size),
					visitor);
			
			if (queue.size() < k)
				queue.add(new Entry(n.id, visitor.loglikelihood));
			else if (queue.peek().log_prob < visitor.loglikelihood) {
				Entry e = queue.remove();
				e.id = n.id; e.log_prob = visitor.loglikelihood;
				queue.add(e);
			}
			
			return true;
		}
	}

	// compute log-likelihood with the naive algorithm
	private class ComputeLikelihood implements NodeVisitor {
		public double loglikelihood = 0;
		public int root = -1;

		public ComputeLikelihood(int root) {
			this.root = root;
			this.loglikelihood = Double.NEGATIVE_INFINITY;
		}

		// increase log-likelihood by log (1 - (1-p)^n_infected_neighbors)
		public boolean visit(Node n, int[] depth) {
			if (n.id == root) {
				if (n.is_infected == 0) return false;
				loglikelihood = 0;
				return true;
			}
			else {
				int count = 0;
				
				// sum the depth of infected parents
				for (Node neigh: n.neighbors) {
					
					// XXX: here, the last point where I quit
					//  --- if neigh is a parent ----- && -- if it is infected -
					//if (depth[neigh.id] <= depth[n.id] && neigh.is_infected == 1)
					//	count += ;
				}
				
				if (count == 0 && n.is_infected > 0) return false;
				
				loglikelihood += Precal.prob(count, n.is_infected);
				return true;
			}
		}
	}

	public void run(int k) {
		// randomly select the first node to visit
		//Random rand = new Random();
		//int root = rand.nextInt(g.size);
		
		int root = 0;
		TraverseGraph t = new TraverseGraph(g, k);
		g.traverse(g.nodes[root],
				new SimpleQueue(g.size),
				t);
		
		for (int i=t.queue.size()-1; i>=0; i--) {
			Entry e = t.queue.remove();
			System.out.println(e.id + "\t" + e.log_prob);
		}
	}

	public static void main(String[] args) {
		if (args.length != 4) {
			System.err.println("usage: <input data prefix> <# of vertexes> <infection prob> <k most probable>");
			System.exit(1);
		}
		int n_vertex = Integer.parseInt(args[1]);
		double p = Double.parseDouble(args[2]);
		int k = Integer.parseInt(args[3]);

		// generate & load the graph
		Graph g = new Graph(n_vertex);
		g.read_graph_from_file(args[0] + ".dat");
		g.read_state_from_file(args[0] + ".stt");

		// run the naive info source detection
		NaiveInfosourceDetector obj = new NaiveInfosourceDetector(g, p);
		obj.run(k);
	}
}
