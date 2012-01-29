/*
 * Copyright (c) 2011. KloudTek Ltd
 */

package com.kloudtek.util;

import java.math.BigDecimal;

/**
 * Some utility method regarding handling currencies
 */
public class CurrencyUtils {
    /**
     * Convert a long that contains an amount of currency in 1/100 of the currency, into a string.
     * For example, the amount of 1000 cents (USD currency) would convert to "10.00".
     *
     * @param amount Amount in 1/100 of the currency value.
     * @return Currency amount string.
     */
    public static String toString(long amount) {
        StringBuilder nb = new StringBuilder(Long.toString(amount / 100)).append('.');
        long remainder = amount % 100;
        if (remainder == 0) {
            nb.append("00");
        } else {
            if (remainder < 10) {
                nb.append("0");
            }
            nb.append(Long.toString(remainder));
        }
        return nb.toString();
    }

    /**
     * Parse a decimal string into an amount in 1/100 of the the currency. i.e.: "10.10" would convert to 1010
     *
     * @param amount Amount value
     * @return Amount in 1/100 of currency
     * @throws NumberFormatException If the amount isn't a decimal number
     */
    public static long parse(String amount) throws NumberFormatException {
        return new BigDecimal(amount).multiply(new BigDecimal(100)).longValue();
    }
}
