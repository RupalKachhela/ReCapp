package recapp.com.recapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;
import recapp.com.recapp.R;
import recapp.com.recapp.application.RecappApplication;


public class SplashScreen extends AppCompatActivity
{

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        }
        setContentView(R.layout.activity_splash_screen);

        TimerTask delayTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {

                        loginIntent();

                    }

                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(delayTask, 1000);

    }

    public void loginIntent()
    {

       /* Intent loginIntent = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();*/

        if (RecappApplication.getInstance().isUserLoggedIn())
        {
            Intent loginIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        }
        else
        {

            Intent loginIntent = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();

        }

    }
}
