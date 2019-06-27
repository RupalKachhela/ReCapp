package recapp.com.recapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import recapp.com.recapp.CommunicationHandler.CommunicationHandler;
import recapp.com.recapp.R;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.model.LoginDataModel;
import recapp.com.recapp.reusables.Apphelper;

public class MakePaymentDetailActivity extends AppCompatActivity implements View.OnClickListener , CommunicationHandler.CommunicationHandlerCallBack
{
    Button btnBack , btnProceed , btnPayment, btn_back_payment;
    EditText edtCode;
    public static boolean ownReg = false;
    String bName , bEmail ,bMobile ,bPassword , bcPassword ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment_detail);
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (getIntent() != null)
        {
          Bundle b =  getIntent().getExtras();
          if (b != null)
          {
             bName =  b.getString("name");
              bEmail =  b.getString("email");
              bPassword =  b.getString("password");
              bcPassword =  b.getString("cpassword");
              bMobile =  b.getString("mobile");

          }
        }
        init();
    }
    public void init()
    {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        edtCode = findViewById(R.id.edt_code_makePayment);
        btnProceed = findViewById(R.id.btn_procced_makePayment);
        btn_back_payment = findViewById(R.id.btn_back_makePayment);
        btnPayment = findViewById(R.id.btn_Payment_makePayment);

        btnBack.setOnClickListener(this);
        btnProceed.setOnClickListener(this);
        btnPayment.setOnClickListener(this);
        btn_back_payment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_back:
            {

                hideInputSoftKey();
                onBackPressed();
                overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                break;

            }

            case R.id.btn_procced_makePayment:
            {

                if (edtCode.getText().toString().length() == 0 )
                {
                    Toast.makeText(this, "Enter code", Toast.LENGTH_SHORT).show();
                }else {
                    hideInputSoftKey();

                    callForRegistration();
                }

                break;
            }
            case R.id.btn_Payment_makePayment:
            {


                hideInputSoftKey();
                Intent registerIntent = new Intent(MakePaymentDetailActivity.this, Category_Own_WihoutCodeActivity.class);
                registerIntent.putExtra("name",bName);
                registerIntent.putExtra("email",bEmail);
                registerIntent.putExtra("password",bPassword);
                registerIntent.putExtra("cpassword",bcPassword);
                registerIntent.putExtra("mobile",bMobile);
                startActivity(registerIntent);
                finish();
                overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);


                break;
            }
            case R.id.btn_back_makePayment:
            {

                hideInputSoftKey();
                onBackPressed();
                overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                break;

            }
        }
    }

    private void hideInputSoftKey()
    {
        try
        {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (Exception aE)
        {
            aE.printStackTrace();
        }

    }

    public void callForRegistration()
    {
        Apphelper.getInstance().showProgressDialog(this,true,null);

        HashMap<String, String> params = new HashMap<>();
        params.put("name", bName);
        params.put("email",bEmail);
        params.put("password", bPassword);
        params.put("cpassword",bcPassword);
        params.put("mobile", bMobile);
        params.put("process_code", edtCode.getText().toString());
        params.put("payment_status", "");

        Logger.putLogDebug("RegistrationParams ==> ",params.toString());
        CommunicationHandler.getInstance(MakePaymentDetailActivity.this).callForRegistarion(MakePaymentDetailActivity.this, params);

    }

    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        Logger.putLogDebug("RegistrationResponse ==> ",jsonObject.toString());

        Apphelper.getInstance().dismissProgressDialog();
        String subCategoryId , subCategoryName, categoryType;
        if (mMethod.equals(Const.REGISTRATION)) {
            Logger.putLogInfo("RegisterResponse ==>", jsonObject.toString());

            try {
                LoginDataModel registerData = new LoginDataModel(jsonObject);
                int result = registerData.getResult();
                String msg = registerData.getMessage();

                if (result == 1) {
                    subCategoryName = registerData.getSubCategoryName();
                    subCategoryId = registerData.getSubCategoryId();
                    categoryType = registerData.getCategoryType();
                    RecappApplication.getInstance().setSPValueByKey(Const.RC_USER_ID, String.valueOf(registerData.getUserId()));

                    //callForCategoryList();

                    System.out.println("===CategoryName : " + categoryType);

                    if (categoryType.equals("School")) {
                        Intent registerIntent = new Intent(this, Category_SchoolActivity.class);
                        registerIntent.putExtra("SubCategoryName", subCategoryName);
                        registerIntent.putExtra("SubCategoryId", subCategoryId);

                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (categoryType.equals("Institude")) {
                        Intent registerIntent = new Intent(this, Category_TuitionActivity.class);
                        registerIntent.putExtra("SubCategoryName", subCategoryName);
                        registerIntent.putExtra("SubCategoryId", subCategoryId);

                        System.out.println("===Insitiu id : "+subCategoryId);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);
                    }


                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                    alertDialog.setMessage(msg);
                    alertDialog.setIcon(R.drawable.rc_logo);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface aDialogInterface, int aI) {
                            aDialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }

            } catch (JSONException aE) {
                aE.printStackTrace();
            }

        }
    }

    @Override
    public void failureCBWithMethod(String mMethod, String mError) {

    }

    @Override
    public void cacheCBWithMethod(String mMethod, String mResponse) {

    }
}
