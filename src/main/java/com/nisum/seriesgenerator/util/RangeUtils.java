package com.nisum.seriesgenerator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nisum.seriesgenerator.bean.Range;
import com.nisum.seriesgenerator.service.SeriesGeneratorService;

/**
 * Utility features to load, and perform operations on ranges.
 * 
 * @author Dinesh Kumar Lohia
 * @email lohiadinesh@gmail.com
 *
 */

public class RangeUtils {

	/**
	 * Loads all ranges defined in location
	 * 
	 * @param location text file location
	 * @return list of ranges
	 */
	public static List<Range> loadRanges(final String location) {
		final List<Range> inputRanges = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(SeriesGeneratorService.class.getResourceAsStream(location)))) {
			for (String line; (line = br.readLine()) != null;) {
				inputRanges.add(new Range(line));
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		return inputRanges;
	}

	private static Pattern CodePattern = Pattern.compile("(\\d{5})");

	/**
	 * Consolidates a list of Range objects into the shortest possible grouping of
	 * ranges.
	 * 
	 * @param ranges The list of Range objects to be processed
	 * @return A List of sorted (ascending) Range objects
	 */
	public static List<Range> consolidate(List<Range> ranges) {
		final Set<Range> sortedRanges = new TreeSet<>(Range.COMPARATOR);
		if (ranges != null) {
			ranges.sort(Range.COMPARATOR);
			for (Range zcr : ranges) {
				// create a copy, so the original object is not change by a future merge
				final Range merge = Range.copy(zcr);
				boolean didOverlap = false;
				for (Range existingRange : sortedRanges) {
					if (existingRange.isMergeable(merge)) {
						existingRange.merge(merge);
						didOverlap = true;
						break;
					}
				}
				if (!didOverlap) {
					sortedRanges.add(merge);
				}
			}
		}
		return new ArrayList<>(sortedRanges);
	}

	/**
	 * Checks if the specified code should be excluded (contained) by any of the
	 * known code ranges.
	 * 
	 * @param Code         The code to check
	 * @param excludeRange The List of Range object to use for exclusion
	 * @return true if the code is contained by the exclusion range; otherwise false
	 */
	public static boolean isExcluded(String Code, List<Range> excludeRange) {
		boolean result = false;
		if (Code != null) {
			final Matcher matcher = CodePattern.matcher(Code);
			if (matcher.matches()) {
				result = isExcluded(Integer.valueOf(matcher.group(1)), excludeRange);
			} else {
				throw new IllegalArgumentException("Invalid code: " + Code);
			}
		}
		return result;
	}

	/**
	 * Checks if the specified code should be excluded (contained) by any of the
	 * known code ranges.
	 * 
	 * @param Code         The code to check
	 * @param excludeRange The List of Range object to use for exclusion
	 * @return true if the specified code is contained by the exclusion range;
	 *         otherwise false
	 */
	public static boolean isExcluded(int Code, List<Range> excludeRange) {
		boolean result = false;
		if (Code < 0 || Code > 99999) {
			throw new IllegalArgumentException("Invalid code: " + Code);
		}
		if (excludeRange != null) {
			for (Range range : excludeRange) {
				if (Code >= range.getStart() && Code <= range.getEnd()) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Checks of the specified code is in the specific range. Both start and end
	 * ranges values are considered to be inclusive, so:
	 * 
	 * <pre>
	 * <code>10 in [1,10]  = true</code>
	 * <code>10 in [10,20] = true</code>
	 * <code>10 in [1,20]  = true</code>
	 * <code>10 in [11,20] = false</code>
	 * </pre>
	 * 
	 * @param Code  The code to test against the range
	 * @param range The range to be tested for the code
	 * @return true if the specified code is in the specified range; otherwise false
	 */
	public static boolean isInRange(final int Code, final Range range) {
		boolean result = false;
		if (range != null) {
			result = (range.getStart() <= Code && Code <= range.getEnd());
		}
		return result;
	}
}
