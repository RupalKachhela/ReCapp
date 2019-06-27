package recapp.com.recapp.reusables;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleTon
{

  private static VolleySingleTon mInstance;
  private RequestQueue mRequestQueue;
  private ImageLoader mImageLoader;
  private static Context mCtx;


  private VolleySingleTon(Context context)
  {
    mCtx = context;
    mRequestQueue = getRequestQueue();

    mImageLoader = new ImageLoader(mRequestQueue,
            new ImageLoader.ImageCache()
            {
              private final LruCache<String, Bitmap> cache = new LruBitmapCache(getCacheSize());

              @Override
              public Bitmap getBitmap(String url)
              {
                return cache.get(url);
              }

              @Override
              public void putBitmap(String url, Bitmap bitmap)
              {
                cache.put(url, bitmap);
              }
            });
  }

  public int getCacheSize() {
    final DisplayMetrics displayMetrics = mCtx.getResources().getDisplayMetrics();
    final int screenWidth = displayMetrics.widthPixels;
    final int screenHeight = displayMetrics.heightPixels;
    final int screenBytes = screenWidth * screenHeight * 4; // 4 bytes per pixel

    return screenBytes * 3;
  }
  public static synchronized VolleySingleTon getInstance(Context context)
  {
    if (mInstance == null)
    {
      mInstance = new VolleySingleTon(context);

    }
    return mInstance;
  }

  /**
   * Get current request queue.
   *
   * @return RequestQueue
   */
  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      // getApplicationContext() is key, it keeps you from leaking the
      // Activity or BroadcastReceiver if someone passes one in.
      mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
    }
    return mRequestQueue;
  }

  /**
   * Add new request depend on type like string, json object, json array request.
   *
   * @param req new request
   * @param <T> request type
   */
  public <T> void addToRequestQueue(Request<T> req)
  {
    getRequestQueue().add(req);
  }

  /**
   * Get image loader.
   *
   * @return ImageLoader
   */
  public ImageLoader getImageLoader()
  {
    return mImageLoader;
  }

  public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public LruBitmapCache(int maxSize) {
      super(maxSize);
    }

      /*  public LruBitmapCache() {
            super();
        }*/

    @Override
    protected int sizeOf(String key, Bitmap value) {
      return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
      return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
      put(url, bitmap);
    }

  }
}
