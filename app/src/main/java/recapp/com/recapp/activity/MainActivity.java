package recapp.com.recapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import recapp.com.recapp.R;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.fragment.DeleteFragment;
import recapp.com.recapp.fragment.HomeFragment;
import recapp.com.recapp.fragment.RecapFragment;
import recapp.com.recapp.fragment.RecordFragment;
import recapp.com.recapp.fragment.ReviseFragment;
import recapp.com.recapp.fragment.SchoolFragment;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.interfaces.ItemClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    FrameLayout mainFrame;
    private FragmentManager mFragmentManager;
    Fragment mFragment = null;
    ImageButton btnRecord, btnDelete , btnBook , btnPaper;
    RelativeLayout rl_pink , rl_purple, rl_orange,rl_green;
    LinearLayout ll_drawer;
    Button menuBtn;
    TextView titletool;
    Button menu;
    LinearLayout ll_home , ll_notification , ll_logout;
    Button btnHome , btnNotification , btnLogout;
    TextView tvHome , tvNotification , tvLogout;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    Toolbar toolbar;
    MyListAdapter arrayAdapter;

    String[] Draw_item = {"Home", "Notification", "Logout"};
    Integer[] draw_icon = {R.drawable.select_home ,R.drawable.unselect_bell , R.drawable.unselect_logout};



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindrawer);

     /*  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/
        //getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                  WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
          /*  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/

        }

        menuBtn = findViewById(R.id.btn_menu);
        mainFrame = findViewById(R.id.fl_main_container);
        btnBook = findViewById(R.id.btnBook);
        btnRecord = findViewById(R.id.btnRecord);
        btnPaper = findViewById(R.id.btnPaper);
        btnDelete = findViewById(R.id.btnDelete);
        rl_pink = findViewById(R.id.rl_pink);
        rl_purple = findViewById(R.id.rl_purple);
        rl_green = findViewById(R.id.rl_green);
        rl_orange = findViewById(R.id.rl_orange);
        ll_drawer = findViewById(R.id.ll_drawer);
        mDrawerLayout =  findViewById(R.id.drawer_layout);
        mDrawerList =  findViewById(R.id.left_drawer);
        titletool =  findViewById(R.id.txt_actionbar_title);
        menu = findViewById(R.id.btn_menu);
        ll_home = findViewById(R.id.ll_home_drawer);
        ll_notification = findViewById(R.id.ll_notification_drawer);
        ll_logout = findViewById(R.id.ll_logout_drawer);
        btnHome = findViewById(R.id.btnHome);
        btnNotification = findViewById(R.id.btnBell);
        btnLogout = findViewById(R.id.btnLogout);
        tvHome = findViewById(R.id.tvNameHome);
        tvNotification = findViewById(R.id.tvNameBell);
        tvLogout = findViewById(R.id.tvNameItem);

        mTitle = mDrawerTitle = getTitle();
        titletool.setText("HomePage ");




        menuBtn.setOnClickListener(this);
        rl_pink.setOnClickListener(this);
        rl_purple.setOnClickListener(this);
        rl_green.setOnClickListener(this);
        rl_orange.setOnClickListener(this);
        btnBook.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnPaper.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_notification.setOnClickListener(this);
        ll_logout.setOnClickListener(this);

//        LayoutInflater inflater = getLayoutInflater();
//        View header = inflater.inflate(R.layout.hader, mDrawerList, false);
//        mDrawerList.addHeaderView(header);
//        arrayAdapter = new MyListAdapter(MainActivity.this,  Draw_item,draw_icon);
//        mDrawerList.setAdapter(arrayAdapter);

       // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        toolbar,
                        R.string.drawer_open,
                        R.string.drawer_close
                )
        {
            public void onDrawerClosed(View view)
            {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                mDrawerLayout.openDrawer(mDrawerList);

            }
        });

        setHomeFragmnet();

        final FragmentManager fm=this.getSupportFragmentManager();
        final Fragment fragment=fm.findFragmentByTag("MY_FRAGMENT");

        if(fragment != null && fragment.isVisible()){
            Log.i("TAG","my fragment is visible");
        }
        else{
            Log.i("TAG","my fragment is not visible");
        }

       /* FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fl_main_container);

        if (currentFragment.getTag().equals("MY_FRAGMENT"))
        {
            System.out.println("====cauurent fragment ==="+"home"+currentFragment.getTag().toString());
            //Do something
        }
       */


        /*  android.app.Fragment myFragment = getFragmentManager().findFragmentByTag("MY_FRAGMENT");
        if (myFragment != null && myFragment.isVisible()) {
            // add your code here
            btnHome.setBackgroundResource(R.drawable.select_home);
            tvHome.setTextColor(getResources().getColor(R.color.colorOrange));
        }*/

    }

    public void setRecordFragmnet()
    {
        mFragment = new RecordFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_main_container, mFragment).addToBackStack(HomeFragment.TAG).commit();

    }
    public void setReviseFragmnet()
    {
        mFragment = new ReviseFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_main_container, mFragment).addToBackStack(HomeFragment.TAG).commit();
    }
    public void setRecapFragmnet()
    {
        mFragment = new RecapFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_main_container, mFragment).addToBackStack(HomeFragment.TAG).commit();
    }
    public void setDeleteFragmnet()
    {
        mFragment = new DeleteFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_main_container, mFragment).addToBackStack(HomeFragment.TAG).commit();

    }
    public void setHomeFragmnet()
    {

        mFragment = new HomeFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_main_container, mFragment).commit();
        btnHome.setBackgroundResource(R.drawable.select_home);
        tvHome.setTextColor(getResources().getColor(R.color.colorOrange));

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnRecord:
            {

                if(RecappApplication.getInstance().getSPValueByKey(Const.RC_PROCESS_CODE).isEmpty() || RecappApplication.getInstance().getSPValueByKey(Const.RC_PROCESS_CODE) == null) {

                    btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink_white);
                    btnRecord.setImageResource(R.drawable.rc_select_audio);
                    btnBook.setBackgroundResource(R.drawable.tab2_circle_green);
                    btnBook.setImageResource(R.drawable.rc_unselected_recap);
                    btnPaper.setBackgroundResource(R.drawable.tab3_circle_purple);
                    btnPaper.setImageResource(R.drawable.rc_unselected_revise);
                    btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange);
                    btnDelete.setImageResource(R.drawable.rc_unselected_delete);
                    rl_pink.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    rl_green.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    rl_purple.setBackgroundColor(getResources().getColor(R.color.colorPurple));
                    rl_orange.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                    setRecordFragmnet();

                }

                else{

                    String node = SchoolFragment.getSelectedNodes();
                    System.out.println("====selected _ nodes : " + node);
                    btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink_white);
                    btnRecord.setImageResource(R.drawable.rc_select_audio);
                    btnBook.setBackgroundResource(R.drawable.tab2_circle_green);
                    btnBook.setImageResource(R.drawable.rc_unselected_recap);
                    btnPaper.setBackgroundResource(R.drawable.tab3_circle_purple);
                    btnPaper.setImageResource(R.drawable.rc_unselected_revise);
                    btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange);
                    btnDelete.setImageResource(R.drawable.rc_unselected_delete);
                    rl_pink.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    rl_green.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    rl_purple.setBackgroundColor(getResources().getColor(R.color.colorPurple));
                    rl_orange.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                    setRecordFragmnet();
                }
                break;

            }
            case R.id.btnBook:
            {

                btnBook.setBackgroundResource(R.drawable.tab2_circle_green_white);
                btnBook.setImageResource(R.drawable.rc_selected_recap);
                btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink);
                btnRecord.setImageResource(R.drawable.rc_unselected_redio);
                btnPaper.setBackgroundResource(R.drawable.tab3_circle_purple);
                btnPaper.setImageResource(R.drawable.rc_unselected_revise);
                btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange);
                btnDelete.setImageResource(R.drawable.rc_unselected_delete);
                rl_pink.setBackgroundColor(getResources().getColor(R.color.colorPink));
                rl_green.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rl_purple.setBackgroundColor(getResources().getColor(R.color.colorPurple));
                rl_orange.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setRecapFragmnet();
                break;

            }
            case R.id.btnPaper:
            {

                btnPaper.setBackgroundResource(R.drawable.tab3_circle_puprple_white);
                btnPaper.setImageResource(R.drawable.rc_selected_revise);
                btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink);
                btnRecord.setImageResource(R.drawable.rc_unselected_redio);
                btnBook.setBackgroundResource(R.drawable.tab2_circle_green);
                btnBook.setImageResource(R.drawable.rc_unselected_recap);
                btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange);
                btnDelete.setImageResource(R.drawable.rc_unselected_delete);
                rl_pink.setBackgroundColor(getResources().getColor(R.color.colorPink));
                rl_green.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                rl_purple.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rl_orange.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setReviseFragmnet();
                break;
            }
            case R.id.btnDelete:
            {


                btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange_white);
                btnDelete.setImageResource(R.drawable.rc_selected_delete);
                btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink);
                btnRecord.setImageResource(R.drawable.rc_unselected_redio);
                btnBook.setBackgroundResource(R.drawable.tab2_circle_green);
                btnBook.setImageResource(R.drawable.rc_unselected_recap);
                btnPaper.setBackgroundResource(R.drawable.tab3_circle_purple);
                btnPaper.setImageResource(R.drawable.rc_unselected_revise);
                rl_pink.setBackgroundColor(getResources().getColor(R.color.colorPink));
                rl_green.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                rl_purple.setBackgroundColor(getResources().getColor(R.color.colorPurple));
                rl_orange.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                setDeleteFragmnet();
                break;
            }
            case R.id.btn_menu:
            {

                ll_drawer.setVisibility(View.VISIBLE);
                break;

            }
            case R.id.ll_home_drawer:
            {
                mDrawerLayout.closeDrawer(mDrawerList);
                setHomeFragmnet();
                btnHome.setBackgroundResource(R.drawable.select_home);
                tvHome.setTextColor(getResources().getColor(R.color.colorOrange));
                btnLogout.setBackgroundResource(R.drawable.unselect_logout);
                tvLogout.setTextColor(getResources().getColor(R.color.colorWhite));
                btnNotification.setBackgroundResource(R.drawable.unselect_bell);
                tvNotification.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            }
            case R.id.ll_notification_drawer:
            {
                mDrawerLayout.closeDrawer(mDrawerList);
                btnHome.setBackgroundResource(R.drawable.unselect_home);
                tvHome.setTextColor(getResources().getColor(R.color.colorWhite));
                btnLogout.setBackgroundResource(R.drawable.unselect_logout);
                tvLogout.setTextColor(getResources().getColor(R.color.colorWhite));
                btnNotification.setBackgroundResource(R.drawable.select_bell);
                tvNotification.setTextColor(getResources().getColor(R.color.colorOrange));
                break;
            }
            case R.id.ll_logout_drawer:
            {

                mDrawerLayout.closeDrawer(mDrawerList);
                btnHome.setBackgroundResource(R.drawable.unselect_home);
                tvHome.setTextColor(getResources().getColor(R.color.colorWhite));
                btnLogout.setBackgroundResource(R.drawable.select_logout);
                tvLogout.setTextColor(getResources().getColor(R.color.colorOrange));
                btnNotification.setBackgroundResource(R.drawable.unselect_bell);
                tvNotification.setTextColor(getResources().getColor(R.color.colorWhite));
                RecappApplication.getInstance().setSPValueByKey(Const.SELECTED_SUBJECT, "");
                RecappApplication.getInstance().setSPValueByKey(Const.RC_IS_LOGGED_IN, false);
                Intent loginIntent  = new Intent(this , LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
                break;

            }

         /*   case R.id.rl_pink:
            {
                btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink_white);
                btnRecord.setImageResource(R.drawable.select_record);
                btnBook.setBackgroundResource(R.drawable.tab2_circle_green);
                btnBook.setImageResource(R.drawable.unselect_book);
                btnPaper.setBackgroundResource(R.drawable.tab3_circle_purple);
                btnPaper.setImageResource(R.drawable.unselect_paper);
                btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange);
                btnDelete.setImageResource(R.drawable.unselect_delete);
                rl_pink.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rl_green.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                rl_purple.setBackgroundColor(getResources().getColor(R.color.colorPurple));
                rl_orange.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setRecordFragmnet();
                break;
            }
            case R.id.rl_green:
            {
                btnBook.setBackgroundResource(R.drawable.tab2_circle_green_white);
                btnBook.setImageResource(R.drawable.select_book);
                btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink);
                btnRecord.setImageResource(R.drawable.unselect_record);
                btnPaper.setBackgroundResource(R.drawable.tab3_circle_purple);
                btnPaper.setImageResource(R.drawable.unselect_paper);
                btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange);
                btnDelete.setImageResource(R.drawable.unselect_delete);
                rl_pink.setBackgroundColor(getResources().getColor(R.color.colorPink));
                rl_green.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rl_purple.setBackgroundColor(getResources().getColor(R.color.colorPurple));
                rl_orange.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setRecapFragmnet();
                break;
            }
            case R.id.rl_purple:
            {
                btnPaper.setBackgroundResource(R.drawable.tab3_circle_puprple_white);
                btnPaper.setImageResource(R.drawable.select_paper);
                btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink);
                btnRecord.setImageResource(R.drawable.unselect_record);
                btnBook.setBackgroundResource(R.drawable.tab2_circle_green);
                btnBook.setImageResource(R.drawable.unselect_book);
                btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange);
                btnDelete.setImageResource(R.drawable.unselect_delete);
                rl_pink.setBackgroundColor(getResources().getColor(R.color.colorPink));
                rl_green.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                rl_purple.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                rl_orange.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setReviseFragmnet();
                break;
            }
            case R.id.rl_orange:
            {
                btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange_white);
                btnDelete.setImageResource(R.drawable.select_delete);
                btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink);
                btnRecord.setImageResource(R.drawable.unselect_record);
                btnBook.setBackgroundResource(R.drawable.tab2_circle_green);
                btnBook.setImageResource(R.drawable.unselect_book);
                btnPaper.setBackgroundResource(R.drawable.tab3_circle_purple);
                btnPaper.setImageResource(R.drawable.unselect_paper);
                rl_pink.setBackgroundColor(getResources().getColor(R.color.colorPink));
                rl_green.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                rl_purple.setBackgroundColor(getResources().getColor(R.color.colorPurple));
                rl_orange.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                setDeleteFragmnet();
                break;
            }*/

        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBackPressed()
    {
        Log.e("@@@@@@ back stack entry count : " ,""+getSupportFragmentManager().getBackStackEntryCount());
        if (getFragmentManager().getBackStackEntryCount() > 0)
        {
         getFragmentManager().popBackStack();

        } else
        {
            super.onBackPressed();
        }
    }

    public void updateStatusBarColor(String color){// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        System.out.println("=====option item selected "+item.getItemId());
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            System.out.println("===== item selected "+item.toString());

            return true;
        }
        return false;


    }

 /*   *//* The click listner for ListView in the navigation drawer *//*
    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {

            Log.e("position", "------------------" + position);

            Toast.makeText(MainActivity.this, ""+parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        }
    }
*/
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public class MyListAdapter extends ArrayAdapter<String>
    {

        private final Activity context;
        private final String[] maintitle;
        private final Integer[] imgid;
        ItemClickListener onItemClickListener;

        public MyListAdapter(Activity context, String[] maintitle, Integer[] imgid)
        {
            super(context, R.layout.item_drawer, maintitle);
            this.context=context;
            this.maintitle=maintitle;
            this.imgid=imgid;

        }

        public View getView(int position,View view,ViewGroup parent)
        {

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.item_drawer, null,true);

            final TextView titleText = (TextView) rowView.findViewById(R.id.tvNameItem);
            Button imageView = (Button) rowView.findViewById(R.id.btnItem);

            titleText.setText(maintitle[position]);
            imageView.setBackgroundResource(imgid[position]);

            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                   // Toast.makeText(context, ""+titleText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            ;
            return rowView;

        };
    }
}
