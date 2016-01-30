package org.sualk.twitterpicturegatherer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sualk.twitterpicturegatherer.services.CollectorService;
import org.sualk.twitterpicturegatherer.services.CollectorStatus;

@RestController
@RequestMapping("/api")
public class TweetController {

	@Autowired
	CollectorService collectorService;

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public void startCollecting() {
		collectorService.startFiltering();
	}

	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public void stopCollecting() {
		collectorService.stopFiltering();
	}

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public CollectorStatus getStatus() {
		return collectorService.getStatus();
	}

}
