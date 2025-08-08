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

public class AT_GammaSampleDistributionData {

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		// nothing to test -- all errors are caught in the mutation methods
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.Builder.class, name = "setShape", args = { double.class })
	public void testSetShape() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1959551290503631288L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			assertEquals(shape, gammaSampleDistributionData.getShape());
		}

		// precondition test: if the shape is non-positive
		ContractException contractException = assertThrows(ContractException.class,
				() -> GammaSampleDistributionData.builder().setShape(0));
		assertEquals(DistributionError.NON_POSITIVE_PARAMETER, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.Builder.class, name = "setScale", args = { double.class })
	public void testSetScale() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1460515750568099813L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			assertEquals(scale, gammaSampleDistributionData.getScale());
		}

		// precondition test: if the scale is non-positive
		ContractException contractException = assertThrows(ContractException.class,
				() -> GammaSampleDistributionData.builder().setScale(0));
		assertEquals(DistributionError.NON_POSITIVE_PARAMETER, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(GammaSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3292188353623898528L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();
			assertFalse(gammaSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();
			assertFalse(gammaSampleDistributionData.equals(new Object()));
		}

		// not equal when having different parameters
		for (int i = 0; i < 30; i++) {
			double shape1 = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale1 = randomGenerator.nextDouble() * 10 + 0.0001;

			double shape2 = shape1 + 0.0001;
			double scale2 = scale1 + 0.0001;

			GammaSampleDistributionData gammaSampleDistributionData1 = GammaSampleDistributionData.builder()
					.setShape(shape1)//
					.setScale(scale1)//
					.build();

			GammaSampleDistributionData gammaSampleDistributionData2 = GammaSampleDistributionData.builder()
					.setShape(shape2)//
					.setScale(scale1)//
					.build();

			GammaSampleDistributionData gammaSampleDistributionData3 = GammaSampleDistributionData.builder()
					.setShape(shape1)//
					.setScale(scale2)//
					.build();

			assertNotEquals(gammaSampleDistributionData1, gammaSampleDistributionData2);
			assertNotEquals(gammaSampleDistributionData1, gammaSampleDistributionData3);
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();
			assertTrue(gammaSampleDistributionData.equals(gammaSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			GammaSampleDistributionData gammaSampleDistributionData1 = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			GammaSampleDistributionData gammaSampleDistributionData2 = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();
			assertTrue(gammaSampleDistributionData1.equals(gammaSampleDistributionData2));
			assertTrue(gammaSampleDistributionData2.equals(gammaSampleDistributionData1));
			for (int j = 0; j < 3; j++) {
				assertTrue(gammaSampleDistributionData1.equals(gammaSampleDistributionData2));
				assertTrue(gammaSampleDistributionData2.equals(gammaSampleDistributionData1));
			}

		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		GammaSampleDistributionData original = GammaSampleDistributionData.builder().setShape(6).setScale(7).build();

		GammaSampleDistributionData duplicate = GammaSampleDistributionData.builder().setShape(6).setScale(7).build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		GammaSampleDistributionData cloned = original.getCloneBuilder().build();
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
	@UnitTestMethod(target = GammaSampleDistributionData.class, name = "getShape", args = {})
	public void testGetShape() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(41559362936133527L);

		assertEquals(1.0, GammaSampleDistributionData.builder().build().getShape());

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			assertEquals(shape, gammaSampleDistributionData.getShape());
		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.class, name = "getScale", args = {})
	public void testGetScale() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3499538744854883458L);

		assertEquals(1.0, GammaSampleDistributionData.builder().build().getScale());

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape).setScale(scale).build();
			assertEquals(scale, gammaSampleDistributionData.getScale());
		}
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8351894033772831598L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			GammaSampleDistributionData gammaSampleDistributionData1 = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			GammaSampleDistributionData gammaSampleDistributionData2 = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			assertEquals(gammaSampleDistributionData1, gammaSampleDistributionData2);

			assertEquals(gammaSampleDistributionData1.hashCode(), gammaSampleDistributionData2.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;
			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			int expectedHashCode = gammaSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, gammaSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			hashCodes.add(gammaSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1295745509156137121L);

		for (int i = 0; i < 30; i++) {
			double shape = randomGenerator.nextDouble() * 10 + 0.0001;
			double scale = randomGenerator.nextDouble() * 10 + 0.0001;

			GammaSampleDistributionData gammaSampleDistributionData = GammaSampleDistributionData.builder()
					.setShape(shape)//
					.setScale(scale)//
					.build();

			String actual = gammaSampleDistributionData.toString();
			String expected = "GammaSampleDistributionData [data=Data [shape=" + shape + ", scale=" + scale + "]]";
			assertEquals(expected, actual);
		}
	}

	private GammaSampleDistributionData getRandomGammaSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		return GammaSampleDistributionData.builder().setScale(randomGenerator.nextDouble())
				.setShape(randomGenerator.nextDouble()).build();
	}

	@Test
	@UnitTestMethod(target = GammaSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2439283860717584336L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			GammaSampleDistributionData gammaSampleDistributionData1 = getRandomGammaSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(gammaSampleDistributionData1);
			GammaSampleDistribution gammaSampleDistribution = gammaSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(gammaSampleDistribution);
			GammaSampleDistributionData gammaSampleDistributionData2 = gammaSampleDistribution
					.getSampleDistributionData();
			assertNotNull(gammaSampleDistributionData2);
			assertEquals(gammaSampleDistributionData1, gammaSampleDistributionData2);
		}
	}

}
