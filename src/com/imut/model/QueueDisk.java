package com.imut.model;

import java.io.Serializable;

/**
 * TbQueue entity. @author MyEclipse Persistence Tools
 */

public class QueueDisk implements Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String link;
	private String host;
	private String weight;
	private String date;

	// Constructors

	/** default constructor */
	public QueueDisk() {
	}

	/** full constructor */
	public QueueDisk(Integer id, String link, String host, String weight,
			String date) {
		super();
		this.id = id;
		this.link = link;
		this.host = host;
		this.weight = weight;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	// Property accessors

}