package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_BetaSampleDistribution {

	@Test
	@UnitTestConstructor(target = BetaSampleDistribution.class, args = { RandomGenerator.class,
			BetaSampleDistributionData.class })
	public void testBetaSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(595403322160924621L);
		BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(3.5)
				.setBeta(2.1).build();

		// precondition test: if the random generator is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new BetaSampleDistribution(null, betaSampleDistributionData));
		assertEquals(DistributionError.NULL_RNG, contractException.getErrorType());

		// precondition test: if the sample distribution data is null
		contractException = assertThrows(ContractException.class,
				() -> new BetaSampleDistribution(randomGenerator, null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2234523718975738906L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)
					.setBeta(beta).build();
			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
					betaSampleDistributionData);

			// build the inner distribution instance
			BetaDistribution innerDistribution = new BetaDistribution(randomGenerator, alpha, beta);

			assertEquals(innerDistribution.getNumericalMean(), betaSampleDistribution.getNumericalMean());

		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6216799153130783886L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)
					.setBeta(beta).build();
			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
					betaSampleDistributionData);

			// build the inner distribution instance
			BetaDistribution innerDistribution = new BetaDistribution(randomGenerator, alpha, beta);

			assertEquals(innerDistribution.getNumericalVariance(), betaSampleDistribution.getNumericalVariance());

		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7540386031449497315L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)
					.setBeta(beta).build();
			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
					betaSampleDistributionData);

			// build the inner distribution instance
			BetaDistribution innerDistribution = new BetaDistribution(randomGenerator, alpha, beta);

			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()),
					betaSampleDistribution.getNumericalStandardDeviation(),
					DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6837522400576046656L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData expectedBetaSampleDistributionData = BetaSampleDistributionData.builder()
					.setAlpha(alpha).setBeta(beta).build();

			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
					expectedBetaSampleDistributionData);

			// get the distribution data from the distribution
			BetaSampleDistributionData actualUniformSampleDistributionData = betaSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedBetaSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator1 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator2 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData expectedBetaSampleDistributionData = BetaSampleDistributionData.builder()
					.setAlpha(alpha).setBeta(beta).build();
			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator1,
					expectedBetaSampleDistributionData);

			// build the inner distribution instance
			BetaDistribution innerDistribution = new BetaDistribution(randomGenerator2, alpha, beta);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), betaSampleDistribution.sample());
			}
		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "getSupportLowerBound", args = {})
	public void testGetSupportLowerBound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2580098363417089706L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData expectedBetaSampleDistributionData = BetaSampleDistributionData.builder()
					.setAlpha(alpha).setBeta(beta).build();
			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
					expectedBetaSampleDistributionData);

			// build the inner distribution instance
			BetaDistribution innerDistribution = new BetaDistribution(randomGenerator, alpha, beta);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.getSupportLowerBound(), betaSampleDistribution.getSupportLowerBound());

		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "isSupportLowerBoundInclusive", args = {})
	public void testIsSupportLowerBoundInclusive() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7072377209897756737L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData expectedBetaSampleDistributionData = BetaSampleDistributionData.builder()
					.setAlpha(alpha).setBeta(beta).build();
			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
					expectedBetaSampleDistributionData);

			// build the inner distribution instance
			BetaDistribution innerDistribution = new BetaDistribution(randomGenerator, alpha, beta);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.isSupportLowerBoundInclusive(),
					betaSampleDistribution.isSupportLowerBoundInclusive());

		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "getSupportUpperBound", args = {})
	public void testGetSupportUpperBound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1457533770663015246L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData expectedBetaSampleDistributionData = BetaSampleDistributionData.builder()
					.setAlpha(alpha).setBeta(beta).build();
			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
					expectedBetaSampleDistributionData);

			// build the inner distribution instance
			BetaDistribution innerDistribution = new BetaDistribution(randomGenerator, alpha, beta);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.getSupportUpperBound(), betaSampleDistribution.getSupportUpperBound());

		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "isSupportUpperBoundInclusive", args = {})
	public void testIsSupportUpperBoundInclusive() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6916049730590656837L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			// build a sample distribution
			BetaSampleDistributionData expectedBetaSampleDistributionData = BetaSampleDistributionData.builder()
					.setAlpha(alpha).setBeta(beta).build();
			BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
					expectedBetaSampleDistributionData);

			// build the inner distribution instance
			BetaDistribution innerDistribution = new BetaDistribution(randomGenerator, alpha, beta);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.isSupportUpperBoundInclusive(),
					betaSampleDistribution.isSupportUpperBoundInclusive());

		}
	}

	private BetaSampleDistribution getRandomBetaSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder()
				.setAlpha(randomGenerator.nextDouble()).setBeta(randomGenerator.nextDouble()).build();
		return new BetaSampleDistribution(randomGenerator, betaSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(838686040022493771L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			BetaSampleDistribution betaSampleDistribution = getRandomBetaSampleDistribution(randomGenerator.nextLong());
			assertFalse(betaSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			BetaSampleDistribution betaSampleDistribution = getRandomBetaSampleDistribution(randomGenerator.nextLong());
			assertFalse(betaSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			BetaSampleDistribution betaSampleDistribution = getRandomBetaSampleDistribution(randomGenerator.nextLong());
			assertTrue(betaSampleDistribution.equals(betaSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			BetaSampleDistribution betaSampleDistribution1 = getRandomBetaSampleDistribution(seed);
			BetaSampleDistribution betaSampleDistribution2 = getRandomBetaSampleDistribution(seed);

			assertTrue(betaSampleDistribution1.equals(betaSampleDistribution2));
			assertTrue(betaSampleDistribution2.equals(betaSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(betaSampleDistribution1.equals(betaSampleDistribution2));
				assertTrue(betaSampleDistribution2.equals(betaSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4120071124696644408L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			BetaSampleDistribution betaSampleDistribution1 = getRandomBetaSampleDistribution(seed);
			BetaSampleDistribution betaSampleDistribution2 = getRandomBetaSampleDistribution(seed);
			assertEquals(betaSampleDistribution1, betaSampleDistribution2);
			assertEquals(betaSampleDistribution1.hashCode(), betaSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			BetaSampleDistribution betaSampleDistribution = getRandomBetaSampleDistribution(randomGenerator.nextLong());
			hashCodes.add(betaSampleDistribution.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistribution.class, name = "toString", args = {})	
	public void testToString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6251919398163586559L);
		BetaSampleDistribution betaSampleDistribution = new BetaSampleDistribution(randomGenerator,
		BetaSampleDistributionData.builder().setAlpha(2.4).setBeta(3.5).build());
		String expectedValue = "BetaSampleDistribution [betaSampleDistributionData=BetaSampleDistributionData [data=Data [alpha=2.4, beta=3.5]]]";
		String actualValue = betaSampleDistribution.toString();
		assertEquals(expectedValue, actualValue);
	}

}
