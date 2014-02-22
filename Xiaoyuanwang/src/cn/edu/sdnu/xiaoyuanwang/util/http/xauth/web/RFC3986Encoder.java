package cn.edu.sdnu.xiaoyuanwang.util.http.xauth.web;

public class RFC3986Encoder {
	//private static final Regex RFC3986EscapeSequence = new Regex("%([0-9A-Fa-f]{2})", RegexOptions.Compiled);
    /**
     * 
     * @param input
     * @return String
     */
    public static String Encode(String input){
        
    	if (input==null||input.length()==0){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++){
            
        	char c = input.charAt(i);
            if (IsReverseChar(c)){               
            	sb.append('%');
                sb.append(bytesToHexString((byte)c));
            }
            else{    
            	sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 因为这个方法没有被用到,所以目前不做处理.
     * 对指定内容进行RFC3986解码
     * @param input  要解码的字符串
     * @return 解码后的字符串
     */
    /**/
    public static String Decode(String input){
        if (input.length()==0){
            return "";
        }
        /*
        return RFC3986Encoder.RFC3986EscapeSequence.Replace(input,
            (Match match) =>
            {
                if (match.Success)
                {
                    Group hexgrp = match.Groups[1];

                    return String.Format(CultureInfo.InvariantCulture, "{0}",
                        (char)int.Parse(hexgrp.Value, NumberStyles.HexNumber, CultureInfo.InvariantCulture));
                }

                throw new FormatException("�޷�����ָ���ַ�");
            });*/
        return "";
         	
    }
    
    private static Boolean IsReverseChar(char c){
        return !((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
                || c == '-' || c == '_' || c == '.' || c == '~');
    }
    /* Convert byte[] to hex string.
     * 这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
    * @param src byte[] data  
    * @return hex string  
    */     
   public static  String bytesToHexString(byte src){       
	   int v = src & 0xFF;  
       String hv = Integer.toHexString(v);
       return hv.toUpperCase(); 
   }  
}
