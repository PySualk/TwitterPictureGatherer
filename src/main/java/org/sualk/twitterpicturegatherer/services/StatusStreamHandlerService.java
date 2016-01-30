package org.sualk.twitterpicturegatherer.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.sualk.twitterpicturegatherer.entities.Tweet;
import org.sualk.twitterpicturegatherer.entities.TweetRepository;

import com.twitter.hbc.twitter4j.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.message.StallWarningMessage;

import twitter4j.MediaEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

@Service
public class StatusStreamHandlerService {

	static Logger log = LoggerFactory.getLogger(StatusStreamHandlerService.class);

	@Autowired
	TweetRepository tweetRepository;

	@Value("${picture.directory}")
	private String directoryName;

	public StatusStreamHandlerService() {
	}

	public StatusListener createListener() {

		return new StatusStreamHandler() {

			@Override
			public void onException(Exception arg0) {
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				log.info("onTrackLimitationNotice: {}", arg0);
			}

			@Override
			public void onStatus(Status status) {

				// Only process tweets with some media object
				if (status.getMediaEntities().length == 0)
					return;

				Tweet tweet = new Tweet();
				tweet.setTweetId(status.getId());
				tweet.setCreated_at(status.getCreatedAt());
				tweet.setLang(status.getLang());
				tweet.setText(status.getText());
				tweet.setTimestamp(status.getCreatedAt().getTime());
				tweet.setCoordinates(status.getGeoLocation());
				tweet.setScreenName(status.getUser().getScreenName());

				// Use only first picture in mediaEntities
				// (usually mediaEntities only contains one picture)
				List<MediaEntity> mediaEntities = new ArrayList<MediaEntity>(Arrays.asList(status.getMediaEntities()));
				for (MediaEntity me : mediaEntities) {
					if (me.getType().equals("photo")) {
						tweet.setPictureUrl(mediaEntities.get(0).getMediaURL());
						break;
					}
				}

				String picturePath = directoryName + "/" + tweet.getTweetId().toString();
				if (tweet.getPictureUrl().endsWith("jpg"))
					picturePath += ".jpg";
				else if (tweet.getPictureUrl().endsWith("png"))
					picturePath += ".png";
				else if (tweet.getPictureUrl().endsWith("gif"))
					picturePath += ".gif";
				tweet.setPicturePath(picturePath);

				// Download the image
				try {
					CloseableHttpClient httpclient = HttpClients.createDefault();
					HttpGet httpGet = new HttpGet(tweet.getPictureUrl());
					CloseableHttpResponse response = httpclient.execute(httpGet);
					try {
						if (!response.getStatusLine().toString().endsWith("OK")) {
							log.error("Error fetching Image for Tweet " + tweet.getTweetId());
							return;
						}
						HttpEntity entity1 = response.getEntity();
						OutputStream outputStream = new FileOutputStream(picturePath);
						IOUtils.copy(entity1.getContent(), outputStream);
						EntityUtils.consume(entity1);
						outputStream.close();
					} finally {
						response.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				// Store tweet at database
				tweetRepository.save(tweet);

			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				log.warn("{}", arg0);
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// A location was deleted
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// A status was deleted
			}

			@Override
			public void onUnknownMessageType(String arg0) {
			}

			@Override
			public void onStallWarningMessage(StallWarningMessage arg0) {
				log.warn("{}", arg0);
			}

			@Override
			public void onDisconnectMessage(DisconnectMessage arg0) {
				log.warn("{}", arg0);
			}
		};

	}

}
