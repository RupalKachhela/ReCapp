/*
 * 
 */
package recapp.com.recapp.reusables;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import recapp.com.recapp.application.RecappApplication;


// TODO: Auto-generated Javadoc

/**
 * The Class WebIntentService.
 */
public class WebIntentService extends IntentService
{

	/**
	 * Instantiates a new web intent service.
	 */
	public WebIntentService()
	{
		super("WebIntentService");
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent)
    {
		final RequestParams params = (RequestParams) intent.getSerializableExtra("params");

		String tag_string_req = params.getMethodName();

		String url = params.getUrl();

		final ResultReceiver rec = intent.getParcelableExtra("rec");

		StringRequest strReq = new StringRequest(params.getMethod(), url, new Response.Listener<String>()
        {

			@Override
			public void onResponse(String response)
            {

				Bundle b = new Bundle();
				b.putString("response",response);
				b.putString("method", params.getMethodName());
				rec.send(200, b);
//				Log.d("response", response.toString());

			}
		}, new Response.ErrorListener()
		{

			@Override
			public void onErrorResponse(VolleyError error)
			{
				Bundle b = new Bundle();
				String data = "";
				int errorCode = 0;
				if (error instanceof NetworkError)
				{
					b.putString("error", "NetworkError : "+ error.getLocalizedMessage());
				} else if (error instanceof ServerError)
				{
					b.putString("error", "ServerError : "+ error.getLocalizedMessage());
				} else if (error instanceof AuthFailureError)
				{
					b.putString("error", "AuthFailureError : "+ error.getLocalizedMessage());
				} else if (error instanceof ParseError)
				{
					b.putString("error", "ParseError : "+ error.getLocalizedMessage());
				} else if (error instanceof NoConnectionError)
				{
					b.putString("error", "NoConnectionError : "+ error.getLocalizedMessage());
				} else if (error instanceof TimeoutError)
				{
					b.putString("error", "TimeoutError : "+ error.getLocalizedMessage());
				}

				if(error.networkResponse!=null && error.networkResponse.statusCode == HttpsURLConnection.HTTP_BAD_REQUEST){
					Log.e("error", "Error: BadRequest ");
					errorCode = error.networkResponse.statusCode;
				}
				if(error.networkResponse!=null && error.networkResponse.data !=null)
				{
					data = new String(error.networkResponse.data);
					Log.e("error", "Error: Data  "+ data);

				}
				b.putString("method", params.getMethodName());
				b.putString("error",data);
				rec.send(errorCode, b);
				Log.e("error", "Error: " + error.getMessage());

			}
		}
		) {
			@Override
			public String getBodyContentType()
			{
				// TODO Auto-generated method stub
				if (params.getContentType() != null)
				{
					return params.getContentType();
				} else {
					return super.getBodyContentType();
				}
			}

			@Override
			public byte[] getBody() throws AuthFailureError
			{
				// TODO Auto-generated method stub
				if (params.getContentBody() != null)
				{
					try
                    {
						return params.getContentBody().toString().getBytes("UTF-8");
					} catch (UnsupportedEncodingException e)
                    {
						e.printStackTrace();
					}
					return params.getContentBody();
				}
				else
                {
					return super.getBody();
				}
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError
			{
				// TODO Auto-generated method stub
				if (params.getHeaders() != null)
				{
					return params.getHeaders();
				}
				else
                {
					return super.getHeaders();
				}

			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError
			{
				// TODO Auto-generated method stub
				if (params.getParams() != null)
				{
					return params.getParams();
				}
				else
				{
					return super.getParams();
				}

			}

			@Override
			public Priority getPriority()
			{
				// TODO Auto-generated method stub
				if (params.getParams() != null)
				{
					return params.getPriority();
				} else {
					return super.getPriority();
				}

			}
		};

		int socketTimeout = 30000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		strReq.setRetryPolicy(policy);
		RecappApplication.getInstance().addToRequestQueue(strReq, tag_string_req);


	}

}
