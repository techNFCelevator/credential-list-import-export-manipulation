package accountlist.util;

import accountlist.data.StoredValue;

public class Validator {
	public static final String DATE_PATTERN = "^\\\\d{4}-\\\\d{2}-\\\\d{2}$";
    public static boolean isValidId(String id) {
        return id != null && !id.isEmpty() && !id.isBlank() && id.matches("^\\d{16}$");
    }
    public static boolean isValidProfile(String[] profile) {
        return profile != null && profile.length > 0 && 
               (profile[0].equalsIgnoreCase("General") || 
                profile[0].equalsIgnoreCase("Concession") || 
                (profile[0].equalsIgnoreCase("Personalized") && profile.length == 4 &&
                 profile[1] != null && !profile[1].isEmpty() && !profile[1].isBlank() &&
                 profile[2] != null && !profile[2].isEmpty() && !profile[2].isBlank() &&
                 profile[3] != null && !profile[3].isEmpty() && !profile[3].isBlank() &&
                 profile[3].matches("^\\d{4}-\\d{2}-\\d{2}$")));
    }
    public static boolean isValidStoredValue(double value) {
        return value >= StoredValue.MIN_VALUE && value <= StoredValue.MAX_VALUE;
    }
}
