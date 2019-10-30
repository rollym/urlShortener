package preAssignment.urlShortener.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
public class DatabaseSequence {
 
    @Id
    private String id;
 
    private long seq;
 
    //getters and setters omitted
    public long getSeq() {
    	return this.seq;
    }
}