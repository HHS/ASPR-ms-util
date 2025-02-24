package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_GammaSampleDistribution {

	@Test
	@UnitTestConstructor(target = GammaSampleDistribution.class, args = { RandomGenerator.class,
			GammaSampleDistributionData.class })
	public void testGammaSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5866656422416894189L);
		GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder().setShape(3.5)
				.setScale(2.1).build();

		// precondition test: if the random generator is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new GammaSampleDistribution(null, gammaSampleDistributionData));
		assertEquals(DistributionError.NULL_RNG, contractException.getErrorType());

		// precondition test: if the sample distribution data is null
		contractException = assertThrows(ContractException.class,
				() -> new GammaSampleDistribution(randomGenerator, null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3350365130616756304L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
					gammaSampleDistributionData);

			// build the inner distribution instance
			GammaDistribution innerDistribution = new GammaDistribution(randomGenerator, scale, shape);

			assertEquals(innerDistribution.getNumericalMean(), gammaSampleDistribution.getNumericalMean());

		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1409609508825716602L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
					gammaSampleDistributionData);

			// build the inner distribution instance
			GammaDistribution innerDistribution = new GammaDistribution(randomGenerator, shape, scale);

			assertEquals(innerDistribution.getNumericalVariance(), gammaSampleDistribution.getNumericalVariance());

		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4977399594539588608L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
					gammaSampleDistributionData);

			// build the inner distribution instance
			GammaDistribution innerDistribution = new GammaDistribution(randomGenerator, shape, scale);

			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()),
					gammaSampleDistribution.getNumericalStandardDeviation(),
					DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(253235231431806424L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData expectedGammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();

			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
					expectedGammaSampleDistributionData);

			// get the distribution data from the distribution
			GammaSampleDistributionData actualUniformSampleDistributionData = gammaSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedGammaSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(9194755651487791680L);
		RandomGenerator randomGenerator1 = RandomGeneratorProvider.getRandomGenerator(9194755651487791680L);
		RandomGenerator randomGenerator2 = RandomGeneratorProvider.getRandomGenerator(9194755651487791680L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData expectedGammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator1,
					expectedGammaSampleDistributionData);

			// build the inner distribution instance
			GammaDistribution innerDistribution = new GammaDistribution(randomGenerator2, shape, scale);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), gammaSampleDistribution.sample());
			}
		}
	}
	
	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name="getSupportLowerBound", args= {})
	public void testGetSupportLowerBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7786647541233128578L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData expectedGammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
					expectedGammaSampleDistributionData);

			// build the inner distribution instance
			GammaDistribution innerDistribution = new GammaDistribution(randomGenerator, shape, scale);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportLowerBound(), gammaSampleDistribution.getSupportLowerBound());
			
		}
	}
	
	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name="isSupportLowerBoundInclusive", args= {})
	public void testIsSupportLowerBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4382595550237559199L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData expectedGammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
					expectedGammaSampleDistributionData);

			// build the inner distribution instance
			GammaDistribution innerDistribution = new GammaDistribution(randomGenerator, shape, scale);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.isSupportLowerBoundInclusive(), gammaSampleDistribution.isSupportLowerBoundInclusive());
			
		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name="getSupportUpperBound", args= {})
	public void testGetSupportUpperBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(801093071877634609L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData expectedGammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
					expectedGammaSampleDistributionData);

			// build the inner distribution instance
			GammaDistribution innerDistribution = new GammaDistribution(randomGenerator, shape, scale);


			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportUpperBound(), gammaSampleDistribution.getSupportUpperBound());
			
		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name="isSupportUpperBoundInclusive", args= {})
	public void testIsSupportUpperBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6969888211147180033L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			GammaSampleDistributionData expectedGammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
					expectedGammaSampleDistributionData);

			// build the inner distribution instance
			GammaDistribution innerDistribution = new GammaDistribution(randomGenerator, shape, scale);


			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.isSupportUpperBoundInclusive(), gammaSampleDistribution.isSupportUpperBoundInclusive());
			
		}
	}
	
	private GammaSampleDistribution getRandomGammaSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
				.setScale(randomGenerator.nextDouble()).setShape(randomGenerator.nextDouble()).build();
		return new GammaSampleDistribution(randomGenerator, gammaSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4441550542972443148L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			GammaSampleDistribution gammaSampleDistribution = getRandomGammaSampleDistribution(randomGenerator.nextLong());
			assertFalse(gammaSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			GammaSampleDistribution gammaSampleDistribution = getRandomGammaSampleDistribution(randomGenerator.nextLong());
			assertFalse(gammaSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			GammaSampleDistribution gammaSampleDistribution = getRandomGammaSampleDistribution(randomGenerator.nextLong());
			assertTrue(gammaSampleDistribution.equals(gammaSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			GammaSampleDistribution gammaSampleDistribution1 = getRandomGammaSampleDistribution(seed);
			GammaSampleDistribution gammaSampleDistribution2 = getRandomGammaSampleDistribution(seed);

			assertTrue(gammaSampleDistribution1.equals(gammaSampleDistribution2));
			assertTrue(gammaSampleDistribution2.equals(gammaSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(gammaSampleDistribution1.equals(gammaSampleDistribution2));
				assertTrue(gammaSampleDistribution2.equals(gammaSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(826293659523842534L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			GammaSampleDistribution gammaSampleDistribution1 = getRandomGammaSampleDistribution(seed);
			GammaSampleDistribution gammaSampleDistribution2 = getRandomGammaSampleDistribution(seed);
			assertEquals(gammaSampleDistribution1, gammaSampleDistribution2);
			assertEquals(gammaSampleDistribution1.hashCode(), gammaSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			GammaSampleDistribution gammaSampleDistribution1 = getRandomGammaSampleDistribution(randomGenerator.nextLong());
			hashCodes.add(gammaSampleDistribution1.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistribution.class, name = "toString", args = {})	
	public void testToString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7762099174516796291L);
		GammaSampleDistribution gammaSampleDistribution = new GammaSampleDistribution(randomGenerator,
				GammaSampleDistributionData.builder().setScale(0.5).setShape(0.7).build());
		String expectedValue = "GammaSampleDistribution [gamaSampleDistributionData=GammaSampleDistributionData [data=Data [shape=0.7, scale=0.5]]]";
		String actualValue = gammaSampleDistribution.toString();
		assertEquals(expectedValue, actualValue);
	}



}
