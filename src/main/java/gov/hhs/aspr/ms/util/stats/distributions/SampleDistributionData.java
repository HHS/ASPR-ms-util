package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.random.RandomGenerator;

public interface SampleDistributionData {

	/**
	 * Returns a new builder instance containing the collected state of this
	 * SampleDistributionData. Immediately invoking build on the returned builder
	 * will result in an identical copy of the SampleDistributionData
	 */
	public SampleDistributionDataBuilder getCloneBuilder();

	@Override
	public String toString();

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);

	/**
	 * Returns a new instance of the SampleDistribution that corresponds to this
	 * SampleDistributionData.
	 */
	public SampleDistribution getSampleDistribution(RandomGenerator randomGenerator);

}
