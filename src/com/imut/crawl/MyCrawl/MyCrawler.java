package com.imut.crawl.MyCrawl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.imut.bll.CollectLinkbll;
import com.imut.bll.Queuebll;
import com.imut.crawl.analyze.LinkFilter;
import com.imut.crawl.analyze.TextFilter;
import com.imut.crawl.download.DownloadHtml;
import com.imut.model.CollectLink;

public class MyCrawler {
	private static CollectLink collectLink = new CollectLink();

	private static TextFilter textFilter = new TextFilter();
	private static DownloadHtml downloadHtml = new DownloadHtml();
	private static String defaulturl = "http://www.sina.com.cn/";
	private static String pagestr;
	private static String pageEncode;
	private static String text="";
	static int i = 0;
	private static Queue<String> queue = new LinkedList<String>();

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		doTask();

	}

	public static void getUrlQueue() {

		queue = Queuebll.getUrl(); // �Ӷ�����ȡ��URL

	}

	public static String str;

	public static void doTask() throws Exception {
		while (true) {

			defaulturl = queue.poll(); // �Ӷ�����ȡ��URL
			if (defaulturl == null) {
				System.out.println("------�����ݿ��л�ȡurl����-------");			
				getUrlQueue();
				System.out.println("------��ȡ�������--------");
				defaulturl = queue.poll();
				if (defaulturl == null) { // �����˳��������жϣ��ж�url�����Ƿ�Ϊ�գ�Ϊ��������˳�
					System.out.println("������û��ʳ���ˣ���Ҫ�˳�����");
					System.exit(1);
					break;
				}
			}

			if (defaulturl.length() > 6) {
				pagestr = downloadHtml.getHTMLByGet(defaulturl, "gb2312");
				if (pagestr == "") {
					continue;
				} else {

					// ���Դ�ļ����� System.out.println(pagestr);
					/*
					 * System.out .println(
					 * "pagehtmlend-------------------------------------------------------------"
					 * );
					 */
					String title = LinkFilter.getTitle(pagestr);
					/*
					 * System.out
					 * .println("��ҳ����---------------------------------------------"
					 * + title);
					 */

					/*
					 * //System.out .println(
					 * " �����ǣ���ҳ���ı�----------------------------------------------------------------------"
					 * );
					 */

					text = textFilter.getPlainText(pagestr);
					/* System.out.println(text); */

					pageEncode = LinkFilter.getCharSet(pagestr);
					/*
					 * System.out .println(
					 * "-------------------------------------------------------------"
					 * + pageEncode); System.out .println(
					 * "-------------------------------------------------------------"
					 * );
					 */
					LinkFilter.findLinkByJ(pagestr);

					try{
					if(isStore(text)){						
						// ��ȡ��������ǰʱ��yyyy-MM-dd HH:mm:ss
						String currentTime = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(Calendar
								.getInstance().getTime());					
						writeToFile(currentTime,defaulturl,text);
						i++;
						System.out.println("���ڴ洢��" + i + "����������");				
					}
					}catch(Exception e){
						e.printStackTrace();
						continue;
						
					}
					continue;
				}// if (pagestr == "") end
			} else {
				/* System.out.println("��������Ч����"); */
				continue;
			}// if (defaulturl.length() > 6) end

		}// while(true) end

	}
	   //�ж���ҳ���ı��Ƿ����
		public static boolean isStore  (String textOfBody) throws Exception{
			
			for(int i=0;i<textOfBody.length();i++){
				if(textOfBody.codePointAt(i)>57923 && textOfBody.codePointAt(i)<58192){
					return true;			
				}
			}		
		return false;	
		}
	
	/**
	 * Prints some data to a file using a BufferedWriter
	 */
	private static String baseDirOfStore = "/home/zhangzeguang/filecrawled/";
	private static String filePathOfAll = "";
	
	public static void writeToFile(String filename,String link,String textOfBody) {
		OutputStreamWriter output = null;
		BufferedWriter bw = null;
		try {
			// Construct the BufferedWriter object
			filePathOfAll = baseDirOfStore + filename+".txt";
			
			output = new OutputStreamWriter(new FileOutputStream(filePathOfAll),"UTF-8"); 
			bw = new BufferedWriter(output); 
			// Start writing to the output stream
			bw.write(link);
			bw.newLine();
			bw.write(textOfBody); //����дһ������
			bw.flush();
			
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// Close the BufferedWriter
			try {
				if(output != null){
					output.flush();
					//output.close();
					
				}
				if (bw != null) {
					bw.flush();
					//bw.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
