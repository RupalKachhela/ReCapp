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

public class Category_Own_WihoutCodeActivity extends AppCompatActivity implements View.OnClickListener ,CommunicationHandler.CommunicationHandlerCallBack
{

    Button btnBack , btnRegister;
    EditText edtRollNo,edtTime,edtSubject,edtSchoolName,edtCountry , edtBatch;
    String rollNo,timings ,subject, schoolName , country,batch;
    CardView card_class , card_batch , card_time;

    List<String> batchNameList;
    List<BatchDataModel> batchList;
    List<String> categoryNameList = new ArrayList<>();

    String selectedBatchID = null;
    String selectedCategoryID = null;

    String subCategoryName= null;
    String subCategoryID= null;

    String bName , bEmail ,bMobile ,bPassword , bcPassword ;

    List<BatchDataModel> categoryList = new ArrayList<>();

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

        callForCategoryList();

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

       /* if (getIntent() != null)
        {
            Bundle b =  getIntent().getExtras();
            if (b != null)
            {
                subCategoryName = b.getString("SubCategoryName");
                subCategoryID = b.getString("SubCategoryId");
               // subCategoryID ="1";

            }
            System.out.println("===Sub CategoryId Test prep Activity: "+subCategoryID);

        }*/
        //callForBatchList();

        hideInputSoftKey();


        final ArrayAdapter<String> batchArray = new  ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, categoryNameList);
        {
            edtSubject.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    hideInputSoftKey();
                    new AlertDialog.Builder(Category_Own_WihoutCodeActivity.this)
                            .setAdapter(batchArray, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    edtSubject.setText(categoryNameList.get(which));
                                    selectedCategoryID = categoryList.get(which).getCategoryId();
                                    System.out.println("===Selected batch id ==> "+selectedCategoryID);

                                    if (!edtBatch.getText().toString().isEmpty()) {
                                        edtBatch.setText("");
                                        batchList.clear();
                                        batchNameList.clear();
                                        callForSubCategoryList(selectedCategoryID);
                                    }
                                    else {

                                        callForSubCategoryList(selectedCategoryID);
                                    }

                                    dialog.dismiss();

                                }
                            }).create().show();

                }
            });
        }

    }

    public void callForCategoryList()
    {
        CommunicationHandler.getInstance(this).callForCategoryList(this);
    }

    public void init()
    {

        btnBack = findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        btnRegister = findViewById(R.id.btn_tutionRegister);
        edtSubject = findViewById(R.id.edt_class_tutionRegister);
        edtRollNo = findViewById(R.id.edt_rollNo_tutionRegister);
        edtTime = findViewById(R.id.edt_time_tutionRegister);
        edtSchoolName = findViewById(R.id.edt_schoolName_tutionRegister);
        edtCountry = findViewById(R.id.edt_country_tutionRegister);
        edtBatch = findViewById(R.id.edt_batch_tutionRegister);
        card_class = findViewById(R.id.card_class_register);
        card_batch = findViewById(R.id.card_batch_register);
        card_time = findViewById(R.id.card_timing_register);

        card_batch.setVisibility(View.VISIBLE);
        card_class.setVisibility(View.VISIBLE);

        edtBatch.setHint("Sub Category Name");
        edtSubject.setHint("Category Type");
        edtCountry.setText("India");
        edtSchoolName.setHint("Your School Name");
        edtSchoolName.setFocusable(true);
        edtSchoolName.setFocusableInTouchMode(true);
        btnRegister.setText("Make Payment");
        edtSchoolName.setInputType(InputType.TYPE_CLASS_TEXT);
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
                    Toast.makeText(this, "Enter Roll Number", Toast.LENGTH_SHORT).show();
                }
                else  if (edtSubject.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select Category Type", Toast.LENGTH_SHORT).show();
                }else  if (edtBatch.getText().toString().length() == 0)
                {
                    Toast.makeText(this, "Select Sub Category Name", Toast.LENGTH_SHORT).show();
                }else  if (edtSchoolName.getText().toString().length() == 0) {

                    Toast.makeText(this, "Enter School Name", Toast.LENGTH_SHORT).show();

                }else  if (country.length() == 0)
                {
                    Toast.makeText(this, "Enter Country", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    hideInputSoftKey();
                    System.out.println("==== Training collection data : "+"\n"+rollNo+"\n"+ selectedCategoryID+"\n"+selectedBatchID+"\n"+schoolName+"\n"+country);
                    callForWithoutCodeRegister(rollNo,selectedCategoryID,selectedBatchID,schoolName,country);

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

    public void callForSubCategoryList(String selectedCategoryID)
    {

        HashMap<String, String> params = new HashMap<>();
        params.put("category_id", selectedCategoryID);
        CommunicationHandler.getInstance(this).callForSubCategoryList(this,params);
    }

    public void callForWithoutCodeRegister(String rollNo ,String selectedCategoryID, String batchId ,String className , String country)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", bName);
        params.put("email", bEmail);
        params.put("password", bPassword);
        params.put("cpassword", bcPassword);
        params.put("mobile", bMobile);
        params.put("rollno", rollNo);
        params.put("category_id", selectedCategoryID);
        params.put("subcategory_id", batchId);
        params.put("reg_institude_name", className);
        params.put("country", country);
        params.put("payment_status","done");

        System.out.println("==Register  param : "+params);

        CommunicationHandler.getInstance(this).callForWithoutCodeRegister(this , params);
    }
    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        if (mMethod.equals(Const.WITHOUT_CODE_REGISTRATION)) {

            Logger.putLogInfo("WITHOUT CODE Registration ==>", jsonObject.toString());

            try {
                int result = jsonObject.getInt("Result");
                String msg = jsonObject.getString("Message");

                if (result == 1) {
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                    //alertDialog.setMessage("Not implemented own registration and payment Gateway but now assumed payment successfully done and move ahead.");
                    alertDialog.setMessage("Not implemented payment Gateway but now assumed payment successfully done and move ahead.");
                    alertDialog.setIcon(R.drawable.rc_logo);

                    alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface aDialogInterface, int aI)
                        {
                            aDialogInterface.dismiss();
                            Intent registerIntent = new Intent(Category_Own_WihoutCodeActivity.this, LoginActivity.class);
                            startActivity(registerIntent);
                            finish();
                            overridePendingTransition(R.anim.anim_hold, R.anim.slide_out_right);

                        }
                    });
                    alertDialog.show();
                }
                else {
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

        if (mMethod.equals(Const.CATEGORY_LIST))
        {
            Logger.putLogInfo("CategoryResponse ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    categoryList.clear();
                    categoryNameList.clear();
                }catch (NullPointerException e)
                {
                    e.getMessage();
                }

                categoryList.addAll(dataList.getCategoryList());

                for (int i= 0 ; i < categoryList.size(); i++)
                {
                    String category_name =  categoryList.get(i).getCategoryName();

                    categoryNameList.add(categoryList.get(i).getCategoryName());

                }

                final ArrayAdapter<String> batchArray = new  ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, batchNameList);
                {
                    edtBatch.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            if (edtSubject.getText().toString().length() == 0) {

                                Toast.makeText(Category_Own_WihoutCodeActivity.this, "Select Category Type", Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                hideInputSoftKey();
                                new AlertDialog.Builder(Category_Own_WihoutCodeActivity.this)
                                        .setAdapter(batchArray, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                edtBatch.setText(batchNameList.get(which));
                                                selectedBatchID = batchList.get(which).getSubCategoryId();
                                                System.out.println("===Selected batch id ==> " + selectedBatchID);
                                                dialog.dismiss();


                                            }
                                }).create().show();
                            }
                        }
                    });
                }

            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }
        }

        if (mMethod.equals(Const.SUB_CATEGORY_LIST)) {
            Logger.putLogInfo("SUB_CATEGORY_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    batchList.clear();
                    batchNameList.clear();
                }catch (NullPointerException e)
                {
                    e.getMessage();
                }

                batchList.addAll(dataList.getSubcategoryList());

                if (batchList != null && !batchList.isEmpty())
                {
                    for (int i =0 ; i < batchList.size(); i++)
                    {
                        batchNameList.add(batchList.get(i).getSubCategoryName());
                    }
                }

                System.out.println("=== SUB_CATEGORY_LIST  ==="+batchNameList);

            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }
        }
    }

    @Override
    public void failureCBWithMethod(String mMethod, String mError) {
        Logger.putLogError("SUB_CATEGORY_LIST_Response ==>", mError);

    }

    @Override
    public void cacheCBWithMethod(String mMethod, String mResponse) {
        Logger.putLogError("SUB_CATEGORY_LIST_Response ==>", mResponse);

    }
}
