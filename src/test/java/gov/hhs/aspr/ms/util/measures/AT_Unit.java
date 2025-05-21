package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_Unit {
	@Test
	@UnitTestConstructor(target = Unit.class, args = { UnitType.class, String.class, String.class })
	public void testUnit_UnitType() {

		// postcondition tests are covered by the remaining tests

		UnitType LENGTH = new UnitType("length");

		// precondition test: if the name is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new Unit(LENGTH, null, "short_name"));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the name is empty or contains only white space
		contractException = assertThrows(ContractException.class, () -> new Unit(LENGTH, "", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Unit(LENGTH, " ", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is null
		contractException = assertThrows(ContractException.class, () -> new Unit(LENGTH, "long_name", null));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is empty or contains only white space
		contractException = assertThrows(ContractException.class, () -> new Unit(LENGTH, "long_name", ""));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Unit(LENGTH, "long_name", " "));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the measure is null
		contractException = assertThrows(ContractException.class, () -> new Unit(null, "long_name", "short_name"));
		assertEquals(MeasuresError.NULL_UNIT_TYPE, contractException.getErrorType());
	}

	@Test
	@UnitTestConstructor(target = Unit.class, args = { Unit.class, double.class, String.class, String.class })
	public void testUnit_Unit() {
		// postcondition tests are covered by the remaining tests

		UnitType LENGTH = new UnitType("length");
		Unit METER = new Unit(LENGTH, "meter", "m");

		// precondition test: if the long name is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new Unit(METER, 10, null, "short_name"));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the long name is empty or contains only white space
		contractException = assertThrows(ContractException.class, () -> new Unit(METER, 10, "", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Unit(METER, 10, " ", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is null
		contractException = assertThrows(ContractException.class, () -> new Unit(METER, 10, "long_name", null));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is empty or contains only white space
		contractException = assertThrows(ContractException.class, () -> new Unit(METER, 10, "long_name", ""));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Unit(METER, 10, "long_name", " "));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the unit is null
		contractException = assertThrows(ContractException.class, () -> new Unit(null, 10, "long_name", "short_name"));
		assertEquals(MeasuresError.NULL_UNIT, contractException.getErrorType());

		// precondition test: if the scalar is not positive
		contractException = assertThrows(ContractException.class, () -> new Unit(METER, -2, "long_name", "short_name"));
		assertEquals(MeasuresError.NON_POSITIVE_SCALAR_VALUE, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = Unit.class, name = "equals", args = { Object.class })
	public void testEquals() {
		// units are equal if and only if they have the same contents

		UnitType LENGTH = new UnitType("length");
		UnitType TIME = new UnitType("time");

		// Length
		Unit A = new Unit(LENGTH, "meter", "m");
		Unit B = new Unit(LENGTH, "meter", "m");
		Unit C = new Unit(LENGTH, "meter", "c");
		Unit D = new Unit(LENGTH, "d", "m");
		Unit E = new Unit(A, 1, "meter", "m");
		Unit F = new Unit(A, 1.6, "meter", "m");
		Unit G = new Unit(TIME, "meter", "m");

		assertTrue(A.equals(B));
		assertFalse(A.equals(C));
		assertFalse(A.equals(D));
		assertTrue(A.equals(E));
		assertFalse(A.equals(F));
		assertFalse(A.equals(G));

		// not equal to null
		assertFalse(A.equals(null));

		// not equal to other types
		assertFalse(A.equals(new Object()));

		// reflexive
		assertTrue(A.equals(A));
		assertTrue(B.equals(B));
		assertTrue(C.equals(C));
		assertTrue(D.equals(D));
		assertTrue(E.equals(E));
		assertTrue(F.equals(F));
		assertTrue(G.equals(G));

		// symmetric, transitive, stable
		for (int i = 0; i < 5; i++) {
			assertTrue(A.equals(B));
			assertTrue(B.equals(A));
			assertTrue(A.equals(E));
			assertTrue(E.equals(A));
			assertTrue(B.equals(E));
			assertTrue(E.equals(B));
		}
	}

	@Test
	@UnitTestMethod(target = Unit.class, name = "getUnitType", args = {})
	public void testGetUnitType() {
		UnitType LENGTH = new UnitType("length");
		UnitType TIME = new UnitType("time");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit SECOND = new Unit(TIME, "second", "s");

		Unit YARD = new Unit(METER, 3.0, "yard", "yd");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");

		assertEquals(LENGTH, METER.getUnitType());
		assertEquals(LENGTH, YARD.getUnitType());
		assertEquals(TIME, SECOND.getUnitType());
		assertEquals(TIME, MINUTE.getUnitType());
	}

	@Test
	@UnitTestMethod(target = Unit.class, name = "getLongName", args = {})
	public void tetsGetLongName() {
		UnitType LENGTH = new UnitType("length");
		UnitType TIME = new UnitType("time");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit SECOND = new Unit(TIME, "second", "s");

		Unit YARD = new Unit(METER, 3.0, "yard", "yd");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");

		assertEquals("meter", METER.getLongName());
		assertEquals("second", SECOND.getLongName());
		assertEquals("yard", YARD.getLongName());
		assertEquals("minute", MINUTE.getLongName());

	}

	@Test
	@UnitTestMethod(target = Unit.class, name = "getShortName", args = {})
	public void testGetShortName() {
		UnitType LENGTH = new UnitType("length");
		UnitType TIME = new UnitType("time");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit SECOND = new Unit(TIME, "second", "s");

		Unit YARD = new Unit(METER, 3.0, "yard", "yd");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");

		assertEquals("m", METER.getShortName());
		assertEquals("s", SECOND.getShortName());
		assertEquals("yd", YARD.getShortName());
		assertEquals("min", MINUTE.getShortName());
	}

	@Test
	@UnitTestMethod(target = Unit.class, name = "getValue", args = {})
	public void testGetValue() {
		UnitType LENGTH = new UnitType("length");
		UnitType TIME = new UnitType("time");

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit SECOND = new Unit(TIME, "second", "s");

		Unit YARD = new Unit(METER, 3.0, "yard", "yd");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");

		assertEquals(1, METER.getValue());
		assertEquals(1, SECOND.getValue());
		assertEquals(3.0, YARD.getValue());
		assertEquals(60.0, MINUTE.getValue());
	}

	@Test
	@UnitTestMethod(target = Unit.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6170924863472890528L);

		// equal objects have equal hash codes
		UnitType LENGTH = new UnitType("length");
		UnitType TIME = new UnitType("time");

		// Length
		Unit METER1 = new Unit(LENGTH, "meter", "m");
		Unit METER2 = new Unit(LENGTH, "meter", "m");
		assertEquals(METER1, METER2);
		assertEquals(METER1.hashCode(), METER2.hashCode());

		Unit SECOND1 = new Unit(TIME, "second", "s");
		Unit SECOND2 = new Unit(TIME, "second", "s");
		assertEquals(SECOND1, SECOND2);
		assertEquals(SECOND1.hashCode(), SECOND2.hashCode());

		// hash codes are reasonably distributed
		Set<Integer> hashcodes = new LinkedHashSet<>();

		for (int i = 0; i < 100; i++) {
			Unit baseUnit;
			if (randomGenerator.nextBoolean()) {
				baseUnit = new Unit(LENGTH, "name" + randomGenerator.nextInt(1000000),
						"n" + randomGenerator.nextInt(1000000));
			} else {
				baseUnit = new Unit(TIME, "name" + randomGenerator.nextInt(1000000),
						"n" + randomGenerator.nextInt(1000000));
			}
			hashcodes.add(baseUnit.hashCode());
		}
		assertEquals(100, hashcodes.size());

	}

	@Test
	@UnitTestMethod(target = Unit.class, name = "toString", args = {})
	public void testToString() {
		UnitType LENGTH = new UnitType("length");
		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit KILOMETER = new Unit(METER, 1000, "kilometer", "km");
		String actualValue = KILOMETER.toString();
		String expectedValue = "Unit [unitType=UnitType [name=length], value=1000.0, name=kilometer, shortName=km]";
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@UnitTestMethod(target = Unit.class, name = "asComposite", args = {})
	public void testAsComposite() {
		UnitType LENGTH = new UnitType("length");
		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");

		ComposedUnit expectedValue = ComposedUnit.builder().setUnit(METER, 1).build();
		ComposedUnit actualValue = METER.asComposite();
		assertEquals(expectedValue, actualValue);

		expectedValue = ComposedUnit.builder().setUnit(CM, 1).build();
		actualValue = CM.asComposite();
		assertEquals(expectedValue, actualValue);

	}
}
