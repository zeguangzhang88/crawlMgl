package com.imut.bll;

import com.imut.dal.CollectLinkdal;
import com.imut.dal.CrawledLinkdal;
import com.imut.model.CrawledLink;

/**
 * CrawledLinkService瀹炵幇
 */

public class CrawledLinkbll {



	public static  boolean save(CrawledLink crawledLink) {

		return CrawledLinkdal.save(crawledLink);
	}


}
