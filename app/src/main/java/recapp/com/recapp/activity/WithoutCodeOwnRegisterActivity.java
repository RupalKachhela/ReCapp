package recapp.com.recapp.activity;

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

import java.util.ArrayList;
import java.util.List;

import recapp.com.recapp.R;

public class WithoutCodeOwnRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnMakePayment , btnBack;
    EditText edtRollNo,edtTime,edtSubject,edtSchoolName,edtCountry ,edtBatch;
    String rollNo,timings ,subject, schoolName , country ,batch ;
    List<String> timeList;
    List<String> subjectList;
    List<String> batchList;
    CardView cardBatch , cardBatchReg;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen_three);

//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
        edtTime.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        System.out.println("===list ==="+timeList);
        final ArrayAdapter<String> spinner_breed = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, timeList);
        {
            edtTime.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    hideInputSoftKey();
                    new AlertDialog.Builder(WithoutCodeOwnRegisterActivity.this)
                            .setAdapter(spinner_breed, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    edtTime.setText(timeList.get(which));
                                    dialog.dismiss();

                                }
                            }).create().show();
                }
            });
        }

        edtSubject.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        System.out.println("===list ==="+subjectList);

        final ArrayAdapter<String> spinner_bre = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, subjectList);
        {
            edtSubject.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    hideInputSoftKey();
                    new AlertDialog.Builder(WithoutCodeOwnRegisterActivity.this)
                            .setAdapter(spinner_bre, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    edtSubject.setText(subjectList.get(which));
                                    dialog.dismiss();

                                }
                            }).create().show();
                }
            });
        }

        edtBatch.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        System.out.println("===list ==="+batchList);

        final ArrayAdapter<String> batchArray = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, batchList);
        {
            edtBatch.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {

                    hideInputSoftKey();
                    new AlertDialog.Builder(WithoutCodeOwnRegisterActivity.this)
                            .setAdapter(batchArray, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    edtBatch.setText(batchList.get(which));
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
        btnMakePayment = findViewById(R.id.btn_makePayment_tutionRegister);
        edtRollNo = findViewById(R.id.edt_rollNo_tutionRegister);
        edtTime = findViewById(R.id.edt_time_tutionRegister);
        edtSubject = findViewById(R.id.edt_subject_tutionRegister);
        edtSchoolName = findViewById(R.id.edt_schoolName_tutionRegister);
        edtCountry = findViewById(R.id.edt_country_tutionRegister);
        edtBatch = findViewById(R.id.edt_batch_makepayment);
        cardBatch = findViewById(R.id.card_batch_payment);
        cardBatchReg = findViewById(R.id.card_batch_register);

        edtCountry.setText("India");
        edtSchoolName.setText("Paradise Lost");
        cardBatchReg.setVisibility(View.GONE);
        cardBatch.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(this);
        btnMakePayment.setOnClickListener(this);

    }

    public void prepareData()
    {
        timeList = new ArrayList<>();
        timeList.add("9:00am to 11:00am");
        timeList.add("11:00am to 1:00pm");
        timeList.add("1:00pm to 3:00pm");
        timeList.add("4:00am to 6:00pm");

        subjectList = new ArrayList<>();
        subjectList.add("Science");
        subjectList.add("Maths");
        subjectList.add("Physics");
        subjectList.add("Social Science");

        batchList = new ArrayList<>();
        batchList.add("Batch 1");
        batchList.add("Batch 2");
        batchList.add("Batch 3");
        batchList.add("Batch 4");
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
            case R.id.btn_makePayment_tutionRegister:
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

                }else  if (batch.length() == 0)
                {

                    Toast.makeText(this, "Select batch name", Toast.LENGTH_SHORT).show();

                }else  if (country.length() == 0)
                {

                    Toast.makeText(this, "Enter Country", Toast.LENGTH_SHORT).show();

                }else
                {

                    hideInputSoftKey();

//                    Intent registerIntent = new Intent(this , MainActivity.class);
//                    startActivity(registerIntent);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);


                    Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent(this , LoginActivity.class);
                    startActivity(registerIntent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.anim_hold);
                    Log.e("===Recapp : payment ","RollNo :- "+rollNo+"\t Timeings :-"+timings+"\t Subject :- "+subject+"\t School Name :-"+schoolName+"\t Country :-"+country);

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
}
