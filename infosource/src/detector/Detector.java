package detector;

public class Detector {
	public final int PRECALC = 64;
	public final int MAX_ITER = 16;
	public double[] infection_prob  = null;
	public Detector (double p) {
		double comp_p = 1. - p;
		infection_prob = new double [PRECALC];
		
		infection_prob[0] = 1.;
		for (int i=1; i<PRECALC; i++) {
			double sum = 1.;
			for (int j=1; j<MAX_ITER && j<i; j++) {
				sum += Math.pow(comp_p, j);
			}
			infection_prob[i] = Math.log(p) + Math.log(sum);
		}
	}
}
