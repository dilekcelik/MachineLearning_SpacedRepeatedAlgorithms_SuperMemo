package com.dc.sralgorithms.supermemo;

abstract class SchedulingAlgorithm {
	double eFactor;
	int qualityResponse;
	double defaultEFactor = 2.5;

	public double getEFactor() {
		return eFactor;
	}
	
	public void setEFactor(double newEFactor) {
		eFactor = newEFactor;
	}
	
	public int getQualityResponse() {
		return qualityResponse;
	}

	/**
	 * Set quality response:
	 * 5 - perfect response
	 * 4 - correct response after a hesitation
	 * 3 - correct response recalled with serious difficulty
	 * 2 - incorrect response; where the correct one seemed easy to recall
	 * 1 - incorrect response; the correct one remembered
	 * 0 - complete blackout.
	 * @param newResponse
	 */
	public void setQualityResponse(int newResponse) {
		qualityResponse = newResponse;
		if (qualityResponse < 3) eFactor = defaultEFactor;
	}
	
	public abstract int getNextInterval(int n);
	public abstract double getNewEFactor();
}