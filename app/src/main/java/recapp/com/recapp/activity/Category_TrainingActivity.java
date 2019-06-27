package recapp.com.recapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
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
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;

public class Category_TrainingActivity extends AppCompatActivity implements View.OnClickListener  ,CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnBack , btnRegister;
    EditText edtRollNo,edtTime,edtSubject,edtSchoolName,edtCountry , edtBatch;
    String rollNo,timings ,subject, schoolName , country,batch;
    CardView card_class , card_batch , card_time;

    List<String> batchNameList;
    List<BatchDataModel> batchList;

    String selectedBatchID = null;

    String subCategoryName= null;
    String subCategoryID= null;

    int result;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen_two);
        batchList = new ArrayList<>();
        batchNameList = new ArrayList<>();
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }

        init();

        if (getIntent() != null)
        {
            Bundle b =  getIntent().getExtras();
            if (b != null)
            {
                subCategoryName = b.getString("SubCategoryName");
                subCategoryID = b.getString("SubCategoryId");
               // subCategoryID ="1";
                edtSchoolName.setText(subCategoryName);

            }
            System.out.println("===Sub CategoryId Test prep Activity: "+subCategoryID);

        }
        callForBatchList();

        hideInputSoftKey();


        System.out.println("=== batch list ==="+batchNameList);

        final ArrayAdapter<String> batchArray = new  ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, batchNameList);
        {
            edtBatch.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    hideInputSoftKey();

                    if (batchNameList != null && !batchNameList.isEmpty()) {
                        new AlertDialog.Builder(Category_TrainingActivity.this)
                                .setAdapter(batchArray, new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        edtBatch.setText(batchNameList.get(which));
                                        selectedBatchID = batchList.get(which).getTestPrepBatchId();
                                        System.out.println("===Selected batch id ==> "+selectedBatchID);
                                        dialog.dismiss();


                                    }
                                }).create().show();
                    }else {
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_TrainingActivity.this).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getResources().getString(R.string.app_name));
                        alertDialog.setMessage("Batch not found");
                        alertDialog.setIcon(R.drawable.rc_logo);
                        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface aDialogInterface, int aI) {
                                aDialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }
            });
        }


    }


    public void init()
    {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        btnRegister = findViewById(R.id.btn_tutionRegister);
        edtRollNo = findViewById(R.id.edt_rollNo_tutionRegister);
        edtTime = findViewById(R.id.edt_time_tutionRegister);
        edtSubject = findViewById(R.id.edt_subject_tutionRegister);
        edtSchoolName = findViewById(R.id.edt_schoolName_tutionRegister);
        edtCountry = findViewById(R.id.edt_country_tutionRegister);
        edtBatch = findViewById(R.id.edt_batch_tutionRegister);
        card_class = findViewById(R.id.card_class_register);
        card_batch = findViewById(R.id.card_batch_register);
        card_time = findViewById(R.id.card_timing_register);


        card_batch.setVisibility(View.VISIBLE);
        edtCountry.setText("India");
        btnBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @SuppressLint("LongLogTag")
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
            case R.id.btn_tutionRegister:
            {
                rollNo = edtRollNo.getText().toString().trim();
                timings = edtTime.getText().toString().trim();
                subject = edtSubject.getText().toString().trim();
                country = edtCountry.getText().toString().trim();
                schoolName = edtSchoolName.getText().toString().trim();
                batch = edtBatch.getText().toString().trim();

                if (rollNo.length() == 0)
                {
                    Toast.makeText(this, "Enter Roll number", Toast.LENGTH_SHORT).show();

                }else  if (edtBatch.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Select batch name", Toast.LENGTH_SHORT).show();

                }else  if (edtSchoolName.getText().toString().length() == 0) {

                    Toast.makeText(this, "Enter Training center name", Toast.LENGTH_SHORT).show();

                }else  if (country.length() == 0)
                {

                    Toast.makeText(this, "Enter Country", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    hideInputSoftKey();
//                    Intent registerIntent = new Intent(this , RegisterScreenThreeActivity.class);
//                    startActivity(registerIntent);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);

                    System.out.println("==== Training collection data : "+"\n"+rollNo+"\n"+selectedBatchID+"\n"+schoolName+"\n"+country);

                    callForTrainingRegister(rollNo , selectedBatchID , schoolName , country);

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

    public void callForBatchList()
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        CommunicationHandler.getInstance(this).callForTrainingBatchList(this,params);    }

    public void callForTrainingRegister(String rollNo , String batchId ,String className , String country)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("category_name",subCategoryName);
        params.put("subcategory_id",subCategoryID);
        params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID) );
        params.put("rollno", rollNo);
        params.put("batch_id", batchId);
        params.put("school_name", className);
        params.put("country", country);

        System.out.println("===Training Register  param : "+params);

        CommunicationHandler.getInstance(this).callForTrainingRegister(this , params);
    }
    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        if (mMethod.equals(Const.TRAINING_REG)) {

            Logger.putLogInfo("collegeRegistration ==>", jsonObject.toString());

            try {
                result = jsonObject.getInt("Result");
                String msg = jsonObject.getString("Message");

                if (result == 1) {
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                    alertDialog.setMessage(msg);
                    alertDialog.setIcon(R.drawable.rc_logo);
                    alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface aDialogInterface, int aI) {
                            Intent registerIntent = new Intent(Category_TrainingActivity.this , LoginActivity.class);
                            registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(registerIntent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);
                            aDialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();

                } else {
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                    alertDialog.setMessage(msg);
                    alertDialog.setIcon(R.drawable.rc_logo);
                    alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
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

        if (mMethod.equals(Const.TRAINING_BATCH_LIST)) {
            Logger.putLogInfo("BatchResponse ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    batchList.clear();
                    batchNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
//                if(batchList!=null)
//                {
//                    batchList.clear();
//                }
//                else if(batchNameList!=null)
//                {
//                    batchNameList.clear();
//                }


                batchList.addAll(dataList.getTrainingBatchList());

                if (batchList != null && !batchList.isEmpty())
                {
                    for (int i =0 ; i < batchList.size(); i++)
                    {
                        batchNameList.add(batchList.get(i).getTestPrepBatchName());
                    }
                }



            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }
        }

    }

    @Override
    public void failureCBWithMethod(String mMethod, String mError) {
        Logger.putLogError("BatchResponse ==>", mError);

    }

    @Override
    public void cacheCBWithMethod(String mMethod, String mResponse) {
        Logger.putLogError("BatchResponse ==>", mResponse);

    }

    @Override
    public void onBackPressed() {

        if(result != 1)
        {
            Toast.makeText(getApplicationContext(),"Please Complete Your registration",Toast.LENGTH_SHORT).show();
        }

        else
        {
            finish();
        }
    }
}
