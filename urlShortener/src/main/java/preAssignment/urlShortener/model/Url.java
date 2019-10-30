package preAssignment.urlShortener.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="urls")
public class Url {
	/**
	 * sequence name
	 */
	@Transient
    public static final String SEQUENCE_NAME = "urls_sequence";
	
	@Id
	private long id;
	
	/**
	 * the entered long url
	 */
	private String longUrl;
	/**
	 * The returned short url
	 */
	private String shortUrl;
	/**
	 * The number of times url is accessed
	 */
	private int accessCount;
	/**
	 * To check if url is valid
	 */
	private boolean isValid;
	/**
	 * 
	 */
	public Url() {
		this.isValid = true;
		this.accessCount = 0;
	}
		/**
		 * 
		 * @param longUrl
		 */
	public Url(String longUrl) {
		super();
		this.longUrl = longUrl;
		this.isValid = true;
		this.accessCount = 0;
	}

/**
 * 
 * @return id of the url
 */
	public long getId() {
		return id;
	}
/**
 * 
 * @param id
 */
	public void setId(long id) {
		this.id = id;
	}
/**
 * 
 * @return
 */
	public String getLongUrl() {
		return longUrl;
	}
	
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
	public int getAccessCount() {
		return accessCount;
	}
	
	public void setAccessCount(int aCount) {
		this.accessCount = aCount;
	}
	
	public boolean getIsValid() {
		return this.isValid;
	}
	
	public void setIsValid(boolean valid) {
		this.isValid = valid;
	}
}