package com.imut.bll;

import java.sql.ResultSet;
import java.util.List;
import java.util.Queue;

import com.imut.dal.Queuedal;
import com.imut.model.QueueDisk;

/**
 * QueueService瀹炵幇
 */

public class Queuebll {


	public static boolean save(QueueDisk queue) {

		return Queuedal.save(queue);

	}

	public static Queue<String> getUrl() {

		return Queuedal.getUrl();

	}



}
