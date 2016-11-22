package com.imut.crawl.analyze;

import java.io.UnsupportedEncodingException;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;

public class TextFilter {

	/**
	 * 根据提供的URL，获取此URL对应网页的纯文本信息
	 * 
	 * @param url
	 *            提供的URL链接
	 * @return RL对应网页的纯文本信息
	 * @throws ParserException
	 * @throws UnsupportedEncodingException
	 */

	public String getPlainText(String htmlstr) throws ParserException,
			UnsupportedEncodingException {

		StringBean sb = new StringBean();
		try {
			Parser parser = new Parser();
			parser.setEncoding(parser.getEncoding());
			parser.setInputHTML(htmlstr);

			// 设置不需要得到页面所包含的链接信息
			sb.setLinks(false);
			// 设置将不间断空格由正规空格所替代
			sb.setReplaceNonBreakingSpaces(true);
			// 设置将一序列空格由一个单一空格所代替
			sb.setCollapse(true);
			// 传入要解析的URL

			parser.visitAllNodesWith(sb);
		} catch (ParserException e) {
			// 后续自己建日志 将异常写入日志
			// log.error(e);

		}
		// return new String(sb.getStrings().getBytes("utf-8"));
		return sb.getStrings();
	}

}
