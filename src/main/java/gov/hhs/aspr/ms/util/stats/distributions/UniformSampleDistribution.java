package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistribution implementation of a uniform distribution that wraps an
 * org.apache.commons.math3.distribution.UniformRealDistribution
 */
public final class UniformSampleDistribution implements SampleDistribution {

	private final UniformRealDistribution distribution;

	private final UniformSampleDistributionData uniformSampleDistributionData;

	/**
	 * Creates a new UniformSampleDistribution from a random generator and a
	 * UniformSampleDistributionData
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_RNG} if the
	 *                           random generator is null</li>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sample distribution data is null</li>
	 *                           </ul>
	 */
	public UniformSampleDistribution(RandomGenerator randomGenerator,
			UniformSampleDistributionData uniformSampleDistributionData) {
		if (randomGenerator == null) {
			throw new ContractException(DistributionError.NULL_RNG);
		}
		if (uniformSampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		this.uniformSampleDistributionData = uniformSampleDistributionData;
		distribution = new UniformRealDistribution(randomGenerator, uniformSampleDistributionData.getLower(),
				uniformSampleDistributionData.getUpper());
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
	public UniformSampleDistributionData getSampleDistributionData() {
		return uniformSampleDistributionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((uniformSampleDistributionData == null) ? 0 : uniformSampleDistributionData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UniformSampleDistribution)) {
			return false;
		}
		UniformSampleDistribution other = (UniformSampleDistribution) obj;
		if (uniformSampleDistributionData == null) {
			if (other.uniformSampleDistributionData != null) {
				return false;
			}
		} else if (!uniformSampleDistributionData.equals(other.uniformSampleDistributionData)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UniformSampleDistribution [uniformSampleDistributionData=");
		builder.append(uniformSampleDistributionData);
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
