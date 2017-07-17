package com.dc.sralgorithms.supermemo;

/**
 * SM2 spaced repetition algorithm implementation.
 * @author Dilek Celik
 * @see Supermemo SM2 algorithm implementation
 */
public class SuperMemo2 extends SchedulingAlgorithm {
	
	public SuperMemo2() {
		eFactor = 2.5f;
		qualityResponse = 0;
	}
	
	public SuperMemo2(double ef, int qr) {
		eFactor = ef;
		qualityResponse = qr;
	}

	/**
	 * Get new interval
	 * @param 
	 * @return
	 */
	public int getNextInterval(int n) {
		if (n==1) {
			return 1;
		}
		else if (n==2) {
			return 6;
		}
		else if (n>2) {
			return (int) ((n-1)*eFactor); 
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Get new E-Factor based on the given E-Factor and quality response 
	 * @param ef Current E-Factor
	 * @param qr Quality response
	 * @return
	 */
	public double getNewEFactor() {
		double newEFactor = eFactor +(0.1-(5-qualityResponse)*(0.08+(5-qualityResponse)*0.02));
		if (newEFactor < 1.3) newEFactor = 1.3;
		return newEFactor;
	}
	
}