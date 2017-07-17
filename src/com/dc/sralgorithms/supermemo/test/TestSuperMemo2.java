package com.dc.sralgorithms.supermemo.test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.dc.sralgorithms.supermemo.SuperMemo2;

public class TestSuperMemo2 {

	@Test
	public void testIntervalOne() {
		SuperMemo2 sm2 = new SuperMemo2(2.5f, 4);
		int newInterval = sm2.getNextInterval(1);
		double newEF = sm2.getNewEFactor();
		assertEquals(newInterval, 1);
		assertTrue((2.4 < newEF) && (newEF < 2.6));
	}
	
	@Test
	public void testIntervals() {
		Vector<Integer> grades = new Vector<Integer>();
		grades.add(0);
		grades.add(3);
		grades.add(4);
		grades.add(5);
		grades.add(5);
		grades.add(1);
		grades.add(4);
		grades.add(5);
		grades.add(5);
		grades.add(5);
		grades.add(4);
		grades.add(3);
		grades.add(4);
		grades.add(5);

		Vector<Double> efs = new Vector<Double>();
		efs.add(2.5);

		Vector<Integer> intervals = new Vector<Integer>();
		intervals.add(1);
		
		for (int i=0; i<grades.size(); i++) {
			SuperMemo2 sm2 = new SuperMemo2(efs.get(i), grades.get(i));
			double newEF = sm2.getNewEFactor();
			int newInterval = sm2.getNextInterval(i);
			efs.add(newEF);
			intervals.add(newInterval);
		}

		assertTrue(intervals.get(0) == 1);
		assertTrue(intervals.get(1) == 0);
		assertTrue(intervals.get(2) == 1);
		assertTrue(intervals.get(3) == 6);
		assertTrue(intervals.get(4) == 3);
		assertTrue(intervals.get(5) == 4);
		assertTrue(intervals.get(6) == 7);
		assertTrue(intervals.get(7) == 6);
		assertTrue(intervals.get(8) == 7);
		assertTrue(intervals.get(9) == 9);
		assertTrue(intervals.get(10) == 12);
		assertTrue(intervals.get(11) == 14);
		assertTrue(intervals.get(12) == 16);
		assertTrue(intervals.get(13) == 16);
		assertTrue(intervals.get(14) == 17);
		
		System.out.print("EF : ");
		for (Double f : efs) {
			System.out.print(String.format("%.2f", f) + "\t");
		}
		System.out.println();
		System.out.print("Int: \t");
		for (Integer i : intervals) {
			System.out.print(i + "\t");
		}
		System.out.println();
	}

}