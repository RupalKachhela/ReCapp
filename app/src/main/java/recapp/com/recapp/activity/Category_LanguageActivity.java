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

public class Category_LanguageActivity extends AppCompatActivity implements View.OnClickListener  ,CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnBack , btnRegister;
    EditText edtRollNo,edtTime,edtSubject,edtSchoolName,edtCountry , edtBatch , edtLevel  , edtLanguage;
    String rollNo,timings ,subject, schoolName , country,batch;
    CardView card_class , card_batch , card_time , card_level , card_subject;

    List<BatchDataModel> languageList;
    List<BatchDataModel> levelList;
    List<BatchDataModel> batchList;
    List<BatchDataModel> timingList;

    List<String> languageNameList;
    List<String> levelNameList;
    List<String> batchNameList;
    List<String> timingNameList;

    String selectedLanguageID = null;
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

        languageList = new ArrayList<>();
        levelList = new ArrayList<>();
        batchList = new ArrayList<>();
        timingList = new ArrayList<>();

        languageNameList = new ArrayList<>();
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
            System.out.println("===Sub CategoryId Language Activity: "+subCategoryID);

        }
        callForLanguageList();

        hideInputSoftKey();

        System.out.println("===language list ==="+languageNameList);

        edtLanguage.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        final ArrayAdapter<String> batchArray = new  ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, languageNameList);
        {
            edtLanguage.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    hideInputSoftKey();
                    if (!languageNameList.isEmpty() && languageNameList != null) {
                        new AlertDialog.Builder(Category_LanguageActivity.this)
                                .setAdapter(batchArray, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                        edtLanguage.setText(languageNameList.get(which));
                                        selectedLanguageID = languageList.get(which).getLanguageId();
                                        System.out.println("===Selected language id ==> " + selectedLanguageID);

                                        if (!edtBatch.getText().toString().isEmpty()) {
                                            edtBatch.setText("");
                                            try {
                                                batchList.clear();
                                                batchNameList.clear();
                                            }
                                            catch (NullPointerException e)
                                            {
                                                e.printStackTrace();
                                            }
//                                            if(batchList!=null)
//                                            {
//                                                batchList.clear();
//                                            }
//                                            else if(batchNameList!=null)
//                                            {
//                                                batchNameList.clear();
//                                            }


                                            callForBatchList(selectedLanguageID);
                                        }

                                        if (!edtTime.getText().toString().isEmpty()) {
                                            edtTime.setText("");
//                                            if(timingList!=null)
//                                            {
//                                                timingList.clear();
//                                            }
//                                            else if(timingNameList!=null)
//                                            {
//                                                timingNameList.clear();
//                                            }

                                            try {
                                                timingList.clear();
                                                timingNameList.clear();
                                            }
                                            catch (NullPointerException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            callForBatchList(selectedLanguageID);
                                        }

                                        dialog.dismiss();


                                    }
                                }).create().show();
                    }else {
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_LanguageActivity.this).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getResources().getString(R.string.app_name));
                        alertDialog.setMessage("Language not found");
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
        edtLanguage = findViewById(R.id.edt_class_tutionRegister);
        edtLevel = findViewById(R.id.edt_level_tutionRegister);
        card_class = findViewById(R.id.card_class_register);
        card_batch = findViewById(R.id.card_batch_register);
        card_time = findViewById(R.id.card_timing_register);
        card_level = findViewById(R.id.card_level_register);

        card_batch.setVisibility(View.VISIBLE);
        card_class.setVisibility(View.VISIBLE);
        card_level.setVisibility(View.GONE);
        card_time.setVisibility(View.VISIBLE);

        edtLanguage.setHint("Language");
        edtLevel.setHint("Level");

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
                timings = edtLanguage.getText().toString().trim();
                timings = edtLevel.getText().toString().trim();
                batch = edtBatch.getText().toString().trim();
                timings = edtTime.getText().toString().trim();
                country = edtCountry.getText().toString().trim();
                schoolName = edtSchoolName.getText().toString().trim();

                if (edtRollNo.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Enter Roll number", Toast.LENGTH_SHORT).show();

                }else  if (edtLanguage.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select language", Toast.LENGTH_SHORT).show();

                }/*else  if (edtLevel.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select level", Toast.LENGTH_SHORT).show();

                }*/else  if (edtBatch.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Select batch", Toast.LENGTH_SHORT).show();

                }else  if (edtTime.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();

                }else  if (edtSchoolName.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Enter language center name", Toast.LENGTH_SHORT).show();

                }else  if (edtCountry.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Enter country", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    hideInputSoftKey();
//                    Intent registerIntent = new Intent(this , RegisterScreenThreeActivity.class);
//                    startActivity(registerIntent);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);
                    System.out.println("=====Language Register Collect Data : "+"\n"+ rollNo +"\n"+selectedLanguageID+"\n"+selectedLevelID+"\n"+selectedBatchID+"\n"+selectedTimeID+"\n"+schoolName+"\n"+country );

                    callForLanguageRegister(rollNo , selectedLanguageID , selectedLevelID , selectedBatchID , selectedTimeID , schoolName , country);

                    Intent registerIntent = new Intent(this , LoginActivity.class);
                    registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(registerIntent);
                    finish();

                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);


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


    public void callForLanguageList()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        CommunicationHandler.getInstance(this).callForLanguagelanguageList(this,params);
    }

    public void callForLevelList()
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("batch_id", selectedLanguageID);
        CommunicationHandler.getInstance(this).callForLanguageLevelList(this , params);
    }
    public void callForBatchList(String selectedLanguageID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("language_id", selectedLanguageID);
        CommunicationHandler.getInstance(this).callForLanguageBatchList(this , params);
    }
    public void callForTimeList(String selectedBatchID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("batch_id", selectedBatchID);

        CommunicationHandler.getInstance(this).callForLanguageTimeList(this , params);
    }

    public void callForLanguageRegister(String rollNo , String languageId , String levelId , String batchId , String timeId , String centerName , String country)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("category_name",subCategoryName);
        params.put("subcategory_id",subCategoryID);
        params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID) );
        params.put("rollno", rollNo);
        params.put("language_id", languageId);
       // params.put("subject_id", levelId);
        params.put("batch_id", batchId);
        params.put("timing_id", timeId);
        params.put("school_name", centerName);
        params.put("country", country);

        System.out.println("===Language Center Register  param : "+params);

        CommunicationHandler.getInstance(this).callForLanguageRegister(this , params);
    }
    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        if (mMethod.equals(Const.COLLEGE_REG)) {

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
                            Intent registerIntent = new Intent(Category_LanguageActivity.this , LoginActivity.class);
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

        if (mMethod.equals(Const.LANGUAGE_LANGUAGE_LIST)) {

            Logger.putLogInfo("LANGUAGE_LANGUAGE_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    languageList.clear();
                    languageNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }


                languageList.addAll(dataList.getLanguagesList());

                if (languageList != null && !languageList.isEmpty())
                {
                    for (int i =0 ; i < languageList.size(); i++)
                    {
                        languageNameList.add(languageList.get(i).getLanguageName());

                    }
                }



                System.out.println("=== batch list ==="+batchNameList);

                final ArrayAdapter<String> spinner_bre = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, batchNameList);
                {
                    edtBatch.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtLanguage.getText().toString().length() == 0) {

                                Toast.makeText(Category_LanguageActivity.this, "Select Language", Toast.LENGTH_SHORT).show();

                            }else {
                                hideInputSoftKey();
                                if (!batchNameList.isEmpty() && batchNameList !=null) {

                                    new AlertDialog.Builder(Category_LanguageActivity.this)
                                        .setAdapter(spinner_bre, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                    edtBatch.setText(batchNameList.get(which));
                                                    selectedBatchID = batchList.get(which).getTestPrepBatchId();

                                                    if (!edtTime.getText().toString().isEmpty()) {
                                                        edtTime.setText("");
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
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_LanguageActivity.this).create();
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
        }else   if (mMethod.equals(Const.LANGUAGE_LEVEL_LIST)) {
            Logger.putLogInfo("LANGUAGE_LEVEL_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    levelList.clear();
                    levelNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }


                levelList.addAll(dataList.getTiminingsList());

                if (levelList != null && !levelList.isEmpty())
                {
                    for (int i =0 ; i < levelList.size(); i++)
                    {
                        levelNameList.add(levelList.get(i).getBatchTime());

                    }
                    Logger.putLogInfo("LANGUAGE_LEVEL_LIST ==>", levelNameList.toString());

                }


            } catch (JSONException aE) {
                aE.printStackTrace();
            }
        }else if (mMethod.equals(Const.LANGUAGE_BATCH_LIST)) {
            Logger.putLogInfo("LANGUAGE_BATCH_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
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


                batchList.addAll(dataList.getLanguagesBatchList());

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
                    edtTime.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtBatch.getText().toString().length() == 0) {

                                Toast.makeText(Category_LanguageActivity.this, "Select batch", Toast.LENGTH_SHORT).show();

                            }else {
                                hideInputSoftKey();
                                if (timingNameList != null && !timingNameList.isEmpty()) {
                                    new AlertDialog.Builder(Category_LanguageActivity.this)
                                            .setAdapter(spinner_section, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    edtTime.setText(timingNameList.get(which));
                                                    selectedTimeID = timingList.get(which).getTimeId();
                                                    dialog.dismiss();

                                                }
                                            }).create().show();
                                }else {
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_LanguageActivity.this).create();
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
        }else if (mMethod.equals(Const.LANGUAGE_TIME_LIST)) {
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


                timingList.addAll(dataList.getLanguagesTimeList());

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
