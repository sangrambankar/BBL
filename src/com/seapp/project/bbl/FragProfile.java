package com.seapp.project.bbl;

import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;
import com.se.project.plugin.BaseFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragProfile extends BaseFragment{

	private View mRootView;
	private TextView name;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRootView = inflater.inflate(R.layout.frag_profile, null);
		name = (TextView)mRootView.findViewById(R.id.profile_name);
		mActivity.changeHeader("Profile");
		
        Toast.makeText(mActivity,"Name changed succesfully", Toast.LENGTH_LONG).show();

		name.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Holder holder = new ViewHolder(R.layout.edit_profile);
				mActivity.showDialog(holder, Gravity.CENTER, true, true, true);
			}
		});
		
		return mRootView;
	}

	
	
	
}
