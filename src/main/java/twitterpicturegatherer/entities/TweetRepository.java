package twitterpicturegatherer.entities;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tweets", path = "tweets")
public interface TweetRepository extends MongoRepository<Tweet, String> {

	Tweet findByTweetId(@Param("tweetId") Long tweetId);

}
