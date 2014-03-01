package cn.edu.sdnu.i.util.xauth;

/*
 * XAuth.java
 *
 * Copyright (C) 2005-2010 Tommi Laukkanen
 * http://www.substanceofcode.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml.Encoding;
import cn.edu.sdnu.i.LoginActivity;

/**
 * 
 * @author HennSun xAuth简介 使用xAuth认证方式，您仍然需要了解如何生成OAuth签名。
 *         为了方便桌面应用和移动应用，特别是那些缺乏浏览器支持的应用，xAuth认证为这类应用提供了一种使用用户名和密码来获取OAuth的
 *         Access Token的方式。 采用xAuth认证的桌面应用和移动应用可以跳过oauth/request_token（获取Request
 *         Token）以及oauth/authorize（授权Request
 *         Token）两步，只要提供了username和password以后，即可直接通过oauth/access_token接口得到Access
 *         Token。
 */
public class Xauth extends Thread{

	private String xauthUsername;
	private String xauthPassword;

	private static String token;
	private static String tokenSecret;

	private static final String OAuthVersion = "1.0";
	private static String consumerKey = "53f48ec66af68087d85ff2610e451258";
	private static String consumerSecret = "7d164b452bcfebf627dbc7e393eabe0effc7ead6";
	private static final String XAuthMode = "client_auth";
	private static final String HMACSHA1SignatureType = "HMAC-SHA1";

	public static final String BASE_URL = "http://210.44.14.10/OAuth/";
	public static final String AUTH_URL = "AccessToken.ashx";
	public static final String REST_URL = "Rest.ashx";

	private static final int _fetchTimeout = 5000;

	public static HttpClient httpClient = new DefaultHttpClient();
	
	
	private Handler mHanlerGlobal;
	private String urlGlobal;
	private boolean flag =true;
	
	
	public Xauth(String username, String password) {
		this.xauthUsername = username;
		this.xauthPassword = password;

		doAccessToken();
	}

	//在线程运行之前必需 运行 这个方法.
	public void setHandlerUrl(Handler mHandler,String url){
		this.mHanlerGlobal = mHandler;
		this.urlGlobal = url;
	}
	@Override
	public void run(){
		Message message;
		if(flag){
			doAccessToken();  //这个方法只在登陆时运行一次.
			flag =false;
		}
		String result = doMethod(this.urlGlobal);
		if(result.length() >0)
		{
		    message = this.mHanlerGlobal.obtainMessage(1,result);
		}else{
			message = this.mHanlerGlobal.obtainMessage(0,result);
		}
		message.sendToTarget();
		
	}
	
	
	// 获得第一步，得到授权的token。
	private void doAccessToken() {

		// 生成一个TreeMap列表parameters，来包含以下要提交的url参数。
		TreeMap<String, String> parameters = new TreeMap<String, String>();

		// 1.是consumerkey，这是申请app的时候，给开发者提供的识别key，通过这个key，可以允许app端申请访问服务器的key
		parameters.put(Constants.ConsumerKeyParameter, consumerKey);
		Log.d("test", "1.consumerKey:" + consumerKey);

		// 2.使用UUID来生成一个随机序列，用来实现服务器端和客户端的一致性验证。
		/*
		 * 随机GUID说明
		 * 
		 * 
		 * 随机生成一个GUID字符串，作为随机字符串来防止重复攻击，随机字符串在服务器保留10分钟（暂定）。GUID作为参数传输时，
		 * 需要以十六进制并使用横线分隔符分隔。 例如，FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF
		 */

		UUID uuid = UUID.randomUUID();

		// 生成的序列格式转换为ws指定的格式
		String guid = uuid.toString();

		// 添加到nonce属性中
		parameters.put(Constants.NonceParameter, guid);
		Log.d("test", "2.guid:" + guid);

		// 3.添加验证方法：Hmacsha1
		parameters.put(Constants.SignatureMethodParameter,
				HMACSHA1SignatureType);
		Log.d("test", "3.SignatureMethodParameter:" + HMACSHA1SignatureType);

		// 4.添加时间戳
		/*
		 * 时间戳说明
		 * 
		 * 
		 * 时间戳使用Unix时间，即当前时间转换为UTC时间后，减去1970年1月1日0时0分0秒后的所有描述。 例如，当前时间为2014/1/29
		 * 16:56:58（UTC+8），其UTC时间为2014/1/29
		 * 8:56:58，减去Unix起始时间为16099天8时56分58秒，共计1390985818秒，故时间戳为1390985818。
		 * 当客户端时间小于服务器时间60秒（暂定）或更多时时间戳无效，认证失败。
		 */
		parameters.put(Constants.TimestampParameter,
				Long.toString(System.currentTimeMillis() / 1000));
		Log.d("test",
				"4.TimestampParameter:"
						+ Long.toString(System.currentTimeMillis() / 1000));

		// 5.添加XAuth协议版本。填写“1.0”。
		parameters.put(Constants.VersionParameter, OAuthVersion);
		Log.d("test", "5.VersionParameter:" + OAuthVersion);

		// 6.标识字段，这里必须是"client_auth"。
		parameters.put(Constants.AuthModeParameter, XAuthMode);
		Log.d("test", "6.AuthModeParameter:" + XAuthMode);

		// 7.添加密码和8.用户名
		parameters.put(Constants.AuthPasswordParameter, this.xauthPassword);
		parameters.put(Constants.AuthUsernameParameter, this.xauthUsername);
		Log.d("test", "7.AuthPasswordParameter:" + xauthPassword);
		Log.d("test", "8.AuthUsernameParameter:" + xauthUsername);

		// 接下来，就是传递参数了。注意这里，采用了引用参数。
		// 所以，参数一定要用对象，这里使用了StringBuffer而不是String来生成result。
		StringBuffer resultBuffer = new StringBuffer("");

		// 调用http传递参数并返回结果status和result。

		Boolean status = TryRequestRemote(BASE_URL + AUTH_URL, "", parameters,
				resultBuffer);

		// 转换回String。
		/*
		 * 比如: oauth_token=88cd54bbf33a4ec79fe297cdd3ac5315 &
		 * oauth_token_secret=a825b4ba2689a77b5cf5b3ee85b371141d3f77c2 &
		 * user_id=111116 & user_type=2 & expires_in=604800
		 */

		String result = resultBuffer.toString();
		Log.d("test", "17.result:" + result);

		if (status) {
			// HashTable pairs = new HashTable();
			Map<String, String> pairs = new HashMap<String, String>();
			// 把result分解为arr。
			String[] arr = result.split("&");
			// 读数组中的每一个元素,比如：oauth_token=test_token_key，把数组转换为Map列表。
			for (int i = 0; i < arr.length; i++) {
				// 继续分解为parts
				String[] parts = arr[i].split("=");

				if (parts.length == 2) {
					pairs.put(parts[0], parts[1]);
				}
			}

			// 得到Map列表中的tokenKey和tokenSecret
			String tokenKey1 = pairs.get(Constants.TokenParameter);
			String tokenSecret1 = pairs.get(Constants.TokenSecretParameter);

			if ((tokenKey1 != null) && (tokenSecret1 != null)) {
				// 返回tokenkey和tokensecret组成的数组
				token = tokenKey1;
				tokenSecret = tokenSecret1;
				Log.d("test", "18.tokenKey:" + token);
				Log.d("test", "19.tokenSecret:" + tokenSecret);
			}
		}
	}

	// 生成一个公开的方法，实现获得数据。是JSON格式的。
	public static String doMethod(String url) {
		// 生成一个TreeMap列表parameters，来包含以下要提交的url参数。
		TreeMap<String, String> parameters = new TreeMap<String, String>();

		// 1.是consumerkey，这是申请app的时候，给开发者提供的识别key，通过这个key，可以允许app端申请访问服务器的key
		parameters.put(Constants.ConsumerKeyParameter, consumerKey);
		Log.d("test1", "1.consumerKey:" + consumerKey);

		// 2.使用UUID来生成一个随机序列，用来实现服务器端和客户端的一致性验证。
		/*
		 * 随机GUID说明
		 * 
		 * 
		 * 随机生成一个GUID字符串，作为随机字符串来防止重复攻击，随机字符串在服务器保留10分钟（暂定）。GUID作为参数传输时，
		 * 需要以十六进制并使用横线分隔符分隔。 例如，FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF
		 */

		UUID uuid = UUID.randomUUID();

		// 生成的序列格式转换为ws指定的格式
		String guid = uuid.toString();

		// 添加到nonce属性中
		parameters.put(Constants.NonceParameter, guid);
		Log.d("test1", "2.guid:" + guid);

		// 3.添加验证方法：Hmacsha1
		parameters.put(Constants.SignatureMethodParameter,
				HMACSHA1SignatureType);
		Log.d("test1", "3.SignatureMethodParameter:" + HMACSHA1SignatureType);

		// 4.添加时间戳
		/*
		 * 时间戳说明
		 * 
		 * 
		 * 时间戳使用Unix时间，即当前时间转换为UTC时间后，减去1970年1月1日0时0分0秒后的所有描述。 例如，当前时间为2014/1/29
		 * 16:56:58（UTC+8），其UTC时间为2014/1/29
		 * 8:56:58，减去Unix起始时间为16099天8时56分58秒，共计1390985818秒，故时间戳为1390985818。
		 * 当客户端时间小于服务器时间60秒（暂定）或更多时时间戳无效，认证失败。
		 */
		parameters.put(Constants.TimestampParameter,
				Long.toString(System.currentTimeMillis() / 1000));
		Log.d("test1",
				"4.TimestampParameter:"
						+ Long.toString(System.currentTimeMillis() / 1000));

		// 5.TokenKey字段。
		parameters.put(Constants.TokenParameter, token);
		Log.d("test", "5.TokenKey字段:" + token);

		// 6.添加XAuth协议版本。填写“1.0”。
		parameters.put(Constants.VersionParameter, OAuthVersion);
		Log.d("test1", "6.VersionParameter:" + OAuthVersion);

		// 接下来，就是传递参数了。注意这里，采用了引用参数。
		// 所以，参数一定要用对象，这里使用了StringBuffer而不是String来生成result。
		StringBuffer resultBuffer = new StringBuffer("");

		// 调用http传递参数并返回结果status和result。

		Boolean status = TryRequestRemote(url, tokenSecret, parameters,
				resultBuffer);
		Log.d("test1",
				"7.调用http传递参数并返回结果status和result:" + resultBuffer.toString());
		return resultBuffer.toString();
	}

	// 提交带有参数的url，把响应消息返回给result。
	private static Boolean TryRequestRemote(String url, String tokenSecret,
			final TreeMap<String, String> parameters, StringBuffer result) {

		try {

			// 9.指定Http访问方法
			String requestMethod = "GET";
			Log.d("test", "9.Http访问方法:" + requestMethod);

			// 10.指定URL网址，绑定为URL类型。
			URL requestUri = new URL(url);
			Log.d("test", "10.requestUri：" + requestUri);

			// 11.生成签名基本字段
			String signatureBase = SignatureBaseBuilder
					.CreateSignatureBaseString(requestMethod, requestUri,
							parameters);
			Log.d("test", "11.signatureBase：" + signatureBase);

			// 12.生成签名
			String signature = HMACSHA1Signer.CreateSignature(consumerSecret,
					tokenSecret, signatureBase);
			Log.d("test", "12.signature：" + signature);

			// 13.生成HTTP头

			String header = XAuthRequestHeaderBuilder
					.CreateSignatureBaseString(requestMethod, requestUri,
							consumerSecret, tokenSecret, parameters);

			Log.d("test", "13.header：" + header);

			// 14.得到HTTP头的String格式
			String urlPreview = String
					.format("[Request Url]\r\n%s\r\n\r\n[Signature Base]\r\n%s\r\n\r\n[Signature]\r\n%s\r\n\r\n[Header]\r\n%s",
							requestUri.toString(), signatureBase, signature,
							header);
			Log.d("test", "14.urlPreview：" + urlPreview);

			// 16.发送给服务器并获得返回结果
			String temp = HttpFetcher.GetRemoteContent(url, Encoding.UTF_8,
					_fetchTimeout, header);
			result.append(temp);
			Log.d("test", "16.result：" + result);

			return true;

		} catch (Exception ex) {
			// this.txtResult.Text = String.Format("Error:\r\n{0}", ex.Message);
			return false;
		}
	}
}
