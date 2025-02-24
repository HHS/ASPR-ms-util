package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistribution implementation of a log normal distribution that wraps an
 * org.apache.commons.math3.distribution.LogNormalDistribution
 */
public final class LogNormalSampleDistribution implements SampleDistribution {

	private final LogNormalDistribution distribution;

	private final LogNormalSampleDistributionData logNormalsSampleDistributionData;

	/**
	 * Creates a new LogNormalSampleDistribution from a random generator and a
	 * LogNormalsSampleDistributionData
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_RNG} if the
	 *                           random generator is null</li>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sample distribution data is null</li>
	 *                           </ul>
	 */
	public LogNormalSampleDistribution(RandomGenerator randomGenerator,
			LogNormalSampleDistributionData logNormalsSampleDistributionData) {
		if (randomGenerator == null) {
			throw new ContractException(DistributionError.NULL_RNG);
		}
		if (logNormalsSampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		this.logNormalsSampleDistributionData = logNormalsSampleDistributionData;
		distribution = new LogNormalDistribution(randomGenerator, logNormalsSampleDistributionData.getScale(),
				logNormalsSampleDistributionData.getShape());
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
	public LogNormalSampleDistributionData getSampleDistributionData() {
		return logNormalsSampleDistributionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((logNormalsSampleDistributionData == null) ? 0 : logNormalsSampleDistributionData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof LogNormalSampleDistribution)) {
			return false;
		}
		LogNormalSampleDistribution other = (LogNormalSampleDistribution) obj;
		if (logNormalsSampleDistributionData == null) {
			if (other.logNormalsSampleDistributionData != null) {
				return false;
			}
		} else if (!logNormalsSampleDistributionData.equals(other.logNormalsSampleDistributionData)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LogNormalSampleDistribution [logNormalsSampleDistributionData=");
		builder.append(logNormalsSampleDistributionData);
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
