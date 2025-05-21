package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_StandardUnitTypes {
    @Test
    @UnitTestField(target = StandardUnitTypes.class, name = "LENGTH")
    public void test_LENGTH() {
        assertInstanceOf(UnitType.class, StandardUnitTypes.LENGTH);
        assertEquals("length", StandardUnitTypes.LENGTH.getName());
    }

    @Test
    @UnitTestField(target = StandardUnitTypes.class, name = "MASS")
    public void test_MASS() {
        assertInstanceOf(UnitType.class, StandardUnitTypes.MASS);
        assertEquals("mass", StandardUnitTypes.MASS.getName());
    }

    @Test
    @UnitTestField(target = StandardUnitTypes.class, name = "TIME")
    public void test_TIME() {
        assertInstanceOf(UnitType.class, StandardUnitTypes.TIME);
        assertEquals("time", StandardUnitTypes.TIME.getName());
    }

    @Test
    @UnitTestField(target = StandardUnitTypes.class, name = "CURRENT")
    public void test_CURRENT() {
        assertInstanceOf(UnitType.class, StandardUnitTypes.CURRENT);
        assertEquals("current", StandardUnitTypes.CURRENT.getName());
    }

    @Test
    @UnitTestField(target = StandardUnitTypes.class, name = "TEMPERATURE")
    public void test_TEMPERATURE() {
        assertInstanceOf(UnitType.class, StandardUnitTypes.TEMPERATURE);
        assertEquals("temperature", StandardUnitTypes.TEMPERATURE.getName());
    }

    @Test
    @UnitTestField(target = StandardUnitTypes.class, name = "LUMINOSITY")
    public void test_LUMINOSITY() {
        assertInstanceOf(UnitType.class, StandardUnitTypes.LUMINOSITY);
        assertEquals("luminosity", StandardUnitTypes.LUMINOSITY.getName());
    }

    @Test
    @UnitTestField(target = StandardUnitTypes.class, name = "ANGLE")
    public void test_ANGLE() {
        assertInstanceOf(UnitType.class, StandardUnitTypes.ANGLE);
        assertEquals("angle", StandardUnitTypes.ANGLE.getName());
    }

    @Test
    @UnitTestField(target = StandardUnitTypes.class, name = "SOLID_ANGLE")
    public void test_SOLID_ANGLE() {
        assertInstanceOf(UnitType.class, StandardUnitTypes.SOLID_ANGLE);
        assertEquals("solid_angle", StandardUnitTypes.SOLID_ANGLE.getName());
    }
}
