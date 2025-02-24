package gov.hhs.aspr.ms.util.stats.distributions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

public class AT_LogNormalSampleDistributionData {

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		// nothing to test -- all errors are caught in the mutation methods
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.Builder.class, name = "setShape", args = { double.class })
	public void testSetShape() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1604369983649310118L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			assertEquals(shape, logNormalSampleDistributionData.getShape());
		}

		// precondition test: if the shape is non-positive
		ContractException contractException = assertThrows(ContractException.class,
				() -> LogNormalSampleDistributionData.builder().setShape(0));
		assertEquals(DistributionError.NON_POSITIVE_PARAMETER, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.Builder.class, name = "setScale", args = { double.class })
	public void testSetScale() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6462058530107974965L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			assertEquals(scale, logNormalSampleDistributionData.getScale());
		}

		// precondition test: if the scale is non-positive
		ContractException contractException = assertThrows(ContractException.class,
				() -> LogNormalSampleDistributionData.builder().setScale(0));
		assertEquals(DistributionError.NON_POSITIVE_PARAMETER, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(LogNormalSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5286343336157169236L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();
			assertFalse(logNormalSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();
			assertFalse(logNormalSampleDistributionData.equals(new Object()));
		}

		// not equal when having different parameters
		for (int i = 0; i < 30; i++) {
			double shape1 = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale1 = randomGenerator.nextDouble() * 10 + 0.0001;

			double shape2 = shape1 + 0.0001;
			double scale2 = scale1 + 0.0001;

			LogNormalSampleDistributionData logNormalSampleDistributionData1 = LogNormalSampleDistributionData.builder()
					.setShape(shape1)//
					.setScale(scale1)//
					.build();

			LogNormalSampleDistributionData logNormalSampleDistributionData2 = LogNormalSampleDistributionData.builder()
					.setShape(shape2)//
					.setScale(scale1)//
					.build();

			LogNormalSampleDistributionData logNormalSampleDistributionData3 = LogNormalSampleDistributionData.builder()
					.setShape(shape1)//
					.setScale(scale2)//
					.build();

			assertNotEquals(logNormalSampleDistributionData1, logNormalSampleDistributionData2);
			assertNotEquals(logNormalSampleDistributionData1, logNormalSampleDistributionData3);
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();
			assertTrue(logNormalSampleDistributionData.equals(logNormalSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			LogNormalSampleDistributionData logNormalSampleDistributionData1 = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			LogNormalSampleDistributionData logNormalSampleDistributionData2 = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();
			assertTrue(logNormalSampleDistributionData1.equals(logNormalSampleDistributionData2));
			assertTrue(logNormalSampleDistributionData2.equals(logNormalSampleDistributionData1));
			for (int j = 0; j < 3; j++) {
				assertTrue(logNormalSampleDistributionData1.equals(logNormalSampleDistributionData2));
				assertTrue(logNormalSampleDistributionData2.equals(logNormalSampleDistributionData1));
			}

		}
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		LogNormalSampleDistributionData original = LogNormalSampleDistributionData.builder().setShape(6).setScale(7)
				.build();

		LogNormalSampleDistributionData duplicate = LogNormalSampleDistributionData.builder().setShape(6).setScale(7)
				.build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		LogNormalSampleDistributionData cloned = original.getCloneBuilder().build();
		assertEquals(original, cloned);
		assertEquals(duplicate, cloned);

		// if the shape value is changed the original is unaffected
		original.getCloneBuilder().setShape(5).build();
		assertEquals(duplicate, original);

		// if the scale value is changed the original is unaffected
		original.getCloneBuilder().setScale(10).build();
		assertEquals(duplicate, original);

	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.class, name = "getShape", args = {})
	public void testGetShape() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1947529837188117390L);

		assertEquals(1.0, LogNormalSampleDistributionData.builder().build().getShape());

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			assertEquals(shape, logNormalSampleDistributionData.getShape());
		}
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.class, name = "getScale", args = {})
	public void testGetScale() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5288550877733869859L);

		assertEquals(1.0, LogNormalSampleDistributionData.builder().build().getScale());

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			assertEquals(scale, logNormalSampleDistributionData.getScale());
		}
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1974002025405786875L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			LogNormalSampleDistributionData logNormalSampleDistributionData1 = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			LogNormalSampleDistributionData logNormalSampleDistributionData2 = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			assertEquals(logNormalSampleDistributionData1, logNormalSampleDistributionData2);

			assertEquals(logNormalSampleDistributionData1.hashCode(), logNormalSampleDistributionData2.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			int expectedHashCode = logNormalSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, logNormalSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			hashCodes.add(logNormalSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(942954871321328843L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			LogNormalSampleDistributionData logNormalSampleDistributionData = LogNormalSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			String actual = logNormalSampleDistributionData.toString();
			String expected = "LogNormalSampleDistributionData [data=Data [shape=" + shape + ", scale=" + scale + "]]";
			assertEquals(expected, actual);
		}
	}

	private LogNormalSampleDistributionData getRandomLogNormalSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		return LogNormalSampleDistributionData.builder().setScale(randomGenerator.nextDouble())
				.setShape(randomGenerator.nextDouble()).build();
	}

	@Test
	@UnitTestMethod(target = LogNormalSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5003774486903184095L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			LogNormalSampleDistributionData logNormalSampleDistributionData1 = getRandomLogNormalSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(logNormalSampleDistributionData1);
			LogNormalSampleDistribution logNormalSampleDistribution = logNormalSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(logNormalSampleDistribution);
			LogNormalSampleDistributionData logNormalSampleDistributionData2 = logNormalSampleDistribution
					.getSampleDistributionData();
			assertNotNull(logNormalSampleDistributionData2);
			assertEquals(logNormalSampleDistributionData1, logNormalSampleDistributionData2);
		}
	}

}
