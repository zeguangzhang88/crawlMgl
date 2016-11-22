package com.imut.model;

import java.io.Serializable;

/**
 * TbUrl entity. @author MyEclipse Persistence Tools
 */

public class CrawledLink implements Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String link;

	// Constructors

	/** default constructor */
	public CrawledLink() {
	}

	/** full constructor */
	public CrawledLink(Integer id, String link) {
		super();
		this.id = id;
		this.link = link;

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

	// Property accessors

}