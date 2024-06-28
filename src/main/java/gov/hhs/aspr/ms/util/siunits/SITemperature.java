package gov.hhs.aspr.ms.util.siunits;

public enum SITemperature {
	
	FAHRENHEIT {
		
		@Override
		protected double toKelvin(double value) {
			return (value - 32) / 9 * 5 + 273.15;
		}
		
		@Override
		protected double fromKelvin(double value) {
			return (value - 273.15) * 9 / 5 + 32;
		}
	},
	
	KELVIN {
		
		@Override
		protected double toKelvin(double value) {
			return value;
		}
		
		@Override
		protected double fromKelvin(double value) {
			return value;
		}
	},
	
	RANKINE {
		
		@Override
		protected double toKelvin(double value) {
			return value * 5 / 9;
		}
		
		@Override
		protected double fromKelvin(double value) {
			return value * 9 / 5;
		}
	},
	
	DELISLE {
		
		@Override
		protected double toKelvin(double value) {
			return 373.15 - value * 2 / 3;
		}
		
		@Override
		protected double fromKelvin(double value) {
			return (373.15 - value) * 3 / 2;
		}
	},
	
	NEWTON {
		
		@Override
		protected double toKelvin(double value) {
			return value / 0.33 + 273.15;
		}
		
		@Override
		protected double fromKelvin(double value) {
			return (value - 273.15) * 0.33;
		}
	},
	
	REAUMUR {
		
		@Override
		protected double toKelvin(double value) {
			return value * 5 / 4 + 273.15;
		}
		
		@Override
		protected double fromKelvin(double value) {
			return (value - 273.15) * 4 / 5;
		}
	},
	
	ROMER {
		
		@Override
		protected double toKelvin(double value) {
			return (value - 7.5) * 40 / 21 + 273.15;
		}
		
		@Override
		protected double fromKelvin(double value) {
			return (value - 273.15) * 21 / 40 + 7.5;
		}
	},
	
	CELSIUS {		
		@Override
		protected double toKelvin(double value) {
			return value + 273.15;
		}
		
		@Override
		protected double fromKelvin(double value) {
			return value - 273.15;
		}
	};
	
	protected abstract double toKelvin(double value);
	
	protected abstract double fromKelvin(double value);
	
	public double from(double value, SITemperature siTemperature) {
		return fromKelvin(siTemperature.toKelvin(value));
	}
	
}
