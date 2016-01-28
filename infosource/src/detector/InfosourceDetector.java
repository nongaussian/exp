package detector;

import common.Graph;
import common.Node;
import common.NodeVisitor;

public class InfosourceDetector {
	private Graph g = null;
	
	public InfosourceDetector (Graph g) {
		this.g = g;
	}
	
	// compute log-likelihood with our algorithm
	public class ComputeLikelihood implements NodeVisitor {
		public void visit (Node root) {
			
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
