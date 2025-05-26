package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_RoundingRule {

	@Test
	@UnitTestMethod(target = RoundingRule.class, name = "isIntValue", args = { double.class })
	public void testIsIntValue() {

		// zero
		assertTrue(RoundingRule.isIntValue(0.0));
		assertTrue(RoundingRule.isIntValue(-0.0));

		// whole numbers
		assertTrue(RoundingRule.isIntValue(-45));
		assertTrue(RoundingRule.isIntValue(-1));
		assertTrue(RoundingRule.isIntValue(1));
		assertTrue(RoundingRule.isIntValue(28));

		// non-whole numbers
		assertFalse(RoundingRule.isIntValue(-122.7));
		assertFalse(RoundingRule.isIntValue(-45.07));
		assertFalse(RoundingRule.isIntValue(13.354));
		assertFalse(RoundingRule.isIntValue(12342343.23));

		// extreme values
		assertTrue(RoundingRule.isIntValue(Integer.MAX_VALUE));
		assertFalse(RoundingRule.isIntValue((double) Integer.MAX_VALUE + 1.0));
		assertTrue(RoundingRule.isIntValue(Integer.MIN_VALUE));
		assertFalse(RoundingRule.isIntValue((double) Integer.MIN_VALUE - 1.0));
		assertFalse(RoundingRule.isIntValue(Double.NaN));
		assertFalse(RoundingRule.isIntValue(Double.POSITIVE_INFINITY));
		assertFalse(RoundingRule.isIntValue(Double.NEGATIVE_INFINITY));

	}

	@Test
	@UnitTestMethod(target = RoundingRule.class, name = "isLongValue", args = { double.class })
	public void testIsLongValue() {

		// zero
		assertTrue(RoundingRule.isLongValue(0.0));
		assertTrue(RoundingRule.isLongValue(-0.0));

		// whole numbers
		assertTrue(RoundingRule.isLongValue(-45));
		assertTrue(RoundingRule.isLongValue(-1));
		assertTrue(RoundingRule.isLongValue(1));
		assertTrue(RoundingRule.isLongValue(28));

		// non-whole numbers
		assertFalse(RoundingRule.isLongValue(-122.7));
		assertFalse(RoundingRule.isLongValue(-45.07));
		assertFalse(RoundingRule.isLongValue(13.354));
		assertFalse(RoundingRule.isLongValue(12342343.23));

		/*
		 * extreme values -- note that adding a small value to a large double will not
		 * necessarily raise the value, so we must use a larger value to properly move
		 * the double above or belong max and min long.
		 */
		assertTrue(RoundingRule.isLongValue(Long.MAX_VALUE));
		assertFalse(RoundingRule.isLongValue((double) Long.MAX_VALUE + 10_000.0));
		assertTrue(RoundingRule.isLongValue(Long.MIN_VALUE));
		assertFalse(RoundingRule.isLongValue((double) Long.MIN_VALUE - 10_000.0));

	}

	@Test
	@UnitTestMethod(target = RoundingRule.class, name = "round", args = { double.class, RoundingRule.class })
	public void testRound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4829795492799583108L);

		/*
		 * All rounding rules when applied to whole numbers should return the input.
		 * Note that large values (x: abs(x)>=2^52) are already whole numbers.
		 */
		for (int i = -10; i < 10; i++) {
			double value = i;
			assertEquals(value, RoundingRule.round(value, RoundingRule.UP));
			assertEquals(value, RoundingRule.round(value, RoundingRule.DOWN));
			assertEquals(value, RoundingRule.round(value, RoundingRule.AWAY_FROM_ZERO));
			assertEquals(value, RoundingRule.round(value, RoundingRule.TOWARD_ZERO));
			assertEquals(value, RoundingRule.round(value, RoundingRule.NEAREST));
		}

		/* Rounding rules when applied to non-whole numbers. */
		for (int i = -10; i < 10; i++) {
			double value = i + 0.9 * randomGenerator.nextDouble() + 0.1;
			assertEquals(i + 1, RoundingRule.round(value, RoundingRule.UP));
			assertEquals(i, RoundingRule.round(value, RoundingRule.DOWN));
			assertEquals(i < 0 ? i : i + 1, RoundingRule.round(value, RoundingRule.AWAY_FROM_ZERO));
			assertEquals(i < 0 ? i + 1 : i, RoundingRule.round(value, RoundingRule.TOWARD_ZERO));
			assertEquals(FastMath.floor(value + 0.5), RoundingRule.round(value, RoundingRule.NEAREST));
		}

	}

}
