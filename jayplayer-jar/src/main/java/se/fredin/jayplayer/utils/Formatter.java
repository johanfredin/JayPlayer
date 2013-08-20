package se.fredin.jayplayer.utils;

import java.text.NumberFormat;

/**
 * Helper class that formats the amount of digits to be displayed.
 * @author johan
 *
 */
public class Formatter {

	/**
	 * Takes a number and formats it to requested amount of decimals
	 * @param number the decimal number user wishes to format
	 * @param amountOfDecimals the amount of decimal user wishes for selected number
	 * @return the formated number converted into a {@link String}
	 */
	public String format(double number, int amountOfDecimals) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(amountOfDecimals);
		numberFormat.setMaximumFractionDigits(amountOfDecimals);
		return numberFormat.format(number);
	}

}
