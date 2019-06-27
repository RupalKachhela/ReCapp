package recapp.com.recapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
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
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;

public class RecapFragment extends android.support.v4.app.Fragment implements CommunicationHandler.CommunicationHandlerCallBack
{

    public static String TAG = "RecapFragment";
    TextView title;
    RelativeLayout relative,rl_topic;
    Button backBtn,btnHelp ,btnProceed;
    CheckBox cbMsg;
    LinearLayout ll_msg,ll_content;
    EditText edtSubject , edtCategory , edtTopic;
    Button btnPlayAnswer;
    ImageButton btnFilter;
    boolean checked = false;
    ImageView imgRecapTopic;
    ImageButton btnPrevious, btnPause;
    Button btnstart;
    MediaPlayer mPlayer;
    public static List<String> topicList;
    RelativeLayout rlprevious,rlnext;

    List<String> topicNameList= new ArrayList<>();
    List<BatchDataModel> topiclist = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragmnet_recap,container,false);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {


        super.onViewCreated(view, savedInstanceState);

      //  getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));

        title = getActivity().findViewById(R.id.txt_actionbar_title);
         relative = getActivity().findViewById(R.id.rl_action_bar);
         backBtn = getActivity().findViewById(R.id.btn_back);
         btnHelp = getActivity().findViewById(R.id.btn_help);
        cbMsg = view.findViewById(R.id.cb_showMsgAgain_recap);
        btnProceed = view.findViewById(R.id.btn_proceed_recap);
        ll_msg = view.findViewById(R.id.ll_recap_detail_msg);
        ll_content = view.findViewById(R.id.ll_recap_detail_audioplay);
        btnPlayAnswer = view.findViewById(R.id.btn_playAns_recap);
        btnFilter = view.findViewById(R.id.imgbtn_filter);
        edtCategory = view.findViewById(R.id.edt_selcet_category_recapFragment);
        edtSubject = view.findViewById(R.id.edt_selcet_subject_recapFragment);
        edtTopic = view.findViewById(R.id.edt_selcet_topic_recappFragment);
        imgRecapTopic = view.findViewById(R.id.img_recapFragment);
        btnPrevious = view.findViewById(R.id.btn_rprevious_recap);
        btnstart = view.findViewById(R.id.btn_rstart_recap);
        btnPause = view.findViewById(R.id.btn_rpause_recap);
        rl_topic = view.findViewById(R.id.rl_select_topic_record);
        rlprevious = view.findViewById(R.id.rl_prevoius);
        rlnext = view.findViewById(R.id.rl_next);

        edtCategory.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_CATEGORY));
        edtSubject.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT));
        backBtn.setVisibility(View.VISIBLE);
        btnHelp.setVisibility(View.GONE);
        relative.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        title.setText("Recap");

        topicList = new ArrayList<>();
//        topicList.add("The Body");
//        topicList.add("Cells");
//        topicList.add("DNA");

        if(!edtSubject.getText().toString().isEmpty()) {
            callForTopicList();
        }

      /*  if (HomeFragment.byteImage != null)
        {
            Bitmap bmp = BitmapFactory.decodeByteArray(HomeFragment.byteImage, 0, HomeFragment.byteImage.length);

            imgRecapTopic.setImageBitmap(bmp);
        }*/

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
//        }

        if (RecappApplication.getInstance().isMessageChecked(Const.RECAP_CHECKED))
        {
            ll_content.setVisibility(View.VISIBLE);
            ll_msg.setVisibility(View.GONE);
            btnHelp.setVisibility(View.VISIBLE);
        }

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

        btnProceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("=====click : "+checked);

                RecappApplication.getInstance().setSPValueByKey(Const.RECAP_CHECKED, checked);
                ll_msg.setVisibility(View.GONE);
                ll_content.setVisibility(View.VISIBLE);
                btnHelp.setVisibility(View.VISIBLE);

            }
        });

        btnPlayAnswer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(getActivity(), "Not implemented yet", Toast.LENGTH_SHORT).show();
               /* RecapReturnAnsFragment fragment = new RecapReturnAnsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_main_container, fragment);
                fragmentTransaction.addToBackStack(RecapFragment.TAG);
                fragmentTransaction.commit();*/

            }
        });

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

        btnFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HomeFragment.showPopup(v , getActivity());
            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPlayer = new MediaPlayer();
               // playMp3(HomeFragment.byteFile);
                Toast.makeText(getActivity(), "Recording Started Playing", Toast.LENGTH_SHORT).show();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(mPlayer == null)
                {
                    Toast.makeText(getActivity(),"First do Start Play Recording", Toast.LENGTH_SHORT).show();
                }

                else {

                    mPlayer.release();
                    mPlayer = null;
                    Toast.makeText(getActivity(), "Recording Stop", Toast.LENGTH_SHORT).show();
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
            }
        });


        rlprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Clicked Previous",Toast.LENGTH_SHORT).show();
            }
        });

        rlnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Clicked Next",Toast.LENGTH_SHORT).show();
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
}
