package gov.hhs.aspr.ms.util.measures;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.apache.commons.math3.util.FastMath;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * Represents the composition of multiple Units raised to non-zero integer
 * powers.
 */
public final class ComposedUnit {
	/*
	 * Internal class for mapping a UnitType to a Unit raised to a non-zero integer
	 * power.
	 */
	private static class UnitPower {
		private final Unit unit;
		private final Integer power;

		public UnitPower(Unit unit, Integer power) {
			super();
			this.unit = unit;
			this.power = power;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((power == null) ? 0 : power.hashCode());
			result = prime * result + ((unit == null) ? 0 : unit.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof UnitPower)) {
				return false;
			}
			UnitPower other = (UnitPower) obj;
			if (power == null) {
				if (other.power != null) {
					return false;
				}
			} else if (!power.equals(other.power)) {
				return false;
			}
			if (unit == null) {
				if (other.unit != null) {
					return false;
				}
			} else if (!unit.equals(other.unit)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("UnitPower [unit=");
			builder.append(unit);
			builder.append(", power=");
			builder.append(power);
			builder.append("]");
			return builder.toString();
		}

	}

	private final double value;
	private final String longName;
	private final String shortName;
	private final Map<UnitType, UnitPower> unitTypes = new LinkedHashMap<>();

	private static class Data {
		private Map<UnitType, UnitPower> unitTypes = new TreeMap<>((m1, m2) -> m1.getName().compareTo(m2.getName()));
		private String shortName;
		private String longName;
	}

	private ComposedUnit(Data data) {
		shortName = data.shortName;
		longName = data.longName;
		unitTypes.putAll(data.unitTypes);

		double product = 1;
		for (UnitType unitType : unitTypes.keySet()) {
			UnitPower unitPower = unitTypes.get(unitType);
			product *= FastMath.pow(unitPower.unit.getValue(), unitPower.power);
		}
		value = product;
	}

	/**
	 * Returns the normalized value that is the product of one and its Unit's values
	 * raised to their associated powers.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Returns the power for the given UnitType in this ComposedUnit. Powers that
	 * are either not set or set to zero in the builder are not present.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_UNIT_TYPE} if
	 *                           the unitType is null</li>
	 *                           </ul>
	 */
	public Optional<Integer> getPower(UnitType unitType) {
		if (unitType == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_TYPE);
		}
		UnitPower unitPower = unitTypes.get(unitType);
		if (unitPower != null) {
			return Optional.of(unitPower.power);
		}
		return Optional.ofNullable(null);
	}

	/**
	 * Returns the Units for this ComposedUnit ordered by UnitType name.
	 */
	public List<Unit> getUnits() {
		List<Unit> result = new ArrayList<>();
		for (UnitPower unitPower : unitTypes.values()) {
			result.add(unitPower.unit);
		}
		return result;
	}

	/**
	 * Returns the Unit for the given UnitType in this ComposedUnit. Powers that are
	 * either not set or set to zero in the builder are not present.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_UNIT_TYPE} if
	 *                           the unitType is null</li>
	 *                           </ul>
	 */
	public Optional<Unit> getUnit(UnitType unitType) {
		if (unitType == null) {
			throw new ContractException(MeasuresError.NULL_UNIT_TYPE);
		}
		UnitPower unitPower = unitTypes.get(unitType);
		if (unitPower != null) {
			return Optional.of(unitPower.unit);
		}
		return Optional.ofNullable(null);
	}

	/**
	 * Returns a new instance of the Builder class
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Fluent builder class for ComposedUnit
	 */
	public static class Builder {
		private Data data = new Data();

		private Builder() {
		}

		/**
		 * Sets the Unit for the Unit's UnitType. If the power is non-zero, the Unit and
		 * power replace the current Unit and power for the Unit's UnitType. If the
		 * power is zero, then the current Unit for the Unit's unitType is removed.
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain MeasuresError#NULL_UNIT} if the
		 *                           unit is null</li>
		 *                           </ul>
		 */
		public Builder setUnit(Unit unit, int power) {
			if (unit == null) {
				throw new ContractException(MeasuresError.NULL_UNIT);
			}
			if (power != 0) {
				data.unitTypes.put(unit.getUnitType(), new UnitPower(unit, power));
			} else {
				data.unitTypes.remove(unit.getUnitType());
			}
			return this;
		}

		/**
		 * Sets the Units for the ComposedUnit's UnitTypes, raised to the given power.
		 * If the power is non-zero, the Units and powers replace the current Units and
		 * powers for the Unit's UnitTypes. If the power is zero, then the current Units
		 * for the ComposedUnit's unitTypes are removed.
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain MeasuresError#NULL_COMPOSITE} if
		 *                           the composed unit is null</li>
		 *                           </ul>
		 */
		public Builder setComposedUnit(ComposedUnit composedUnit, int power) {
			if (composedUnit == null) {
				throw new ContractException(MeasuresError.NULL_COMPOSITE);
			}

			for (UnitType unitType : composedUnit.unitTypes.keySet()) {
				if (power == 0) {
					data.unitTypes.remove(unitType);
					continue;
				}
				UnitPower unitPower = composedUnit.unitTypes.get(unitType);
				UnitPower newUnitPower = new UnitPower(unitPower.unit, unitPower.power * power);
				data.unitTypes.put(unitType, newUnitPower);
			}

			return this;
		}

		/**
		 * Sets the long name for the ComposedUnit. Defaults to null. If the long name
		 * is not set or is set to null, the long name is replaced by the long label.
		 */
		public Builder setLongName(String longName) {
			data.longName = longName;
			return this;
		}

		/**
		 * Sets the short name for the ComposedUnit. Defaults to null. If the short name
		 * is not set or is set to null, the long name is replaced by the long label.
		 */
		public Builder setShortName(String shortName) {
			data.shortName = shortName;
			return this;
		}

		/**
		 * Sets the long and short names for the ComposedUnit. Defaults to null. If the
		 * long or short name is not set or is set to null, they are replaced by the
		 * long or short labels respectively.
		 */
		public Builder setNames(String longName, String shortName) {
			data.longName = longName;
			data.shortName = shortName;
			return this;
		}

		/**
		 * Returns the ComposedUnit from the collected input.
		 */
		public ComposedUnit build() {
			return new ComposedUnit(data);
		}
	}

	/**
	 * Returns true if and only if there are no units associated with this
	 * ComposedUnit. The ComposedUnit is simply the scalar value of 1.
	 */
	public boolean isUnitLess() {
		return unitTypes.isEmpty();
	}

	/**
	 * Returns true if and only if this ComposedUnit and the given ComposedUnit have
	 * the same UnitTypes(but perhaps different Units) to the same integer powers.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_COMPOSITE} if
	 *                           the ComposedUnit is null</li>
	 *                           </ul>
	 */
	public boolean isCompatible(ComposedUnit composedUnit) {
		if (composedUnit == null) {
			throw new ContractException(MeasuresError.NULL_COMPOSITE);
		}
		if (!unitTypes.keySet().equals(composedUnit.unitTypes.keySet())) {
			return false;
		}
		for (UnitType unitType : unitTypes.keySet()) {
			Integer power1 = unitTypes.get(unitType).power;
			Integer power2 = composedUnit.unitTypes.get(unitType).power;
			if (!power1.equals(power2)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Boilerplate implementation consistent with equals()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((longName == null) ? 0 : longName.hashCode());
		result = prime * result + ((unitTypes == null) ? 0 : unitTypes.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	/**
	 * Two ComposedUnits are equal if and only if their Units and powers are equal
	 * and their assigned long and short names are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ComposedUnit)) {
			return false;
		}
		ComposedUnit other = (ComposedUnit) obj;
		if (longName == null) {
			if (other.longName != null) {
				return false;
			}
		} else if (!longName.equals(other.longName)) {
			return false;
		}
		if (unitTypes == null) {
			if (other.unitTypes != null) {
				return false;
			}
		} else if (!unitTypes.equals(other.unitTypes)) {
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
	 * Returns the string representation of this ComposedUnit. Example of meter per
	 * second. UnitTypes are listed in alphabetical order.
	 * <p>
	 * ComposedUnit [value=1.0, longName=meters per second, shortName=mps,
	 * unitTypes={UnitType [name=length]=UnitPower [unit=Unit [unitType=UnitType
	 * [name=length], value=1.0, name=meter, shortName=m], power=1], UnitType
	 * [name=time]=UnitPower [unit=Unit [unitType=UnitType [name=time], value=1.0,
	 * name=second, shortName=s], power=-1]}]
	 */
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("ComposedUnit [value=");
		builder2.append(value);
		builder2.append(", longName=");
		builder2.append(longName);
		builder2.append(", shortName=");
		builder2.append(shortName);
		builder2.append(", unitTypes=");
		builder2.append(unitTypes);
		builder2.append("]");
		return builder2.toString();
	}

	/**
	 * Returns the label for this ComposedUnit based on the short names of the
	 * corresponding Units. Power values are displayed after a ^ symbol. Example for
	 * meters per second: m^1 s^-1
	 */
	public String getShortLabel() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (UnitType unitType : unitTypes.keySet()) {
			UnitPower unitPower = unitTypes.get(unitType);
			if (first) {
				first = false;
			} else {
				sb.append(" ");
			}
			sb.append(unitPower.unit.getShortName());
			sb.append("^");
			sb.append(unitPower.power);
		}
		return sb.toString();
	}

	/**
	 * Returns the long name of this ComposedUnit if one was assigned. Otherwise,
	 * the long label is returned.
	 */
	public String getLongName() {
		if (longName != null) {
			return longName;
		}
		return getLongLabel();
	}

	/**
	 * Returns the short name of this ComposedUnit if one was assigned. Otherwise,
	 * the short label is returned.
	 */
	public String getShortName() {
		if (shortName != null) {
			return shortName;
		}
		return getShortLabel();
	}

	/**
	 * Returns the label for this ComposedUnit based on the long names of the
	 * corresponding Units. Power values are displayed after a ^ symbol. Example for
	 * meters per second: meter^1 second^-1
	 */
	public String getLongLabel() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (UnitType unitType : unitTypes.keySet()) {
			UnitPower unitPower = unitTypes.get(unitType);
			if (first) {
				first = false;
			} else {
				sb.append(" ");
			}
			sb.append(unitPower.unit.getLongName());
			sb.append("^");
			sb.append(unitPower.power);
		}
		return sb.toString();
	}

	/**
	 * Returns the product of the two composites, favoring the units of the base
	 * composite over the aux composite.
	 */
	static ComposedUnit getCompositeProduct(ComposedUnit baseComposite, ComposedUnit auxComposite) {
		Builder builder = builder();

		// Favor the units of the base
		for (UnitType unitType : baseComposite.unitTypes.keySet()) {
			UnitPower unitPower = baseComposite.unitTypes.get(unitType);
			int power = unitPower.power;
			UnitPower auxUnitPower = auxComposite.unitTypes.get(unitType);
			if (auxUnitPower != null) {
				power += auxUnitPower.power;
			}
			builder.setUnit(unitPower.unit, power);
		}

		// Any unitTypes not in the base need to be captured
		for (UnitType unitType : auxComposite.unitTypes.keySet()) {
			UnitPower auxUnitPower = auxComposite.unitTypes.get(unitType);
			int power = auxUnitPower.power;
			UnitPower unitPower = baseComposite.unitTypes.get(unitType);
			if (unitPower == null) {
				builder.setUnit(auxUnitPower.unit, power);
			}
		}

		return builder.build();

	}

	/**
	 * Returns the quotient of the two composites, favoring the units of the base
	 * composite over the aux composite.
	 */
	static ComposedUnit getCompositeQuotient(ComposedUnit baseComposite, ComposedUnit auxComposite) {
		Builder builder = builder();

		// Favor the units of the base
		for (UnitType unitType : baseComposite.unitTypes.keySet()) {
			UnitPower unitPower = baseComposite.unitTypes.get(unitType);
			int power = unitPower.power;
			UnitPower auxUnitPower = auxComposite.unitTypes.get(unitType);
			if (auxUnitPower != null) {
				power -= auxUnitPower.power;
			}
			builder.setUnit(unitPower.unit, power);
		}

		// Any unitTypes not in the base need to be captured
		for (UnitType unitType : auxComposite.unitTypes.keySet()) {
			UnitPower auxUnitPower = auxComposite.unitTypes.get(unitType);
			int power = auxUnitPower.power;
			UnitPower unitPower = baseComposite.unitTypes.get(unitType);
			if (unitPower == null) {
				builder.setUnit(auxUnitPower.unit, -power);
			}
		}

		return builder.build();

	}

	static ComposedUnit getInverse(ComposedUnit composedUnit) {
		Builder builder = builder();
		for (UnitType unitType : composedUnit.unitTypes.keySet()) {
			UnitPower unitPower = composedUnit.unitTypes.get(unitType);
			builder.setUnit(unitPower.unit, -unitPower.power);
		}
		return builder.build();
	}

	static ComposedUnit getPowerComposite(ComposedUnit composedUnit, int power) {
		Builder builder = builder();
		for (UnitType unitType : composedUnit.unitTypes.keySet()) {
			UnitPower unitPower = composedUnit.unitTypes.get(unitType);
			builder.setUnit(unitPower.unit, unitPower.power * power);
		}
		return builder.build();
	}

	static ComposedUnit getRootComposite(ComposedUnit composedUnit, int root) {
		if (root < 1) {
			throw new ContractException(MeasuresError.NON_POSITIVE_ROOT);
		}
		if (root == 1) {
			return composedUnit;
		}
		Builder builder = builder();
		for (UnitType unitType : composedUnit.unitTypes.keySet()) {
			UnitPower unitPower = composedUnit.unitTypes.get(unitType);
			if (unitPower.power % root != 0) {
				throw new ContractException(MeasuresError.POWER_IS_NOT_ROOT_COMPATIBLE);
			}
			builder.setUnit(unitPower.unit, unitPower.power / root);
		}
		return builder.build();
	}
}
