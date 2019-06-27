package recapp.com.recapp.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recapp.com.recapp.CommunicationHandler.CommunicationHandler;
import recapp.com.recapp.R;
import recapp.com.recapp.activity.MainActivity;
import recapp.com.recapp.activity.Utils;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;


public class RecordFragment extends android.support.v4.app.Fragment implements View.OnClickListener,CommunicationHandler.CommunicationHandlerCallBack
{

    EditText edtSubject , edtTopic , edtCategory,edtSubtopic;
    TextView title;
    RelativeLayout relative ,rl_nextAction;
    Button backBtn,btnHelp ,btnProceed;
    CheckBox cbMsg;
    LinearLayout ll_msg,ll_content;
    ImageView imgAddContentPhoto;
    ImageButton imgbtnStart ,imgbtnPrevious ,imgbtnPause , imgbtnPlay ,imgbtnStop;
    private int GALLERY = 1;
    private static final int REQUEST_CAMERA = 0;
    Bitmap bitmap;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    public static String mFileName = null;
    RelativeLayout rl_edit , rl_delete , rl_topic,rl_subtopic;
    ImageButton btnFilter;
    boolean checked = false;
    boolean btnPalyClicked = false;
    boolean btnPauseClicked = false;
    boolean btnStopClicked = false;
    boolean btnStartClicked = false;
    boolean btnPreviosClicked = false;
    int length = 0;
    byte[] byteImage;
    public static List<String> topicList;

    public static List<String> subtopicList;
    String audioType;

    List<String> topicNameList= new ArrayList<>();
    List<String> subTopicNameList= new ArrayList<>();

    List<BatchDataModel> topiclist = new ArrayList<>();
    List<BatchDataModel> subtopiclist = new ArrayList<>();
    String selectedTopicId,selectedSubtopicId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragmnet_record,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPink));

         title = getActivity().findViewById(R.id.txt_actionbar_title);
         relative = getActivity().findViewById(R.id.rl_action_bar);
         backBtn = getActivity().findViewById(R.id.btn_back);
         btnHelp = getActivity().findViewById(R.id.btn_help);
         rl_nextAction = view.findViewById(R.id.rl_next_record);
         edtSubject = view.findViewById(R.id.edt_selcet_subject_recordFragment);
        edtCategory = view.findViewById(R.id.edt_selcet_category_recordFragment);
        edtTopic = view.findViewById(R.id.edt_selcet_topic_recordFragment);
        edtSubtopic = view.findViewById(R.id.edt_selcet_subtopic_recordFragment);

        cbMsg = view.findViewById(R.id.cb_showMsgAgain_record);
        btnProceed = view.findViewById(R.id.btn_proceed_record);
        ll_msg = view.findViewById(R.id.ll_record_detail_msg);
        ll_content = view.findViewById(R.id.ll_record_detail_audioplay);
        imgbtnStart = view.findViewById(R.id.btn_rstart_record);
        imgbtnPrevious = view.findViewById(R.id.btn_rprevious_record);
        imgbtnPause = view.findViewById(R.id.btn_rpause_record);
        imgbtnStop = view.findViewById(R.id.btn_rstop_record);
        imgbtnPlay = view.findViewById(R.id.btn_rplay_record);
        imgAddContentPhoto = view.findViewById(R.id.img_addphoto_record);
        rl_edit= view.findViewById(R.id.img_edit_record);
        rl_delete = view.findViewById(R.id.img_delete_record);
        btnFilter = view.findViewById(R.id.imgbtn_filter);
        rl_topic = view.findViewById(R.id.rl_select_topic_record);
        rl_subtopic = view.findViewById(R.id.rl_select_subtopic_record);

        backBtn.setVisibility(View.VISIBLE);
        btnHelp.setVisibility(View.INVISIBLE);
        relative.setBackgroundColor(getResources().getColor(R.color.colorPink));
        title.setText(R.string.record_help);
        edtSubject.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT));
        edtCategory.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_CATEGORY));


        imgbtnPause.setEnabled(false);
        imgbtnPrevious.setEnabled(false);
        imgbtnPlay.setEnabled(false);
        imgbtnStop.setEnabled(false);

        if(!edtSubject.getText().toString().isEmpty()) {
            callForTopicList();
        }

        try {

            String isfrom = getArguments().getString("isFrom");

            if(isfrom.equals(null)) {

            } else {
                String subject = getArguments().getString("subject");
                String topic = getArguments().getString("topic_name");
                String subtopic = getArguments().getString("subtopic");
                audioType = getArguments().getString("audioType");

                System.out.println("=====AUDIO TYPE========" +audioType);

                Utils.audioType = audioType;
                edtSubject.setText(subject);
                edtTopic.setText(topic);
                edtSubtopic.setText(subtopic);
            }

        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        topicList = new ArrayList<>();
        /*topicList.add("The Body");
        topicList.add("Cells");
        topicList.add("DNA");*/


        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/AudioRecording.3gp";

        if (hasImage(imgAddContentPhoto))
        {
            rl_edit.setVisibility(View.VISIBLE);
            rl_delete.setVisibility(View.VISIBLE);
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
//            });
//        }


        if ( RecappApplication.getInstance().isMessageChecked(Const.RECOED_CHECKED))
        {
            ll_content.setVisibility(View.VISIBLE);
            ll_msg.setVisibility(View.GONE);
            title.setText("Record");

            requestMultiplePermissions();

        }

        cbMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if(isChecked)
                {
                   checked = true;

                }else
                {
                    checked= false;
                }
            }
        });


        btnProceed.setOnClickListener(this);
        rl_nextAction.setOnClickListener(this);
        imgAddContentPhoto.setOnClickListener(this);
        imgbtnPrevious.setOnClickListener(this);
        imgbtnStart.setOnClickListener(this);
        imgbtnPlay.setOnClickListener(this);
        imgbtnPause.setOnClickListener(this);
        imgbtnStop.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        rl_edit.setOnClickListener(this);
        rl_delete.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        rl_topic.setOnClickListener(this);
        rl_subtopic.setOnClickListener(this);

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

    public byte[] converFileToByte()
    {
        File file = new File(mFileName);
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];

        try {
            FileInputStream fis = new FileInputStream(file);

            fis.read(bytesArray);
            fis.close();
        } catch (IOException aE) {
            aE.printStackTrace();
        }

        return bytesArray;
    }
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_proceed_record:
            {
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

                else if(audioType == null && HomeFragment.selectedAudioType == null)
                {
                    Toast.makeText(getActivity(), "Please select Audio Type", Toast.LENGTH_SHORT).show();
                }

                else {
                    RecappApplication.getInstance().setSPValueByKey(Const.RECOED_CHECKED, checked);
                    ll_msg.setVisibility(View.GONE);
                    ll_content.setVisibility(View.VISIBLE);
                    title.setText("Record");
                    requestMultiplePermissions();
                }
                break;

            }
            case  R.id.rl_next_record:
            {
                if(mFileName == null)
                {
                    Toast.makeText(getContext(),"Please Record something..",Toast.LENGTH_SHORT).show();
                }

                else if(byteImage==null)
                {
                    Toast.makeText(getContext(),"Please Select Image..",Toast.LENGTH_LONG).show();
                }

                else {
                    RecordConfirmFragment fragment = new RecordConfirmFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("audio_file", mFileName);
                    bundle.putByteArray("image_file", byteImage);
                    bundle.putString("subject", edtSubject.getText().toString().trim());
                    bundle.putString("topic", edtTopic.getText().toString().trim());
                    bundle.putString("subtopic", edtSubtopic.getText().toString().trim());
                    bundle.putString("audioType",audioType);
                   // bundle.putString("audioType",HomeFragment.selectedAudioType);

                    System.out.println("====bundle value : " + bundle);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                break;

            }

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

                    final ArrayAdapter<String> spinner_subtopic = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, RecordFragment.subtopicList);
                    {
                        System.out.println("====inside array adpater click ===" + HomeFragment.subjectList);

                        System.out.println("====click subject ===");
                        new AlertDialog.Builder(getActivity())
                                .setAdapter(spinner_subtopic, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        edtSubtopic.setText(RecordFragment.subtopicList.get(which));
                                        dialog.dismiss();

                                    }
                                }).create().show();
                    }
                }

                break;

            }*/

        /*    case  R.id.btn_rprevious_record:
            {
//                imgbtnPause.setEnabled(false);
//                imgbtnRestart.setEnabled(false);
//                imgbtnStop.setEnabled(false);
//                imgbtnPlay.setEnabled(true);
//                mRecorder = new MediaRecorder();
//                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                mRecorder.setOutputFile(mFileName);
//                try
//                {
//                    mRecorder.prepare();
//
//                } catch (IOException e)
//                {
//                    Log.e("AudioCapture", "prepare() failed");
//                }
//
//                mRecorder.start();
                Toast.makeText(getActivity(), "Recording Previos", Toast.LENGTH_LONG).show();

                break;
            }
            case  R.id.btn_rstop_record:
            {
                if (imgbtnPlay.isSelected())
                {
                    imgbtnRestart.setEnabled(false);
                    imgbtnStop.setEnabled(false);
                    imgbtnPlay.setSelected(false);

                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder = null;
                }else
                {
                    imgbtnRestart.setEnabled(false);
                    imgbtnStart.setEnabled(false);
                    imgbtnPlay.setEnabled(false);

                    imgbtnPlay.setSelected(true);

                }

                mPlayer.release();
                mPlayer = null;
                Toast.makeText(getActivity(),"Playing Audio Saved", Toast.LENGTH_SHORT).show();

                break;
            }
            case  R.id.btn_rstart_record:
            {
                imgbtnPause.setEnabled(false);
                imgbtnRestart.setEnabled(false);
                imgbtnStop.setEnabled(false);
                imgbtnPlay.setEnabled(true);
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile(mFileName);
                try
                {
                    mRecorder.prepare();

                } catch (IOException e)
                {
                    Log.e("AudioCapture", "prepare() failed");
                }

                mRecorder.start();
                Toast.makeText(getActivity(),"Recording Started", Toast.LENGTH_SHORT).show();

                break;
            }
            case  R.id.btn_rpause_record:
            {
                imgbtnPause.setEnabled(false);
                imgbtnRestart.setEnabled(false);
                imgbtnStop.setEnabled(true);
                imgbtnPlay.setEnabled(false);

                Toast.makeText(getActivity(), "Recording pause", Toast.LENGTH_LONG).show();
                break;

            }
            case  R.id.btn_rplay_record:
            {
                mPlayer = new MediaPlayer();
                try
                {
                    imgbtnPause.setEnabled(true);
                    imgbtnRestart.setEnabled(true);
                    imgbtnStart.setEnabled(false);
                    imgbtnPlay.setEnabled(false);

                    mPlayer.setDataSource(mFileName);
                    mPlayer.prepare();
                    mPlayer.start();
                    Toast.makeText(getActivity(), "Recording Started Playing", Toast.LENGTH_LONG).show();

                } catch (IOException e)
                {
                    Log.e("AudioCapture", "prepare() failed");
                }

                break;
            }*/
            case  R.id.img_addphoto_record:
            {
                showPictureDialog();
                break;
            }
            case  R.id.btn_back:
            {
                if (getActivity() != null && !getActivity().isFinishing())
                {

                    Intent homeIntent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(homeIntent);
                    getActivity().finish();
                }
                break;
            }
            case R.id.img_delete_record:
            {
                imgAddContentPhoto.setImageDrawable(null);

                break;

            }
            case R.id.img_edit_record:
            {
                showPictureDialog();

                break;

            }
            case R.id.imgbtn_filter:
            {
                HomeFragment.showPopup(v,getActivity());

                break;

            }

            case  R.id.btn_rprevious_record:
            {

                Toast.makeText(getActivity(), "Recording Previous", Toast.LENGTH_LONG).show();

                break;

            }

            case  R.id.btn_rstop_record:
            {

                btnStopClicked = true;

                if (btnStartClicked == true)
                {
                    imgbtnPause.setEnabled(false);
                    imgbtnStop.setEnabled(false);
                    imgbtnStart.setEnabled(true);
                    imgbtnPlay.setEnabled(true);
                    rl_nextAction.setEnabled(true);
                    btnStartClicked = false;
                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder = null;
                    Toast.makeText(getActivity(),"Recording Audio Saved", Toast.LENGTH_SHORT).show();

                }
                if (btnPalyClicked == true)
                {
                    btnPalyClicked = false;
                    imgbtnPlay.setEnabled(true);
                    imgbtnPause.setEnabled(false);
                    imgbtnStart.setEnabled(true);
                    rl_nextAction.setEnabled(true);

                    mPlayer.release();
                    mPlayer = null;
                    Toast.makeText(getActivity(),"Playing Audio Saved", Toast.LENGTH_SHORT).show();

                }
                break;

            }
            case  R.id.btn_rstart_record:
            {

            /*    btnStartClicked = true;
                imgbtnStop.setEnabled(true);
                imgbtnPause.setEnabled(true);
                rl_nextAction.setEnabled(false);
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile(mFileName);
                try
                {
                    mRecorder.prepare();

                } catch (IOException e)
                {
                    Log.e("AudioCapture", "prepare() failed");
                }*/

               // mRecorder.start();
                //Toast.makeText(getActivity(),"Recording Started", Toast.LENGTH_SHORT).show();

                if(btnStartClicked==false)
                {
                    btnStartClicked = true;
                    imgbtnStop.setEnabled(true);
                    imgbtnPause.setEnabled(true);
                    rl_nextAction.setEnabled(false);
                    mRecorder = new MediaRecorder();

                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile(mFileName);
                    try
                    {
                        mRecorder.prepare();

                    } catch (IOException e)
                    {
                        Log.e("AudioCapture", "prepare() failed");
                    }

                    mRecorder.start();
                    Toast.makeText(getActivity(),"Recording Started", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getActivity(),"Recording Already Started",Toast.LENGTH_LONG).show();
                }

                break;

            }
            case  R.id.btn_rpause_record:
            {

                if (btnPauseClicked == true && btnStartClicked == true)
                {
                    btnPauseClicked = false;
                    mRecorder.resume();
                    Toast.makeText(getActivity(), "Recording Resume", Toast.LENGTH_LONG).show();
                }

                if (btnPauseClicked == true && btnPalyClicked == true)
                {
                    btnPauseClicked = false;
                    mPlayer.seekTo(length);
                    mPlayer.start();
                    Toast.makeText(getActivity(), "Player Resume", Toast.LENGTH_LONG).show();

                }

                if (btnStartClicked == true && btnPauseClicked == false)
                {
                    btnPauseClicked = true;
                    mRecorder.pause();
                    imgbtnStart.setEnabled(false);
                    Toast.makeText(getActivity(), "Recording pause", Toast.LENGTH_LONG).show();

                }
                if (btnPalyClicked == true &&  btnPauseClicked == false)
                {
                    btnPauseClicked = true;
                    mPlayer.pause();
                    length = mPlayer.getCurrentPosition();
                    imgbtnPlay.setEnabled(false);
                    Toast.makeText(getActivity(), "Player pause", Toast.LENGTH_LONG).show();

                }
                break;

            }
            case  R.id.btn_rplay_record:
            {
                //Toast.makeText(getContext(),"Clicked play",Toast.LENGTH_SHORT).show();

                btnPalyClicked = true;
                imgbtnStart.setEnabled(false);
                imgbtnPrevious.setEnabled(false);
                imgbtnPause.setEnabled(true);
                imgbtnStop.setEnabled(true);
                rl_nextAction.setEnabled(false);
/*
                mPlayer = new MediaPlayer();
                playMp3(HomeFragment.byteFile);
                Toast.makeText(getActivity(), "Recording Started Playing", Toast.LENGTH_LONG).show();*/
                System.out.println("=======FILE :::: "+ mFileName);
               try
                {
                    if(!mFileName.isEmpty()) {

                        try {
                            mPlayer.setDataSource(mFileName);
                            mPlayer.prepare();
                            mPlayer.start();
                            Toast.makeText(getActivity(), "Recording Started Playing", Toast.LENGTH_LONG).show();
                        }

                        catch (NullPointerException e)
                        {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e)
                {
                    Log.e("AudioCapture", "prepare() failed");
                }

                break;
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY)
        {
            if (data != null)
            {

                Uri selectedImage = data.getData();

                //   profileimage.setImageURI(selectedImage);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImage);

                    //  encodedImage = saveImage(bitmap);

                    bitmap=getResizedBitmap(bitmap,400);
                    imgAddContentPhoto.setImageBitmap(bitmap);
                   byteImage =  convertBitmapTOByteArray(bitmap);
                    if (hasImage(imgAddContentPhoto))
                    {
                        rl_edit.setVisibility(View.VISIBLE);
                        rl_delete.setVisibility(View.VISIBLE);
                    }

                    //uploadBitmap(bitmap);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }

        }
        else if (requestCode == REQUEST_CAMERA)
        {
            if (data != null) {
                onCaptureImageResult(data);
            }
        }

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void showPictureDialog()
    {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems =
                {
                        "Select photo from gallery",
                        "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    private void onCaptureImageResult(Intent data)
    {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        Log.i("TAG" ,"Camera Image :"+thumbnail);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try
        {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        imgAddContentPhoto.setImageBitmap(thumbnail);
        byteImage = convertBitmapTOByteArray(thumbnail);
        if (hasImage(imgAddContentPhoto))
        {
            rl_edit.setVisibility(View.VISIBLE);
            rl_delete.setVisibility(View.VISIBLE);
        }

    }


    public void choosePhotoFromGallary()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    private void takePhotoFromCamera()
    {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", true);
        startActivityForResult(intent,REQUEST_CAMERA);

    }
    private void  requestMultiplePermissions()
    {

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                )

                .withListener(new MultiplePermissionsListener()
                {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report)
                    {

                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted())
                        {
                            Log.i("TAG","All permissions are granted");
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied())
                        {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token)
                    {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener()
                {
                    @Override
                    public void onError(DexterError error)
                    {
                        Log.i("TAG","Some Error!");
                    }
                })
                .onSameThread()
                .check();

    }

    private boolean hasImage(@NonNull ImageView view)
    {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable))
        {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }

    public byte[] convertBitmapTOByteArray(Bitmap bmp)
    {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

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

                            if (edtTopic.getText().toString().length() == 0) {
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


                            else {
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
                    Logger.putLogInfo("SUBTOPIC_LIST==>", subTopicNameList.toString());

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
