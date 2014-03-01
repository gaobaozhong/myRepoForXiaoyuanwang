package cn.edu.sdnu.i.util.xauth;

import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;


public class XAuthRequestHeaderBuilder {

	/**
	 * ����ǩ����ַ�
	 * @param httpMethod  ����ʽ
	 * @param requestUrl	����URL��ַ
	 * @param consumerSecret	�ͻ�����Կ
	 * @param tokenSecret	������Կ
	 * @param parameters	��������б�
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
    public static String CreateSignatureBaseString(String httpMethod, URL requestUrl, String consumerSecret, String tokenSecret, TreeMap<String, String> parameters) 
    		throws InvalidKeyException, NoSuchAlgorithmException{
    	
        String signatureBase = SignatureBaseBuilder.CreateSignatureBaseString(httpMethod, requestUrl, parameters);
        String signature = HMACSHA1Signer.CreateSignature(consumerSecret, tokenSecret, signatureBase);

        TreeMap<String, String> allParams = new TreeMap<String, String>();
        
        for(String key : parameters.keySet()){
        	allParams.put(key, parameters.get(key));
        } 
        allParams.put(Constants.SignatureParameter, signature);
        StringBuilder sb = new StringBuilder();
        sb.append("OAuth ");
        int index = 0;
        
        for(String key : allParams.keySet()){
        	if(index++>0)
        		sb.append(',');
        	sb.append(RFC3986Encoder.Encode(key)).append("=\"").append(RFC3986Encoder.Encode(allParams.get(key))).append("\"");
        }  
        return sb.toString();
    }

}
