package preAssignment.urlShortener.service;

import java.util.Optional;

import preAssignment.urlShortener.controller.UrlController;
import preAssignment.urlShortener.dao.UrlRepository;
import preAssignment.urlShortener.model.Url;
import preAssignment.urlShortener.utils.Base62;
import preAssignment.urlShortener.utils.Base10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 
 * @author Rolly Mantri
 *
 */
@Service
public class UrlService
{
	/**
	 * 
	 */
	private SequenceGeneratorService sequenceGenerator;
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);
	private int MAX_COUNT = 10;


	@Autowired
	public UrlService(SequenceGeneratorService sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}
	
	@Autowired
	private UrlRepository urlRepository;

	private final String domain="localhost:8080/redirect/";

	/**
	 * method to save url in db
	 * @param originalURL
	 * @return
	 */
	public String saveUrl(String originalURL)
	{
		Url url = new Url();
		String[] schemes = {"http","https"};
		UrlValidator valid = new UrlValidator(schemes);
		
		//check if url is valid
		if (valid.isValid(originalURL))
		{
			LOGGER.info("URL is valid.");
			LOGGER.info("Adding Url to database.");
			url.setId(sequenceGenerator.generateSequence(Url.SEQUENCE_NAME));
			url.setLongUrl(originalURL);
			String shortUrl = generateURLShorterner(url);
			url.setShortUrl(shortUrl);
			urlRepository.save(url);
			return shortUrl;
		}
		else {
			LOGGER.info("URL is invalid.");
			return "INVALID";
		}

	}
	/**
	 * convert long url to short url using base 62 conversion 
	 * @param url
	 * @return
	 */
	private String generateURLShorterner(Url url) {

		String shortenedURL = this.domain + Base62.toBase62(url.getId());

		return shortenedURL;
	}
	
	/**
	 * gets long url from short url id by reversing back to base 10
	 * @param uniqueID
	 * @return
	 * @throws Exception
	 */
	public String getLongURLFromShortUrl(String shortUrl) throws Exception {
		LOGGER.info("Received Short URL: " + shortUrl);
		Long dictionaryKey = Base10.reverseUrl(shortUrl);
		//retrieve entity by its id
        Url longUrl = urlRepository.findOne(dictionaryKey);
        String result = "NOT_FOUND"; 
        if (longUrl != null) {
        	
        	LOGGER.info("Found long URL {}", longUrl.getLongUrl());
        	LOGGER.info("ID {}", longUrl.getId());
        	if (!longUrl.getIsValid()) {
        		return "INVALID";
        	}
        	int accessCount = longUrl.getAccessCount();
        	LOGGER.info("URL Count: " + Integer.toString(accessCount));
        	accessCount++;
        	//if url is access maximum no of times, set invalid
        	if (accessCount == MAX_COUNT) {
            	longUrl.setIsValid(false);
        	}
        	longUrl.setAccessCount(accessCount);
        	urlRepository.save(longUrl);
        	result = longUrl.getLongUrl();
        }
        return result;
    }

}
