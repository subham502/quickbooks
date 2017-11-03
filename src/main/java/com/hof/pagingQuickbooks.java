package com.hof;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;

public class pagingQuickbooks {
	public static void main(String[] args) throws InvalidKeyException, ClientProtocolException, NoSuchAlgorithmException, IOException, URISyntaxException
	{
		String query = "invoice";
		String maxRecord = "20";
		String companyId = "193514473438819";
		int count;
		String position = "1";
		String maximum = "10";
		String queryURL = "";
		String minorVer = "8";
		
		String queryString = "%26query%3Dselect%2520count%2528%252A%2529from%2520"+query;
		
		queryURL = "select%20count%28%2A%29from%20"+query;
		
		String response = QuickData.getdata(query, companyId, queryString,queryURL);
		//System.out.println(response);
		
		FileWriter file = new FileWriter("/home/aptus/invoice.txt");
		//file.write(response);
		//file.close();
		
		JSONObject obj = new JSONObject(new String(response));
		int totalCount = obj.getJSONObject("QueryResponse").getInt("totalCount");
		System.out.println(totalCount);
		
		/*if(Integer.parseInt(maxRecord)>totalCount)
			maxRecord = ""+totalCount;
		 */
		if(Integer.parseInt(maxRecord)>10)
		{
			count = Integer.parseInt(maxRecord)/10;
			count++;
		}
		else
			count = 1;		
				
		queryString = "%26query%3Dselect%252Afrom%2520"+query+"%2520STARTPOSITION%2520"+position+"%2520MAXRESULTS%2520"+maximum;
		queryURL = "select%2Afrom%20"+query+"%20STARTPOSITION%20"+position+"%20MAXRESULTS%20"+maximum;

		response = QuickData.getdata(query, companyId, queryString,queryURL);
		System.out.println("response=="+response);
		JSONObject objFinal = new JSONObject(new String(response));
		JSONArray arrFinal =objFinal.getJSONObject("QueryResponse").getJSONArray("Invoice");		
		
		for(int i=1;i<count;i++)
		{
			position = ""+(Integer.parseInt(position)+10);
			//System.out.println("query=="+query+"position=="+position+"maximum=="+maximum);
			
			queryString = "%26query%3Dselect%252Afrom%2520"+query+"%2520STARTPOSITION%2520"+position+"%2520MAXRESULTS%2520"+maximum;
			queryURL = "select%2Afrom%20"+query+"%20STARTPOSITION%20"+position+"%20MAXRESULTS%20"+maximum;
			
			System.out.println();
	
			response = QuickData.getdata(query, companyId, queryString,queryURL);			
			JSONObject objAppend = new JSONObject(new String(response));
			JSONArray arrAppend = objAppend.getJSONObject("QueryResponse").getJSONArray("Invoice");
			int length = arrAppend.length();
			
			for(int j=0;j<length;j++)
				arrFinal.put(arrAppend.get(j));
				
			//position = ""+(Integer.parseInt(position)+100);
			//System.out.println(response);
			//System.out.println("=========================================================="+i);
		}
		file.append(objFinal.toString());
		System.out.println(objFinal.toString());
	}	
}
