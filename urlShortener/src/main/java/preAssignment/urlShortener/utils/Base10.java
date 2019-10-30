package preAssignment.urlShortener.utils;

/**
 * class to convert value to base 10
 * @author Rolly Mantri
 *
 */
public class Base10 {

	/**
	 * alphabet string
	 */
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	/**
	 * length of alphabet
	 */
	private static final int base = ALPHABET.length();

	/**
	 * calls method to convert the url to base 10
	 * @param reverse
	 * @return
	 */
	public static long reverseUrl(String reverse)
	{
		long reverseUrlId= convertTobase10(reverse);
		return reverseUrlId;

	}
	/**
	 * converts string to base 10
	 * @param reverse
	 * @return
	 */
	private static long convertTobase10(String reverse) {
		long nBase10 = 0;
		char [] chars = new StringBuilder(reverse).reverse().toString().toCharArray();

		for(int i=chars.length -1 ; i >= 0; i--)
		{
			int index=ALPHABET.indexOf(chars[i]);
			nBase10 += index * (long)Math.pow(base, i);
		}
		return nBase10;
	}

}