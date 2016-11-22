package com.imut.crawl.analyze;

import java.io.UnsupportedEncodingException;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;

public class TextFilter {

	/**
	 * �����ṩ��URL����ȡ��URL��Ӧ��ҳ�Ĵ��ı���Ϣ
	 * 
	 * @param url
	 *            �ṩ��URL����
	 * @return RL��Ӧ��ҳ�Ĵ��ı���Ϣ
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

			// ���ò���Ҫ�õ�ҳ����������������Ϣ
			sb.setLinks(false);
			// ���ý�����Ͽո�������ո������
			sb.setReplaceNonBreakingSpaces(true);
			// ���ý�һ���пո���һ����һ�ո�������
			sb.setCollapse(true);
			// ����Ҫ������URL

			parser.visitAllNodesWith(sb);
		} catch (ParserException e) {
			// �����Լ�����־ ���쳣д����־
			// log.error(e);

		}
		// return new String(sb.getStrings().getBytes("utf-8"));
		return sb.getStrings();
	}

}
