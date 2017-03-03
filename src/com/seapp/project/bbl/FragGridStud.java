package com.seapp.project.bbl;

import java.util.Random;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.se.project.plugin.AppConstants;
import com.se.project.plugin.BaseFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragGridStud extends BaseFragment{

	
	
	private View mRoot;
	private GridView grid;
	private String[]  myStringArray={"Piyush Zode","Pooja Venkatesh","Pranjali Pai","Gurleen Kaur","Sangram Bankar",
										"Travis Fedd","Roger Freeman","Desi Kalkkar","Balwinder Kaur","D Iyer",
										"Tom Zode","Jerry Venkatesh","Harminder Pai","Pranat Kaur","Poonam Bankar",
										"Sam Zode","Taurus Venkatesh","Big Pai","Mastana Kaur","Sneha Bankar"};
	private int[]  myImageArray={R.drawable.pai,R.drawable.index,R.drawable.kaur,R.drawable.zode,R.drawable.poo};

	private TextView txt;
	
	 private View mListTouchInterceptor;
	    private View mDetailsLayout;
	    private UnfoldableView mUnfoldableView;
	    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRoot = inflater.inflate(R.layout.fraggridstud, null);
	
		txt = (TextView)mRoot.findViewById(R.id.gridtitle);
		txt.setText(getArguments().getString("title"));
		grid = (GridView)mRoot.findViewById(R.id.gridstud);
		
		CustomAdapter adapter = new CustomAdapter(mActivity, myStringArray, myImageArray);
		grid.setAdapter(adapter);
		
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
				// mUnfoldableView.unfold(view, mDetailsLayout);
				Bundle b = new Bundle();
				b.putString("name",myStringArray[position]);
				b.putString("title", getArguments().getString("title"));
				FragGridDet f = new FragGridDet();
				f.setArguments(b);
				mActivity.pushFragments(AppConstants.FRAG_STUDENTS, f, true, true);
			
			}
		});
		
		
	/*	  mListTouchInterceptor = mRoot.findViewById( R.id.touch_interceptor_view);
	        mListTouchInterceptor.setClickable(false);

	        mDetailsLayout =(LinearLayout) mRoot.findViewById( R.id.details_layout);
	        mDetailsLayout.setVisibility(View.INVISIBLE);

	        mUnfoldableView = (UnfoldableView) mRoot.findViewById(R.id.unfoldable_view);

	        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
	        mUnfoldableView.setFoldShading(new GlanceFoldShading(glance));

	        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
	            @Override
	            public void onUnfolding(UnfoldableView unfoldableView) {
	                mListTouchInterceptor.setClickable(true);
	                mDetailsLayout.setVisibility(View.VISIBLE);
	            }

	            @Override
	            public void onUnfolded(UnfoldableView unfoldableView) {
	                mListTouchInterceptor.setClickable(false);
	            }

	            @Override
	            public void onFoldingBack(UnfoldableView unfoldableView) {
	                mListTouchInterceptor.setClickable(true);
	            }

	            @Override
	            public void onFoldedBack(UnfoldableView unfoldableView) {
	                mListTouchInterceptor.setClickable(false);
	                mDetailsLayout.setVisibility(View.INVISIBLE);
	            }
	        });*/
		
		return mRoot;
	}
	
	
	  /*public void openDetails(View coverView, int pos) {
	        ImageView image = (ImageView)mDetailsLayout.findViewById(R.id.details_image);
	        TextView title = (TextView)mDetailsLayout.findViewById(R.id.details_title);
	        TextView desc = (TextView)mDetailsLayout.findViewById(R.id.details_text);

	        Random random = new Random();
	        int randomNumber = random.nextInt(5 - 0) + 0;
	        image.setImageResource(myImageArray[randomNumber]);         
	        title.setText(myStringArray[pos]);
	       

	        mUnfoldableView.unfold(coverView, mDetailsLayout);
	    }
*/	
	class CustomAdapter extends BaseAdapter{
		   String [] result;
		   Context context;
		   int [] imageId;
		   Random random;
		    private  LayoutInflater inflater=null;
		    public CustomAdapter(Context mainActivity, String[] prgmNameList,int[] prgmImages) {
		        // TODO Auto-generated constructor stub
		        result=prgmNameList;
		        imageId=prgmImages;
		        context=mainActivity;
		         random = new Random();
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
		             rowView = inflater.inflate(R.layout.list_grid, null);
		             holder.img=(ImageView) rowView.findViewById(R.id.grid_img);  
		             holder.tv=(TextView) rowView.findViewById(R.id.grid_name);
		             
		         int randomNumber = random.nextInt(5 - 0) + 0;
		         holder.tv.setText(result[position]);
		         holder.img.setImageResource(imageId[randomNumber]);         
		         
		        
		        return rowView;
		    }
	}
	
}

