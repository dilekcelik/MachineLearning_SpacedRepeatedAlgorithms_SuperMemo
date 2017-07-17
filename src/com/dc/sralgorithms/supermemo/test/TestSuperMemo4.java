package com.dc.sralgorithms.supermemo.test;

import org.junit.Test;

import com.dc.sralgorithms.supermemo.*;

public class TestSuperMemo4 {

	@Test
	public void testIntervalOne() {
		SuperMemo4 sm4 = new SuperMemo4(1.7, 1);
		int newInterval = sm4.getNextInterval(5);
		double newEF = sm4.getNewEFactor();
		System.out.println(newInterval + " " + newEF);
	}

}