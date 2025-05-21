package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

public class AT_UnitType {

	@Test
	@UnitTestConstructor(target = UnitType.class, args = { String.class })
	public void testMeasure() {
		// there are no postcondition tests

		// precondition test: if the name is null
		ContractException contractException = assertThrows(ContractException.class, () -> new UnitType(null));
		assertEquals(MeasuresError.NULL_UNIT_TYPE_NAME, contractException.getErrorType());

		// precondition test: if the name is blank
		contractException = assertThrows(ContractException.class, () -> new UnitType(""));
		assertEquals(MeasuresError.BLANK_UNIT_TYPE_NAME, contractException.getErrorType());

		// precondition test: if the name is blank
		contractException = assertThrows(ContractException.class, () -> new UnitType(" "));
		assertEquals(MeasuresError.BLANK_UNIT_TYPE_NAME, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = UnitType.class, name = "equals", args = { Object.class })
	public void testEquals() {

		// only unitTypes with the same name are equal
		assertEquals(new UnitType("a"), new UnitType("a"));
		assertNotEquals(new UnitType("a"), new UnitType("b"));
		assertNotEquals(new UnitType("a"), new UnitType("A"));

		// not equal to null
		UnitType a = new UnitType("a");
		UnitType b = new UnitType("b");
		assertFalse(a.equals(null));
		assertFalse(b.equals(null));

		// not equal to other object
		assertFalse(a.equals(new Object()));

		// reflexive
		assertTrue(a.equals(a));
		assertTrue(b.equals(b));

		// symmetric, transitive, stable
		UnitType c1 = new UnitType("c");
		UnitType c2 = new UnitType("c");
		for (int i = 0; i < 5; i++) {
			assertTrue(c1.equals(c2));
			assertTrue(c2.equals(c1));
		}
	}

	@Test
	@UnitTestMethod(target = UnitType.class, name = "getName", args = {})
	public void testGetName() {

		for (int i = 0; i < 30; i++) {
			String expectedValue = "name" + i;
			UnitType unitType = new UnitType(expectedValue);
			String actualValue = unitType.getName();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = UnitType.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3826969971798275453L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			String name = "name"+randomGenerator.nextInt(1000000000);
			UnitType m1 = new UnitType(name);
			UnitType m2 = new UnitType(name);
			assertEquals(m1, m2);
			assertEquals(m1.hashCode(), m2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {			
			UnitType unitType = new UnitType("name"+randomGenerator.nextInt(1000000000));
			hashCodes.add(unitType.hashCode());
		}
		assertEquals(100, hashCodes.size());
		

	}

	@Test
	@UnitTestMethod(target = UnitType.class, name = "toString", args = {})
	public void testToString() {
		UnitType unitType = new UnitType("someMeasure");
		String actualValue = unitType.toString();
		String expectedValue = "Measure [name=someMeasure]";
		assertEquals(expectedValue, actualValue);
	}

}
