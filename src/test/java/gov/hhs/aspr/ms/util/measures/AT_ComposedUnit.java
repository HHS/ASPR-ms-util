package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTag;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_ComposedUnit {

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "getBaseUnits", args = {})
	public void testGetBaseUnits() {

		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
		Unit POUND = new Unit(KILOGRAM, 0.45359237, "pound", "lb");
		Unit OUNCE = new Unit(POUND, 1.0 / 16, "ounce", "oz");

		// case 1
		ComposedUnit composedUnit = ComposedUnit.builder().setBaseUnit(OUNCE, 2).setBaseUnit(HOUR, 3).build();
		List<Unit> expectedBaseUnits = new ArrayList<>();
		expectedBaseUnits.add(OUNCE);
		expectedBaseUnits.add(HOUR);

		List<Unit> actualBaseUnits = composedUnit.getBaseUnits();
		assertEquals(expectedBaseUnits, actualBaseUnits);

		// case 2
		composedUnit = ComposedUnit.builder().setBaseUnit(HOUR, -2).setBaseUnit(MILE, 2).build();
		expectedBaseUnits = new ArrayList<>();
		expectedBaseUnits.add(MILE);
		expectedBaseUnits.add(HOUR);

		actualBaseUnits = composedUnit.getBaseUnits();
		assertEquals(expectedBaseUnits, actualBaseUnits);

		// case 3
		composedUnit = ComposedUnit.builder().setBaseUnit(KILOGRAM, 1).setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();
		expectedBaseUnits = new ArrayList<>();
		expectedBaseUnits.add(METER);
		expectedBaseUnits.add(KILOGRAM);
		expectedBaseUnits.add(SECOND);

		actualBaseUnits = composedUnit.getBaseUnits();
		assertEquals(expectedBaseUnits, actualBaseUnits);

	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "builder", args = {})
	public void testBuilder() {
		assertNotNull(ComposedUnit.builder());
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "equals", args = { Object.class })
	public void testEquals() {
		// Two composed units are equal if and only if their units and powers are equal

		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		ComposedUnit MPS1 = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.build();

		ComposedUnit MPS2 = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.build();

		ComposedUnit MPH1 = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.build();

		ComposedUnit MPH2 = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.setNames("miles per hour", "mph")//
				.build();

		assertEquals(MPS1, MPS2);
		assertNotEquals(MPS1, MPH1);
		assertNotEquals(MPH1, MPH2);

		// not equal null
		assertFalse(MPS1.equals(null));
		assertFalse(MPS2.equals(null));
		assertFalse(MPH1.equals(null));
		assertFalse(MPH2.equals(null));

		// not equal to other types
		assertFalse(MPS1.equals(new Object()));
		assertFalse(MPS2.equals(new Object()));
		assertFalse(MPH1.equals(new Object()));
		assertFalse(MPH2.equals(new Object()));

		// reflexive
		assertTrue(MPS1.equals(MPS1));
		assertTrue(MPS2.equals(MPS2));
		assertTrue(MPH1.equals(MPH1));
		assertTrue(MPH2.equals(MPH2));

		// symmetric, transitive and stable
		for (int i = 0; i < 5; i++) {
			assertTrue(MPS1.equals(MPS2));
			assertTrue(MPS2.equals(MPS1));
		}
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "getLongLabel", args = {})
	public void testGetLongLabel() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");

		// sample 1
		String actualValue = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.build().getLongLabel();

		String expectedValue = "meter^1 second^-1";
		assertEquals(expectedValue, actualValue);

		// sample 2
		actualValue = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.build().getLongLabel();

		expectedValue = "mile^1 hour^-1";
		assertEquals(expectedValue, actualValue);

		// sample 2
		actualValue = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(KILOGRAM, 1)//
				.setBaseUnit(SECOND, -2)//
				.build().getLongLabel();

		expectedValue = "meter^1 kilogram^1 second^-2";
		assertEquals(expectedValue, actualValue);

	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "getLongName", args = {})
	public void testGetLongName() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		// sample 1 -- without setting long name
		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.build();
		String actualValue = composedUnit.getLongName();
		String expectedValue = composedUnit.getLongLabel();
		assertEquals(expectedValue, actualValue);

		// sample 2 -- with setting long name
		expectedValue = "miles_per_hour";
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.setLongName(expectedValue).build();
		actualValue = composedUnit.getLongName();
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "getShortName", args = {})
	public void testGetShortName() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		// sample 1 -- without setting short name
		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.build();
		String actualValue = composedUnit.getShortName();
		String expectedValue = composedUnit.getShortLabel();
		assertEquals(expectedValue, actualValue);

		// sample 2 -- with setting short name
		expectedValue = "mph";
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.setShortName(expectedValue).build();
		actualValue = composedUnit.getShortName();
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "getPower", args = { UnitType.class })
	public void testGetPower() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");
		UnitType AMPERE = new UnitType("electric_current");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");

		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(KILOGRAM, 1)//
				.setBaseUnit(METER, 2)//
				.setBaseUnit(SECOND, -3)//
				.build();

		Optional<Integer> optional = composedUnit.getPower(MASS);
		assertTrue(optional.isPresent());
		assertEquals(1, optional.get());

		optional = composedUnit.getPower(LENGTH);
		assertTrue(optional.isPresent());
		assertEquals(2, optional.get());

		optional = composedUnit.getPower(TIME);
		assertTrue(optional.isPresent());
		assertEquals(-3, optional.get());

		optional = composedUnit.getPower(AMPERE);
		assertFalse(optional.isPresent());

		// precondition test: if the measure is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			ComposedUnit.builder()//
					.setBaseUnit(METER, 1)//
					.build().getPower(null);
		});

		assertEquals(MeasuresError.NULL_MEASURE, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "getShortLabel", args = {})
	public void testGetShortLabel() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");

		// sample 1
		String actualValue = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.build().getShortLabel();

		String expectedValue = "m^1 s^-1";
		assertEquals(expectedValue, actualValue);

		// sample 2
		actualValue = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.build().getShortLabel();

		expectedValue = "mi^1 h^-1";
		assertEquals(expectedValue, actualValue);

		// sample 3
		actualValue = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(KILOGRAM, 1)//
				.setBaseUnit(SECOND, -2)//
				.build().getShortLabel();

		expectedValue = "m^1 kg^1 s^-2";
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "getBaseUnit", args = { UnitType.class })
	public void testGetBaseUnit() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");
		UnitType AMPERE = new UnitType("electric_current");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");

		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(KILOGRAM, 1)//
				.setBaseUnit(METER, 2)//
				.setBaseUnit(SECOND, -3)//
				.build();

		Optional<Unit> optional = composedUnit.getBaseUnit(MASS);
		assertTrue(optional.isPresent());
		assertEquals(KILOGRAM, optional.get());

		optional = composedUnit.getBaseUnit(LENGTH);
		assertTrue(optional.isPresent());
		assertEquals(METER, optional.get());

		optional = composedUnit.getBaseUnit(TIME);
		assertTrue(optional.isPresent());
		assertEquals(SECOND, optional.get());

		optional = composedUnit.getBaseUnit(AMPERE);
		assertFalse(optional.isPresent());

		// precondition test: if the measure is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			ComposedUnit.builder()//
					.setBaseUnit(METER, 1)//
					.build().getBaseUnit(null);
		});

		assertEquals(MeasuresError.NULL_MEASURE, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "getValue", args = {})
	public void getValue() {
		// The unit-less case should result in a value of 1
		assertEquals(1.0, ComposedUnit.builder()//
				.build().getValue());

		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
		Unit POUND = new Unit(KILOGRAM, 0.45359237, "pound", "lb");

		// example 1: miles per hour
		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.setNames("miles per hour", "mph")//
				.build();

		/*
		 * note: we calculate the expected value by mirroring the order of operations in
		 * the units above so that we can derive an exact value
		 */
		double expectedValue = (1.0 / 100) * 2.54 * 12 * 5280 / 3600;
		double actualValue = composedUnit.getValue();

		assertEquals(expectedValue, actualValue);

		// example 2
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(POUND, 1)//
				.setBaseUnit(INCH, -2)//
				.setNames("pounds per square foot", "psi")//
				.build();

		double poundValue = 0.45359237;
		double inchValue = 0.01 * 2.54;
		expectedValue = poundValue / (inchValue * inchValue);
		actualValue = composedUnit.getValue();
		assertEquals(expectedValue, actualValue);

	}

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
				.setBaseUnit(timeUnit, timePower)//
				.setBaseUnit(distanceUnit, distancePower)//
				.setBaseUnit(massUnit, massPower)//
				.build();
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5013191714334523037L);
		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			ComposedUnit composedUnit1 = getRandomComposedUnit(seed);
			ComposedUnit composedUnit2 = getRandomComposedUnit(seed);
			assertEquals(composedUnit1, composedUnit2);
			assertEquals(composedUnit1.hashCode(), composedUnit2.hashCode());
		}

		// hash codes are reasonably distributed

		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			ComposedUnit composedUnit = getRandomComposedUnit(randomGenerator.nextLong());
			hashCodes.add(composedUnit.hashCode());
		}
		assertTrue(hashCodes.size() > 95);
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "isCompatible", args = { ComposedUnit.class })
	public void testIsCompatible() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
		Unit POUND = new Unit(KILOGRAM, 0.45359237, "pound", "lb");
		Unit OUNCE = new Unit(POUND, 1.0 / 16, "ounce", "oz");

		ComposedUnit composedUnit1 = ComposedUnit.builder().setBaseUnit(HOUR, 1).setBaseUnit(METER, -1).build();
		ComposedUnit composedUnit2 = ComposedUnit.builder().setBaseUnit(MINUTE, 1).setBaseUnit(FOOT, -1).build();
		assertTrue(composedUnit1.isCompatible(composedUnit2));
		assertTrue(composedUnit2.isCompatible(composedUnit1));

		composedUnit1 = ComposedUnit.builder().setBaseUnit(MILE, 1).setBaseUnit(HOUR, -1).build();
		composedUnit2 = ComposedUnit.builder().setBaseUnit(FOOT, 1).setBaseUnit(SECOND, -2).build();
		assertFalse(composedUnit1.isCompatible(composedUnit2));
		assertFalse(composedUnit2.isCompatible(composedUnit1));

		composedUnit1 = ComposedUnit.builder().setBaseUnit(OUNCE, 1).build();
		composedUnit2 = ComposedUnit.builder().setBaseUnit(POUND, 1).build();
		assertTrue(composedUnit1.isCompatible(composedUnit2));
		assertTrue(composedUnit2.isCompatible(composedUnit1));

		composedUnit1 = ComposedUnit.builder().setBaseUnit(OUNCE, 1).build();
		composedUnit2 = ComposedUnit.builder().setBaseUnit(POUND, 2).build();
		assertFalse(composedUnit1.isCompatible(composedUnit2));
		assertFalse(composedUnit2.isCompatible(composedUnit1));

		// precondition test: if the composed unit is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> ComposedUnit.builder().build().isCompatible(null));
		assertEquals(MeasuresError.NULL_COMPOSITE, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "isMeasureLess", args = {})
	public void testIsMeasureLess() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(METER, 0)//
				.setBaseUnit(SECOND, 0)//
				.setNames("long_name", "short_name").build();

		assertTrue(composedUnit.isMeasureLess());

		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, 0)//
				.setNames("long_name", "short_name").build();
		assertFalse(composedUnit.isMeasureLess());
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.class, name = "toString", args = {})
	public void testToString() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPS = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.setNames("meters per second", "mps").build();
		String actualValue = MPS.toString();

		String expectedValue = "ComposedUnit [value=1.0, longName=meters per second, shortName=mps, measures={Measure [name=length]=UnitPower [baseUnit=BaseUnit [measure=Measure [name=length], value=1.0, name=meter, shortName=m], power=1], Measure [name=time]=UnitPower [baseUnit=BaseUnit [measure=Measure [name=time], value=1.0, name=second, shortName=s], power=-1]}]";
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.Builder.class, name = "build", args = {}, tags = { UnitTag.LOCAL_PROXY })
	public void testBuild() {
		// covered by the other tests
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.Builder.class, name = "setBaseUnit", args = { Unit.class, int.class })
	public void testSetBaseUnit() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");

		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(KILOGRAM, 1)//
				.setBaseUnit(METER, 2)//
				.setBaseUnit(SECOND, -3)//
				.build();

		Optional<Unit> optionalUnit = composedUnit.getBaseUnit(MASS);
		assertTrue(optionalUnit.isPresent());
		assertEquals(KILOGRAM, optionalUnit.get());
		Optional<Integer> optionalPower = composedUnit.getPower(MASS);
		assertTrue(optionalPower.isPresent());
		assertEquals(1, optionalPower.get());

		optionalUnit = composedUnit.getBaseUnit(LENGTH);
		assertTrue(optionalUnit.isPresent());
		assertEquals(METER, optionalUnit.get());
		optionalPower = composedUnit.getPower(LENGTH);
		assertTrue(optionalPower.isPresent());
		assertEquals(2, optionalPower.get());

		optionalUnit = composedUnit.getBaseUnit(TIME);
		assertTrue(optionalUnit.isPresent());
		assertEquals(SECOND, optionalUnit.get());
		optionalPower = composedUnit.getPower(TIME);
		assertTrue(optionalPower.isPresent());
		assertEquals(-3, optionalPower.get());

		// precondition test: if the base unit is null
		ContractException contractException = assertThrows(ContractException.class, () -> {
			ComposedUnit.builder()//
					.setBaseUnit(null, 1)//
					.build();
		});

		assertEquals(MeasuresError.NULL_UNIT, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = ComposedUnit.Builder.class, name = "setLongName", args = { String.class })
	public void testSetLongName() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		// sample 1 -- without setting long name
		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.build();
		String actualValue = composedUnit.getLongName();
		String expectedValue = composedUnit.getLongLabel();
		assertEquals(expectedValue, actualValue);

		// sample 2 -- with setting long name
		expectedValue = "miles_per_hour";
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.setLongName(expectedValue).build();
		actualValue = composedUnit.getLongName();
		assertEquals(expectedValue, actualValue);

		// sample 3 -- with setting long name to null
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.setLongName(null).build();
		expectedValue = composedUnit.getLongLabel();
		actualValue = composedUnit.getLongName();
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.Builder.class, name = "setShortName", args = { String.class })
	public void testSetShortName() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

		// sample 1 -- without setting short name
		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.build();
		String actualValue = composedUnit.getShortName();
		String expectedValue = composedUnit.getShortLabel();
		assertEquals(expectedValue, actualValue);

		// sample 2 -- with setting short name
		expectedValue = "mph";
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(MILE, 1)//
				.setBaseUnit(HOUR, -1)//
				.setShortName(expectedValue).build();
		actualValue = composedUnit.getShortName();
		assertEquals(expectedValue, actualValue);

		// sample 3 -- with setting short name to null
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.setShortName(null).build();
		actualValue = composedUnit.getShortName();
		expectedValue = composedUnit.getShortLabel();
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = ComposedUnit.Builder.class, name = "setNames", args = { String.class, String.class })
	public void testSetNames() {
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");

		// case 1 -- null long name, null short name
		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.setNames(null, null)//
				.build();

		String actualValue = composedUnit.getLongName();
		String expectedValue = composedUnit.getLongLabel();
		assertEquals(expectedValue, actualValue);

		actualValue = composedUnit.getShortName();
		expectedValue = composedUnit.getShortLabel();
		assertEquals(expectedValue, actualValue);

		// case 2 -- null long name, non-null short name
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.setNames(null, "short_name").build();

		actualValue = composedUnit.getLongName();
		expectedValue = composedUnit.getLongLabel();
		assertEquals(expectedValue, actualValue);

		actualValue = composedUnit.getShortName();
		expectedValue = "short_name";
		assertEquals(expectedValue, actualValue);

		// case 3 -- non-null long name, null short name
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.setNames("long_name", null).build();

		actualValue = composedUnit.getLongName();
		expectedValue = "long_name";
		assertEquals(expectedValue, actualValue);

		actualValue = composedUnit.getShortName();
		expectedValue = composedUnit.getShortLabel();
		assertEquals(expectedValue, actualValue);

		// case 4 -- non-null long name, non-null short name
		composedUnit = ComposedUnit.builder()//
				.setBaseUnit(METER, 1)//
				.setBaseUnit(SECOND, -1)//
				.setNames("long_name", "short_name").build();

		actualValue = composedUnit.getLongName();
		expectedValue = "long_name";
		assertEquals(expectedValue, actualValue);

		actualValue = composedUnit.getShortName();
		expectedValue = "short_name";
		assertEquals(expectedValue, actualValue);
	}

}
