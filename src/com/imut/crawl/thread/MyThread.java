package com.imut.crawl.thread;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.imut.crawl.download.DownloadHtml;
import com.imut.model.CollectLink;

public class MyThread extends Thread {
	
	int num;
	DownloadHtml downloadHtml = new DownloadHtml();
	public MyThread(int num) {
		this.num = num;
	}
	
	public void run(){
		
		try {
			downloadHtml.doTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
}
