package recapp.com.recapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import recapp.com.recapp.activity.MainActivity;
import recapp.com.recapp.adapter.DeleteRecordListRecyclerAdapter;
import recapp.com.recapp.adapter.UpdateDataAdapter;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.database.DBHelper;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.interfaces.ItemClickListener;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;
import recapp.com.recapp.model.DeleteRecordListData;

public class DeleteFragment extends android.support.v4.app.Fragment implements ItemClickListener ,UpdateDataAdapter.UpdateDataClickListener, CommunicationHandler.CommunicationHandlerCallBack
{
    EditText edtSubject , edtcategory , edttopic;
    RecyclerView mRecyclerView;
    DeleteRecordListRecyclerAdapter deleteAdpater;
    List<DeleteRecordListData>  recordList ;
    public static  ArrayList<Integer>  deleteArray = new ArrayList<>();
    Button btnYesDelete , btnNoDelete;
    ImageButton btnFilter;
    UpdateDataAdapter mAdapter;
    private static final int MULTIPLE = 0;
    private static final int SINGLE = 1;
    DeleteRecordListData dataModel;
    List<Integer>  deleteList = new ArrayList<>();
    RelativeLayout rl_topic;
    public static List<String> topicList;

    List<String> topicNameList= new ArrayList<>();
    List<BatchDataModel> topiclist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragmnet_delete,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

        recordList = new ArrayList<>();
        TextView title = getActivity().findViewById(R.id.txt_actionbar_title);
        RelativeLayout relative = getActivity().findViewById(R.id.rl_action_bar);
        Button backBtn = getActivity().findViewById(R.id.btn_back);
        Button btnHelp = getActivity().findViewById(R.id.btn_help);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        btnNoDelete = view.findViewById(R.id.btn_No_delete);
        btnYesDelete = view.findViewById(R.id.btn_yes_delete);
        btnFilter = view.findViewById(R.id.imgbtn_filter);
        edtcategory = view.findViewById(R.id.edt_selcet_category_deleteFragment);
        edtSubject = view.findViewById(R.id.edt_selcet_subject_deleteFragment);
        edttopic = view.findViewById(R.id.edt_selcet_topic_deleteFragment);
        rl_topic = view.findViewById(R.id.rl_select_topic_record);

        edtcategory.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_CATEGORY));
        edtSubject.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT));
        backBtn.setVisibility(View.VISIBLE);
        btnHelp.setVisibility(View.VISIBLE);

        relative.setBackground(getResources().getDrawable(R.drawable.bg_orange_gradient_90));
        title.setText("Delete");

        topicList = new ArrayList<>();
       /* topicList.add("The Body");
        topicList.add("Cells");
        topicList.add("DNA");*/

        if(!edtSubject.getText().toString().isEmpty()) {
            callForTopicList();
        }

       /* DBHelper db = new DBHelper(getActivity());
        if (db != null) {
            ArrayList<HashMap<String, String>> userList = db.GetRecordList();
            ArrayList<HashMap<String, byte[]>> audioList = db.retreiveAudioFromDB();

            if (userList != null && !userList.isEmpty() && userList.size() > 0) {

                System.out.println("===user list : " + userList);
                System.out.println("===user lis size: " + userList.size());

                for (int i = 0; i < userList.size(); i++) {
                    String value = userList.get(i).get("subject_name");
                    String audioType = userList.get(i).get("audio_type");
                    String file = userList.get(i).get("file_name");
                   // contentList.add(file);

//                    byte[] byteFile = audioList.get(i).get("audio_file");
//                    byte[] byteImage = audioList.get(i).get("image_file");
                   *//* AUDIOLIST.add(byteFile);
                    imagelist.add(byteImage);*//*
                   dataModel = new DeleteRecordListData(value,audioType,file);
                    recordList.add(dataModel);

                }
            }
        }*/
        bindRecyclerview();


//        edtSubject.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
//
//        System.out.println("===list ==="+HomeFragment.subjectList);
//
//        final ArrayAdapter<String> spinner_breed = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, HomeFragment.subjectList);
//        {
//
//            System.out.println("====inside array adpater click ==="+HomeFragment.subjectList);
//
//            edtSubject.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//
//                    System.out.println("====click subject ===");
//
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
//
//            });
//        }



        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (getActivity() != null && !getActivity().isFinishing())
                {

                    Intent homeIntent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(homeIntent);
                    getActivity().finish();
                }

            }
        });

        rl_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayAdapter<String> spinner_topic = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,topicNameList);
                {
                    System.out.println("====inside array adpater click ===" + topicNameList);

                    if(!topicNameList.isEmpty() && topicNameList != null) {
                        System.out.println("====click subject ===");
                        new AlertDialog.Builder(getActivity())
                                .setAdapter(spinner_topic, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        edttopic.setText(topicNameList.get(which));
                                        String selectedTopicId = topiclist.get(which).getTopicId();

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

        btnYesDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*if(DeleteFragment.deleteArray != null && !DeleteFragment.deleteArray.isEmpty())
                {
                    for (int i =0 ; i < DeleteFragment.deleteArray.size() ; i++)
                    {
                        recordList.remove(i);
                        deleteAdpater.notifyItemRemoved(i);
                        deleteAdpater.notifyItemRangeChanged(i, recordList.size());

                    }

                }
                DeleteFragment.deleteArray.clear();*/

                try {

                    if (deleteList != null && !deleteList.isEmpty()) {
                        for (int i = 0; i < deleteList.size(); i++) {
                            recordList.remove(i);
                            mAdapter.notifyItemRemoved(i);
                            mAdapter.notifyItemRangeChanged(i, recordList.size());

                        }

                    }
                    deleteList.clear();
                }
                catch (IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
        });


        btnNoDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(deleteList != null && !deleteList.isEmpty())
                {
                    for (int i =0 ; i <deleteList.size() ; i++)
                    {
                        UpdateDataAdapter.sSelectedItems.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                }
                deleteList.clear();
            }

        });

        btnFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HomeFragment.showPopup(v,getActivity());
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


//    public void bindRecyclerview()
//    {
//        preparedata();
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(layoutManager);
//        deleteAdpater = new DeleteRecordListRecyclerAdapter(getActivity(), recordList);
//        deleteAdpater.setListener(this);
//        mRecyclerView.setAdapter(deleteAdpater);
//        deleteAdpater.notifyDataSetChanged();
//    }

    public void bindRecyclerview()
    {
      preparedata();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new UpdateDataAdapter((ArrayList<DeleteRecordListData>) recordList,getContext(), MULTIPLE);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnemClickListener(this);

//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(layoutManager);
//        deleteAdpater = new DeleteRecordListRecyclerAdapter(getActivity(), recordList);
//        deleteAdpater.setListener(this);
//        mRecyclerView.setAdapter(deleteAdpater);
//        deleteAdpater.notifyDataSetChanged();
    }

   public void preparedata()
    {
        DeleteRecordListData dataModel = new DeleteRecordListData("DNA prep","DNA makeup","My qs");
        recordList.add(dataModel);
        dataModel = new DeleteRecordListData("DNA prep","DNA makeup","My qs");
        recordList.add(dataModel);
        dataModel = new DeleteRecordListData("DNA prep","DNA makeup","My qs");
        recordList.add(dataModel);
    }

    @Override
    public void listItemClick(int position)
    {

    }

    @Override
    public void listItemClick(int position, String eventType)
    {

    }

    @Override
    public void onItemClick(int position)
    {
        mAdapter.selected(position);

        System.out.println("==="+recordList.get(position).getChapterName());

        deleteList.add(position);
        System.out.println("===selected array "+deleteList);

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
                    Logger.putLogInfo("TOPIC_LIST_LIST ==>", topicNameList.toString());

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
 /*   private ArrayList<DeleteRecordListData> getDataSet()
    {
        ArrayList results = new ArrayList<>();
        for (int index = 0; index < 20; index++)
        {
            DeleteRecordListData obj = new UpdateData("Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }
        return results;
    }*/
}
