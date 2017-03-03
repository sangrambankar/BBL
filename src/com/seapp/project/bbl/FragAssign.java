package com.seapp.project.bbl;

import com.alamkanak.weekview.WeekView;
import com.se.project.plugin.AppConstants;
import com.se.project.plugin.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragAssign extends BaseFragment{

	private View mRoot;
	private LinearLayout viewlin;
	private LinearLayout gradelin;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		mRoot = inflater.inflate(R.layout.frag_assign, null);
		
		viewlin = (LinearLayout)mRoot.findViewById(R.id.lin1);
		gradelin = (LinearLayout)mRoot.findViewById(R.id.lin2);
		
		viewlin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubFr
				mActivity.pushFragments(AppConstants.FRAG_ASSIGN, new FragViewAssign(), true, true);
			}
		});

		gradelin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.pushFragments(AppConstants.FRAG_ASSIGN, new FragViewAssign(), true, true);

			}
		});
		
		return mRoot;
	}

	
	
	
}

