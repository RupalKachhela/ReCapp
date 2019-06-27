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

public class Category_MusicActivity extends AppCompatActivity implements View.OnClickListener  ,CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnBack , btnRegister;
    EditText edtRollNo,edtTime,edtSubject,edtSchoolName,edtCountry , edtBatch ,edtInstrument , edtLevel;
    String rollNo,timings ,subject, schoolName , country,batch;
    CardView card_class , card_batch , card_time , card_level , card_subject;


    List<BatchDataModel> instrumentList;
    List<BatchDataModel> levelList;
    List<BatchDataModel> batchList;
    List<BatchDataModel> timingList;

    List<String> instrumentNameList;
    List<String> levelNameList;
    List<String> batchNameList;
    List<String> timingNameList;

    String selectedInstrumentID = null;
    String selectedLevelID = null;
    String selectedBatchID = null;
    String selectedTimeID = null;

    String subCategoryName= null;
    String subCategoryID= null;

    int result;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen_two);


        instrumentList = new ArrayList<>();
        levelList = new ArrayList<>();
        batchList = new ArrayList<>();
        timingList = new ArrayList<>();

        instrumentNameList = new ArrayList<>();
        levelNameList = new ArrayList<>();
        batchNameList = new ArrayList<>();
        timingNameList = new ArrayList<>();

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
                //subCategoryID ="1";
                edtSchoolName.setText(subCategoryName);

            }
            System.out.println("===Sub CategoryId Music Activity: "+subCategoryID);

        }
        callForMusicInstrumentList();

        hideInputSoftKey();


            final ArrayAdapter<String> batchArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, instrumentNameList);
            {
                edtInstrument.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        hideInputSoftKey();
                        new AlertDialog.Builder(Category_MusicActivity.this)
                                .setAdapter(batchArray, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (!instrumentNameList.isEmpty() && instrumentNameList != null) {

                                            edtInstrument.setText(instrumentNameList.get(which));
                                            selectedInstrumentID = instrumentList.get(which).getInstrumentId();
                                            System.out.println("===Selected instrument id ==> " + selectedInstrumentID);

                                            if (!edtBatch.getText().toString().isEmpty())
                                            {
                                                edtBatch.setText("");
//                                                if(batchList!=null)
//                                                {
//                                                    batchList.clear();
//                                                }
//                                                else if(batchNameList!=null)
//                                                {
//                                                    batchNameList.clear();
//                                                }
                                                try {
                                                    batchList.clear();
                                                    batchNameList.clear();
                                                }
                                                catch (NullPointerException e)
                                                {
                                                    e.printStackTrace();
                                                }


                                                callForBatchList(selectedInstrumentID);
                                            }

                                            if (!edtTime.getText().toString().isEmpty()) {
                                                edtTime.setText("");
//                                                if(timingList!=null)
//                                                {
//                                                    timingList.clear();
//                                                }
//                                                else if(timingNameList!=null)
//                                                {
//                                                    timingNameList.clear();
//                                                }

                                                try
                                                {
                                                    timingList.clear();
                                                    timingNameList.clear();

                                                }
                                                catch (NullPointerException e)
                                                {}


                                            } else {
                                                callForBatchList(selectedInstrumentID);
                                            }
                                        }else {
                                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getApplicationContext()).create();
                                            alertDialog.setCancelable(false);
                                            alertDialog.setTitle(getResources().getString(R.string.app_name));
                                            alertDialog.setMessage("Instrument not found");
                                            alertDialog.setIcon(R.drawable.rc_logo);
                                            alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface aDialogInterface, int aI) {
                                                    aDialogInterface.dismiss();
                                                }
                                            });
                                            alertDialog.show();
                                        }

                                        dialog.dismiss();

                                    }
                                }).create().show();
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
        edtInstrument = findViewById(R.id.edt_class_tutionRegister);
        edtLevel = findViewById(R.id.edt_level_tutionRegister);
        edtSchoolName = findViewById(R.id.edt_schoolName_tutionRegister);
        edtCountry = findViewById(R.id.edt_country_tutionRegister);
        edtBatch = findViewById(R.id.edt_batch_tutionRegister);
        card_class = findViewById(R.id.card_class_register);
        card_batch = findViewById(R.id.card_batch_register);
        card_time = findViewById(R.id.card_timing_register);
        card_level = findViewById(R.id.card_level_register);
        card_subject = findViewById(R.id.card_subject_register);

        card_batch.setVisibility(View.VISIBLE);
        card_level.setVisibility(View.GONE);
        card_class.setVisibility(View.VISIBLE);
        card_time.setVisibility(View.VISIBLE);
        card_subject.setVisibility(View.GONE);


        edtInstrument.setHint("Instrument");
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

                if ( edtRollNo.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Enter Roll number", Toast.LENGTH_SHORT).show();

                }else  if ( edtInstrument.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select Instrument", Toast.LENGTH_SHORT).show();

                }else  if ( edtBatch.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select Batch", Toast.LENGTH_SHORT).show();

                }else  if (edtTime.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();

                }else  if (edtSchoolName.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select Music center", Toast.LENGTH_SHORT).show();

                }else  if ( edtRollNo.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Enter Country", Toast.LENGTH_SHORT).show();

                }
                else
                {

                    hideInputSoftKey();

                    System.out.println("=====Language Register Collect Data : "+"\n"+ rollNo +"\n"+selectedInstrumentID+"\n"+selectedLevelID+"\n"+selectedBatchID+"\n"+selectedTimeID+"\n"+schoolName+"\n"+country );

                    callForMusicRegister(rollNo , selectedInstrumentID , selectedBatchID , selectedTimeID , schoolName , country);

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
    public void callForMusicInstrumentList()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        CommunicationHandler.getInstance(this).callForMusicInstrumentList(this,params);
    }

    public void callForBatchList(String selectedInstrumentID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("instrument_id", selectedInstrumentID);
        CommunicationHandler.getInstance(this).callForMusicBatchList(this , params);
    }

    public void callForTimeList(String selectedBatchID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("batch_id", selectedBatchID);

        CommunicationHandler.getInstance(this).callForMusicTimeList(this , params);
    }

    public void callForMusicRegister(String rollNo , String instrumentId, String batchId , String timeId , String centerName , String country)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("category_name",subCategoryName);
        params.put("subcategory_id",subCategoryID);
        params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID) );
        params.put("rollno", rollNo);
        params.put("instrument_id", instrumentId);
        params.put("batch_id", batchId);
        params.put("timing_id", timeId);
        params.put("school_name", centerName);
        params.put("country", country);

        System.out.println("===Music Center Register  param : "+params);

        CommunicationHandler.getInstance(this).callForMusicRegister(this , params);
    }
    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        if (mMethod.equals(Const.MUSIC_REG)) {

            Logger.putLogInfo("musicRegistration ==>", jsonObject.toString());

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
                            Intent registerIntent = new Intent(Category_MusicActivity.this , LoginActivity.class);
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

        if (mMethod.equals(Const.MUSIC_INSTRUMENT_LIST)) {

            Logger.putLogInfo("LANGUAGE_LANGUAGE_LIST_Response ==>", jsonObject.toString());

            String msg = null;
            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                msg = dataList.getMessage();
//                if(instrumentList!=null)
//                {
//                    instrumentList.clear();
//                }
//                else if(instrumentNameList!=null)
//                {
//                    instrumentNameList.clear();
//                }
                try {
                    instrumentList.clear();
                    instrumentNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }


                instrumentList.addAll(dataList.getMusicInstrumentList());

                if (instrumentList != null && !instrumentList.isEmpty())
                {
                    for (int i =0 ; i < instrumentList.size(); i++)
                    {
                        instrumentNameList.add(instrumentList.get(i).getInstrumentName());
                    }
                }

                else {
                    Toast.makeText(getApplicationContext(),dataList.getMessage(),Toast.LENGTH_SHORT).show();
                }

                System.out.println("=== batch list ==="+batchNameList);

                final ArrayAdapter<String> spinner_bre = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, batchNameList);
                {
                    final String finalMsg = msg;
                    edtBatch.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtInstrument.getText().toString().length() == 0) {

                                Toast.makeText(Category_MusicActivity.this, "Select Instrument", Toast.LENGTH_SHORT).show();

                            }else {
                                hideInputSoftKey();
                                if (!batchNameList.isEmpty() && batchNameList != null) {
                                    new AlertDialog.Builder(Category_MusicActivity.this)
                                            .setAdapter(spinner_bre, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    edtBatch.setText(batchNameList.get(which));
                                                    selectedBatchID = batchList.get(which).getTestPrepBatchId();


                                                    if (!edtTime.getText().toString().isEmpty())
                                                    {
                                                        edtTime.setText("");
//                                                        if(timingList!=null)
//                                                        {
//                                                            timingList.clear();
//                                                        }
//                                                        else if (timingNameList!=null)
//                                                        {
//                                                            timingNameList.clear();
//                                                        }
                                                        try {
                                                           timingList.clear();
                                                           timingNameList.clear();
                                                        }
                                                        catch (NullPointerException e)
                                                        {
                                                            e.printStackTrace();
                                                        }


                                                        callForTimeList(selectedBatchID);
                                                    } else {
                                                        callForTimeList(selectedBatchID);
                                                    }


                                                    dialog.dismiss();

                                                }
                                            }).create().show();
                                }else {
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_MusicActivity.this).create();
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

                        }
                    });
                }

                System.out.println("=== section list ==="+timingNameList);


            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }
        }else if (mMethod.equals(Const.MUSIC_BATCH_LIST)) {
            Logger.putLogInfo("MUSIC_BATCH_LIST_Response ==>", jsonObject.toString());
             String finalMsg = null;

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                finalMsg = dataList.getMessage();
//                if(batchList!=null)
//                {
//                    batchList.clear();
//                }
//                else if(batchNameList!=null)
//                {
//                    batchNameList.clear();
//                }

                try {

                    batchList.clear();
                    batchNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

                batchList.addAll(dataList.getMusicBatchList());

                if (batchList != null && !batchList.isEmpty())
                {
                    for (int i =0 ; i < batchList.size(); i++)
                    {
                        batchNameList.add(batchList.get(i).getTestPrepBatchName());

                    }
                    Logger.putLogInfo("LANGUAGE_BATCH_LIST==>", batchNameList.toString());

                }


                final ArrayAdapter<String> spinner_section = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, timingNameList);
                {
                    final String finalMsg1 = finalMsg;
                    edtTime.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtBatch.getText().toString().length() == 0) {

                                Toast.makeText(Category_MusicActivity.this, "Select batch", Toast.LENGTH_SHORT).show();

                            }else {
                                hideInputSoftKey();
                                if (!timingNameList.isEmpty() && timingNameList != null)
                                {
                                new AlertDialog.Builder(Category_MusicActivity.this)
                                        .setAdapter(spinner_section, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                edtTime.setText(timingNameList.get(which));
                                                selectedTimeID = timingList.get(which).getTimeId();
                                                dialog.dismiss();

                                            }
                                        }).create().show();


                                }else {
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_MusicActivity.this).create();
                                    alertDialog.setCancelable(false);
                                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                                    alertDialog.setMessage("Time not found");
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

                        }
                    });
                }

            } catch (JSONException aE) {
                aE.printStackTrace();
            }
        }else if (mMethod.equals(Const.MUSIC_TIME_LIST)) {
            Logger.putLogInfo("LANGUAGE_TIME_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
//                if(timingList!=null)
//                {
//                    timingList.clear();
//                }
//                else if(timingNameList!=null)
//                {
//                    timingNameList.clear();
//                }
                try {
                    timingList.clear();
                    timingNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }


                timingList.addAll(dataList.getMusicTimeList());

                if (timingList != null && !timingList.isEmpty())
                {
                    for (int i =0 ; i < timingList.size(); i++)
                    {
                        timingNameList.add(timingList.get(i).getBatchTime());

                    }
                    Logger.putLogInfo("LANGUAGE_TIME_LIST==>", timingNameList.toString());

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
