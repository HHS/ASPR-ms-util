package gov.hhs.aspr.ms.util.measures;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * Represents a type of thing to be measured that is irreducible. Examples:
 * distance, time and mass. Volume would not be good choice for a UnitType since
 * a volume can be viewed as the cube of distance. Each UnitType is identified
 * by its name.
 */
public final class UnitType {

	private final String name;

	/**
	 * Constructs the UnitType from the given name. Names are by convention in lower
	 * snake case.
	 * 
	 * @throws ContractException
	 *                               <ul>
	 *                               <li>{@linkplain MeasuresError#NULL_UNIT_TYPE_NAME}
	 *                               if the name is null</li>
	 *                               <li>{@linkplain MeasuresError#BLANK_UNIT_TYPE_NAME}
	 *                               if the name is empty of contains only white
	 *                               space characters</li>
	 *                               </ul>
	 */
	public UnitType(String name) {
		if (name == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_TYPE_NAME);
		}
		if (name.isBlank()) {
			throw new ContractException(MeasuresError.BLANK_UNIT_TYPE_NAME);
		}
		this.name = name;
	}

	/**
	 * Returns the name of the UnitType
	 */
	public String getName() {
		return name;
	}

	/**
	 * Standard boilerplate implementation consistent with equals()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Two UnitTypes are equal if and only if their names are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UnitType)) {
			return false;
		}
		UnitType other = (UnitType) obj;
		if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the string representation of the UnitType in the form:
	 * <p>
	 * UnitType [name=X]
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UnitType [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
