package gov.hhs.aspr.ms.util.measures;

/**
 * A utility enum for converting absolute and relative temperatures across
 * various temperature scales.
 */
public enum TemperatureScale {

	/**
	 * fahrenheit = 9*(kelvin - 273.15)/5+32
	 */
	FAHRENHEIT {

		@Override
		protected double toAbsoluteKelvin(double value) {
			return (value - 32) / 9 * 5 + 273.15;
		}

		@Override
		protected double fromAbsoluteKelvin(double value) {
			return (value - 273.15) * 9 / 5 + 32;
		}

		@Override
		protected double toRelativeKelvin(double value) {
			return value / 9 * 5;
		}

		@Override
		protected double fromRelativeKelvin(double value) {
			return value * 9 / 5;
		}
	},

	/**
	 * Kelvin is the fundamental scale
	 * 
	 * kelvin = kelvin
	 */
	KELVIN {

		@Override
		protected double toAbsoluteKelvin(double value) {
			return value;
		}

		@Override
		protected double fromAbsoluteKelvin(double value) {
			return value;
		}

		@Override
		protected double toRelativeKelvin(double value) {
			return value;
		}

		@Override
		protected double fromRelativeKelvin(double value) {
			return value;
		}
	},

	/**
	 * rankine = 9*kelvin/5
	 */
	RANKINE {

		@Override
		protected double toAbsoluteKelvin(double value) {
			return value * 5 / 9;
		}

		@Override
		protected double fromAbsoluteKelvin(double value) {
			return value * 9 / 5;
		}

		@Override
		protected double toRelativeKelvin(double value) {
			return value * 5 / 9;
		}

		@Override
		protected double fromRelativeKelvin(double value) {
			return value * 9 / 5;
		}
	},
	/**
	 * delisle = 3*(373.15 - kelvin)/2
	 */
	DELISLE {

		@Override
		protected double toAbsoluteKelvin(double value) {
			return 373.15 - value * 2 / 3;
		}

		@Override
		protected double fromAbsoluteKelvin(double value) {
			return (373.15 - value) * 3 / 2;
		}

		@Override
		protected double toRelativeKelvin(double value) {
			return -value * 2 / 3;
		}

		@Override
		protected double fromRelativeKelvin(double value) {
			return -value * 3 / 2;
		}
	},
	/**
	 * newton = 0.33*(kelvin - 273.15)
	 */
	NEWTON {

		@Override
		protected double toAbsoluteKelvin(double value) {
			return value / 0.33 + 273.15;
		}

		@Override
		protected double fromAbsoluteKelvin(double value) {
			return (value - 273.15) * 0.33;
		}

		@Override
		protected double toRelativeKelvin(double value) {
			return value / 0.33;
		}

		@Override
		protected double fromRelativeKelvin(double value) {
			return value * 0.33;
		}
	},

	/**
	 * reaumur = 4*(kelvin - 273.15)/5
	 */
	REAUMUR {

		@Override
		protected double toAbsoluteKelvin(double value) {
			return value * 5 / 4 + 273.15;
		}

		@Override
		protected double fromAbsoluteKelvin(double value) {
			return (value - 273.15) * 4 / 5;
		}

		@Override
		protected double toRelativeKelvin(double value) {
			return value * 5 / 4;
		}

		@Override
		protected double fromRelativeKelvin(double value) {
			return value * 4 / 5;
		}
	},
	
	/**
	 * romer = 21*(kelvin - 273.15)/40 + 7.5
	 */
	ROMER {

		@Override
		protected double toAbsoluteKelvin(double value) {
			return (value - 7.5) * 40 / 21 + 273.15;
		}

		@Override
		protected double fromAbsoluteKelvin(double value) {
			return (value - 273.15) * 21 / 40 + 7.5;
		}

		@Override
		protected double toRelativeKelvin(double value) {
			return value * 40 / 21;
		}

		@Override
		protected double fromRelativeKelvin(double value) {
			return value * 21 / 40;
		}
	},

	/**
	 * celsius = kelvin - 273.15
	 */
	CELSIUS {
		@Override
		protected double toAbsoluteKelvin(double value) {
			return value + 273.15;
		}

		@Override
		protected double fromAbsoluteKelvin(double value) {
			return value - 273.15;
		}

		@Override
		protected double toRelativeKelvin(double value) {
			return value;
		}

		@Override
		protected double fromRelativeKelvin(double value) {
			return value;
		}
	};

	protected abstract double toAbsoluteKelvin(double value);

	protected abstract double fromAbsoluteKelvin(double value);

	protected abstract double toRelativeKelvin(double value);

	protected abstract double fromRelativeKelvin(double value);

	/**
	 * Converts the absolute degrees in the given scale to this scale's absolute
	 * temperature degree value.
	 */
	public double fromAbsolute(double absoluteDegrees, TemperatureScale temperatureScale) {
		return fromAbsoluteKelvin(temperatureScale.toAbsoluteKelvin(absoluteDegrees));
	}

	/**
	 * Converts the relative degrees in the given scale to this scale's relative
	 * temperature degree value.
	 */
	public double fromRelative(double relativeDegrees, TemperatureScale temperatureScale) {
		return fromRelativeKelvin(temperatureScale.toRelativeKelvin(relativeDegrees));
	}

}
