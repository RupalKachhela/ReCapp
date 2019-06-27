package recapp.com.recapp.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recapp.com.recapp.CommunicationHandler.CommunicationHandler;
import recapp.com.recapp.R;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.database.DBHelper;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;

public class RecordConfirmFragment extends android.support.v4.app.Fragment implements View.OnClickListener,CommunicationHandler.CommunicationHandlerCallBack {

    EditText edtSubject, edtTopic, edtCategory, edtSubtopic, edtFileName;
    TextView title;
    RelativeLayout relative;
    Button backBtn, btnHelp, btnDone;
    CheckBox cbMsg;
    LinearLayout ll_msg, ll_content;
    ImageButton btnFilter;
    String file,subject,topic,subtopic,audioType;
    byte[] imgeFile;
    RelativeLayout rl_edit, rl_delete, rl_topic, rl_subtopic;

    int i = 0;

    public static List<String> topicList;

    public static List<String> subtopicList;

    List<String> topicNameList= new ArrayList<>();
    List<String> subTopicNameList= new ArrayList<>();

    List<BatchDataModel> topiclist = new ArrayList<>();
    List<BatchDataModel> subtopiclist = new ArrayList<>();
    String selectedTopicId,selectedSubtopicId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmnet_record_confirm, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPink));

        if (getArguments() != null) {
            file = getArguments().getString("audio_file");
            imgeFile = getArguments().getByteArray("image_file");
            subject = getArguments().getString("subject");
            topic = getArguments().getString("topic");
            subtopic = getArguments().getString("subtopic");
            audioType = getArguments().getString("audioType");

            System.out.println("=====get value : " + file + " : " + imgeFile);

            if(audioType == null)
            {
                audioType = HomeFragment.selectedAudioType;
            }
        }

        System.out.println("======VALUE ::: " + subject + audioType);

        title = getActivity().findViewById(R.id.txt_actionbar_title);
        relative = getActivity().findViewById(R.id.rl_action_bar);
        backBtn = getActivity().findViewById(R.id.btn_back);
        btnHelp = getActivity().findViewById(R.id.btn_help);
        edtSubject = view.findViewById(R.id.edt_selcet_subject_record);
        ll_msg = view.findViewById(R.id.ll_record_detail_msg);
        ll_content = view.findViewById(R.id.ll_record_detail_audioplay);
        btnFilter = view.findViewById(R.id.imgbtn_filter);
        edtSubject = view.findViewById(R.id.edt_selcet_subject_recordFragment);
        edtCategory = view.findViewById(R.id.edt_selcet_category_recordFragment);
        edtTopic = view.findViewById(R.id.edt_selcet_topic_recordFragment);
        edtSubtopic = view.findViewById(R.id.edt_selcet_subtopic_recordFragment);

        edtFileName = view.findViewById(R.id.edt_fileName_recordConfirm);
        btnDone = getActivity().findViewById(R.id.btn_done_recordConfirm);

        edtSubject.setText(subject);
        edtTopic.setText(topic);
        edtSubtopic.setText(subtopic);

       // edtSubject.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT));
        edtCategory.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_CATEGORY));

        if(!edtSubject.getText().toString().isEmpty()) {

            callForTopicList();
        }

        backBtn.setVisibility(View.VISIBLE);
        btnHelp.setVisibility(View.INVISIBLE);
        relative.setBackgroundColor(getResources().getColor(R.color.colorPink));
        title.setText("Record");

        rl_topic = view.findViewById(R.id.rl_select_topic_record);
        rl_subtopic = view.findViewById(R.id.rl_select_subtopic_record);

        topicList = new ArrayList<>();
//        topicList.add("The Body");
//        topicList.add("Cells");
//        topicList.add("DNA");

        rl_topic.setOnClickListener(this);
        rl_subtopic.setOnClickListener(this);


//        edtSubject.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
//        System.out.println("===list ==="+HomeFragment.subjectList);
//
//        final ArrayAdapter<String> spinner_breed = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, HomeFragment.subjectList);
//        {
//            System.out.println("====inside array adpater click ==="+HomeFragment.subjectList);
//
//            edtSubject.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    System.out.println("====click subject ===");
//                    new AlertDialog.Builder(getActivity())
//                            .setAdapter(spinner_breed, new DialogInterface.OnClickListener()
//                            {
//                                public void onClick(DialogInterface dialog, int which)
//                                {
//
//                                    edtSubject.setText(HomeFragment.subjectList.get(which));
//                                    dialog.dismiss();
//
//                                }
//                            }).create().show();
//                }
//            });
//        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().onBackPressed();
                }
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragment.showPopup(v, getActivity());
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

               if(edtSubject.getText().toString().length() == 0)
                {
                    Toast.makeText(getActivity(), "Please select Subject", Toast.LENGTH_SHORT).show();
                }

               else if (edtTopic.getText().toString().length() == 0) {
                   Toast.makeText(getActivity(), "Please select Topic", Toast.LENGTH_SHORT).show();
               }

               else if (edtSubtopic.getText().toString().length() == 0) {
                   Toast.makeText(getActivity(), "Please select Sub Topic", Toast.LENGTH_SHORT).show();
               }

               else if (edtFileName.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter file name", Toast.LENGTH_SHORT).show();
                }

                else{

                    String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
                    dir += "/" + edtFileName.getText().toString() + ".3gp";
                    File oldFile = new File(file);
                    File latestname = new File(dir);
                    boolean success = oldFile.renameTo(latestname);

                    if (success) {

                        System.out.println("file is renamed..");
                        try {

                            storeFile(dir);

                            Bundle bundle=new Bundle();
                            bundle.putString("subject",subject);
                            bundle.putString("isFrom","recordConfirm");
                            HomeFragment fragment = new HomeFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fl_main_container, fragment);
                            fragment.setArguments(bundle);
                            fragmentTransaction.addToBackStack(HomeFragment.TAG);
                            fragmentTransaction.commit();

                        } catch (IOException aE) {
                            aE.printStackTrace();
                        }

                    }
                }
            }
        });

    }

    public void callForTopicList()
    {
        HashMap<String, String> params = new HashMap<>();
        // params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID));
        params.put("subject_id",RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT_ID));
        CommunicationHandler.getInstance(this).callForTopicList(getContext(),params);
        System.out.println("======PARAM : " + params);
    }

    public void callForSubTopicList(String selectedTopicId)
    {
        HashMap<String, String> params = new HashMap<>();
        // params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID));
        params.put("subject_id",RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT_ID));
        params.put("topic_id",selectedTopicId);
        CommunicationHandler.getInstance(this).callForSubTopicList(getContext(),params);
        System.out.println("======PARAM : " + params);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.rl_select_topic_record:
            {

                final ArrayAdapter<String> spinner_topic = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,topicNameList);
                {
                    System.out.println("====inside array adpater click ===" + topicNameList);

                    if(edtSubject.getText().toString().length() == 0)
                    {
                        Toast.makeText(getContext(),"Please Select Subject First",Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if (!topicNameList.isEmpty() && topicNameList != null) {

                            System.out.println("====click subject ===");
                            new AlertDialog.Builder(getActivity())
                                    .setAdapter(spinner_topic, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            edtTopic.setText(topicNameList.get(which));
                                            selectedTopicId = topiclist.get(which).getTopicId();

                                            if (!edtSubtopic.getText().toString().isEmpty()) {
                                                edtSubtopic.setText("");
                                                subtopiclist.clear();
                                                subTopicNameList.clear();
                                                callForSubTopicList(selectedTopicId);
                                            } else {
                                                callForSubTopicList(selectedTopicId);
                                            }

                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }

                        else {

                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
                            alertDialog.setCancelable(false);
                            alertDialog.setTitle(getResources().getString(R.string.app_name));
                            alertDialog.setMessage("No Topic Found");
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

                break;
            }

/*            case  R.id.rl_select_subtopic_record:
            {

                if(edtTopic.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getContext(),"Please select Topic",Toast.LENGTH_SHORT).show();
                }

                else {

                    if(edtTopic.getText().toString().trim().equals("The Body"))
                    {
                        subtopicList = new ArrayList<>();
                        subtopicList.add("Structure of Body");
                        subtopicList.add("Body explanation");
                    }

                    else if(edtTopic.getText().toString().trim().equals("Cells"))
                    {
                        subtopicList = new ArrayList<>();
                        subtopicList.add("Structure of Cell");
                        subtopicList.add("Cell Code");
                        subtopicList.add("Cell explanation");
                        subtopicList.add("Cell Definition");
                    }

                    else {

                        subtopicList = new ArrayList<>();
                        subtopicList.add("Structure of DNA");
                        subtopicList.add("DNA Code");
                        subtopicList.add("DNA explanation");
                    }

                    final ArrayAdapter<String> spinner_subtopic = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, RecordConfirmFragment.subtopicList);
                    {
                        System.out.println("====inside array adpater click ===" + HomeFragment.subjectList);

                        System.out.println("====click subject ===");
                        new AlertDialog.Builder(getActivity())
                                .setAdapter(spinner_subtopic, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        edtSubtopic.setText(RecordConfirmFragment.subtopicList.get(which));
                                        dialog.dismiss();

                                    }
                                }).create().show();
                    }
                }

                break;
            }*/
        }
    }

    public void storeFile (String dir) throws IOException
    {

        FileInputStream fis = new FileInputStream(dir);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer =new byte[1024];
        int read;
        while ((read = fis.read(buffer)) != -1)
        {
            baos.write(buffer, 0, read);
        }
        baos.flush();
        byte[] fileByteArray = baos.toByteArray();
        System.out.println("=== file byte array : "+fileByteArray);

        if (imgeFile != null && fileByteArray != null)
        {

            DBHelper dbHandler = new DBHelper(getActivity());

            if(audioType == null)
            {
                dbHandler.insertUserDetails(RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID), dir, edtCategory.getText().toString(), edtSubject.getText().toString(), edtTopic.getText().toString(), edtSubtopic.getText().toString(), HomeFragment.selectedAudioType, fileByteArray, imgeFile);

            }

            else
            {

               dbHandler.insertUserDetails(RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID),dir ,edtCategory.getText().toString(),edtSubject.getText().toString(),edtTopic.getText().toString(),edtSubtopic.getText().toString(),audioType,fileByteArray , imgeFile);
            }
        }
    }

    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        if (mMethod.equals(Const.TOPIC_LIST)) {
            Logger.putLogInfo("TOPIC_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                topiclist.clear();
                topicNameList.clear();
                topiclist.addAll(dataList.getTopicList());

                if (topiclist != null && !topiclist.isEmpty())
                {
                    for (int i =0 ; i < topiclist.size(); i++)
                    {
                        topicNameList.add(topiclist.get(i).getTopicName());
                    }
                    Logger.putLogInfo("TOPIC_LIST ==>", topicNameList.toString());
                }

                final ArrayAdapter<String> spinner_branch = new  ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, subTopicNameList);
                {
                    rl_subtopic.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtTopic.getText().toString().trim().length() == 0) {
                                Toast.makeText(getActivity(), "Please select Topic", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(!subTopicNameList.isEmpty() && subTopicNameList != null) {

                                    new AlertDialog.Builder(getActivity())
                                            .setAdapter(spinner_branch, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    edtSubtopic.setText(subTopicNameList.get(which));
                                                    selectedSubtopicId = subtopiclist.get(which).getSubtopicId();

                                                    dialog.dismiss();

                                                }
                                            }).create().show();
                                }

                                else
                                {

                                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
                                    alertDialog.setCancelable(false);
                                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                                    alertDialog.setMessage("No Sub Topic Found");
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
        else if (mMethod.equals(Const.SUB_TOPIC_LIST)) {
            Logger.putLogInfo("SUB_TOPIC_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                subtopiclist.clear();
                subTopicNameList.clear();
                subtopiclist.addAll(dataList.getSubtopicList());

                if (subtopiclist != null && !subtopiclist.isEmpty())
                {
                    for (int i =0 ; i < subtopiclist.size(); i++)
                    {
                        subTopicNameList.add(subtopiclist.get(i).getSubtopicName());
                    }
                    Logger.putLogInfo("SUB_TOPIC_LIST", subTopicNameList.toString());
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

