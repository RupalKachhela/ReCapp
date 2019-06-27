/*
 *
 */
package recapp.com.recapp.webservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import recapp.com.recapp.application.RecappApplication;

// TODO: Auto-generated Javadoc

/**
 * The Class NetworkReachability.
 */
public class NetworkReachability {

	/**
	 * Checks network available or not.
	 *
	 * @return true, if network is available
	 */
	public static boolean isNetworkAvailable()
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) RecappApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager!=null)
		{
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}

		return false;
	}

}
