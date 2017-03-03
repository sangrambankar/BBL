package com.seapp.project.bbl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;
import com.se.project.plugin.AppConstants;
import com.se.project.plugin.SessionManager;
import com.se.project.plugin.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class LoginActivity extends Activity implements OnClickListener {
//	private static final String TAG = RegisterActivity.class.getSimpleName();
	private Button btnLogin;
	private EditText inputEmail;
	private EditText inputPassword;
	private SessionManager session;
	String email, password, result;
	private UserFunctions userfunctions;

	private View myprogressdialog;
	private RelativeLayout parentRelLayout;

	// Asyntask
		AsyncTask<Void, Void, Void> mRegisterTask;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

       // Toast.makeText(LoginActivity.this,"Logout Successfully", Toast.LENGTH_LONG).show();

		userfunctions = new UserFunctions();
		session = new SessionManager(getApplicationContext());
		inputEmail = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.btnLogin);

		

		
  		parentRelLayout = (RelativeLayout)findViewById(R.id.rel_verify);

		
		  ProgressDialog pDialog = new ProgressDialog(this);
		  pDialog.setCancelable(false);
		  
		 
		  SessionManager session = new SessionManager(getApplicationContext());
		 
		 // Check if user is already logged in or not if
		 if(session.isLoggedIn()) { // User is already logged in. Take him to
			 Intent intent = new Intent(LoginActivity.this,BBLAct.class);
			 startActivity(intent); 
			 finish(); 
		 }
		 
		// Login button Click Event
		btnLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btnLogin:
			System.out.println("Entered OnClick");
			String email = inputEmail.getText().toString().trim();
			String password = inputPassword.getText().toString();

			if (email.length() == 0) {
				Toast.makeText(getApplicationContext(), "Enter NetID!",
						Toast.LENGTH_SHORT).show();
			} else if (password.length() == 0) {
				Toast.makeText(getApplicationContext(), "Enter the password!",
						Toast.LENGTH_SHORT).show();
			} else {
				UserValidation validates = new UserValidation();
				validates.execute(email, password);
			}
			break;

		}

	}

	private class UserValidation extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String jsonObj;
			try {
				jsonObj = userfunctions.getUsers(params[0],params[1]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonObj = null;
			}
			
		
			return jsonObj;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(myprogressdialog != null){
				parentRelLayout.removeView(myprogressdialog);
			}
			
			Log.i("lo",result);
			if(result!=null){
				try {
					JSONObject jsonObj = new JSONObject(result);
					JSONArray jsonArray = jsonObj.getJSONArray("userdetails");
					 if(jsonArray.length()>0){
						 for(int i=0;i<jsonArray.length();i++)
							{
								JSONObject json_e= jsonArray.getJSONObject(i);
								String netid = json_e.getString("netid");
								String firstname = json_e.getString("firstname");
								String lastname = json_e.getString("lastname");
								String app_id = json_e.getString("app_id");
								String usertype = json_e.getString("usertype");
								String userimgurl = "";

							 session.insertInfo(netid, firstname, lastname, usertype, app_id, userimgurl);
							 session.setLogin(true);
							}
						 
					 }
					 
					 
					 
					 
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				// Make sure the device has the proper dependencies.
				GCMRegistrar.checkDevice(LoginActivity.this);

				// Make sure the manifest was properly set - comment out this line
				// while developing the app, then uncomment it when it's ready.
				GCMRegistrar.checkManifest(LoginActivity.this);
				
				// Get GCM registration id
				final String regId = GCMRegistrar.getRegistrationId(LoginActivity.this);

				// Check if regid already presents
				if (regId.equals("")) {
					// Registration is not present, register now with GCM			
					GCMRegistrar.register(LoginActivity.this,AppConstants.SENDER_ID);
				} else {
					// Device is already registered on GCM
					if (GCMRegistrar.isRegisteredOnServer(LoginActivity.this)) {
						// Skips registration.				
						Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
					} else {
						// Try to register again, but not in the UI thread.
						// It's also necessary to cancel the thread onDestroy(),
						// hence the use of AsyncTask instead of a raw thread.
						final Context context = LoginActivity.this;
						mRegisterTask = new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								// Register on our server
								// On server creates a new user
								   URL url;
							        try {
							            url = new URL("http://omega.uta.edu/~sab7146/save_reg.php");
							        } catch (MalformedURLException e) {
							            throw new IllegalArgumentException("invalid url: ");
							        }
							        StringBuilder bodyBuilder = new StringBuilder();
							        Map<String, String> paramster = new HashMap<String, String>();
							        paramster.put("regId", regId);
							        Iterator<Entry<String, String>> iterator = paramster.entrySet().iterator();
							        // constructs the POST body using the parameters
							        while (iterator.hasNext()) {
							            Entry<String, String> param = iterator.next();
							            bodyBuilder.append(param.getKey()).append('=')
							                    .append(param.getValue());
							            if (iterator.hasNext()) {
							                bodyBuilder.append('&');
							            }
							        }
							        String body = bodyBuilder.toString();
							        byte[] bytes = body.getBytes();
							        HttpURLConnection conn = null;
							        try {
							        	Log.e("URL", "> " + url);
							            conn = (HttpURLConnection) url.openConnection();
							            conn.setDoOutput(true);
							            conn.setUseCaches(false);
							            conn.setFixedLengthStreamingMode(bytes.length);
							            conn.setRequestMethod("POST");
							            conn.setRequestProperty("Content-Type",
							                    "application/x-www-form-urlencoded;charset=UTF-8");
							            // post the request
							            OutputStream out = conn.getOutputStream();
							            out.write(bytes);
							            out.close();
							            // handle the response
							            int status = conn.getResponseCode();
							            if (status != 200) {
							              throw new IOException("Post failed with error code " + status);
							            }
							        } catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} finally {
							            if (conn != null) {
							                conn.disconnect();
							            }
							        }
												
								
								
								return null;
							}

							@Override
							protected void onPostExecute(Void result) {
								GCMRegistrar.setRegisteredOnServer(context, true);
								mRegisterTask = null;
							}

						};

						 mRegisterTask.execute(null, null, null);
						
					}
				}
				

				 Intent intent = new Intent(LoginActivity.this,
						 BBLAct.class);
						 startActivity(intent); 
						 finish();
			}
		}
		
	}

	
	
}
