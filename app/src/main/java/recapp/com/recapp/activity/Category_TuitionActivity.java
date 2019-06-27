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

public class Category_TuitionActivity extends AppCompatActivity implements View.OnClickListener  ,CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnBack , btnRegister;
    EditText edtRollNo,edtTime,edtSubject,edtSchoolName,edtCountry , edtBatch ,edtClass;
    String rollNo,timings ,subject, schoolName , country,batch ,className;
    CardView card_class , card_batch , card_time;
    List<String> timeNameList;
    List<String> subjectNameList;
    List<String> batchNameList;
    List<String> classNameList;
    List<BatchDataModel> batchList;
    List<BatchDataModel> timeList;
    List<BatchDataModel> subjectList;
    List<BatchDataModel> classList;

    String selectedBatchID = null;
    String selectedTimeID = null;
    String selectedSubjectID = null;
    String selectedClassID = null;

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
        timeList = new ArrayList<>();
        timeNameList = new ArrayList<>();
        subjectList = new ArrayList<>();
        subjectNameList = new ArrayList<>();
        classList = new ArrayList<>();
        classNameList = new ArrayList<>();

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
                edtSchoolName.setText(subCategoryName);

            }
            System.out.println("===Sub CategoryName Scool Activity: "+subCategoryID);
        }

        callForClassList();

        hideInputSoftKey();

        edtClass.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        System.out.println("===list ==="+classNameList);

        final ArrayAdapter<String> spinner_class = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, classNameList);
        {
            edtClass.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    hideInputSoftKey();


                    if (classNameList != null && !classNameList.isEmpty()) {
                        new AlertDialog.Builder(Category_TuitionActivity.this)
                                .setAdapter(spinner_class, new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        edtClass.setText(classNameList.get(which));
                                        selectedClassID = classList.get(which).getTuitionClassId();

                                        System.out.println("====Select class id :---"+ classList.get(which).getTuitionClassId() == selectedClassID);

                                        if (!edtBatch.getText().toString().isEmpty()) {
                                            edtBatch.setText("");
//                                            if(batchList!=null)
//                                            {
//                                                batchList.clear();
//                                            }
//                                            else if(batchNameList!=null)
//                                            {
//                                                batchNameList.clear();
//                                            }
                                            try {

                                                batchList.clear();
                                                batchNameList.clear();
                                            }
                                            catch (NullPointerException e)
                                            {
                                                e.printStackTrace();
                                            }


                                            callForBatchList(selectedClassID);
                                        }

                                        if(!edtTime.getText().toString().isEmpty())
                                        {
                                            edtTime.setText("");

//                                            if(timeList!=null)
//                                            {
//                                                timeList.clear();
//                                            }
//                                            else if(timeNameList!=null)
//                                            {
//                                                timeNameList.clear();
//                                            }
                                            try {
                                                timeList.clear();
                                                timeNameList.clear();
                                            }
                                            catch (NullPointerException e)
                                            {
                                                e.printStackTrace();
                                            }


                                        }

                                        else{
                                            callForBatchList(selectedClassID);
                                        }

                                        dialog.dismiss();

                                    }
                                }).create().show();
                    }else {
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_TuitionActivity.this).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getResources().getString(R.string.app_name));
                        alertDialog.setMessage("Class not found");
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
        edtClass = findViewById(R.id.edt_class_tutionRegister);
        card_class = findViewById(R.id.card_class_register);
        card_batch = findViewById(R.id.card_batch_register);
        card_time = findViewById(R.id.card_timing_register);


        card_batch.setVisibility(View.VISIBLE);
        card_class.setVisibility(View.VISIBLE);
        card_time.setVisibility(View.VISIBLE);

        edtCountry.setText("India");
       // edtSchoolName.setText("Paradise Lost");
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
                className = edtClass.getText().toString().trim();

                if (rollNo.length() == 0)
                {
                    Toast.makeText(this, "Enter Roll number", Toast.LENGTH_SHORT).show();

                }else  if (className.length() == 0)
                {
                    Toast.makeText(this, "Select class", Toast.LENGTH_SHORT).show();

                }
                else  if (timings.length() == 0)
                {
                    Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();

                }else  if (batch.length() == 0)
                {
                    Toast.makeText(this, "Select batch", Toast.LENGTH_SHORT).show();

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
                    callForTuitionRegister(rollNo,selectedClassID, selectedBatchID,selectedTimeID,schoolName,country);


                  /*  Intent registerIntent = new Intent(this , LoginActivity.class);
                    startActivity(registerIntent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);*/
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

    public void callForTuitionRegister(String rollNo , String classId , String batchId , String timeId , String schoolname , String country)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("category_name",subCategoryName);
        params.put("subcategory_id",subCategoryID);
        params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID) );
        params.put("rollno", rollNo);
        params.put("class_id", classId);
        params.put("batch_id", batchId);
        params.put("timing_id", timeId);
        params.put("institude_name", schoolname);
        params.put("country", country);

        System.out.println("===subject param : "+params);

        CommunicationHandler.getInstance(this).callForTuitionRegister(this , params);

    }
    public void callForClassList()
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        System.out.println("====Institude sub category id : "+params);
        CommunicationHandler.getInstance(this).callForTuitionClassList(this,params);
    }

    public void callForBatchList(String selectedClassID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("class_id", selectedClassID);

        System.out.println("====Institude sub category id : "+params);
        CommunicationHandler.getInstance(this).callForTuitionBatchList(this,params);
    }

    public void callForTimingsList(String selectedBatchID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("batch_id", selectedBatchID);
        System.out.println("====Institude time sub category id : "+params);

        CommunicationHandler.getInstance(this).callForTuitionTiminingsList(this , params);
    }


    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        Logger.putLogInfo("tuRegistration ==>", jsonObject.toString());
        if (mMethod.equals(Const.TUITION_REG))
        {

            try {
                result = jsonObject.getInt("Result");
                String  msg = jsonObject.getString("Message");

                if (result ==1)
                {
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                    alertDialog.setMessage(msg);
                    alertDialog.setIcon(R.drawable.rc_logo);
                    alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface aDialogInterface, int aI) {
                            Intent registerIntent = new Intent(Category_TuitionActivity.this , LoginActivity.class);
                            registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(registerIntent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);
                            aDialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }else {
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

        }else if (mMethod.equals(Const.TUITION_BATCH_LIST)) {
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


                batchList.addAll(dataList.getBatchList());

                if (batchList != null && !batchList.isEmpty())
                {
                    for (int i =0 ; i < batchList.size(); i++)
                    {
                        batchNameList.add(batchList.get(i).getTutionBatchName());

                    }
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

                                Toast.makeText(Category_TuitionActivity.this, "Select Batch", Toast.LENGTH_SHORT).show();
                            }else {
                                hideInputSoftKey();

                                if (timeNameList != null && !timeNameList.isEmpty()) {
                                    new AlertDialog.Builder(Category_TuitionActivity.this)
                                            .setAdapter(spinner_breed, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    edtTime.setText(timeNameList.get(which));
                                                    selectedTimeID = timeList.get(which).getTimeId();

                                                    System.out.println("====Select time id :---"+ timeList.get(which).getTimeId() == selectedTimeID);

                                                    dialog.dismiss();

                                                }
                                            }).create().show();
                                } else {
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_TuitionActivity.this).create();
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


            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }

        }else   if (mMethod.equals(Const.TUITION_TIMINIGS_LIST)) {
            Logger.putLogInfo("Tuiiton_Time_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    timeList.clear();
                    timeNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }


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

        }else if (mMethod.equals(Const.TUITION_CLASS_LIST)) {
            Logger.putLogInfo("Class_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    classList.clear();
                    classNameList.clear();
                }
                catch (NullPointerException e)
                {}


                classList.addAll(dataList.getClassList());

                if (classList != null && !classList.isEmpty())
                {
                    for (int i =0 ; i < classList.size(); i++)
                    {
                        classNameList.add(classList.get(i).getClassName());

                    }
                    Logger.putLogInfo("Class_List==>", classNameList.toString());

                }

                System.out.println("===list ==="+batchNameList);

                final ArrayAdapter<String> batchArray = new  ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, batchNameList);
                {
                    edtBatch.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            hideInputSoftKey();
                            if (edtClass.getText().toString().length() == 0) {

                                Toast.makeText(Category_TuitionActivity.this, "Select class", Toast.LENGTH_SHORT).show();

                            }else {

                                if (batchNameList != null && !batchNameList.isEmpty()) {
                                    new AlertDialog.Builder(Category_TuitionActivity.this)
                                            .setAdapter(batchArray, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    edtBatch.setText(batchNameList.get(which));
                                                    System.out.println("===Selected batch id ==> " + batchList.get(which).getTuitonBatchId());
                                                    System.out.println("====Select batch id :---" + batchList.get(which).getTuitonBatchId() == batchList.get(which).getTuitonBatchId());
                                                    selectedBatchID = batchList.get(which).getTuitonBatchId();


                                                    if (!edtTime.getText().toString().isEmpty()) {
                                                        edtTime.setText("");
//                                                        if(timeList!=null)
//                                                        {
//                                                            timeList.clear();
//                                                        }
//                                                        else if(timeNameList!=null)
//                                                        {
//                                                            timeNameList.clear();
//                                                        }
                                                        try {
                                                            timeList.clear();
                                                            timeNameList.clear();
                                                        }
                                                        catch (NullPointerException e)
                                                        {
                                                            e.printStackTrace();
                                                        }


                                                        callForTimingsList(selectedBatchID);
                                                    } else {
                                                        callForTimingsList(selectedBatchID);
                                                    }


                                                    dialog.dismiss();
                                                }
                                            }).create().show();
                                } else {
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_TuitionActivity.this).create();
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
