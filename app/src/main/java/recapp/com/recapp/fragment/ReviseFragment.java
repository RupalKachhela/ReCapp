package recapp.com.recapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recapp.com.recapp.CommunicationHandler.CommunicationHandler;
import recapp.com.recapp.R;
import recapp.com.recapp.activity.MainActivity;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.database.DBHelper;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;
import recapp.com.recapp.model.ReviseListData;

public class ReviseFragment extends android.support.v4.app.Fragment implements View.OnClickListener,CommunicationHandler.CommunicationHandlerCallBack
{

    TextView title,tvplayname;
    RelativeLayout relative ,rl_bottom;
    Button backBtn,btnHelp ,btnProceed;
    CheckBox cbMsg;
    LinearLayout ll_msg,ll_content;
    EditText edtSubject , edtCategory , edtTopic;
    List<String> urlList;
    List<ReviseListData>  reviseDataList;
    ImageView imgTopic;
    RelativeLayout btnPrevious,rl_topic;
    RelativeLayout btnNext;
    int count =0 ;
    ImageButton btnFilter;
    boolean checked;
    ArrayList<String>  contentList = new ArrayList<>();
    ArrayList<byte[]>  imagelist = new ArrayList<>();
    ArrayList<byte[]>  audioList = new ArrayList<>();
    MediaPlayer mPlayer;
    ReviseListData reviseDatamodel ;
    Button btnPlay , btnStop;
    ImageButton btnPlayBack;
    public static List<String> topicList;
    public static String mFileName = null;
    String topicname;

   /* RecyclerView recyclerview;
    ReviseListRecyclerAdapter reviseAdapter;
    */
   byte[] byteFile;

    ArrayList<String>  audioFileNameList = new ArrayList<>();
    String newFileName;

    List<String> topicNameList= new ArrayList<>();
    List<BatchDataModel> topiclist = new ArrayList<>();

    ArrayList<HashMap<String, String>> userList;
    ArrayList<HashMap<String, byte[]>> blobList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragmnet_revise,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPurple));


        title = getActivity().findViewById(R.id.txt_actionbar_title);
        relative = getActivity().findViewById(R.id.rl_action_bar);
        backBtn = getActivity().findViewById(R.id.btn_back);
        btnHelp = getActivity().findViewById(R.id.btn_help);
        rl_bottom = view.findViewById(R.id.rl_bottom_revise);
        cbMsg = view.findViewById(R.id.cb_showMsgAgain_revise);
        btnProceed = view.findViewById(R.id.btn_proceed_revise);
        ll_msg = view.findViewById(R.id.ll_revice_detail_msg);
        ll_content = view.findViewById(R.id.ll_revice_detail_audioplay);
        btnPrevious = view.findViewById(R.id.rl_prevoius);
        btnNext = view.findViewById(R.id.rl_next);
        imgTopic = view.findViewById(R.id.img_revise);
        btnFilter = view.findViewById(R.id.imgbtn_filter);
        edtSubject = view.findViewById(R.id.edt_selcet_subject_reviseFragment);
        edtCategory = view.findViewById(R.id.edt_selcet_category_reviseFragment);
        edtTopic = view.findViewById(R.id.edt_selcet_topic_reviseFragment);
        //btnPlay = view.findViewById(R.id.btn_rstart_revise);
        //btnStop = view.findViewById(R.id.btn_rpause_revise);

        btnPlay = view.findViewById(R.id.btn_rstart_record);
        btnStop = view.findViewById(R.id.btn_rpause_record);
        btnPlayBack = view.findViewById(R.id.btn_rprevious_revise);
        rl_topic = view.findViewById(R.id.rl_select_topic_record);
        tvplayname = view.findViewById(R.id.txt_play_name);


        backBtn.setVisibility(View.VISIBLE);
        btnHelp.setVisibility(View.VISIBLE);
        relative.setBackgroundColor(getResources().getColor(R.color.colorPurple));
        title.setText(R.string.revise_help);
        edtSubject.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT));
        edtCategory.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_CATEGORY));

        btnProceed.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        rl_topic.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);


        reviseDataList = new ArrayList<>();

        topicList = new ArrayList<>();
      /*  topicList.add("The Body");
        topicList.add("Cells");
        topicList.add("DNA");*/


        if(!edtSubject.getText().toString().isEmpty()) {
            callForTopicList();
        }

        preparedata();

        if(!reviseDataList.isEmpty())
        {
            try{
                Glide.with(getActivity())
                        .load(reviseDataList.get(0).getUrl())
                        .fitCenter()
                        .into(imgTopic);

                //topicname = userList.get(0).get("topic_name");
                topicname = audioFileNameList.get(0);
                tvplayname.setText(topicname);
            }

            catch (IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
        }

        if (RecappApplication.getInstance().isMessageChecked(Const.REVISE_CHECKED))
        {
            ll_content.setVisibility(View.VISIBLE);
            ll_msg.setVisibility(View.GONE);
            title.setText("Revise");
            rl_bottom.setVisibility(View.VISIBLE);
        }

//        edtSubject.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
//
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
//
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
//
//            });
//
//        }

        cbMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked)
                {

                   checked = true;

                }else
                {

                   checked = false;

                }
            }

        });

       /*
        btnProceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ll_msg.setVisibility(View.GONE);
                ll_content.setVisibility(View.VISIBLE);
                title.setText("Revise");
                rl_bottom.setVisibility(View.VISIBLE);

            }
        });*/

    }

    public void callForTopicList()
    {
        HashMap<String, String> params = new HashMap<>();
        // params.put("user_id", RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID));
        params.put("subject_id",RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT_ID));
        CommunicationHandler.getInstance(this).callForTopicList(getContext(),params);
        System.out.println("======PARAM : " + params);
    }

    public String preparedata()
    {
        /*ReviseListData dataModel = new ReviseListData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRH_kaGZnbIYXKQC4drhCzDMQG5QlRdRot-tECnhxel2Xu4SB7d","Xyz");
        reviseDataList.add(dataModel);
        dataModel = new ReviseListData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStlR_TtB4kjP10FUc8VgNbM7mcm41-G6rIHSb_7kStGb8mNA3x","Xyz");
        reviseDataList.add(dataModel);
        dataModel = new ReviseListData("https://media.istockphoto.com/vectors/types-of-poliovirus-the-brunnhild-virus-virus-lansing-the-virus-leon-vector-id834276932","Xyz");
        reviseDataList.add(dataModel);*/

        DBHelper db = new DBHelper(getActivity());
        if (db != null)
        {

            String subjectName = edtSubject.getText().toString().trim();
            String categoryName = RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID);
            System.out.println("====type : "+categoryName +" Subject : "+subjectName);
            if (subjectName.length() == 0 && categoryName.length() == 0) {

                Toast.makeText(getActivity(), "Select subject", Toast.LENGTH_SHORT).show();
            }else {

                userList = db.GetRecordListByCategory(subjectName, categoryName);

                blobList = db.retreiveAudioFromDBByCategory(subjectName, categoryName);

            }
            //ArrayList<HashMap<String, String>> userList = db.GetRecordList();
            //ArrayList<HashMap<String, byte[]>> blobList = db.retreiveAudioFromDB();

            if (userList != null && !userList.isEmpty() && userList.size() > 0)
            {
                System.out.println("===user list : " + userList);
                System.out.println("===user lis size: " + userList.size());

                for (int i = 0; i < userList.size(); i++)
                {
                    String value = userList.get(i).get("subject_name");
                    String audioType = userList.get(i).get("audio_type");
                    String file = userList.get(i).get("file_name");
                    contentList.add(file);

                    byteFile = blobList.get(i).get("audio_file");
                    byte[] byteImage = blobList.get(i).get("image_file");
                    audioList.add(byteFile);
                    imagelist.add(byteImage);

                    reviseDatamodel = new ReviseListData(file,byteImage,byteFile);
                    reviseDataList.add(reviseDatamodel);

                    mFileName = file;
                    mFileName += "/AudioRecording.3gp";

                }
            }
            System.out.println("===File name list: " + contentList);
            System.out.println("===Audio name list: " + audioList);
            System.out.println("===Image name list: " + imagelist);

            //get only file name from full path
            for(int j =0;j<contentList.size();j++)
            {
                System.out.println("====File name: " + contentList.get(j).endsWith(".3gp"));
                String path = contentList.get(j);
                String filename =  path.substring(path.lastIndexOf("/")+1);

                System.out.println("====FILE NAME : "+ filename);

                if (filename.indexOf(".") > 0) {
                    System.out.println("===NEW FILE NAME : " + filename.substring(0, filename.lastIndexOf(".")));
                    newFileName = filename.substring(0, filename.lastIndexOf("."));
                   // return filename.substring(0, filename.lastIndexOf("."));

                } else {

                    System.out.println("===NEW FILE NAME : " + filename);
                    newFileName = filename;
                    //return filename;
                }

                audioFileNameList.add(newFileName);
                System.out.println("====FILE NAME LIST : " +audioFileNameList);
            }

            //System.out.println("====FILE NAME LIST : " +audioFileNameList);
        }
        return null;
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {

            case R.id.rl_prevoius:
            {

                //System.out.println("==prev =list size : "+reviseDataList.size());
                //System.out.println("==prev =list pos : "+reviseDataList.get(count));
                //System.out.println("==prev count : "+count+ "  data  : "+reviseDataList.get(count).getUrl());
                try {
                    if (count > 0) {
                        count--;
                        Glide.with(getActivity())
                                .load(reviseDataList.get(count).getUrl())
                                .fitCenter()
                                .into(imgTopic);


                        byteFile = blobList.get(count).get("audio_file");
                        topicname = audioFileNameList.get(count);
                        //topicname = userList.get(count).get("topic_name");
                        tvplayname.setText(topicname);

                    }
                    else {

                    }
                }
                catch (IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }

                break;

            }
            case R.id.rl_next:
            {

               // System.out.println("===list size : "+reviseDataList.size());
//                System.out.println("===list pos : "+reviseDataList.get(count));

               // System.out.println("==count : "+count+ "  data  : "+reviseDataList.get(count).getUrl());

                try {

                if (count <  reviseDataList.size()-1)
                {

                    count++;
                    Glide.with(getActivity())
                            .load(reviseDataList.get(count).getUrl())
                            .fitCenter()
                            .into(imgTopic);

                    byteFile = blobList.get(count).get("audio_file");
                    topicname = audioFileNameList.get(count);
                    //topicname = userList.get(count).get("topic_name");
                    tvplayname.setText(topicname);
                }else
                {

                }
                }
                catch (IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
                break;

            }

            case R.id.btn_rstart_record:
            {
                mPlayer = new MediaPlayer();
                Toast.makeText(getActivity(), "Recording Started Playing", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.btn_rpause_record:
            {
                //Toast.makeText(getContext(),"CLICKED STOP",Toast.LENGTH_SHORT).show();
                if(mPlayer == null)
                {
                    Toast.makeText(getActivity(),"First do Start Play Recording", Toast.LENGTH_SHORT).show();
                }

                else {

                    mPlayer.release();
                    mPlayer = null;
                    Toast.makeText(getActivity(), "Recording Stop", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.btn_proceed_revise:
            {
                RecappApplication.getInstance().setSPValueByKey(Const.REVISE_CHECKED , checked);
                ll_msg.setVisibility(View.GONE);
                ll_content.setVisibility(View.VISIBLE);
                title.setText("Revise");
                rl_bottom.setVisibility(View.VISIBLE);

                break;

            }
            case R.id.btn_back:
            {
                if (getActivity() != null && !getActivity().isFinishing())
                {
                    Intent homeIntent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(homeIntent);
                    getActivity().finish();
                }

                break;

            }
            case R.id.imgbtn_filter:
            {

                HomeFragment.showPopup(v,getActivity());
                break;

            }

            case R.id.rl_select_topic_record:
            {
                final ArrayAdapter<String> spinner_topic = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,topicNameList);
                {
                    System.out.println("====inside array adpater click ===" + topicNameList);

                    if(!topicNameList.isEmpty() && topicNameList != null) {
                        System.out.println("====click subject ===");
                        new AlertDialog.Builder(getActivity())
                                .setAdapter(spinner_topic, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        edtTopic.setText(topicNameList.get(which));
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

                break;
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

    private void playMp3(byte[] mp3SoundByteArray)
    {
        try
        {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("recappnew", "mp3", getActivity().getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            mPlayer.reset();

            FileInputStream fis = new FileInputStream(tempMp3);
            mPlayer.setDataSource(fis.getFD());

            mPlayer.prepare();
            mPlayer.start();

        } catch (IOException ex)
        {
            String s = ex.toString();
            ex.printStackTrace();
        }

    }


  /*  public void bindRecyclerview()
    {
        preparedata();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(layoutManager);
        reviseAdapter = new ReviseListRecyclerAdapter(getActivity(), reviseDataList);
        recyclerview.setAdapter(reviseAdapter);
        reviseAdapter.notifyDataSetChanged();
    }


    public void preparedata()
    {
        ReviseListData dataModel = new ReviseListData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRH_kaGZnbIYXKQC4drhCzDMQG5QlRdRot-tECnhxel2Xu4SB7d","Xyz");
        reviseDataList.add(dataModel);
        dataModel = new ReviseListData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStlR_TtB4kjP10FUc8VgNbM7mcm41-G6rIHSb_7kStGb8mNA3x","Xyz");
        reviseDataList.add(dataModel);
        dataModel = new ReviseListData("https://media.istockphoto.com/vectors/types-of-poliovirus-the-brunnhild-virus-virus-lansing-the-virus-leon-vector-id834276932","Xyz");
        reviseDataList.add(dataModel);
    }*/

}
