/*
 *
 */
package recapp.com.recapp.reusables;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

// TODO: Auto-generated Javadoc

/**
 * The Class ResponseReceiver.
 */
public class ResponseReceiver extends ResultReceiver {

  /** The listener. */
  private Listener listener;

  /**
   * Instantiates a new response receiver.
   *
   * @param handler the handler
   */
  public ResponseReceiver(Handler handler)
  {
    super(handler);
    // TODO Auto-generated constructor stub
  }

  /**
   * Sets the listener.
   *
   * @param listener the new listener
   */
  public void setListener(Listener listener) {
    this.listener = listener;
  }

  /* (non-Javadoc)
   * @see android.os.ResultReceiver#onReceiveResult(int, android.os.Bundle)
   */
  @Override
  protected void onReceiveResult(int resultCode, Bundle resultData) {
    if (listener != null)
      listener.onReceiveResult(resultCode, resultData);
  }

  /**
   * The Interface Listener.
   */
  public static interface Listener
  {

    /**
     * On receive result.
     *
     * @param resultCode the result code
     * @param resultData the result data
     */
    void onReceiveResult(int resultCode, Bundle resultData);
  }



}
