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
public class AT_NormalSampleDistribution {

	@Test
	@UnitTestConstructor(target = NormalSampleDistribution.class, args = { RandomGenerator.class,
			NormalSampleDistributionData.class })
	public void testNormalSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(595403322160924621L);
		NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
				.setMean(3.5).setStandardDeviation(2.1).build();

		// precondition test: if the random generator is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new NormalSampleDistribution(null, normalSampleDistributionData));
		assertEquals(DistributionError.NULL_RNG, contractException.getErrorType());

		// precondition test: if the sample distribution data is null
		contractException = assertThrows(ContractException.class,
				() -> new NormalSampleDistribution(randomGenerator, null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2234523718975738906L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean).setStandardDeviation(standardDeviation).build();
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
					normalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			assertEquals(innerDistribution.getNumericalMean(), normalSampleDistribution.getNumericalMean());

		}
	}
	
	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7489934247486389049L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean).setStandardDeviation(standardDeviation).build();
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
					normalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			assertEquals(innerDistribution.getNumericalVariance(), normalSampleDistribution.getNumericalVariance());

		}
	}
	
	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5307616017327734374L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
					.setMean(mean).setStandardDeviation(standardDeviation).build();
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
					normalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()), normalSampleDistribution.getNumericalStandardDeviation(),DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6837522400576046656L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData expectedNormalSampleDistributionData = NormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
					expectedNormalSampleDistributionData);

			// get the distribution data from the distribution
			NormalSampleDistributionData actualUniformSampleDistributionData = normalSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedNormalSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator1 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator2 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData expectedNormalSampleDistributionData = NormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator1,
					expectedNormalSampleDistributionData);

			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator2, mean, standardDeviation);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), normalSampleDistribution.sample());
			}
		}
	}
	
	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name="getSupportLowerBound", args= {})
	public void testGetSupportLowerBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6701494486630184115L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData expectedNormalSampleDistributionData = NormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
					expectedNormalSampleDistributionData);


			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportLowerBound(), normalSampleDistribution.getSupportLowerBound());
			
		}
	}
	
	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name="isSupportLowerBoundInclusive", args= {})
	public void testIsSupportLowerBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8851545581699822960L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData expectedNormalSampleDistributionData = NormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
					expectedNormalSampleDistributionData);


			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.isSupportLowerBoundInclusive(), normalSampleDistribution.isSupportLowerBoundInclusive());
			
		}
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name="getSupportUpperBound", args= {})
	public void testGetSupportUpperBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5446313118983625876L);

		for (int i = 0; i < 30; i++) {
			
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData expectedNormalSampleDistributionData = NormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
					expectedNormalSampleDistributionData);


			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportUpperBound(), normalSampleDistribution.getSupportUpperBound());
			
		}
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name="isSupportUpperBoundInclusive", args= {})
	public void testIsSupportUpperBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1189913565027575668L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble() * 100 - 50;
			double standardDeviation = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			NormalSampleDistributionData expectedNormalSampleDistributionData = NormalSampleDistributionData
					.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
			NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
					expectedNormalSampleDistributionData);


			// build the inner distribution instance
			NormalDistribution innerDistribution = new NormalDistribution(randomGenerator, mean, standardDeviation);


			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.isSupportUpperBoundInclusive(), normalSampleDistribution.isSupportUpperBoundInclusive());
			
		}
	}
	
	
	private NormalSampleDistribution getRandomNormalSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		NormalSampleDistributionData normalSampleDistributionData = NormalSampleDistributionData.builder()
				.setMean(randomGenerator.nextDouble()).setStandardDeviation(randomGenerator.nextDouble()).build();
		return new NormalSampleDistribution(randomGenerator, normalSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5045652760494057537L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			NormalSampleDistribution normalSampleDistribution = getRandomNormalSampleDistribution(randomGenerator.nextLong());
			assertFalse(normalSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			NormalSampleDistribution normalSampleDistribution = getRandomNormalSampleDistribution(randomGenerator.nextLong());
			assertFalse(normalSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			NormalSampleDistribution normalSampleDistribution = getRandomNormalSampleDistribution(randomGenerator.nextLong());
			assertTrue(normalSampleDistribution.equals(normalSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			NormalSampleDistribution normalSampleDistribution1 = getRandomNormalSampleDistribution(seed);
			NormalSampleDistribution normalSampleDistribution2 = getRandomNormalSampleDistribution(seed);

			assertTrue(normalSampleDistribution1.equals(normalSampleDistribution2));
			assertTrue(normalSampleDistribution2.equals(normalSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(normalSampleDistribution1.equals(normalSampleDistribution2));
				assertTrue(normalSampleDistribution2.equals(normalSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1078669929016055965L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			NormalSampleDistribution normalSampleDistribution1 = getRandomNormalSampleDistribution(seed);
			NormalSampleDistribution normalSampleDistribution2 = getRandomNormalSampleDistribution(seed);
			assertEquals(normalSampleDistribution1, normalSampleDistribution2);
			assertEquals(normalSampleDistribution1.hashCode(), normalSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			NormalSampleDistribution normalSampleDistribution1 = getRandomNormalSampleDistribution(randomGenerator.nextLong());
			hashCodes.add(normalSampleDistribution1.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = NormalSampleDistribution.class, name = "toString", args = {})	
	public void testToString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6457035299497152638L);
		NormalSampleDistribution normalSampleDistribution = new NormalSampleDistribution(randomGenerator,
				NormalSampleDistributionData.builder().setMean(0.5).setStandardDeviation(3).build());
		String expectedValue = "NormalSampleDistribution [normalSampleDistributionData=NormalSampleDistributionData [data=Data [mean=0.5, standardDeviation=3.0]]]";
		String actualValue = normalSampleDistribution.toString();
		assertEquals(expectedValue, actualValue);
	}



}
