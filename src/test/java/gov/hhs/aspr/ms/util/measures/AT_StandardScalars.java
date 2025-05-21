package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_StandardScalars {
    @Test
    @UnitTestField(target = StandardScalars.class, name = "QUECTO")
    public void test_QUECTO() {
        assertEquals(1E-30, StandardScalars.QUECTO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "RONTO")
    public void test_RONTO() {
        assertEquals(1E-27, StandardScalars.RONTO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "YOCTO")
    public void test_YOCTO() {
        assertEquals(1E-24, StandardScalars.YOCTO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "ZEPTO")
    public void test_ZEPTO() {
        assertEquals(1E-21, StandardScalars.ZEPTO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "ATTO")
    public void test_ATTO() {
        assertEquals(1E-18, StandardScalars.ATTO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "FEMTO")
    public void test_FEMTO() {
        assertEquals(1E-15, StandardScalars.FEMTO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "PICO")
    public void test_PICO() {
        assertEquals(1E-12, StandardScalars.PICO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "NANO")
    public void test_NANO() {
        assertEquals(1E-9, StandardScalars.NANO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "MICRO")
    public void test_MICRO() {
        assertEquals(1E-6, StandardScalars.MICRO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "MILLI")
    public void test_MILLI() {
        assertEquals(1E-3, StandardScalars.MILLI);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "CENTI")
    public void test_CENTI() {
        assertEquals(1E-2, StandardScalars.CENTI);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "DECI")
    public void test_DECI() {
        assertEquals(1E-1, StandardScalars.DECI);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "DECA")
    public void test_DECA() {
        assertEquals(1E1, StandardScalars.DECA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "HECTO")
    public void test_HECTO() {
        assertEquals(1E2, StandardScalars.HECTO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "KILO")
    public void test_KILO() {
        assertEquals(1E3, StandardScalars.KILO);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "MEGA")
    public void test_MEGA() {
        assertEquals(1E6, StandardScalars.MEGA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "GIGA")
    public void test_GIGA() {
        assertEquals(1E9, StandardScalars.GIGA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "TERA")
    public void test_TERA() {
        assertEquals(1E12, StandardScalars.TERA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "PETA")
    public void test_PETA() {
        assertEquals(1E15, StandardScalars.PETA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "EXA")
    public void test_EXA() {
        assertEquals(1E18, StandardScalars.EXA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "ZETTA")
    public void test_ZETTA() {
        assertEquals(1E21, StandardScalars.ZETTA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "YOTTA")
    public void test_YOTTA() {
        assertEquals(1E24, StandardScalars.YOTTA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "RONNA")
    public void test_RONNA() {
        assertEquals(1E27, StandardScalars.RONNA);
    }

    @Test
    @UnitTestField(target = StandardScalars.class, name = "QUETTA")
    public void test_QUETTA() {
        assertEquals(1E30, StandardScalars.QUETTA);
    }
}
