package com.se.project.plugin;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
	// LogCat tag
	private static String TAG = SessionManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	Editor editor;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Shared preferences file name
	private static final String PREF_NAME = "UserLogin";
	
	private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

	
    public static String usernetid = "netid",userfirstname = "firstname",userlastname = "lastname",
    		uusertype = "usertype",userappid = "appid",userimgurl = "imgurl";

	
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void setLogin(boolean isLoggedIn) {

		editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
		// commit changes
		editor.commit();

		Log.d(TAG, "User login session modified!");
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_IS_LOGGED_IN, false);
	}
	
    public void insertInfo(String netid,String firstname,String lastname,
    		String usertype,String app_id,String img_url){

		editor.putString(usernetid,netid);
		editor.putString(userfirstname,firstname);
		editor.putString(userlastname,lastname);
		editor.putString(uusertype,usertype);
		editor.putString(userappid,app_id);
		editor.putString(userimgurl,img_url);
		editor.commit();
    }
	
    public HashMap<String, String>  getUserDetails(){
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put(usernetid, pref.getString(usernetid, ""));
        hmap.put(userfirstname, pref.getString(userfirstname, ""));
        hmap.put(userlastname, pref.getString(userlastname, ""));
        hmap.put(uusertype, pref.getString(uusertype, ""));
        hmap.put(userappid, pref.getString(userappid, ""));
        hmap.put(userimgurl, pref.getString(userimgurl, ""));
        return hmap;
    } 
}
