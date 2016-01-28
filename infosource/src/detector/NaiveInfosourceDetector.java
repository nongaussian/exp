package detector;

import common.Graph;
import common.Node;
import common.NodeVisitor;;

public class NaiveInfosourceDetector extends Detector {
	private Graph g = null;
	double maxprob = Double.NEGATIVE_INFINITY;
	
	public NaiveInfosourceDetector (Graph g, double p) {
		super(p);
		this.g = g;
	}
	
	private double loglikelihood = 0;
	
	// visit every node with BFS
	private class TraverseGraph implements NodeVisitor {
		private double[] probs = null;
		public TraverseGraph (double[] probs) {
			this.probs = probs;
		}
		public void visit(Node root) {
			ComputeLikelihood obj = new ComputeLikelihood(probs);
			
			g.set_root(root.id);
			g.traverse(obj);
		}
	}
	
	// compute log-likelihood with the naive algorithm
	private class ComputeLikelihood implements NodeVisitor {
		public double loglikelihood = 0;
		public double[] infection_prob = null;
		
		public ComputeLikelihood (double[] probs) {
			this.infection_prob = probs;
		}
		// increase log-likelihood by log (1 - (1-p)^n_infected_neighbors)
		public void visit(Node n) {
			loglikelihood += infection_prob[n.n_infected_parents(queue)]
		}
	}
	
	public void run () {
		g.traverse(new TraverseGraph(infection_prob));
	}

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("usage: <input data prefix> <# of vertexes> <infection prob>");
			System.exit(1);
		}
		int n_vertex = Integer.parseInt(args[1]);
		double p = Double.parseDouble(args[2]);
		
		Graph g = new Graph (n_vertex);
		g.read_from_file(args[0]);
		NaiveInfosourceDetector obj = new NaiveInfosourceDetector(g, p);
	}

}
