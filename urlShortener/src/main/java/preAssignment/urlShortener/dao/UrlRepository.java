package preAssignment.urlShortener.dao;

import preAssignment.urlShortener.model.Url;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Rolly Mantri
 *
 */
@Repository
public interface UrlRepository extends MongoRepository<Url,Long>
{
	//Optional<Url> findBylongUrl(String longUrl);
	
}