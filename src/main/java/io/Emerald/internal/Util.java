package io.Emerald.internal;

import java.time.LocalDateTime;

public class Util {

    /**
     * Sanitize a string so that it will display properly in embeds.
     *
     * @param input The string to sanitize.
     * @return The sanitized string.
     */
    public static String sanitize(String input) {
        return input.replaceAll("[^\\x00-\\x7F]", "");
    }

    /**
     * Capitalize the first letter in a string.
     *
     * @param input The string to parse.
     * @return The capitalized string.
     */
    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Parse the date to
     *
     * @param input The date to parse.
     * @return The properly parsed date.
     */
    public static String parseDate(LocalDateTime input) {
        return capitalize(input.getMonth().toString()) + " " + input.getDayOfMonth() + ", " + input.getYear();
    }
}
