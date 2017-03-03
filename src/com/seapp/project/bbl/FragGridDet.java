package com.seapp.project.bbl;

import org.json.JSONArray;
import org.json.JSONObject;

import com.se.project.plugin.BaseFragment;
import com.se.project.plugin.SessionManager;
import com.se.project.plugin.UserFunctions;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragGridDet extends BaseFragment{

	
	private View mRoot;
	
	private TextView txtname;
	private TextView txtlinks;
	private String txtlink;
	private String txtassignmntno;
	private EditText txtgrades;
	private EditText txtcommments;
	private Button btnsubmit;
	SessionManager sess;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		mRoot = inflater.inflate(R.layout.frag_griddet, null);
		txtname = (TextView)mRoot.findViewById(R.id.griddetname);
		txtlinks = (TextView)mRoot.findViewById(R.id.gridetlink);
		txtgrades = (EditText)mRoot.findViewById(R.id.editgrade);
		txtcommments = (EditText)mRoot.findViewById(R.id.editcomment);
		btnsubmit = (Button)mRoot.findViewById(R.id.gridetsubmit);
		
		
		
		//txtname.setText(getArguments().get("name").toString());
		txtassignmntno = getArguments().get("title").toString();
		txtlinks.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "http://omega.uta.edu/~sab7146/UC13.docx";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
		
		btnsubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String grades = txtgrades.getText().toString();
				String comment = txtcommments.getText().toString();
				String message = "Prof. John Robb graded your "+txtassignmntno+" with "+grades;
				new UserPush().execute(message);
				
			}
		});
		
		
		
		return mRoot;
	}

private class UserPush extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = null;
			UserFunctions user = new UserFunctions();
			try {
				result = user.sendPush(params[0]);
				Log.e("TAG", params[0]+"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Log.e("TAG", result+"");
		}
		
	}
	
}
