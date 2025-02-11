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
 * Represents the composition of multiple base units raised to non-zero integer
 * powers.
 */
public final class ComposedUnit {
	/*
	 * Internal class for mapping a measure to a base unit raised to a non-zero
	 * integer power.
	 */
	private static class UnitPower {
		private final BaseUnit baseUnit;
		private final Integer power;

		public UnitPower(BaseUnit baseUnit, Integer power) {
			super();
			this.baseUnit = baseUnit;
			this.power = power;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((power == null) ? 0 : power.hashCode());
			result = prime * result + ((baseUnit == null) ? 0 : baseUnit.hashCode());
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
			if (baseUnit == null) {
				if (other.baseUnit != null) {
					return false;
				}
			} else if (!baseUnit.equals(other.baseUnit)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("UnitPower [baseUnit=");
			builder.append(baseUnit);
			builder.append(", power=");
			builder.append(power);
			builder.append("]");
			return builder.toString();
		}

	}

	private final double value;
	private final String longName;
	private final String shortName;
	private final Map<Measure, UnitPower> measures = new LinkedHashMap<>();

	private static class Data {
		private Map<Measure, UnitPower> measures = new TreeMap<>((m1, m2) -> m1.getName().compareTo(m2.getName()));
		private String shortName;
		private String longName;
	}

	private ComposedUnit(Data data) {
		shortName = data.shortName;
		longName = data.longName;
		measures.putAll(data.measures);

		double product = 1;
		for (Measure measure : measures.keySet()) {
			UnitPower unitPower = measures.get(measure);
			product *= FastMath.pow(unitPower.baseUnit.getValue(), unitPower.power);
		}
		value = product;
	}

	/**
	 * Returns the normalized value that is the product of one and its base unit's
	 * values raised to their associated powers.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Returns the power for the given measure in this composed unit. Powers that
	 * are either not set or set to zero in the builder are not present.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_MEASURE} if the
	 *                           measure is null</li>
	 *                           </ul>
	 */
	public Optional<Integer> getPower(Measure measure) {
		if (measure == null) {
			throw new ContractException(MeasuresError.NULL_MEASURE);
		}
		UnitPower unitPower = measures.get(measure);
		if (unitPower != null) {
			return Optional.of(unitPower.power);
		}
		return Optional.ofNullable(null);
	}
	
	/**
	 * Returns the base units for this composed unit ordered by measure name.
	 * 
	 */
	public List<BaseUnit> getBaseUnits() {
		List<BaseUnit> result = new ArrayList<>();
		for (UnitPower unitPower : measures.values()) {
			result.add(unitPower.baseUnit);
		}
		return result;
	}

	/**
	 * Returns the b for the given measure in this composed unit. Powers that are
	 * either not set or set to zero in the builder are not present.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_MEASURE} if the
	 *                           measure is null</li>
	 *                           </ul>
	 */
	public Optional<BaseUnit> getBaseUnit(Measure measure) {
		if (measure == null) {
			throw new ContractException(MeasuresError.NULL_MEASURE);
		}
		UnitPower unitPower = measures.get(measure);
		if (unitPower != null) {
			return Optional.of(unitPower.baseUnit);
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
		 * Sets the unit for the unit's measure. If the power is non-zero, the base unit
		 * and power replace the current unit and power for the unit's measure. If the
		 * power is zero, then the current unit for the unit's measure is removed.
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain MeasuresError#NULL_UNIT} if the
		 *                           base unit is null</li>
		 *                           </ul>
		 */
		public Builder setBaseUnit(BaseUnit baseUnit, int power) {
			if (baseUnit == null) {
				throw new ContractException(MeasuresError.NULL_UNIT);
			}
			if (power != 0) {
				data.measures.put(baseUnit.getMeasure(), new UnitPower(baseUnit, power));
			} else {
				data.measures.remove(baseUnit.getMeasure());
			}
			return this;
		}

		/**
		 * Sets the long name for the composed unit. Defaults to null. If the long name
		 * is not set or is set to null, the long name is replaced by the long label.
		 */
		public Builder setLongName(String longName) {
			data.longName = longName;
			return this;
		}

		/**
		 * Sets the short name for the composed unit. Defaults to null. If the short
		 * name is not set or is set to null, the long name is replaced by the long
		 * label.
		 */
		public Builder setShortName(String shortName) {
			data.shortName = shortName;
			return this;
		}

		/**
		 * Sets the long and short names for the composed unit. Defaults to null. If the
		 * long or short name is not set or is set to null, they are replaced by the
		 * long or short labels respectively.
		 */
		public Builder setNames(String longName, String shortName) {
			data.longName = longName;
			data.shortName = shortName;
			return this;
		}

		/**
		 * Returns the composed unit from the collected input.
		 */
		public ComposedUnit build() {
			return new ComposedUnit(data);
		}
	}

	/**
	 * Returns true if and only if there are no base units associated with this
	 * composed unit. The composed unit is simply the scalar value of 1.
	 */
	public boolean isMeasureLess() {
		return measures.isEmpty();
	}

	/**
	 * Returns true if and only if this composed unit and the given composed unit
	 * have the same measures(but perhaps different base units) to the same integer
	 * powers.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_COMPOSITE} if
	 *                           the composed unit is null</li>
	 *                           </ul>
	 */
	public boolean isCompatible(ComposedUnit composedUnit) {
		if (composedUnit == null) {
			throw new ContractException(MeasuresError.NULL_COMPOSITE);
		}
		if (!measures.keySet().equals(composedUnit.measures.keySet())) {
			return false;
		}
		for (Measure measure : measures.keySet()) {
			Integer power1 = measures.get(measure).power;
			Integer power2 = composedUnit.measures.get(measure).power;
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
		result = prime * result + ((measures == null) ? 0 : measures.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	/**
	 * Two composed units are equal if and only if their units and powers are equal
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
		if (measures == null) {
			if (other.measures != null) {
				return false;
			}
		} else if (!measures.equals(other.measures)) {
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
	 * Returns the string representation of this composed unit. Example of meter per
	 * second. Measures are listed in alphabetical order.
	 * 
	 * ComposedUnit [value=1.0, longName=meters per second, shortName=mps,
	 * measures={Measure [name=length]=UnitPower [baseUnit=BaseUnit [measure=Measure
	 * [name=length], value=1.0, name=meter, shortName=m], power=1], Measure
	 * [name=time]=UnitPower [baseUnit=BaseUnit [measure=Measure [name=time],
	 * value=1.0, name=second, shortName=s], power=-1]}]
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
		builder2.append(", measures=");
		builder2.append(measures);
		builder2.append("]");
		return builder2.toString();
	}

	/**
	 * Returns the label for this composed unit based on the short names of the
	 * corresponding units. Power values are displayed after a ^ symbol. Example for
	 * meters per second: m^1 s^-1
	 */
	public String getShortLabel() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Measure measure : measures.keySet()) {
			UnitPower unitPower = measures.get(measure);
			if (first) {
				first = false;
			} else {
				sb.append(" ");
			}
			sb.append(unitPower.baseUnit.getShortName());
			sb.append("^");
			sb.append(unitPower.power);
		}
		return sb.toString();
	}

	/**
	 * Returns the long name of this composed unit if one was assigned. Otherwise,
	 * the short label is returned.
	 */
	public String getLongName() {
		if (longName != null) {
			return longName;
		}
		return getLongLabel();
	}

	/**
	 * Returns the short name of this composed unit if one was assigned. Otherwise,
	 * the short label is returned.
	 */
	public String getShortName() {
		if (shortName != null) {
			return shortName;
		}
		return getShortLabel();
	}

	/**
	 * Returns the label for this composed unit based on the long names of the
	 * corresponding units. Power values are displayed after a ^ symbol. Example for
	 * meters per second: meter^1 second^-1
	 */
	public String getLongLabel() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Measure measure : measures.keySet()) {
			UnitPower unitPower = measures.get(measure);
			if (first) {
				first = false;
			} else {
				sb.append(" ");
			}
			sb.append(unitPower.baseUnit.getLongName());
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
		for (Measure measure : baseComposite.measures.keySet()) {
			UnitPower baseUnitPower = baseComposite.measures.get(measure);
			int power = baseUnitPower.power;
			UnitPower auxUnitPower = auxComposite.measures.get(measure);
			if (auxUnitPower != null) {
				power += auxUnitPower.power;
			}
			builder.setBaseUnit(baseUnitPower.baseUnit, power);
		}

		// Any measures not in the base need to be captured
		for (Measure measure : auxComposite.measures.keySet()) {
			UnitPower auxUnitPower = auxComposite.measures.get(measure);
			int power = auxUnitPower.power;
			UnitPower baseUnitPower = baseComposite.measures.get(measure);
			if (baseUnitPower == null) {
				builder.setBaseUnit(auxUnitPower.baseUnit, power);
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
		for (Measure measure : baseComposite.measures.keySet()) {
			UnitPower baseUnitPower = baseComposite.measures.get(measure);
			int power = baseUnitPower.power;
			UnitPower auxUnitPower = auxComposite.measures.get(measure);
			if (auxUnitPower != null) {
				power -= auxUnitPower.power;
			}
			builder.setBaseUnit(baseUnitPower.baseUnit, power);
		}

		// Any measures not in the base need to be captured
		for (Measure measure : auxComposite.measures.keySet()) {
			UnitPower auxUnitPower = auxComposite.measures.get(measure);
			int power = auxUnitPower.power;
			UnitPower baseUnitPower = baseComposite.measures.get(measure);
			if (baseUnitPower == null) {
				builder.setBaseUnit(auxUnitPower.baseUnit, -power);
			}
		}

		return builder.build();

	}

	static ComposedUnit getInverse(ComposedUnit composedUnit) {
		Builder builder = builder();
		for (Measure measure : composedUnit.measures.keySet()) {
			UnitPower unitPower = composedUnit.measures.get(measure);
			builder.setBaseUnit(unitPower.baseUnit, -unitPower.power);
		}
		return builder.build();
	}

	static ComposedUnit getPowerComposite(ComposedUnit composedUnit, int power) {
		Builder builder = builder();
		for (Measure measure : composedUnit.measures.keySet()) {
			UnitPower unitPower = composedUnit.measures.get(measure);
			builder.setBaseUnit(unitPower.baseUnit, unitPower.power * power);
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
		for (Measure measure : composedUnit.measures.keySet()) {
			UnitPower unitPower = composedUnit.measures.get(measure);
			if (unitPower.power % root != 0) {
				throw new ContractException(MeasuresError.POWER_IS_NOT_ROOT_COMPATIBLE);
			}
			builder.setBaseUnit(unitPower.baseUnit, unitPower.power / root);
		}
		return builder.build();
	}

}
