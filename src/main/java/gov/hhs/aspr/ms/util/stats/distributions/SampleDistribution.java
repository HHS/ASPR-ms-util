package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.util.FastMath;

public interface SampleDistribution {

	/**
	 * Returns the next value from the distribution
	 */
	public double sample();

	/**
	 * Returns the numerical mean of the distribution
	 */
	public double getNumericalMean();

	/**
	 * Returns the numerical variance of the distribution
	 */
	public double getNumericalVariance();

	/**
	 * Returns the numerical standard deviation of the distribution, equal to the
	 * square root of the numerical variance.
	 */
	public default double getNumericalStandardDeviation() {
		return FastMath.sqrt(getNumericalVariance());
	};

	/**
	 * Returns the lower bound of the support. The support is the sub-domain of the
	 * real numbers for which the distribution function has a non-zero value.
	 * 
	 */
	public double getSupportLowerBound();

	/** 
	  * Return true if and only if the getSupportLowerBound() value may be returned by the sample() method.
	  */
    public boolean isSupportLowerBoundInclusive();
    
    /**
	 * Returns the upper bound of the support. The support is the sub-domain of the
	 * real numbers for which the distribution function has a non-zero value.
	 * 
	 */
	public double getSupportUpperBound();

	/** 
	  * Return true if and only if the getSupportUpperBound() value may be returned by the sample() method.
	  */
    public boolean isSupportUpperBoundInclusive();

	/**
	 * Returns the SampleDistributionData used to create this SampleDistribution
	 */
	public SampleDistributionData getSampleDistributionData();

	/**
	 * Returns true if and only if the object is the same type and has an equal
	 * SampleDistributionData
	 */
	@Override
	public boolean equals(Object object);

	/**
	 * Returns a hash code based on the contained SampleDistributionData consistent
	 * with equals()
	 */
	@Override
	public int hashCode();

	/**
	 * Returns the toString() of the contained SampleDistributionData
	 */
	@Override
	public String toString();

}
