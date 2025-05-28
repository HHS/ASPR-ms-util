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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");
		List<Unit> timeUnits = new ArrayList<>();
		timeUnits.add(SECOND);
		timeUnits.add(MINUTE);
		timeUnits.add(HOUR);

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");
		List<Unit> distanceUnits = new ArrayList<>();
		distanceUnits.add(METER);
		distanceUnits.add(CM);
		distanceUnits.add(INCH);
		distanceUnits.add(FOOT);
		distanceUnits.add(MILE);

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
		Unit POUND = new Unit(KILOGRAM, 0.45359237, "pound", "lb");
		Unit OUNCE = new Unit(POUND, 1.0 / 16, "ounce", "oz");
		List<Unit> massUnits = new ArrayList<>();
		massUnits.add(KILOGRAM);
		massUnits.add(POUND);
		massUnits.add(OUNCE);

		Unit timeUnit = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		Unit distanceUnit = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		Unit massUnit = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		int timePower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);
		int distancePower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);
		int massPower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);

		return ComposedUnit.builder()//
				.setUnit(timeUnit, timePower)//
				.setUnit(distanceUnit, distancePower)//
				.setUnit(massUnit, massPower)//
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
	 * even chances of having 1, 2 or 3 unitTypes.
	 */
	private Quantity getRandomQuantity(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");
		List<Unit> timeUnits = new ArrayList<>();
		timeUnits.add(SECOND);
		timeUnits.add(MINUTE);
		timeUnits.add(HOUR);

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");
		List<Unit> distanceUnits = new ArrayList<>();
		distanceUnits.add(METER);
		distanceUnits.add(CM);
		distanceUnits.add(INCH);
		distanceUnits.add(FOOT);
		distanceUnits.add(MILE);

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
		Unit POUND = new Unit(KILOGRAM, 0.45359237, "pound", "lb");
		Unit OUNCE = new Unit(POUND, 1.0 / 16, "ounce", "oz");
		List<Unit> massUnits = new ArrayList<>();
		massUnits.add(KILOGRAM);
		massUnits.add(POUND);
		massUnits.add(OUNCE);

		Unit timeUnit = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		Unit distanceUnit = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		Unit massUnit = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		int[] selectedPowers = selectPowers(randomGenerator);

		int timePower = selectedPowers[0];
		int distancePower = selectedPowers[1];
		int massPower = selectedPowers[2];

		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setUnit(timeUnit, timePower)//
				.setUnit(distanceUnit, distancePower)//
				.setUnit(massUnit, massPower)//
				.build();

		double value = randomGenerator.nextDouble();
		return new Quantity(composedUnit, value);
	}

	/*
	 * Creates two randomized quantities using several time, length and mass units
	 * to powers in [-5,-1]U[1,5]. They will have random positive values(bounded to
	 * the interval [0.0001,1), but will agree on unitTypes and powers.
	 */
	private Pair<Quantity, Quantity> getRandomCompatibleQuanties(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");
		List<Unit> timeUnits = new ArrayList<>();
		timeUnits.add(SECOND);
		timeUnits.add(MINUTE);
		timeUnits.add(HOUR);

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");
		List<Unit> distanceUnits = new ArrayList<>();
		distanceUnits.add(METER);
		distanceUnits.add(CM);
		distanceUnits.add(INCH);
		distanceUnits.add(FOOT);
		distanceUnits.add(MILE);

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
		Unit POUND = new Unit(KILOGRAM, 0.45359237, "pound", "lb");
		Unit OUNCE = new Unit(POUND, 1.0 / 16, "ounce", "oz");
		List<Unit> massUnits = new ArrayList<>();
		massUnits.add(KILOGRAM);
		massUnits.add(POUND);
		massUnits.add(OUNCE);

		Unit timeUnit1 = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		Unit distanceUnit1 = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		Unit massUnit1 = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		Unit timeUnit2 = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		Unit distanceUnit2 = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		Unit massUnit2 = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		int[] selectedPowers = selectPowers(randomGenerator);

		int timePower = selectedPowers[0];
		int distancePower = selectedPowers[1];
		int massPower = selectedPowers[2];

		ComposedUnit composedUnit1 = ComposedUnit.builder()//
				.setUnit(timeUnit1, timePower)//
				.setUnit(distanceUnit1, distancePower)//
				.setUnit(massUnit1, massPower)//
				.build();

		ComposedUnit composedUnit2 = ComposedUnit.builder()//
				.setUnit(timeUnit2, timePower)//
				.setUnit(distanceUnit2, distancePower)//
				.setUnit(massUnit2, massPower)//
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
	@UnitTestConstructor(target = Quantity.class, args = { Unit.class, double.class })
	public void testQuantity_Unit() {
		// postcontition tests covered by the other tests

		// precondition test: if the unit is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Unit unit = null;
			new Quantity(unit, 1.2);
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
		List<Unit> units = composedUnit.getUnits();
		Unit alteredUnit = units.get(randomGenerator.nextInt(units.size()));
		int alteredPower = composedUnit.getPower(alteredUnit.getUnitType()).get() + 1;

		ComposedUnit.Builder builder = ComposedUnit.builder();
		for (Unit unit : units) {
			if (unit != alteredUnit) {
				int power = composedUnit.getPower(unit.getUnitType()).get();
				builder.setUnit(unit, power);
			} else {
				builder.setUnit(alteredUnit, alteredPower);
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
		// unitTypes
		contractException = assertThrows(ContractException.class, () -> {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();
			Quantity q3 = getQuantityWithAlteredPower(q2, randomGenerator.nextLong());
			q1.add(q3);
		});
		assertEquals(MeasuresError.INCOMPATIBLE_UNIT_TYPES, contractException.getErrorType());
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
			Map<UnitType, Pair<Unit, MutableInteger>> unitTypeMap = new LinkedHashMap<>();
			ComposedUnit c1 = q1.getComposedUnit();
			for (Unit unit : c1.getUnits()) {
				int power = c1.getPower(unit.getUnitType()).get();
				unitTypeMap.put(unit.getUnitType(), new Pair<>(unit, new MutableInteger(power)));
			}
			ComposedUnit c2 = q2.getComposedUnit();
			for (Unit unit : c2.getUnits()) {
				int power = c2.getPower(unit.getUnitType()).get();
				Pair<Unit, MutableInteger> pair = unitTypeMap.get(unit.getUnitType());
				if (pair != null) {
					pair.getSecond().decrement(power);
				} else {
					unitTypeMap.put(unit.getUnitType(), new Pair<>(unit, new MutableInteger(-power)));
				}
			}

			ComposedUnit.Builder builder = ComposedUnit.builder();
			for (Pair<Unit, MutableInteger> pair : unitTypeMap.values()) {
				Unit unit = pair.getFirst();
				MutableInteger power = pair.getSecond();
				builder.setUnit(unit, power.getValue());
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
	@UnitTestMethod(target = Quantity.class, name = "eq", args = { Quantity.class, double.class })
	public void testEq() {
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

			assertTrue(q1.eq(q2, 1e-12));

			Quantity q3 = q2.scale(1 + 1e-10);
			assertFalse(q1.eq(q3, 1e-12));

			Quantity q4 = q2.scale(1 - 1e-10);
			assertFalse(q1.eq(q4, 1e-12));
		}

		// special edge case tests
		UnitType MASS = new UnitType("mass");
		Unit KG = new Unit(MASS, "kilogram", "kg");
		UnitType TIME = new UnitType("time");
		Unit HOUR = new Unit(TIME, "hour", "h");

		Quantity q1 = new Quantity(HOUR, Double.NaN);
		Quantity q2 = new Quantity(HOUR, 1);
		assertFalse(q1.eq(q2));

		q1 = new Quantity(HOUR, 1);
		q2 = new Quantity(HOUR, Double.NaN);
		assertFalse(q1.eq(q2));
		
		q1 = new Quantity(HOUR, Double.NaN);
		q2 = new Quantity(HOUR, Double.NaN);
		assertFalse(q1.eq(q2));

		// Show reflexivity for eq.
		for (int i = 0; i < 30; i++) {
			double baseValue = randomGenerator.nextDouble();						
			q1 = new Quantity(HOUR, baseValue);			
			assertTrue(q1.eq(q1, 0));			
		}

		// Show symmetry for eq.
		for (int i = 0; i < 30; i++) {
			double baseValue = randomGenerator.nextDouble() + 1;
			double tolerance = 0.01;
			double altValue = baseValue * 1.001;
			q1 = new Quantity(HOUR, baseValue);
			q2 = new Quantity(HOUR, altValue);
			assertTrue(q1.eq(q2, tolerance));
			assertTrue(q2.eq(q1, tolerance));
		}

		for (int i = 0; i < 30; i++) {
			double baseValue = randomGenerator.nextDouble() + 1;
			double tolerance = 0.01;
			double altValue = baseValue * 1.1;
			q1 = new Quantity(HOUR, baseValue);
			q2 = new Quantity(HOUR, altValue);
			assertFalse(q1.eq(q2, tolerance));
			assertFalse(q2.eq(q1, tolerance));
		}

		// precondition test: if the quantity is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			Quantity q = new Quantity(HOUR, 1);
			q.eq(null);
		});
		assertEquals(MeasuresError.NULL_QUANTITY, contractException.getErrorType());

		// precondition test: if the quantity does not have matching dimensions
		contractException = assertThrows(ContractException.class, () -> {
			Quantity q3 = new Quantity(HOUR, 1);
			Quantity q4 = new Quantity(KG, 1);
			q3.eq(q4);
		});
		assertEquals(MeasuresError.INCOMPATIBLE_UNIT_TYPES, contractException.getErrorType());

		// precondition test: if the tolerance value us not in the interval
		contractException = assertThrows(ContractException.class, () -> {
			Quantity q3 = new Quantity(HOUR, 1.5);
			Quantity q4 = new Quantity(HOUR, 1.5000005);
			q3.eq(q4, 1.0);
		});
		assertEquals(MeasuresError.INVALID_TOLERANCE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> {
			Quantity q3 = new Quantity(HOUR, 1.5);
			Quantity q4 = new Quantity(HOUR, 1.5000005);
			q3.eq(q4, -0.1);
		});
		assertEquals(MeasuresError.INVALID_TOLERANCE, contractException.getErrorType());

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
		UnitType LENGTH = new UnitType("length");
		UnitType TIME = new UnitType("time");
		Unit INCH = new Unit(LENGTH, "foot", "ft");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit SECOND = new Unit(TIME, "second", "sec");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");

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

		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();

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
			Map<UnitType, Pair<Unit, MutableInteger>> unitTypeMap = new LinkedHashMap<>();
			ComposedUnit c1 = q1.getComposedUnit();
			for (Unit unit : c1.getUnits()) {
				int power = c1.getPower(unit.getUnitType()).get();
				unitTypeMap.put(unit.getUnitType(), new Pair<>(unit, new MutableInteger(power)));
			}
			ComposedUnit c2 = q2.getComposedUnit();
			for (Unit unit : c2.getUnits()) {
				int power = c2.getPower(unit.getUnitType()).get();
				Pair<Unit, MutableInteger> pair = unitTypeMap.get(unit.getUnitType());
				if (pair != null) {
					pair.getSecond().increment(power);
				} else {
					unitTypeMap.put(unit.getUnitType(), new Pair<>(unit, new MutableInteger(power)));
				}
			}

			ComposedUnit.Builder builder = ComposedUnit.builder();
			for (Pair<Unit, MutableInteger> pair : unitTypeMap.values()) {
				Unit unit = pair.getFirst();
				MutableInteger power = pair.getSecond();
				builder.setUnit(unit, power.getValue());
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
		// unitTypes
		contractException = assertThrows(ContractException.class, () -> {
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(randomGenerator.nextLong());
			Quantity q1 = pair.getFirst();
			Quantity q2 = pair.getSecond();
			Quantity q3 = getQuantityWithAlteredPower(q2, randomGenerator.nextLong());
			q1.sub(q3);
		});
		assertEquals(MeasuresError.INCOMPATIBLE_UNIT_TYPES, contractException.getErrorType());
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
			for (Unit unit : composedUnit.getUnits()) {
				builder.setUnit(unit, -composedUnit.getPower(unit.getUnitType()).get());
			}
			ComposedUnit expectedComposedUnit = builder.build();
			ComposedUnit actualComposedUnit = invertedQuantity.getComposedUnit();
			assertEquals(expectedComposedUnit, actualComposedUnit);
		}

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isFinite", args = {})
	public void testIsFinite() {

		UnitType TIME = new UnitType("time");
		Unit SECOND = new Unit(TIME, "second", "s");

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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();

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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();

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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();

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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();

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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");

		Quantity quantity = new Quantity(SECOND, 1);
		assertFalse(quantity.isUnitLess());

		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();
		quantity = new Quantity(MPSS, 12.6);
		assertFalse(quantity.isUnitLess());

		quantity = new Quantity(ComposedUnit.builder().build(), 12.6);
		assertTrue(quantity.isUnitLess());
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isZero", args = {})
	public void testIsZero() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();

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
				for (Unit unit : composedUnit.getUnits()) {
					int p = composedUnit.getPower(unit.getUnitType()).get();
					builder.setUnit(unit, power * p);
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
		assertEquals(MeasuresError.INCOMPATIBLE_UNIT_TYPES, contractException.getErrorType());
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
			UnitType LENGTH = new UnitType("length");
			UnitType TIME = new UnitType("time");
			Unit INCH = new Unit(LENGTH, "foot", "ft");
			Unit FOOT = new Unit(INCH, 12, "foot", "ft");
			Unit SECOND = new Unit(TIME, "second", "sec");
			Unit MINUTE = new Unit(SECOND, 60, "minute", "min");

			ComposedUnit FSPMS = ComposedUnit.builder().setUnit(FOOT, 2).setUnit(MINUTE, -2).build();
			Quantity q = new Quantity(FSPMS, 3.6);
			q.root(-1);
		});
		assertEquals(MeasuresError.NON_POSITIVE_ROOT, contractException.getErrorType());

		// precondition test: if any of the unitType powers is not divisible by the root
		contractException = assertThrows(ContractException.class, () -> {
			UnitType LENGTH = new UnitType("length");
			UnitType TIME = new UnitType("time");
			Unit INCH = new Unit(LENGTH, "foot", "ft");
			Unit FOOT = new Unit(INCH, 12, "foot", "ft");
			Unit SECOND = new Unit(TIME, "second", "sec");
			Unit MINUTE = new Unit(SECOND, 60, "minute", "min");

			ComposedUnit FSPMS = ComposedUnit.builder().setUnit(FOOT, 2).setUnit(MINUTE, -2).build();
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

		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();

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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");

		// we create two composed units, one with and one without names
		ComposedUnit MPSS1 = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();
		ComposedUnit MPSS2 = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).setShortName("acc")
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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");

		// we create two composed units, one with and one without names
		ComposedUnit MPSS1 = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();
		ComposedUnit MPSS2 = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).setShortName("acc")
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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");

		// we create two composed units, one with and one without names
		ComposedUnit MPSS1 = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();
		ComposedUnit MPSS2 = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).setShortName("acc")
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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS1 = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();
		ComposedUnit MPSS2 = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).setShortName("acc")
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
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");

		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).setShortName("acc")
				.setLongName("acceleration").build();

		Quantity quantity = new Quantity(MPSS, 14.5);
		String actualValue = quantity.toString();
		String expectedValue = "Quantity [composedUnit="
				+ "ComposedUnit [value=1.0, longName=acceleration, shortName=acc, "
				+ "unitTypes={UnitType [name=length]=UnitPower [unit=Unit [unitType=UnitType [name=length], value=1.0, name=meter, shortName=m], power=1],"
				+ " UnitType [name=time]=UnitPower [unit=Unit [unitType=UnitType [name=time], value=1.0, name=second, shortName=s], power=-2]}],"
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

	@Test
	@UnitTestMethod(target = Quantity.class, name = "round", args = {})
	public void testRound() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(4829795492799583108L);

		UnitType TIME = new UnitType("time");
		Unit HOUR = new Unit(TIME, "hour", "h");

		/*
		 * All rounding rules when applied to whole numbers should return the input.
		 * Note that large values (x: abs(x)>=2^52) are already whole numbers.
		 */
		for (int i = -10; i < 10; i++) {
			double value = i;
			Quantity quantity = new Quantity(HOUR, value);
			assertEquals(value, quantity.round(RoundingRule.UP).getValue());
			assertEquals(value, quantity.round(RoundingRule.DOWN).getValue());
			assertEquals(value, quantity.round(RoundingRule.AWAY_FROM_ZERO).getValue());
			assertEquals(value, quantity.round(RoundingRule.TOWARD_ZERO).getValue());
			assertEquals(value, quantity.round(RoundingRule.NEAREST).getValue());
		}

		/* Rounding rules when applied to non-whole numbers. */
		for (int i = -10; i < 10; i++) {
			double value = i + 0.9 * randomGenerator.nextDouble() + 0.1;
			Quantity quantity = new Quantity(HOUR, value);
			assertEquals((double) (i + 1), quantity.round(RoundingRule.UP).getValue());
			assertEquals((double) (i), quantity.round(RoundingRule.DOWN).getValue());
			assertEquals((double) (i < 0 ? i : i + 1), quantity.round(RoundingRule.AWAY_FROM_ZERO).getValue());
			assertEquals((double) (i < 0 ? i + 1 : i), quantity.round(RoundingRule.TOWARD_ZERO).getValue());
			assertEquals(FastMath.floor(value + 0.5), quantity.round(RoundingRule.NEAREST).getValue());
		}
	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isIntValue", args = {})
	public void testIsIntValue() {
		UnitType TIME = new UnitType("time");
		Unit HOUR = new Unit(TIME, "hour", "h");

		// zero
		assertTrue(new Quantity(HOUR, 0.0).isIntValue());
		assertTrue(new Quantity(HOUR, -0.0).isIntValue());

		// whole numbers
		assertTrue(new Quantity(HOUR, -45).isIntValue());
		assertTrue(new Quantity(HOUR, -1).isIntValue());
		assertTrue(new Quantity(HOUR, 1).isIntValue());
		assertTrue(new Quantity(HOUR, 28).isIntValue());

		// non-whole numbers
		assertFalse(new Quantity(HOUR, -122.7).isIntValue());
		assertFalse(new Quantity(HOUR, -45.07).isIntValue());
		assertFalse(new Quantity(HOUR, 13.354).isIntValue());
		assertFalse(new Quantity(HOUR, 12342343.23).isIntValue());

		// extreme values
		assertTrue(new Quantity(HOUR, Integer.MAX_VALUE).isIntValue());
		assertFalse(new Quantity(HOUR, (double) Integer.MAX_VALUE + 1.0).isIntValue());
		assertTrue(new Quantity(HOUR, Integer.MIN_VALUE).isIntValue());
		assertFalse(new Quantity(HOUR, (double) Integer.MIN_VALUE - 1.0).isIntValue());
		assertFalse(new Quantity(HOUR, Double.NaN).isIntValue());
		assertFalse(new Quantity(HOUR, Double.POSITIVE_INFINITY).isIntValue());
		assertFalse(new Quantity(HOUR, Double.NEGATIVE_INFINITY).isIntValue());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "isLongValue", args = {})
	public void testIsLongValue() {

		UnitType TIME = new UnitType("time");
		Unit HOUR = new Unit(TIME, "hour", "h");

		// zero
		assertTrue(new Quantity(HOUR, 0.0).isLongValue());
		assertTrue(new Quantity(HOUR, -0.0).isLongValue());

		// whole numbers
		assertTrue(new Quantity(HOUR, -5234789128456L).isLongValue());
		assertTrue(new Quantity(HOUR, -45).isLongValue());
		assertTrue(new Quantity(HOUR, -1).isLongValue());
		assertTrue(new Quantity(HOUR, 1).isLongValue());
		assertTrue(new Quantity(HOUR, 28).isLongValue());
		assertTrue(new Quantity(HOUR, 5234789128456L).isLongValue());

		// non-whole numbers
		assertFalse(new Quantity(HOUR, -122.7).isLongValue());
		assertFalse(new Quantity(HOUR, -45.07).isLongValue());
		assertFalse(new Quantity(HOUR, 13.354).isLongValue());
		assertFalse(new Quantity(HOUR, 12342343.23).isLongValue());

		/*
		 * extreme values -- note that adding a small value to a large double will not
		 * necessarily raise the value, so we must use a larger value to properly move
		 * the double above or belong max and min long.
		 */
		assertTrue(new Quantity(HOUR, Long.MAX_VALUE).isLongValue());
		assertFalse(new Quantity(HOUR, (double) Long.MAX_VALUE + 10_000.0).isLongValue());
		assertTrue(new Quantity(HOUR, Long.MIN_VALUE).isLongValue());
		assertFalse(new Quantity(HOUR, (double) Long.MIN_VALUE - 10_000.0).isLongValue());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "getValueAsInt", args = {})
	public void testGetValueAsInt() {

		UnitType TIME = new UnitType("time");
		Unit HOUR = new Unit(TIME, "hour", "h");

		// zero
		assertEquals(0, new Quantity(HOUR, 0.0).getValueAsInt());
		assertEquals(0, new Quantity(HOUR, -0.0).getValueAsInt());

		// whole numbers
		assertEquals(-45, new Quantity(HOUR, -45).getValueAsInt());
		assertEquals(-1, new Quantity(HOUR, -1).getValueAsInt());
		assertEquals(1, new Quantity(HOUR, 1).getValueAsInt());
		assertEquals(28, new Quantity(HOUR, 28).getValueAsInt());

		// non-whole numbers
		assertEquals(-122, new Quantity(HOUR, -122.7).getValueAsInt());
		assertEquals(-45, new Quantity(HOUR, -45.07).getValueAsInt());
		assertEquals(13, new Quantity(HOUR, 13.354).getValueAsInt());
		assertEquals(12342343, new Quantity(HOUR, 12342343.23).getValueAsInt());

		// extreme values
		assertEquals(Integer.MAX_VALUE, new Quantity(HOUR, Integer.MAX_VALUE).getValueAsInt());
		assertEquals(Integer.MIN_VALUE, new Quantity(HOUR, Integer.MIN_VALUE).getValueAsInt());

		ContractException contractException = assertThrows(ContractException.class, () -> {
			new Quantity(HOUR, Double.NaN).getValueAsInt();
		});
		assertEquals(MeasuresError.VALUE_CANNOT_BE_CAST_TO_INT, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> {
			new Quantity(HOUR, (double) Integer.MAX_VALUE + 1.0).getValueAsInt();
		});
		assertEquals(MeasuresError.VALUE_CANNOT_BE_CAST_TO_INT, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> {
			new Quantity(HOUR, (double) Integer.MIN_VALUE - 1.0).getValueAsInt();
		});
		assertEquals(MeasuresError.VALUE_CANNOT_BE_CAST_TO_INT, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Quantity.class, name = "getValueAsLong", args = {})
	public void testGetValueAsLong() {
		UnitType TIME = new UnitType("time");
		Unit HOUR = new Unit(TIME, "hour", "h");

		// zero
		assertEquals(0, new Quantity(HOUR, 0.0).getValueAsLong());
		assertEquals(0, new Quantity(HOUR, -0.0).getValueAsLong());

		// whole numbers
		assertEquals(-45, new Quantity(HOUR, -45).getValueAsLong());
		assertEquals(-1, new Quantity(HOUR, -1).getValueAsLong());
		assertEquals(1, new Quantity(HOUR, 1).getValueAsLong());
		assertEquals(28, new Quantity(HOUR, 28).getValueAsLong());

		// non-whole numbers
		assertEquals(-122, new Quantity(HOUR, -122.7).getValueAsLong());
		assertEquals(-45, new Quantity(HOUR, -45.07).getValueAsLong());
		assertEquals(13, new Quantity(HOUR, 13.354).getValueAsLong());
		assertEquals(12342343, new Quantity(HOUR, 12342343.23).getValueAsLong());

		// extreme values
		assertEquals(Integer.MAX_VALUE, new Quantity(HOUR, Integer.MAX_VALUE).getValueAsLong());
		assertEquals(Integer.MIN_VALUE, new Quantity(HOUR, Integer.MIN_VALUE).getValueAsLong());

		ContractException contractException = assertThrows(ContractException.class, () -> {
			new Quantity(HOUR, Double.NaN).getValueAsLong();
		});
		assertEquals(MeasuresError.VALUE_CANNOT_BE_CAST_TO_LONG, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> {
			new Quantity(HOUR, (double) Long.MAX_VALUE + 10000.0).getValueAsLong();
		});
		assertEquals(MeasuresError.VALUE_CANNOT_BE_CAST_TO_LONG, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> {
			new Quantity(HOUR, (double) Long.MIN_VALUE - 10000.0).getValueAsLong();
		});
		assertEquals(MeasuresError.VALUE_CANNOT_BE_CAST_TO_LONG, contractException.getErrorType());
	}

}
