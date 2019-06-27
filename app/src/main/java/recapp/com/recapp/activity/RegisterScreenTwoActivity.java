package recapp.com.recapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;

public class RegisterScreenTwoActivity extends AppCompatActivity implements View.OnClickListener  ,CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnBack , btnRegister;
    EditText edtRollNo,edtTime,edtSubject,edtSchoolName,edtCountry , edtBatch;
    String rollNo,timings ,subject, schoolName , country,batch;
    List<String> timeNameList;
    List<String> subjectNameList;
    List<String> batchNameList;
    List<BatchDataModel> batchList;
    List<BatchDataModel> timeList;
    List<BatchDataModel> subjectList;

    String selectedBatchID = null;
    String selectedTimeID = null;
    String selectedSubjectID = null;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen_two);
        timeList = new ArrayList<>();
        timeNameList = new ArrayList<>();
        subjectList = new ArrayList<>();
        subjectNameList = new ArrayList<>();
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

        prepareData();

        hideInputSoftKey();

        edtBatch.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        System.out.println("===list ==="+batchNameList);

        final ArrayAdapter<String> batchArray = new  ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, batchNameList);
        {
            edtBatch.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    hideInputSoftKey();
                    new AlertDialog.Builder(RegisterScreenTwoActivity.this)
                            .setAdapter(batchArray, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    edtBatch.setText(batchNameList.get(which));
                                    selectedBatchID = batchList.get(which).getBatchId();
                                    System.out.println("===Selected batch id ==> "+selectedBatchID);

                                    callForSubjectList();
                                    callForTimingsList();
                                    dialog.dismiss();


                                }
                            }).create().show();
                }
            });
        }

        edtTime.setInputType(InputType.TYPE_NULL);

        System.out.println("===list ==="+timeNameList);
        final ArrayAdapter<String> spinner_breed = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, timeNameList);
        {
            edtTime.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    if (edtBatch.getText().toString().length() == 0) {

                        Toast.makeText(RegisterScreenTwoActivity.this, "Select Batch", Toast.LENGTH_SHORT).show();
                    }else {
                        hideInputSoftKey();
                        new AlertDialog.Builder(RegisterScreenTwoActivity.this)
                                .setAdapter(spinner_breed, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        edtTime.setText(timeNameList.get(which));
                                        selectedTimeID = timeList.get(which).getBatchId();
                                        dialog.dismiss();

                                    }
                                }).create().show();
                    }
                }
            });
        }

        edtSubject.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        System.out.println("===list ==="+subjectNameList);

        final ArrayAdapter<String> spinner_bre = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, subjectNameList);
        {
            edtSubject.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {


                    hideInputSoftKey();
                    new AlertDialog.Builder(RegisterScreenTwoActivity.this)
                            .setAdapter(spinner_bre, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    edtSubject.setText(subjectNameList.get(which));
                                    selectedSubjectID = subjectList.get(which).getBatchId();
                                    dialog.dismiss();

                                }
                            }).create().show();

                }
            });
        }

    }
    public void prepareData()
    {
      /*  timeList = new ArrayList<>();
        timeList.add("9:00am to 11:00am");
        timeList.add("11:00am to 1:00pm");
        timeList.add("1:00pm to 3:00pm");
        timeList.add("4:00am to 6:00pm");

        subjectList = new ArrayList<>();
        subjectList.add("Science");
        subjectList.add("Maths");
        subjectList.add("Physics");
        subjectList.add("Social Science");*/

     /*   batchStaticList = new ArrayList<>();
        batchStaticList.add("Batch 1");
        batchStaticList.add("Batch 2");
        batchStaticList.add("Batch 3");
        batchStaticList.add("Batch 4");*/

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

        callForBatchList();
        edtCountry.setText("India");
        edtSchoolName.setText("Paradise Lost");
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

                }else  if (timings.length() == 0)
                {
                    Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();

                }else  if (subject.length() == 0)
                {
                    Toast.makeText(this, "Select subject", Toast.LENGTH_SHORT).show();

                }else  if (schoolName.length() == 0)
                {

                    Toast.makeText(this, "Enter school name", Toast.LENGTH_SHORT).show();

                }else  if (edtBatch.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Select batch name", Toast.LENGTH_SHORT).show();

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


                    Intent registerIntent = new Intent(this , LoginActivity.class);
                    startActivity(registerIntent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);
                    Log.e("===Recapp : Tution Register ","RollNo :- "+rollNo+"\t Timeings :-"+timings+"\t Subject :- "+subject+"\t School Name :-"+schoolName+"\t Country :-"+country);
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
        batchList = new ArrayList<>();
        batchNameList = new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", selectedBatchID);
       // CommunicationHandler.getInstance(this).callForBatch(this,params);
    }

    public void callForTimingsList()
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("batch_id", selectedBatchID);
      //  CommunicationHandler.getInstance(this).callForGetTiminingsList(this , params);
    }

    public void callForSubjectList()
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("batch_id", selectedBatchID);
       // CommunicationHandler.getInstance(this).callForGetSubjectList(this,params);
    }
    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        if (mMethod.equals(Const.BATCH_LIST)) {
            Logger.putLogInfo("BatchResponse ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                batchList.addAll(dataList.getBatchList());

                if (batchList != null && !batchList.isEmpty())
                {
                    for (int i =0 ; i < batchList.size(); i++)
                    {
                        batchNameList.add(batchList.get(i).getBatchName());

                    }
                }

            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }
        }else   if (mMethod.equals(Const.BATCH_TIMINIGS_LIST)) {
            Logger.putLogInfo("Batch_Time_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                timeList.addAll(dataList.getTiminingsList());

                if (timeList != null && !timeList.isEmpty())
                {
                    for (int i =0 ; i < timeList.size(); i++)
                    {
                        timeNameList.add(timeList.get(i).getBatchTime());

                    }
                    Logger.putLogInfo("Batch_Time_List ==>", timeNameList.toString());

                }
            } catch (JSONException aE) {
                aE.printStackTrace();
            }
        }else if (mMethod.equals(Const.BATCH_SUBJECT_LIST)) {
            Logger.putLogInfo("Batch_Subject_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                subjectList.addAll(dataList.getSubjectsList());

                if (subjectList != null && !subjectList.isEmpty())
                {
                    for (int i =0 ; i < subjectList.size(); i++)
                    {
                        subjectNameList.add(subjectList.get(i).getBatchSubject());

                    }
                    Logger.putLogInfo("Batch_Time_List==>", subjectNameList.toString());

                }
            } catch (JSONException aE) {
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
}
