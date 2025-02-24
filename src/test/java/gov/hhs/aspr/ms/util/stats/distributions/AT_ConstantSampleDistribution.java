package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.ConstantRealDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_ConstantSampleDistribution {

	@Test
	@UnitTestConstructor(target = ConstantSampleDistribution.class, args = {
			ConstantSampleDistributionData.class })
	public void testConstantSampleDistribution() {

		// precondition test: if the sample distribution data is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new ConstantSampleDistribution(null));
		assertEquals(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "getNumericalMean", args = {})
	public void testGetNumericalMean() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1768151454887450386L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;
			// build a sample distribution
			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(value).build();
			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					constantSampleDistributionData);

			// build the inner distribution instance
			ConstantRealDistribution innerDistribution = new ConstantRealDistribution(value);

			assertEquals(innerDistribution.getNumericalMean(), constantSampleDistribution.getNumericalMean());

		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "getNumericalVariance", args = {})
	public void testGetNumericalVariance() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5218033541300203697L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;
			// build a sample distribution
			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(value).build();
			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					constantSampleDistributionData);

			// build the inner distribution instance
			ConstantRealDistribution innerDistribution = new ConstantRealDistribution(value);

			assertEquals(innerDistribution.getNumericalVariance(), constantSampleDistribution.getNumericalVariance());

		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "getNumericalStandardDeviation", args = {})
	public void testGetNumericalStandardDeviation() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2191501536942530754L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;
			// build a sample distribution
			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(value).build();
			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					constantSampleDistributionData);

			// build the inner distribution instance
			ConstantRealDistribution innerDistribution = new ConstantRealDistribution(value);

			assertEquals(FastMath.sqrt(innerDistribution.getNumericalVariance()),
					constantSampleDistribution.getNumericalStandardDeviation(),
					DistributionTestSupport.STANDARD_DEVIATION_TOLERANCE);

		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "getSampleDistributionData", args = {})
	public void testGetSampleDistributionData() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7265686488094247263L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ConstantSampleDistributionData expectedConstantSampleDistributionData = ConstantSampleDistributionData
					.builder().setValue(value).build();

			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					expectedConstantSampleDistributionData);

			// get the distribution data from the distribution
			ConstantSampleDistributionData actualUniformSampleDistributionData = constantSampleDistribution
					.getSampleDistributionData();

			// show that the distribution data is equal to the input
			assertEquals(expectedConstantSampleDistributionData, actualUniformSampleDistributionData);

		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "sample", args = {})
	public void testSample() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7168258417324022228L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ConstantSampleDistributionData expectedConstantSampleDistributionData = ConstantSampleDistributionData
					.builder().setValue(value).build();
			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					expectedConstantSampleDistributionData);

			// build the inner distribution instance
			ConstantRealDistribution innerDistribution = new ConstantRealDistribution(value);

			// show that sample distribution properly wraps the inner distribution
			for (int j = 0; j < 100; j++) {
				assertEquals(innerDistribution.sample(), constantSampleDistribution.sample());
			}
		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "getSupportLowerBound", args = {})
	public void testGetSupportLowerBound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7101609231290201624L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ConstantSampleDistributionData expectedConstantSampleDistributionData = ConstantSampleDistributionData
					.builder().setValue(value).build();
			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					expectedConstantSampleDistributionData);

			// build the inner distribution instance
			ConstantRealDistribution innerDistribution = new ConstantRealDistribution(value);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.getSupportLowerBound(), constantSampleDistribution.getSupportLowerBound());

		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "isSupportLowerBoundInclusive", args = {})
	public void testIsSupportLowerBoundInclusive() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6686135128963504940L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ConstantSampleDistributionData expectedConstantSampleDistributionData = ConstantSampleDistributionData
					.builder().setValue(value).build();
			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					expectedConstantSampleDistributionData);

			// build the inner distribution instance
			ConstantRealDistribution innerDistribution = new ConstantRealDistribution(value);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.isSupportLowerBoundInclusive(),
					constantSampleDistribution.isSupportLowerBoundInclusive());

		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "getSupportUpperBound", args = {})
	public void testGetSupportUpperBound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8237858947786492252L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ConstantSampleDistributionData expectedConstantSampleDistributionData = ConstantSampleDistributionData
					.builder().setValue(value).build();
			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					expectedConstantSampleDistributionData);

			// build the inner distribution instance
			ConstantRealDistribution innerDistribution = new ConstantRealDistribution(value);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.getSupportUpperBound(), constantSampleDistribution.getSupportUpperBound());

		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "isSupportUpperBoundInclusive", args = {})
	public void testIsSupportUpperBoundInclusive() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(813539485621720876L);

		for (int i = 0; i < 30; i++) {
			double value = randomGenerator.nextDouble() * 100 + 0.0000001;

			// build a sample distribution
			ConstantSampleDistributionData expectedConstantSampleDistributionData = ConstantSampleDistributionData
					.builder().setValue(value).build();
			ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
					expectedConstantSampleDistributionData);

			// build the inner distribution instance
			ConstantRealDistribution innerDistribution = new ConstantRealDistribution(value);

			// show that sample distribution properly wraps the inner distribution

			assertEquals(innerDistribution.isSupportUpperBoundInclusive(),
					constantSampleDistribution.isSupportUpperBoundInclusive());

		}
	}
	
	private ConstantSampleDistribution getRandomConstantSampleDistribution(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
				.setValue(randomGenerator.nextDouble()).build();
		return new ConstantSampleDistribution(constantSampleDistributionData);
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(9019216524599406239L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			ConstantSampleDistribution constantSampleDistribution = getRandomConstantSampleDistribution(randomGenerator.nextLong());
			assertFalse(constantSampleDistribution.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			ConstantSampleDistribution constantSampleDistribution = getRandomConstantSampleDistribution(randomGenerator.nextLong());
			assertFalse(constantSampleDistribution.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			ConstantSampleDistribution constantSampleDistribution = getRandomConstantSampleDistribution(randomGenerator.nextLong());
			assertTrue(constantSampleDistribution.equals(constantSampleDistribution));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			ConstantSampleDistribution constantSampleDistribution1 = getRandomConstantSampleDistribution(seed);
			ConstantSampleDistribution constantSampleDistribution2 = getRandomConstantSampleDistribution(seed);

			assertTrue(constantSampleDistribution1.equals(constantSampleDistribution2));
			assertTrue(constantSampleDistribution2.equals(constantSampleDistribution1));
			for (int j = 0; j < 3; j++) {
				assertTrue(constantSampleDistribution1.equals(constantSampleDistribution2));
				assertTrue(constantSampleDistribution2.equals(constantSampleDistribution1));
			}
		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5458269950794795316L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			ConstantSampleDistribution constantSampleDistribution1 = getRandomConstantSampleDistribution(seed);
			ConstantSampleDistribution constantSampleDistribution2 = getRandomConstantSampleDistribution(seed);
			assertEquals(constantSampleDistribution1, constantSampleDistribution2);
			assertEquals(constantSampleDistribution1.hashCode(), constantSampleDistribution2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			ConstantSampleDistribution constantSampleDistribution = getRandomConstantSampleDistribution(randomGenerator.nextLong());
			hashCodes.add(constantSampleDistribution.hashCode());
		}
		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistribution.class, name = "toString", args = {})	
	public void testToString() {		
		ConstantSampleDistribution constantSampleDistribution = new ConstantSampleDistribution(
				ConstantSampleDistributionData.builder().setValue(0.5).build());
		String expectedValue = "ConstantSampleDistribution [constantSampleDistributionData=ConstantSampleDistributionData [data=Data [value=0.5]]]";
		String actualValue = constantSampleDistribution.toString();		
		assertEquals(expectedValue, actualValue);
	}

}
