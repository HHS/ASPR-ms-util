package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_ConstantSampleDistributionData {

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		//nothing to test -- all errors are caught in the mutation methods
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.Builder.class, name = "setValue", args = { double.class })
	public void testSetMean() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2502688629848284439L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;			
			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(mean).build();
			assertEquals(mean, constantSampleDistributionData.getValue());
		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(ConstantSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8002651869627697878L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;

			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(mean)//
					
					.build();
			assertFalse(constantSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(mean)//					
					.build();
			assertFalse(constantSampleDistributionData.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			
			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(mean)//					
					.build();
			assertTrue(constantSampleDistributionData.equals(constantSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;

			ConstantSampleDistributionData constantSampleDistributionData1 = ConstantSampleDistributionData.builder()
					.setValue(mean)//					
					.build();

			ConstantSampleDistributionData constantSampleDistributionData2 = ConstantSampleDistributionData.builder()
					.setValue(mean)//					
					.build();
			assertTrue(constantSampleDistributionData1.equals(constantSampleDistributionData2));
			assertTrue(constantSampleDistributionData2.equals(constantSampleDistributionData1));
			for (int j = 0; j < 3; j++) {
				assertTrue(constantSampleDistributionData1.equals(constantSampleDistributionData2));
				assertTrue(constantSampleDistributionData2.equals(constantSampleDistributionData1));
			}

		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		ConstantSampleDistributionData original = ConstantSampleDistributionData.builder().setValue(6)
				.build();

		ConstantSampleDistributionData duplicate = ConstantSampleDistributionData.builder().setValue(6)
				.build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		ConstantSampleDistributionData cloned = original.getCloneBuilder().build();
		assertEquals(duplicate, cloned);

		// if the mean value is changed the original is unaffected
		original.getCloneBuilder().setValue(5).build();
		assertEquals(duplicate, original);

		// if the standardDeviation value is changed the original is unaffected
		original.getCloneBuilder().build();
		assertEquals(duplicate, original);

	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.class, name = "getValue", args = {})
	public void testGetMean() {
		
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8707148797311985140L);

		assertEquals(0.0, ConstantSampleDistributionData.builder().build().getValue());
		
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(mean).build();
			assertEquals(mean, constantSampleDistributionData.getValue());
		}
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2678948420814252720L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			

			ConstantSampleDistributionData constantSampleDistributionData1 = ConstantSampleDistributionData.builder()
					.setValue(mean)//					
					.build();

			ConstantSampleDistributionData constantSampleDistributionData2 = ConstantSampleDistributionData.builder()
					.setValue(mean)//			
					.build();

			assertEquals(constantSampleDistributionData1, constantSampleDistributionData2);

			assertEquals(constantSampleDistributionData1.hashCode(), constantSampleDistributionData2.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
	
			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(mean)//					
					.build();

			int expectedHashCode = constantSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, constantSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;			

			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(mean)//					
					.build();

			hashCodes.add(constantSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(762503665576823288L);

		for (int i = 0; i < 30; i++) {
			double mean = randomGenerator.nextDouble()*100+0.00001;
			

			ConstantSampleDistributionData constantSampleDistributionData = ConstantSampleDistributionData.builder()
					.setValue(mean)//					
					.build();

			String actual = constantSampleDistributionData.toString();
			String expected = "ConstantSampleDistributionData [data=Data [value=" + mean + "]]";
			assertEquals(expected, actual);
		}
	}
	
	private ConstantSampleDistributionData getRandomConstantSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		return ConstantSampleDistributionData.builder().setValue(randomGenerator.nextDouble())
				.build();
	}

	@Test
	@UnitTestMethod(target = ConstantSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3409841893168514122L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			ConstantSampleDistributionData constantSampleDistributionData1 = getRandomConstantSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(constantSampleDistributionData1);
			ConstantSampleDistribution constantSampleDistribution = constantSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(constantSampleDistribution);
			ConstantSampleDistributionData constantSampleDistributionData2 = constantSampleDistribution.getSampleDistributionData();
			assertNotNull(constantSampleDistributionData2);
			assertEquals(constantSampleDistributionData1, constantSampleDistributionData2);
		}
	}

}
