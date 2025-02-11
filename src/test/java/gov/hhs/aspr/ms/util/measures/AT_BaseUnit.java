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

public class AT_BaseUnit {
	@Test
	@UnitTestConstructor(target = BaseUnit.class, args = { Measure.class, String.class, String.class })
	public void testUnit_Measure() {

		// postcondition tests are covered by the remaining tests

		Measure LENGTH = new Measure("length");

		// precondition test: if the name is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new BaseUnit(LENGTH, null, "short_name"));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the name is empty or contains only white space
		contractException = assertThrows(ContractException.class, () -> new BaseUnit(LENGTH, "", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new BaseUnit(LENGTH, " ", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is null
		contractException = assertThrows(ContractException.class, () -> new BaseUnit(LENGTH, "long_name", null));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is empty or contains only white space
		contractException = assertThrows(ContractException.class, () -> new BaseUnit(LENGTH, "long_name", ""));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new BaseUnit(LENGTH, "long_name", " "));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the measure is null
		contractException = assertThrows(ContractException.class, () -> new BaseUnit(null, "long_name", "short_name"));
		assertEquals(MeasuresError.NULL_MEASURE, contractException.getErrorType());
	}

	@Test
	@UnitTestConstructor(target = BaseUnit.class, args = { BaseUnit.class, double.class, String.class, String.class })
	public void testUnit_Unit() {
		// postcondition tests are covered by the remaining tests

		Measure LENGTH = new Measure("length");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");

		// precondition test: if the long name is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new BaseUnit(METER, 10, null, "short_name"));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the long name is empty or contains only white space
		contractException = assertThrows(ContractException.class, () -> new BaseUnit(METER, 10, "", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new BaseUnit(METER, 10, " ", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is null
		contractException = assertThrows(ContractException.class, () -> new BaseUnit(METER, 10, "long_name", null));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is empty or contains only white space
		contractException = assertThrows(ContractException.class, () -> new BaseUnit(METER, 10, "long_name", ""));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new BaseUnit(METER, 10, "long_name", " "));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the unit is null
		contractException = assertThrows(ContractException.class,
				() -> new BaseUnit(null, 10, "long_name", "short_name"));
		assertEquals(MeasuresError.NULL_UNIT, contractException.getErrorType());

		// precondition test: if the scalar is not positive
		contractException = assertThrows(ContractException.class,
				() -> new BaseUnit(METER, -2, "long_name", "short_name"));
		assertEquals(MeasuresError.NON_POSITIVE_SCALAR_VALUE, contractException.getErrorType());
	}

	@Test
	@UnitTestMethod(target = BaseUnit.class, name = "equals", args = { Object.class })
	public void testEquals() {
		// units are equal if and only if they have the same contents

		Measure LENGTH = new Measure("length");
		Measure TIME = new Measure("time");

		// Length
		BaseUnit A = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit B = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit C = new BaseUnit(LENGTH, "meter", "c");
		BaseUnit D = new BaseUnit(LENGTH, "d", "m");
		BaseUnit E = new BaseUnit(A, 1, "meter", "m");
		BaseUnit F = new BaseUnit(A, 1.6, "meter", "m");
		BaseUnit G = new BaseUnit(TIME, "meter", "m");

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
	@UnitTestMethod(target = BaseUnit.class, name = "getMeasure", args = {})
	public void testGetMeasure() {
		Measure LENGTH = new Measure("length");
		Measure TIME = new Measure("time");
		
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		
		BaseUnit YARD = new BaseUnit(METER, 3.0, "yard", "yd");
		BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
		
		assertEquals(LENGTH, METER.getMeasure());
		assertEquals(LENGTH, YARD.getMeasure());
		assertEquals(TIME, SECOND.getMeasure());
		assertEquals(TIME, MINUTE.getMeasure());
	}

	@Test
	@UnitTestMethod(target = BaseUnit.class, name = "getLongName", args = {})
	public void tetsGetLongName() {
		Measure LENGTH = new Measure("length");
		Measure TIME = new Measure("time");
		
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		
		BaseUnit YARD = new BaseUnit(METER, 3.0, "yard", "yd");
		BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
		
		assertEquals("meter", METER.getLongName());
		assertEquals("second", SECOND.getLongName());
		assertEquals("yard", YARD.getLongName());
		assertEquals("minute", MINUTE.getLongName());
		
	}

	@Test
	@UnitTestMethod(target = BaseUnit.class, name = "getShortName", args = {})
	public void testGetShortName() {
		Measure LENGTH = new Measure("length");
		Measure TIME = new Measure("time");
		
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		
		BaseUnit YARD = new BaseUnit(METER, 3.0, "yard", "yd");
		BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
		
		assertEquals("m", METER.getShortName());
		assertEquals("s", SECOND.getShortName());
		assertEquals("yd", YARD.getShortName());
		assertEquals("min", MINUTE.getShortName());
	}

	@Test
	@UnitTestMethod(target = BaseUnit.class, name = "getValue", args = {})
	public void testGetValue() {
		Measure LENGTH = new Measure("length");
		Measure TIME = new Measure("time");
		
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
		
		BaseUnit YARD = new BaseUnit(METER, 3.0, "yard", "yd");
		BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
		
		assertEquals(1, METER.getValue());
		assertEquals(1, SECOND.getValue());
		assertEquals(3.0, YARD.getValue());
		assertEquals(60.0, MINUTE.getValue());
	}

	@Test
	@UnitTestMethod(target = BaseUnit.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6170924863472890528L);
		
		//equal objects have equal hash codes
		Measure LENGTH = new Measure("length");
		Measure TIME = new Measure("time");

		// Length
		BaseUnit METER1 = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit METER2 = new BaseUnit(LENGTH, "meter", "m");
		assertEquals(METER1, METER2);
		assertEquals(METER1.hashCode(), METER2.hashCode());
		
		BaseUnit SECOND1 = new BaseUnit(TIME, "second", "s");
		BaseUnit SECOND2 = new BaseUnit(TIME, "second", "s");
		assertEquals(SECOND1, SECOND2);
		assertEquals(SECOND1.hashCode(), SECOND2.hashCode());
		
		
		//hash codes are reasonably distributed
		Set<Integer>hashcodes = new LinkedHashSet<>();
		
		for(int i= 0;i<100;i++) {
			BaseUnit baseUnit;
			if(randomGenerator.nextBoolean()) {
				baseUnit = new BaseUnit(LENGTH, "name"+randomGenerator.nextInt(1000000), "n"+randomGenerator.nextInt(1000000));
			}else {
				baseUnit = new BaseUnit(TIME, "name"+randomGenerator.nextInt(1000000), "n"+randomGenerator.nextInt(1000000));
			}
			hashcodes.add(baseUnit.hashCode());
		}
		assertEquals(100, hashcodes.size());
		
	}

	@Test
	@UnitTestMethod(target = BaseUnit.class, name = "toString", args = {})
	public void testToString() {
		Measure LENGTH = new Measure("length");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit KILOMETER = new BaseUnit(METER, 1000, "kilometer", "km");
		String actualValue = KILOMETER.toString();
		String expectedValue = "BaseUnit [measure=Measure [name=length], value=1000.0, name=kilometer, shortName=km]";
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	@UnitTestMethod(target = BaseUnit.class, name = "asComposedUnit", args = {})
	public void testAsComposedUnit() {
		Measure LENGTH = new Measure("length");
		BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
		BaseUnit CM = new BaseUnit(METER, 0.01,"centimeter", "cm");
		
		ComposedUnit expectedValue = ComposedUnit.builder().setBaseUnit(METER, 1).build();
		ComposedUnit actualValue = METER.asComposedUnit();
		assertEquals(expectedValue, actualValue);
		
		expectedValue = ComposedUnit.builder().setBaseUnit(CM, 1).build();
		actualValue = CM.asComposedUnit();
		assertEquals(expectedValue, actualValue);
		

	}
}
