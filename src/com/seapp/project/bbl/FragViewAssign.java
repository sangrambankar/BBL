package com.seapp.project.bbl;

import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;
import com.se.project.plugin.BaseFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragViewAssign extends BaseFragment{

	private View mRoot;
	private Button btnupload;
	private ListView list;
	
	String[]  myStringArray={"Assignment 1","Assignment 2","Assignment 3"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRoot = inflater.inflate(R.layout.frag_viewassign, null);
		
		btnupload = (Button)mRoot.findViewById(R.id.upload_btn);
		list = (ListView)mRoot.findViewById(R.id.viewassignlist);
		
		CustomAdapter myAdapter	= new CustomAdapter(mActivity, myStringArray);
		list.setAdapter(myAdapter);;
		
		btnupload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.showChooser();
			}
		});
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Holder holder;
			    holder = new ViewHolder(R.layout.content);
				mActivity.showDialog(holder, Gravity.CENTER, true, true, true);
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
