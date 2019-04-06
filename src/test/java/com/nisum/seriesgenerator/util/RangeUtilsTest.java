package com.nisum.seriesgenerator.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nisum.seriesgenerator.bean.Range;

/**
 * @author Dinesh Kumar Lohia
 * @email lohiadinesh@gmail.com
 */
public class RangeUtilsTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// --------------------------------------------------
	// Exceptions - from a String
	// --------------------------------------------------

	@Test
	public void consolidateRangeFromNull() {
		List<Range> list = RangeUtils.consolidate(null);
		assertNotNull(list);
		assertEquals(0, list.size());
	}

	@Test
	public void consolidateRangeFromOne() {
		Range zcr1 = new Range("10100,10150");
		List<Range> list = RangeUtils.consolidate(Arrays.asList(zcr1));
		assertNotNull(list);
		assertEquals(1, list.size());
		assertArrayEquals(new int[] { 10100, 10150 }, list.get(0).getRangeArray());
	}

	@Test
	public void consolidateRangeNoOverlap() {
		Range zcr1 = new Range("10100,10150");
		Range zcr2 = new Range("20100,20150");
		List<Range> list = RangeUtils.consolidate(Arrays.asList(zcr1, zcr2));
		assertNotNull(list);
		assertEquals(2, list.size());
		assertArrayEquals(new int[] { 10100, 10150 }, list.get(0).getRangeArray());
		assertArrayEquals(new int[] { 20100, 20150 }, list.get(1).getRangeArray());
	}

	@Test
	public void consolidateRangeNoOverlapCorrectOrder() {
		Range zcr1 = new Range("10100,10150");
		Range zcr2 = new Range("20100,20150");
		List<Range> list = RangeUtils.consolidate(Arrays.asList(zcr2, zcr1));
		assertNotNull(list);
		assertEquals(2, list.size());
		assertArrayEquals(new int[] { 10100, 10150 }, list.get(0).getRangeArray());
		assertArrayEquals(new int[] { 20100, 20150 }, list.get(1).getRangeArray());
	}

	@Test
	public void consolidateRangeAdjacentStart() {
		Range zcr1 = new Range("10100,10150");
		Range zcr2 = new Range("10001,10099");
		List<Range> list = RangeUtils.consolidate(Arrays.asList(zcr1, zcr2));
		assertNotNull(list);
		assertEquals(1, list.size());
		assertArrayEquals(new int[] { 10001, 10150 }, list.get(0).getRangeArray());
	}

	@Test
	public void consolidateRangeOverlapStart() {
		Range zcr1 = new Range("10100,10150");
		Range zcr2 = new Range("10001,10101");
		List<Range> list = RangeUtils.consolidate(Arrays.asList(zcr1, zcr2));
		assertNotNull(list);
		assertEquals(1, list.size());
		assertArrayEquals(new int[] { 10001, 10150 }, list.get(0).getRangeArray());
	}

	@Test
	public void consolidateRangeOverlapStartAndEnd() {
		Range zcr1 = new Range("10100,10150");
		Range zcr2 = new Range("10001,20101");
		List<Range> list = RangeUtils.consolidate(Arrays.asList(zcr1, zcr2));
		assertNotNull(list);
		assertEquals(1, list.size());
		assertArrayEquals(new int[] { 10001, 20101 }, list.get(0).getRangeArray());
	}

	// --------------------------------------------------
	// Typical usage - RangeUtils.isExcluded(String)
	// --------------------------------------------------

	@Test
	public void checkIfNullExcludedRange1() {
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertFalse(RangeUtils.isExcluded(null, Arrays.asList(zcr1, zcr2)));
	}

	@Test
	public void checkIfStringExcludedNullRange() {
		assertFalse(RangeUtils.isExcluded("11111", null));
	}

	@Test
	public void checkIfStringExcludedRange1() {
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertTrue(RangeUtils.isExcluded("11111", Arrays.asList(zcr1, zcr2)));
	}

	@Test
	public void checkIfStringExcludedRange2() {
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertTrue(RangeUtils.isExcluded("22222", Arrays.asList(zcr1, zcr2)));
	}

	@Test
	public void checkIfStringNotExcluded() {
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertFalse(RangeUtils.isExcluded("20000", Arrays.asList(zcr1, zcr2)));
	}

	@Test
	public void checkIfBadStringExcluded() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code: 123456"));
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertFalse(RangeUtils.isExcluded("123456", Arrays.asList(zcr1, zcr2)));
	}

	// --------------------------------------------------
	// Typical usage - RangeUtils.isExcluded(int)
	// --------------------------------------------------

	@Test
	public void checkIfIntExcludedNullRange() {
		assertFalse(RangeUtils.isExcluded(11111, null));
	}

	@Test
	public void checkIfIntExcludedRange1() {
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertTrue(RangeUtils.isExcluded(11111, Arrays.asList(zcr1, zcr2)));
	}

	@Test
	public void checkIfIntExcludedRange2() {
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertTrue(RangeUtils.isExcluded(22222, Arrays.asList(zcr1, zcr2)));
	}

	@Test
	public void checkIfIntNotExcluded() {
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertFalse(RangeUtils.isExcluded(20000, Arrays.asList(zcr1, zcr2)));
	}

	@Test
	public void checkIfNegativeIntExcluded() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code: -12345"));
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertFalse(RangeUtils.isExcluded(-12345, Arrays.asList(zcr1, zcr2)));
	}

	@Test
	public void checkIfBadIntExcluded() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code: 123456"));
		Range zcr1 = new Range("10000, 19999");
		Range zcr2 = new Range("20001, 29999");
		assertFalse(RangeUtils.isExcluded(123456, Arrays.asList(zcr1, zcr2)));
	}

	// --------------------------------------------------
	// Typical usage - RangeUtils.isInRange()
	// --------------------------------------------------

	@Test
	public void checkInRangeWithNull() {
		assertFalse(RangeUtils.isInRange(10001, null));
	}

	@Test
	public void checkInRangeLower() {
		assertFalse(RangeUtils.isInRange(10099, new Range("10100,10150")));
	}

	@Test
	public void checkInRangeEqualsStart() {
		assertTrue(RangeUtils.isInRange(10100, new Range("10100,10150")));
	}

	@Test
	public void checkInRangeInBetween() {
		assertTrue(RangeUtils.isInRange(10125, new Range("10100,10150")));
	}

	@Test
	public void checkInRangeEqualsEnd() {
		assertTrue(RangeUtils.isInRange(10150, new Range("10100,10150")));
	}

	@Test
	public void checkInRangeHigher() {
		assertFalse(RangeUtils.isInRange(10151, new Range("10100,10150")));
	}
}
