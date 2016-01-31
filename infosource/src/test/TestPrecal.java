package test;

import common.Precal;

public class TestPrecal {

	public static void main(String[] args) {
		Precal.init(0.1);
		for (int i=0; i<Precal.PRECALC; i++) {
			System.out.println(i + "\t" + Precal.log_prob_pos[i] + "\t" + Precal.log_prob_neg[i]);
		}
	}

}
