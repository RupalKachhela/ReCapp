package recapp.com.recapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;

public class Category_SchoolActivity extends AppCompatActivity implements View.OnClickListener ,CommunicationHandler.CommunicationHandlerCallBack{

    Button btnBack , btnRegister;
    EditText edtClass,edtSection ,edtRollNo ,edtSchoolName ,edtCountry ,edtSubject;
    List<BatchDataModel> classList;
    List<BatchDataModel> sectionList;
    List<BatchDataModel> subjectList;

    List<String> sectionNameList;
    List<String> classNameList;
    List<String> subjectNameList;

    String subCategoryName= null;
    String subCategoryID= null;

    String rollNo , className ,section , country , schoolName , subjectName;
    String selectedClassId = null;
    String selectedSectionId = null;
    String selectedSubjectId = null;
    int result;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_screen_one);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

          //  getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
        classList = new ArrayList<>();
        classNameList = new ArrayList<>();
        sectionList = new ArrayList<>();
        sectionNameList = new ArrayList<>();
        subjectList = new ArrayList<>();
        subjectNameList = new ArrayList<>();

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

            System.out.println("===Sub CategoryName Scool Activity: "+subCategoryName);

        }

        callForClassList();
        hideInputSoftKey();
        edtClass.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        System.out.println("===list ==="+classList);
        final ArrayAdapter<String > spinner_breed = new  ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, classNameList);
        {
            edtClass.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v) {

                        hideInputSoftKey();

                    if (classNameList != null && !classNameList.isEmpty())
                    {

                        new AlertDialog.Builder(Category_SchoolActivity.this)
                                .setAdapter(spinner_breed, new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which) {
                                        edtClass.setText(classNameList.get(which));
                                        selectedClassId = classList.get(which).getClassId();

                                        if (!edtSection.getText().toString().isEmpty())
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
                                            try
                                            {
                                                sectionList.clear();
                                                sectionNameList.clear();
                                            }
                                            catch (NullPointerException e)
                                            {
                                                e.printStackTrace();
                                            }


                                            callForSectionList(selectedClassId);
                                        }

                                        else{
                                            callForSectionList(selectedClassId);
                                        }

                                        dialog.dismiss();
                                    }
                                }).create().show();

                    }else {
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_SchoolActivity.this).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getResources().getString(R.string.app_name));
                        alertDialog.setMessage("Class not found");
                        alertDialog.setIcon(R.drawable.rc_logo);
                        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener()
                        {
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
        btnRegister = findViewById(R.id.btn_school_register);
        edtClass = findViewById(R.id.edt_class_SchoolRegister);
        edtSection = findViewById(R.id.edt_section_SchoolRegister);
        edtSubject = findViewById(R.id.edt_subject_schoolRegister);
        edtRollNo = findViewById(R.id.edt_rollNo_SchoolRegister);
        edtSchoolName = findViewById(R.id.edt_schoolName_schoolRegister);
        edtCountry = findViewById(R.id.edt_country_schoolRegister);

        System.out.println("===Sub  : "+subCategoryName);

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
            case R.id.btn_school_register:
            {

                rollNo = edtRollNo.getText().toString().trim();
                className = edtClass.getText().toString().trim();
                section = edtSection.getText().toString().trim();
                country = edtCountry.getText().toString().trim();
                schoolName = edtSchoolName.getText().toString().trim();
                subjectName = edtSubject.getText().toString().trim();

                if (rollNo.length() == 0)
                {
                    Toast.makeText(this, "Enter Roll number", Toast.LENGTH_SHORT).show();

                }else  if (className.length() == 0)
                {
                    Toast.makeText(this, "Select class", Toast.LENGTH_SHORT).show();

                }else  if (section.length() == 0)
                {
                    Toast.makeText(this, "Select section", Toast.LENGTH_SHORT).show();

                }else  if (schoolName.length() == 0)
                {

                    Toast.makeText(this, "Enter school name", Toast.LENGTH_SHORT).show();

                }else  if (country.length() == 0)
                {

                    Toast.makeText(this, "Enter Country", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    hideInputSoftKey();
                    callForSchoolRegister(rollNo, selectedClassId,selectedSectionId,schoolName,country);
//                    Intent registerIntent = new Intent(this , RegisterScreenTwoActivity.class);
//                    startActivity(registerIntent);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);
                    Log.e("===Recapp : School Register ",selectedClassId + " == "+selectedSectionId+"=="+selectedSubjectId+"RollNo :- "+rollNo+"\t Class :-"+className+"\t Section :- "+section+"\t School Name :-"+schoolName+"\t Country :-"+country);

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

    public void callForClassList()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCategoryID);
        CommunicationHandler.getInstance(this).callForGetClassList(this , params);
    }

    public void callForSectionList(String selectedClassId)
    {
        try
        {
            sectionList.clear();
            sectionNameList.clear();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("class_id", selectedClassId);
        params.put("subcategory_id", subCategoryID);

        System.out.println("===section param : "+params);
        CommunicationHandler.getInstance(this).callForGetSectionList(this , params);
    }
    public void callForSubjectList()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("class_id", selectedClassId);
        params.put("subcategory_id", subCategoryID);
        System.out.println("===subject param : "+params);

        CommunicationHandler.getInstance(this).callForGetClassSubjectList(this , params);
    }

    public void callForSchoolRegister(String rollNo , String classId , String sectionId, String schoolname , String country)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("category_name",subCategoryName);
        params.put("subcategory_id",subCategoryID);
        params.put("user_id",RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID) );
        params.put("rollno", rollNo);
        params.put("class_id", classId);
        params.put("section_id", sectionId);
        params.put("school_name", schoolname);
        params.put("country", country);

        System.out.println("===subject param : "+params);

        CommunicationHandler.getInstance(this).callForSchoolRegister(this , params);
    }

    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {

        if (mMethod.equals(Const.SCHOOL_REG))
        {

            Logger.putLogInfo("SchoolRegistration ==>", jsonObject.toString());

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
                            Intent registerIntent = new Intent(Category_SchoolActivity.this , LoginActivity.class);
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

            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }

        }else  if (mMethod.equals(Const.CLASS_LIST))
        {

            Logger.putLogInfo("ClassResponse ==>", jsonObject.toString());

            try {

                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    classList.clear();
                    classNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
//                if(classList!=null)
//                {
//                    classList.clear();
//                }
//                else if(classNameList!=null)
//                {
//                    classNameList.clear();
//                }


                classList.addAll(dataList.getClassList());

                if (classList != null && !classList.isEmpty())
                {
                    for (int i =0 ; i < classList.size(); i++)
                    {
                        classNameList.add(classList.get(i).getClassName());

                    }

                    final ArrayAdapter<String> spinner_bre = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, sectionNameList);
                    {

                            edtSection.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v)
                                {
                                    if (edtClass.getText().toString().length() == 0) {

                                        Toast.makeText(Category_SchoolActivity.this, "Select Class", Toast.LENGTH_SHORT).show();
                                    }else {
                                        hideInputSoftKey();
                                        if (sectionNameList != null && !sectionNameList.isEmpty()) {
                                            new AlertDialog.Builder(Category_SchoolActivity.this)
                                                    .setAdapter(spinner_bre, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            edtSection.setText(sectionNameList.get(which));
                                                            selectedSectionId = sectionList.get(which).getSectionId();
                                                            dialog.dismiss();

                                                        }
                                                    }).create().show();

                                        } else {
                                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Category_SchoolActivity.this).create();
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

                }


                System.out.println("===list ==="+sectionNameList);

              /*  final ArrayAdapter<String> spinner_bre = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, sectionNameList);
                {
                    edtSection.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            hideInputSoftKey();
                            new AlertDialog.Builder(Category_SchoolActivity.this)
                                    .setAdapter(spinner_bre, new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            edtSection.setText(sectionNameList.get(which));
                                            selectedSectionId = sectionList.get(which).getSectionId();
                                            dialog.dismiss();

                                        }
                                    }).create().show();
                        }
                    });
                }

            */

            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }

        }else  if (mMethod.equals(Const.CLASS_SECTION_LIST)) {
            Logger.putLogInfo("ClassSectionResponse ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
//                if(sectionList!=null)
//                {
//                    sectionList.clear();
//                }
                try {
                    sectionList.clear();
                    sectionNameList.clear();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

                sectionList.addAll(dataList.getSectionList());

                if (sectionList != null && !sectionList.isEmpty())
                {
                    for (int i =0 ; i < sectionList.size(); i++)
                    {
                        sectionNameList.add(sectionList.get(i).getSectionName());
                    }
                }


            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }

        }else  if (mMethod.equals(Const.CLASS_SUBJECT_LIST)) {
            Logger.putLogInfo("ClassSectionResponse ==>", jsonObject.toString());
//            if(subjectList!=null)
//            {
//                subjectList.clear();
//            }
            try {
                subjectList.clear();
                subjectNameList.clear();
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                subjectList.addAll(dataList.getClassSubjectList());

                if (subjectList != null && !subjectList.isEmpty())
                {
                    for (int i =0 ; i < subjectList.size(); i++)
                    {
                        subjectNameList.add(subjectList.get(i).getBatchSubject());

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

    }

    @Override
    public void cacheCBWithMethod(String mMethod, String mResponse) {

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
