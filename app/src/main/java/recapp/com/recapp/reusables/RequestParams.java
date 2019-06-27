/*
 *
 */
package recapp.com.recapp.reusables;



import com.android.volley.Request;

import java.io.Serializable;
import java.util.HashMap;

// TODO: Auto-generated Javadoc

/**
 * The Class RequestParams.
 */
@SuppressWarnings("serial")
public class RequestParams implements Serializable
{

  /** The content type. */
  String contentType;

  /** The content body. */
  byte[] contentBody;

  /** The headers. */
  HashMap<String, String> headers;

  /** The params. */
  HashMap<String, String> params;

  /** The url. */
  String url;

  /** The method name. */
  String methodName;

  /** The method. */
  int method;

  /** The priority. */
  Request.Priority priority;

  /**
   * Gets the content type.
   *
   * @return the content type
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * Sets the content type.
   *
   * @param contentType the new content type
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * Gets the priority.
   *
   * @return the priority
   */
  public Request.Priority getPriority() {
    return priority;
  }

  /**
   * Sets the priority.
   *
   * @param priority the new priority
   */
  public void setPriority(Request.Priority priority) {
    this.priority = priority;
  }

  /**
   * Gets the content body.
   *
   * @return the content body
   */
  public byte[] getContentBody() {
    return contentBody;
  }

  /**
   * Sets the content body.
   *
   * @param contentBody the new content body
   */
  public void setContentBody(byte[] contentBody) {
    this.contentBody = contentBody;
  }

  /**
   * Gets the headers.
   *
   * @return the headers
   */
  public HashMap<String, String> getHeaders() {
    return headers;
  }

  /**
   * Sets the headers.
   *
   * @param headers the headers
   */
  public void setHeaders(HashMap<String, String> headers) {
    this.headers = headers;
  }

  /**
   * Gets the params.
   *
   * @return the params
   */
  public HashMap<String, String> getParams() {
    return params;
  }

  /**
   * Sets the params.
   *
   * @param params the params
   */
  public void setParams(HashMap<String, String> params) {
    this.params = params;
  }

  /**
   * Gets the url.
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets the url.
   *
   * @param url the new url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Gets the method name.
   *
   * @return the method name
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * Sets the method name.
   *
   * @param methodName the new method name
   */
  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  /**
   * Gets the method.
   *
   * @return the method
   */
  public int getMethod() {
    return method;
  }

  /**
   * Sets the method.
   *
   * @param method the new method
   */
  public void setMethod(int method) {
    this.method = method;
  }

}
