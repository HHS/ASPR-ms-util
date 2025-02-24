package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistribution implementation of an exponential distribution that wraps an
 * org.apache.commons.math3.distribution.ExponentialDistribution
 */
public final class ExponentialSampleDistribution implements SampleDistribution {

	private final ExponentialDistribution distribution;

	private final ExponentialSampleDistributionData exponentialSampleDistributionData;

	/**
	 * Creates a new ExponentialSampleDistribution from a random generator and an
	 * ExponentialSampleDistributionData
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_RNG} if the
	 *                           random generator is null</li>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sample distribution data is null</li>
	 *                           </ul>
	 */
	public ExponentialSampleDistribution(RandomGenerator randomGenerator,
			ExponentialSampleDistributionData exponentialSampleDistributionData) {
		if (randomGenerator == null) {
			throw new ContractException(DistributionError.NULL_RNG);
		}
		if (exponentialSampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		this.exponentialSampleDistributionData = exponentialSampleDistributionData;
		distribution = new ExponentialDistribution(randomGenerator, exponentialSampleDistributionData.getMean());
	}

	@Override
	public double sample() {
		return distribution.sample();
	}

	@Override
	public double getNumericalMean() {
		return distribution.getNumericalMean();
	}

	@Override
	public ExponentialSampleDistributionData getSampleDistributionData() {
		return exponentialSampleDistributionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((exponentialSampleDistributionData == null) ? 0 : exponentialSampleDistributionData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ExponentialSampleDistribution)) {
			return false;
		}
		ExponentialSampleDistribution other = (ExponentialSampleDistribution) obj;
		if (exponentialSampleDistributionData == null) {
			if (other.exponentialSampleDistributionData != null) {
				return false;
			}
		} else if (!exponentialSampleDistributionData.equals(other.exponentialSampleDistributionData)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExponentialSampleDistribution [exponentialSampleDistributionData=");
		builder.append(exponentialSampleDistributionData);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public double getNumericalVariance() {
		return distribution.getNumericalVariance();
	}	

	@Override
	public double getSupportLowerBound() {		
		return distribution.getSupportLowerBound();
	}

	@Override
	public boolean isSupportLowerBoundInclusive() {		
		return distribution.isSupportLowerBoundInclusive();
	}

	@Override
	public double getSupportUpperBound() {
		return distribution.getSupportUpperBound();
	}

	@Override
	public boolean isSupportUpperBoundInclusive() {		
		return distribution.isSupportUpperBoundInclusive();
	}	


}
