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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import recapp.com.recapp.CommunicationHandler.CommunicationHandler;
import recapp.com.recapp.R;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.CustomDialogBox;
import recapp.com.recapp.helper.Validator;
import recapp.com.recapp.model.LoginDataModel;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener,CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnLogin ,btnRegister;
    EditText edtUserName , edtPassword;
    String email , password;
    Button btnBack;
    private CustomDialogBox mProgressDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorTransparent));*/

//       getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
        hideInputSoftKey();

    }

    public void init()
    {
        btnLogin = findViewById(R.id.btn_login_Login);
        btnRegister = findViewById(R.id.btn_register_Login);
        edtUserName = findViewById(R.id.edt_userName_Login);
        edtPassword = findViewById(R.id.edt_password_Login);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    private void hideInputSoftKey()
    {
        try
        {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        }
        catch (Exception aE)
        {
            aE.printStackTrace();
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_login_Login :
            {

               if (edtUserName.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Enter UserName", Toast.LENGTH_SHORT).show();

                }else if (edtPassword.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();

                }else
                {
                    if (!Validator.isValidEmailId(edtUserName.getText().toString())) {
                        Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    hideInputSoftKey();
                    showProgressDialog();
                    callForLogin();

                   /* Intent registerIntent = new Intent(this,MainActivity.class);
                    startActivity(registerIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);

                    email = edtUserName.getText().toString();
                    password =edtPassword.getText().toString();
                    Log.e("===Recapp : Login","Email :- "+email+"\tPassword :-"+password);*/

                }

              /*  hideInputSoftKey();
                Intent registerIntent = new Intent(this,MainActivity.class);
                startActivity(registerIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);*/

                break;

            }

            case R.id.btn_register_Login :
            {
                hideInputSoftKey();
                Intent registerIntent = new Intent(this,RegisterActivity.class);
                startActivity(registerIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);

                break;
            }
        }

    }

    public void callForLogin()
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("email", edtUserName.getText().toString());
        params.put("password", edtPassword.getText().toString());

        CommunicationHandler.getInstance(LoginActivity.this).callForLogin(LoginActivity.this, params);

        System.out.println("====LOGIN PARAM : " + params);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {

            mProgressDialog = new CustomDialogBox(this, CustomDialogBox.DT_PROGRESS_MSG);
            mProgressDialog.setMessage(getResources().getString(R.string.progress_login));

        }
        mProgressDialog.show();
    }

    private void dismissProgressDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess) {

        System.out.println("====METHOD CALL : "+mMethod + "JSON RESPONSE :::  "+ jsonObject);
            try {

                dismissProgressDialog();
                if (mMethod.equals(Const.LOGIN))
                {


                int result = jsonObject.getInt("Result");
                String msg = jsonObject.optString("Message");
                if (result == 0)
                {

                    final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                    alertDialog.setMessage(msg);
                    alertDialog.setIcon(R.drawable.rc_logo);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface aDialogInterface, int aI)
                        {
                            aDialogInterface.dismiss();
                        }

                    });

                    alertDialog.show();
                    final Timer timer2 = new Timer();
                    timer2.schedule(new TimerTask() {
                        public void run() {
                            alertDialog.dismiss();
                            timer2.cancel(); //this will cancel the timer of the system
                        }
                    }, 3000); // the timer will count 3 seconds...

                }
                else
                {

                    LoginDataModel loginDataModel = new LoginDataModel(jsonObject);
                    RecappApplication.getInstance().setSPValueByKey(Const.RC_USER_EMAIL, loginDataModel.getEmail());
                    RecappApplication.getInstance().setSPValueByKey(Const.RC_USERNAME, loginDataModel.getName());
                    RecappApplication.getInstance().setSPValueByKey(Const.RC_USER_ID, String.valueOf(loginDataModel.getUserId()));
                    RecappApplication.getInstance().setSPValueByKey(Const.RC_PASSWORD, edtPassword.getText().toString());
                    RecappApplication.getInstance().setSPValueByKey(Const.RC_IS_LOGGED_IN, true);
                    RecappApplication.getInstance().setSPValueByKey(Const.IS_DOWNlODED, false);
                    RecappApplication.getInstance().setSPValueByKey(Const.RC_CATEGORY_NAME, loginDataModel.getCategoryName());
                    RecappApplication.getInstance().setSPValueByKey(Const.RC_PROCESS_CODE, loginDataModel.getProcesscode());
                    RecappApplication.getInstance().setSPValueByKey(Const.SUB_CATEGORY_ID, loginDataModel.getSub_CategoryId());

                    //callForUpdateLatLong(currentLatitude,currentLongitude);

                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                    alertDialog.setMessage(msg);
                    alertDialog.setIcon(R.drawable.rc_logo);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface aDialogInterface, int aI) {
                            aDialogInterface.dismiss();

                            Intent intent = new Intent();
                            //intent.setClass(getApplicationContext(), LetsTalkBusinessActivity.class);
                            intent.setClass(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    });
                    alertDialog.show();
                }
            }
        }

            catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    public void failureCBWithMethod(String mMethod, String mError) {
        dismissProgressDialog();
        if (mError != null && mError.startsWith("{")) {
            try {
                JSONObject jsonObject = new JSONObject(mError);
                if (jsonObject.has("Result") && jsonObject.optInt("Result") == 0) {
                    String message = jsonObject.optString("Message");
                    if (message != null && !message.isEmpty()) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle(getResources().getString(R.string.app_name));
                        alertDialog.setIcon(R.drawable.rc_logo);
                        alertDialog.setMessage(message);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface aDialogInterface, int aI) {
                                aDialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }
            } catch (JSONException aE) {
                aE.printStackTrace();
            }
        }

    }

    @Override
    public void cacheCBWithMethod(String mMethod, String mResponse) {

    }
}
