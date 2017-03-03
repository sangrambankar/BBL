package com.se.project.plugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

public class UserFunctions {
	
	private JSONParser jsonParser;
	private static String login_tag = "login";

	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}

	
	
	public String getUsers(String net_id,String password) throws Exception{
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag",login_tag));
		params.add(new BasicNameValuePair("net_id",net_id));
		params.add(new BasicNameValuePair("password",password));

		// getting JSON Object
		String json = jsonParser.getJSONFromUrl(AppConstants.SERVER_URL, params);
		
		return json;
	
	}
	
		public String sendPush(String msg) throws Exception{
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag","push"));
		params.add(new BasicNameValuePair("msg",msg));

		// getting JSON Object
		String json = jsonParser.getJSONFromUrl(AppConstants.SERVER_URL, params);
		Log.e("TAG",params+"");
		return json;
	
	}


}
