package gov.hhs.aspr.ms.util.measures;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * Represents a constant Quantity with long and short names.
 */
public final class Constant {
	private final String longName;
	private final String shortName;
	private final Quantity quantity;

	/**
	 * Constructs the Constant from the Quantity and names
	 * 
	 * @throws ContractException
	 *                               <ul>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT_NAME }
	 *                               if the long longName is null</li>
	 *                               <li>{@linkplain MeasuresError#BLANK_UNIT_NAME }
	 *                               if the long longName is empty or contains only
	 *                               white space characters</li>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT_NAME }
	 *                               if the short longName is null</li>
	 *                               <li>{@linkplain MeasuresError#BLANK_UNIT_NAME }
	 *                               if the short longName is empty or contains only
	 *                               white space characters</li>
	 *                               <li>{@linkplain MeasuresError#NULL_QUANTITY }
	 *                               if the quantity is null</li>
	 *                               </ul>
	 */
	public Constant(Quantity quantity, String longName, String shortName) {

		if (longName == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_NAME);
		}

		if (longName.isBlank()) {
			throw new ContractException(MeasuresError.BLANK_UNIT_NAME);
		}

		if (shortName == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_NAME);
		}

		if (shortName.isBlank()) {
			throw new ContractException(MeasuresError.BLANK_UNIT_NAME);
		}

		if (quantity == null) {
			throw new ContractException(MeasuresError.NULL_QUANTITY);
		}

		this.quantity = quantity;
		this.longName = longName;
		this.shortName = shortName;
	}

	/**
	 * Returns the long name
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * Returns the short name
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Returns the Quantity
	 */
	public Quantity getQuantity() {
		return quantity;
	}

	/**
	 * Boilerplate implementation consistent with equals
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((longName == null) ? 0 : longName.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	/**
	 * Two Constants are equal if and only if their Quantities and names are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Constant)) {
			return false;
		}
		Constant other = (Constant) obj;
		if (longName == null) {
			if (other.longName != null) {
				return false;
			}
		} else if (!longName.equals(other.longName)) {
			return false;
		}
		if (quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (!quantity.equals(other.quantity)) {
			return false;
		}
		if (shortName == null) {
			if (other.shortName != null) {
				return false;
			}
		} else if (!shortName.equals(other.shortName)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the string representation of this Constant. Example: earth gravity =
	 * 9.80665 meters per second squared.
	 * <p>
	 * Constant [longName=earth_gravity, shortName=g, quantity=Quantity
	 * [composedUnit=ComposedUnit [value=1.0, longName=null, shortName=null,
	 * unitTypes={Measure [name=length]=UnitPower [unit=Unit [unitType=Measure
	 * [name=length], value=1.0, name=meter, shortName=m], power=1], Measure
	 * [name=time]=UnitPower [unit=Unit [unitType=Measure [name=time], value=1.0,
	 * name=second, shortName=s], power=-2]}], value=9.80665]]
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Constant [longName=");
		builder.append(longName);
		builder.append(", shortName=");
		builder.append(shortName);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Returns the Constant represented as its value concatenated with its short
	 * name. Example, 12 mph
	 */
	public String getShortString(Quantity quantity) {
		double convertedValue = getConvertedValue(quantity);
		return convertedValue + " " + shortName;
	}

	/**
	 * Returns the Constant represented as its value concatenated with its short
	 * name. Example, 12 miles per hour
	 */
	public String getLongString(Quantity quantity) {
		double convertedValue = getConvertedValue(quantity);
		return convertedValue + " " + longName;
	}

	/**
	 * Returns the value of the Quantity resulting from the division of the given
	 * Quantity and the Quantity contained in this Constant.
	 * 
	 * @throws ContractException
	 *                               <ul>
	 *                               <li>{@linkplain MeasuresError#NULL_QUANTITY} if
	 *                               the quantity is null</li>
	 *                               <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                               if the quantity does not have the same
	 *                               UnitTypes to the same powers</li>
	 *                               </ul>
	 */
	public double getConvertedValue(Quantity quantity) {
		if (quantity == null) {
			throw new ContractException(MeasuresError.NULL_QUANTITY);
		}
		if (!quantity.getComposedUnit().isCompatible(this.quantity.getComposedUnit())) {
			throw new ContractException(MeasuresError.INCOMPATIBLE_UNIT_TYPES);
		}

		double v1 = this.quantity.getValue() * this.quantity.getComposedUnit().getValue();
		double v2 = quantity.getValue() * quantity.getComposedUnit().getValue();
		return v2 / v1;
	}

}
