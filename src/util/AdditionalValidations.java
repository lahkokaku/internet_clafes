package util;

public class AdditionalValidations {

	public static boolean isAlphanumeric(String string) {
		int alp = 0;
		int num = 0;
		for (int i = 0; i < string.length(); i++) {
			if (Character.isAlphabetic(string.charAt(i)))
				alp++;
			if (Character.isDigit(string.charAt(i)))
				num++;
			if (alp != 0 && num != 0)
				return true;
		}
		return false;
	}

}
