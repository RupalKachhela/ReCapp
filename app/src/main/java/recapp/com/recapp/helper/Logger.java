package recapp.com.recapp.helper;

import android.util.Log;

public  class Logger
{

    public static void putLogDebug(String msg , String contentValue)
    {
        Log.d(msg, contentValue);
    }

    public static void putLogInfo(String msg , String contentValue)
    {
        Log.i(msg, contentValue);
    }

    public static void putLogError(String msg , String contentValue)
    {
        Log.e(msg, contentValue);
    }

}
