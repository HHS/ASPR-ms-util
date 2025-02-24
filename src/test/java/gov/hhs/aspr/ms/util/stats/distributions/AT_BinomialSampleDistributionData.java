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

public class AT_BinomialSampleDistributionData {

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.Builder.class, name = "build", args = {})
	public void testBuild() {
		// nothing to test -- all errors are caught in the mutation methods
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.Builder.class, name = "setProbabilityOfSuccess", args = {
			double.class })
	public void testSetProbabilityOfSuccess() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4136612356254359350L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);
			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			assertEquals(probabilityOfSuccess, binomialSampleDistributionData.getProbabilityOfSuccess());
		}

		// precondition test:if probabilityOfSuccess is negative
		ContractException contractException = assertThrows(ContractException.class,
				() -> BinomialSampleDistributionData.builder().setProbabilityOfSuccess(-0.1));
		assertEquals(DistributionError.PARAMETER_LESS_THAN_0, contractException.getErrorType());
		
		// precondition test:if probabilityOfSuccess is negative
		contractException = assertThrows(ContractException.class,
				() -> BinomialSampleDistributionData.builder().setProbabilityOfSuccess(1.1));
		assertEquals(DistributionError.PARAMETER_EXCEEDS_1, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.Builder.class, name = "setTrials", args = { int.class })
	public void testSetTrials() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(9087387973719436443L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);
			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			assertEquals(trials, binomialSampleDistributionData.getTrials());
		}
		
		//precondition test:  if trials is negative
		ContractException contractException = assertThrows(ContractException.class, ()->BinomialSampleDistributionData.builder().setTrials(-1));
		assertEquals(DistributionError.PARAMETER_LESS_THAN_0, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(BinomialSampleDistributionData.builder());
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.class, name = "equals", args = { Object.class })
	public void testEquals() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5785257345876461817L);

		// never equal null
		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();
			assertFalse(binomialSampleDistributionData.equals(null));
		}

		// not equal to other things
		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();
			assertFalse(binomialSampleDistributionData.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);
			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();
			assertTrue(binomialSampleDistributionData.equals(binomialSampleDistributionData));
		}

		// symmetric, transitive, consistent
		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			BinomialSampleDistributionData binomialSampleDistributionData1 = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();

			BinomialSampleDistributionData binomialSampleDistributionData2 = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();
			assertTrue(binomialSampleDistributionData1.equals(binomialSampleDistributionData2));
			assertTrue(binomialSampleDistributionData2.equals(binomialSampleDistributionData1));
			for (int j = 0; j < 3; j++) {
				assertTrue(binomialSampleDistributionData1.equals(binomialSampleDistributionData2));
				assertTrue(binomialSampleDistributionData2.equals(binomialSampleDistributionData1));
			}

		}
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.class, name = "getCloneBuilder", args = {})
	public void testGetCloneBuilder() {
		BinomialSampleDistributionData original = BinomialSampleDistributionData.builder().setProbabilityOfSuccess(0.5)
				.setTrials(7).build();

		BinomialSampleDistributionData duplicate = BinomialSampleDistributionData.builder().setProbabilityOfSuccess(0.5)
				.setTrials(7).build();
		assertEquals(duplicate, original);

		// if no changes are made, the cloned version is equal to the duplicate
		BinomialSampleDistributionData cloned = original.getCloneBuilder().build();
		assertEquals(duplicate, cloned);

		// if the probabilityOfSuccess value is changed the original is unaffected
		original.getCloneBuilder().setProbabilityOfSuccess(0.7).build();
		assertEquals(duplicate, original);

		// if the trials value is changed the original is unaffected
		original.getCloneBuilder().setTrials(10).build();
		assertEquals(duplicate, original);

	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.class, name = "getProbabilityOfSuccess", args = {})
	public void testGetProbabilityOfSuccess() {

		assertEquals(0.5, BinomialSampleDistributionData.builder().build().getProbabilityOfSuccess());

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7429574140058073324L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);
			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			assertEquals(probabilityOfSuccess, binomialSampleDistributionData.getProbabilityOfSuccess());
		}
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.class, name = "getTrials", args = {})
	public void testGetTrials() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2070812167102585375L);
		assertEquals(0, BinomialSampleDistributionData.builder().build().getTrials());
		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);
			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials).build();
			assertEquals(trials, binomialSampleDistributionData.getTrials());
		}
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.class, name = "hashCode", args = {})
	public void testHashCode() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4683267966152824984L);

		// equal object have equal hash codes
		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			BinomialSampleDistributionData binomialSampleDistributionData1 = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();

			BinomialSampleDistributionData binomialSampleDistributionData2 = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();

			assertEquals(binomialSampleDistributionData1, binomialSampleDistributionData2);

			assertEquals(binomialSampleDistributionData1.hashCode(), binomialSampleDistributionData2.hashCode());

		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();

			int expectedHashCode = binomialSampleDistributionData.hashCode();

			for (int j = 0; j < 10; j++) {
				assertEquals(expectedHashCode, binomialSampleDistributionData.hashCode());
			}

		}
		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();

			hashCodes.add(binomialSampleDistributionData.hashCode());
		}

		assertEquals(100, hashCodes.size());
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.class, name = "toString", args = {})
	public void testToString() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(462056814875480878L);

		for (int i = 0; i < 30; i++) {
			double probabilityOfSuccess = randomGenerator.nextDouble();
			int trials = randomGenerator.nextInt(10);

			BinomialSampleDistributionData binomialSampleDistributionData = BinomialSampleDistributionData.builder()
					.setProbabilityOfSuccess(probabilityOfSuccess)//
					.setTrials(trials)//
					.build();

			String actual = binomialSampleDistributionData.toString();
			String expected = "BinomialSampleDistributionData [data=Data [trials=" + trials
					+ ", probabilityOfSuccess=" + probabilityOfSuccess + "]]";
			assertEquals(expected, actual);
		}
	}
	
	
	private BinomialSampleDistributionData getRandomBinomialSampleDistributionData(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		return BinomialSampleDistributionData.builder().setProbabilityOfSuccess(randomGenerator.nextDouble())
				.setTrials(randomGenerator.nextInt(10)).build();
	}

	@Test
	@UnitTestMethod(target = BinomialSampleDistributionData.class, name = "getSampleDistribution", args = {
			RandomGenerator.class })
	public void testGetSampleDistribution() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3981519327237114073L);
		/*
		 * If the SampleDistribution returned from getSampleDistribution will in turn
		 * return the data used to create it, we can assume that the generated sample
		 * distribution was corrected.
		 */
		for (int i = 0; i < 30; i++) {
			BinomialSampleDistributionData binomialSampleDistributionData1 = getRandomBinomialSampleDistributionData(
					randomGenerator.nextLong());
			assertNotNull(binomialSampleDistributionData1);
			BinomialSampleDistribution binomialSampleDistribution = binomialSampleDistributionData1
					.getSampleDistribution(randomGenerator);
			assertNotNull(binomialSampleDistribution);
			BinomialSampleDistributionData binomialSampleDistributionData2 = binomialSampleDistribution.getSampleDistributionData();
			assertNotNull(binomialSampleDistributionData2);
			assertEquals(binomialSampleDistributionData1, binomialSampleDistributionData2);
		}
	}


}
