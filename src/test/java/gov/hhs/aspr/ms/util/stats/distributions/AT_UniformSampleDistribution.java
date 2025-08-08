package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_UniformSampleDistribution {

	@Test
	@UnitTestConstructor(target = UniformSampleDistribution.class, args = { RandomGenerator.class,
			UniformSampleDistributionData.class })
	public void testUniformSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4087922987871655931L);
		UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
				.setLower(4).setUpper(10).build();

		// precondition test: if the random generator is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new UniformSampleDistribution(null, uniformSampleDistributionData));
		assertEquals(DistributionError.NULL_RNG, contractException.getErrorType());

		// precondition test: if the sample distribution data is null
		contractException = assertThrows(ContractException.class,
				() -> new UniformSampleDistribution(randomGenerator, null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8084481482271342980L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
					uniformSampleDistributionData);

			// build the inner distribution instance
			UniformRealDistribution innerDistribution = new UniformRealDistribution(randomGenerator, low, high);

			assertEquals(innerDistribution.getNumericalMean(), uniformSampleDistribution.getNumericalMean());

		}
	}
	
	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2110109272457108070L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
					uniformSampleDistributionData);

			// build the inner distribution instance
			UniformRealDistribution innerDistribution = new UniformRealDistribution(randomGenerator, low, high);

			assertEquals(innerDistribution.getNumericalVariance(), uniformSampleDistribution.getNumericalVariance());

		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7235237153656466250L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
					uniformSampleDistributionData);

			// build the inner distribution instance
			UniformRealDistribution innerDistribution = new UniformRealDistribution(randomGenerator, low, high);

			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()), uniformSampleDistribution.getNumericalStandardDeviation(),DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}


	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1253749039745699002L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData expectedUniformSampleDistributionData = UniformSampleDistributionData
					.builder().setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
					expectedUniformSampleDistributionData);

			// get the distribution data from the distribution
			UniformSampleDistributionData actualUniformSampleDistributionData = uniformSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedUniformSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8426274651253725090L);
		RandomGenerator randomGenerator1 = RandomGeneratorProvider.getRandomGenerator(8426274651253725090L);
		RandomGenerator randomGenerator2 = RandomGeneratorProvider.getRandomGenerator(8426274651253725090L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData expectedUniformSampleDistributionData = UniformSampleDistributionData
					.builder().setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator1,
					expectedUniformSampleDistributionData);

			// build the inner distribution instance
			UniformRealDistribution innerDistribution = new UniformRealDistribution(randomGenerator2, low, high);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), uniformSampleDistribution.sample());
			}
		}
	}
	
	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name="getSupportLowerBound", args= {})
	public void testGetSupportLowerBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1455838252882552627L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData expectedUniformSampleDistributionData = UniformSampleDistributionData
					.builder().setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
					expectedUniformSampleDistributionData);

			// build the inner distribution instance
			UniformRealDistribution innerDistribution = new UniformRealDistribution(randomGenerator, low, high);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportLowerBound(), uniformSampleDistribution.getSupportLowerBound());
			
		}
	}
	
	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name="isSupportLowerBoundInclusive", args= {})
	public void testIsSupportLowerBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1824821263956009723L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData expectedUniformSampleDistributionData = UniformSampleDistributionData
					.builder().setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
					expectedUniformSampleDistributionData);

			// build the inner distribution instance
			UniformRealDistribution innerDistribution = new UniformRealDistribution(randomGenerator, low, high);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.isSupportLowerBoundInclusive(), uniformSampleDistribution.isSupportLowerBoundInclusive());
			
		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name="getSupportUpperBound", args= {})
	public void testGetSupportUpperBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1683959810845295912L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData expectedUniformSampleDistributionData = UniformSampleDistributionData
					.builder().setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
					expectedUniformSampleDistributionData);

			// build the inner distribution instance
			UniformRealDistribution innerDistribution = new UniformRealDistribution(randomGenerator, low, high);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportUpperBound(), uniformSampleDistribution.getSupportUpperBound());
			
		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name="isSupportUpperBoundInclusive", args= {})
	public void testIsSupportUpperBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7578008229570285953L);

		for (int i = 0; i < 30; i++) {
			double low = randomGenerator.nextDouble() * 100 - 50;
			double high = randomGenerator.nextDouble() * 100 + low;

			// build a sample distribution
			UniformSampleDistributionData expectedUniformSampleDistributionData = UniformSampleDistributionData
					.builder().setLower(low).setUpper(high).build();
			UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
					expectedUniformSampleDistributionData);

			// build the inner distribution instance
			UniformRealDistribution innerDistribution = new UniformRealDistribution(randomGenerator, low, high);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.isSupportUpperBoundInclusive(), uniformSampleDistribution.isSupportUpperBoundInclusive());
			
		}
	}
	
	private UniformSampleDistribution getRandomUniformSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		double lower = randomGenerator.nextDouble();
		double upper = lower + randomGenerator.nextDouble();		
		UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
				.setLower(lower).setUpper(upper).build();
		return new UniformSampleDistribution(randomGenerator, uniformSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1049090067788854663L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			UniformSampleDistribution uniformSampleDistribution = getRandomUniformSampleDistribution(randomGenerator.nextLong());
			assertFalse(uniformSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			UniformSampleDistribution uniformSampleDistribution = getRandomUniformSampleDistribution(randomGenerator.nextLong());
			assertFalse(uniformSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			UniformSampleDistribution uniformSampleDistribution = getRandomUniformSampleDistribution(randomGenerator.nextLong());
			assertTrue(uniformSampleDistribution.equals(uniformSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			UniformSampleDistribution uniformSampleDistribution1 = getRandomUniformSampleDistribution(seed);
			UniformSampleDistribution uniformSampleDistribution2 = getRandomUniformSampleDistribution(seed);

			assertTrue(uniformSampleDistribution1.equals(uniformSampleDistribution2));
			assertTrue(uniformSampleDistribution2.equals(uniformSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(uniformSampleDistribution1.equals(uniformSampleDistribution2));
				assertTrue(uniformSampleDistribution2.equals(uniformSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4348829682964300149L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			UniformSampleDistribution uniformSampleDistribution1 = getRandomUniformSampleDistribution(seed);
			UniformSampleDistribution uniformSampleDistribution2 = getRandomUniformSampleDistribution(seed);
			assertEquals(uniformSampleDistribution1, uniformSampleDistribution2);
			assertEquals(uniformSampleDistribution1.hashCode(), uniformSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			UniformSampleDistribution uniformSampleDistribution1 = getRandomUniformSampleDistribution(randomGenerator.nextLong());
			hashCodes.add(uniformSampleDistribution1.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistribution.class, name = "toString", args = {})	
	public void testToString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(133693667452813973L);
		UniformSampleDistribution uniformSampleDistribution = new UniformSampleDistribution(randomGenerator,
				UniformSampleDistributionData.builder().setLower(2.0).setUpper(3.0).build());
		String expectedValue = "UniformSampleDistribution [uniformSampleDistributionData=UniformSampleDistributionData [data=Data [lower=2.0, uppper=3.0]]]";
		String actualValue = uniformSampleDistribution.toString();
		assertEquals(expectedValue, actualValue);
	}


}
