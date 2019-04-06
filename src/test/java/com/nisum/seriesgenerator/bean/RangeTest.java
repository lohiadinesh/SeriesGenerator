package com.nisum.seriesgenerator.bean;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Dinesh Kumar Lohia
 * @email lohiadinesh@gmail.com
 *
 */

public class RangeTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// --------------------------------------------------
	// Exceptions - Range(String)
	// --------------------------------------------------

	@Test(expected = NullPointerException.class)
	public void createRangeFromStringWithNull() {
		new Range((String) null);
	}

	@Test
	public void createRangeFromStringEmpty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: "));
		new Range("");
	}

	@Test
	public void createRangeFromStringWithOneNumber() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: 10000"));
		new Range("10000");
	}

	@Test
	public void createRangeFromStringWithThreeNumbers() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: 10000, 20000, 30000"));
		new Range("10000, 20000, 30000");
	}

	@Test
	public void createRangeFromStringWithBadFormat1() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: 10000 10000"));
		new Range("10000 10000");
	}

	@Test
	public void createRangeFromStringWithBadFormat2() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: 10000 - 10000"));
		new Range("10000 - 10000");
	}

	@Test
	public void createRangeFromStringWithNegativeStart() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: -10000, 22222"));
		new Range("-10000, 22222");
	}

	@Test
	public void createRangeFromStringWithNegativeEnd() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: 10000, -22222"));
		new Range("10000, -22222");
	}

	@Test
	public void createRangeFromStringWithStartTooLarge() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: 123456, 10000"));
		new Range("123456, 10000");
	}

	@Test
	public void createRangeFromStringWithEndTooLarge() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Invalid code range: 10000, 567890"));
		new Range("10000, 567890");
	}

	// --------------------------------------------------
	// Typical usage - Range(String)
	// --------------------------------------------------

	@Test
	public void createRangeFromStringNoBrackets() {
		Range zcr = new Range("10001,22222");
		assertEquals("[10001,22222]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromStringNoBracketsAndWhitespace() {
		Range zcr = new Range("\t 10002\t ,\t 22222\t ");
		assertEquals("[10002,22222]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromStringWithOpeningBracket() {
		Range zcr = new Range("[10003,22222");
		assertEquals("[10003,22222]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromStringWithClosingBracket() {
		Range zcr = new Range("10004,22222]");
		assertEquals("[10004,22222]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromStringWithBothBrackets() {
		Range zcr = new Range("[10005,22222]");
		assertEquals("[10005,22222]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromStringWithBothBracketsAndWhitespace() {
		Range zcr = new Range("[ \t10006 \t, \t22222 \t]");
		assertEquals("[10006,22222]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromStringSingleValue() {
		Range zcr = new Range("10007,10007]");
		assertEquals("[10007,10007]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromStringFullRange() {
		Range zcr = new Range("0,99999]");
		assertEquals("[00000,99999]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromStringReversed() {
		Range zcr = new Range("33333,22222]");
		assertEquals("[22222,33333]", zcr.getRangeStr());
	}

	// --------------------------------------------------
	// Exceptions - Range(int[])
	// --------------------------------------------------

	@Test(expected = NullPointerException.class)
	public void createRangeFromArrayNull() {
		new Range((int[]) null);
	}

	@Test
	public void createRangeFromArrayEmpty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid code range - exactly two values must be provided: []");
		new Range(new int[] {});
	}

	@Test
	public void createRangeFromArrayOneNumber() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid code range - exactly two values must be provided: [10000]");
		new Range(new int[] { 10000 });
	}

	@Test
	public void createRangeFromArrayThreeNumbers() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid code range - exactly two values must be provided: [10000, 20000, 30000]");
		new Range(new int[] { 10000, 20000, 30000 });
	}

	@Test
	public void createRangeFromArrayWithNegativeStart() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid start value for code range: -10000");
		new Range(new int[] { -10000, 22222 });
	}

	@Test
	public void createRangeFromArrayWithNegativeEnd() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid end value for code range: -22222");
		new Range(new int[] { 10001, -22222 });
	}

	@Test
	public void createRangeFromArrayWithStartTooLarge() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid start value for code range: 123456");
		new Range(new int[] { 123456, 10000 });
	}

	@Test
	public void createRangeFromArrayWithEndTooLarge() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid end value for code range: 567890");
		new Range(new int[] { 10000, 567890 });
	}

	// --------------------------------------------------
	// Typical usage - Range(int[])
	// --------------------------------------------------

	@Test
	public void createRangeFromArray() {
		Range zcr = new Range(new int[] { 10001, 22222 });
		assertEquals("[10001,22222]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromArraySingleValue() {
		Range zcr = new Range(new int[] { 10002, 10002 });
		assertEquals("[10002,10002]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromArrayFullRange() {
		Range zcr = new Range(new int[] { 0, 99999 });
		assertEquals("[00000,99999]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromArrayReversed() {
		Range zcr = new Range(new int[] { 33333, 22222 });
		assertEquals("[22222,33333]", zcr.getRangeStr());
	}

	// --------------------------------------------------
	// Exceptions - Range(List<Integer>)
	// --------------------------------------------------

	@Test(expected = NullPointerException.class)
	public void createRangeFromListNull() {
		new Range((ArrayList<Integer>) null);
	}

	@Test
	public void createRangeFromListEmpty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid code range - exactly two values must be provided: []");
		new Range(Collections.emptyList());
	}

	@Test
	public void createRangeFromListOneNumber() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid code range - exactly two values must be provided: [10000]");
		new Range(Collections.singletonList(10000));
	}

	@Test
	public void createRangeFromListThreeNumbers() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid code range - exactly two values must be provided: [10000, 20000, 30000]");
		new Range(Arrays.asList(10000, 20000, 30000));
	}

	@Test
	public void createRangeFromListWithNegativeStart() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid start value for code range: -10000");
		new Range(Arrays.asList(-10000, 22222));
	}

	@Test
	public void createRangeFromListWithNegativeEnd() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid end value for code range: -22222");
		new Range(Arrays.asList(10000, -22222));
	}

	@Test
	public void createRangeFromListWithStartTooLarge() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid start value for code range: 123456");
		new Range(Arrays.asList(123456, 10000));
	}

	@Test
	public void createRangeFromListWithEndTooLarge() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid end value for code range: 567890");
		new Range(Arrays.asList(10000, 567890));
	}

	// --------------------------------------------------
	// Typical usage - Range(List<Integer>)
	// --------------------------------------------------

	@Test
	public void createRangeFromList() {
		Range zcr = new Range(Arrays.asList(10001, 22222));
		assertEquals("[10001,22222]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromListSingleValue() {
		Range zcr = new Range(Arrays.asList(10002, 10002));
		assertEquals("[10002,10002]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromListFullRange() {
		Range zcr = new Range(Arrays.asList(0, 99999));
		assertEquals("[00000,99999]", zcr.getRangeStr());
	}

	@Test
	public void createRangeFromListReversed() {
		Range zcr = new Range(Arrays.asList(33333, 22222));
		assertEquals("[22222,33333]", zcr.getRangeStr());
	}

	// --------------------------------------------------
	// Typical usage - copy()
	// --------------------------------------------------

	@Test
	public void createCopy() {
		Range zcr = new Range("12345, 67890");
		Range clone = Range.copy(zcr);
		assertEquals(12345, zcr.getStart());
		assertEquals(12345, clone.getStart());
		assertEquals(67890, zcr.getEnd());
		assertEquals(67890, clone.getEnd());
		assertNotEquals(zcr, clone);
	}

	// --------------------------------------------------
	// Typical usage - compareTo()
	// --------------------------------------------------

	@Test
	public void compareToEqual() {
		Range zcr1 = new Range("11111, 22222");
		Range zcr1a = new Range("11111, 22222");
		assertEquals(0, zcr1.compareTo(zcr1a));
	}

	@Test
	public void compareToLessThanStart() {
		Range zcr1 = new Range("11111, 12222");
		Range zcr2 = new Range("22222, 23333");
		assertThat(zcr1.compareTo(zcr2), is(lessThan(0)));
	}

	@Test
	public void compareToLessThanEnd() {
		Range zcr1 = new Range("11111, 12222");
		Range zcr2 = new Range("11111, 13333");
		assertThat(zcr1.compareTo(zcr2), is(lessThan(0)));
	}

	@Test
	public void compareToGreaterThanStart() {
		Range zcr1 = new Range("11111, 12222");
		Range zcr2 = new Range("22222, 23333");
		assertThat(zcr2.compareTo(zcr1), is(greaterThan(0)));
	}

	@Test
	public void compareToGreaterThanEnd() {
		Range zcr1 = new Range("11111, 12222");
		Range zcr2 = new Range("11111, 13333");
		assertThat(zcr2.compareTo(zcr1), is(greaterThan(0)));
	}

	// --------------------------------------------------
	// Typical usage - getStart()
	// --------------------------------------------------

	@Test
	public void getStartValue() {
		Range zcr = new Range("12345, 67890");
		assertEquals(12345, zcr.getStart());
	}

	// --------------------------------------------------
	// Typical usage - getEnd()
	// --------------------------------------------------

	@Test
	public void getEndValue() {
		Range zcr = new Range("12345, 67890");
		assertEquals(67890, zcr.getEnd());
	}

	// --------------------------------------------------
	// Typical usage - getRangeArray()
	// --------------------------------------------------

	@Test
	public void getRangeArray() {
		Range zcr = new Range("12345, 67890");
		assertArrayEquals(new int[] { 12345, 67890 }, zcr.getRangeArray());
	}

	// --------------------------------------------------
	// Typical usage - getRangeStr()
	// --------------------------------------------------

	@Test
	public void getRangeStr() {
		Range zcr = new Range("12345, 67890");
		assertEquals("[12345,67890]", zcr.getRangeStr());
	}

	@Test
	public void getRangeStrWithPaddedZeroes() {
		Range zcr = new Range("1, 500");
		assertEquals("[00001,00500]", zcr.getRangeStr());
	}

	// --------------------------------------------------
	// Typical usage - isMergeable()
	// --------------------------------------------------

	@Test
	public void checkMergeWithNull() {
		Range zcr = new Range("10100, 10150");
		assertFalse(zcr.isMergeable(null));
	}

	// mergeStart, mergeEnd, rangeStart, rangeEnd
	@Test
	public void checkMerge_mergeEndEqualRangeStartMinus2() {
		Range zcr = new Range("10100, 10150");
		assertFalse(zcr.isMergeable(new Range("10098, 10098")));
	}

	@Test
	public void checkMerge_mergeEndEqualRangeStartMinus1() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10098, 10099")));
	}

	@Test
	public void checkMerge_mergeEndEqualRangeStart() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10098, 10100")));
	}

	// mergeStart, rangeStart, mergeEnd, rangeEnd
	@Test
	public void checkMerge_mergeStartEqualRangeStartMinus2() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10098, 10101")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeStartMinus1() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10099, 10150")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeStart() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10100, 10150")));
	}

	// mergeStart, rangeStart, rangeEnd, mergeEnd
	@Test
	public void checkMerge_mergeStartEqualRangeStartMinus2_mergeEndEqualRangeEnd() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10098, 10150")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeStartMinus2_mergeEndEqualRangeEndPlus1() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10098, 10151")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeStartMinus1_mergeEndEqualRangeEnd() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10099, 10150")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeStartMinus1_mergeEndEqualRangeEndPlus1() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10099, 10151")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeStart_mergeEndEqualRangeEndPlus1() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10100, 10151")));
	}

	// rangeStart, mergeStart, mergeEnd, rangeEnd
	@Test
	public void checkMerge_mergeStartEqualRangeStart_mergeEndLessThanRangeEnd() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10100, 10101")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeStartPlus1_mergeEndLessThanRangeEnd() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10101, 10149")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeStartPlus2_mergeEndEqualRangeEnd() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10101, 10149")));
	}

	// rangeStart, mergeStart, rangeEnd, mergeEnd
	@Test
	public void checkMerge_mergeEndEqualRangeEnd() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10150, 10150")));
	}

	@Test
	public void checkMerge_mergeEndEqualRangeEndPlus1() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10150, 10151")));
	}

	@Test
	public void checkMerge_mergeEndEqualRangeEndPlus2() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10150, 10152")));
	}

	// rangeStart, mergeStart, rangeEnd, mergeEnd
	@Test
	public void checkMerge_mergeStartEqualRangeEnd() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10150, 10151")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeEndPlus1() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10151, 10152")));
	}

	@Test
	public void checkMerge_mergeStartEqualRangeEndPlus2() {
		Range zcr = new Range("10100, 10150");
		assertFalse(zcr.isMergeable(new Range("10152, 10153")));
	}

	@Test
	public void checkMerge_outerRangeWithInner() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10101, 10102")));
	}

	@Test
	public void checkMerge_innerRangeWithOuter() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10001, 20101")));
	}

	@Test
	public void checkMerge_mergeStartInRange() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10101, 10125")));
	}

	@Test
	public void checkMerge_mergeEndInRange() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10001, 10125")));
	}

	@Test
	public void checkMerge_rangeStartInMerge() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10001, 10125")));
	}

	@Test
	public void checkMerge_rangeEndInMerge() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10125, 10500")));
	}

	@Test
	public void checkMerge_mergeStartAdjacentRange() {
		Range zcr = new Range("10100, 10150");
		assertTrue(zcr.isMergeable(new Range("10098, 10099")));
	}

	// --------------------------------------------------
	// Typical usage - merge()
	// --------------------------------------------------

	@Test
	public void mergeNull() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(null);
		assertEquals("[10100,10150]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartBeforeRangeStart_mergeEndEqualRangeStartMinus2() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10095, 10098"));
		assertEquals("[10100,10150]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartBeforeRangeStart_mergeEndEqualRangeStartMinus1() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10095, 10099"));
		assertEquals("[10095,10150]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartBeforeRangeStart_mergeEndEqualRangeStart() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10095, 10100"));
		assertEquals("[10095,10150]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartBeforeRangeStart_mergeEndAfterRangeStart() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10095, 10101"));
		assertEquals("[10095,10150]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartBeforeRangeStart_mergeEndEqualRangeEnd() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10095, 10150"));
		assertEquals("[10095,10150]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartBeforeRangeStart_mergeEndAfterRangeEnd() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10095, 10151"));
		assertEquals("[10095,10151]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartEqualRangeStart_mergeEndEqualRangeStartMinus1() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10100, 10149"));
		assertEquals("[10100,10150]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartEqualRangeStart_mergeEndEqualRangeStart() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10100, 10150"));
		assertEquals("[10100,10150]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartEqualRangeStart_mergeEndEqualRangeStartPlus1() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10100, 10151"));
		assertEquals("[10100,10151]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartEqualRangeEndMinus1_mergeEndEqualRangeEndPlus25() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10149, 10175"));
		assertEquals("[10100,10175]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartEqualRangeEnd_mergeEndEqualRangeEndPlus25() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10150, 10175"));
		assertEquals("[10100,10175]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartEqualRangeEndPlus1_mergeEndEqualRangeEndPlus25() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10151, 10175"));
		assertEquals("[10100,10175]", zcr.getRangeStr());
	}

	@Test
	public void mergeStartEqualRangeEndPlus2_mergeEndEqualRangeEndPlus25() {
		Range zcr = new Range("10100, 10150");
		zcr.merge(new Range("10152, 10175"));
		assertEquals("[10100,10150]", zcr.getRangeStr());
	}

	// --------------------------------------------------
	// Typical usage - toString()
	// --------------------------------------------------

	@Test
	public void getToString() {
		Range zcr = new Range("12345, 67890");
		assertEquals("Range {\n\tstart: 12345\n\tend: 67890\n}", zcr.toString());
	}

	@Test
	public void getToStringWithPaddedZeroes() {
		Range zcr = new Range("1, 500");
		assertEquals("Range {\n\tstart: 00001\n\tend: 00500\n}", zcr.toString());
	}

}