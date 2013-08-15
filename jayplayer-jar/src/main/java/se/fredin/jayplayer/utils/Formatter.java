package se.fredin.jayplayer.utils;

import java.text.NumberFormat;

public class Formatter {

	public String format(double number, int amountOfDecimals) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(amountOfDecimals);
		numberFormat.setMaximumFractionDigits(amountOfDecimals);
		return numberFormat.format(number);
	}

}
