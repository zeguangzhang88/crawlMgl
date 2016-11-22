package com.imut.model;

import java.io.Serializable;

/**
 * TbCollection entity. @author MyEclipse Persistence Tools
 */

public class CollectLink implements Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String link;
	private String host;
	private String text;
	private String weight;
	private String date;

	// Constructors

	/** default constructor */
	public CollectLink() {

	}

	public CollectLink(Integer id, String link, String host, String text,
			String weight, String date) {
		super();
		this.id = id;
		this.link = link;
		this.host = host;
		this.text = text;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** full constructor */

	// Property accessors

}