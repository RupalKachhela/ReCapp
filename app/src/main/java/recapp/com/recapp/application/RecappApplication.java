/*
 * 
 */
package recapp.com.recapp.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import recapp.com.recapp.R;
import recapp.com.recapp.helper.Const;


// TODO: Auto-generated Javadoc

/**
 * The Class SmartApplication.
 */
public class RecappApplication extends Application
{

	/** The ref smart application. */
	public static RecappApplication REF_SMART_APPLICATION;

	/** The app name. */
	private String appName;

	/** The Constant TAG. */
	public static final String TAG = RecappApplication.class.getSimpleName();

	/** The m instance. */
	private static RecappApplication mInstance;

	public static Typeface typeface;

	/** The m request queue. */
	private RequestQueue mRequestQueue;

	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();
		mInstance = this;
		appName = getResources().getString(R.string.app_name);

		REF_SMART_APPLICATION = this;

	}

	/**
	 * Instantiates a new smart application.
	 */
	public RecappApplication() {
		super();
	}

	/**
	 * Gets the single instance of SmartApplication.
	 * 
	 * @return single instance of SmartApplication
	 */
	public static synchronized RecappApplication getInstance() {
		return mInstance;
	}


	public static Context getContext(){
		return getInstance().getApplicationContext();
	}

	/**
	 * Gets the request queue.
	 *
	 * @return the request queue
	 */
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	/**
	 * Adds the to request queue.
	 *
	 * @param <T>
	 *            the generic type
	 * @param req
	 *            the req
	 * @param tag
	 *            the tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty

		try{
			req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
			getRequestQueue().add(req);
		}
		catch (NullPointerException e)
		{
		}

	}

	/**
	 * Adds the to request queue.
	 *
	 * @param <T>
	 *            the generic type
	 * @param req
	 *            the req
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	/**
	 * Cancel pending requests.
	 *
	 * @param tag
	 *            the tag
	 */
	public void cancelPendingRequests(Object tag)
	{
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	/**
	 * Gets the SP value by key.
	 *
	 * @param sharedPreferenceKey
	 *            the shared preference key
	 * @return the SP value by key
	 */
	public String getSPValueByKey(String sharedPreferenceKey) {
		SharedPreferences sharedPreferences = getSharedPreferences("RecappProject", Context.MODE_PRIVATE);
		return sharedPreferences.getString(sharedPreferenceKey, "");
	}

	public boolean getBoolean(String sharedPreferenceKey) {
		SharedPreferences sharedPreferences = getSharedPreferences("RecappProject", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(sharedPreferenceKey, false);
	}

	public boolean isUserLoggedIn() {
		SharedPreferences sharedPreferences = getSharedPreferences("RecappProject", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(Const.RC_IS_LOGGED_IN,false);
	}

	public boolean isMessageChecked(String sharedPreferenceKey)
	{
		SharedPreferences sharedPreferences = getSharedPreferences("RecappProject", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(sharedPreferenceKey,false);
	}

	public String[] getSPArray(String arrayName, Context mContext)
	{
		SharedPreferences prefs = mContext.getSharedPreferences("RecappProject", Context.MODE_PRIVATE);
		int size = prefs.getInt(arrayName + "_size", 0);
		String array[] = new String[size];
		for(int i=0;i<size;i++)
			array[i] = prefs.getString(arrayName + "_" + i, null);
		return array;
	}

	/**
	 * Sets the sp value by key.
	 *
	 * @param sharedPreferenceKey
	 *            the shared preference key
	 * @param value
	 *            the value
	 */
	public void setSPValueByKey(String sharedPreferenceKey, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences("RecappProject", Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
		prefsEditor.putString(sharedPreferenceKey, value);
		prefsEditor.apply();
	}

	public void setSPValueByKey(String sharedPreferenceKey, boolean value)
	{
		SharedPreferences sharedPreferences = getSharedPreferences("RecappProject", Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
		prefsEditor.putBoolean(sharedPreferenceKey, value);
		prefsEditor.apply();
	}


	public boolean saveArray(String[] array, String arrayName)
	{

		SharedPreferences prefs = getSharedPreferences("RecappProject",  Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(arrayName +"_size", array.length);
		for(int i=0;i<array.length;i++)
			editor.putString(arrayName + "_" + i, array[i]);
		return editor.commit();

	}

	public String nullToBlank(JSONObject obj, String key){
		try {
			if (obj.has(key)){
				if (obj.getString(key) == null || obj.getString(key).equalsIgnoreCase("null")){
					return "";
				}else {
					return  obj.getString(key);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return "";
	}
}
