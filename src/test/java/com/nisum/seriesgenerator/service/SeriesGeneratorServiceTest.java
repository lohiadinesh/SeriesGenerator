package com.nisum.seriesgenerator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.nisum.seriesgenerator.bean.Range;

/**
 * @author Dinesh Kumar Lohia
 * @email lohiadinesh@gmail.com
 */

public class SeriesGeneratorServiceTest {

	@Test
	public void instance() {
		assertNotNull(new SeriesGeneratorService());
	}

	@Test(expected = NullPointerException.class)
	public void exclusionsWithNull() {
		new SeriesGeneratorService().exclusions(null);
	}

	@Test
	public void exclusions() {
		final List<Range> input = new SeriesGeneratorService().exclusions("/rawRanges.txt");
		assertNotNull(input);
		assertEquals(2, input.size());

	}
}