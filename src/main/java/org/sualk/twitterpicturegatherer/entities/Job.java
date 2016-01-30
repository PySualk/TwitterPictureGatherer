package org.sualk.twitterpicturegatherer.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")
public class Job {

	@Id
	private String id;
	private String name;
	private String description;
	private List<String> keywords;
	private Date validFrom;
	private Date validTo;

	public Job() {

	}

	public Job(String name, String description, List<String> keywords, Date validFrom, Date validTo) {
		this.name = name;
		this.description = description;
		this.keywords = keywords;
		this.validFrom = validFrom;
		this.validTo = validTo;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String toString() {
		return "Job[" + name + ", " + description + ", " + keywords + "]";
	}
}
