package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistribution implementation of a beta distribution that wraps an
 * org.apache.commons.math3.distribution.BetaDistribution
 */
public final class BetaSampleDistribution implements SampleDistribution {

	private final BetaDistribution distribution;

	private final BetaSampleDistributionData betaSampleDistributionData;

	/**
	 * Creates a new BetaSampleDistribution from a random generator and a
	 * BetaSampleDistributionData
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_RNG} if the
	 *                           random generator is null</li>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sample distribution data is null</li>
	 *                           </ul>
	 */
	public BetaSampleDistribution(RandomGenerator randomGenerator,
			BetaSampleDistributionData betaSampleDistributionData) {
		if (randomGenerator == null) {
			throw new ContractException(DistributionError.NULL_RNG);
		}
		if (betaSampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		this.betaSampleDistributionData = betaSampleDistributionData;
		distribution = new BetaDistribution(randomGenerator, betaSampleDistributionData.getAlpha(),
				betaSampleDistributionData.getBeta());
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
	public BetaSampleDistributionData getSampleDistributionData() {
		return betaSampleDistributionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((betaSampleDistributionData == null) ? 0 : betaSampleDistributionData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BetaSampleDistribution)) {
			return false;
		}
		BetaSampleDistribution other = (BetaSampleDistribution) obj;
		if (betaSampleDistributionData == null) {
			if (other.betaSampleDistributionData != null) {
				return false;
			}
		} else if (!betaSampleDistributionData.equals(other.betaSampleDistributionData)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BetaSampleDistribution [betaSampleDistributionData=");
		builder.append(betaSampleDistributionData);
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
