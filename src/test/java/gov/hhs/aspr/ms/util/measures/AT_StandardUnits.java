package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_StandardUnits {
    @Test
    @UnitTestField(target = StandardUnits.class, name = "METER")
    public void test_METER() {
        assertInstanceOf(Unit.class, StandardUnits.METER);
        assertEquals("meter", StandardUnits.METER.getLongName());
        assertEquals("m", StandardUnits.METER.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "CM")
    public void test_CM() {
        assertInstanceOf(Unit.class, StandardUnits.CM);
        assertEquals("centimeter", StandardUnits.CM.getLongName());
        assertEquals("cm", StandardUnits.CM.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "DM")
    public void test_DM() {
        assertInstanceOf(Unit.class, StandardUnits.DM);
        assertEquals("decimeter", StandardUnits.DM.getLongName());
        assertEquals("dm", StandardUnits.DM.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "INCH")
    public void test_INCH() {
        assertInstanceOf(Unit.class, StandardUnits.INCH);
        assertEquals("inch", StandardUnits.INCH.getLongName());
        assertEquals("in", StandardUnits.INCH.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "FOOT")
    public void test_FOOT() {
        assertInstanceOf(Unit.class, StandardUnits.FOOT);
        assertEquals("foot", StandardUnits.FOOT.getLongName());
        assertEquals("ft", StandardUnits.FOOT.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "MILE")
    public void test_MILE() {
        assertInstanceOf(Unit.class, StandardUnits.MILE);
        assertEquals("mile", StandardUnits.MILE.getLongName());
        assertEquals("mi", StandardUnits.MILE.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "SECOND")
    public void test_SECOND() {
        assertInstanceOf(Unit.class, StandardUnits.SECOND);
        assertEquals("second", StandardUnits.SECOND.getLongName());
        assertEquals("s", StandardUnits.SECOND.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "MINUTE")
    public void test_MINUTE() {
        assertInstanceOf(Unit.class, StandardUnits.MINUTE);
        assertEquals("minute", StandardUnits.MINUTE.getLongName());
        assertEquals("min", StandardUnits.MINUTE.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "HOUR")
    public void test_HOUR() {
        assertInstanceOf(Unit.class, StandardUnits.HOUR);
        assertEquals("hour", StandardUnits.HOUR.getLongName());
        assertEquals("h", StandardUnits.HOUR.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "DAY")
    public void test_DAY() {
        assertInstanceOf(Unit.class, StandardUnits.DAY);
        assertEquals("day", StandardUnits.DAY.getLongName());
        assertEquals("d", StandardUnits.DAY.getShortName());
    }
    
    @Test
    @UnitTestField(target = StandardUnits.class, name = "WEEK")
    public void test_WEEK() {
        assertInstanceOf(Unit.class, StandardUnits.WEEK);
        assertEquals("week", StandardUnits.WEEK.getLongName());
        assertEquals("d", StandardUnits.DAY.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "KILOGRAM")
    public void test_KILOGRAM() {
        assertInstanceOf(Unit.class, StandardUnits.KILOGRAM);
        assertEquals("kilogram", StandardUnits.KILOGRAM.getLongName());
        assertEquals("kg", StandardUnits.KILOGRAM.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "GRAM")
    public void test_GRAM() {
        assertInstanceOf(Unit.class, StandardUnits.GRAM);
        assertEquals("gram", StandardUnits.GRAM.getLongName());
        assertEquals("g", StandardUnits.GRAM.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "MILLIGRAM")
    public void test_MILLIGRAM() {
        assertInstanceOf(Unit.class, StandardUnits.MILLIGRAM);
        assertEquals("milligram", StandardUnits.MILLIGRAM.getLongName());
        assertEquals("mg", StandardUnits.MILLIGRAM.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "MICROGRAM")
    public void test_MICROGRAM() {
        assertInstanceOf(Unit.class, StandardUnits.MICROGRAM);
        assertEquals("microgram", StandardUnits.MICROGRAM.getLongName());
        assertEquals("mcg", StandardUnits.MICROGRAM.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "AMPERE")
    public void test_AMPERE() {
        assertInstanceOf(Unit.class, StandardUnits.AMPERE);
        assertEquals("ampere", StandardUnits.AMPERE.getLongName());
        assertEquals("A", StandardUnits.AMPERE.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "KELVIN")
    public void test_KELVIN() {
        assertInstanceOf(Unit.class, StandardUnits.KELVIN);
        assertEquals("kelvin", StandardUnits.KELVIN.getLongName());
        assertEquals("K", StandardUnits.KELVIN.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "CANDELA")
    public void test_CANDELA() {
        assertInstanceOf(Unit.class, StandardUnits.CANDELA);
        assertEquals("candela", StandardUnits.CANDELA.getLongName());
        assertEquals("cd", StandardUnits.CANDELA.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "RADIAN")
    public void test_RADIAN() {
        assertInstanceOf(Unit.class, StandardUnits.RADIAN);
        assertEquals("raidan", StandardUnits.RADIAN.getLongName());
        assertEquals("rad", StandardUnits.RADIAN.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "DEGREE")
    public void test_DEGREE() {
        assertInstanceOf(Unit.class, StandardUnits.DEGREE);
        assertEquals("degree", StandardUnits.DEGREE.getLongName());
        assertEquals("deg", StandardUnits.DEGREE.getShortName());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "STERADIAN")
    public void test_STERADIAN() {
        assertInstanceOf(Unit.class, StandardUnits.STERADIAN);
        assertEquals("steradian", StandardUnits.STERADIAN.getLongName());
        assertEquals("st", StandardUnits.STERADIAN.getShortName());
    }
}
