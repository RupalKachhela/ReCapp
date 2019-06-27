package recapp.com.recapp.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LoginDataModel implements Serializable {

    private int result;
    private String message;
    private int userId;
    private String name;
    private String email;
    private String password;
    private String mobile;
    private String processcode;
    private String paymentStatus;
    private String subCategoryId;

    public String getCategoryType() {
        return categoryType;
    }

    private String subCategoryCode;
    private String subCategoryName;
    private String categoryType;
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getSub_CategoryId() {
        return sub_CategoryId;
    }

    private String categoryId;
    private String sub_CategoryId;


    public LoginDataModel(JSONObject aJSONObject) throws JSONException
    {

        if (aJSONObject!=null)
        {
            result = aJSONObject.optInt("Result");
            message = aJSONObject.optString("Message");
            JSONObject dataObject = null;

            if(aJSONObject.has("user_data"))
            {
                dataObject = aJSONObject.getJSONObject("user_data");

                System.out.println("===login user data=="+dataObject);
                Log.i("Login Data Model :"," "+dataObject);

            }

            if(dataObject!=null)
            {

                userId = dataObject.optInt("user_id");
                name = dataObject.optString("name");
                email = dataObject.optString("email");
                password = dataObject.optString("password");
                mobile = dataObject.optString("mobile");
                processcode = dataObject.optString("process_code");
                paymentStatus = dataObject.optString("payment_status");
                subCategoryId = dataObject.optString("subcategory_id");
                subCategoryCode = dataObject.optString("subcategory_code");
                subCategoryName = dataObject.optString("subcategory_name");
                categoryType = dataObject.optString("type");
                categoryName = dataObject.optString("category_name");
                categoryId = dataObject.optString("category_id");
                sub_CategoryId = dataObject.optString("sub_category_id");

            }
        }
    }


    public int getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getProcesscode() {
        return processcode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public String getSubCategoryCode() {
        return subCategoryCode;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }



}
