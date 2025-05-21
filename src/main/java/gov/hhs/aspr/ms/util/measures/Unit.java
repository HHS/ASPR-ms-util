package gov.hhs.aspr.ms.util.measures;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * A Unit represents a unit of measure for some particular UnitType. For
 * example, meter, foot, and mile all measure length, and second, minute and
 * hour all measure time.
 */
public final class Unit {
	private final String name;
	private final double value;
	private final String shortName;
	private final UnitType unitType;

	/**
	 * Creates a Unit from another Unit and applies a scalar to that Unit. The name
	 * and short name are used in labeling.
	 * 
	 * @throws ContractException
	 *                               <ul>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT_NAME}
	 *                               if the name is null</li>
	 *                               <li>{@linkplain MeasuresError#BLANK_UNIT_NAME}
	 *                               if the name is empty or contains only white
	 *                               space characters</li>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT_NAME}
	 *                               if the short name is null</li>
	 *                               <li>{@linkplain MeasuresError#BLANK_UNIT_NAME}
	 *                               if the short name is empty or contains only
	 *                               white space characters</li>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT} if the
	 *                               unit is null</li>
	 *                               <li>{@linkplain MeasuresError#NON_POSITIVE_SCALAR_VALUE}
	 *                               if the scalar is not positive</li>
	 *                               </ul>
	 */
	public Unit(Unit unit, double scalar, String name, String shortName) {
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

		if (unit == null) {
			throw new ContractException(MeasuresError.NULL_UNIT);
		}

		if (scalar <= 0) {
			throw new ContractException(MeasuresError.NON_POSITIVE_SCALAR_VALUE);
		}

		this.unitType = unit.getUnitType();
		this.name = name;
		this.shortName = shortName;
		this.value = unit.getValue() * scalar;
	}

	/**
	 * Creates a Unit based on the given UnitType. The name and short name are used
	 * in labeling. The Unit's value will be 1.
	 * 
	 * @throws ContractException
	 *                               <ul>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT_NAME}
	 *                               if the name is null</li>
	 *                               <li>{@linkplain MeasuresError#BLANK_UNIT_NAME}
	 *                               if the name is empty or contains only white
	 *                               space characters</li>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT_NAME}
	 *                               if the short name is null</li>
	 *                               <li>{@linkplain MeasuresError#BLANK_UNIT_NAME}
	 *                               if the short name is empty or contains only
	 *                               white space characters</li>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT_TYPE}
	 *                               if the unitType is null</li>
	 *                               </ul>
	 */
	public Unit(UnitType unitType, String name, String shortName) {
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

		if (unitType == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_TYPE);
		}

		this.unitType = unitType;
		this.name = name;
		this.shortName = shortName;
		this.value = 1;
	}

	/**
	 * Returns the name of this Unit
	 */
	public String getLongName() {
		return name;
	}

	/**
	 * Returns the short name of this Unit
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Returns the UnitType of this Unit
	 */
	public UnitType getUnitType() {
		return unitType;
	}

	/**
	 * Returns the value of this Unit. Typically, one Unit for each UnitType has a
	 * value of 1 and is the basis for all other Units sharing that UnitType.
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
		result = prime * result + ((unitType == null) ? 0 : unitType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Two Units are equal if and only if the have equal UnitTypes, equal names,
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
		if (unitType == null) {
			if (other.unitType != null) {
				return false;
			}
		} else if (!unitType.equals(other.unitType)) {
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
	 * <p>
	 * Unit [unitType=UnitType [name=length], value=1000.0, name=kilometer,
	 * shortName=km]
	 */

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Unit [unitType=");
		builder.append(unitType);
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
	 * Returns this Unit as a ComposedUnit
	 */
	public ComposedUnit asComposite() {
		return ComposedUnit.builder().setUnit(this, 1).build();
	}

}
