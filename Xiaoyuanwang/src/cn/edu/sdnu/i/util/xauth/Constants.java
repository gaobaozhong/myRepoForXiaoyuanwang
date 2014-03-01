/**
 * 
 */
package cn.edu.sdnu.i.util.xauth;

/**
 * @author Administrator
 *
 */
public class Constants {
	
        
        /// <summary>
        /// 获取应用Key参数名
        /// </summary>
        public final static String ConsumerKeyParameter = "oauth_consumer_key";

        /// <summary>
        /// 获取单次值参数名
        /// </summary>
        public final static String NonceParameter = "oauth_nonce";

        /// <summary>
        /// 获取签名方法参数名
        /// </summary>
        public final static String SignatureMethodParameter = "oauth_signature_method";

        /// <summary>
        /// 获取签名内容参数名
        /// </summary>
        public final static String SignatureParameter = "oauth_signature";

        /// <summary>
        /// 获取时间戳参数名
        /// </summary>
        public final static String TimestampParameter = "oauth_timestamp";

        /// <summary>
        /// 获取版本参数名
        /// </summary>
        public final static String VersionParameter = "oauth_version";

        /// <summary>
        /// 获取令牌内容参数名
        /// </summary>
        public final static String TokenParameter = "oauth_token";

        /// <summary>
        /// 获取令牌密钥参数名
        /// </summary>
        public final static String TokenSecretParameter = "oauth_token_secret";

        /// <summary>
        /// 获取认证方式参数名
        /// </summary>
        public final static String AuthModeParameter = "x_auth_mode";

        /// <summary>
        /// 获取认证用户密码参数名
        /// </summary>
        public final static String AuthPasswordParameter = "x_auth_password";

        /// <summary>
        /// 获取认证用户名参数名
        /// </summary>
        public final static String AuthUsernameParameter = "x_auth_username";

        /// <summary>
        /// 获取支持的认证方式
        /// </summary>
        public final static String SupportAuthMode = "client_auth";
}
