package com.gobeike.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 标题、简要说明. <br>
 * 提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具类 类详细说明.目前支持3种提交方法（POST，GET，数据流）.
 * <p>
 * Copyright: Copyright (c) 2014-8-25 上午11:10:21
 * <p>
 * Company: 联著实业
 * <p>
 * 
 * @author zhawz@lianzhu.com
 * @version 1.0.0
 */
public class GobeikeHttpClient
{

	private final static Logger logger = Logger.getLogger(GobeikeHttpClient.class);

	/** http的端口 */
	private static final int HTTP_PORT = 80;

	/** https的端口 */
	private static final int HTTPS_PORT = 443;

	/** http标记位 */
	private static final String HTTP = "http";

	/** https标记位 */
	private static final String HTTPS = "https";

	/** 设置编码格式 */
	private   String encoding = EnumEncode.utf_8.getEncoding();

	private String getEncoding() {
		return encoding;
	}

	public void setEncoding(EnumEncode encoding) {
		this.encoding = encoding.getEncoding();
	}

	/** 设置每个路由默认最大连接数 */
	private int maxConnector = 50;

	// 请求超时
	private int connectTimeout = 120000;

	// 读取超时
	private int readTimeout = 120000;

	private static DefaultHttpClient httpclient = null;

	private static SchemeRegistry schemeRegistry = new SchemeRegistry();

	private static PoolingClientConnectionManager clientConnectionManager = null;

	static
	{
		schemeRegistry.register(new Scheme(HTTP, HTTP_PORT, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme(HTTPS, HTTPS_PORT, SSLSocketFactory.getSocketFactory()));

		clientConnectionManager = new PoolingClientConnectionManager(schemeRegistry);
	}

	private void _init()
	{
		clientConnectionManager.setDefaultMaxPerRoute(this.maxConnector);
		httpclient = new DefaultHttpClient(clientConnectionManager);
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.connectTimeout);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.readTimeout);
	}

	/**
	 * 默认构造方法 ，
	 * 
	 * @param maxConnector
	 */
	public GobeikeHttpClient()
	{
		_init();
	}

	/**
	 * 构造方法
	 * 
	 * @param maxConnector 设置每个实例化对象的最大连接数
	 */
	public GobeikeHttpClient(int maxConnector)
	{
		this.maxConnector = maxConnector;
		_init();
	}

	/**
	 * 初始化超时时间
	 * 
	 * @param connectTimeout
	 * @param readTimeout
	 */
	public GobeikeHttpClient(int connectTimeout, int readTimeout)
	{
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		_init();
	}

	/**
	 * 通过post方式提交发送请求
	 * 
	 * @param url 请求url地址
	 * @param params 请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByPost(String url, List<NameValuePair> params) throws Exception
	{

		logger.info("[GobeikeHttpClient:sendRequestByStream],url:" + url);

		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try
		{
			httpPost = new HttpPost(url);
			HttpEntity entity = new UrlEncodedFormEntity(params, getEncoding());
			httpPost.setEntity(entity);

			response = httpclient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 400)
			{
				throw new Exception("HTTP REQUEST FAIL,HTTP_STATUS:" + status);
			}
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), getEncoding()));

			String lines = "";
			while ((lines = reader.readLine()) != null)
			{
				builder.append(lines);
			}
		}
		catch (Exception e)
		{
			logger.error("[GobeikeHttpClient:sendRequestByPost],url:" + url);
			logger.error("[GobeikeHttpClient:sendRequestByPost]", e);
			throw e;
		}
		finally
		{

			if (null != reader)
			{
				reader.close();
				reader = null;
			}

			if (null != response)
			{
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpPost = null;

			httpclient.getConnectionManager().closeExpiredConnections();
		}

		logger.info("[GobeikeHttpClient:sendRequestByStream],response content:" + builder.toString());

		return builder.toString();
	}

	/**
	 * 通过流形式方式提交发送请求
	 * 
	 * @param url 请求url地址
	 * @param strContent 请求参数
	 * @param strContent 编码方式
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByStream(String url, String strContent, String enCoding) throws Exception
	{
		logger.info("[GobeikeHttpClient:sendRequestByStream],url:" + url + ",sendContent:" + strContent);

		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try
		{

			httpPost = new HttpPost(url);
			HttpEntity entity = new StringEntity(strContent, enCoding);
			httpPost.setEntity(entity);

			response = httpclient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 400)
			{
				throw new Exception("HTTP REQUEST FAIL,HTTP_STATUS:" + status);
			}
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), enCoding));

			String lines = "";
			while ((lines = reader.readLine()) != null)
			{
				builder.append(lines);
			}
		}
		catch (Exception e)
		{
			logger.error("[GobeikeHttpClient:sendRequestByStream],url:" + url + ",sendContent:" + strContent, e);
		}
		finally
		{

			if (null != reader)
			{
				reader.close();
				reader = null;
			}

			if (null != response)
			{
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpPost = null;
			httpclient.getConnectionManager().closeExpiredConnections();
		}

		logger.info("[GobeikeHttpClient:sendRequestByStream],response content:" + builder.toString());

		return builder.toString();
	}

	/**
	 * 通过流形式方式提交发送请求
	 * 
	 * @param url 请求url地址
	 * @param strContent 请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByStream(String url, String strContent) throws Exception
	{

		return sendRequestByStream(url, strContent, getEncoding());
	}

	/**
	 * 通过Get方式提交发送请求
	 * 
	 * @param url 请求url地址
	 * @param strContent 请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByGet(String url) throws Exception
	{

		logger.info("[GobeikeHttpClient:sendRequestByStream],url:" + url);

		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpGet httpGet = null;
		try
		{

			httpGet = new HttpGet(url);

			response = httpclient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 400)
			{
				throw new Exception("HTTP REQUEST FAIL,HTTP_STATUS:" + status);
			}
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), getEncoding()));

			String lines = "";
			while ((lines = reader.readLine()) != null)
			{
				builder.append(lines);
			}
		}
		catch (Exception e)
		{

			logger.error("[GobeikeHttpClient:sendRequestByStream],url:" + url);
			logger.error("[GobeikeHttpClient:sendRequestByStream]", e);
			throw e;
		}
		finally
		{

			if (null != reader)
			{
				reader.close();
				reader = null;
			}

			if (null != response)
			{
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpGet = null;
			httpclient.getConnectionManager().closeExpiredConnections();
		}

//		logger.info("[GobeikeHttpClient:sendRequestByStream],response content:" + builder.toString());

		return builder.toString();
	}

	public static String sendHttpsRequestByGet(String url)
	{
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			SSLContext ctx = SSLContext.getInstance("SSL");
			X509TrustManager tm = new X509TrustManager()
			{

				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException
				{

				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException
				{
				}

				public X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);

			ClientConnectionManager ccm = httpclient.getConnectionManager();

			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));

			HttpGet httpget = new HttpGet(url);
			HttpParams params = httpclient.getParams();

			params.setParameter("param1", "paramValue1");

			httpget.setParams(params);
			System.out.println("REQUEST:" + httpget.getURI());

			return (String) httpclient.execute(httpget, new BasicResponseHandler());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) throws Exception
	{
		String url = "http://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.2";
		GobeikeHttpClient client = new GobeikeHttpClient();
//		System.out.println(GobeikeHttpClient.sendHttpsRequestByGet(url));
		System.out.print(client.getEncoding());
		
	}


}
