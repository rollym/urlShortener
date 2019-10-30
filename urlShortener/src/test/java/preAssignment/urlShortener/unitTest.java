package preAssignment.urlShortener;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import preAssignment.urlShortener.service.UrlService;
import preAssignment.urlShortener.utils.Base10;
import preAssignment.urlShortener.utils.Base62;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
public class unitTest {
	
	@Autowired
	UrlService urlService;
	
	private final String domain="localhost:8080/redirect/";
	/**
	 * get base 62 conversion test
	 */
	@Test
	public void testBase62() {
		long base10Number = 72;
		String expectedResult = "bk";
		String gotResult = Base62.toBase62(base10Number);
		assertEquals(expectedResult, gotResult);
	}
	/**
	 * get base 10 conversion test
	 */
	@Test
	public void testBase10() {
		String base62String = "bk";
		long expectedResult = 72;
		long gotResult = Base10.reverseUrl(base62String);
		assertEquals(expectedResult, gotResult);
	}
	/**
	 * test to check if long url being saved and being generated from short Url is same
	 * @throws Exception
	 */
	@Test
	public void testUrlConversion() throws Exception {
		String longUrl = "https://turbotax.intuit.com/";
		String shortUrl = urlService.saveUrl(longUrl);
		String convertedLongUrl = urlService.getLongURLFromShortUrl(shortUrl.replaceFirst(domain, ""));
		assertEquals(longUrl, convertedLongUrl);
	}

}
