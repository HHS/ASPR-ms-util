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

public class AT_Measure {

	@Test
	@UnitTestConstructor(target = Measure.class, args = { String.class })
	public void testMeasure() {
		// there are no postcondition tests

		// precondition test: if the name is null
		ContractException contractException = assertThrows(ContractException.class, () -> new Measure(null));
		assertEquals(MeasuresError.NULL_MEASURE_NAME, contractException.getErrorType());

		// precondition test: if the name is blank
		contractException = assertThrows(ContractException.class, () -> new Measure(""));
		assertEquals(MeasuresError.BLANK_MEASURE_NAME, contractException.getErrorType());

		// precondition test: if the name is blank
		contractException = assertThrows(ContractException.class, () -> new Measure(" "));
		assertEquals(MeasuresError.BLANK_MEASURE_NAME, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Measure.class, name = "equals", args = { Object.class })
	public void testEquals() {

		// only measures with the same name are equal
		assertEquals(new Measure("a"), new Measure("a"));
		assertNotEquals(new Measure("a"), new Measure("b"));
		assertNotEquals(new Measure("a"), new Measure("A"));

		// not equal to null
		Measure a = new Measure("a");
		Measure b = new Measure("b");
		assertFalse(a.equals(null));
		assertFalse(b.equals(null));

		// not equal to other object
		assertFalse(a.equals(new Object()));

		// reflexive
		assertTrue(a.equals(a));
		assertTrue(b.equals(b));

		// symmetric, transitive, stable
		Measure c1 = new Measure("c");
		Measure c2 = new Measure("c");
		for (int i = 0; i < 5; i++) {
			assertTrue(c1.equals(c2));
			assertTrue(c2.equals(c1));
		}
	}

	@Test
	@UnitTestMethod(target = Measure.class, name = "getName", args = {})
	public void testGetName() {

		for (int i = 0; i < 30; i++) {
			String expectedValue = "name" + i;
			Measure measure = new Measure(expectedValue);
			String actualValue = measure.getName();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Measure.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3826969971798275453L);

		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			String name = "name"+randomGenerator.nextInt(1000000000);
			Measure m1 = new Measure(name);
			Measure m2 = new Measure(name);
			assertEquals(m1, m2);
			assertEquals(m1.hashCode(), m2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {			
			Measure measure = new Measure("name"+randomGenerator.nextInt(1000000000));
			hashCodes.add(measure.hashCode());
		}
		assertEquals(100, hashCodes.size());
		

	}

	@Test
	@UnitTestMethod(target = Measure.class, name = "toString", args = {})
	public void testToString() {
		Measure measure = new Measure("someMeasure");
		String actualValue = measure.toString();
		String expectedValue = "Measure [name=someMeasure]";
		assertEquals(expectedValue, actualValue);
	}

}
