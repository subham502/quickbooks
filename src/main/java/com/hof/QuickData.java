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
import org.json.JSONObject;

public class QuickData {

	private static String key = "qyprdE6Ok345IZCrQoFaVDtH7WLB3Y";
    private static String secret = "GSMvLImmjbg3tJuBj3d0gWQqpZdCwIsUDBFb6SYA";
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String ENC = "UTF-8";
    //private static final String Oauth_nounce = ""+ (int) (Math.random() * 100000000);
    //private static final String Oauth_time_stamp = ""+(System.currentTimeMillis() / 1000);
    
    private static final String Oauth_nounce = "avZDde";
    private static final String Oauth_time_stamp = "1483944238";
    
    private static Base64 base64 = new Base64();
    private static final String Oauth_token = "qyprdmVTa4zcylDvVYEqJhWmagYNzXFs2Wpw2nLdU8I0BSI9";
    private static final String Oauth_token_secret = "Qz6uNDePfPPbvGkmeHN4HjJCzcM4Gv8MpIA2AbFg";
    static String queryString  = "";
    private static String getSignature(String url,String params)throws UnsupportedEncodingException, NoSuchAlgorithmException,InvalidKeyException 
    {
        StringBuilder base = new StringBuilder();
        base.append("GET&");
        base.append(url);
        base.append("&");
        base.append(params);
        base.append(queryString);
        
        byte[] keyBytes = (secret + "&" + Oauth_token_secret ).getBytes(ENC);
        SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(key);
        //System.out.println(base.toString());
        return new String(base64.encode(mac.doFinal(base.toString().getBytes(ENC))), ENC).trim();
    }
    public static String getdata(String query,String companyId,String queryS,String queryURL) throws ClientProtocolException,IOException, URISyntaxException, InvalidKeyException,NoSuchAlgorithmException 
    {	
    	//String query = "invoice";
    	String dummy = "";
    	//String maxRecord = "500";
    	//String companyId = "520173815";
    	//queryString = "%26query%3Dselect%252Afrom%2520"+query+"%2520MAXRESULTS%2520"+maxRecord;
    	queryString = queryS;
    	
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("minorversion", "8"));
        qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
        qparams.add(new BasicNameValuePair("oauth_nonce", Oauth_nounce));             
        qparams.add(new BasicNameValuePair("oauth_signature_method","HMAC-SHA1"));
        qparams.add(new BasicNameValuePair("oauth_timestamp", Oauth_time_stamp));
        qparams.add(new BasicNameValuePair("oauth_token", Oauth_token));
        qparams.add(new BasicNameValuePair("oauth_version", "1.0"));
        
        //qparams.add(new BasicNameValuePair("query", "select*from customer"));
        
        // generate the oauth_signature
        String signature = getSignature(URLEncoder.encode("https://sandbox-quickbooks.api.intuit.com/v3/company/"+companyId+"/query", ENC),URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC));
        //String signature ="XCqdcNKoOkpfhRtazL8SaqGcKfk%3D";
        
        qparams.add(new BasicNameValuePair("oauth_signature", signature));
        System.out.println("signature=="+signature);
        
        String header = "OAuth ";
        for(NameValuePair o : qparams){
        	if(o.getName().equals("minorversion"));
        	else{
        		header += o.getName();
            	header+="=";
            	header += "\"";
        	}
        	if(o.getName().equals("oauth_signature"))
        		header += URLEncoder.encode(o.getValue());
        	else if(o.getName().equals("minorversion"))
        		header += "";
        	else{
        		header += o.getValue();
            	header += "\",";
        	}
        		
        }
        
        //header = header.substring(0, header.length()-1);
        System.out.println(header);
        
        //header = "";
                
        Client client = ClientBuilder.newClient();
	    //Response response = client.target("https://quickbooks.api.intuit.com/v3/company/"+companyId+"/query?query=select%2Afrom%20"+query+"%20MAXRESULTS%20"+maxRecord)
        Response response = client.target("https://sandbox-quickbooks.api.intuit.com/v3/company/"+companyId+"/query?minorversion=8&query="+queryURL)//select%20count%28%2A%29from%20"+query+"%20MAXRESULTS%20"+maxRecord)
	    							.request()
	    							.header("oauth_consumer_key", key)
	    							.header("oauth_nonce", Oauth_nounce)
	    							.header("oauth_signature_method", "HMAC-SHA1")
	    							.header("oauth_timestamp", Oauth_time_stamp)
	    							.header("oauth_token", Oauth_token)
	    							.header("oauth_version", "1.0")
	    							.header("oauth_signature", signature)
	    							.header("Authorization",header)
	    							.header("Accept","application/json")
	    							.get();
	   /* System.out.println(response.getStatus());
	    System.out.println(response.getStatusInfo());
	    System.out.println(response.readEntity(String.class));
	   */
	    String str = response.readEntity(String.class);
	    //System.out.println(str);
	   /* FileWriter writer= new FileWriter("/home/aptus/invoice.txt");
	    writer.write(str);
	    writer.close();*/
	    //JSONObject obj = new JSONObject(str);

	    //System.out.println(new JSONObject(str).toString());
	    
	    return str;
	   /* JSONObject customer = obj.getJSONObject("QueryResponse");
	    JSONArray arr = customer.getJSONArray("Customer");*/
	  /*try{
		  int j = 0;
		for (int i = 0; i < arr.length(); i++)
		{
			try{
				//System.out.println((String) arr.getJSONObject(i).get("Taxable").toString());
				System.out.println((String) arr.getJSONObject(i).getJSONObject("BillAddr").get("Long").toString());
			}catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		}
	    Iterator itr =customer.keys();
	    while(itr.hasNext()){
	    	System.out.println(itr.next());
	    }
    }catch(Exception e)
    {
    	e.printStackTrace();
    }*/
    }
}
