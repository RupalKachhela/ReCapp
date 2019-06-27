package recapp.com.recapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import recapp.com.recapp.R;
import recapp.com.recapp.activity.MainActivity;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.model.ReviseListData;

public class RecapReturnAnsFragment extends android.support.v4.app.Fragment implements View.OnClickListener
{

    TextView title;
    RelativeLayout relative;
    Button backBtn,btnHelp ,btnProceed;
    CheckBox cbMsg;
    LinearLayout ll_msg,ll_content;
    EditText edtSubject;
    RelativeLayout rl_next , rl_previoud;
    List<ReviseListData>  recapList;
    ImageView imgTopic;

    int count =0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragmnet_recap_returnans,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
         title = view.findViewById(R.id.txt_actionbar_title);
         relative = view.findViewById(R.id.rl_action_bar);
         backBtn = view.findViewById(R.id.btn_back);
         btnHelp = view.findViewById(R.id.btn_help);
         edtSubject = view.findViewById(R.id.edt_selcet_subject_recap);
         rl_next = view.findViewById(R.id.rl_next_recap);
         rl_previoud = view.findViewById(R.id.rl_prevoius_recap);
         imgTopic = view.findViewById(R.id.img_recaplist);


        backBtn.setVisibility(View.VISIBLE);
        relative.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        title.setText("Recap");

        recapList = new ArrayList<>();

       // preparedata();
        Glide.with(getActivity())
                .load(recapList.get(0).getUrl())
                .fitCenter()
                .into(imgTopic);


        rl_previoud.setOnClickListener(this);
        rl_next.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        edtSubject.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

        System.out.println("===list ==="+HomeFragment.subjectList);

        final ArrayAdapter<String> spinner_breed = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, HomeFragment.subjectList);
        {
            System.out.println("====inside array adpater click ==="+HomeFragment.subjectList);

            edtSubject.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    System.out.println("====click subject ===");
                    new AlertDialog.Builder(getActivity())
                            .setAdapter(spinner_breed, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    edtSubject.setText(HomeFragment.subjectList.get(which));
                                    dialog.dismiss();
                                }
                    }).create().show();
                }

            });
        }

    }

   /* public void preparedata()
    {
        ReviseListData dataModel = new ReviseListData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRH_kaGZnbIYXKQC4drhCzDMQG5QlRdRot-tECnhxel2Xu4SB7d","Xyz");
        recapList.add(dataModel);
        dataModel = new ReviseListData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStlR_TtB4kjP10FUc8VgNbM7mcm41-G6rIHSb_7kStGb8mNA3x","Xyz");
        recapList.add(dataModel);
        dataModel = new ReviseListData("https://media.istockphoto.com/vectors/types-of-poliovirus-the-brunnhild-virus-virus-lansing-the-virus-leon-vector-id834276932","Xyz");
        recapList.add(dataModel);
    }*/

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.rl_prevoius_recap:
            {

                System.out.println("==prev =list size : "+recapList.size());
                System.out.println("==prev =list pos : "+recapList.get(count));

                System.out.println("==prev count : "+count+ "  data  : "+recapList.get(count).getUrl());
                if (count > 0){
                    count--;

                    Glide.with(getActivity())
                            .load(recapList.get(count).getUrl())
                            .fitCenter()
                            .into(imgTopic);
                }
                else {

                }


                break;
            }
            case R.id.rl_next_recap:
            {

                System.out.println("===list size : "+recapList.size());
                System.out.println("===list pos : "+recapList.get(count));

                System.out.println("==count : "+count+ "  data  : "+recapList.get(count).getUrl());
                if (count <  recapList.size()-1){
                    count++;
                    Glide.with(getActivity())
                            .load(recapList.get(count).getUrl())
                            .fitCenter()
                            .into(imgTopic);
                }
                else {
                }

                break;
            }

            case R.id.btn_back:
            {

                if (getActivity() != null && !getActivity().isFinishing())
                {

                    getActivity().onBackPressed();
                }

                break;
            }
        }
    }
}
