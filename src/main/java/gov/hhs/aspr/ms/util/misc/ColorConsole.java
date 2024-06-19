package gov.hhs.aspr.ms.util.misc;

public class ColorConsole {
	
	private static enum TextCase{
		NONE,UPPER,LOWER;
	}
	
	private String resetToken = "\u001B[0m";
	private String boldToken = "\u001B[1m";
	private String underlineToken = "\u001B[4m";
	
	private String textColorToken;
	private String backgroundColorToken;
	private boolean useBold;
	private TextCase textCase = TextCase.NONE;	
	private boolean useUnderline;

	public ColorConsole black() {
		textColorToken = "\u001B[30m";
		return this;
	}

	public ColorConsole black_B() {
		backgroundColorToken = "\u001B[40m";
		return this;
	}

	public ColorConsole red() {
		textColorToken = "\u001B[31m";
		return this;
	}

	public ColorConsole red_B() {
		backgroundColorToken = "\u001B[41m";
		return this;
	}

	public ColorConsole green() {
		textColorToken = "\u001B[32m";
		return this;
	}

	public ColorConsole green_B() {
		backgroundColorToken = "\u001B[42m";
		return this;
	}

	public ColorConsole yellow() {
		textColorToken = "\u001B[33m";
		return this;
	}

	public ColorConsole yellow_B() {
		backgroundColorToken = "\u001B[43m";
		return this;
	}

	public ColorConsole blue() {
		textColorToken = "\u001B[34m";
		return this;
	}

	public ColorConsole blue_B() {
		backgroundColorToken = "\u001B[44m";
		return this;
	}

	public ColorConsole magenta() {
		textColorToken = "\u001B[35m";
		return this;
	}

	public ColorConsole magenta_B() {
		backgroundColorToken = "\u001B[45m";
		return this;
	}

	public ColorConsole cyan() {
		textColorToken = "\u001B[36m";
		return this;
	}

	public ColorConsole cyan_B() {
		backgroundColorToken = "\u001B[46m";
		return this;
	}

	public ColorConsole lightGray() {
		textColorToken = "\u001B[37m";
		return this;
	}

	public ColorConsole lightGray_B() {
		backgroundColorToken = "\u001B[47m";
		return this;
	}

	public ColorConsole darkGray() {
		textColorToken = "\u001B[90m";
		return this;
	}

	public ColorConsole darkGray_B() {
		backgroundColorToken = "\u001B[100m";
		return this;
	}

	public ColorConsole lightRed() {
		textColorToken = "\u001B[91m";
		return this;
	}

	public ColorConsole lightRed_B() {
		backgroundColorToken = "\u001B[101m";
		return this;
	}

	public ColorConsole lightGreen() {
		textColorToken = "\u001B[92m";
		return this;
	}

	public ColorConsole lightGreen_B() {
		backgroundColorToken = "\u001B[102m";
		return this;
	}

	public ColorConsole lightYellow() {
		textColorToken = "\u001B[93m";
		return this;
	}

	public ColorConsole lightYellow_B() {
		backgroundColorToken = "\u001B[103m";
		return this;
	}

	public ColorConsole lightBlue() {
		textColorToken = "\u001B[94m";
		return this;
	}

	public ColorConsole lightBlue_B() {
		backgroundColorToken = "\u001B[104m";
		return this;
	}

	public ColorConsole lightMagenta() {
		textColorToken = "\u001B[95m";
		return this;
	}

	public ColorConsole lightMagenta_B() {
		backgroundColorToken = "\u001B[105m";
		return this;
	}

	public ColorConsole lightCyan() {
		textColorToken = "\u001B[96m";
		return this;
	}

	public ColorConsole lightCyan_B() {
		backgroundColorToken = "\u001B[106m";
		return this;
	}

	public ColorConsole white() {
		textColorToken = "\u001B[97m";
		return this;		
	}

	public ColorConsole white_B() {
		backgroundColorToken = "\u001B[107m";
		return this;
	}
	
	public ColorConsole upperCase() {
		this.textCase = TextCase.UPPER;
		return this;
	}
	
	public ColorConsole lowerCase() {
		this.textCase = TextCase.LOWER;
		return this;
	}

	public ColorConsole noCase() {
		this.textCase = TextCase.NONE;
		return this;
	}

	public ColorConsole bold() {
		this.useBold = true;
		return this;
	}
	
	public ColorConsole regular() {
		this.useBold = false;
		return this;
	}

	public ColorConsole underline() {
		this.useUnderline = true;
		return this;
	}
	
	public ColorConsole clearline() {
		this.useUnderline = true;
		return this;
	}
	

	private String constructDecoratedString(String message) {
		StringBuilder sb = new StringBuilder();
		if (useBold) {
			sb.append(boldToken);
		}
		if (useUnderline) {
			sb.append(underlineToken);
		}
		if (textColorToken != null) {
			sb.append(textColorToken);
		}
		if (backgroundColorToken != null) {
			sb.append(backgroundColorToken);
		}
		switch(textCase) {
		case LOWER:
			sb.append(message.toLowerCase());
			break;		
		case UPPER:
			sb.append(message.toUpperCase());
			break;
		default:
			sb.append(message);
			break;
		
		}
		
		sb.append(resetToken);
		return sb.toString();
	}

	public ColorConsole print(String message) {
		System.out.print(constructDecoratedString(message));
		return this;
	}

	public ColorConsole println(String message) {
		System.out.println(constructDecoratedString(message));
		return this;
	}
	
	public ColorConsole reset() {
		backgroundColorToken = null;
		textColorToken = null;
		useBold = false;
		useUnderline = false;
		textCase = TextCase.NONE;
		return this;
	}

}
