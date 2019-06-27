package recapp.com.recapp.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recapp.com.recapp.CommunicationHandler.CommunicationHandler;
import recapp.com.recapp.R;
import recapp.com.recapp.activity.Category_CollegeActivity;
import recapp.com.recapp.activity.LoginActivity;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;

public class AddSubjectDetailHomeFragment extends android.support.v4.app.Fragment implements CommunicationHandler.CommunicationHandlerCallBack
{

    RelativeLayout rl_subject , rl_subjectDetail,rl_addSubject;
    TextView title;
    RelativeLayout relative;
    Button backBtn,btnHelp;
    EditText edtSelectSubject , edtSelectTopic ,edtSelectTopicName ,edtSelectAudioType;
    RelativeLayout rl_startRecording;
    List<String> subjectList = new ArrayList<>();
    List<String> topicNameList= new ArrayList<>();
    List<String> subTopicNameList= new ArrayList<>();
    List<String> audioTypeList= new ArrayList<>();
    String subject , topic , subTopic , audio;

    List<BatchDataModel> subjectlist = new ArrayList<>();
    List<BatchDataModel> topiclist = new ArrayList<>();
    List<BatchDataModel> subtopiclist = new ArrayList<>();

    String selectedSubId,selectedTopicId,selectedSubtopicId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragmnet_addsubject_home,container,false);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

     /*   title = view.findViewById(R.id.txt_actionbar_title);
        relative = view.findViewById(R.id.rl_action_bar);
        backBtn = view.findViewById(R.id.btn_back);
        btnHelp = view.findViewById(R.id.btn_help);
        rl_addSubject = view.findViewById(R.id.card_addSubject_home);
        rl_subject = view.findViewById(R.id.rl_add_newSubject);*/
        rl_subjectDetail = view.findViewById(R.id.rl_add_SubjectDetail);
        edtSelectSubject = view.findViewById(R.id.edt_select_subName_SubjectDetail);
        edtSelectTopic = view.findViewById(R.id.edt_select_topic_SubjectDetail);
        edtSelectTopicName = view.findViewById(R.id.edt_select_topicname_SubjectDetail);
        edtSelectAudioType = view.findViewById(R.id.edt_select_audioType_SubjectDetail);
        rl_startRecording = view.findViewById(R.id.rl_startRecording_addSubject);

//        backBtn.setVisibility(View.VISIBLE);
        // relative.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        //  title.setText("Add Subject");

        edtSelectSubject.setHint("Enter Subject");
        edtSelectTopic.setHint("Enter Chapter");
        edtSelectTopicName.setHint("Enter Topic");

        // prepareData();

        callForSubjectList();


        audioTypeList = new ArrayList<>();
        audioTypeList.add("Question and Answer");
        audioTypeList.add("Definitions");
        audioTypeList.add("Explanation");
        audioTypeList.add("Summary");

      /*  rl_addSubject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                rl_subjectDetail.setVisibility(View.VISIBLE);

            }
        });
*/
        rl_startRecording.setOnClickListener(new View.OnClickListener()
        {

            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v)
            {
                if (edtSelectSubject.getText().toString().length() == 0)
                {
                    Toast.makeText(getActivity(), "Select Subject", Toast.LENGTH_SHORT).show();
                }else if (edtSelectTopic.getText().toString().length() == 0)
                {
                    Toast.makeText(getActivity(), "Select Topic name", Toast.LENGTH_SHORT).show();
                }else  if (edtSelectTopicName.getText().toString().length() == 0)
                {
                    Toast.makeText(getActivity(), "Select Sub Topic name", Toast.LENGTH_SHORT).show();
                }else  if (edtSelectAudioType.getText().toString().length() == 0)
                {
                    Toast.makeText(getActivity(), "Select Audio type", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Bundle bundle=new Bundle();
                    bundle.putString("subject",edtSelectSubject.getText().toString().trim());
                    bundle.putString("topic_name",edtSelectTopic.getText().toString().trim());
                    bundle.putString("subtopic",edtSelectTopicName.getText().toString().trim());
                    bundle.putString("audioType",edtSelectAudioType.getText().toString().trim());
                    bundle.putString("isFrom","AddSubject");

                    RecordFragment fragment = new RecordFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main_container, fragment);
                    fragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(HomeFragment.TAG);
                    fragmentTransaction.commit();


                    Log.e("===Recapp : add subject ","Subject :- "+subject +"\t TopicName :- "+topic+"\t SubTopicName :-"+subTopic+"\t Audio :- "+audio);
                }
            }

        });


        final ArrayAdapter<String> audioType = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, audioTypeList);
        {
            System.out.println("====inside array adpater click ==="+audioTypeList);


            edtSelectAudioType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        System.out.println("====click subject ===");
                        new AlertDialog.Builder(getActivity())

                                .setAdapter(audioType, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        edtSelectAudioType.setText(audioTypeList.get(which));
                                        audio = edtSelectAudioType.getText().toString().trim();

                                        dialog.dismiss();

                                    }
                                }).create().show();

                }
            });

        }

        //choose subject
/*
        final ArrayAdapter<String> subjectname = new  ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item, subjectList);
        {
            edtSelectSubject.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    if(!subjectList.isEmpty() && subjectList != null) {
                        try {
                            new AlertDialog.Builder(getActivity())
                                    .setAdapter(subjectname, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            edtSelectSubject.setText(subjectList.get(which));
                                            selectedSubId = subjectlist.get(which).getSubjectId();
                                            System.out.println("===Selected subject id ==> " + selectedSubId);

                                            if (!edtSelectTopic.getText().toString().isEmpty()) {
                                                edtSelectTopic.setText("");
                                                topiclist.clear();
                                                topicNameList.clear();
                                                callForTopicList(selectedSubId);
                                            }

                                            if (!edtSelectTopicName.getText().toString().isEmpty()) {
                                                edtSelectTopicName.setText("");
                                                subtopiclist.clear();
                                                subTopicNameList.clear();
                                            } else {
                                                callForTopicList(selectedSubId);
                                            }

                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }

                        catch (IndexOutOfBoundsException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else {

                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getResources().getString(R.string.app_name));
                        alertDialog.setMessage("No Subject Found");
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
*/


       /* final ArrayAdapter<String> topicName = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, topicNameList);
        {
            System.out.println("====inside array adpater click ==="+topicNameList);

            edtSelectTopic.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    System.out.println("====click subject ===");
                    new AlertDialog.Builder(getActivity())
                            .setAdapter(topicName, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {

                                    edtSelectTopic.setText(topicNameList.get(which));
                                    topic = edtSelectTopic.getText().toString().trim();

                                    dialog.dismiss();

                                }
                            }).create().show();
                }

            });
        }*/

      /*  final ArrayAdapter<String> subtopicName = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, subTopicNameList);
        {
            System.out.println("====inside array adpater click ==="+subTopicNameList);

            edtSelectTopicName.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    System.out.println("====click subject ===");
                    new AlertDialog.Builder(getActivity())
                            .setAdapter(subtopicName, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {

                                    edtSelectTopicName.setText(subTopicNameList.get(which));
                                    subTopic = edtSelectTopicName.getText().toString().trim();

                                    dialog.dismiss();

                                }
                            }).create().show();
                }

            });
        }*/

       /* final ArrayAdapter<String> audioType = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, audioTypeList);
        {
            System.out.println("====inside array adpater click ==="+audioTypeList);

            edtSelectAudioType.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    System.out.println("====click subject ===");
                    new AlertDialog.Builder(getActivity())

                            .setAdapter(audioType, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    edtSelectAudioType.setText(audioTypeList.get(which));
                                    audio = edtSelectAudioType.getText().toString().trim();

                                    dialog.dismiss();

                                }
                            }).create().show();
                }
            });
        }*/
    }

    public void callForSubjectList()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id",RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID));
        params.put("subcategory_id",RecappApplication.getInstance().getSPValueByKey(Const.SUB_CATEGORY_ID));
        CommunicationHandler.getInstance(this).callForSubjectList(getContext(),params);
        System.out.println("======PARAM : " + params);

    }

    public void callForTopicList(String selectedSubId)
    {
        HashMap<String, String> params = new HashMap<>();
       // params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID));
        params.put("subject_id",selectedSubId);
        CommunicationHandler.getInstance(this).callForTopicList(getContext(),params);
        System.out.println("======PARAM : " + params);
    }

    public void callForSubTopicList(String selectedTopicId)
    {
        HashMap<String, String> params = new HashMap<>();
       // params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID));
        params.put("subject_id",selectedSubId);
        params.put("topic_id",selectedTopicId);
        CommunicationHandler.getInstance(this).callForSubTopicList(getContext(),params);
        System.out.println("======PARAM : " + params);
    }

    public void prepareData()
    {
        subjectList = new ArrayList<>();
        subjectList.add("Biology");
        subjectList.add("Maths");
        subjectList.add("Physics");
        subjectList.add("Chemistry");

        topicNameList = new ArrayList<>();
        topicNameList.add("The Body");
        topicNameList.add("Cells");
        topicNameList.add("DNA");

        subTopicNameList = new ArrayList<>();
        subTopicNameList.add("Sub Topic 1");
        subTopicNameList.add("Sub Topic 2");
        subTopicNameList.add("Sub Topic 3");
        subTopicNameList.add("Sub Topic 4");

        audioTypeList = new ArrayList<>();
        audioTypeList.add("Question and Answer");
        audioTypeList.add("Definitions");
        audioTypeList.add("Explanation");
        audioTypeList.add("Summary");

    }

    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        if (mMethod.equals(Const.SUBJECT_LIST)) {
            Logger.putLogInfo("BatchResponse ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                try {
                    subjectlist.clear();
                    subjectList.clear();
                }catch (NullPointerException e)
                {
                    e.getMessage();
                }
                subjectlist.addAll(dataList.getSubjectList());

                if (subjectlist != null && !subjectlist.isEmpty())
                {
                    for (int i =0 ; i < subjectlist.size(); i++)
                    {
                        subjectList.add(subjectlist.get(i).getSubjectName());
                    }

                    Logger.putLogInfo("subject_List==>", subjectList.toString());
                }

                final ArrayAdapter<String> spinner_semester = new  ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, topicNameList);
                {
                    edtSelectTopic.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtSelectSubject.getText().toString().length() == 0) {

                                Toast.makeText(getContext(), "Select Subject", Toast.LENGTH_SHORT).show();

                            }else {

                                if(!topicNameList.isEmpty() && topicNameList != null) {
                                    new AlertDialog.Builder(getActivity())
                                            .setAdapter(spinner_semester, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    edtSelectTopic.setText(topicNameList.get(which));
                                                    selectedTopicId = topiclist.get(which).getTopicId();

                                                    if (!edtSelectTopicName.getText().toString().isEmpty()) {
                                                        edtSelectTopicName.setText("");
                                                        try {
                                                            subtopiclist.clear();
                                                            subTopicNameList.clear();
                                                        }catch (NullPointerException e)
                                                        {
                                                            e.getMessage();
                                                        }
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
                    });
                }

            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }
        }else  if (mMethod.equals(Const.TOPIC_LIST)) {
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
                    edtSelectTopicName.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {

                            if (edtSelectSubject.getText().toString().length() == 0) {

                                Toast.makeText(getContext(), "Select topic name", Toast.LENGTH_SHORT).show();

                            }else {
                                if (!subTopicNameList.isEmpty() && subTopicNameList != null) {
                                    new AlertDialog.Builder(getActivity())
                                            .setAdapter(spinner_branch, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    edtSelectTopicName.setText(subTopicNameList.get(which));
                                                    selectedSubtopicId = subtopiclist.get(which).getSubtopicId();

                                                    dialog.dismiss();

                                                }
                                            }).create().show();
                                } else {
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
                try {
                    subtopiclist.clear();
                    subTopicNameList.clear();
                }catch (NullPointerException e)
                {
                    e.getMessage();
                }
                subtopiclist.addAll(dataList.getSubtopicList());

                if (subtopiclist != null && !subtopiclist.isEmpty())
                {
                    for (int i =0 ; i < subtopiclist.size(); i++)
                    {
                        subTopicNameList.add(subtopiclist.get(i).getSubtopicName());

                    }
                    Logger.putLogInfo("SUB_TOPIC_LIST==>", subTopicNameList.toString());

                }

               /* final ArrayAdapter<String> audioType = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, audioTypeList);
                {
                    System.out.println("====inside array adpater click ==="+audioTypeList);


                        edtSelectAudioType.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (edtSelectSubject.getText().toString().length() == 0) {

                                    Toast.makeText(getContext(), "Select sub topic name", Toast.LENGTH_SHORT).show();

                                }else {
                                    System.out.println("====click subject ===");
                                    new AlertDialog.Builder(getActivity())

                                            .setAdapter(audioType, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    edtSelectAudioType.setText(audioTypeList.get(which));
                                                    audio = edtSelectAudioType.getText().toString().trim();

                                                    dialog.dismiss();

                                                }
                                            }).create().show();
                                }
                            }
                        });

                }*/

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
