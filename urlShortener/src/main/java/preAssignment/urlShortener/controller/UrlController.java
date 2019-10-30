package preAssignment.urlShortener.controller;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import preAssignment.urlShortener.service.UrlService;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * 
 * @author Rolly Mantri
 *
 */
@RestController
public class UrlController {
	/**
	 * Logger to log messages from projects
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);
	
	@Autowired
	UrlService urlService;

/**
 * 
 * @param originalUrl
 * @return
 * @throws JsonProcessingException
 * @throws IOException
 */
	@RequestMapping(value="/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Map<String, String>> getShortenedUrl(@RequestBody(required=true) JSONObject originalUrl) throws  JsonProcessingException, IOException
	{
		final String orgUrl = originalUrl.get("url").toString();
		LOGGER.info("Received shorten request: " + orgUrl);
		String shortUrl = urlService.saveUrl(orgUrl);
		if (shortUrl == "INVALID") {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<>(Collections.singletonMap("ShortUrl", shortUrl),HttpStatus.OK);
		}
	}
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws Exception
	 */
	@RequestMapping(value = "redirect/{shortUrl}", method=RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String shortUrl, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        LOGGER.info("Received shortened url to redirect: " + shortUrl);
        String redirectUrlString = urlService.getLongURLFromShortUrl(shortUrl);
        if (redirectUrlString == "NOT_FOUND") {
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Short URL not found.");
        	
        	return null;
        }
        else if (redirectUrlString == "INVALID") {
        	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "URL no longer valid");
        	return null;
        }
        LOGGER.info("Original URL: " + redirectUrlString);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrlString);
        return redirectView;
    }
}

