package com.hof;

import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

public class formatCustomer {
		
		public final static Logger logger = Logger.getLogger(formatCustomer.class.getName());
		
		String[] taxable = null;
		String[] billAddressId = null;
		String[] billAddressLine1 = null;
		String[] billAddressCity = null;
		String[] billAddressCountrysubDivisionCode = null;
		String[] billAddressPostalCode = null;
		String[] billAddressLat = null;
		String[] billAddressLong = null;
		String[] shipAddressId = null;
		String[] shipAddressLine1 = null;
		String[] shipAddressCity = null;
		String[] shipAddressCountrysubDivisionCode = null;
		String[] shipAddressPostalCode = null;
		String[] shipAddressLat = null;
		String[] shipAddressLong = null;
		String[] job = null;
		String[] billWithParent = null;
		String[] balance = null;
		String[] balanceWithJobs = null;
		String[] currencyRefvalue = null;
		String[] preferredDeliveryMethod = null;
		String[] domain = null;
		String[] sparse = null;
		String[] id = null;
		String[] syncToken = null;
		String[] createTime = null;
		String[] lastUpdatedTime = null;
		String[] familyName = null;
		String[] fullName = null;
		String[] companyName = null;
		String[] displayName = null;
		String[] printOnCheckName = null;
		String[] active = null;
		String[] primaryPhoneNumber = null;
		String[] primaryEmailAddressess = null;
	    int customerSize;
	    //Format data for Lead   
		public void formatLead(JSONObject obj)
		{
			
			JSONObject customer = obj.getJSONObject("QueryResponse");
		    JSONArray arr = customer.getJSONArray("Customer");		        
	        customerSize = arr.length();
	        
	        taxable = new String[customerSize];
	        billAddressId = new String[customerSize];
	        billAddressLine1 = new String[customerSize];
	        billAddressCity = new String[customerSize];
	        billAddressCountrysubDivisionCode = new String[customerSize];
	        billAddressPostalCode = new String[customerSize];
	        billAddressLat = new String[customerSize];
	        billAddressLong = new String[customerSize];
	        shipAddressId = new String[customerSize];
	        shipAddressLine1 = new String[customerSize];
	        shipAddressCity = new String[customerSize];
	        shipAddressCountrysubDivisionCode = new String[customerSize];
	        shipAddressPostalCode = new String[customerSize];
	        shipAddressLat = new String[customerSize];
	        shipAddressLong = new String[customerSize];
	        job = new String[customerSize];
	        billWithParent = new String[customerSize];
	        balance = new String[customerSize];
	        balanceWithJobs = new String[customerSize];
	        currencyRefvalue = new String[customerSize];
	        preferredDeliveryMethod = new String[customerSize];
	        domain = new String[customerSize];
	        sparse = new String[customerSize];
	        id = new String[customerSize];
	        syncToken = new String[customerSize];
	        createTime = new String[customerSize];
	        lastUpdatedTime = new String[customerSize];
	        familyName = new String[customerSize];
	        fullName = new String[customerSize];
	        companyName = new String[customerSize];
	        displayName = new String[customerSize];
	        printOnCheckName = new String[customerSize];
	        active = new String[customerSize];
	        primaryPhoneNumber = new String[customerSize];
	        primaryEmailAddressess = new String[customerSize];
	        
	        for(int i=0;i<customerSize;i++)
	        {
	        	taxable[i] = (String) arr.getJSONObject(i).get("Taxable").toString();;
	        }
		}
		public String[] getTaxable() {
			return taxable;
		}
		public String[] getBillAddressId() {
			return billAddressId;
		}
		public String[] getBillAddressLine1() {
			return billAddressLine1;
		}
		public String[] getBillAddressCity() {
			return billAddressCity;
		}
		public String[] getBillAddressCountrysubDivisionCode() {
			return billAddressCountrysubDivisionCode;
		}
		public String[] getBillAddressPostalCode() {
			return billAddressPostalCode;
		}
		public String[] getBillAddressLat() {
			return billAddressLat;
		}
		public String[] getBillAddressLong() {
			return billAddressLong;
		}
		public String[] getShipAddressId() {
			return shipAddressId;
		}
		public String[] getShipAddressLine1() {
			return shipAddressLine1;
		}
		public String[] getShipAddressCity() {
			return shipAddressCity;
		}
		public String[] getShipAddressCountrysubDivisionCode() {
			return shipAddressCountrysubDivisionCode;
		}
		public String[] getShipAddressPostalCode() {
			return shipAddressPostalCode;
		}
		public String[] getShipAddressLat() {
			return shipAddressLat;
		}
		public String[] getShipAddressLong() {
			return shipAddressLong;
		}
		public String[] getJob() {
			return job;
		}
		public String[] getBillWithParent() {
			return billWithParent;
		}
		public String[] getBalance() {
			return balance;
		}
		public String[] getBalanceWithJobs() {
			return balanceWithJobs;
		}
		public String[] getCurrencyRefvalue() {
			return currencyRefvalue;
		}
		public String[] getPreferredDeliveryMethod() {
			return preferredDeliveryMethod;
		}
		public String[] getDomain() {
			return domain;
		}
		public String[] getSparse() {
			return sparse;
		}
		public String[] getId() {
			return id;
		}
		public String[] getSyncToken() {
			return syncToken;
		}
		public String[] getCreateTime() {
			return createTime;
		}
		public String[] getLastUpdatedTime() {
			return lastUpdatedTime;
		}
		public String[] getFamilyName() {
			return familyName;
		}
		public String[] getFullName() {
			return fullName;
		}
		public String[] getCompanyName() {
			return companyName;
		}
		public String[] getDisplayName() {
			return displayName;
		}
		public String[] getPrintOnCheckName() {
			return printOnCheckName;
		}
		public String[] getActive() {
			return active;
		}
		public String[] getPrimaryPhoneNumber() {
			return primaryPhoneNumber;
		}
		public String[] getPrimaryEmailAddressess() {
			return primaryEmailAddressess;
		}
		public int getCustomerSize() {
			return customerSize;
		}
}
