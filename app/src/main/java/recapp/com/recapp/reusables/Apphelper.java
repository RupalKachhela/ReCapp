package recapp.com.recapp.reusables;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;


import java.io.ByteArrayOutputStream;

import recapp.com.recapp.R;

public class Apphelper
{

  private static Apphelper mApphelper;

  private ProgressDialog dialog;

  public static Apphelper getInstance()
  {
    if (mApphelper == null)
    {
      mApphelper = new Apphelper();
    }
    return mApphelper;
  }


  public static byte[] getFileDataFromDrawable(Context context, int id) {
    Drawable drawable = ContextCompat.getDrawable(context, id);
    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }

  /**
   * Turn drawable into byte array.
   *
   * @param drawable data
   * @return byte array
   */
  public static byte[] getFileDataFromDrawable(Context context, Drawable drawable)
  {
    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }

  public synchronized void showProgressDialog(Context mContext, boolean showFullScreen, @Nullable String message)
  {
    if (dialog == null)
    {
      dialog = new ProgressDialog(mContext, R.style.MyAlertDialogStyle);
      dialog.setCancelable(false);
    }
    try {
      if (message != null && !message.isEmpty())
      {
        dialog.setMessage(message);
      } else {
        dialog.setMessage("");
      }
      if (!dialog.isShowing())
      {
        dialog.show();
        if (showFullScreen)
        {
          if (dialog.getWindow() != null)
          {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.progress_layout);
          }
        }
      }
    } catch (WindowManager.BadTokenException e)
    {
      Log.e("TAG", "" + e.getMessage());
    }
  }

  public synchronized void dismissProgressDialog()
  {
    if (dialog != null && dialog.isShowing())
    {
      dialog.dismiss();
      dialog = null;
    }
  }
}
