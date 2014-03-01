package cn.edu.sdnu.i.util.xauth;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;
import android.util.Xml.Encoding;

/*xAuth验证参数 
 虽然xAuth标准允许xAuth验证参数通过Http头、URL参数或Post参数传输，

 但为了区分参数及提高安全性和效率，本系统的全部服务只从Http头中获取

 xAuth验证信息。 
 在提交xAuth验证信息时，请将所有信息按如下算法提交到Authorization类

 型的头部中。 
 “OAuth ” +  
 sorted_header_params.each { | k, v | 
 url_encode ( k ) + "=\”" + url_encode ( v ) + “\”” 
 }.join(",") 
 【例】xAuth验证参数为： 
 oauth_consumer_key=test_consumer_key 
 oauth_nonce=1234567890 
 oauth_signature=MzH0d6CIwakPrh8FM8DmaiUwvvY= 
 oauth_signature_method=HMAC-SHA1 
 oauth_timestamp=9999999999 
 oauth_version=1.0 
 x_auth_mode=client_auth 
 x_auth_password=PASSWORD 
 x_auth_username=2013001001 

 则需要附加如下的Http请求头： 
 Authorization: OAuth 

 oauth_consumer_key="test_consumer_key",oauth_nonce="1234567890",oa

 uth_signature="MzH0d6CIwakPrh8FM8DmaiUwvvY

 %3D",oauth_signature_method="HMAC-

 SHA1",oauth_timestamp="9999999999",oauth_version="1.0",x_auth_mode

 ="client_auth",x_auth_password="PASSWORD",x_auth_username="2013001

 001" 
 */
public class HttpFetcher {
	// / <summary>
	// / 从指定URL获取网页内容
	// / </summary>
	// / <param name="method">请求方式</param>
	// / <param name="url">指定URL</param>
	// / <param name="encoding">字符编码</param>
	// / <param name="timeout">超时时间</param>
	// / <param name="headers">请求头部信息</param>
	// / <returns>网页内容</returns>

	public static String GetRemoteContent(final String url, Encoding encoding,
			int _fetchTimeout, final String headers)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		get.setHeader("Authorization", headers);
		HttpResponse httpResponse = httpClient.execute(get);
		Log.d("test", "15.getStatusCode:"
				+ httpResponse.getStatusLine().getStatusCode());
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}

	/*
	 * try { FutureTask<String> task = new FutureTask<String>( new
	 * Callable<String>() {
	 * 
	 * @Override public String call() throws Exception {
	 * 
	 * HttpClient httpClient = new DefaultHttpClient();
	 * 
	 * HttpGet get = new HttpGet(url);
	 * 
	 * get.setHeader("Authorization", headers);
	 * 
	 * HttpResponse httpResponse = httpClient.execute(get);
	 * 
	 * Log.d("test","15.getStatusCode:"+httpResponse.getStatusLine().getStatusCode
	 * ());
	 * 
	 * if (httpResponse.getStatusLine().getStatusCode() == 200) {
	 * 
	 * String result = EntityUtils .toString(httpResponse.getEntity()); return
	 * result; } return null; } });
	 * 
	 * new Thread(task).start();
	 * 
	 * return task.get();
	 * 
	 * } catch (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (ExecutionException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * return null;
	 */
}
