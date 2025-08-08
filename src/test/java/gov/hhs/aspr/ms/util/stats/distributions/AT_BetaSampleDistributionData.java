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

public class AT_BetaSampleDistributionData {

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		// nothing to test -- all errors are caught in the mutation methods
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.Builder.class, name = "setAlpha", args = { double.class })
	public void testSetAlpha() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2502688629848284439L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();
			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)
					.setBeta(beta).build();
			assertEquals(alpha, betaSampleDistributionData.getAlpha());
		}

		// precondition test: if alpha is negative
		ContractException contractException = assertThrows(ContractException.class,
				() -> BetaSampleDistributionData.builder().setAlpha(-0.5));
		assertEquals(DistributionError.NON_POSITIVE_PARAMETER, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.Builder.class, name = "setBeta", args = { double.class })
	public void testSetBeta() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1570219788202151800L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();
			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)
					.setBeta(beta).build();
			assertEquals(beta, betaSampleDistributionData.getBeta());
		}

		// precondition test: if beta is negative
		ContractException contractException = assertThrows(ContractException.class,
				() -> BetaSampleDistributionData.builder().setBeta(-0.5));
		assertEquals(DistributionError.NON_POSITIVE_PARAMETER, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(BetaSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8002651869627697878L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)//
					.setBeta(beta)//
					.build();
			assertFalse(betaSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)//
					.setBeta(beta)//
					.build();
			assertFalse(betaSampleDistributionData.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();
			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)//
					.setBeta(beta)//
					.build();
			assertTrue(betaSampleDistributionData.equals(betaSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			BetaSampleDistributionData betaSampleDistributionData1 = BetaSampleDistributionData.builder()
					.setAlpha(alpha)//
					.setBeta(beta)//
					.build();

			BetaSampleDistributionData betaSampleDistributionData2 = BetaSampleDistributionData.builder()
					.setAlpha(alpha)//
					.setBeta(beta)//
					.build();
			assertTrue(betaSampleDistributionData1.equals(betaSampleDistributionData2));
			assertTrue(betaSampleDistributionData2.equals(betaSampleDistributionData1));
			for (int j = 0; j < 3; j++) {
				assertTrue(betaSampleDistributionData1.equals(betaSampleDistributionData2));
				assertTrue(betaSampleDistributionData2.equals(betaSampleDistributionData1));
			}

		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		BetaSampleDistributionData original = BetaSampleDistributionData.builder().setAlpha(6).setBeta(7).build();

		BetaSampleDistributionData duplicate = BetaSampleDistributionData.builder().setAlpha(6).setBeta(7).build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		BetaSampleDistributionData cloned = original.getCloneBuilder().build();
		assertEquals(duplicate, cloned);

		// if the alpha value is changed the original is unaffected
		original.getCloneBuilder().setAlpha(5).build();
		assertEquals(duplicate, original);

		// if the beta value is changed the original is unaffected
		original.getCloneBuilder().setBeta(10).build();
		assertEquals(duplicate, original);

	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.class, name = "getAlpha", args = {})
	public void testGetAlpha() {
		assertEquals(1.0, BetaSampleDistributionData.builder().build().getAlpha());
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8707148797311985140L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();
			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)
					.setBeta(beta).build();
			assertEquals(alpha, betaSampleDistributionData.getAlpha());
		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.class, name = "getBeta", args = {})
	public void testGetBeta() {

		assertEquals(1.0, BetaSampleDistributionData.builder().build().getBeta());

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2226337999633679132L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();
			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)
					.setBeta(beta).build();
			assertEquals(beta, betaSampleDistributionData.getBeta());
		}
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2678948420814252720L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			BetaSampleDistributionData betaSampleDistributionData1 = BetaSampleDistributionData.builder()
					.setAlpha(alpha)//
					.setBeta(beta)//
					.build();

			BetaSampleDistributionData betaSampleDistributionData2 = BetaSampleDistributionData.builder()
					.setAlpha(alpha)//
					.setBeta(beta)//
					.build();

			assertEquals(betaSampleDistributionData1, betaSampleDistributionData2);

			assertEquals(betaSampleDistributionData1.hashCode(), betaSampleDistributionData2.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)//
					.setBeta(beta)//
					.build();

			int expectedHashCode = betaSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, betaSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)//
					.setBeta(beta)//
					.build();

			hashCodes.add(betaSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(762503665576823288L);

		for (int i = 0; i < 30; i++) {
			double alpha = randomGenerator.nextDouble();
			double beta = randomGenerator.nextDouble();

			BetaSampleDistributionData betaSampleDistributionData = BetaSampleDistributionData.builder().setAlpha(alpha)//
					.setBeta(beta)//
					.build();

			String actual = betaSampleDistributionData.toString();
			String expected = "BetaSampleDistributionData [data=Data [alpha=" + alpha + ", beta=" + beta + "]]";
			assertEquals(expected, actual);
		}
	}

	private BetaSampleDistributionData getRandomBetaSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		return BetaSampleDistributionData.builder().setAlpha(randomGenerator.nextDouble())
				.setBeta(randomGenerator.nextDouble()).build();
	}

	@Test
	@UnitTestMethod(target = BetaSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7544953035453001936L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			BetaSampleDistributionData betaSampleDistributionData1 = getRandomBetaSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(betaSampleDistributionData1);
			BetaSampleDistribution betaSampleDistribution = betaSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(betaSampleDistribution);
			BetaSampleDistributionData betaSampleDistributionData2 = betaSampleDistribution.getSampleDistributionData();
			assertNotNull(betaSampleDistributionData2);
			assertEquals(betaSampleDistributionData1, betaSampleDistributionData2);
		}
	}

}
