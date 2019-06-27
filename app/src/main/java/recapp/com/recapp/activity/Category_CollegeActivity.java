package recapp.com.recapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
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

public class Category_CollegeActivity extends AppCompatActivity implements View.OnClickListener ,CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnBack , btnRegister;
    EditText edtRollNo,edtTime,edtSubject,edtSchoolName,edtCountry , edtBatch , edtClass , edtSemester , edtYear , edtBranch , edtSection;
    String rollNo,timings ,subject, schoolName , country,batch , year , semester , branch , section;
    CardView card_class , card_batch , card_time , card_level;

    List<String> timeNameList;
    List<String> batchNameList;
    List<String> subjectNameList;

    List<BatchDataModel> batchList;
    List<BatchDataModel> timeList;
    List<BatchDataModel> subjectList;

    List<BatchDataModel> yearList;
    List<BatchDataModel> semesterList;
    List<BatchDataModel> branchList;
    List<BatchDataModel> sectionList;

    List<String> yearNameList;
    List<String> semesterNameList;
    List<String> branchNameList;
    List<String> sectionNameList;

    String selectedYearID = null;
    String selectedSemesterID = null;
    String selectedBranchID = null;
    String selectedSectionID = null;

    String subCategoryName= null;
    String subCategoryID= null;

    int result;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen_two);

        yearList = new ArrayList<>();
        semesterList = new ArrayList<>();
        branchList = new ArrayList<>();
        sectionList = new ArrayList<>();

        yearNameList = new ArrayList<>();
        semesterNameList = new ArrayList<>();
        branchNameList = new ArrayList<>();
        sectionNameList = new ArrayList<>();


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

            Log.i("COLLEGE==>","categoryName==>"+subCategoryName+" categoryId=>"+subCategoryID);
            System.out.println("===Sub CategoryId College Activity: "+subCategoryID);

        }
        callForYearList();

        hideInputSoftKey();

        System.out.println("===year list ==="+yearNameList);

        final ArrayAdapter<String> batchArray = new  ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, yearNameList);
        {
            edtYear.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    hideInputSoftKey();
                    if (yearNameList != null && !yearNameList.isEmpty()) {

                        new AlertDialog.Builder(Category_CollegeActivity.this)
                                .setAdapter(batchArray, new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        edtYear.setText(yearNameList.get(which));
                                        selectedYearID = yearList.get(which).getYearId();
                                        System.out.println("===Selected year id ==> "+selectedYearID);

                                        if (!edtSemester.getText().toString().isEmpty())
                                        {

                                            edtSemester.setText("");
//                                            if(semesterList!=null)
//                                            {
//                                                semesterList.clear();
//                                            }
//                                            else if(sectionNameList!=null)
//                                            {
//                                                semesterNameList.clear();
//                                            }
                                            try {
                                                semesterList.clear();
                                                sectionNameList.clear();
                                            }
                                            catch (NullPointerException e)
                                            {
                                                e.printStackTrace();
                                            }

                                            callForSemesterList(selectedYearID);
                                        }

                                        if(!edtBranch.getText().toString().isEmpty())
                                        {
                                            edtBranch.setText("");
                                            if(!branchNameList.isEmpty()||batchList!=null||!branchList.isEmpty()||branchNameList!=null)
                                            {
                                                branchList.clear();
                                                branchNameList.clear();
                                            }
//                                            if(branchList!=null)
//                                            {
//                                                branchList.clear();
//                                            }
//                                            else if(branchNameList!=null)
//                                            {
//                                                branchNameList.clear();
//                                            }

//                                            try {
//                                                branchList.clear();
//                                                branchNameList.clear();
//                                            }
//                                            catch (NullPointerException e)
//                                            {
//                                                e.printStackTrace();
//                                            }

                                        }

                                        if(!edtSection.getText().toString().isEmpty())
                                        {
                                            edtSection.setText("");
//                                            if(sectionList!=null)
//                                            {
//                                                sectionList.clear();
//                                            }
//                                            else if(sectionNameList!=null)
//                                            {
//                                                sectionNameList.clear();
//                                            }
                                            try {
                                                sectionList.clear();
                                                sectionNameList.clear();
                                            }
                                            catch (NullPointerException e)
                                            {
                                                e.printStackTrace();
                                            }


                                        }

                                        else{
                                            callForSemesterList(selectedYearID);
                                        }

                                        dialog.dismiss();


                                    }
                                }).create().show();
                    }else {
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_CollegeActivity.this).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getResources().getString(R.string.app_name));
                        alertDialog.setMessage("Year not found");
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
        edtSection = findViewById(R.id.edt_time_tutionRegister);
        edtSemester = findViewById(R.id.edt_level_tutionRegister);
        edtSubject = findViewById(R.id.edt_subject_tutionRegister);
        edtSchoolName = findViewById(R.id.edt_schoolName_tutionRegister);
        edtCountry = findViewById(R.id.edt_country_tutionRegister);
        edtBranch = findViewById(R.id.edt_batch_tutionRegister);
        edtYear = findViewById(R.id.edt_class_tutionRegister);
        card_class = findViewById(R.id.card_class_register);
        card_batch = findViewById(R.id.card_batch_register);
        card_time = findViewById(R.id.card_timing_register);
        card_level = findViewById(R.id.card_level_register);


        card_batch.setVisibility(View.VISIBLE);
        card_class.setVisibility(View.VISIBLE);
        card_time.setVisibility(View.VISIBLE);
        card_level.setVisibility(View.VISIBLE);

        edtYear.setHint("Year");
        edtSemester.setHint("Semester");
        edtBranch.setHint("Branch");
        edtSection.setHint("Section");

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
                year = edtYear.getText().toString().trim();
                semester = edtSemester.getText().toString().trim();
                branch = edtBranch.getText().toString().trim();
                section = edtSection.getText().toString().trim();
              //  timings = edtSection.getText().toString().trim();
              //  subject = edtSubject.getText().toString().trim();
                country = edtCountry.getText().toString().trim();
                schoolName = edtSchoolName.getText().toString().trim();

                if (edtRollNo.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Enter Roll number", Toast.LENGTH_SHORT).show();

                }else  if (edtYear.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select year", Toast.LENGTH_SHORT).show();

                }else  if (edtSemester.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select semester", Toast.LENGTH_SHORT).show();

                }else  if (edtBranch.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select branch", Toast.LENGTH_SHORT).show();

                }else  if (edtSection.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select section", Toast.LENGTH_SHORT).show();

                }else  if (edtSchoolName.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Enter College name", Toast.LENGTH_SHORT).show();

                }else  if (edtCountry.getText().toString().length() == 0)
                {

                    Toast.makeText(this, "Enter Country", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    hideInputSoftKey();
//                    Intent registerIntent = new Intent(this , RegisterScreenThreeActivity.class);
//                    startActivity(registerIntent);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);

                    System.out.println("=====College Collect Data : "+"\n"+ year +"\n"+selectedYearID+"\n"+selectedSemesterID+"\n"+selectedBranchID+"\n"+selectedSectionID+"\n"+schoolName+"\n"+country );

                    callForCollegeRegister(rollNo , selectedYearID , selectedSemesterID , selectedBranchID , selectedSectionID , schoolName , country);
//                    Intent registerIntent = new Intent(this , LoginActivity.class);
//                    startActivity(registerIntent);
//                    finish();
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

    public void callForYearList()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        CommunicationHandler.getInstance(this).callForCollegeYearList(this,params);

    }

    public void callForSemesterList(String selectedYearID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("year_id", selectedYearID);
        CommunicationHandler.getInstance(this).callForCollegeSemesterList(this , params);
    }
    public void callForBranchList(String selectedSemesterID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("sem_id", selectedSemesterID);
        CommunicationHandler.getInstance(this).callForCollegeBranchList(this , params);
    }
    public void callForSectionList(String selectedBranchID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        params.put("branch_id", selectedBranchID);

        CommunicationHandler.getInstance(this).callForCollegeSectionList(this , params);
    }

    public void callForCollegeRegister(String rollNo , String yearId , String semesterId , String brachId , String sectionId , String collegeName , String country)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("category_name",subCategoryName);
        params.put("subcategory_id",subCategoryID);
        params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID) );
        params.put("rollno", rollNo);
        params.put("year_id", yearId);
        params.put("sem_id", semesterId);
        params.put("branch_id", brachId);
        params.put("section_id", sectionId);
        params.put("school_name", collegeName);
        params.put("country", country);

        System.out.println("===College Register  param : "+params);
        CommunicationHandler.getInstance(this).callForCollegeRegister(this , params);
    }

    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        Logger.putLogInfo("TESTING==>", jsonObject.toString());

        if (mMethod.equals(Const.COLLEGE_REG))
        {

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
                            Intent registerIntent = new Intent(Category_CollegeActivity.this , LoginActivity.class);
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

         if (mMethod.equals(Const.COLLEGE_YEAR_LIST)) {
            Logger.putLogInfo("BatchResponse ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    yearList.clear();
                    yearNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }


                yearList.addAll(dataList.getClgYearList());

                if (yearList != null && !yearList.isEmpty())
                {
                    for (int i =0 ; i < yearList.size(); i++)
                    {
                        yearNameList.add(yearList.get(i).getYear());
                    }

                    Logger.putLogInfo("YEAR_List==>", yearNameList.toString());
                }

                System.out.println("=== semester list ==="+semesterNameList);
                final ArrayAdapter<String> spinner_semester = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, semesterNameList);
                {
                    edtSemester.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtYear.getText().toString().length() == 0) {

                                Toast.makeText(Category_CollegeActivity.this, "Select year", Toast.LENGTH_SHORT).show();

                            }else {
                                hideInputSoftKey();

                                if (semesterNameList != null && !semesterNameList.isEmpty())
                                {
                                    new AlertDialog.Builder(Category_CollegeActivity.this)
                                            .setAdapter(spinner_semester, new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int which)
                                                {

                                                    edtSemester.setText(semesterNameList.get(which));
                                                    selectedSemesterID = semesterList.get(which).getSemId();

                                                    if (!edtBranch.getText().toString().isEmpty()) {
                                                        edtBranch.setText("");
//                                                        if(branchList!=null)
//                                                        {
//                                                            branchList.clear();
//                                                        }
//                                                        else if(branchNameList!=null)
//                                                        {
//                                                            branchNameList.clear();
//                                                        }
//                                                        try {
//                                                                branchList.clear();
//                                                                branchNameList.clear();
//                                                        }
//                                                        catch (NullPointerException e)
//                                                        {
//                                                            e.printStackTrace();
//                                                        }
                                                        if(!branchNameList.isEmpty()||batchList!=null||!branchList.isEmpty()||branchNameList!=null)

                                                        {
                                                            branchList.clear();
                                                            branchNameList.clear();
                                                        }

                                                        callForBranchList(selectedSemesterID);
                                                    }

                                                    if(!edtSection.getText().toString().isEmpty())
                                                    {
                                                        edtSection.setText("");
                                                        try {
                                                            sectionList.clear();
                                                            sectionNameList.clear();
                                                        }
                                                        catch (NullPointerException e)
                                                        {
                                                            e.printStackTrace();
                                                        }

                                                    }

                                                    else{
                                                        callForBranchList(selectedSemesterID);
                                                    }

                                                    dialog.dismiss();

                                                }
                                            }).create().show();

                                }else {
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_CollegeActivity.this).create();
                                    alertDialog.setCancelable(false);
                                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                                    alertDialog.setMessage("Semester not found");
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
        }else   if (mMethod.equals(Const.COLLEGE_SEMESTER_LIST)) {
            Logger.putLogInfo("COLLEGE_SEMESTER_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    semesterList.clear();
                    semesterNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

                semesterList.addAll(dataList.getClgSemesterList());

                if (semesterList != null && !semesterList.isEmpty())
                {
                    for (int i =0 ; i < semesterList.size(); i++)
                    {
                        semesterNameList.add(semesterList.get(i).getSemName());

                    }
                    Logger.putLogInfo("COLLEGE_SEMESTER_LIST ==>", semesterNameList.toString());

                }

                System.out.println("=== branch list ==="+branchNameList);

                final ArrayAdapter<String> spinner_branch = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, branchNameList);
                {
                    edtBranch.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtSemester.getText().toString().length() == 0) {

                                Toast.makeText(Category_CollegeActivity.this, "Select semester", Toast.LENGTH_SHORT).show();

                            }else {
                                hideInputSoftKey();
                                if (branchNameList != null && !branchNameList.isEmpty()) {
                                    new AlertDialog.Builder(Category_CollegeActivity.this)
                                            .setAdapter(spinner_branch, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    edtBranch.setText(branchNameList.get(which));
                                                    selectedBranchID = branchList.get(which).getBranchId();

                                                    if(!edtSection.getText().toString().isEmpty())
                                                    {
                                                        edtSection.setText("");
                                                        try {
                                                            sectionList.clear();
                                                            sectionNameList.clear();
                                                        }
                                                        catch (NullPointerException e)
                                                        {}

                                                        callForSectionList(selectedBranchID);
                                                    }

                                                    else{
                                                        callForSectionList(selectedBranchID);
                                                    }
                                                    dialog.dismiss();

                                                }
                                            }).create().show();
                                }else {
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_CollegeActivity.this).create();
                                    alertDialog.setCancelable(false);
                                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                                    alertDialog.setMessage("Branch not found");
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
        }else if (mMethod.equals(Const.COLLEGE_BRANCH_LIST)) {
            Logger.putLogInfo("COLLEGE_BRANCH_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                if(!branchNameList.isEmpty()||batchList!=null||!branchList.isEmpty()||branchNameList!=null)

                {
                    branchList.clear();
                    branchNameList.clear();
                }
//                try {
//                    branchList.clear();
//                    batchNameList.clear();
//                }
//                catch (NullPointerException e)
//                {
//                    e.printStackTrace();
//                }



                branchList.addAll(dataList.getClgBranchList());

                if (branchList != null && !branchList.isEmpty())
                {
                    for (int i =0 ; i < branchList.size(); i++)
                    {
                        branchNameList.add(branchList.get(i).getBranchName());

                    }
                    Logger.putLogInfo("COLLEGE_BRANCH_LIST==>", branchNameList.toString());

                }

                System.out.println("=== section list ==="+semesterNameList);

                final ArrayAdapter<String> spinner_section = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, sectionNameList);
                {
                    edtSection.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtBranch.getText().toString().length() == 0) {

                                Toast.makeText(Category_CollegeActivity.this, "Select branch", Toast.LENGTH_SHORT).show();

                            }else {
                                hideInputSoftKey();

                                if (sectionNameList != null && !sectionNameList.isEmpty())
                                {
                                    new AlertDialog.Builder(Category_CollegeActivity.this)
                                            .setAdapter(spinner_section, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    edtSection.setText(sectionNameList.get(which));
                                                    selectedSectionID = sectionList.get(which).getSectionId();
                                                    dialog.dismiss();

                                                }
                                            }).create().show();
                                }else {
                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_CollegeActivity.this).create();
                                    alertDialog.setCancelable(false);
                                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                                    alertDialog.setMessage("Section not found");
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

        }else if (mMethod.equals(Const.COLLEGE_SECTION_LIST)) {
            Logger.putLogInfo("COLLEGE_SECTION_LIST_Response ==>", jsonObject.toString());

            try
            {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    sectionList.clear();
                    sectionNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

                sectionList.addAll(dataList.getClgSectionList());

                if (sectionList != null && !sectionList.isEmpty())
                {
                    for (int i =0 ; i < sectionList.size(); i++)
                    {
                        sectionNameList.add(sectionList.get(i).getClgSectionName());

                    }
                    Logger.putLogInfo("COLLEGE_SECTION_LIST==>", sectionNameList.toString());

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
