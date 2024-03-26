package com.msm.nogari.core.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 최대희
 * @since 2024-01-19
 */
@Getter
@Setter
public class NewsResponse {
	@JsonProperty("lastBuildDate")
	private String lastBuildDate;

	@JsonProperty("total")
	private int total;

	@JsonProperty("start")
	private int start;

	@JsonProperty("display")
	private int display;

	@JsonProperty("items")
	private List<NewsItem> items;

	// Getter and Setter methods

	// Inner class representing the NewsItem
	@Getter
	@Setter
	public static class NewsItem {
		@JsonProperty("title")
		private String title;

		@JsonProperty("originallink")
		private String originallink;

		@JsonProperty("link")
		private String link;

		@JsonProperty("description")
		private String description;

		@JsonProperty("pubDate")
		private String pubDate;

		private String imgUrl;

		private String regDt;

		// Getter and Setter methods
	}
}
