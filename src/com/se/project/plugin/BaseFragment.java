package com.se.project.plugin;

import com.seapp.project.bbl.BBLAct;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	public BBLAct mActivity;
	public NetworkInfo networkInfo;
	public ConnectivityManager connMgr;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivity		=	(BBLAct) this.getActivity();
		
	}
	
	public boolean onBackPressed(){
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

		networkInfo = connMgr.getActiveNetworkInfo();
	}

	
}
