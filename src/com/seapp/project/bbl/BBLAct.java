package com.seapp.project.bbl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.se.project.plugin.AppConstants;
import com.se.project.plugin.BaseFragment;
import com.se.project.plugin.SessionManager;
import com.se.project.plugin.SimpleAdapter;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

public class BBLAct extends FragmentActivity implements OnMenuItemClickListener,OnMenuItemLongClickListener{

	
    private HashMap<Integer, Stack<Fragment>> mStacks;
    private int mCurrentTab;
	//private TextView headerTitle;    

    private View contentHamburger;
    private FrameLayout root;
    private LinearLayout toolbar;
    private static final long RIPPLE_DURATION = 250;
    private GuillotineAnimation menu;
    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    private LinearLayout linprofile;
    private LinearLayout linschedule;
    private LinearLayout linlogout;
    private LinearLayout linstudent;
    private LinearLayout linassignment;
    private LinearLayout linaddfolder;
    private LinearLayout lingrades;
    
    Holder holder;
    SessionManager sess;
    private static final int REQUEST_CODE = 6384; // onActivityResult request
    // code
    
    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);

    }
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbl);
	
		sess = new SessionManager(getApplicationContext());
	    mStacks             =   new HashMap<Integer, Stack<Fragment>>();
	    mStacks.put(AppConstants.FRAG_SCHED, new Stack<Fragment>());
	    mStacks.put(AppConstants.FRAG_PROFILE, new Stack<Fragment>());
	    mStacks.put(AppConstants.FRAG_ASSIGN, new Stack<Fragment>());
	    mStacks.put(AppConstants.FRAG_GRADES, new Stack<Fragment>());
	    mStacks.put(AppConstants.FRAG_STUDENTS, new Stack<Fragment>());
	    
	    String usertype = sess.getUserDetails().get("usertype");
        initTypeface();

  		//headerTitle = (TextView)findViewById(R.id.headertitle);
  		root = (FrameLayout)findViewById(R.id.root);
		contentHamburger = (ImageView)findViewById(R.id.content_hamburger);
		toolbar = (LinearLayout)findViewById(R.id.toolbar);

  		View guillotineMenu = LayoutInflater.from(BBLAct.this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        linprofile = (LinearLayout)guillotineMenu.findViewById(R.id.profile_group);
        linschedule = (LinearLayout)guillotineMenu.findViewById(R.id.schedule_group);
        linassignment = (LinearLayout)guillotineMenu.findViewById(R.id.assign_group);
        linlogout = (LinearLayout)guillotineMenu.findViewById(R.id.logout_group);
        linaddfolder = (LinearLayout)guillotineMenu.findViewById(R.id.folder_group);
        lingrades = (LinearLayout)guillotineMenu.findViewById(R.id.grades_group);
        linstudent = (LinearLayout)guillotineMenu.findViewById(R.id.student_group);
        
        
        if(usertype.equals('0')){
        	linaddfolder.setVisibility(View.GONE);
        }
        holder = new ViewHolder(R.layout.content);    
        
        
        linprofile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pushFragments(AppConstants.FRAG_PROFILE, new FragProfile(), true, true);
				menu.close();
				
			}
		});
        
        
        linassignment.setOnClickListener(new View.OnClickListener() {
			
   			@Override
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				pushFragments(AppConstants.FRAG_ASSIGN, new FragViewAssign(), true, true);
   				menu.close();
   				
   			}
   		});
        
        linlogout.setOnClickListener(new View.OnClickListener() {
			
   			@Override
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				sess.setLogin(false);
   				Intent i = new Intent(BBLAct.this,LoginActivity.class);
   				startActivity(i);
   				
   			}
   		});
           
        linschedule.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pushFragments(AppConstants.FRAG_SCHED, new ScheduleFrag(), true, true);
				menu.close();
			}
		});

        //Toast.makeText(BBLAct.this,"Grades Folder Deleted Successfully", Toast.LENGTH_LONG).show();
        
        linaddfolder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Holder holder;
			    holder = new ViewHolder(R.layout.content);
				showDialog(holder,Gravity.CENTER,true,true,true);
		        		
			}
		});
        

        linstudent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pushFragments(AppConstants.FRAG_STUDENTS, new FragStuAssign(), true, true);
				menu.close();
		        		
			}
		});
        
        linaddfolder.setOnLongClickListener(new View.OnLongClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				 PopupMenu popup = new PopupMenu(BBLAct.this, linaddfolder);  
		            //Inflating the Popup using xml file  
		            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());  
		           popup.setGravity(Gravity.CENTER);
		            //registering popup with OnMenuItemClickListener  
		            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
		             public boolean onMenuItemClick(MenuItem item) {  
		              Toast.makeText(BBLAct.this,"Folder Deleted Successfully",Toast.LENGTH_SHORT).show();  
		              return true;  
		             }  
		            });  
		  
		            popup.show();//showing popup menu  
		         
			
				return false;
			}
		});

        lingrades.setOnLongClickListener(new View.OnLongClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				PopupWindow popupwindow_obj; // create object

				popupwindow_obj=popupDisplay();  // initialize in onCreate()

				popupwindow_obj.showAsDropDown(lingrades, 200, 0, Gravity.CENTER);
				return false;
			}
		});

        
        menu = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
        .setStartDelay(RIPPLE_DURATION)
        .setActionBarViewForAnimation(toolbar)
        .setClosedOnStart(false)
        .build();
  		
    	
	}

	
	public PopupWindow popupDisplay() { // disply designing your popoup window
	    final PopupWindow popupWindow = new PopupWindow(this); // inflet your layout or diynamic add view

	    View view; 

	    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE); 

	    view = inflater.inflate(R.layout.mymenu, null);


	    popupWindow.setFocusable(true);
	    popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
	    popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
	    popupWindow.setContentView(view);

	    return popupWindow;
	}
	
	

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i("", "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            Toast.makeText(BBLAct.this,
                                    "File Selected: " + path, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e("FileSelectorTestActivity", "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
	
	 public void showChooser() {
	        // Use the GET_CONTENT intent from the utility class
	        Intent target = FileUtils.createGetContentIntent();
	        // Create the chooser Intent
	        Intent intent = Intent.createChooser(
	                target, getString(R.string.chooser_title));
	        try {
	            startActivityForResult(intent, REQUEST_CODE);
	        } catch (ActivityNotFoundException e) {
	            // The reason for the existence of aFileChooser
	        }
	    }

	public void showDialog(Holder holder,int gravity, boolean showHeader, boolean showFooter, boolean expanded) {
	    

	    OnClickListener clickListener = new OnClickListener() {
	      @Override
	      public void onClick(DialogPlus dialog, View view) {
	      
	    	  switch (view.getId()) {
	    	  
	    	   case R.id.footer_confirm_button:
               Toast.makeText(BBLAct.this, "Confirm button clicked", Toast.LENGTH_LONG).show();
               break;
             case R.id.footer_close_button:
               Toast.makeText(BBLAct.this, "Close button clicked", Toast.LENGTH_LONG).show();
               break;
	    	  
	    	  }
	    	  dialog.dismiss();
	      }
	    };

	    OnItemClickListener itemClickListener = new OnItemClickListener() {
	      @Override
	      public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
	      
	    	  
	      }
	    };

	    OnDismissListener dismissListener = new OnDismissListener() {
	      @Override
	      public void onDismiss(DialogPlus dialog) {
	               Toast.makeText(BBLAct.this, "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
	      }
	    };

	    OnCancelListener cancelListener = new OnCancelListener() {
	      @Override
	      public void onCancel(DialogPlus dialog) {
	               Toast.makeText(BBLAct.this, "cancel listener invoked!", Toast.LENGTH_SHORT).show();
	      }
	    };

	    SimpleAdapter adapter = new SimpleAdapter(BBLAct.this, false);
	    showOnlyContentDialog(holder, gravity, adapter,clickListener, itemClickListener, dismissListener, cancelListener,
	          expanded);

	}
	
	 private void showOnlyContentDialog(Holder holder, int gravity, BaseAdapter adapter,
			 OnClickListener clickListener,
             OnItemClickListener itemClickListener, OnDismissListener dismissListener,
             OnCancelListener cancelListener, boolean expanded) {
			final DialogPlus dialog = DialogPlus.newDialog(this)
			.setContentHolder(holder)
			.setGravity(gravity)
			.setAdapter(adapter)
			.setFooter(R.layout.footer)
			.setOnClickListener(clickListener)
			.setOnItemClickListener(itemClickListener)
			.setOnDismissListener(dismissListener)
			.setOnCancelListener(cancelListener)
			.setExpanded(expanded)
			.setCancelable(true)
			.create();
			dialog.show();
}

	public void changeHeader(String txt){
		//headerTitle.setText(txt);
	}

	
	
	
	
	   private List<MenuObject> getMenuObjects() {
	       List<MenuObject> menuObjects = new ArrayList<MenuObject>();
	       MenuObject close = new MenuObject();
	       close.setResource(R.drawable.close1);

	       MenuObject send = new MenuObject("Schedule");
	       send.setResource(R.drawable.ic_vignette);

	       MenuObject like = new MenuObject("Assignments");
	       Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notes);
	       like.setBitmap(b);

	       MenuObject addFr = new MenuObject("Grades");
	       BitmapDrawable bd = new BitmapDrawable(getResources(),
	               BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_expand_card_dark_pressed));
	       addFr.setDrawable(bd);

	       MenuObject addFav = new MenuObject("Add Folder");
	       addFav.setResource(R.drawable.add1);

	       

	       MenuObject addProfile = new MenuObject("View Profile");
	       addProfile.setResource(R.drawable.menu_icon1);

	       
	       
	       MenuObject block = new MenuObject("Logout");
	       block.setResource(R.drawable.ic_logout);

	       menuObjects.add(close);
	       menuObjects.add(send);
	       menuObjects.add(like);
	       menuObjects.add(addFr);
	       menuObjects.add(addFav);
	       menuObjects.add(addProfile);
	       menuObjects.add(block);
	       return menuObjects;
	   }

	@Override
	public void onMenuItemLongClick(View clickedView, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMenuItemClick(View clickedView, int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 1:
			pushFragments(AppConstants.FRAG_SCHED, new ScheduleFrag(), true, true);
					
			break;
		case 5:
			pushFragments(AppConstants.FRAG_PROFILE, new FragProfile(), true, true);
				
		case 6:
			SessionManager session = new SessionManager(getApplicationContext());
			session.setLogin(false);
			 Intent intent = new Intent(BBLAct.this,
					 LoginActivity.class);
					 startActivity(intent); 
					 finish(); 
			break;
		default:
			break;
		}
	}

	/* 
     *      To add fragment to a tab. 
     *  tag             ->  Tab identifier
     *  fragment        ->  Fragment to show, in tab identified by tag
     *  shouldAnimate   ->  should animate transaction. false when we switch tabs, or adding first fragment to a tab
     *                      true when when we are pushing more fragment into navigation stack. 
     *  shouldAdd       ->  Should add to fragment navigation stack (mStacks.get(tag)). false when we are switching tabs (except for the first time)
     *                      true in all other cases.
     */
    public void pushFragments(int tag, Fragment fragment,boolean shouldAnimate, boolean shouldAdd){
    	
    	if(tag == mCurrentTab){
    		shouldAdd = false;
    		shouldAnimate = false;
    	}
	      if(shouldAdd){
        	mStacks.get(tag).push(fragment);
	      }
	    	
	      FragmentManager   manager         =   getSupportFragmentManager();
	      FragmentTransaction ft            =   manager.beginTransaction();
	      if(shouldAnimate)
	          ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
	      ft.replace(R.id.content_frame, fragment);
	      ft.commit();
	      mCurrentTab = tag;
    }

    
    @Override
    public void onBackPressed() {
    	
			       	if(((BaseFragment)mStacks.get(mCurrentTab).lastElement()).onBackPressed() == false){
			       		/*.
			       		 * top fragment in current tab doesn't handles back press, we can do our thing, which is
			       		 * 
			       		 * if current tab has only one fragment in stack, ie first fragment is showing for this tab.
			       		 *        finish the activity
			       		 * else
			       		 *        pop to previous fragment in stack for the same tab
			       		 * 
			       		 */
			       		if(mStacks.get(mCurrentTab).size() == 1){
			       			if(mCurrentTab == AppConstants.FRAG_SCHED){
			       				super.onBackPressed();  // or call finish..
			       			}else {
			       				mCurrentTab = AppConstants.FRAG_SCHED;
			       				pushFragments(AppConstants.FRAG_SCHED, new ScheduleFrag(), false, true);
			       			}
			       		
			       		}else{
			       			popFragments();
			       		}
			       	}else{
			       		//do nothing.. fragment already handled back button press.
			       	}
       	
    }
    
    public void popFragments(){
        /*    
         *    Select the second last fragment in current tab's stack.. 
         *    which will be shown after the fragment transaction given below 
         */
        Fragment fragment             =   mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

        /*pop current fragment from stack.. */
        mStacks.get(mCurrentTab).pop();
        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        FragmentManager   manager         =   getSupportFragmentManager();
        FragmentTransaction ft            =   manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
      }
	
   
}
