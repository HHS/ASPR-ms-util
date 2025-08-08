package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
public class AT_LogNormalSampleDistribution {

	@Test
	@UnitTestConstructor(target = LogNormalSampleDistribution.class, args = { RandomGenerator.class,
			LogNormalSampleDistributionData.class })
	public void testLogNormalSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(595403322160924621L);
		LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
				.setShape(3.5).setScale(2.1).build();

		// precondition test: if the random generator is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new LogNormalSampleDistribution(null, logNormalSampleDistributionData));
		assertEquals(DistributionError.NULL_RNG, contractException.getErrorType());

		// precondition test: if the sample distribution data is null
		contractException = assertThrows(ContractException.class,
				() -> new LogNormalSampleDistribution(randomGenerator, null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2234523718975738906L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
					logNormalSampleDistributionData);

			// build the inner distribution instance
			LogNormalDistribution innerDistribution = new LogNormalDistribution(randomGenerator,  scale, shape);

			assertEquals(innerDistribution.getNumericalMean(), logNormalSampleDistribution.getNumericalMean());

		}
	}
	
	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(900632611392620612L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
					logNormalSampleDistributionData);

			// build the inner distribution instance
			LogNormalDistribution innerDistribution = new LogNormalDistribution(randomGenerator,  scale, shape);

			
			assertEquals(innerDistribution.getNumericalVariance(), logNormalSampleDistribution.getNumericalVariance());

		}
	}
	
	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(900632611392620612L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
					logNormalSampleDistributionData);

			// build the inner distribution instance
			LogNormalDistribution innerDistribution = new LogNormalDistribution(randomGenerator,  scale, shape);

			
			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()), logNormalSampleDistribution.getNumericalStandardDeviation(),DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6837522400576046656L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData expectedLogNormalSampleDistributionData = LogNormalSampleDistributionData
					.builder().setShape(shape).setScale(scale).build();
			
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
					expectedLogNormalSampleDistributionData);

			// get the distribution data from the distribution
			LogNormalSampleDistributionData actualUniformSampleDistributionData = logNormalSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedLogNormalSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator1 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);
		RandomGenerator randomGenerator2 = RandomGeneratorProvider.getRandomGenerator(4432106006956140718L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData expectedLogNormalSampleDistributionData = LogNormalSampleDistributionData
					.builder().setShape(shape).setScale(scale).build();
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator1,
					expectedLogNormalSampleDistributionData);

			// build the inner distribution instance
			LogNormalDistribution innerDistribution = new LogNormalDistribution(randomGenerator2,  scale, shape);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), logNormalSampleDistribution.sample());
			}
		}
	}
	
	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name="getSupportLowerBound", args= {})
	public void testGetSupportLowerBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4128519345203072653L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData expectedLogNormalSampleDistributionData = LogNormalSampleDistributionData
					.builder().setShape(shape).setScale(scale).build();
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
					expectedLogNormalSampleDistributionData);

			// build the inner distribution instance
			LogNormalDistribution innerDistribution = new LogNormalDistribution(randomGenerator,  scale, shape);


			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportLowerBound(), logNormalSampleDistribution.getSupportLowerBound());
			
		}
	}
	
	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name="isSupportLowerBoundInclusive", args= {})
	public void testIsSupportLowerBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7927122069430845040L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData expectedLogNormalSampleDistributionData = LogNormalSampleDistributionData
					.builder().setShape(shape).setScale(scale).build();
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
					expectedLogNormalSampleDistributionData);

			// build the inner distribution instance
			LogNormalDistribution innerDistribution = new LogNormalDistribution(randomGenerator,  scale, shape);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.isSupportLowerBoundInclusive(), logNormalSampleDistribution.isSupportLowerBoundInclusive());
			
		}
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name="getSupportUpperBound", args= {})
	public void testGetSupportUpperBound() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(458076179726228607L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData expectedLogNormalSampleDistributionData = LogNormalSampleDistributionData
					.builder().setShape(shape).setScale(scale).build();
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
					expectedLogNormalSampleDistributionData);

			// build the inner distribution instance
			LogNormalDistribution innerDistribution = new LogNormalDistribution(randomGenerator,  scale, shape);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.getSupportUpperBound(), logNormalSampleDistribution.getSupportUpperBound());
			
		}
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name="isSupportUpperBoundInclusive", args= {})
	public void testIsSupportUpperBoundInclusive() {		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4764140302522002099L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10;
			double scale = randomGenerator.nextDouble() * 10;

			// build a sample distribution
			LogNormalSampleDistributionData expectedLogNormalSampleDistributionData = LogNormalSampleDistributionData
					.builder().setShape(shape).setScale(scale).build();
			LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
					expectedLogNormalSampleDistributionData);

			// build the inner distribution instance
			LogNormalDistribution innerDistribution = new LogNormalDistribution(randomGenerator,  scale, shape);

			// show that sample distribution properly wraps the inner distribution
			
			assertEquals(innerDistribution.isSupportUpperBoundInclusive(), logNormalSampleDistribution.isSupportUpperBoundInclusive());
			
		}
	}
	
	
	private LogNormalSampleDistribution getRandomLogNormalSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
				.setScale(randomGenerator.nextDouble()).setShape(randomGenerator.nextDouble()).build();
		return new LogNormalSampleDistribution(randomGenerator, logNormalSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6421965957132195460L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			LogNormalSampleDistribution logNormalSampleDistribution = getRandomLogNormalSampleDistribution(randomGenerator.nextLong());
			assertFalse(logNormalSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			LogNormalSampleDistribution logNormalSampleDistribution = getRandomLogNormalSampleDistribution(randomGenerator.nextLong());
			assertFalse(logNormalSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			LogNormalSampleDistribution logNormalSampleDistribution = getRandomLogNormalSampleDistribution(randomGenerator.nextLong());
			assertTrue(logNormalSampleDistribution.equals(logNormalSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			LogNormalSampleDistribution logNormalSampleDistribution1 = getRandomLogNormalSampleDistribution(seed);
			LogNormalSampleDistribution logNormalSampleDistribution2 = getRandomLogNormalSampleDistribution(seed);

			assertTrue(logNormalSampleDistribution1.equals(logNormalSampleDistribution2));
			assertTrue(logNormalSampleDistribution2.equals(logNormalSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(logNormalSampleDistribution1.equals(logNormalSampleDistribution2));
				assertTrue(logNormalSampleDistribution2.equals(logNormalSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(63859868154024288L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			LogNormalSampleDistribution logNormalSampleDistribution1 = getRandomLogNormalSampleDistribution(seed);
			LogNormalSampleDistribution logNormalSampleDistribution2 = getRandomLogNormalSampleDistribution(seed);
			assertEquals(logNormalSampleDistribution1, logNormalSampleDistribution2);
			assertEquals(logNormalSampleDistribution1.hashCode(), logNormalSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			LogNormalSampleDistribution logNormalSampleDistribution1 = getRandomLogNormalSampleDistribution(randomGenerator.nextLong());
			hashCodes.add(logNormalSampleDistribution1.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistribution.class, name = "toString", args = {})	
	public void testToString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8722440409511978674L);
		LogNormalSampleDistribution logNormalSampleDistribution = new LogNormalSampleDistribution(randomGenerator,
				LogNormalSampleDistributionData.builder().setScale(0.1).setShape(0.2).build());
		String expectedValue = "LogNormalSampleDistribution [logNormalsSampleDistributionData=LogNormalSampleDistributionData [data=Data [shape=0.2, scale=0.1]]]";
		String actualValue = logNormalSampleDistribution.toString();
		assertEquals(expectedValue, actualValue);
	}



}
