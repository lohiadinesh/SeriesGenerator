package com.nisum.seriesgenerator.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.nisum.seriesgenerator.bean.Range;
import com.nisum.seriesgenerator.util.RangeUtils;

/**
 * @author Dinesh Kumar Lohia
 * @email lohiadinesh@gmail.com
 */

@Scope("prototype")
@Service("seriesGeneratorService")
public class SeriesGeneratorService {

	public List<Range> exclusions(final String fileLocation) {
		final List<Range> input = RangeUtils.loadRanges(fileLocation);
		System.out.println("\nRaw input ranges:\n" + input);

		final List<Range> output = RangeUtils.consolidate(input);
		System.out.println("\nConsolidated exclusion ranges:\n" + output);
		return output;
	}
}
