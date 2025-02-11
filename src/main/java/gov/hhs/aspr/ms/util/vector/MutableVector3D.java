package gov.hhs.aspr.ms.util.vector;

import org.apache.commons.math3.util.FastMath;

/**
 * A mutable vector class representing 3-dimensional components: v = (x,y,z).
 * (Description adapted from Wikipedia) This is a Euclidean vector (also called
 * geometric or spatial vector) that has a length (or magnitude) and direction.
 * This vector can be added to other vectors and obeys vector algebra logic.
 * 3-dimensional Euclidean space can be represented as coordinate vectors in a
 * Cartesian coordinate system. The endpoint of a vector can be identified with
 * an ordered list of 3 real numbers (3-tuple). These numbers are the
 * coordinates of the endpoint of the vector, with respect to a given Cartesian
 * coordinate system, and are typically called the scalar components (or scalar
 * projections) of the vector on the axes of the coordinate system.
 * <p>
 * All calculations conform to a right handed system. Furthermore, all
 * calculations are 'self-safe', meaning that any arguments passed in a
 * computation will not be mutated (i.e. if provided a vector a copy of that
 * vector may be made and mutated, but the vector itself will not be mutated).
 * <p>
 * It is important to note, that since this is a fully mutative vector class
 * that method chaining is discouraged to ensure proper order of operations
 * occur in calculations. This unit will not account for rounding error logic.
 * This unit is not {@code null} tolerant and passing {@code null} as a
 * parameter for a vector will result in a {@link NullPointerException}.
 *
 * @see <a href=
 *      "http://en.wikipedia.org/wiki/Euclidean_vector">http://en.wikipedia.org/wiki/Euclidean_vector</a>
 * @see <a href=
 *      "http://mathworld.wolfram.com/Vector.html">http://mathworld.wolfram.com/Vector.html</a>
 */
public final class MutableVector3D {

	private static final long ZERO_LONG_BITS = Double.doubleToLongBits(0);

	public final static double NORMAL_LENGTH_TOLERANCE = 1E-13;

	private static double crunch(final double value) {

		if (value > 1) {
			return 1;
		}
		if (value < -1) {
			return -1;
		}
		return value;
	}

	/*
	 * A function that masks Double.doubleToLongBits() by forcing the long bits
	 * representation of -0 to be that of +0.
	 */
	private static long toLongBits(final double value) {
		if (value == 0) {
			return ZERO_LONG_BITS;
		} else {
			return Double.doubleToLongBits(value);
		}
	}

	private double x;

	private double y;

	private double z;

	/**
	 * Creates a {@link MutableVector3D} with components of (0,0,0).
	 */
	public MutableVector3D() {
		// Do nothing. Defaults to a (0,0,0) vector construction.
	}

	/**
	 * Creates a {@link MutableVector3D} with the component values of (x,y,z).
	 * <p>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>{@code Vector3D(-1,2,6) = (-1,2,6)}.
	 * <li>{@code Vector3D(0,0,0) = (0,0,0)}. <b>Note:</b> This is equivalent to
	 * calling {@code Vector3D()}.
	 * </ul>
	 *
	 * @param x the {@code x} component.
	 * @param y the {@code y} component.
	 * @param z the {@code y} component.
	 */
	public MutableVector3D(final double x, final double y, final double z) {
		assign(x, y, z);
	}

	/**
	 * Creates a new {@link MutableVector3D} instance that is initialized with the
	 * coordinates values of the provided {@link MutableVector3D}.
	 * <p>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>{@code v1 = (-1,2,6)}: {@code Vector3D(v1) = (-1,2,6)}.
	 * <li>{@code v1 = (0,0,0)}: {@code Vector3D(v1) = (0,0,0)}. <b>Note:</b> This
	 * is equivalent to calling {@code Vector3D()}.
	 * </ul>
	 *
	 * @param v the initialization vector.
	 */
	public MutableVector3D(final MutableVector3D v) {
		assign(v);
	}

	/**
	 * Creates a new {@link MutableVector3D} instance that is initialized with the
	 * coordinates values of the provided {@link Vector3D}.
	 */
	public MutableVector3D(final Vector3D vector3d) {
		x = vector3d.getX();
		y = vector3d.getY();
		z = vector3d.getZ();
	}

	/**
	 * Adds scalar components to this vector.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}
	 * <ul>
	 * <li>{@code v1.add(1,-1,-1) = (-1+1,2-1,6-1) = (0,1,5)}.
	 * <li>{@code v1.add(0,0,0) = (-1,2,6)}.
	 * </ul>
	 */
	public void add(final double x, final double y, final double z) {
		this.x = this.x + x;
		this.y = this.y + y;
		this.z = this.z + z;
	}

	/**
	 * Adds the given MutableVector3D components to this MutableVector3D.
	 * <p>
	 * A {@code null} vector is treated as a (0,0,0) vector such that no addition
	 * occurs.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}: {@code v1.add(v2) = (-1+1,2-1,6-1) = (0,1,5)}.
	 * <li>{@code v2 = (0,0,0)}: {@code v1.add(v2) = (-1,2,6)}.
	 * </ul>
	 *
	 * @param v the vector to be added.
	 */
	public void add(final MutableVector3D v) {
		x = x + v.x;
		y = y + v.y;
		z = z + v.z;
	}

	/**
	 * Adds the given {@link Vector3D} components to this vector.
	 */
	public void add(final Vector3D v) {
		x = x + v.getX();
		y = y + v.getY();
		z = z + v.getZ();
	}

	/**
	 * Scales and then adds the given MutableVector3D. Adding a vector scaled by
	 * 'zero' results in a (0,0,0) vector such that no effective scaling or addition
	 * occurs.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}:
	 * <ul>
	 * <li>
	 * {@code v1.addScaled(v2,5) = (-1+5*(1),2+5*(-1),6+5*(-1)) = (-1+5,2-5,6-5) = (4,-3,1)}.
	 * <li>
	 * {@code v1.addScaled(v2,0) = (-1+0*(1),2+0*(-1),6+0*(-1)) = (-1+0,2+0,6+0) = (-1,2,6)}.
	 * </ul>
	 * <li>{@code v2 = (0,0,0)}: {@code v1.addScaled(v2,5) = (-1,2,6)}.
	 * </ul>
	 */
	public void addScaled(final MutableVector3D v, final double scalar) {
		x = x + (scalar * v.x);
		y = y + (scalar * v.y);
		z = z + (scalar * v.z);
	}

	/**
	 * Adds the given {@link Vector3D} scaled by the scalar to this
	 * {@link MutableVector3D} Adding a vector scaled by 'zero' results in a (0,0,0)
	 * vector such that no effective scaling or addition occurs.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}:
	 * <ul>
	 * <li>
	 * {@code v1.addScaled(v2,5) = (-1+5*(1),2+5*(-1),6+5*(-1)) = (-1+5,2-5,6-5) = (4,-3,1)}.
	 * <li>
	 * {@code v1.addScaled(v2,0) = (-1+0*(1),2+0*(-1),6+0*(-1)) = (-1+0,2+0,6+0) = (-1,2,6)}.
	 * </ul>
	 * <li>{@code v2 = (0,0,0)}: {@code v1.addScaled(v2,5) = (-1,2,6)}.
	 * </ul>
	 */
	public void addScaled(final Vector3D v, final double scalar) {
		x = x + (scalar * v.getX());
		y = y + (scalar * v.getY());
		z = z + (scalar * v.getZ());
	}

	/**
	 * Returns the angle in radians.
	 * <p>
	 * Recall, the angle (in radians) between two vectors can be determined by:<br>
	 * Alpha = ArcCos[Dot[v1, v2] / (Norm[v1]*Norm[v2])]
	 * <p>
	 * If the provided vector is a 'zero vector' (i.e. {@code (0,0,0)}) the angle in
	 * radians will result in a {@code NaN}. If the provided vector is equal to this
	 * vector the angle in radians will be {@code 0.0}.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}:
	 * {@code v1.angleInRadians(v2) = 2.5175154010533864}
	 * <li>{@code v2 = (0,0,0)}: {@code v1.angleInRadians(v2) = NaN}
	 * <li>{@code v1.angleInRadians(v1) = 0.0}
	 * </ul>
	 *
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Dot_product#Geometric_definition">http://en.wikipedia.org/wiki/Dot_product#Geometric_definition</a>
	 * @see <a href=
	 *      "http://mathworld.wolfram.com/DotProduct.html">http://mathworld.wolfram.com/DotProduct.html</a>
	 */
	public double angle(final MutableVector3D v) {
		return FastMath.acos(crunch(this.dot(v) / (length() * v.length())));
	}

	/**
	 * Returns the angle in radians from this {@link MutableVector3D} to the given
	 * {@link Vector3D}
	 */
	public double angle(final Vector3D v) {
		return FastMath.acos(crunch(this.dot(v) / (length() * v.length())));
	}

	/**
	 * Assigns this {@link MutableVector3D} with the component values of (x,y,z).
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v1.assign(1,-1,-1) = (1,-1,-1)}
	 * <li>{@code v1.assign(0,0,0) = (0,0,0)}
	 * </ul>
	 *
	 * @param x the {@code x} component to be added.
	 * @param y the {@code y} component to be added.
	 * @param z the {@code y} component to be added.
	 */
	public void assign(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Assigns this vector with the coordinates values of the provided vector.
	 * <p>
	 * If provided a {@code null} vector, a vector with components (0,0,0) is
	 * assigned.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}: {@code v1.assign(v2) = (1,-1,-1)}.
	 * <li>{@code v2 = (0,0,0)}: {@code v1.assign(v2) = (0,0,0)}.
	 * </ul>
	 *
	 * @param v the vector values to be assigned.
	 */
	public void assign(final MutableVector3D v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	/**
	 * Assigns this vector with the coordinates values of the provided vector.
	 */
	public void assign(final Vector3D v) {
		x = v.getX();
		y = v.getY();
		z = v.getZ();
	}

	/**
	 * Assigns this vector as the right handed cross product (also called the
	 * 'vector product').
	 * <p>
	 * Recall, that given two vectors that the cross product is the vector that is
	 * perpendicular to them. The cross product vector is therefore normal to the
	 * plane formed by the given two vectors. This is very useful for calculating a
	 * surface normal at a particular point given two distinct tangent vectors.
	 * <p>
	 * The calculation performed acts as a 'right cross product', meaning that
	 * 'this' vector is the 'left' vector and the given vector is the 'right' vector
	 * for purposes of calculating the cross product.
	 * <p>
	 * The cross product, {@code v1 X v2}, formulation is therefore,
	 *
	 * <pre>
	 * {@code
	 * v1.cross(v2)
	 * }
	 *
	 * = Norm[v1]Norm[v2]Sin[Alpha]
	 *
	 *        { i ,  j ,  k }
	 * = Det[ {v1x, v1y, v1z} ]
	 *        {v2x, v2y, v2z}
	 *
	 *   {  0 , -v1z,  v1y}    {v2x}
	 * = { v1z,   0 , -v1x} ] [{v2y}]
	 *   {-v1y,  v1z,   0 }    {v2z)
	 *
	 *     {(-v1z)(v2y) + (v1y)(v2z)}
	 * = [ {(v1z)(v2x)  - (v1x)(v2z)}  ]
	 *     {(-v1y)(v2x) + (v1z)(v2y)}
	 * </pre>
	 * <p>
	 * If provided a {@code null} vector, a vector with components (0,0,0) is
	 * returned as the cross product.
	 * <p>
	 * <b>Note:</b> To keep both vector values that form the cross product, create a
	 * cross product vector as:<br>
	 * {@code vCross = new Vector3D(v1);}<br>
	 * {@code vCross.cross(v2);}
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}: {@code v1.cross(v2) = (4,5,-1)}.
	 * <li>{@code v2 = (0,0,0)}: {@code v1.cross(v2) = (0,0,0)}.
	 * <li>{@code v1.cross(v1) = (0,0,0)}.
	 * </ul>
	 *
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Cross_product">http://en.wikipedia.org/wiki/Cross_product</a>
	 * @see <a href=
	 *      "http://mathworld.wolfram.com/CrossProduct.html">http://mathworld.wolfram.com/CrossProduct.html</a>
	 * @param v the 'right' cross vector.
	 */
	public void cross(final MutableVector3D v) {
		final double crossx = (y * v.z) - (v.y * z);
		final double crossy = (v.x * z) - (x * v.z);
		final double crossz = (x * v.y) - (v.x * y);
		assign(crossx, crossy, crossz);
	}

	/**
	 * Assigns this vector as the right handed cross product of this
	 * {@link MutableVector3D} and the given {@link Vector3D}
	 */
	public void cross(final Vector3D v) {
		final double crossx = (y * v.getZ()) - (v.getY() * z);
		final double crossy = (v.getX() * z) - (x * v.getZ());
		final double crossz = (x * v.getY()) - (v.getX() * y);
		assign(crossx, crossy, crossz);
	}

	/**
	 * Returns the distance between this vector and the given vector.
	 * <p>
	 * This is Euclidean Distance (e.g. SQRT[(a-x)^2 + (b-y)^2 + (c-z)^2]) value.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}: {@code v1.distanceTo(v2) = 7.874007874011811}
	 * </ul>
	 *
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Euclidean_vector#Length">http://en.wikipedia.org/wiki/Euclidean_vector#Length</a>
	 * @param v the vector to find the distance to.
	 * @return the distance between this vector and the given vector.
	 */
	public double distanceTo(final MutableVector3D v) {
		return FastMath.sqrt(sqr(v.x - x) + sqr(v.y - y) + sqr(v.z - z));
	}

	/**
	 * Returns the distance between this vector and the given vector.
	 */
	public double distanceTo(final Vector3D v) {
		return FastMath.sqrt(sqr(v.getX() - x) + sqr(v.getY() - y) + sqr(v.getZ() - z));
	}

	/**
	 * Returns the dot product of this vector and the given vector.
	 * <p>
	 * Recall that the Dot Product is also called the "Scalar Product" or "Inner
	 * Product".
	 * <p>
	 * The dot product, {@code v1.v2}, formulation is therefore,
	 *
	 * <pre>
	 * {@code
	 * v1.dot(v2)
	 * }
	 *
	 * = Norm[v1]Norm[v2]Sin[Alpha]
	 *
	 * = Transpose[v1]*v2
	 *
	 * = (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z)
	 * </pre>
	 *
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}: {@code v1.dot(v2) = -9.0}
	 * <li>{@code v2 = (0,0,0)}: {@code v1.dot(v2) = 0.0}
	 * </ul>
	 *
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Dot_product">http://en.wikipedia.org/wiki/Dot_product</a>
	 * @see <a href=
	 *      "http://mathworld.wolfram.com/DotProduct.html">http://mathworld.wolfram.com/DotProduct.html</a>
	 * @param v the vector for the dot product.
	 * @return the dot product
	 */
	public double dot(final MutableVector3D v) {
		return (x * v.x) + (y * v.y) + (z * v.z);
	}

	/**
	 * Returns the dot product of this {@link MutableVector3D} and the given
	 * {@link Vector3D}.
	 */
	public double dot(final Vector3D v) {
		return (x * v.getX()) + (y * v.getY()) + (z * v.getZ());
	}

	/**
	 * Equals contract of Vector3D. Two vectors a and b are equal if their x,y and z
	 * values convert to long bits in the same way. An exception is made for -0,
	 * which is calculated as if its long bits were representing +0. This is done to
	 * give a more intuitive meaning of equals.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MutableVector3D other = (MutableVector3D) obj;
		if (toLongBits(x) != toLongBits(other.x)) {
			return false;
		}
		if (toLongBits(y) != toLongBits(other.y)) {
			return false;
		}
		if (toLongBits(z) != toLongBits(other.z)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the value at the given index: 0->x, 1->y, 2->z
	 */
	public double get(final int index) {
		switch (index) {
		case 0:
			return x;
		case 1:
			return y;
		case 2:
			return z;
		default:
			throw new RuntimeException("index out of bounds " + index);
		}
	}

	/**
	 * Returns the x component of this vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y component of this vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the z component of this vector
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Default generated hash code.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = toLongBits(x);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		temp = toLongBits(y);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		temp = toLongBits(z);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Returns {@code true} if any of the vector components are not NaN (i.e. 'Not a
	 * Number') and is not infinite.
	 *
	 * @return {@code true} if the vector is not NaN (i.e. 'Not a Number') and is
	 *         not infinite.
	 */
	public boolean isFinite() {
		return !isNaN() && !isInfinite();
	}

	/**
	 * Returns {@code true} if any of the vector components are positive or negative
	 * infinity.
	 *
	 * @return {@code true} if the vector is infinite.
	 */
	public boolean isInfinite() {
		return Double.isInfinite(x) || Double.isInfinite(y) || Double.isInfinite(z);
	}

	/**
	 * Returns {@code true} if any of the vector components are NaN 'Not a Number'.
	 *
	 * @return {@code true} if the vector is NaN 'Not a Number'.
	 */
	public boolean isNaN() {
		return Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z);
	}

	/**
	 * Returns true if and only if this vector is finite and has a length within
	 * {@link MutableVector3D#NORMAL_LENGTH_TOLERANCE} of unit length.
	 */
	public boolean isNormal() {
		final double len = length();
		return Double.isFinite(len) && (FastMath.abs(len - 1) < NORMAL_LENGTH_TOLERANCE);
	}

	/**
	 * Returns the norm of the vector, i.e. its length.
	 * <p>
	 * This is vector norm (e.g. SQRT[x^2 + y^2 + z^2]) value.
	 * <p>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>{@code v1 = (-1,2,6)}: {@code v1.norm() = SQRT[41] = 6.4031242374328485}
	 * <li>{@code v1 = (0,0,0)}: {@code v1.norm() = SQRT[0] = 0.0}
	 * </ul>
	 *
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Euclidean_vector#Length">http://en.wikipedia.org/wiki/Euclidean_vector#Length</a>
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm">http://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm</a>
	 * @return the norm of the vector
	 */
	public double length() {
		return FastMath.sqrt((x * x) + (y * y) + (z * z));
	}

	/**
	 * Normalizes the vector.
	 * <p>
	 * Normalizing a vector scales the vector to a unit vector. If the vector has
	 * norm (i.g. length) of zero, {@code NaN} will result.
	 * <p>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>{@code v1 = (-1,2,6)}:
	 * {@code v1.normalize() = (-0.15617376188860607, 0.31234752377721214, 0.9370425713316364)}
	 * <li>{@code v1 = (0,0,0)}: {@code v1.normalize() = (NaN, NaN, NaN)}
	 * </ul>
	 *
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Normalized_vector">http://en.wikipedia.org/wiki/Normalized_vector</a>
	 */
	public void normalize() {
		scale(1.0 / length());
	}

	/**
	 * Reverses the vector.
	 * <p>
	 * This is equivalent to scaling the vector by -1 (i.e. {@code v.scale(-1)} ).
	 * <p>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>{@code v1 = (-1,2,6)}: {@code v1.reverse() = (1,-2,-6)}
	 * <li>{@code v1 = (0,0,0)}: {@code v1.reverse() = (0,0,0)}
	 * </ul>
	 */
	public void reverse() {
		scale(-1);
	}

	/**
	 * Rotates this vector about the given vector at the given angle according to a
	 * right hand coordinate system (i.e. counter-clockwise).
	 * <p>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>{@code v1 = (-1,2,6)}, {@code v2 = (1,-1,-1)},
	 * {@code v1.rotate(v2, theta)}:
	 * <ul>
	 * <li>{@code theta = 0.0} -> {@code (-1.0, 2.0, 6.0)}
	 * <li>{@code theta = Math.PI/2.0 = 1.5707963267948966 ->
	 * (-5.309401076758505, 0.11324865405187179, 3.577350269189627)}
	 * <li>{@code theta = Math.PI = 3.141592653589793 -> (-5.000000000000002,
	 * 4.000000000000002, 1.7763568394002505E-15)}
	 * <li>{@code theta = (3.0/2.0)*Math.PI = 4.71238898038469 ->
	 * (-0.690598923241498, 5.88675134594813, 2.4226497308103747)}
	 * <li>{@code theta = 2.0*Math.PI = 6.283185307179586 ->
	 * (-0.9999999999999996, 2.000000000000001, 6.0)}
	 * </ul>
	 * <li>{@code v1 = (-1,2,6)}, {@code v2 = (0,0,0)},
	 * {@code theta = Math.PI = 3.141592653589793}, {@code v1.rotate(v2, theta)} ->
	 * {@code (NaN, NaN, NaN)}
	 * <li>{@code v1 = (0,0,0)}, {@code v2 = (1,-1,-1)},
	 * {@code theta = Math.PI = 3.141592653589793}, {@code v1.rotate(v2, theta)} ->
	 * {@code (0,0,0)}
	 * </ul>
	 *
	 * @param v     the vector to rotate about.
	 * @param theta the angle of rotation according to a right hand coordinate
	 *                  system (i.e. counter-clockwise).
	 */
	public void rotateAbout(final MutableVector3D v, final double theta) {
		// Normalize the vector
		final MutableVector3D normalizedRotator = new MutableVector3D(v);
		normalizedRotator.normalize();

		// parallelProjection is the part of this vector that lies in the
		// direction of the rotator
		final MutableVector3D parallelProjection = new MutableVector3D(normalizedRotator);
		parallelProjection.scale(dot(normalizedRotator));

		// x_perpendicularProjection is the part of this vector that is
		// perpendicular to the rotator. It is the projection of self onto the
		// plane perpendicular to the rotator.
		final MutableVector3D x_perpendicularProjection = new MutableVector3D(this);
		x_perpendicularProjection.sub(parallelProjection);

		// y_perpendicularProjection is the vector that is both in the plane
		// perpendicular to the rotator AND perpendicular to the
		// x_perpendicularProjection

		final MutableVector3D y_perpendicularProjection = new MutableVector3D(normalizedRotator);
		y_perpendicularProjection.cross(x_perpendicularProjection);

		// Note: y_perpendicularProjection and x_perpendicularProjection
		// have the same length. This has reduced the problem to a rotation in
		// the plane.

		// We will now accumulate the various components after resetting this
		// vector to zero.
		zero();
		addScaled(x_perpendicularProjection, FastMath.cos(theta));
		addScaled(y_perpendicularProjection, FastMath.sin(theta));

		// Now by adding back the parallelProjection, we obtain the desired
		// result that is off the plane
		add(parallelProjection);
	}

	/**
	 * Rotates this vector about the given vector at the given angle according to a
	 * right hand coordinate system (i.e. counter-clockwise).
	 */
	public void rotateAbout(final Vector3D v, final double theta) {
		// Normalize the vector
		final MutableVector3D normalizedRotator = new MutableVector3D(v);
		normalizedRotator.normalize();

		// parallelProjection is the part of this vector that lies in the
		// direction of the rotator
		final MutableVector3D parallelProjection = new MutableVector3D(normalizedRotator);
		parallelProjection.scale(dot(normalizedRotator));

		// x_perpendicularProjection is the part of this vector that is
		// perpendicular to the rotator. It is the projection of self onto the
		// plane perpendicular to the rotator.
		final MutableVector3D x_perpendicularProjection = new MutableVector3D(this);
		x_perpendicularProjection.sub(parallelProjection);

		// y_perpendicularProjection is the vector that is both in the plane
		// perpendicular to the rotator AND perpendicular to the
		// x_perpendicularProjection

		final MutableVector3D y_perpendicularProjection = new MutableVector3D(normalizedRotator);
		y_perpendicularProjection.cross(x_perpendicularProjection);

		// Note: y_perpendicularProjection and x_perpendicularProjection
		// have the same length. This has reduced the problem to a rotation in
		// the plane.

		// We will now accumulate the various components after resetting this
		// vector to zero.
		zero();
		addScaled(x_perpendicularProjection, FastMath.cos(theta));
		addScaled(y_perpendicularProjection, FastMath.sin(theta));

		// Now by adding back the parallelProjection, we obtain the desired
		// result that is off the plane
		add(parallelProjection);
	}

	/**
	 * Rotates this vector toward the given vector at the given angle according to a
	 * right hand coordinate system (i.e. counter-clockwise).
	 * <p>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>{@code v1 = (-1,2,6)}, {@code v2 = (1,-1,-1)}:
	 * {@code v1.rotateToward(v2, theta)} produces:
	 * <ul>
	 * <li>{@code theta = 0.0 -> (-0.9999999999999999, 2.0, 6.0)}
	 * <li>{@code theta = Math.PI/2.0 = 1.5707963267948966 ->
	 * (4.937707198786941, -3.548977049128114, 2.005943549507195)}
	 * <li>{@code theta = Math.PI = 3.141592653589793 -> (1.0000000000000007,
	 * -2.0000000000000004, -6.0)}
	 * <li>{@code theta = (3.0/2.0)*Math.PI = 4.71238898038469 ->
	 * (-4.937707198786941, 3.5489770491281134, -2.0059435495071956)}
	 * <li>{@code theta = 2.0*Math.PI = 6.283185307179586 ->
	 * (-1.000000000000001, 2.000000000000001, 5.999999999999999)}
	 * </ul>
	 * <li>{@code v1 = (-1,2,6)}, {@code v2 = (0,0,0)},
	 * {@code theta = Math.PI = 3.141592653589793}, {@code v1.rotate(v2, theta)} ->
	 * {@code (NaN, NaN, NaN)}
	 * <li>{@code v1 = (0,0,0)}, {@code v2 = (1,-1,-1)},
	 * {@code theta = Math.PI = 3.141592653589793}, {@code v1.rotate(v2, theta)} ->
	 * {@code (NaN, NaN, NaN)}
	 * </ul>
	 *
	 * @param v     the vector to rotate toward.
	 * @param theta the angle of rotation according to a right hand coordinate
	 *                  system (i.e. counter-clockwise).
	 */
	public void rotateToward(final MutableVector3D v, final double theta) {
		final MutableVector3D perp = new MutableVector3D(this);
		perp.cross(v);
		rotateAbout(perp, theta);
	}

	/**
	 * Rotates this vector toward the given vector at the given angle according to a
	 * right hand coordinate system (i.e. counter-clockwise).
	 */
	public void rotateToward(final Vector3D v, final double theta) {
		final MutableVector3D perp = new MutableVector3D(this);
		perp.cross(v);
		rotateAbout(perp, theta);
	}

	/**
	 * Scales the vector by the given magnitude, e.g. scaling factor.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v1.scale(2) = (2,4,-12)}
	 * <li>{@code v1.scale(-1) = (1,-2,-6)}, <b>Note:</b> This is the same as
	 * {@code v1.reverse()}
	 * <li>{@code v1.scale(1) = (-1,2,6)}
	 * <li>{@code v1.scale(0) = (0,0,0)}
	 * </ul>
	 *
	 * @param m the magnitude, e.g. scaling factor.
	 */
	public void scale(final double m) {
		x *= m;
		y *= m;
		z *= m;
	}

	/**
	 * Sets the x component of this vector
	 */
	public void setX(final double x) {
		this.x = x;
	}

	/**
	 * Sets the y component of this vector
	 */
	public void setY(final double y) {
		this.y = y;
	}

	/**
	 * Sets the z component of this vector
	 */
	public void setZ(final double z) {
		this.z = z;
	}

	/**
	 * Squares the provided value.
	 *
	 * @param value to be squared
	 * @return the value squared
	 */
	private double sqr(final double value) {
		return value * value;
	}

	/**
	 * Returns the square of the distance to the vector provided.
	 * <p>
	 * This is Euclidean Distance squared (e.g. (a-x)^2 + (b-y)^2 + (c-z)^2) value.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}:
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}: {@code v1.squareDistanceTo(v2) = 62.0}
	 * </ul>
	 *
	 * @param v the vector to find the distance to.
	 * @return the square of the distance to the vector provided.
	 */
	public double squareDistanceTo(final MutableVector3D v) {
		return ((v.x - x) * (v.x - x)) + ((v.y - y) * (v.y - y)) + ((v.z - z) * (v.z - z));
	}

	/**
	 * Returns the square of the distance to the vector provided.
	 */
	public double squareDistanceTo(final Vector3D v) {
		return ((v.getX() - x) * (v.getX() - x)) + ((v.getY() - y) * (v.getY() - y))
				+ ((v.getZ() - z) * (v.getZ() - z));
	}

	/**
	 * Returns the length of the vector squared.
	 * <p>
	 * This is vector norm squared (e.g. x^2 + y^2 + z^2).
	 * <p>
	 * <b>Examples:</b>
	 * <ul>
	 * <li>{@code v1 = (-1,2,6)}: {@code v1.normSquared() = 41}
	 * <li>{@code v1 = (0,0,0)}: {@code v1.normSquared() = 0}
	 * </ul>
	 *
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Euclidean_vector#Length">http://en.wikipedia.org/wiki/Euclidean_vector#Length</a>
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm">http://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm</a>
	 * @return the norm of the vector
	 */
	public double squareLength() {
		return (x * x) + (y * y) + (z * z);
	}

	/**
	 * Subtracts scalar components from this vector.
	 * <p>
	 * <b>Examples:</b> Given that {@code v1 = (-1,2,6)}
	 * <ul>
	 * <li>{@code v1.sub(1,-1,-1) = (-1-1,2-(-1),6-(-1)) = (-2,3,7)}
	 * <li>{@code v1.sub(0,0,0) = (-1,2,6)}
	 * </ul>
	 *
	 * @param x the {@code x} component.
	 * @param y the {@code y} component.
	 * @param z the {@code y} component.
	 */
	public void sub(final double x, final double y, final double z) {
		this.x = this.x - x;
		this.y = this.y - y;
		this.z = this.z - z;
	}

	/**
	 * Subtracts the given vector's components from this vector.
	 * <p>
	 * A {@code null} vector is treated as a (0,0,0) vector such that no addition
	 * occurs.
	 * <p>
	 * <b>Examples:</b> {@code v1 = (-1,2,6)}
	 * <ul>
	 * <li>{@code v2 = (1,-1,-1)}:
	 * {@code v1.sub(v2) = (-1-1,2-(-1),6-(-1)) = (-2,3,7)}
	 * <li>{@code v2 = (0,0,0)}: {@code v1.sub(v2) = (-1,2,6)}
	 * </ul>
	 *
	 * @param v the vector to be added.
	 */
	public void sub(final MutableVector3D v) {
		x = x - v.x;
		y = y - v.y;
		z = z - v.z;
	}

	/**
	 * Subtracts the given vector's components from this vector.
	 */
	public void sub(final Vector3D v) {
		x = x - v.getX();
		y = y - v.getY();
		z = z - v.getZ();
	}

	/**
	 * Returns a length 3 array of double [x,y,z]
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	/**
	 * Returns the string representation in the form Vector3D
	 * [x=2.57,y=-34.1,z=8.73]
	 */
	@Override
	public String toString() {
		return "Vector3D [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	/**
	 * Sets each component of this {@link MutableVector3D} to zero. <b>Note:</b>
	 * This is the same as calling {@code v.assign(0,0,0)}.
	 */
	public void zero() {
		assign(0, 0, 0);
	}

	public final static double PERPENDICULAR_ANGLE_TOLERANCE = 1E-8;

	/**
	 * Returns true if and only if this {@link MutableVector3D} is perpendicular to
	 * the given {@link MutableVector3D} within the
	 * {@link Vector3D#PERPENDICULAR_ANGLE_TOLERANCE}
	 */
	public boolean isPerpendicularTo(MutableVector3D v) {
		return FastMath.abs(angle(v) - FastMath.PI / 2) < PERPENDICULAR_ANGLE_TOLERANCE;
	}

	/**
	 * Returns true if and only if this {@link Vector3D} is perpendicular to the
	 * given {@link Vector3D} within the
	 * {@link Vector3D#PERPENDICULAR_ANGLE_TOLERANCE}
	 */
	public boolean isPerpendicularTo(Vector3D v) {
		return FastMath.abs(angle(v) - FastMath.PI / 2) < PERPENDICULAR_ANGLE_TOLERANCE;
	}
}