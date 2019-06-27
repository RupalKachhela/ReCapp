package recapp.com.recapp.webservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.Request;


import java.util.HashMap;

import recapp.com.recapp.R;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.reusables.RequestParams;
import recapp.com.recapp.reusables.ResponseReceiver;
import recapp.com.recapp.reusables.WebIntentService;

public class WebServiceHandler implements ResponseReceiver.Listener
{

  /** The m listener. */
  private static WebServiceResponseCallBack mListener;

  /** The m instance. */
  private static WebServiceHandler mInstance;

  /** The request params. */
  private RequestParams requestParams;



  /**
   * The Interface WebServiceResponseCallBack.
   */
  public interface WebServiceResponseCallBack
  {

    /**
     * On success.
     *
     * @param mMethod
     *            the m method
     * @param mResponse
     *            the m response
     */
    void onSuccess(String mMethod, String mResponse);

    /**
     * On failure.
     *
     * @param mMethod
     *            the m method
     * @param mError
     *            the m error
     */
    void onFailure(String mMethod, String mError);

    /**
     * On cache.
     *
     * @param mMethod
     *            the m method
     * @param mResponse
     *            the m response
     */
    void onCache(String mMethod, String mResponse);
  }

  /**
   * Gets the single instance of WebserviceHandler.
   *
   * @param listener
   *            the listener
   * @return single instance of WebserviceHandler
   */
  public static synchronized WebServiceHandler getInstance(WebServiceResponseCallBack listener) {
    mListener = listener;
    if (mInstance == null)
    {
      mInstance = new WebServiceHandler();
    }
    return mInstance;
  }

  /**
   * Prepare call.
   */
  public void prepareCall()
  {

    requestParams = new RequestParams();

    // set header parameter here
    // requestParams.setHeaders(headers)
  }

  /**
   * Web service get call.
   *
   * @param mContext
   *            the m context
   * @param mMethod
   *            the m method
   * @param mURL
   *            the m url
   */
  public void WebServiceGetCall(Context mContext, String mMethod, String mURL)
  {
    prepareCall();

    if (NetworkReachability.isNetworkAvailable())
    {
      requestParams.setMethod(Request.Method.GET);
      requestParams.setUrl(mURL);
      requestParams.setMethodName(mMethod);
      HashMap<String, String> headers = new HashMap<>();
      headers.put("Content-Type", "application/json");
      headers.put("Accept", "application/json");
      requestParams.setHeaders(headers);
      mContext.startService(createCallingIntent(requestParams));
    }
    else
    {
      if (mListener != null)
      {
        mListener.onFailure(mMethod, "No internet connection available");
        showAlertDialog(mContext);
      }
    }

  }
  /**
   *
   * Web service post call.
   *
   * @param mMethod
   *            the m method
   * @param mURL
   *            the m url
   * @param mStringData
   *            the m string data
   */
  public void WebServicePostCall(Context context, String mMethod, String mURL, String mStringData, HashMap<String, String> params)
  {
    prepareCall();

    if (NetworkReachability.isNetworkAvailable())
    {
      requestParams.setMethod(Request.Method.POST);
      requestParams.setUrl(mURL);
      requestParams.setMethodName(mMethod);

      HashMap<String, String> header = new HashMap<>();
      header.put("Content-Type", "application/x-www-form-urlencoded");
      requestParams.setHeaders(header);

      if (params.size() > 0)
      {
        requestParams.setParams(params);
      }
      System.out.println("=====Request param===="+requestParams);
      RecappApplication.getInstance().getApplicationContext().startService(createCallingIntent(requestParams));

    } else {
      if (mListener != null)
      {
        mListener.onFailure(mMethod, "No internet connection available");
        showAlertDialog(context);
      }
    }

  }


  private Intent createCallingIntent(RequestParams params)
  {
    Intent i = new Intent(RecappApplication.getInstance().getApplicationContext(), WebIntentService.class);
    ResponseReceiver receiver = new ResponseReceiver(new Handler());
    receiver.setListener(WebServiceHandler.this);
    i.putExtra("rec", receiver);
    i.putExtra("params", params);
    return i;
  }



  @Override
  public void onReceiveResult(int resultCode, Bundle resultData)
  {
    // TODO Auto-generated method stub
    if (resultCode == 200)
    {
      if (mListener != null)
      {
        mListener.onSuccess(resultData.getString("method"), resultData.getString("response"));
      }

    }

    else
    {
      if (mListener != null)
      {
        mListener.onFailure(resultData.getString("method"), resultData.getString("error"));
      }
    }
  }

  public void showAlertDialog(Context context)
  {
    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    alertDialog.setTitle("Alert");
    // Setting Dialog Message
    alertDialog.setMessage("No internet connection available");
    // Setting Icon to Dialog
    alertDialog.setIcon(R.drawable.rc_logo);
    // Setting OK Button
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int which)
      {
        // Write your code here to execute after dialog closed

        //Calling this method to close this activity when internet is not available.


      }
    });

    // Showing Alert Message
    alertDialog.show();
  }
}
