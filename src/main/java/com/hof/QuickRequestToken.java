package com.hof;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class QuickRequestToken {

    private static String key = "qyprdE6Ok345IZCrQoFaVDtH7WLB3Y";
    //private static String key = "qyprdP5XxQJJ1MNdQ5lF8hz95jchXu";
    private static String secret = "GSMvLImmjbg3tJuBj3d0gWQqpZdCwIsUDBFb6SYA";
    //private static String secret = "p22e76kjUCXil7TLDGFnaJDX5YgrAzwJPzs8vLqa";
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String ENC = "UTF-8";
    private static final String Oauth_nounce =""+ (int) (Math.random() * 100000000);
    private static final String Oauth_time_stamp = ""+(System.currentTimeMillis() / 1000);
    private static String oauthToken = null;
    private static String oauthTokenSecret = null;

    public static String getOauthtoken() {
		return oauthToken;
	}
	public static String getOauthtokensecret() {
		return oauthTokenSecret;
	}
    private static Base64 base64 = new Base64();

    private static String getSignature(String url, String params)throws UnsupportedEncodingException, NoSuchAlgorithmException,InvalidKeyException 
    {
        StringBuilder base = new StringBuilder();
        base.append("GET&");
        base.append(url);
        base.append("&");
        base.append(params);
        byte[] keyBytes = (secret + "&").getBytes(ENC);

        SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(key);
        return new String(base64.encode(mac.doFinal(base.toString().getBytes(ENC))), ENC).trim();
    }
    public static void main(String[] args) throws ClientProtocolException,IOException, URISyntaxException, InvalidKeyException,NoSuchAlgorithmException 
    {
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        // These params should ordered in key
        //qparams.add(new BasicNameValuePair("oauth_callback", "http://localhost:8080/OauthSample/AccessToken"));
        qparams.add(new BasicNameValuePair("oauth_callback", "https://tpconnect.yellowfin.bi/getToken.jsp"));
        qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
        qparams.add(new BasicNameValuePair("oauth_nonce", Oauth_nounce));
        qparams.add(new BasicNameValuePair("oauth_signature_method","HMAC-SHA1"));
        qparams.add(new BasicNameValuePair("oauth_timestamp", Oauth_time_stamp));
        qparams.add(new BasicNameValuePair("oauth_version", "1.0"));

        // generate the oauth_signature
        String signature = getSignature(URLEncoder.encode("https://oauth.intuit.com/oauth/v1/get_request_token", ENC),URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC));
        // add it to params list
        qparams.add(new BasicNameValuePair("oauth_signature", signature));
        
       // System.out.println(Oauth_nounce+"          "+Oauth_time_stamp+"          "+signature);
        
        Client client = ClientBuilder.newClient();
	    Response response = client.target("https://oauth.intuit.com/oauth/v1/get_request_token")	
	    							//.queryParam("oauth_callback", "http://localhost:8080/OauthSample/AccessToken")
	    							.queryParam("oauth_callback", "https://tpconnect.yellowfin.bi/getToken.jsp")
	    							.queryParam("oauth_consumer_key", key)
	    							.queryParam("oauth_nonce", Oauth_nounce)
	    							.queryParam("oauth_signature_method", "HMAC-SHA1")
	    							.queryParam("oauth_timestamp", Oauth_time_stamp)
	    							.queryParam("oauth_version", "1.0")
	    							.queryParam("oauth_signature", signature)
	    							.request()
	    							.get();
	    //System.out.println(response.readEntity(String.class));
	    String obj = response.readEntity(String.class);
	    System.out.println(obj);
	    String[] arr = obj.split("&");
	    System.out.println("https://appcenter.intuit.com/Connect/Begin?"+arr[1]);
	    String[] token = arr[1].split("=");
	    oauthToken = token[1];
	    String[] secret = arr[0].split("=");
	    oauthTokenSecret = secret[1];
	    
	    QuickAccessToken.getToken();
    }
}
