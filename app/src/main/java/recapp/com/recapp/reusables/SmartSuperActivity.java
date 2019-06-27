package recapp.com.recapp.reusables;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SmartSuperActivity extends AppCompatActivity
{
  public static SmartSuperActivity currentRef;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    currentRef = this;
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.e("onTerminate", "onDestroy");

  }
}
