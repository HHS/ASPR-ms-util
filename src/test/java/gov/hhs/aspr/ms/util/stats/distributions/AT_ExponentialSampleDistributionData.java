package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_ExponentialSampleDistributionData {

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		//nothing to test -- all errors are caught in the mutation methods
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.Builder.class, name = "setMean", args = { double.class })
	public void testSetMean() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2502688629848284439L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;			
			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
					.setMean(mean).build();
			assertEquals(mean, exponentialSampleDistributionData.getMean());
		}
		
		//precondition test: if the mean is non-positive
		ContractException contractException = assertThrows(ContractException.class,()->ExponentialSampleDistributionData.builder().setMean(0.0));
		assertEquals(DistributionError.NON_POSITIVE_PARAMETER, contractException.getErrorType());
		
		contractException = assertThrows(ContractException.class,()->ExponentialSampleDistributionData.builder().setMean(-1.0));
		assertEquals(DistributionError.NON_POSITIVE_PARAMETER, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(ExponentialSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8002651869627697878L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;

			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
					.setMean(mean)//
					
					.build();
			assertFalse(exponentialSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
					.setMean(mean)//					
					.build();
			assertFalse(exponentialSampleDistributionData.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			
			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
					.setMean(mean)//					
					.build();
			assertTrue(exponentialSampleDistributionData.equals(exponentialSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;

			ExponentialSampleDistributionData exponentialSampleDistributionData1 = ExponentialSampleDistributionData.builder()
					.setMean(mean)//					
					.build();

			ExponentialSampleDistributionData exponentialSampleDistributionData2 = ExponentialSampleDistributionData.builder()
					.setMean(mean)//					
					.build();
			assertTrue(exponentialSampleDistributionData1.equals(exponentialSampleDistributionData2));
			assertTrue(exponentialSampleDistributionData2.equals(exponentialSampleDistributionData1));
			for (int j = 0; j < 3; j++) {
				assertTrue(exponentialSampleDistributionData1.equals(exponentialSampleDistributionData2));
				assertTrue(exponentialSampleDistributionData2.equals(exponentialSampleDistributionData1));
			}

		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		ExponentialSampleDistributionData original = ExponentialSampleDistributionData.builder().setMean(6)
				.build();

		ExponentialSampleDistributionData duplicate = ExponentialSampleDistributionData.builder().setMean(6)
				.build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		ExponentialSampleDistributionData cloned = original.getCloneBuilder().build();
		assertEquals(duplicate, cloned);

		// if the mean value is changed the original is unaffected
		original.getCloneBuilder().setMean(5).build();
		assertEquals(duplicate, original);

		// if the standardDeviation value is changed the original is unaffected
		original.getCloneBuilder().build();
		assertEquals(duplicate, original);

	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.class, name = "getMean", args = {})
	public void testGetMean() {
		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8707148797311985140L);

		assertEquals(1.0, ExponentialSampleDistributionData.builder().build().getMean());
		
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
					.setMean(mean).build();
			assertEquals(mean, exponentialSampleDistributionData.getMean());
		}
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2678948420814252720L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			

			ExponentialSampleDistributionData exponentialSampleDistributionData1 = ExponentialSampleDistributionData.builder()
					.setMean(mean)//					
					.build();

			ExponentialSampleDistributionData exponentialSampleDistributionData2 = ExponentialSampleDistributionData.builder()
					.setMean(mean)//			
					.build();

			assertEquals(exponentialSampleDistributionData1, exponentialSampleDistributionData2);

			assertEquals(exponentialSampleDistributionData1.hashCode(), exponentialSampleDistributionData2.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
	
			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
					.setMean(mean)//					
					.build();

			int expectedHashCode = exponentialSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, exponentialSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;			

			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
					.setMean(mean)//					
					.build();

			hashCodes.add(exponentialSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(762503665576823288L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			

			ExponentialSampleDistributionData exponentialSampleDistributionData = ExponentialSampleDistributionData.builder()
					.setMean(mean)//					
					.build();

			String actual = exponentialSampleDistributionData.toString();
			String expected = "ExponentialSampleDistributionData [data=Data [mean=" + mean + "]]";
			assertEquals(expected, actual);
		}
	}
	
	
	private ExponentialSampleDistributionData getRandomExponentialSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		return ExponentialSampleDistributionData.builder().setMean(randomGenerator.nextDouble())
				.build();
	}

	@Test
	@UnitTestMethod(target = ExponentialSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3630219710509216001L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			ExponentialSampleDistributionData exponentialSampleDistributionData1 = getRandomExponentialSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(exponentialSampleDistributionData1);
			ExponentialSampleDistribution exponentialSampleDistribution = exponentialSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(exponentialSampleDistribution);
			ExponentialSampleDistributionData exponentialSampleDistributionData2 = exponentialSampleDistribution.getSampleDistributionData();
			assertNotNull(exponentialSampleDistributionData2);
			assertEquals(exponentialSampleDistributionData1, exponentialSampleDistributionData2);
		}
	}

}
