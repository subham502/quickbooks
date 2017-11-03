package com.hof;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

public class QuickAccessToken {

	private static String key = "qyprdE6Ok345IZCrQoFaVDtH7WLB3Y";
    private static String secret = "GSMvLImmjbg3tJuBj3d0gWQqpZdCwIsUDBFb6SYA";
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String ENC = "UTF-8";
    private static final String Oauth_nounce =""+ (int) (Math.random() * 100000000);
    private static final String Oauth_time_stamp = ""+(System.currentTimeMillis() / 1000);
    private static Base64 base64 = new Base64();
    /*private static final String Oauth_token = "qyprdeEVkiW2DIPJdXp7tLZn02lHUHgBsKh1NKVXuTBiECR0";
    private static final String Oauth_verifier = "a69tcqm";
    private static final String Oauth_token_secret = "3N38H1Vkc45J89jWuurpNdyKACOjPMYRQZwfDXaJ";*/
    private static String Oauth_token = null;
    private static String Oauth_verifier = null;
    private static String Oauth_token_secret = null;
    
    private static String getSignature(String url, String params)throws UnsupportedEncodingException, NoSuchAlgorithmException,InvalidKeyException 
    {
        StringBuilder base = new StringBuilder();
        base.append("GET&");
        base.append(url);
        base.append("&");
        base.append(params);
        byte[] keyBytes = (secret + "&" + Oauth_token_secret ).getBytes(ENC);

        SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(key);
        return new String(base64.encode(mac.doFinal(base.toString().getBytes(ENC))), ENC).trim();
    }
    public static void getToken() throws ClientProtocolException,IOException, URISyntaxException, InvalidKeyException,NoSuchAlgorithmException 
    {
    	Oauth_token = QuickRequestToken.getOauthtoken();
    	Oauth_token_secret = QuickRequestToken.getOauthtokensecret();
    	Scanner sc = new Scanner(System.in);
    	System.out.println("enter the verifier::");
    	Oauth_verifier = sc.nextLine();
    	
    	System.out.println(Oauth_token+"      "+Oauth_token_secret+"      "+Oauth_verifier+"      ");
    	
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        // These params should ordered in key
        qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
        qparams.add(new BasicNameValuePair("oauth_nonce", Oauth_nounce));
        qparams.add(new BasicNameValuePair("oauth_signature_method","HMAC-SHA1"));
        qparams.add(new BasicNameValuePair("oauth_timestamp", Oauth_time_stamp));
        qparams.add(new BasicNameValuePair("oauth_token", Oauth_token));
        qparams.add(new BasicNameValuePair("oauth_verifier", Oauth_verifier));
        qparams.add(new BasicNameValuePair("oauth_version", "1.0"));
        
     // generate the oauth_signature
        String signature = getSignature(URLEncoder.encode("https://oauth.intuit.com/oauth/v1/get_access_token", ENC),URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC));
        // add it to params list
        qparams.add(new BasicNameValuePair("oauth_signature", signature));
        
        //System.out.println(signature+"    "+Oauth_nounce+"    "+"       "+Oauth_time_stamp);
        
        Client client = ClientBuilder.newClient();
	    Response response = client.target("https://oauth.intuit.com/oauth/v1/get_access_token")	
	    							.queryParam("oauth_consumer_key", key)
	    							.queryParam("oauth_nonce", Oauth_nounce)
	    							.queryParam("oauth_signature_method", "HMAC-SHA1")
	    							.queryParam("oauth_timestamp", Oauth_time_stamp)
	    							.queryParam("oauth_token", Oauth_token)
	    							.queryParam("oauth_verifier", Oauth_verifier)
	    							.queryParam("oauth_version", "1.0")
	    							.queryParam("oauth_signature", signature)
	    							.request()
	    							.get();
	    System.out.println(response.getStatus());
	    System.out.println(response.getStatusInfo());
	    System.out.println(response.readEntity(String.class));
    }
}
