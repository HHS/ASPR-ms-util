package gov.hhs.aspr.ms.util.delaunay;

import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTag;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_GeoDelaunaySolver {

	@Test
	@UnitTestMethod(target = GeoDelaunaySolver.class, name = "solve", args = { Map.class }, tags = { UnitTag.MANUAL })
	public void testSolve() {
		// Should be manually tested. See GeoVisualizerDriver for a manual
		// demonstration that displays points on a 3D globe.
	}
}
