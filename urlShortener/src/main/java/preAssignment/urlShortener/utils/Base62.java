package preAssignment.urlShortener.utils;

/**
 * utils class to convert value to base 62
 * @author Rolly Mantri
 *
 */
public class Base62 {
	
	/**
	 * ALPHANUMERIC  string
	 */
	private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	/**
	 * length of ALPHANUMERIC 
	 */
	private static final int BASE62 = ALPHANUMERIC .length();
    /**
     *  Convert given value to a base 62 number 
     * @param number
     * @return 
     */
    public static String toBase62(long number) 
    {
        final StringBuilder sb = new StringBuilder(1);
        	do 
        	{
        		sb.insert(0, ALPHANUMERIC .charAt((int) (number % BASE62)));
        		number /= BASE62;
        	} while (number > 0);
        
        return sb.toString();

    }
}
