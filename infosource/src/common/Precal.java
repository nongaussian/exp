package common;

public class Precal {
	public static final int PRECALC = 512;
	
	public static double p = 0;
	public static double[] log_prob_pos = null;
	public static double[] log_prob_neg = null;
	public static void init(double p) {
		Precal.p = p;
		
		double complementary_p = 1. - p;
		log_prob_pos = new double [PRECALC];
		log_prob_neg = new double [PRECALC];
		
		log_prob_pos[0] = Double.NEGATIVE_INFINITY;
		log_prob_neg[0] = 0;
		for (int i=1; i<PRECALC; i++) {
			double sum = 1.;
			for (int j=1; j<PRECALC && j<i; j++) {
				sum += Math.pow(complementary_p, j);
			}
			log_prob_pos[i] = Math.log(p) + Math.log(sum);
			log_prob_neg[i] = Math.log(complementary_p) * (double)i;
		}
	}
	public static double prob(int n_parent, int infected) {
		if (infected == 0)
			return (n_parent >= PRECALC) ? log_prob_neg[PRECALC-1] : log_prob_neg[n_parent];
		else
			return (n_parent >= PRECALC) ? log_prob_pos[PRECALC-1] : log_prob_pos[n_parent];
	}
}
