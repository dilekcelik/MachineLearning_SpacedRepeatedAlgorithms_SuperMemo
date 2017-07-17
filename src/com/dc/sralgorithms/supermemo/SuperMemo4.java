package com.dc.sralgorithms.supermemo;

/**
 * SM4 spaced repetition algorithm implementation.
 * @author Dilek Celik
 * @see Supermemo SM4 algorithm implementation
 */
public class SuperMemo4 extends SchedulingAlgorithm {
	/**
	 * OI Matrix (optimal interval
	 * columns: E-Factors (1.2..6.9) (step=0.3)
	 * rows: repetitions (1..20)
	 * TODO: this matrix shall be dynamic with entries being modified during the learning process
	 */
	static final double[][] oiMatrix = {
		{ 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50, 2.50 },
		{ 3.00, 3.70, 4.50, 5.20, 6.00, 6.70, 7.50, 8.20, 8.90, 9.70, 10.40, 11.20, 11.90, 12.70, 13.40, 14.20, 14.90, 15.60, 16.40, 17.10 },
		{ 3.60, 5.00, 6.70, 8.60, 10.70, 13.10, 15.70, 18.40, 21.50, 24.70, 28.20, 33.00, 36.00, 39.00, 45.00, 48.00, 54.00, 60.00, 63.00, 69.00 },
		{ 4.30, 6.50, 9.40, 12.90, 17.20, 22.20, 28.20, 36.00, 42.00, 51.00, 63.00, 72.00, 87.00, 99.00, 114.00, 120.00, 150.00, 180.00, 180.00, 210.00 },
		{ 5.20, 8.30, 12.70, 18.40, 25.80, 36.00, 45.00, 60.00, 78.00, 96.00, 120.00, 150.00, 180.00, 210.00, 270.00, 300.00, 365.00, 438.00, 511.00, 584.00 },
		{ 6.20, 10.50, 16.70, 25.40, 36.00, 54.00, 72.00, 99.00, 120.00, 180.00, 210.00, 270.00, 360.00, 438.00, 547.50, 657.00, 803.00, 949.00, 1131.50, 1350.50 },
		{ 7.40, 13.10, 21.80, 33.00, 51.00, 75.00, 108.00, 150.00, 210.00, 270.00, 365.00, 474.50, 620.50, 803.00, 1022.00, 1277.50, 1460.00, 1825.00, 2555.00, 2920.00 },
		{ 8.90, 16.30, 28.00, 45.00, 72.00, 108.00, 150.00, 240.00, 330.00, 438.00, 620.50, 803.00, 1095.00, 1423.50, 1825.00, 2190.00, 2920.00, 3650.00, 4745.00, 5840.00 },
		{ 10.70, 20.20, 36.00, 60.00, 96.00, 150.00, 240.00, 330.00, 474.50, 693.50, 949.00, 1314.00, 1825.00, 2555.00, 3285.00, 4015.00, 5475.00, 6935.00, 8760.00, 10950.00 },
		{ 12.80, 24.90, 45.00, 78.00, 120.00, 210.00, 330.00, 474.50, 730.00, 1022.00, 1460.00, 2190.00, 2920.00, 4015.00, 5110.00, 6935.00, 9125.00, 12045.00 },
		{ 15.40, 30.60, 57.00, 99.00, 180.00, 270.00, 438.00, 693.50, 1022.00, 1460.00, 2190.00, 3285.00, 4380.00, 6205.00, 8395.00, 11680.00 },
		{ 18.50, 39.00, 72.00, 120.00, 210.00, 365.00, 620.50, 949.00, 1460.00, 2190.00, 3285.00, 4745.00, 6935.00, 9490.00, 13505.00 },
		{ 22.10, 45.00, 90.00, 150.00, 300.00, 511.00, 803.00, 1314.00, 2190.00, 3285.00, 4745.00, 6935.00, 10220.00 },
		{ 26.60, 57.00, 111.00, 210.00, 365.00, 657.00, 1095.00, 1825.00, 2920.00, 4380.00, 6935.00, 10220.00 },
		{ 33.00, 69.00, 150.00, 270.00, 474.50, 839.50, 1460.00, 2555.00, 4015.00, 6205.00, 9490.00 },
		{ 39.00, 84.00, 180.00, 330.00, 620.50, 1095.00, 1825.00, 3285.00, 5110.00, 8395.00, 13505.00 },
		{ 45.00, 102.00, 210.00, 438.00, 803.00, 1423.50, 2555.00, 4380.00, 7300.00, 11680.00 },
		{ 54.00, 120.00, 270.00, 511.00, 1022.00, 1825.00, 3285.00, 5840.00, 9490.00 },
		{ 66.00, 150.00, 330.00, 657.00, 1277.50, 2190.00, 4380.00, 7300.00, 12775.00 },
		{ 78.00, 180.00, 401.50, 839.50, 1460.00, 2920.00, 5475.00, 9855.00 },
	};
	
	int repetitionNumber = -1;
	double fraction = 0.5; // fraction is a factor indicating how quickly the OI matrix will be changing

	public SuperMemo4() {
		eFactor = 2.5;
		qualityResponse = 0;
	}
	
	public SuperMemo4(double ef, int qr) {
		eFactor = ef;
		qualityResponse = qr;
	}
	
	public int getNextInterval(int n) {
		repetitionNumber = n;
		if (n==1) return 1;
		else if (n==2) return 6;
		else {
			int column = (int) Math.round(((eFactor - 1.2) / 0.3));
			int row = n-2;
			if (row > oiMatrix.length-1) row = oiMatrix.length - 1;
			if (column > oiMatrix[row].length-1) column = oiMatrix[row].length - 1;
			return (int) Math.round(oiMatrix[row][column]*eFactor);
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
	
	/**
	 * Get next repetition number. Repetitions are reset if quality response is lower than 3.
	 * @return
	 */
	public int getNextRepetitionNumber() {
		if (qualityResponse<3) {
			return 1;
		}
		else {
			return repetitionNumber + 1;
		}
	}
	
	/**
	 * Get new entry for the OI matrix
	 * 
	 * Formula:
	 * OI':=interval+interval*(1-1/EF)/2*(0.25*q-1)
	 * OI'':=(1-fraction)*OI+fraction*OI'
	 * 
	 * OI'' - new value of the OI entry,
	 * OI' - auxiliary value of the OI entry used in calculations,
	 * OI - old value of the OI entry,
	 * interval - interval used before the considered repetition (i.e. the last used interval for the given item),
	 * fraction - any number between 0 and 1 (the greater it is the faster the changes of the OI matrix),
	 * EF - E-Factor of the repeated item,
	 * q - quality of the response in the 0-5 grade scale.
	 * 
	 * @return
	 */
	public double getNewOIMatrixEntry(int previousInterval, double previousOI) {
		double oi1 = previousInterval + previousInterval * (1 - 1.0 / eFactor) / 2 * (0.25 * qualityResponse-1);
		double oi2 = (1 - fraction) * previousOI + fraction * oi1;
		return oi2;
	}	
}