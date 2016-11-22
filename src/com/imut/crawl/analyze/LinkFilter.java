package com.imut.crawl.analyze;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imut.bll.CrawledLinkbll;
import com.imut.bll.Queuebll;
import com.imut.crawl.judge.BloomFilter;
import com.imut.crawl.judge.BloomFilterInit;
import com.imut.model.CrawledLink;
import com.imut.model.QueueDisk;

public class LinkFilter {

	private static CrawledLink crawledLink = new CrawledLink();
	private static QueueDisk queue = new QueueDisk();
	private static BloomFilter bloomFilter = new BloomFilter();
	private static BloomFilterInit bloomFilterInit = new BloomFilterInit();
	private static int i;
	static String textOfBody;
	static String textOfAnthor;
	static String weight;

	public LinkFilter() {

		bloomFilter = bloomFilterInit.getBloomFilter();// 给布隆过滤器赋值
	}

	// 通过JSOUP获取链接
	public static void findLinkByJ(String html) throws Exception {

		// 获取服务器当前时间yyyy-MM-dd HH:mm:ss
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		try {
			Document doc = Jsoup.parse(html);
			textOfBody = doc.body().text(); // "An example link"
			//System.out.println("网页body文本-----------------------------" + textOfBody);
			Elements links = doc.getElementsByTag("a"); // <a
														// href='http://example.com/'><b>example</b></a>

			for (Element link : links) {				
				String linkHref = link.attr("abs:href"); // "http://example.com/"				
				textOfAnthor = link.text();
				 
				//System.out.println("------linkText---"+ textOfAnthor);
				 
				if (linkHref.length() < 4 || getLinkType(linkHref, ".*apk$")
						|| getLinkType(linkHref, ".*exe$")
						|| getLinkType(linkHref, ".*rar$")
						|| getLinkType(linkHref, ".*zip$")
						|| getLinkType(linkHref, ".*pps$")
						|| getLinkType(linkHref, ".*ppt$")) { 
					/* 判断是否为有效链接
					System.out.println("这是无效链接，不存储，直接丢弃"); */

				} else {
					if (!bloomFilter.contains(linkHref)) {
						bloomFilter.add(linkHref);
						i++;
						if (i % 1000 == 0) {
							bloomFilterInit.setBloomFilter(bloomFilter);// 给布隆过滤器赋值
							System.out.println("保存至布隆过滤器");
							Thread.sleep(1000);
						}

						/*crawledLink暂时用布隆过滤器能够满足，不需要用到数据库，所以下面两行代码暂时省略
						 * crawledLink.setLink(linkHref);
						CrawledLinkbll.save(crawledLink);*/

						weight= computeWeight(textOfAnthor,textOfBody);
						
						double weightd = Double.parseDouble(weight);
						if(weightd>=0.5){
							queue.setDate(currentTime);
							queue.setHost("123");
							queue.setLink(linkHref);
							queue.setWeight(weight);
							Queuebll.save(queue);							
						}
					}

				}
			} // for循环结束
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}// findLinkByJ方法结束

	//计算链接权值
	public static String computeWeight(String textOfAnchor,String textOfBody){
		
		for(int i=0;i<textOfAnthor.length();i++){
			if(textOfAnchor.codePointAt(i)>57923 && textOfAnchor.codePointAt(i)<58192){
				return "1";								
			}
		}
		for(int i=0;i<textOfAnthor.length();i++){
			if(textOfAnchor.codePointAt(i)>57923&&textOfAnchor.codePointAt(i)<58192){
				return "0.5";			
			}
		}		
	return "0";	
	}
	
	// 通过htmlparser获取链接
	public void findLinkByH(String htmlcode) {

		Parser parser = Parser.createParser(htmlcode, "GBK");
		HtmlPage page = new HtmlPage(parser);
		try {
			parser.visitAllNodesWith(page);
		} catch (ParserException e1) {
			e1 = null;
		}
		NodeList nodelist = page.getBody();
		NodeFilter filter = new TagNameFilter("A");
		nodelist = nodelist.extractAllNodesThatMatch(filter, true);
		for (int i = 0; i < nodelist.size(); i++) {
			LinkTag link = (LinkTag) nodelist.elementAt(i);
			System.out.println(link.getAttribute("href") + "\n");
			// System.Console.Write(link.getAttribute("href") + "\n");
		}
	}

	// 正则表达式获取链接
	public List<String> findA(String s) {
		String regex;
		final List<String> list = new ArrayList<String>();
		regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	// 正则表达式 获取标题
	public static String getTitle(String s) {
		String regex;
		String title = "";
		final List<String> list = new ArrayList<String>();
		regex = "<title>.*?</title>";
		final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		for (int i = 0; i < list.size(); i++) {
			title = title + list.get(i);
		}
		return outTag(title);
	}

	// 获取脚本
	public List<String> getScript(final String s) {
		String regex;
		final List<String> list = new ArrayList<String>();
		regex = "<script.*?</script>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	// 获取css
	public List<String> getCSS(final String s) {
		String regex;
		final List<String> list = new ArrayList<String>();
		regex = "<style.*?</style>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	// 去掉标记
	public static String outTag(final String s) {
		return s.replaceAll("<.*?>", "");
	}

	public static String getCharSet(String content) {
		String regex = "<meta[^>]*?charset=(\\w+)[\\W]*?>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find())
			return matcher.group(1);
		else
			return null;
	}


	// 判断链接的类型
	public static boolean getLinkType(String url, String reg) {
		String regex = reg;
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(url);
		if (matcher.find())
			return true;
		else
			return false;

	}

}
