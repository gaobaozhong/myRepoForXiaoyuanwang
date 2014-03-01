package cn.edu.sdnu.i.util.xauth;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;


/*
 * 所有的OAuth请求都使用同样的算法来生成（Signature Base String）签名
 * 字符基串和签名。 
Base String是把http方法名、请求URL以及请求参数用“&”字符连起来后做

RFC3986 Encode编码（类似于各个平台自带的UrlEncode，但是对空格替换

为“%20”，而不是“+”）。具体来讲，Base String由http方法名，之后是&，

接着是UrlEncode之后的url和访问路径及&。接下来，把所有的请求参数（

包括POST方法体中的参数），经过排序（按参数名进行文本排序，如果参数

名有重复则再按参数值进行重复项目排序），使用%3D替代=号，并且使

用%26作为每个参数之间的分隔符，拼接成一个字符串。 
这个算法可以简单表示为： 
httpMethod + "&" + 
  url_encode( base_uri ) + "&" + 
  sorted_query_params.each { | k, v | 
      url_encode ( k ) + "%3D" + url_encode ( v ) 
  }.join("%26") 
【例】请求方法为：GET 
请求地址为：http://localhost:37476/OAuth/AccessToken.ashx 
请求参数为： 
oauth_consumer_key=test_consumer_key 
oauth_nonce=1234567890 
oauth_signature_method=HMAC-SHA1 
oauth_timestamp=9999999999 
oauth_version=1.0 
x_auth_mode=client_auth 
x_auth_password=PASSWORD 
x_auth_username=2013001001 
 
则Base String为： 
GET&http%3A%2F%2Flocalhost%3A37476%2FOAuth

%2FAccessToken.ashx&oauth_consumer_key%3Dtest_consumer_key

%26oauth_nonce%3D1234567890%26oauth_signature_method%3DHMAC-

SHA1%26oauth_timestamp%3D9999999999%26oauth_version

%3D1.0%26x_auth_mode%3Dclient_auth%26x_auth_password%3DPASSWORD

%26x_auth_username%3D2013001001 


接下来使用HMAC-SHA1算法对Base String进行哈希，哈希后的结果即为

Signature。其中HMAC-SHA1算法的密钥由oauth_consumer_secret与

oauth_token_secret拼接起来得到，两个密钥之间使用“&”连接。其中

oauth_consumer_secret为使用者密钥， oauth_token_secret 为令牌密钥

。其中在用户认证过程中，由于oauth_token_secret不存在，所以请留空（

但需要保留“&”符号）。 
【例】oauth_consumer_secret为test_consumer_secret，则HMAC-SHA1在获

取Token前的密钥为： 
test_consumer_secret& 
接下来使用该密钥对Base String使用HMAC-SHA1算法进行哈希，然后对结果

进行Base 64转换，结果即为Signature 
MzH0d6CIwakPrh8FM8DmaiUwvvY= */
public class SignatureBaseBuilder {

	// / <summary>
	// / 创建签名基础字符串
	// / </summary>
	// / <param name="httpMethod">请求方式</param>
	// / <param name="requestUrl">请求URL地址</param>
	// / <param name="parameters">请求参数列表</param>
	// / <returns>签名基础字符串</returns>

	public static String CreateSignatureBaseString(String httpMethod,
			URL requestUrl, TreeMap<String, String> parameters) {

		StringBuilder sb = new StringBuilder();

		sb.append(RFC3986Encoder.Encode(httpMethod)).append("&");

		sb.append(RFC3986Encoder.Encode(requestUrl.getProtocol())).append(
				RFC3986Encoder.Encode("://"));

		sb.append(
				RFC3986Encoder.Encode(requestUrl.getAuthority().toLowerCase()))
				.append(RFC3986Encoder.Encode(requestUrl.getPath()))
				.append("&");

		sb.append(RFC3986Encoder.Encode(SignatureBaseBuilder
				.GetJoinedParameter(parameters)));

		return sb.toString();
	}

	private static String GetJoinedParameter(TreeMap<String, String> parameters) {

		StringBuilder sb = new StringBuilder();

		Boolean nonfirst = false;

		for (String key : parameters.keySet()) {

			if (nonfirst) {

				sb.append("&");

			} else {

				nonfirst = true;

			}

			sb.append(key).append('=').append(parameters.get(key));

		}
		return sb.toString();
	}
}
