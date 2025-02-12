package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
import gov.hhs.aspr.ms.util.wrappers.MutableInteger;

public class AT_Quantity {

	/*
	 * Creates a randomized ComposedUnit using several time, length and mass units
	 * to powers in [-5,-1]U[1,5].
	 */
	private ComposedUnit getRandomComposedUnit(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		Measure MASS = new Measure("mass");

		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
		BaseUnit HOUR = new BaseUnit(MINUTE, 60, "hour", "h");
		List<BaseUnit> timeUnits = new ArrayList<>();
		timeUnits.add(SECOND);
		timeUnits.add(MINUTE);
		timeUnits.add(HOUR);

		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit CM = new BaseUnit(METER, 0.01, "centimeter", "cm");
		BaseUnit INCH = new BaseUnit(CM, 2.54, "inch", "in");
		BaseUnit FOOT = new BaseUnit(INCH, 12, "foot", "ft");
		BaseUnit MILE = new BaseUnit(FOOT, 5280, "mile", "mi");
		List<BaseUnit> distanceUnits = new ArrayList<>();
		distanceUnits.add(METER);
		distanceUnits.add(CM);
		distanceUnits.add(INCH);
		distanceUnits.add(FOOT);
		distanceUnits.add(MILE);

		BaseUnit KILOGRAM = new BaseUnit(MASS, "kilogram", "kg");
		BaseUnit POUND = new BaseUnit(KILOGRAM, 0.45359237, "pound", "lb");
		BaseUnit OUNCE = new BaseUnit(POUND, 1.0 / 16, "ounce", "oz");
		List<BaseUnit> massUnits = new ArrayList<>();
		massUnits.add(KILOGRAM);
		massUnits.add(POUND);
		massUnits.add(OUNCE);

		BaseUnit timeUnit = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		BaseUnit distanceUnit = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		BaseUnit massUnit = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		int timePower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);
		int distancePower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);
		int massPower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);

		return ComposedUnit.builder()//
				.setBaseUnit(timeUnit, timePower)//
				.setBaseUnit(distanceUnit, distancePower)//
				.setBaseUnit(massUnit, massPower)//
				.build();
	}

	private int generateNonZeroPower(RandomGenerator randomGenerator) {
		int result = randomGenerator.nextInt(5) + 1;
		if (randomGenerator.nextBoolean()) {
			result = -result;
		}
		return result;
	}

	// Returns an array of three integers. The array will have 0, 1 or 2 zero
	// entries in even distribution. The non-zero values will be bounded to [-5,5]
	private int[] selectPowers(RandomGenerator randomGenerator) {

		int[] result = new int[3];

		int numberOfZeroEntries = randomGenerator.nextInt(3);
		switch (numberOfZeroEntries) {
		case 1:
			int excludedIndex = randomGenerator.nextInt(3);
			for (int i = 0; i < 3; i++) {
				if (i != excludedIndex) {
					result[i] = generateNonZeroPower(randomGenerator);
				}
			}
			break;
		case 2:
			int includedIndex = randomGenerator.nextInt(3);
			result[includedIndex] = generateNonZeroPower(randomGenerator);
			break;
		default:// 0
			for (int i = 0; i < 3; i++) {
				result[i] = generateNonZeroPower(randomGenerator);
			}
			break;
		}

		return result;
	}

	/*
	 * Creates a randomized Quantity using several time, length and mass units to
	 * powers in [-5,-1]U[1,5], a random value. The resultant quantities will have
	 * even chances of having 1, 2 or 3 measures.
	 */
	private Quantity getRandomQuantity(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		Measure MASS = new Measure("mass");

		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
		BaseUnit HOUR = new BaseUnit(MINUTE, 60, "hour", "h");
		List<BaseUnit> timeUnits = new ArrayList<>();
		timeUnits.add(SECOND);
		timeUnits.add(MINUTE);
		timeUnits.add(HOUR);

		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit CM = new BaseUnit(METER, 0.01, "centimeter", "cm");
		BaseUnit INCH = new BaseUnit(CM, 2.54, "inch", "in");
		BaseUnit FOOT = new BaseUnit(INCH, 12, "foot", "ft");
		BaseUnit MILE = new BaseUnit(FOOT, 5280, "mile", "mi");
		List<BaseUnit> distanceUnits = new ArrayList<>();
		distanceUnits.add(METER);
		distanceUnits.add(CM);
		distanceUnits.add(INCH);
		distanceUnits.add(FOOT);
		distanceUnits.add(MILE);

		BaseUnit KILOGRAM = new BaseUnit(MASS, "kilogram", "kg");
		BaseUnit POUND = new BaseUnit(KILOGRAM, 0.45359237, "pound", "lb");
		BaseUnit OUNCE = new BaseUnit(POUND, 1.0 / 16, "ounce", "oz");
		List<BaseUnit> massUnits = new ArrayList<>();
		massUnits.add(KILOGRAM);
		massUnits.add(POUND);
		massUnits.add(OUNCE);

		BaseUnit timeUnit = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		BaseUnit distanceUnit = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		BaseUnit massUnit = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		int[] selectedPowers = selectPowers(randomGenerator);

		int timePower = selectedPowers[0];
		int distancePower = selectedPowers[1];
		int massPower = selectedPowers[2];

		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(timeUnit, timePower)//
				.setBaseUnit(distanceUnit, distancePower)//
				.setBaseUnit(massUnit, massPower)//
				.build();

		double value = randomGenerator.nextDouble();
		return new Quantity(composedUnit, value);
	}

	/*
	 * Creates two randomized quantities using several time, length and mass units
	 * to powers in [-5,-1]U[1,5]. They will have random positive values(bounded to
	 * the interval [0.0001,1), but will agree on measures and powers.
	 */
	private Pair<Quantity, Quantity> getRandomCompatibleQuanties(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		Measure MASS = new Measure("mass");

		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
		BaseUnit HOUR = new BaseUnit(MINUTE, 60, "hour", "h");
		List<BaseUnit> timeUnits = new ArrayList<>();
		timeUnits.add(SECOND);
		timeUnits.add(MINUTE);
		timeUnits.add(HOUR);

		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit CM = new BaseUnit(METER, 0.01, "centimeter", "cm");
		BaseUnit INCH = new BaseUnit(CM, 2.54, "inch", "in");
		BaseUnit FOOT = new BaseUnit(INCH, 12, "foot", "ft");
		BaseUnit MILE = new BaseUnit(FOOT, 5280, "mile", "mi");
		List<BaseUnit> distanceUnits = new ArrayList<>();
		distanceUnits.add(METER);
		distanceUnits.add(CM);
		distanceUnits.add(INCH);
		distanceUnits.add(FOOT);
		distanceUnits.add(MILE);

		BaseUnit KILOGRAM = new BaseUnit(MASS, "kilogram", "kg");
		BaseUnit POUND = new BaseUnit(KILOGRAM, 0.45359237, "pound", "lb");
		BaseUnit OUNCE = new BaseUnit(POUND, 1.0 / 16, "ounce", "oz");
		List<BaseUnit> massUnits = new ArrayList<>();
		massUnits.add(KILOGRAM);
		massUnits.add(POUND);
		massUnits.add(OUNCE);

		BaseUnit timeUnit1 = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		BaseUnit distanceUnit1 = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		BaseUnit massUnit1 = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		BaseUnit timeUnit2 = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		BaseUnit distanceUnit2 = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		BaseUnit massUnit2 = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		int[] selectedPowers = selectPowers(randomGenerator);

		int timePower = selectedPowers[0];
		int distancePower = selectedPowers[1];
		int massPower = selectedPowers[2];

		ComposedUnit composedUnit1 = ComposedUnit.builder()//
				.setBaseUnit(timeUnit1, timePower)//
				.setBaseUnit(distanceUnit1, distancePower)//
				.setBaseUnit(massUnit1, massPower)//
				.build();

		ComposedUnit composedUnit2 = ComposedUnit.builder()//
				.setBaseUnit(timeUnit2, timePower)//
				.setBaseUnit(distanceUnit2, distancePower)//
				.setBaseUnit(massUnit2, massPower)//
				.build();

		double value1 = randomGenerator.nextDouble() * 0.9999 + 0.0001;
		double value2 = randomGenerator.nextDouble() * 0.9999 + 0.0001;

		Quantity quantity1 = new Quantity(composedUnit1, value1);
		Quantity quantity2 = new Quantity(composedUnit2, value2);

		return new Pair<>(quantity1, quantity2);
	}

	@Test
	@UnitTestConstructor(target = Quantity.class, args = { ComposedUnit.class, double.class })
	public void testQuantity_Composite() {
		// postcontition tests covered by the other tests

		// precondition test: if the composed unit is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			ComposedUnit composedUnit = null;
			new Quantity(composedUnit, 1.2);
		});
		assertEquals(MeasuresError.NULL_COMPOSITE, contractException.getErrorType());
	}

	@Test
	@UnitTestConstructor(target = Quantity.class, args = { BaseUnit.class, double.class })
	public void testQuantity_Unit() {
		// postcontition tests covered by the other tests

		// precondition test: if the base unit is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			BaseUnit baseUnit = null;
			new Quantity(baseUnit, 1.2);
		});
		assertEquals(MeasuresError.NULL_UNIT, contractException.getErrorType());
	}

	/*
	 * Returns a new quantity by altering one of the original quantity's powers.
	 * Assumes that the given quantity has at least one power.
	 */
	private Quantity getQuantityWithAlteredPower(Quantity quantity, long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);

		ComposedUnit composedUnit = quantity.getComposedUnit();
		List<BaseUnit> baseUnits = composedUnit.getBaseUnits();
		BaseUnit alteredBaseUnit = baseUnits.get(randomGenerator.nextInt(baseUnits.size()));
		int alteredPower = composedUnit.getPower(alteredBaseUnit.getMeasure()).get() + 1;

		ComposedUnit.Builder builder = ComposedUnit.builder();
		for (BaseUnit baseUnit : baseUnits) {
			if (baseUnit != alteredBaseUnit) {
				int power = composedUnit.getPower(baseUnit.getMeasure()).get();
				builder.setBaseUnit(baseUnit, power);
			} else {
				builder.setBaseUnit(alteredBaseUnit, alteredPower);
			}
		}
		ComposedUnit newComposedUnit = builder.build();
		return new Quantity(newComposedUnit, quantity.getValue());
	}

	private boolean equalWithinTolerance(double expected, double actual) {
		return FastMath.abs(1 - actual / expected) <= 1e-12;
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "add", args = { Quantity.class })
	public void testAdd() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8658754499063356339L);
		for (int i = 0; i < 30; i++) {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();
			Quantity q3 = q1.add(q2);

			// the resultant quantity should have the same units as the first quantity
			assertEquals(q1.getComposedUnit(), q3.getComposedUnit());

			double expectedValue = q1.getValue()
					+ q2.getValue() * q2.getComposedUnit().getValue() / q1.getComposedUnit().getValue();
			double actualValue = q3.getValue();

			assertTrue(equalWithinTolerance(expectedValue, actualValue));
		}

		// precondition test: if the quantity is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			quantity.add(null);
		});
		assertEquals(MeasuresError.NULL_QUANTITY, contractException.getErrorType());

		// precondition test: if the quantity does not have equal powers over it
		// measures
		contractException = assertThrows(ContractException.class, () -> {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();
			Quantity q3 = getQuantityWithAlteredPower(q2, randomGenerator.nextLong());
			q1.add(q3);
		});
		assertEquals(MeasuresError.INCOMPATIBLE_MEASURES, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "div", args = { Quantity.class })
	public void testDiv() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1487645678038623637L);
		for (int i = 0; i < 30; i++) {
			Quantity q1 = getRandomQuantity(randomGenerator.nextLong());
			Quantity q2 = getRandomQuantity(randomGenerator.nextLong());
			Quantity q3 = q1.div(q2);

			// the resultant composed unit should primarily match q1 and secondarily match
			// q2
			Map<Measure, Pair<BaseUnit, MutableInteger>> measureMap = new LinkedHashMap<>();
			ComposedUnit c1 = q1.getComposedUnit();
			for (BaseUnit baseUnit : c1.getBaseUnits()) {
				int power = c1.getPower(baseUnit.getMeasure()).get();
				measureMap.put(baseUnit.getMeasure(), new Pair<>(baseUnit, new MutableInteger(power)));
			}
			ComposedUnit c2 = q2.getComposedUnit();
			for (BaseUnit baseUnit : c2.getBaseUnits()) {
				int power = c2.getPower(baseUnit.getMeasure()).get();
				Pair<BaseUnit, MutableInteger> pair = measureMap.get(baseUnit.getMeasure());
				if (pair != null) {
					pair.getSecond().decrement(power);
				} else {
					measureMap.put(baseUnit.getMeasure(), new Pair<>(baseUnit, new MutableInteger(-power)));
				}
			}

			ComposedUnit.Builder builder = ComposedUnit.builder();
			for (Pair<BaseUnit, MutableInteger> pair : measureMap.values()) {
				BaseUnit baseUnit = pair.getFirst();
				MutableInteger power = pair.getSecond();
				builder.setBaseUnit(baseUnit, power.getValue());
			}
			ComposedUnit c3 = builder.build();
			assertEquals(c3, q3.getComposedUnit());

			double compositeFactor = c1.getValue() / (c3.getValue() * c2.getValue());
			double expectedValue = q1.getValue() / q2.getValue() * compositeFactor;

			double actualValue = q3.getValue();

			/*
			 * By calculating the expected value exactly how it is implemented in the div
			 * method, we can avoid precision issues in the comparison.
			 */
			assertEquals(expectedValue, actualValue);

		}

		// precondition test: if the quantity is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			quantity.div(null);
		});
		assertEquals(MeasuresError.NULL_QUANTITY, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "e", args = { Quantity.class, double.class })
	public void testE() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2589875454956650034L);
		for (int i = 0; i < 100; i++) {

			/*
			 * We know that getRandomCompatibleQuanties() returns quantities with positive
			 * values, so we don't have to consider the cases where scaling a zero would
			 * have no effect.
			 */
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();

			// alter the value of q2 so that it will be numerically very close to q1
			double v = q1.getValue() * q1.getComposedUnit().getValue() / q2.getComposedUnit().getValue();
			q2 = q2.setValue(v);

			assertTrue(q1.e(q2, 1e-12));

			Quantity q3 = q2.scale(1 + 1e-10);
			assertFalse(q1.e(q3, 1e-12));

			Quantity q4 = q2.scale(1 - 1e-10);
			assertFalse(q1.e(q4, 1e-12));
		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "equals", args = { Object.class })
	public void testEquals() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6449542055211533914L);

		/**
		 * Two quantities are equal if and only if they have the equal composed units
		 * and equal values. For example, the quantities q1 = new Quantity(FOOT,1) and
		 * q2 = new Quantity(INCH,12) are not equal even though they are equivalent.
		 */
		Measure LENGTH = new Measure("length");
		Measure TIME = new Measure("time");
		BaseUnit INCH = new BaseUnit(LENGTH, "foot", "ft");
		BaseUnit FOOT = new BaseUnit(INCH, 12, "foot", "ft");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "sec");
		BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");

		// these are identical and so should be equal
		Quantity q1 = new Quantity(MINUTE, 37.5);
		Quantity q2 = new Quantity(MINUTE, 37.5);
		assertTrue(q1.equals(q2));

		// these represent the same length and are equivalent but not equal
		q1 = new Quantity(FOOT, 1);
		q2 = new Quantity(INCH, 12);
		assertFalse(q1.equals(q2));

		// not equal null
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			assertFalse(quantity.equals(null));
		}

		// not equal another type
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			assertFalse(quantity.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			assertTrue(quantity.equals(quantity));
		}

		// symmetric, transitive and stable
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			Quantity quantity1 = getRandomQuantity(seed);
			Quantity quantity2 = getRandomQuantity(seed);
			for (int j = 0; j < 5; j++) {
				assertTrue(quantity1.equals(quantity2));
				assertTrue(quantity2.equals(quantity1));
			}
		}

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "getComposite", args = {})
	public void testGetComposite() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8887201502112472056L);
		for (int i = 0; i < 30; i++) {
			ComposedUnit expectedComposedUnit = getRandomComposedUnit(randomGenerator.nextLong());
			Quantity quantity = new Quantity(expectedComposedUnit, randomGenerator.nextDouble());
			ComposedUnit actualComposedUnit = quantity.getComposedUnit();
			assertEquals(expectedComposedUnit, actualComposedUnit);
		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "getValue", args = {})
	public void testGetValue() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1368153997671183276L);

		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();

		for (int i = 0; i < 100; i++) {
			double expectedValue = randomGenerator.nextDouble();
			Quantity quantity = new Quantity(SECOND, expectedValue);
			double actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);

			quantity = new Quantity(MPSS, expectedValue);
			actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "gt", args = { Quantity.class })
	public void testGt() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5292505696005214354L);
		for (int i = 0; i < 100; i++) {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();

			double v1 = q1.getValue() * q1.getComposedUnit().getValue();
			double v2 = q2.getValue() * q2.getComposedUnit().getValue();
			boolean expectedValue = v1 > v2;
			boolean actualValue = q1.gt(q2);
			assertEquals(expectedValue, actualValue);

			assertFalse(q1.gt(q1));

		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "gte", args = { Quantity.class })
	public void testGte() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7838228582280275499L);
		for (int i = 0; i < 100; i++) {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();

			double v1 = q1.getValue() * q1.getComposedUnit().getValue();
			double v2 = q2.getValue() * q2.getComposedUnit().getValue();
			boolean expectedValue = v1 >= v2;
			boolean actualValue = q1.gte(q2);
			assertEquals(expectedValue, actualValue);

			assertTrue(q1.gte(q1));

		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "lt", args = { Quantity.class })
	public void testLt() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2589875454956650034L);
		for (int i = 0; i < 100; i++) {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();

			double v1 = q1.getValue() * q1.getComposedUnit().getValue();
			double v2 = q2.getValue() * q2.getComposedUnit().getValue();
			boolean expectedValue = v1 < v2;
			boolean actualValue = q1.lt(q2);
			assertEquals(expectedValue, actualValue);

			assertFalse(q1.lt(q1));

		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "lte", args = { Quantity.class })
	public void testLte() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2373098157771611180L);
		for (int i = 0; i < 100; i++) {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();

			double v1 = q1.getValue() * q1.getComposedUnit().getValue();
			double v2 = q2.getValue() * q2.getComposedUnit().getValue();
			boolean expectedValue = v1 <= v2;
			boolean actualValue = q1.lte(q2);
			assertEquals(expectedValue, actualValue);

			assertTrue(q1.lte(q1));

		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isCompatible", args = { Quantity.class })
	public void testIsCompatible() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7864129897851447411L);

		// we know that the getRandomCompatibleQuanties() returns pairs of compatible
		// quantities
		for (int i = 0; i < 30; i++) {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();
			assertTrue(q1.isCompatible(q2));
			Quantity q3 = getQuantityWithAlteredPower(q2, randomGenerator.nextLong());
			assertFalse(q1.isCompatible(q3));
		}

		// precondition test: if the quantity is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			quantity.isCompatible(null);
		});
		assertEquals(MeasuresError.NULL_QUANTITY, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "mult", args = { Quantity.class })
	public void testMult() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8658754499063356339L);
		for (int i = 0; i < 30; i++) {
			Quantity q1 = getRandomQuantity(randomGenerator.nextLong());
			Quantity q2 = getRandomQuantity(randomGenerator.nextLong());
			Quantity q3 = q1.mult(q2);

			// the resultant composed unit should primarily match q1 and secondarily match
			// q2
			Map<Measure, Pair<BaseUnit, MutableInteger>> measureMap = new LinkedHashMap<>();
			ComposedUnit c1 = q1.getComposedUnit();
			for (BaseUnit baseUnit : c1.getBaseUnits()) {
				int power = c1.getPower(baseUnit.getMeasure()).get();
				measureMap.put(baseUnit.getMeasure(), new Pair<>(baseUnit, new MutableInteger(power)));
			}
			ComposedUnit c2 = q2.getComposedUnit();
			for (BaseUnit baseUnit : c2.getBaseUnits()) {
				int power = c2.getPower(baseUnit.getMeasure()).get();
				Pair<BaseUnit, MutableInteger> pair = measureMap.get(baseUnit.getMeasure());
				if (pair != null) {
					pair.getSecond().increment(power);
				} else {
					measureMap.put(baseUnit.getMeasure(), new Pair<>(baseUnit, new MutableInteger(power)));
				}
			}

			ComposedUnit.Builder builder = ComposedUnit.builder();
			for (Pair<BaseUnit, MutableInteger> pair : measureMap.values()) {
				BaseUnit baseUnit = pair.getFirst();
				MutableInteger power = pair.getSecond();
				builder.setBaseUnit(baseUnit, power.getValue());
			}
			ComposedUnit c3 = builder.build();
			assertEquals(c3, q3.getComposedUnit());

			double compositeFactor = (c1.getValue() * c2.getValue()) / c3.getValue();
			double expectedValue = q1.getValue() * q2.getValue() * compositeFactor;

			double actualValue = q3.getValue();

			/*
			 * By calculating the expected value exactly how it is implemented in the
			 * multiply method, we can avoid precision issues in the comparison.
			 */
			assertEquals(expectedValue, actualValue);

		}

		// precondition test: if the quantity is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			quantity.mult(null);
		});
		assertEquals(MeasuresError.NULL_QUANTITY, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "sub", args = { Quantity.class })
	public void testSub() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5445545737245805697L);
		for (int i = 0; i < 30; i++) {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();
			Quantity q3 = q1.sub(q2);

			// the resultant quantity should have the same units as the first quantity
			assertEquals(q1.getComposedUnit(), q3.getComposedUnit());

			double expectedValue = q1.getValue()
					- q2.getValue() * q2.getComposedUnit().getValue() / q1.getComposedUnit().getValue();
			double actualValue = q3.getValue();

			assertTrue(equalWithinTolerance(expectedValue, actualValue));
		}

		// precondition test: if the quantity is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			quantity.add(null);
		});
		assertEquals(MeasuresError.NULL_QUANTITY, contractException.getErrorType());

		// precondition test: if the quantity does not have equal powers over it
		// measures
		contractException = assertThrows(ContractException.class, () -> {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();
			Quantity q3 = getQuantityWithAlteredPower(q2, randomGenerator.nextLong());
			q1.sub(q3);
		});
		assertEquals(MeasuresError.INCOMPATIBLE_MEASURES, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6449542055211533914L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			Quantity quantity1 = getRandomQuantity(seed);
			Quantity quantity2 = getRandomQuantity(seed);
			assertEquals(quantity1, quantity2);
			assertEquals(quantity1.hashCode(), quantity2.hashCode());
		}

		// hash codes are stable
		for (int i = 0; i < 30; i++) {

			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			int expectedHashCode = quantity.hashCode();
			for (int j = 0; j < 5; j++) {
				assertEquals(expectedHashCode, quantity.hashCode());
			}
		}

		// hash codes are reasonable distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			hashCodes.add(quantity.hashCode());
		}
		assertEquals(100, hashCodes.size());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "invert", args = {})
	public void testInvert() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5331525545262629742L);
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			Quantity invertedQuantity = quantity.invert();
			double expectedValue = 1.0 / quantity.getValue();
			double actualValue = invertedQuantity.getValue();
			assertEquals(expectedValue, actualValue);

			ComposedUnit composedUnit = quantity.getComposedUnit();
			ComposedUnit.Builder builder = ComposedUnit.builder();
			for (BaseUnit baseUnit : composedUnit.getBaseUnits()) {
				builder.setBaseUnit(baseUnit, -composedUnit.getPower(baseUnit.getMeasure()).get());
			}
			ComposedUnit expectedComposedUnit = builder.build();
			ComposedUnit actualComposedUnit = invertedQuantity.getComposedUnit();
			assertEquals(expectedComposedUnit, actualComposedUnit);
		}

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isFinite", args = {})
	public void testIsFinite() {

		Measure TIME = new Measure("time");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");

		Quantity quantity = new Quantity(SECOND, 1);
		assertTrue(quantity.isFinite());

		quantity = new Quantity(SECOND, 0);
		assertTrue(quantity.isFinite());

		quantity = new Quantity(SECOND, -1);
		assertTrue(quantity.isFinite());

		quantity = new Quantity(SECOND, Double.POSITIVE_INFINITY);
		assertFalse(quantity.isFinite());

		quantity = new Quantity(SECOND, Double.NEGATIVE_INFINITY);
		assertFalse(quantity.isFinite());

		quantity = new Quantity(SECOND, Double.NaN);
		assertFalse(quantity.isFinite());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isNegative", args = {})
	public void testIsNegative() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();

		Quantity quantity = new Quantity(SECOND, 0.001);
		assertFalse(quantity.isNegative());

		quantity = new Quantity(SECOND, 0);
		assertFalse(quantity.isNegative());

		quantity = new Quantity(SECOND, -0.001);
		assertTrue(quantity.isNegative());

		quantity = new Quantity(MPSS, 0.001);
		assertFalse(quantity.isNegative());

		quantity = new Quantity(MPSS, 0);
		assertFalse(quantity.isNegative());

		quantity = new Quantity(MPSS, -0.001);
		assertTrue(quantity.isNegative());

		quantity = new Quantity(MPSS, Double.NaN);
		assertFalse(quantity.isNegative());

		quantity = new Quantity(MPSS, Double.NEGATIVE_INFINITY);
		assertTrue(quantity.isNegative());

		quantity = new Quantity(MPSS, Double.POSITIVE_INFINITY);
		assertFalse(quantity.isNegative());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isNonNegative", args = {})
	public void testIsNonNegative() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();

		Quantity quantity = new Quantity(SECOND, 0.001);
		assertTrue(quantity.isNonNegative());

		quantity = new Quantity(SECOND, 0);
		assertTrue(quantity.isNonNegative());

		quantity = new Quantity(SECOND, -0.001);
		assertFalse(quantity.isNonNegative());

		quantity = new Quantity(MPSS, 0.001);
		assertTrue(quantity.isNonNegative());

		quantity = new Quantity(MPSS, 0);
		assertTrue(quantity.isNonNegative());

		quantity = new Quantity(MPSS, -0.001);
		assertFalse(quantity.isNonNegative());

		quantity = new Quantity(MPSS, Double.NaN);
		assertFalse(quantity.isNonNegative());

		quantity = new Quantity(MPSS, Double.NEGATIVE_INFINITY);
		assertFalse(quantity.isNonNegative());

		quantity = new Quantity(MPSS, Double.POSITIVE_INFINITY);
		assertTrue(quantity.isNonNegative());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isNonPositive", args = {})
	public void testIsNonPositive() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();

		Quantity quantity = new Quantity(SECOND, 0.001);
		assertFalse(quantity.isNonPositive());

		quantity = new Quantity(SECOND, 0);
		assertTrue(quantity.isNonPositive());

		quantity = new Quantity(SECOND, -0.001);
		assertTrue(quantity.isNonPositive());

		quantity = new Quantity(MPSS, 0.001);
		assertFalse(quantity.isNonPositive());

		quantity = new Quantity(MPSS, 0);
		assertTrue(quantity.isNonPositive());

		quantity = new Quantity(MPSS, -0.001);
		assertTrue(quantity.isNonPositive());

		quantity = new Quantity(MPSS, Double.NaN);
		assertFalse(quantity.isNonPositive());

		quantity = new Quantity(MPSS, Double.NEGATIVE_INFINITY);
		assertTrue(quantity.isNonPositive());

		quantity = new Quantity(MPSS, Double.POSITIVE_INFINITY);
		assertFalse(quantity.isNonPositive());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isPositive", args = {})
	public void testIsPositive() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();

		Quantity quantity = new Quantity(SECOND, 0.001);
		assertTrue(quantity.isPositive());

		quantity = new Quantity(SECOND, 0);
		assertFalse(quantity.isPositive());

		quantity = new Quantity(SECOND, -0.001);
		assertFalse(quantity.isPositive());

		quantity = new Quantity(MPSS, 0.001);
		assertTrue(quantity.isPositive());

		quantity = new Quantity(MPSS, 0);
		assertFalse(quantity.isPositive());

		quantity = new Quantity(MPSS, -0.001);
		assertFalse(quantity.isPositive());

		quantity = new Quantity(MPSS, Double.NaN);
		assertFalse(quantity.isPositive());

		quantity = new Quantity(MPSS, Double.NEGATIVE_INFINITY);
		assertFalse(quantity.isPositive());

		quantity = new Quantity(MPSS, Double.POSITIVE_INFINITY);
		assertTrue(quantity.isPositive());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isMeasureLess", args = {})
	public void testIsMeasureLess() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");

		Quantity quantity = new Quantity(SECOND, 1);
		assertFalse(quantity.isMeasureLess());

		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();
		quantity = new Quantity(MPSS, 12.6);
		assertFalse(quantity.isMeasureLess());

		quantity = new Quantity(ComposedUnit.builder().build(), 12.6);
		assertTrue(quantity.isMeasureLess());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isZero", args = {})
	public void testIsZero() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();

		Quantity quantity = new Quantity(SECOND, 0.001);
		assertFalse(quantity.isZero());

		quantity = new Quantity(SECOND, 0);
		assertTrue(quantity.isZero());

		quantity = new Quantity(SECOND, -0);
		assertTrue(quantity.isZero());

		quantity = new Quantity(SECOND, -0.001);
		assertFalse(quantity.isZero());

		quantity = new Quantity(MPSS, Double.NaN);
		assertFalse(quantity.isZero());

		quantity = new Quantity(MPSS, Double.NEGATIVE_INFINITY);
		assertFalse(quantity.isZero());

		quantity = new Quantity(MPSS, Double.POSITIVE_INFINITY);
		assertFalse(quantity.isZero());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "pow", args = { int.class })
	public void testPow() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(9003235440386204107L);
		for (int i = 0; i < 100; i++) {
			Quantity q1 = getRandomQuantity(randomGenerator.nextLong());
			for (int power = 0; power < 5; power++) {
				Quantity q2 = q1.pow(power);
				ComposedUnit composedUnit = q1.getComposedUnit();
				ComposedUnit.Builder builder = ComposedUnit.builder();
				for (BaseUnit baseUnit : composedUnit.getBaseUnits()) {
					int p = composedUnit.getPower(baseUnit.getMeasure()).get();
					builder.setBaseUnit(baseUnit, power * p);
				}
				ComposedUnit expectedComposedUnit = builder.build();
				ComposedUnit actualComposedUnit = q2.getComposedUnit();
				assertEquals(expectedComposedUnit, actualComposedUnit);

				double expectedValue = FastMath.pow(q1.getValue(), power);
				double actualValue = q2.getValue();
				assertEquals(expectedValue, actualValue);
			}
		}

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "rebase", args = { ComposedUnit.class })
	public void testRebase() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6431753317939915457L);
		for (int i = 0; i < 100; i++) {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			ComposedUnit composedUnit = pair.getSecond().getComposedUnit();
			Quantity q3 = q1.rebase(composedUnit);

			assertEquals(composedUnit, q3.getComposedUnit());

			double compositeFactor = q1.getComposedUnit().getValue() / composedUnit.getValue();
			double expectedValue = q1.getValue() * compositeFactor;
			double actualValue = q3.getValue();

			assertEquals(expectedValue, actualValue);
		}

		// precondition test : if the composedUnit is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			ComposedUnit nullComposedUnit = null;
			quantity.rebase(nullComposedUnit);
		});
		assertEquals(MeasuresError.NULL_COMPOSITE, contractException.getErrorType());

		// precondition test : if the composedUnit is not compatible with this
		// quantity's composedUnit
		contractException = assertThrows(ContractException.class, () -> {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			ComposedUnit composedUnit = getQuantityWithAlteredPower(quantity, randomGenerator.nextLong())
					.getComposedUnit();
			quantity.rebase(composedUnit);
		});
		assertEquals(MeasuresError.INCOMPATIBLE_MEASURES, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "root", args = { int.class })
	public void testRoot() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(9003235440386204107L);
		for (int i = 0; i < 100; i++) {
			Quantity q1 = getRandomQuantity(randomGenerator.nextLong());
			for (int power = 1; power < 5; power++) {
				Quantity q2 = q1.pow(power);
				Quantity q3 = q2.root(power);

				ComposedUnit expectedComposedUnit = q1.getComposedUnit();
				ComposedUnit actualComposedUnit = q3.getComposedUnit();
				assertEquals(expectedComposedUnit, actualComposedUnit);

				double expectedValue = q1.getValue();
				double actualValue = q3.getValue();

				assertTrue(equalWithinTolerance(expectedValue, actualValue));
			}
		}

		// precondition test: if the root is not positive
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Measure LENGTH = new Measure("length");
			Measure TIME = new Measure("time");
			BaseUnit INCH = new BaseUnit(LENGTH, "foot", "ft");
			BaseUnit FOOT = new BaseUnit(INCH, 12, "foot", "ft");
			BaseUnit SECOND = new BaseUnit(TIME, "second", "sec");
			BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
			
			ComposedUnit FSPMS = ComposedUnit.builder().setBaseUnit(FOOT, 2).setBaseUnit(MINUTE, -2).build();
			Quantity q = new Quantity(FSPMS, 3.6);
			q.root(-1);
		});
		assertEquals(MeasuresError.NON_POSITIVE_ROOT, contractException.getErrorType());

		// precondition test: if any of the measure powers is not divisible by the root
		contractException = assertThrows(ContractException.class, () -> {
			Measure LENGTH = new Measure("length");
			Measure TIME = new Measure("time");
			BaseUnit INCH = new BaseUnit(LENGTH, "foot", "ft");
			BaseUnit FOOT = new BaseUnit(INCH, 12, "foot", "ft");
			BaseUnit SECOND = new BaseUnit(TIME, "second", "sec");
			BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
			
			ComposedUnit FSPMS = ComposedUnit.builder().setBaseUnit(FOOT, 2).setBaseUnit(MINUTE, -2).build();
			Quantity q = new Quantity(FSPMS, 3.6);
			q.root(3);

		});
		assertEquals(MeasuresError.POWER_IS_NOT_ROOT_COMPATIBLE, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "scale", args = { double.class })
	public void testScale() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7438551332551547296L);
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			double scalar = randomGenerator.nextDouble();
			double expectedValue = quantity.getValue() * scalar;
			quantity = quantity.scale(scalar);
			double actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "setValue", args = { double.class })
	public void testSetValue() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6407582171193740886L);

		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();

		for (int i = 0; i < 100; i++) {

			Quantity quantity = new Quantity(SECOND, randomGenerator.nextDouble());
			double expectedValue = randomGenerator.nextDouble();
			quantity = quantity.setValue(expectedValue);
			double actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);

			quantity = new Quantity(MPSS, randomGenerator.nextDouble());
			expectedValue = randomGenerator.nextDouble();
			quantity = quantity.setValue(expectedValue);
			actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "getLongName", args = {})
	public void testGetLongName() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");

		// we create two composed units, one with and one without names
		ComposedUnit MPSS1 = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();
		ComposedUnit MPSS2 = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).setShortName("acc")
				.setLongName("acceleration").build();

		Quantity quantity = new Quantity(MPSS1, 14.5);
		String actualValue = quantity.getLongName();
		String expectedValue = quantity.getValue() + " " + MPSS1.getLongName();
		assertEquals(expectedValue, actualValue);

		quantity = new Quantity(MPSS2, 14.5);
		actualValue = quantity.getLongName();
		expectedValue = quantity.getValue() + " " + MPSS2.getLongName();
		assertEquals(expectedValue, actualValue);

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "getLongLabel", args = {})
	public void testGetLongLabel() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");

		// we create two composed units, one with and one without names
		ComposedUnit MPSS1 = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();
		ComposedUnit MPSS2 = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).setShortName("acc")
				.setLongName("acceleration").build();

		Quantity quantity = new Quantity(MPSS1, 14.5);
		String actualValue = quantity.getLongLabel();
		String expectedValue = quantity.getValue() + " " + MPSS1.getLongLabel();
		assertEquals(expectedValue, actualValue);

		quantity = new Quantity(MPSS2, 14.5);
		actualValue = quantity.getLongLabel();
		expectedValue = quantity.getValue() + " " + MPSS2.getLongLabel();
		assertEquals(expectedValue, actualValue);

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "getShortLabel", args = {})
	public void testGetShortLabel() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");

		// we create two composed units, one with and one without names
		ComposedUnit MPSS1 = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();
		ComposedUnit MPSS2 = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).setShortName("acc")
				.setLongName("acceleration").build();

		Quantity quantity = new Quantity(MPSS1, 14.5);
		String actualValue = quantity.getShortLabel();
		String expectedValue = quantity.getValue() + " " + MPSS1.getShortLabel();
		assertEquals(expectedValue, actualValue);

		quantity = new Quantity(MPSS2, 14.5);
		actualValue = quantity.getShortLabel();
		expectedValue = quantity.getValue() + " " + MPSS2.getShortLabel();
		assertEquals(expectedValue, actualValue);

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "getShortName", args = {})
	public void testGetShortName() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		ComposedUnit MPSS1 = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();
		ComposedUnit MPSS2 = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).setShortName("acc")
				.setLongName("acceleration").build();

		Quantity quantity = new Quantity(MPSS1, 14.5);
		String actualValue = quantity.getShortName();
		String expectedValue = quantity.getValue() + " " + MPSS1.getShortName();
		assertEquals(expectedValue, actualValue);

		quantity = new Quantity(MPSS2, 14.5);
		actualValue = quantity.getShortName();
		expectedValue = quantity.getValue() + " " + MPSS2.getShortName();
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "toString", args = {})
	public void testToString() {
		Measure TIME = new Measure("time");
		Measure LENGTH = new Measure("length");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");

		ComposedUnit MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).setShortName("acc")
				.setLongName("acceleration").build();

		Quantity quantity = new Quantity(MPSS, 14.5);
		String actualValue = quantity.toString();
		String expectedValue = "Quantity [composedUnit="
				+ "ComposedUnit [value=1.0, longName=acceleration, shortName=acc, "
				+ "measures={Measure [name=length]=UnitPower [baseUnit=BaseUnit [measure=Measure [name=length], value=1.0, name=meter, shortName=m], power=1],"
				+ " Measure [name=time]=UnitPower [baseUnit=BaseUnit [measure=Measure [name=time], value=1.0, name=second, shortName=s], power=-2]}],"
				+ " value=14.5]";
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "inc", args = {})
	public void testInc() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8441552572669386207L);
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			double expectedValue = quantity.getValue() + 1;
			quantity = quantity.inc();
			double actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);

		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "inc", args = { double.class })
	public void testInc_value() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8963912264107163805L);
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			double amount = randomGenerator.nextDouble();
			double expectedValue = quantity.getValue() + amount;
			quantity = quantity.inc(amount);
			double actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "dec", args = {})
	public void testDec() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2045113016927822606L);
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			double expectedValue = quantity.getValue() - 1;
			quantity = quantity.dec();
			double actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "dec", args = { double.class })
	public void testDec_value() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8963912264107163805L);
		for (int i = 0; i < 30; i++) {
			Quantity quantity = getRandomQuantity(randomGenerator.nextLong());
			double amount = randomGenerator.nextDouble();
			double expectedValue = quantity.getValue() - amount;
			quantity = quantity.dec(amount);
			double actualValue = quantity.getValue();
			assertEquals(expectedValue, actualValue);
		}
	}
}
