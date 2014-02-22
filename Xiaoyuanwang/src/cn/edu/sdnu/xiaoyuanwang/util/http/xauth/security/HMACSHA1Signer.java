package cn.edu.sdnu.xiaoyuanwang.util.http.xauth.security;

import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cn.edu.sdnu.xiaoyuanwang.util.http.xauth.web.RFC3986Encoder;

public class HMACSHA1Signer {
	/**
	 * 创建签名字符串
	 * 
	 * @param consumerSecret
	 *            应用密钥
	 * @param tokenSecret
	 *            令牌密钥
	 * @param signatureBase
	 *            SignatureBase
	 * @return 签名字符串
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static String CreateSignature(String consumerSecret,
			String tokenSecret, String signatureBase)
			throws NoSuchAlgorithmException, InvalidKeyException {

		/*
		 * HMAC-SHA1算法的密钥由oauth_consumer_secret与oauth_token_secret拼接起来得到，
		 * 两个密钥之间使用“&”连接。 其中oauth_consumer_secret为使用者密钥， oauth_token_secret
		 * 为令牌密钥 。其中在用户认证过程中，由于oauth_token_secret不存在，所以请留空
		 * （  但需要保留“&”符号）。
		 */
		byte[] key = (RFC3986Encoder.Encode(consumerSecret) + "&" + RFC3986Encoder
				.Encode(tokenSecret)).getBytes();

		byte[] data = signatureBase.getBytes();

		Base64 encodeBase64 = new Base64();

		String hash = "";

		SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");

		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(data);

		hash = encodeBase64.encode(rawHmac);

		return hash;
	}

	/**
	 * 创建签名字符串
	 * 
	 * @param currentSignature
	 *            应用密钥
	 * @param consumerSecret
	 *            令牌密钥
	 * @param tokenSecret
	 *            SignatureBase
	 * @param httpMethod
	 *            ����ʽ
	 * @param requestUrl
	 *            ����URL��ַ
	 * @param parameters
	 *            ��������б�
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static Boolean CheckSignature(String currentSignature,
			String consumerSecret, String tokenSecret, String httpMethod,
			URL requestUrl, TreeMap<String, String> parameters)
			throws InvalidKeyException, NoSuchAlgorithmException {

		String sigBase = SignatureBaseBuilder.CreateSignatureBaseString(
				httpMethod, requestUrl, parameters);

		String expectedSignature = HMACSHA1Signer.CreateSignature(
				consumerSecret, tokenSecret, sigBase);

		String actualSignature = RFC3986Encoder.Decode(currentSignature);

		return expectedSignature.equals(actualSignature);
	}
}
