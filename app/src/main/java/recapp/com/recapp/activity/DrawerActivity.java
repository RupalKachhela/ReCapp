package recapp.com.recapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import recapp.com.recapp.R;

public class DrawerActivity extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    Toolbar toolbar;

    private MyListAdapter arrayAdapter;
    String[] Draw_item = {"Home", "Notification", "Logout"};
    Integer[] draw_icon = {R.drawable.select_home ,R.drawable.unselect_bell , R.drawable.unselect_logout};

    private ActionBar actionBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList =  findViewById(R.id.left_drawer);
        TextView titletool = (TextView) findViewById(R.id.txt_actionbar_title);
        Button menu = findViewById(R.id.btn_menu);
        final Button home = findViewById(R.id.btnHome);

        titletool.setText("HomePage ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }

      /*  LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.hader, mDrawerList, false);
        mDrawerList.addHeaderView(header);
        arrayAdapter = new MyListAdapter(DrawerActivity.this,  Draw_item,draw_icon);

        mDrawerList.setAdapter(arrayAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());*/
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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawerActivity.this, "click home", Toast.LENGTH_SHORT).show();
                home.setBackgroundResource(R.drawable.unselect_home);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
       if (mDrawerToggle.onOptionsItemSelected(item))
       {
            return true;
        }
        return false;

      /*  if (item != null && item.getItemId() == android.R.id.home)
        {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
            {

                mDrawerLayout.closeDrawer(Gravity.RIGHT);

            }
            else
            {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return false;*/

    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements LinearLayout.OnClickListener
    {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//        {
//
//            Log.e("position", "------------------" + position);
//
//        }

        @Override
        public void onClick(View v) {
            Log.e("position", "------------------" + v);

        }
    }

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

            TextView titleText =  rowView.findViewById(R.id.tvNameItem);
            Button imageView =  rowView.findViewById(R.id.btnItem);

            titleText.setText(maintitle[position]);
            imageView.setBackgroundResource(imgid[position]);

            return rowView;

        };
    }
}
