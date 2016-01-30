package org.sualk.twitterpicturegatherer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.sualk.twitterpicturegatherer.entities.Job;
import org.sualk.twitterpicturegatherer.entities.JobRepository;
import org.sualk.twitterpicturegatherer.entities.TweetRepository;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jStatusClient;

import twitter4j.StatusListener;

@Service
public class CollectorService {

	@Value("${twitter.consumer.key}")
	private String consumerKey;

	@Value("${twitter.consumer.secret}")
	private String consumerSecret;

	@Value("${twitter.access.token}")
	private String accessToken;

	@Value("${twitter.access.secret}")
	private String accessSecret;

	@Autowired
	TweetRepository tweetRepository;

	@Autowired
	StatusStreamHandlerService statusStreamHandlerImpl;

	private BasicClient client = null;

	static Logger log = LoggerFactory.getLogger(CollectorService.class);

	@Autowired
	private JobRepository jobRepository;

	public CollectorStatus getStatus() {
		if (client == null) {
			return CollectorStatus.IDLE;
		}
		if (client.isDone()) {
			return CollectorStatus.IDLE;
		} else {
			return CollectorStatus.RUNNING;
		}
	}

	public void startFiltering() {

		List<Job> jobs = jobRepository.findAll();
		log.info("There are " + jobs.size() + " jobs in the database.");

		if (jobs.size() == 0) {
			log.error("No jobs found at the database. Please define some jobs first.");
			return;
		}

		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		List<String> keywords = new ArrayList<>();
		for (Job j : jobs) {
			keywords.addAll(j.getKeywords());
		}
		endpoint.trackTerms(keywords);

		Authentication auth = new OAuth1(consumerKey, consumerSecret, accessToken, accessSecret);

		this.client = new ClientBuilder().hosts(Constants.STREAM_HOST).endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		int numProcessingThreads = 4;
		ExecutorService service = Executors.newFixedThreadPool(numProcessingThreads);

		List<StatusListener> listeners = new ArrayList<>();
		listeners.add(statusStreamHandlerImpl.createListener());

		Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(client, queue, listeners, service);

		t4jClient.connect();
		for (int threads = 0; threads < numProcessingThreads; threads++) {
			t4jClient.process();
		}

	}

	public void stopFiltering() {
		client.stop();
	}

}
