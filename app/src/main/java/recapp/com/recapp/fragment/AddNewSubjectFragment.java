package recapp.com.recapp.fragment;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recapp.com.recapp.R;
import recapp.com.recapp.adapter.SubjectRecyclerAdapter;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.database.DBHelper;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.interfaces.ItemClickListener;
import recapp.com.recapp.model.ReviseListData;
import recapp.com.recapp.model.SubjectListData;

public class AddNewSubjectFragment extends android.support.v4.app.Fragment implements ItemClickListener
{

    CardView cardSubject;
    RelativeLayout rl_subject;
    RelativeLayout rl_subjectDetail;
    TextView title;
    RelativeLayout relative;
    Button backBtn,btnHelp;
    EditText edtSelectSubject , edtSelectTopic ,edtSelectTopicName ,edtSelectAudioType;
    RecyclerView recyclerview_subject;
    LinearLayout lladdsub;
    public static List<SubjectListData>  subjectNameLlist;
    SubjectRecyclerAdapter recyclerviewAdapter;

    ArrayList<String>  contentList = new ArrayList<>();
    ArrayList<byte[]>  imagelist = new ArrayList<>();
    ArrayList<byte[]>  audioList = new ArrayList<>();
    ReviseListData reviseDatamodel ;
    List<ReviseListData>  reviseDataList = new ArrayList<>();
    ArrayList<HashMap<String, String>> userList;
    String subject,subShort;
    SubjectListData datamodel;
    ArrayList<HashMap<String, byte[]>> blobList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragmnet_addnew_subject,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

        subjectNameLlist = new ArrayList<>();
        title = view.findViewById(R.id.txt_actionbar_title);
        relative = view.findViewById(R.id.rl_action_bar);
        backBtn = view.findViewById(R.id.btn_back);
        btnHelp = view.findViewById(R.id.btn_help);
        cardSubject = view.findViewById(R.id.card_addSubject);
        rl_subject = view.findViewById(R.id.rl_add_newSubject);
        rl_subjectDetail = view.findViewById(R.id.rl_subjectList);
        recyclerview_subject = view.findViewById(R.id.recyclerview_subject);
        lladdsub = view.findViewById(R.id.ll_add_sub);


        DBHelper db = new DBHelper(getActivity());
        if (db != null)
        {
            userList = db.GetRecordList();
            ArrayList<HashMap<String, byte[]>> blobList = db.retreiveAudioFromDB();

            if (userList != null && !userList.isEmpty() && userList.size() > 0)
            {
                System.out.println("===user list : " + userList);
                System.out.println("===user lis size: " + userList.size());

               /* for (int i = 0; i < userList.size(); i++) {
                    subject = userList.get(i).get("subject_name");
                    String audioType = userList.get(i).get("audio_type");
                    String file = userList.get(i).get("file_name");
                    contentList.add(file);

                    byte[] byteFile = blobList.get(i).get("audio_file");
                    byte[] byteImage = blobList.get(i).get("image_file");
                    audioList.add(byteFile);
                    imagelist.add(byteImage);

                    reviseDatamodel = new ReviseListData(file, byteImage, byteFile);
                    reviseDataList.add(reviseDatamodel);

                    assert subject != null;
                    subShort = subject.substring(0, 2);

                    datamodel = new SubjectListData(subShort, subject);
                    subjectNameLlist.add(datamodel);
                }*/

                   //for remove duplicate value multiple times start
                    for(int k=0;k<userList.size();k++) {
                        for (int j = k + 1; j < userList.size(); j++) {
                            if (userList.get(k).get("subject_name").equals(userList.get(j).get("subject_name"))) {

                                userList.remove(j).get("subject_name");
                                j--;

                            }
                        }
                    }

                for(int m= 0;m<userList.size();m++)
                {
                    subject = userList.get(m).get("subject_name");
                    assert subject != null;
                    subShort = subject.substring(0,2);
                    datamodel = new SubjectListData(subShort,subject);
                    subjectNameLlist.add(datamodel);
                    System.out.println("===========NEW LIST ====" + userList);
                } //end

            }

            System.out.println("===File name list: " + contentList);
            System.out.println("===Audio name list: " + audioList);
            System.out.println("===Image name list: " + imagelist);
            System.out.println("===Subjecshort name : " + subShort);

        }

        if(!userList.isEmpty())
        {
            rl_subjectDetail.setVisibility(View.VISIBLE);
            rl_subject.setVisibility(View.GONE);

            bindRecyclerview();

        }

        else {

            rl_subjectDetail.setVisibility(View.GONE);
            rl_subject.setVisibility(View.VISIBLE);

            cardSubject.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    rl_subjectDetail.setVisibility(View.GONE);
                    rl_subject.setVisibility(View.GONE);

                 /*   RecordFragment fragment = new RecordFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main_container, fragment);
                    fragmentTransaction.addToBackStack(HomeFragment.TAG);
                    fragmentTransaction.commit();
*/
                    AddSubjectDetailHomeFragment fragment = new AddSubjectDetailHomeFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.ll_add_sub, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
        }

    }

    public void bindRecyclerview()
    {
        int numberOfColumns = 3;

            recyclerviewAdapter = new SubjectRecyclerAdapter(getActivity(), subjectNameLlist);
            recyclerview_subject.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
            recyclerview_subject.setItemAnimator(new DefaultItemAnimator());
            recyclerviewAdapter.setOnListener(this);
            recyclerview_subject.setAdapter(recyclerviewAdapter);

           // prepareData();
            recyclerviewAdapter.notifyDataSetChanged();
    }

   /* public void bindRecyclerview()
    {
        int numberOfColumns = 3;

        if(HomeFragment.audioList != null) {

            recyclerviewAdapter = new SubjectRecyclerAdapter(getActivity(), HomeFragment.audioList);
            recyclerview_subject.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
            recyclerview_subject.setItemAnimator(new DefaultItemAnimator());
            recyclerviewAdapter.setOnListener(this);
            recyclerview_subject.setAdapter(recyclerviewAdapter);

            //prepareData();
            recyclerviewAdapter.notifyDataSetChanged();
        }

        else {

                recyclerviewAdapter = new SubjectRecyclerAdapter(getActivity(), subjectNameLlist);
                recyclerview_subject.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
                recyclerview_subject.setItemAnimator(new DefaultItemAnimator());
                recyclerviewAdapter.setOnListener(this);
                recyclerview_subject.setAdapter(recyclerviewAdapter);

                prepareData();
                recyclerviewAdapter.notifyDataSetChanged();
            }
    }*/

    public void prepareData()
    {

       /* SubjectListData data = new SubjectListData("CH","Chemistry");
        subjectNameLlist.add(data);*/

        datamodel = new SubjectListData(subShort,subject);
        subjectNameLlist.add(datamodel);

      /*  data = new SubjectListData("PH","Physics");
        subjectNameLlist.add(data);

        data = new SubjectListData("CH","Chemistry");
        subjectNameLlist.add(data);

        data = new SubjectListData("MH","Maths");
        subjectNameLlist.add(data);
*/
    }

    @Override
    public void listItemClick(int position)
    {

    }

    @Override
    public void listItemClick(int position, String eventType)
    {
        if (eventType.equals("AddSubject"))
        {

            AddSubjectDetailHomeFragment fragment = new AddSubjectDetailHomeFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rl_subjectList, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }else
        {
           String name =  subjectNameLlist.get(position).getShortName();
           String subName = subjectNameLlist.get(position).getSubjectName();

          //  Toast.makeText(getContext(), "Subject : "+ name, Toast.LENGTH_SHORT).show();
            String categoryName = RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID);

            DBHelper db = new DBHelper(getActivity());

            if (db != null) {
                blobList = db.retreiveAudioFromDBByCategory(subName, categoryName);

                for(int i = 0;i<blobList.size();i++) {
                    byte[] byteFile = blobList.get(i).get("audio_file");
                    byte[] byteImage = blobList.get(i).get("image_file");
                    audioList.add(byteFile);
                    imagelist.add(byteImage);
                }

                System.out.println("===========AUDIO LIST : " + audioList  + "Image List : " + imagelist);
            }

            if(!audioList.isEmpty() && !imagelist.isEmpty())
            {
                PlayOwnAudioFragment fragment = new PlayOwnAudioFragment();
                Bundle bundle = new Bundle();
                bundle.putString("subject", subName);

                System.out.println("====bundle value : " + bundle);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rl_subjectList, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }
}
