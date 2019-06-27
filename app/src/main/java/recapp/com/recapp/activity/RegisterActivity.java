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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recapp.com.recapp.CommunicationHandler.CommunicationHandler;
import recapp.com.recapp.R;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.helper.Validator;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;
import recapp.com.recapp.model.LoginDataModel;
import recapp.com.recapp.reusables.Apphelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener , CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnBack,btnPaymnet , btnProceed;
    EditText edtName ,edtMobile , edtPassword , edtCpaaword , edtEmail ,edtCode;
    String name , mobile , password , cpassword , email , code ;
    String paymentStatus = "";
    List<BatchDataModel> categoryList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

          /*  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/

        }

        init();
    }

    public void init()
    {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(this);
        btnPaymnet = findViewById(R.id.btn_makePaymnet_register);
        btnProceed = findViewById(R.id.btn_procced_register);
        edtName = findViewById(R.id.edt_Name_Register);
        edtEmail = findViewById(R.id.edt_Email_Register);
        edtPassword = findViewById(R.id.edt_password_Register);
        edtCpaaword = findViewById(R.id.edt_confirm_password_Register);
        edtMobile = findViewById(R.id.edt_mobile_Register);
        edtCode = findViewById(R.id.edt_entercode_Register);


        btnProceed.setOnClickListener(this);
        btnPaymnet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.btn_back:
            {
                onBackPressed();
                overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                break;
            }
            case R.id.btn_makePaymnet_register:
            {
                hideInputSoftKey();
                name = edtName.getText().toString().trim();
                mobile = edtMobile.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                cpassword = edtCpaaword.getText().toString().trim();
                code = edtCode.getText().toString().trim();

                if (name.length() == 0 )
                {
                    Toast.makeText(this, "Enter your name ", Toast.LENGTH_SHORT).show();

                }else  if(email.length() == 0)
                {
                    Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();

                }else  if(password.length() == 0)
                {
                    Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();

                }else if (!password.equals(cpassword))
                {

                    if (cpassword.length() == 0)
                    {
                        Toast.makeText(this, "Enter confirm  password", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();

                    }

                }else  if(mobile.length() == 0)
                {
                    Toast.makeText(this, "Enter mobile Number", Toast.LENGTH_SHORT).show();

                }else {
                    if (!Validator.isValidEmailId(edtEmail.getText().toString()))
                    {
                        Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        hideInputSoftKey();
                        Intent registerIntent = new Intent(this,MakePaymentDetailActivity.class);
                        registerIntent.putExtra("name",name);
                        registerIntent.putExtra("email",email);
                        registerIntent.putExtra("password",password);
                        registerIntent.putExtra("cpassword",cpassword);
                        registerIntent.putExtra("mobile",mobile);

                        startActivity(registerIntent);
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);
                    }

                }

                break;
            }
            case R.id.btn_procced_register:
            {
                name = edtName.getText().toString().trim();
                mobile = edtMobile.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                cpassword = edtCpaaword.getText().toString().trim();
                code = edtCode.getText().toString().trim();


                if (name.length() == 0 )
                {
                    Toast.makeText(this, "Enter your name ", Toast.LENGTH_SHORT).show();

                }else  if(email.length() == 0)
                {
                    Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();

                }else  if(password.length() == 0)
                {
                    Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();

                }else if (!password.equals(cpassword))
                {

                    if (cpassword.length() == 0)
                    {
                        Toast.makeText(this, "Enter confirm  password", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();

                    }

                }else  if(mobile.length() == 0)
                {
                    Toast.makeText(this, "Enter mobile Number", Toast.LENGTH_SHORT).show();
                }else  if(code.length() == 0)
                {
                    Toast.makeText(this, "Enter code", Toast.LENGTH_SHORT).show();
                }else {
                    if (!Validator.isValidEmailId(edtEmail.getText().toString()))
                    {
                        Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        hideInputSoftKey();
                        callForRegistration();
                    }


/*

                    if (code.equals("1"))
                    {
                        Intent registerIntent = new Intent(this, Category_SchoolActivity.class);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (code.equals("2"))
                    {
                        Intent registerIntent = new Intent(this, Category_CollegeActivity.class);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (code.equals("3"))
                    {
                        Intent registerIntent = new Intent(this, Category_TuitionActivity.class);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (code.equals("4"))
                    {
                        Intent registerIntent = new Intent(this, Category_TestPreparationActivity.class);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (code.equals("5"))
                    {
                        Intent registerIntent = new Intent(this, Category_TrainingActivity.class);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (code.equals("6"))
                    {
                        Intent registerIntent = new Intent(this, Category_LanguageActivity.class);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (code.equals("7"))
                    {
                        Intent registerIntent = new Intent(this, Category_MusicActivity.class);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (code != null)
                    {
                       // callForRegistration();

                         Intent registerIntent = new Intent(this, Category_SchoolActivity.class);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);


                        //Toast.makeText(this, "If you have no code then make payment", Toast.LENGTH_SHORT).show();
                    }

*/


                  //  Log.e("=== Recapp : Register", "Nmae : " + name + "\t Email :" + email + "\t Mobile :" + mobile + "\t Password : " + password + "\t cPassword : " + cpassword+"\t code : "+code);

                }
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
    public void callForCategoryList()
    {
        CommunicationHandler.getInstance(this).callForCategoryList(this);
    }
    public void callForRegistration()
    {
        Apphelper.getInstance().showProgressDialog(this,true,null);

        HashMap<String, String> params = new HashMap<>();
        params.put("name", edtName.getText().toString());
        params.put("email", edtEmail.getText().toString());
        params.put("password", edtPassword.getText().toString());
        params.put("cpassword", edtCpaaword.getText().toString());
        params.put("mobile", edtMobile.getText().toString());
        params.put("process_code", edtCode.getText().toString());
        params.put("payment_status", paymentStatus);

        Logger.putLogDebug("RegistrationParams ==> ",params.toString());
        CommunicationHandler.getInstance(RegisterActivity.this).callForRegistarion(RegisterActivity.this, params);

    }

    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
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

                        System.out.println("===School id : "+subCategoryId);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (categoryType.equals("Tution")) {
                        Intent registerIntent = new Intent(this, Category_TuitionActivity.class);
                        registerIntent.putExtra("SubCategoryName", subCategoryName);
                        registerIntent.putExtra("SubCategoryId", subCategoryId);

                        System.out.println("===Institude id : "+subCategoryId);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);
                    }else if (categoryType.equals("College")) {
                        Intent registerIntent = new Intent(this, Category_CollegeActivity.class);
                        registerIntent.putExtra("SubCategoryName", subCategoryName);
                        registerIntent.putExtra("SubCategoryId", subCategoryId);

                        System.out.println("===College id : "+subCategoryId);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);
                    }else if (categoryType.equals("Test Prepare")) {
                        Intent registerIntent = new Intent(this, Category_TestPreparationActivity.class);
                        registerIntent.putExtra("SubCategoryName", subCategoryName);
                        registerIntent.putExtra("SubCategoryId", subCategoryId);

                        System.out.println("===Test Prep id : "+subCategoryId);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (categoryType.equals("Training")) {
                        Intent registerIntent = new Intent(this, Category_TrainingActivity.class);
                        registerIntent.putExtra("SubCategoryName", subCategoryName);
                        registerIntent.putExtra("SubCategoryId", subCategoryId);

                        System.out.println("===Training id : "+subCategoryId);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);
                    }else if (categoryType.equals("Language")) {
                        Intent registerIntent = new Intent(this, Category_LanguageActivity.class);
                        registerIntent.putExtra("SubCategoryName", subCategoryName);
                        registerIntent.putExtra("SubCategoryId", subCategoryId);

                        System.out.println("===Language id : "+subCategoryId);
                        startActivity(registerIntent);
                        finish();
                        overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                    }else if (categoryType.equals("Music")) {
                        Intent registerIntent = new Intent(this, Category_MusicActivity.class);
                        registerIntent.putExtra("SubCategoryName", subCategoryName);
                        registerIntent.putExtra("SubCategoryId", subCategoryId);

                        System.out.println("===Music id : "+subCategoryId);
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

        }else if (mMethod.equals(Const.CATEGORY_LIST))
        {
            Logger.putLogInfo("CategoryResponse ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                categoryList.addAll(dataList.getCategoryList());

                for (int i= 0 ; i < categoryList.size(); i++)
                {
                   String category_name =  categoryList.get(i).getCategoryName();


                }

            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }
        }

    }

    @Override
    public void failureCBWithMethod(String mMethod, String mError)
    {
        Logger.putLogError("RegisterResponse ==>" , mError.toString());


    }

    @Override
    public void cacheCBWithMethod(String mMethod, String mResponse)
    {
        Logger.putLogError("RegisterResponse ==>" , mResponse.toString());
    }

}
