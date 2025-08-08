package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_TruncatedNormalSampleDistribution {

	@Test
	@UnitTestConstructor(target = TruncatedNormalSampleDistribution.class, args = { RandomGenerator.class,
			TruncatedNormalSampleDistributionData.class })
	public void testTruncatedNormalSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(595403322160924621L);
		TruncatedNormalSampleDistributionData truncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
				.builder().setMean(3.5).setStandardDeviation(2.1).build();

		// precondition test: if the random generator is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new TruncatedNormalSampleDistribution(null, truncatedNormalSampleDistributionData));
		assertEquals(DistributionError.NULL_RNG, contractException.getErrorType());

		// precondition test: if the sample distribution data is null
		contractException = assertThrows(ContractException.class,
				() -> new TruncatedNormalSampleDistribution(randomGenerator, null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2234523718975738906L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData truncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator, truncatedNormalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			assertEquals(innerDistribution.getNumericalMean(), truncatedNormalSampleDistribution.getNumericalMean());

		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7489934247486389049L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData truncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator, truncatedNormalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			assertEquals(innerDistribution.getNumericalVariance(),
					truncatedNormalSampleDistribution.getNumericalVariance());

		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5307616017327734374L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData truncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator, truncatedNormalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()),
					truncatedNormalSampleDistribution.getNumericalStandardDeviation(),
					DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6837522400576046656L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData expectedTruncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();

			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator, expectedTruncatedNormalSampleDistributionData);

			// get the distribution data from the distribution
			TruncatedNormalSampleDistributionData actualUniformSampleDistributionData = truncatedNormalSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedTruncatedNormalSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator1 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator2 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 + 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData expectedTruncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator1, expectedTruncatedNormalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator2, mean, standardDeviation);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), truncatedNormalSampleDistribution.sample());
			}
		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "getSupportLowerBound", args = {})
	public void testGetSupportLowerBound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6701494486630184115L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData expectedTruncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator, expectedTruncatedNormalSampleDistributionData);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(0.0, truncatedNormalSampleDistribution.getSupportLowerBound());

		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "isSupportLowerBoundInclusive", args = {})
	public void testIsSupportLowerBoundInclusive() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8851545581699822960L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData expectedTruncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator, expectedTruncatedNormalSampleDistributionData);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(true, truncatedNormalSampleDistribution.isSupportLowerBoundInclusive());

		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "getSupportUpperBound", args = {})
	public void testGetSupportUpperBound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5446313118983625876L);

		for (int i = 0; i < 30; i++) {

			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData expectedTruncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator, expectedTruncatedNormalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.getSupportUpperBound(),
					truncatedNormalSampleDistribution.getSupportUpperBound());

		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "isSupportUpperBoundInclusive", args = {})
	public void testIsSupportUpperBoundInclusive() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1189913565027575668L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			TruncatedNormalSampleDistributionData expectedTruncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
					randomGenerator, expectedTruncatedNormalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.isSupportUpperBoundInclusive(),
					truncatedNormalSampleDistribution.isSupportUpperBoundInclusive());

		}
	}

	private TruncatedNormalSampleDistribution getRandomTruncatedNormalSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		TruncatedNormalSampleDistributionData truncatedNormalSampleDistributionData = TruncatedNormalSampleDistributionData
				.builder().setMean(randomGenerator.nextDouble()).setStandardDeviation(randomGenerator.nextDouble())
				.build();
		return new TruncatedNormalSampleDistribution(randomGenerator, truncatedNormalSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1049090067788854663L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = getRandomTruncatedNormalSampleDistribution(
					randomGenerator.nextLong());
			assertFalse(truncatedNormalSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = getRandomTruncatedNormalSampleDistribution(
					randomGenerator.nextLong());
			assertFalse(truncatedNormalSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = getRandomTruncatedNormalSampleDistribution(
					randomGenerator.nextLong());
			assertTrue(truncatedNormalSampleDistribution.equals(truncatedNormalSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution1 = getRandomTruncatedNormalSampleDistribution(
					seed);
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution2 = getRandomTruncatedNormalSampleDistribution(
					seed);

			assertTrue(truncatedNormalSampleDistribution1.equals(truncatedNormalSampleDistribution2));
			assertTrue(truncatedNormalSampleDistribution2.equals(truncatedNormalSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(truncatedNormalSampleDistribution1.equals(truncatedNormalSampleDistribution2));
				assertTrue(truncatedNormalSampleDistribution2.equals(truncatedNormalSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4348829682964300149L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution1 = getRandomTruncatedNormalSampleDistribution(
					seed);
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution2 = getRandomTruncatedNormalSampleDistribution(
					seed);
			assertEquals(truncatedNormalSampleDistribution1, truncatedNormalSampleDistribution2);
			assertEquals(truncatedNormalSampleDistribution1.hashCode(), truncatedNormalSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			TruncatedNormalSampleDistribution truncatedNormalSampleDistribution1 = getRandomTruncatedNormalSampleDistribution(
					randomGenerator.nextLong());
			hashCodes.add(truncatedNormalSampleDistribution1.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = TruncatedNormalSampleDistribution.class, name = "toString", args = {})
	public void testToString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(133693667452813973L);
		TruncatedNormalSampleDistribution truncatedNormalSampleDistribution = new TruncatedNormalSampleDistribution(
				randomGenerator,
				TruncatedNormalSampleDistributionData.builder().setMean(0.5).setStandardDeviation(0.1).build());
		String expectedValue = "TtruncatedNormalSampleDistributionData [truncatedNormalSampleDistributionData=TruncatedNormalSampleDistributionData [data=Data [mean=0.5, standardDeviation=0.1]]]";
		String actualValue = truncatedNormalSampleDistribution.toString();
		assertEquals(expectedValue, actualValue);
	}

}
