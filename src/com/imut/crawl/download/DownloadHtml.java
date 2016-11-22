/*
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.imut.crawl.download;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.imut.crawl.analyze.LinkFilter;

public class DownloadHtml {

	static int i;

	// 获取网页html
	public static String getHTMLByGet(String url, String encoding)
			throws Exception {
		if(LinkFilter.getLinkType(url,".*apk$")||LinkFilter.getLinkType(url,".*exe$")||LinkFilter.getLinkType(url,".*rar$")||LinkFilter.getLinkType(url,".*zip$")
				||LinkFilter.getLinkType(url,".*pps$")||LinkFilter.getLinkType(url,".*ppt$")){
			return "";
			
		}else
		{
		String pagehtml;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(20000).setConnectTimeout(4000).build();// 设置请求和传输时间
			httpget.setConfig(requestConfig);
			System.out.println("Executing request " + httpget.getURI());
			i = i + 1;
			System.out.println("本次运行正在抓取第    " + i + "  链接");
			try {
				response = httpclient.execute(httpget);
			} catch (SSLHandshakeException e) {
				e.printStackTrace();
				// doTask();

			} catch (Exception e) {
				e.printStackTrace();
				// doTask();
				pagehtml = "";
			}

			
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();

				/*System.out.println("内容类型是: " + entity.getContentType());*/
				try {
					pagehtml = EntityUtils.toString(entity, encoding);
				} catch (Exception e) {
					e.printStackTrace();
					// doTask();
					pagehtml = "";
				}
			} else if (statusCode == 404) {

				pagehtml = "";
				// doTask();
				System.out
						.println("404-------------------------------------------------------");

			} else {

				pagehtml = "";
				// doTask();
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// doTask();
			pagehtml = "";
		} catch (Exception e) {
			System.out.println("下载异常--------------------");
			e.printStackTrace();
			// doTask();
			pagehtml = "";
		} finally {
			if (response != null) {
				response.close();
			}
			if (httpclient != null) {

				httpclient.close();
			}

		}

		return pagehtml;
		}
	}

	public void getHTMLByPost() throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://targethost/login");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));
		nvps.add(new BasicNameValuePair("password", "secret"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response2 = httpclient.execute(httpPost);

		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			httpPost.releaseConnection();
		}
	}

}
