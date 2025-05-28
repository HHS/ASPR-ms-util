package gov.hhs.aspr.ms.util.measures;

import org.apache.commons.math3.util.FastMath;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * An immutable representation of a scaled quantity with multiple integer
 * dimensions.
 */
public final class Quantity {
	private final ComposedUnit composedUnit;

	private final double value;

	/**
	 * Returns a Quantity from the given ComposedUnit and value.
	 */
	public Quantity(ComposedUnit composedUnit, double value) {
		if (composedUnit == null) {
			throw new ContractException(MeasuresError.NULL_COMPOSITE);
		}
		this.composedUnit = composedUnit;
		this.value = value;
	}

	/**
	 * Returns a Quantity from the given Unit and value. The Unit is used to create
	 * a ComposedUnit.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_UNIT} if the
	 *                           unit is null</li>
	 *                           </ul>
	 */
	public Quantity(Unit unit, double value) {
		if (unit == null) {
			throw new ContractException(MeasuresError.NULL_UNIT);
		}
		this.composedUnit = unit.asComposite();
		this.value = value;
	}

	/**
	 * Returns the value of the Quantity
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Returns the value of the Quantity cast to an int value.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>
	 *                           {@linkplain MeasuresError#VALUE_CANNOT_BE_CAST_TO_INT}
	 *                           if the value is NaN, less than Integer.MIN_VALUE,
	 *                           or greater than Integer.MAX_VALUE.</li>
	 *                           </ul>
	 */
	public int getValueAsInt() {
		if (Double.isNaN(value) || value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
			throw new ContractException(MeasuresError.VALUE_CANNOT_BE_CAST_TO_INT);
		}
		return (int) value;
	}

	/**
	 * Returns the value of the Quantity cast to a long value.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>
	 *                           {@linkplain MeasuresError#VALUE_CANNOT_BE_CAST_TO_INT}
	 *                           if the value is NaN, less than Long.MIN_VALUE, or
	 *                           greater than Long.MAX_VALUE.</li>
	 *                           </ul>
	 */
	public long getValueAsLong() {
		if (Double.isNaN(value) || value < Long.MIN_VALUE || value > Long.MAX_VALUE) {
			throw new ContractException(MeasuresError.VALUE_CANNOT_BE_CAST_TO_LONG);
		}
		return (long) value;
	}

	/**
	 * Returns the ComposedUnit of the Quantity
	 */
	public ComposedUnit getComposedUnit() {
		return composedUnit;
	}

	/**
	 * Returns true if and only if the ratio of this Quantity to the given Quantity
	 * are exactly equal.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the quantity does not have matching
	 *                           dimensions</li>
	 *                           </ul>
	 */
	public boolean eq(Quantity quantity) {
		return eq(quantity, 0);
	}

	/**
	 * Returns true if and only if the ratio quantities differ from 1 by no more
	 * than the tolerance. For non NaN valued quantities, results are reflexive,
	 * symmetric, but not necessarily transitive.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the quantity does not have matching
	 *                           dimensions</li>
	 *                           <li>{@linkplain MeasuresError#INVALID_TOLERANCE} if
	 *                           the tolerance value us not in the interval
	 *                           [0,1)</li>
	 * 
	 *                           </ul>
	 */
	public boolean eq(Quantity quantity, double tolerance) {
		validateTolerance(tolerance);
		validateQuantityNotNull(quantity);
		validateQuantityIsCompatible(quantity);
		
		double v1 = value * composedUnit.getValue();
		double v2 = quantity.value * quantity.composedUnit.getValue();
		
		if(Double.isNaN(v1)||Double.isNaN(v2)) {
			return false;
		}

		double intendedLowerBound = (1 - tolerance) * v1;
		double intendedUpperBound = (1 + tolerance) * v1;
		
		double actualLowerBound = FastMath.min(intendedLowerBound, intendedUpperBound);
		double actualUpperBound = FastMath.max(intendedLowerBound, intendedUpperBound);
		
		if (v2 < actualLowerBound || v2 > actualUpperBound) {
			return false;
		}

		intendedLowerBound = (1 - tolerance) * v2;
		intendedUpperBound = (1 + tolerance) * v2;
		
		actualLowerBound = FastMath.min(intendedLowerBound, intendedUpperBound);
		actualUpperBound = FastMath.max(intendedLowerBound, intendedUpperBound);

		
		if (v1 < actualLowerBound || v1 > actualUpperBound) {
			return false;
		}
		
		return true;
	}

	/**
	 * Returns true if and only if this Quantity is greater than the given Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the quantity does not have matching
	 *                           dimensions</li>
	 *                           </ul>
	 */
	public boolean gt(Quantity quantity) {
		validateQuantityNotNull(quantity);
		validateQuantityIsCompatible(quantity);
		double v1 = value * composedUnit.getValue();
		double v2 = quantity.value * quantity.composedUnit.getValue();
		return v1 > v2;
	}

	/**
	 * Returns true if and only if this Quantity is less than the given Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the quantity does not have matching
	 *                           dimensions</li>
	 *                           </ul>
	 */
	public boolean lt(Quantity quantity) {
		validateQuantityNotNull(quantity);
		validateQuantityIsCompatible(quantity);
		double v1 = value * composedUnit.getValue();
		double v2 = quantity.value * quantity.composedUnit.getValue();
		return v1 < v2;
	}

	/**
	 * Returns true if and only if this Quantity is greater than or equal to the
	 * given Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the quantity does not have matching
	 *                           dimensions</li>
	 *                           </ul>
	 */
	public boolean gte(Quantity quantity) {
		validateQuantityNotNull(quantity);
		validateQuantityIsCompatible(quantity);
		double v1 = value * composedUnit.getValue();
		double v2 = quantity.value * quantity.composedUnit.getValue();
		return v1 >= v2;
	}

	/**
	 * Returns true if and only if this Quantity is less than or equal to the given
	 * Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the quantity does not have matching
	 *                           dimensions</li>
	 *                           </ul>
	 */
	public boolean lte(Quantity quantity) {
		validateQuantityNotNull(quantity);
		validateQuantityIsCompatible(quantity);
		double v1 = value * composedUnit.getValue();
		double v2 = quantity.value * quantity.composedUnit.getValue();
		return v1 <= v2;
	}

	private void validateTolerance(double tolerance) {
	
		if(Double.isNaN(tolerance)) {
			throw new ContractException(MeasuresError.INVALID_TOLERANCE);
		}
		
		if (tolerance < 0 || tolerance >= 1) {
			throw new ContractException(MeasuresError.INVALID_TOLERANCE);
		}
	}

	private void validateQuantityNotNull(Quantity quantity) {
		if (quantity == null) {
			throw new ContractException(MeasuresError.NULL_QUANTITY);
		}
	}

	private void validateQuantityIsCompatible(Quantity quantity) {
		if (!composedUnit.isCompatible(quantity.composedUnit)) {
			throw new ContractException(MeasuresError.INCOMPATIBLE_UNIT_TYPES);
		}
	}

	/**
	 * Returns a new Quantity resulting from the addition of the given Quantity to
	 * this Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the quantity does not have equal powers over it
	 *                           unitTypes</li>
	 *                           </ul>
	 */
	public Quantity add(Quantity quantity) {
		validateQuantityNotNull(quantity);
		validateQuantityIsCompatible(quantity);
		if (quantity.composedUnit.getValue() == composedUnit.getValue()) {
			return new Quantity(composedUnit, value + quantity.getValue());
		}
		double v = quantity.value * quantity.composedUnit.getValue() / composedUnit.getValue();
		return new Quantity(composedUnit, value + v);
	}

	/**
	 * Returns a new Quantity resulting from the rounding of this quantity's value
	 * to a whole number based on the rounding rule.
	 */
	public Quantity round(RoundingRule roundingRule) {
		return new Quantity(composedUnit, RoundingRule.round(value, roundingRule));
	}

	/**
	 * Returns a new Quantity resulting from the subtraction of the given Quantity
	 * from this Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the quantity does not have equal powers over it
	 *                           unitTypes</li>
	 *                           </ul>
	 */
	public Quantity sub(Quantity quantity) {
		validateQuantityNotNull(quantity);
		validateQuantityIsCompatible(quantity);
		if (quantity.composedUnit.getValue() == composedUnit.getValue()) {
			return new Quantity(composedUnit, value - quantity.getValue());
		}
		double v = quantity.value * quantity.composedUnit.getValue() / composedUnit.getValue();
		return new Quantity(composedUnit, value - v);
	}

	/**
	 * Returns a new Quantity resulting from the multiplication of this Quantity by
	 * the given Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           </ul>
	 */
	public Quantity mult(Quantity quantity) {
		validateQuantityNotNull(quantity);
		ComposedUnit newComposedUnit = ComposedUnit.getCompositeProduct(composedUnit, quantity.composedUnit);
		double compositeFactor = (composedUnit.getValue() * quantity.composedUnit.getValue())
				/ newComposedUnit.getValue();
		double newValue = value * quantity.value * compositeFactor;
		return new Quantity(newComposedUnit, newValue);
	}

	/**
	 * Returns a new Quantity resulting from the division of this Quantity by the
	 * given Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           </ul>
	 */
	public Quantity div(Quantity quantity) {
		validateQuantityNotNull(quantity);
		ComposedUnit newComposedUnit = ComposedUnit.getCompositeQuotient(composedUnit, quantity.composedUnit);
		double compositeFactor = composedUnit.getValue()
				/ (newComposedUnit.getValue() * quantity.composedUnit.getValue());
		double newValue = value / quantity.value * compositeFactor;
		return new Quantity(newComposedUnit, newValue);
	}

	/**
	 * Returns a new Quantity resulting from the inversion of this Quantity
	 */
	public Quantity invert() {
		ComposedUnit newComposedUnit = ComposedUnit.getInverse(composedUnit);
		double newValue = 1.0 / value;
		return new Quantity(newComposedUnit, newValue);
	}

	/**
	 * Returns a new Quantity resulting from raising this Quantity to the given
	 * power
	 */
	public Quantity pow(int power) {
		ComposedUnit newComposedUnit = ComposedUnit.getPowerComposite(composedUnit, power);
		double newValue = FastMath.pow(value, power);
		return new Quantity(newComposedUnit, newValue);
	}

	/**
	 * Returns a new Quantity resulting from the inversion of this Quantity
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NON_POSITIVE_ROOT} if
	 *                           the root is not positive</li>
	 *                           <li>{@linkplain MeasuresError#POWER_IS_NOT_ROOT_COMPATIBLE}
	 *                           if any of the unitType powers is not divisible by
	 *                           the root</li>
	 *                           </ul>
	 */
	public Quantity root(int root) {
		ComposedUnit newComposedUnit = ComposedUnit.getRootComposite(composedUnit, root);
		double newValue = FastMath.pow(value, 1.0 / root);
		return new Quantity(newComposedUnit, newValue);
	}

	/**
	 * Returns a new Quantity that is equal to this Quantity, but has been rebased
	 * to the given ComposedUnit
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_COMPOSITE} if
	 *                           the composedUnit is null</li>
	 *                           <li>{@linkplain MeasuresError#INCOMPATIBLE_UNIT_TYPES}
	 *                           if the composedUnit is not compatible with this
	 *                           Quantity's composedUnit</li>
	 *                           </ul>
	 */
	public Quantity rebase(ComposedUnit composedUnit) {
		if (composedUnit == null) {
			throw new ContractException(MeasuresError.NULL_COMPOSITE);
		}
		if (!this.composedUnit.isCompatible(composedUnit)) {
			throw new ContractException(MeasuresError.INCOMPATIBLE_UNIT_TYPES);
		}
		double compositeFactor = this.composedUnit.getValue() / composedUnit.getValue();
		double newValue = value * compositeFactor;
		return new Quantity(composedUnit, newValue);
	}

	/**
	 * Returns a new Quantity resulting from the value multiplication of this
	 * Quantity by the given value
	 */
	public Quantity scale(double scalar) {
		return new Quantity(composedUnit, this.value * scalar);//
	}

	/**
	 * Returns a new Quantity resulting the replacement of the value.
	 */
	public Quantity setValue(double value) {
		return new Quantity(composedUnit, value);//
	}

	/**
	 * Returns a new Quantity resulting from adding 1 to the value
	 */
	public Quantity inc() {
		return new Quantity(composedUnit, value + 1);//
	}

	/**
	 * Returns a new Quantity resulting from adding the given value to the current
	 * value
	 */
	public Quantity inc(double value) {
		return new Quantity(composedUnit, this.value + value);//
	}

	/**
	 * Returns a new Quantity resulting from subtracting 1 from the value
	 */
	public Quantity dec() {
		return new Quantity(composedUnit, value - 1);//
	}

	/**
	 * Returns a new Quantity resulting from subtracting the given value from the
	 * current value
	 */

	public Quantity dec(double value) {
		return new Quantity(composedUnit, this.value - value);//
	}

	/**
	 * Returns true if and only if the value is finite
	 */
	public boolean isFinite() {
		return Double.isFinite(value);
	}

	/**
	 * Returns true if and only if the value is zero
	 */
	public boolean isZero() {
		return value == 0;
	}

	/**
	 * Returns true if and only if the value is positive
	 */
	public boolean isPositive() {
		return value > 0;
	}

	/**
	 * Returns true if and only if the value is negative
	 */
	public boolean isNegative() {
		return value < 0;
	}

	/**
	 * Returns true if and only if the value can be cast to int without loss.
	 */
	public boolean isIntValue() {
		return RoundingRule.isIntValue(value);
	}

	/**
	 * Returns true if and only if the value can be cast to long without loss.
	 */
	public boolean isLongValue() {
		return RoundingRule.isLongValue(value);
	}

	/**
	 * Returns true if and only if the value is non-positive
	 */
	public boolean isNonPositive() {
		return value <= 0;
	}

	/**
	 * Returns true if and only if the value is non-negative
	 */
	public boolean isNonNegative() {
		return value >= 0;
	}

	/**
	 * Returns true if and only if the UnitType powers of this Quantity and the
	 * given Quantity are equal.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain MeasuresError#NULL_QUANTITY} if the
	 *                           quantity is null</li>
	 *                           </ul>
	 */
	public boolean isCompatible(Quantity quantity) {
		validateQuantityNotNull(quantity);
		return composedUnit.isCompatible(quantity.composedUnit);
	}

	/**
	 * Return true if and only if each of the dimension values is zero.
	 */
	public boolean isUnitLess() {
		return composedUnit.isUnitLess();
	}

	/**
	 * Boilerplate implementation conistent with equals()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((composedUnit == null) ? 0 : composedUnit.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Two quantities are equal if and only if they have the equal composed units
	 * and equal values. For example, the quantities q1 = new Quantity(FOOT,1) and
	 * q2 = new Quantity(INCH,12) are not equal even though they are equivalent.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Quantity)) {
			return false;
		}
		Quantity other = (Quantity) obj;
		if (composedUnit == null) {
			if (other.composedUnit != null) {
				return false;
			}
		} else if (!composedUnit.equals(other.composedUnit)) {
			return false;
		}
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the string representation of this quantity. Example form of 14.5
	 * meters per second squared:
	 * <p>
	 * Quantity [composedUnit=ComposedUnit [value=1.0, longName=acceleration,
	 * shortName=acc, unitTypes={Measure [name=length]=UnitPower [unit=Unit
	 * [unitType=Measure [name=length], value=1.0, name=meter, shortName=m],
	 * power=1], Measure [name=time]=UnitPower [unit=Unit [unitType=Measure
	 * [name=time], value=1.0, name=second, shortName=s], power=-2]}], value=14.5]
	 */

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Quantity [composedUnit=");
		builder.append(composedUnit);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Returns the value combined with the short label of this Quantity's
	 * ComposedUnit. Example: "12.56 m^1 sec^-1"
	 */

	public String getShortLabel() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		builder.append(" ");
		builder.append(composedUnit.getShortLabel());
		return builder.toString();
	}

	/**
	 * Returns the value combined with the long label of this Quantity's
	 * ComposedUnit. Example: "12.56 meter^1 second^-1"
	 */
	public String getLongLabel() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		builder.append(" ");
		builder.append(composedUnit.getLongLabel());
		return builder.toString();
	}

	/**
	 * Returns the value combined with the short name of this Quantity's
	 * ComposedUnit. Example: "12.56 mps"
	 */
	public String getShortName() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		builder.append(" ");
		builder.append(composedUnit.getShortName());
		return builder.toString();
	}

	/**
	 * Returns the value combined with the long name of this Quantity's
	 * ComposedUnit. Example: "12.56 meters per second"
	 */
	public String getLongName() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		builder.append(" ");
		builder.append(composedUnit.getLongName());
		return builder.toString();
	}

}
