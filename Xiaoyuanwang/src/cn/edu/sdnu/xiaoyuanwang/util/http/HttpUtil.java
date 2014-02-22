/**
 * 
 */
package cn.edu.sdnu.xiaoyuanwang.util.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * @author gaobaozhong
 * 提供了两个方法getRequest和postRequest，可以返回相应的String结果
 */
public class HttpUtil {
//
	public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL = "http://210.44.14.10/OAuth/";

	public static String getRequest(final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {

						HttpGet get = new HttpGet(url);

						HttpResponse httpResponse = httpClient.execute(get);												
						
						if (httpResponse.getStatusLine().getStatusCode() == 200) {

							String result = EntityUtils.toString(httpResponse
									.getEntity());
							return result;
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}

	public static String postRequest(final String url,
			final Map<String, String> rawParams) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {

						HttpPost post = new HttpPost(url);

						List<NameValuePair> params = new ArrayList<NameValuePair>();
						for (String key : rawParams.keySet()) {

							params.add(new BasicNameValuePair(key, rawParams
									.get(key)));
						}

						post.setEntity(new UrlEncodedFormEntity(params, "gbk"));

						HttpResponse httpResponse = httpClient.execute(post);

						if (httpResponse.getStatusLine().getStatusCode() == 200) {

							String result = EntityUtils.toString(httpResponse
									.getEntity());
							return result;
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
}