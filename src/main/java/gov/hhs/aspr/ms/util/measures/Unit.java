package gov.hhs.aspr.ms.util.measures;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * A BaseUnit represents a unit of measure for some particular measure. For
 * example, meter, foot, mile all measure length and second, minute and hour all
 * measure time.
 */
public final class Unit {
	private final String name;
	private final double value;
	private final String shortName;
	private final UnitType measure;

	/**
	 * Creates a unit from another unit and applies a scalar to that unit. The name
	 * and short name are used in labeling.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_UNIT_NAME} if
	 *                           the name is null</li>
	 *                           <li>{@linkplain MeasuresError#BLANK_UNIT_NAME} if
	 *                           the name is empty or contains only white space
	 *                           characters</li>
	 *                           <li>{@linkplain MeasuresError#NULL_UNIT_NAME} if
	 *                           the short name is null</li>
	 *                           <li>{@linkplain MeasuresError#BLANK_UNIT_NAME} if
	 *                           the short name is empty or contains only white
	 *                           space characters</li>
	 *                           <li>{@linkplain MeasuresError#NULL_UNIT} if the
	 *                           unit is null</li>
	 *                           <li>{@linkplain MeasuresError#NON_POSITIVE_SCALAR_VALUE}
	 *                           if the scalar is not positive</li>
	 *                           </ul>
	 */
	public Unit(Unit baseUnit, double scalar, String name, String shortName) {
		if (name == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_NAME);
		}

		if (name.isBlank()) {
			throw new ContractException(MeasuresError.BLANK_UNIT_NAME);
		}

		if (shortName == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_NAME);
		}

		if (shortName.isBlank()) {
			throw new ContractException(MeasuresError.BLANK_UNIT_NAME);
		}

		if (baseUnit == null) {
			throw new ContractException(MeasuresError.NULL_UNIT);
		}

		if (scalar <= 0) {
			throw new ContractException(MeasuresError.NON_POSITIVE_SCALAR_VALUE);
		}

		this.measure = baseUnit.getMeasure();
		this.name = name;
		this.shortName = shortName;
		this.value = baseUnit.getValue() * scalar;
	}

	/**
	 * Creates a unit based on the give measure to that unit. The name and short
	 * name are used in labeling. The unit's value will be 1.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_UNIT_NAME} if
	 *                           the name is null</li>
	 *                           <li>{@linkplain MeasuresError#BLANK_UNIT_NAME} if
	 *                           the name is empty or contains only white space
	 *                           characters</li>
	 *                           <li>{@linkplain MeasuresError#NULL_UNIT_NAME} if
	 *                           the short name is null</li>
	 *                           <li>{@linkplain MeasuresError#BLANK_UNIT_NAME} if
	 *                           the short name is empty or contains only white
	 *                           space characters</li>
	 *                           <li>{@linkplain MeasuresError#NULL_MEASURE} if the
	 *                           measure is null</li>
	 *                           </ul>
	 */
	public Unit(UnitType measure, String name, String shortName) {
		if (name == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_NAME);
		}

		if (name.isBlank()) {
			throw new ContractException(MeasuresError.BLANK_UNIT_NAME);
		}

		if (shortName == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_NAME);
		}

		if (shortName.isBlank()) {
			throw new ContractException(MeasuresError.BLANK_UNIT_NAME);
		}

		if (measure == null) {
			throw new ContractException(MeasuresError.NULL_MEASURE);
		}

		this.measure = measure;
		this.name = name;
		this.shortName = shortName;
		this.value = 1;
	}

	/**
	 * Returns the name of this unit
	 */
	public String getLongName() {
		return name;
	}

	/**
	 * Returns the short name of this unit
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Returns the measure of this unit
	 */
	public UnitType getMeasure() {
		return measure;
	}

	/**
	 * Returns the value of this unit. Typically, one unit for each measure has a
	 * value of 1 and is the basis for all other units sharing that measure.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Boilerplate implementation consistent with equals()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((measure == null) ? 0 : measure.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Two units are equal if and only if the have equal measures, equal names,
	 * equal short names and equal values.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Unit)) {
			return false;
		}
		Unit other = (Unit) obj;
		if (measure == null) {
			if (other.measure != null) {
				return false;
			}
		} else if (!measure.equals(other.measure)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (shortName == null) {
			if (other.shortName != null) {
				return false;
			}
		} else if (!shortName.equals(other.shortName)) {
			return false;
		}
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the string representation of this unit in the form:
	 * 
	 * BaseUnit [measure=Measure [name=length], value=1000.0, name=kilometer,
	 * shortName=km]
	 * 
	 */

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseUnit [measure=");
		builder.append(measure);
		builder.append(", value=");
		builder.append(value);
		builder.append(", name=");
		builder.append(name);
		builder.append(", shortName=");
		builder.append(shortName);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Returns the ComposedUnit formed from this base unit.
	 */
	public ComposedUnit asComposedUnit() {
		return ComposedUnit.builder().setBaseUnit(this, 1).build();

	}

}
