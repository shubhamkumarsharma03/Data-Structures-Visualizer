package dsvisualizer.util;

/**
 * Validation utilities for input validation.
 */
public class ValidationUtil {

    /**
     * Validates if a string is a valid integer.
     */
    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if a string is a valid integer within a range.
     */
    public static boolean isValidIntegerInRange(String input, int min, int max) {
        if (!isValidInteger(input)) {
            return false;
        }
        int value = Integer.parseInt(input);
        return value >= min && value <= max;
    }

    /**
     * Validates if a string is not empty.
     */
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Gets error message for invalid input.
     */
    public static String getErrorMessage(String input, int min, int max) {
        if (!isNotEmpty(input)) {
            return "Input cannot be empty";
        }
        if (!isValidInteger(input)) {
            return "Input must be a valid integer";
        }
        if (!isValidIntegerInRange(input, min, max)) {
            return "Input must be between " + min + " and " + max;
        }
        return "";
    }
}
