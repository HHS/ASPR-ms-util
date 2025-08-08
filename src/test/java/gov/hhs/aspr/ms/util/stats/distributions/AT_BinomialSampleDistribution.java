package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
public class AT_BinomialSampleDistribution {

	@Test
	@UnitTestConstructor(target = BinomialSampleDistribution.class, args = { RandomGenerator.class,
			BinomialSampleDistributionData.class })
	public void testBinomialSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(595403322160924621L);
		BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
				.setProbabilityOfSuccess(0.5).setTrials(2).build();

		// precondition test: if the random generator is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new BinomialSampleDistribution(null, binomialSampleDistributionData));
		assertEquals(DistributionError.NULL_RNG, contractException.getErrorType());

		// precondition test: if the sample distribution data is null
		contractException = assertThrows(ContractException.class,
				() -> new BinomialSampleDistribution(randomGenerator, null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2234523718975738906L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
					binomialSampleDistributionData);

			// build the inner distribution instance
			BinomialDistribution innerDistribution = new BinomialDistribution(randomGenerator,trials, probabilityOfSuccess);

			assertEquals(innerDistribution.getNumericalMean(), binomialSampleDistribution.getNumericalMean());

		}
	}
	
	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7276702068178363667L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
					binomialSampleDistributionData);

			// build the inner distribution instance
			BinomialDistribution innerDistribution = new BinomialDistribution(randomGenerator,trials, probabilityOfSuccess);

			assertEquals(innerDistribution.getNumericalVariance(), binomialSampleDistribution.getNumericalVariance());

		}
	}
	
	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5460346824710009094L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
					binomialSampleDistributionData);

			// build the inner distribution instance
			BinomialDistribution innerDistribution = new BinomialDistribution(randomGenerator,trials, probabilityOfSuccess);

			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()), binomialSampleDistribution.getNumericalStandardDeviation(),DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6837522400576046656L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData expectedBinomialSampleDistributionData = BinomialSampleDistributionData
					.builder().setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
					expectedBinomialSampleDistributionData);

			// get the distribution data from the distribution
			BinomialSampleDistributionData actualUniformSampleDistributionData = binomialSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedBinomialSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator1 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator2 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData expectedBinomialSampleDistributionData = BinomialSampleDistributionData
					.builder().setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator1,
					expectedBinomialSampleDistributionData);

			// build the inner distribution instance
			BinomialDistribution innerDistribution = new BinomialDistribution(randomGenerator2, trials, probabilityOfSuccess);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), binomialSampleDistribution.sample());
			}
		}
	}
	
	
	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name="getSupportLowerBound", args= {})
	public void testGetSupportLowerBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6127318844779880131L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData expectedBinomialSampleDistributionData = BinomialSampleDistributionData
					.builder().setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
					expectedBinomialSampleDistributionData);

			// build the inner distribution instance
			BinomialDistribution innerDistribution = new BinomialDistribution(randomGenerator, trials, probabilityOfSuccess);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportLowerBound(), binomialSampleDistribution.getSupportLowerBound());
			
		}
	}
	
	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name="isSupportLowerBoundInclusive", args= {})
	public void testIsSupportLowerBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3023706723124922532L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData expectedBinomialSampleDistributionData = BinomialSampleDistributionData
					.builder().setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
					expectedBinomialSampleDistributionData);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(true, binomialSampleDistribution.isSupportLowerBoundInclusive());
			
		}
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name="getSupportUpperBound", args= {})
	public void testGetSupportUpperBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8551209604540925237L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData expectedBinomialSampleDistributionData = BinomialSampleDistributionData
					.builder().setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
					expectedBinomialSampleDistributionData);

			// build the inner distribution instance
			BinomialDistribution innerDistribution = new BinomialDistribution(randomGenerator, trials, probabilityOfSuccess);


			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportUpperBound(), binomialSampleDistribution.getSupportUpperBound());
			
		}
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name="isSupportUpperBoundInclusive", args= {})
	public void testIsSupportUpperBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5336370403876213331L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			// build a sample distribution
			BinomialSampleDistributionData expectedBinomialSampleDistributionData = BinomialSampleDistributionData
					.builder().setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
					expectedBinomialSampleDistributionData);


			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(true, binomialSampleDistribution.isSupportUpperBoundInclusive());
			
		}
	}

	private BinomialSampleDistribution getRandomBinomialSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
				.setProbabilityOfSuccess(randomGenerator.nextDouble()).setTrials(randomGenerator.nextInt(10)).build();
		return new BinomialSampleDistribution(randomGenerator, binomialSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1049090067788854663L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			BinomialSampleDistribution binomialSampleDistribution = getRandomBinomialSampleDistribution(randomGenerator.nextLong());
			assertFalse(binomialSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			BinomialSampleDistribution binomialSampleDistribution = getRandomBinomialSampleDistribution(randomGenerator.nextLong());
			assertFalse(binomialSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			BinomialSampleDistribution binomialSampleDistribution = getRandomBinomialSampleDistribution(randomGenerator.nextLong());
			assertTrue(binomialSampleDistribution.equals(binomialSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			BinomialSampleDistribution binomialSampleDistribution1 = getRandomBinomialSampleDistribution(seed);
			BinomialSampleDistribution binomialSampleDistribution2 = getRandomBinomialSampleDistribution(seed);

			assertTrue(binomialSampleDistribution1.equals(binomialSampleDistribution2));
			assertTrue(binomialSampleDistribution2.equals(binomialSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(binomialSampleDistribution1.equals(binomialSampleDistribution2));
				assertTrue(binomialSampleDistribution2.equals(binomialSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4348829682964300149L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			BinomialSampleDistribution binomialSampleDistribution1 = getRandomBinomialSampleDistribution(seed);
			BinomialSampleDistribution binomialSampleDistribution2 = getRandomBinomialSampleDistribution(seed);
			assertEquals(binomialSampleDistribution1, binomialSampleDistribution2);
			assertEquals(binomialSampleDistribution1.hashCode(), binomialSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			BinomialSampleDistribution binomialSampleDistribution1 = getRandomBinomialSampleDistribution(randomGenerator.nextLong());
			hashCodes.add(binomialSampleDistribution1.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistribution.class, name = "toString", args = {})	
	public void testToString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(133693667452813973L);
		BinomialSampleDistribution binomialSampleDistribution = new BinomialSampleDistribution(randomGenerator,
				BinomialSampleDistributionData.builder().setProbabilityOfSuccess(0.5).setTrials(3).build());
		String expectedValue = "BinomialSampleDistribution [binomialSampleDistributionData=BinomialSampleDistributionData [data=Data [trials=3, probabilityOfSuccess=0.5]]]";
		String actualValue = binomialSampleDistribution.toString();
		assertEquals(expectedValue, actualValue);
	}

}
