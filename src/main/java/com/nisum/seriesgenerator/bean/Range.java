/**
 * 
 */
package com.nisum.seriesgenerator.bean;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nisum.seriesgenerator.util.RangeUtils;

/**
 * @author Dinesh Kumar Lohia
 * @email lohiadinesh@gmail.com
 *
 */

public class Range implements Comparable<Range> {
	private int start;
	private int end;
	private static Pattern rangePattern = Pattern.compile("\\[?\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*]?");

	/**
	 * A comparator that uses both {@code start} and {@code end} values.
	 */
	public static Comparator<Range> COMPARATOR = Comparator.comparingInt(Range::getStart)
			.thenComparingInt(Range::getEnd);

	/**
	 * Sets the start and end code ranges.
	 * 
	 * @param range A string of exactly two code values in the format of
	 *              [#####,#####]
	 * @throws IllegalArgumentException when the format of the range is incorrect
	 *
	 *                                  Note: the [] are optional and there may be
	 *                                  any amount of whitespace before/after the
	 *                                  comma and within the brackets
	 */
	public Range(String range) {
		final Matcher matcher = rangePattern.matcher(range);
		if (matcher.matches()) {
			setRange(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
		} else {
			throw new IllegalArgumentException("Invalid code range: " + range);
		}
	}

	/**
	 * Sets the start and end code ranges.
	 * 
	 * @param range An int[] of exactly two code values ranging from 0-99999,
	 *              representing the start and end range
	 * @throws IllegalArgumentException when the code value is not within the
	 *                                  supported range
	 */
	public Range(int[] range) {
		if (range.length == 2) {
			setRange(range[0], range[1]);
		} else {
			throw new IllegalArgumentException(
					"Invalid code range - exactly two values must be provided: " + Arrays.toString(range));
		}
	}

	/**
	 * Sets the start and end code ranges.
	 * 
	 * @param range A List&lt;Integer&gt; of exactly two code values ranging from
	 *              0-99999, representing the start and end range
	 * @throws IllegalArgumentException when the code value is not within the
	 *                                  supported range
	 */
	public Range(List<Integer> range) {
		if (range.size() == 2) {
			setRange(range.get(0), range.get(1));
		} else {
			throw new IllegalArgumentException("Invalid code range - exactly two values must be provided: " + range);
		}
	}

	/**
	 * Creates and returns a copy constructor of the object.
	 * 
	 * @param range The Range object to be copied
	 * @return The newly copied object
	 */
	public static Range copy(Range range) {
		return new Range(new int[] { range.getStart(), range.getEnd() });
	}

	/**
	 * Overridden method to compare Range objects.
	 * 
	 * @param range the Range object to be compared
	 * @return a negative integer, zero, or a positive integer as this object is
	 *         less than, equal to, or greater than the specified object
	 */
	@Override
	public int compareTo(Range range) {
		return Comparator.comparing(Range::getStart).thenComparing(Range::getEnd).compare(this, range);
	}

	/**
	 * Retrieves the value for the end of the code range.
	 * 
	 * @return The end value for the code range
	 */
	public int getEnd() {
		return this.end;
	}

	/**
	 * Return the Range as an int[].
	 * 
	 * @return An int[] containing the start and end values for this object
	 */
	public int[] getRangeArray() {
		return new int[] { this.start, this.end };
	}

	/**
	 * Return the Range as a String in the format of {@code [00000,99999]}.
	 * 
	 * @return An int[] containing the start and end values for this object
	 */
	public String getRangeStr() {
		return "[" + String.format("%05d", this.start) + "," + String.format("%05d", this.end) + "]";
	}

	/**
	 * Retrieves the value for the start of the code range.
	 * 
	 * @return The start value for the code range
	 */
	public int getStart() {
		return this.start;
	}

	/**
	 * Checks if the specified Range can be merged with this instance (adjacent or
	 * overlaps either the start or end).
	 * 
	 * @param merge The Range object to check against
	 * @return true if either the start or end of the specific range overlaps the
	 *         current range
	 */
	public boolean isMergeable(Range merge) {
		boolean result = false;
		if (merge != null) {
			result = ((merge.end + 1 == this.start) || (merge.start - 1) == this.end
					|| RangeUtils.isInRange(merge.start, this) || RangeUtils.isInRange(merge.end, this)
					|| RangeUtils.isInRange(this.start, merge));
		}
		return result;
	}

	/**
	 * Merges any overlapped values from the specified range into this instance.
	 * 
	 * @param range The Range object to merge into this instance
	 */
	public void merge(Range range) {
		if (range != null && isMergeable(range)) {
			if (range.start < this.start) {
				this.start = range.start;
			}
			if (range.end > this.end) {
				this.end = range.end;
			}
		}
	}

	/**
	 * Sets the start and end code ranges.
	 * 
	 * @param start The starting code
	 * @param end   The ending code
	 *
	 *              Note: the order of the parameter values is unimportant as the
	 *              minimum and maximum values will be determined internally
	 */
	private void setRange(int start, int end) {
		if (start < 0 || start > 99999) {
			throw new IllegalArgumentException("Invalid start value for code range: " + start);
		} else if (end < 0 || end > 99999) {
			throw new IllegalArgumentException("Invalid end value for code range: " + end);
		}
		if (start < end) {
			this.start = start;
			this.end = end;
		} else if (start > end) {
			this.start = end;
			this.end = start;
		} else {
			this.start = start;
			this.end = start;
		}
	}

	/**
	 * Returns a string representation of this instance.
	 */
	@Override
	public String toString() {
		final String indent = "\n\t";
		return "Range {" + indent + "start: " + String.format("%05d", this.start) + indent + "end: "
				+ String.format("%05d", this.end) + "\n}";
	}

}
