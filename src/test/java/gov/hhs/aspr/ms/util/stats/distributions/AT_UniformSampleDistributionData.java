package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

public class AT_UniformSampleDistributionData {

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		// postcondition tests covered by the other tests in this unit

		// precondition test: if the lower parameter value exceeds the upper parameter
		// value
		assertDoesNotThrow(() -> UniformSampleDistributionData.builder().setLower(10).setUpper(10).build());

		ContractException contractException = assertThrows(ContractException.class,
				() -> UniformSampleDistributionData.builder().setLower(10).setUpper(7).build());
		assertEquals(DistributionError.LOWER_EXCEEDS_UPPER, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.Builder.class, name = "setLower", args = { double.class })
	public void testSetLower() {

		assertEquals(0.0, UniformSampleDistributionData.builder().build().getLower());

		for (int i = 0; i < 30; i++) {
			double lower = (i - 15) * 100;
			double upper = lower + 10;
			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower).setUpper(upper).build();
			assertEquals(lower, uniformSampleDistributionData.getLower());
		}

	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.Builder.class, name = "setUpper", args = { double.class })
	public void testSetUpper() {

		assertEquals(1.0, UniformSampleDistributionData.builder().build().getUpper());

		for (int i = 0; i < 30; i++) {
			double lower = (i - 15) * 100;
			double upper = lower + 10;
			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower).setUpper(upper).build();
			assertEquals(upper, uniformSampleDistributionData.getUpper());
		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(UniformSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1637520462006664100L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double lower = randomGenerator.nextDouble() * 100 - 50;
			double upper = lower + randomGenerator.nextDouble() * 100;

			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();
			assertFalse(uniformSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double lower = randomGenerator.nextDouble() * 100 - 50;
			double upper = lower + randomGenerator.nextDouble() * 100;

			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();
			assertFalse(uniformSampleDistributionData.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double lower = randomGenerator.nextDouble() * 100 - 50;
			double upper = lower + randomGenerator.nextDouble() * 100;

			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();
			assertTrue(uniformSampleDistributionData.equals(uniformSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double lower = randomGenerator.nextDouble() * 100 - 50;
			double upper = lower + randomGenerator.nextDouble() * 100;

			UniformSampleDistributionData uniformSampleDistributionData1 = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();

			UniformSampleDistributionData uniformSampleDistributionData2 = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();
			assertTrue(uniformSampleDistributionData1.equals(uniformSampleDistributionData2));
			assertTrue(uniformSampleDistributionData2.equals(uniformSampleDistributionData1));
			for (int j = 0; j < 3; j++) {
				assertTrue(uniformSampleDistributionData1.equals(uniformSampleDistributionData2));
				assertTrue(uniformSampleDistributionData2.equals(uniformSampleDistributionData1));
			}

		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		UniformSampleDistributionData original = UniformSampleDistributionData.builder().setLower(6).setUpper(7)
				.build();

		UniformSampleDistributionData duplicate = UniformSampleDistributionData.builder().setLower(6).setUpper(7)
				.build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		UniformSampleDistributionData cloned = original.getCloneBuilder().build();
		assertEquals(duplicate, cloned);

		// if the lower value is changed the original is unaffected
		original.getCloneBuilder().setLower(5).build();
		assertEquals(duplicate, original);

		// if the upper value is changed the original is unaffected
		original.getCloneBuilder().setUpper(10).build();
		assertEquals(duplicate, original);

	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.class, name = "getLower", args = {})
	public void testGetLower() {

		for (int i = 0; i < 30; i++) {
			double lower = (i - 15) * 100;
			double upper = lower + 10;
			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower).setUpper(upper).build();
			assertEquals(lower, uniformSampleDistributionData.getLower());
		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.class, name = "getUpper", args = {})
	public void testGetUpper() {
		for (int i = 0; i < 30; i++) {
			double lower = (i - 15) * 100;
			double upper = lower + 10;
			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower).setUpper(upper).build();
			assertEquals(upper, uniformSampleDistributionData.getUpper());
		}
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2542586806278055148L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double lower = randomGenerator.nextDouble() * 100 - 50;
			double upper = lower + randomGenerator.nextDouble() * 100;

			UniformSampleDistributionData uniformSampleDistributionData1 = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();

			UniformSampleDistributionData uniformSampleDistributionData2 = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();

			assertEquals(uniformSampleDistributionData1, uniformSampleDistributionData2);

			assertEquals(uniformSampleDistributionData1.hashCode(), uniformSampleDistributionData2.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double lower = randomGenerator.nextDouble() * 100 - 50;
			double upper = lower + randomGenerator.nextDouble() * 100;

			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();

			int expectedHashCode = uniformSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, uniformSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double lower = randomGenerator.nextDouble() * 100 - 50;
			double upper = lower + randomGenerator.nextDouble() * 100;

			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();

			hashCodes.add(uniformSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8150299390089728419L);

		for (int i = 0; i < 30; i++) {
			double lower = randomGenerator.nextDouble() * 100 - 50;
			double upper = lower + randomGenerator.nextDouble() * 100;

			UniformSampleDistributionData uniformSampleDistributionData = UniformSampleDistributionData.builder()
					.setLower(lower)//
					.setUpper(upper)//
					.build();

			String actual = uniformSampleDistributionData.toString();
			String expected = "UniformSampleDistributionData [data=Data [lower=" + lower + ", uppper=" + upper + "]]";
			assertEquals(expected, actual);
		}
	}

	private UniformSampleDistributionData getRandomUniformSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		double lower = randomGenerator.nextDouble();
		double upper = lower + randomGenerator.nextDouble();
		return UniformSampleDistributionData.builder().setLower(lower).setUpper(upper).build();
	}

	@Test
	@UnitTestMethod(target = UniformSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2122656001888205582L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			UniformSampleDistributionData uniformSampleDistributionData1 = getRandomUniformSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(uniformSampleDistributionData1);
			UniformSampleDistribution uniformSampleDistribution = uniformSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(uniformSampleDistribution);
			UniformSampleDistributionData uniformSampleDistributionData2 = uniformSampleDistribution
					.getSampleDistributionData();
			assertNotNull(uniformSampleDistributionData2);
			assertEquals(uniformSampleDistributionData1, uniformSampleDistributionData2);
		}
	}

}
