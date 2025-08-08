package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_ExponentialSampleDistribution {

	@Test
	@UnitTestConstructor(target = ExponentialSampleDistribution.class, args = { RandomGenerator.class,
			ExponentialSampleDistributionData.class })
	public void testExponentialSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3997929098958013170L);
		ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData
				.builder().setMean(3.5).build();

		// precondition test: if the random generator is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new ExponentialSampleDistribution(null, exponentialSampleDistributionData));
		assertEquals(DistributionError.NULL_RNG, contractException.getErrorType());

		// precondition test: if the sample distribution data is null
		contractException = assertThrows(ContractException.class,
				() -> new ExponentialSampleDistribution(randomGenerator, null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1768151454887450386L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;
			// build a sample distribution
			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();
			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator, exponentialSampleDistributionData);

			// build the inner distribution instance
			ExponentialDistribution innerDistribution = new ExponentialDistribution(randomGenerator, mean);

			assertEquals(innerDistribution.getNumericalMean(), exponentialSampleDistribution.getNumericalMean());

		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(9113515785809444673L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;
			// build a sample distribution
			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();
			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator, exponentialSampleDistributionData);

			// build the inner distribution instance
			ExponentialDistribution innerDistribution = new ExponentialDistribution(randomGenerator, mean);

			assertEquals(innerDistribution.getNumericalVariance(),
					exponentialSampleDistribution.getNumericalVariance());

		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2153185988910629086L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;
			// build a sample distribution
			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();
			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator, exponentialSampleDistributionData);

			// build the inner distribution instance
			ExponentialDistribution innerDistribution = new ExponentialDistribution(randomGenerator, mean);

			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()),
					exponentialSampleDistribution.getNumericalStandardDeviation(),
					DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7265686488094247263L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ExponentialSampleDistributionData expectedExponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();

			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator, expectedExponentialSampleDistributionData);

			// get the distribution data from the distribution
			ExponentialSampleDistributionData actualUniformSampleDistributionData = exponentialSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedExponentialSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7168258417324022228L);
		RandomGenerator randomGenerator1 = RandomGeneratorProvider.getRandomGenerator(7168258417324022228L);
		RandomGenerator randomGenerator2 = RandomGeneratorProvider.getRandomGenerator(7168258417324022228L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ExponentialSampleDistributionData expectedExponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();
			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator1, expectedExponentialSampleDistributionData);

			// build the inner distribution instance
			ExponentialDistribution innerDistribution = new ExponentialDistribution(randomGenerator2, mean);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), exponentialSampleDistribution.sample());
			}
		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "getSupportLowerBound", args = {})
	public void testGetSupportLowerBound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8542058485322556704L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ExponentialSampleDistributionData expectedExponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();
			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator, expectedExponentialSampleDistributionData);

			// build the inner distribution instance
			ExponentialDistribution innerDistribution = new ExponentialDistribution(randomGenerator, mean);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.getSupportLowerBound(),
					exponentialSampleDistribution.getSupportLowerBound());

		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "isSupportLowerBoundInclusive", args = {})
	public void testIsSupportLowerBoundInclusive() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8786806449032742908L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ExponentialSampleDistributionData expectedExponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();
			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator, expectedExponentialSampleDistributionData);

			// build the inner distribution instance
			ExponentialDistribution innerDistribution = new ExponentialDistribution(randomGenerator, mean);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.isSupportLowerBoundInclusive(),
					exponentialSampleDistribution.isSupportLowerBoundInclusive());

		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "getSupportUpperBound", args = {})
	public void testGetSupportUpperBound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8416474308452890654L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ExponentialSampleDistributionData expectedExponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();
			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator, expectedExponentialSampleDistributionData);

			// build the inner distribution instance
			ExponentialDistribution innerDistribution = new ExponentialDistribution(randomGenerator, mean);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.getSupportUpperBound(),
					exponentialSampleDistribution.getSupportUpperBound());

		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "isSupportUpperBoundInclusive", args = {})
	public void testIsSupportUpperBoundInclusive() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1141816460237954145L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ExponentialSampleDistributionData expectedExponentialSampleDistributionData = ExponentialSampleDistributionData
					.builder().setMean(mean).build();
			ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(
					randomGenerator, expectedExponentialSampleDistributionData);

			// build the inner distribution instance
			ExponentialDistribution innerDistribution = new ExponentialDistribution(randomGenerator, mean);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.isSupportUpperBoundInclusive(),
					exponentialSampleDistribution.isSupportUpperBoundInclusive());

		}
	}
	
	private ExponentialSampleDistribution getRandomExponentialSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
				.setMean(randomGenerator.nextDouble()).build();
		return new ExponentialSampleDistribution(randomGenerator, exponentialSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6425134029816575791L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			ExponentialSampleDistribution exponentialSampleDistribution = getRandomExponentialSampleDistribution(randomGenerator.nextLong());
			assertFalse(exponentialSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			ExponentialSampleDistribution exponentialSampleDistribution = getRandomExponentialSampleDistribution(randomGenerator.nextLong());
			assertFalse(exponentialSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			ExponentialSampleDistribution exponentialSampleDistribution = getRandomExponentialSampleDistribution(randomGenerator.nextLong());
			assertTrue(exponentialSampleDistribution.equals(exponentialSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			ExponentialSampleDistribution exponentialSampleDistribution1 = getRandomExponentialSampleDistribution(seed);
			ExponentialSampleDistribution exponentialSampleDistribution2 = getRandomExponentialSampleDistribution(seed);

			assertTrue(exponentialSampleDistribution1.equals(exponentialSampleDistribution2));
			assertTrue(exponentialSampleDistribution2.equals(exponentialSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(exponentialSampleDistribution1.equals(exponentialSampleDistribution2));
				assertTrue(exponentialSampleDistribution2.equals(exponentialSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6096286110676220069L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			ExponentialSampleDistribution exponentialSampleDistribution1 = getRandomExponentialSampleDistribution(seed);
			ExponentialSampleDistribution exponentialSampleDistribution2 = getRandomExponentialSampleDistribution(seed);
			assertEquals(exponentialSampleDistribution1, exponentialSampleDistribution2);
			assertEquals(exponentialSampleDistribution1.hashCode(), exponentialSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			ExponentialSampleDistribution exponentialSampleDistribution1 = getRandomExponentialSampleDistribution(randomGenerator.nextLong());
			hashCodes.add(exponentialSampleDistribution1.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistribution.class, name = "toString", args = {})	
	public void testToString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4115624221349709461L);
		ExponentialSampleDistribution exponentialSampleDistribution = new ExponentialSampleDistribution(randomGenerator,
				ExponentialSampleDistributionData.builder().setMean(0.5).build());
		String expectedValue = "ExponentialSampleDistribution [exponentialSampleDistributionData=ExponentialSampleDistributionData [data=Data [mean=0.5]]]";
		String actualValue = exponentialSampleDistribution.toString();
		assertEquals(expectedValue, actualValue);
	}


}
