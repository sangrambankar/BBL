package com.seapp.project.bbl;

import com.se.project.plugin.AppConstants;
import com.se.project.plugin.BaseFragment;
import com.se.project.plugin.SessionManager;
import com.seapp.project.bbl.FragViewAssign.CustomAdapter;
import com.seapp.project.bbl.FragViewAssign.CustomAdapter.Holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragStuAssign extends BaseFragment{

	private View mRoot;
	private String[]  myStringArray={"Assignment 1","Assignment 2","Assignment 3"};
	private ListView list;
	SessionManager sess;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRoot = inflater.inflate(R.layout.fragstuassign, null);
		list = (ListView)mRoot.findViewById(R.id.viewstuassign);

		sess = new SessionManager(mActivity);
	    final String usertype = sess.getUserDetails().get("usertype");
	    final String username = sess.getUserDetails().get("username");
	    Log.i("TAG",usertype);
				
		CustomAdapter myAdapter	= new CustomAdapter(mActivity, myStringArray);
		list.setAdapter(myAdapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				Fragment f ;
				b.putString("title",myStringArray[position]);
				if(usertype.equals("0")){
					b.putString("name",username);
					f = new FragGridDet();
					f.setArguments(b);
				}else{
					f = new FragGridStud();
					f.setArguments(b);	
				}
				mActivity.pushFragments(AppConstants.FRAG_STUDENTS, f, true, true);
			}
		});
		return mRoot;
	}

	
	class CustomAdapter extends BaseAdapter{
		   String [] result;
		   Context context;
		    private  LayoutInflater inflater=null;
		    public CustomAdapter(Context mainActivity, String[] prgmNameList) {
		        // TODO Auto-generated constructor stub
		        result=prgmNameList;
		        context=mainActivity;
		         inflater = ( LayoutInflater )context.
		                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    }
		    @Override
		    public int getCount() {
		        // TODO Auto-generated method stub
		        return result.length;
		    }

		    @Override
		    public Object getItem(int position) {
		        // TODO Auto-generated method stub
		        return position;
		    }

		    @Override
		    public long getItemId(int position) {
		        // TODO Auto-generated method stub
		        return position;
		    }

		    public class Holder
		    {
		        TextView tv;
		        ImageView img;
		    }
		    @Override
		    public View getView(final int position, View convertView, ViewGroup parent) {
		        // TODO Auto-generated method stub
		        Holder holder=new Holder();
		        View rowView;       
		             rowView = inflater.inflate(R.layout.list_assign, null);
		             holder.tv=(TextView) rowView.findViewById(R.id.list_item1);
		         holder.tv.setText(result[position]);
		         //holder.img.setImageResource(imageId[position]);         
		           
		        return rowView;
		    }
	}
}
