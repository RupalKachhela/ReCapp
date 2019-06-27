package recapp.com.recapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import recapp.com.recapp.CommunicationHandler.CommunicationHandler;
import recapp.com.recapp.R;
import recapp.com.recapp.activity.MainActivity;
import recapp.com.recapp.adapter.CustomAdapter;
import recapp.com.recapp.adapter.HomePagerAdpater;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.database.DBHelper;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.helper.MyNodeViewFactory;
import recapp.com.recapp.model.BatchDataModel;
import recapp.com.recapp.model.BatchListDataModel;
import recapp.com.recapp.model.ContentDataList;
import recapp.com.recapp.model.ReviseListData;

public class SchoolFragment extends android.support.v4.app.Fragment implements View.OnClickListener,CommunicationHandler.CommunicationHandlerCallBack
{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    RelativeLayout rl_SelectSubject , rl_container , rl_cardContent , rl_pink;
    CardView cardSubject , cardDownload;
    public static  TreeNode treeNode1;
    RelativeLayout rl_downloadContainer;

    ImageButton btnAdd;
    EditText edtSubject;
    public static List<String> subjectList = new ArrayList<>();
    public ViewGroup viewGroup;
    public TreeNode root;
    public static TreeView treeView;

    public static List<ContentDataList> rootList = new ArrayList<>();
    List<ContentDataList> rootChildList = new ArrayList<>();
    List<ContentDataList> rootSubChildList = new ArrayList<>();
    List<ContentDataList> mostChildList = new ArrayList<>();

    List<String> grandParentlist = new ArrayList<>();
    List<String> parentlist = new ArrayList<>();
    List<String> childlist = new ArrayList<>();
    List<String> datalist = new ArrayList<>();
    List<BatchDataModel> subjectlist = new ArrayList<>();
    ArrayList<String>  contentList = new ArrayList<>();
    ArrayList<byte[]>  imagelist = new ArrayList<>();
    ArrayList<byte[]>  audioList = new ArrayList<>();
    ArrayList<String>  audioFileNameList = new ArrayList<>();
    List<ReviseListData>  reviseDataList = new ArrayList<>();
    ArrayList<String>  subjectnameList = new ArrayList<>();
    ArrayList<String>  audioTypeList = new ArrayList<>();
    String newFileName;
    ReviseListData reviseDatamodel ;
    String subject,audioType,topicName,subTopicName;
    ArrayList<String>  topicNameList = new ArrayList<>();
    ArrayList<String>  subtopicNameList = new ArrayList<>();
    int m;
    int id,newId,newChildId;
    ArrayList<HashMap<String, String>> userList;
    ArrayList<HashMap<String, byte[]>> blobList;
    String selectedSubId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragmnet_school,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

      // AddContentFragment.isDownloaded = true;

        //preparedata();

        edtSubject = view.findViewById(R.id.edt_select_subject);
        btnAdd = view.findViewById(R.id.img_add);
        rl_SelectSubject = view.findViewById(R.id.rl_selectSubject_school);
        rl_container = view.findViewById(R.id.container);
        cardSubject = view.findViewById(R.id.card_addSubject);
        cardDownload = view.findViewById(R.id.card_downloadContent);
        rl_cardContent = view.findViewById(R.id.rl_addContent);
        viewPager = getActivity().findViewById(R.id.viewpager);
        rl_downloadContainer = getActivity().findViewById(R.id.rl_downloadcontainer);
        rl_pink = getActivity().findViewById(R.id.rl_pink);

        cardSubject.setOnClickListener(this);
        cardDownload.setOnClickListener(this);
        viewGroup = view.findViewById(R.id.container);

        setLightStatusBar(viewGroup);

       if (RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT) != null)
        {
            edtSubject.setText(RecappApplication.getInstance().getSPValueByKey(Const.SELECTED_SUBJECT));
        }

        System.out.println("===value :"+RecappApplication.getInstance().isMessageChecked(Const.IS_DOWNlODED));

        if(RecappApplication.getInstance().isMessageChecked(Const.IS_DOWNlODED))
        {
            rl_container.setVisibility(View.VISIBLE);
            rl_SelectSubject.setVisibility(View.VISIBLE);
            rl_cardContent.setVisibility(View.GONE);

        }

        callForSubjectList();

        //preparedata();

        prepareOwnDatabaseListdata();

        root = TreeNode.root();
        buildTree();
        treeView = new TreeView(root, getContext(), new MyNodeViewFactory());
        View view1 = treeView.getView();
        view1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(view1);


        edtSubject.setInputType(InputType.TYPE_NULL);
        System.out.println("===list ==="+subjectList);

        final ArrayAdapter<String> spinner_breed = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, subjectList);
        {
            System.out.println("====inside array adpater click ==="+subjectList);

            edtSubject.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    if(!subjectList.isEmpty() && subjectList != null) {
                        System.out.println("====subject list ===" + subjectList);
                        new AlertDialog.Builder(getActivity())
                                .setAdapter(spinner_breed, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        edtSubject.setText(subjectList.get(which));
                                        selectedSubId = subjectlist.get(which).getSubjectId();

                                        System.out.println("===subject : " + edtSubject.getText().toString());
                                        RecappApplication.getInstance().setSPValueByKey(Const.SELECTED_SUBJECT, edtSubject.getText().toString());
                                        RecappApplication.getInstance().setSPValueByKey(Const.SELECTED_SUBJECT_ID, selectedSubId);

                                        String node = getSelectedNodes();
                                        System.out.println("=== node selected : " + node);
                                        prepareOwnDatabaseListdata();
                                        setUserVisibleHint(true);
                                        dialog.dismiss();
                                    }

                                }).create().show();
                    }

                    else{
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

    }

    public void callForSubjectList()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id",RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID));
        params.put("subcategory_id",RecappApplication.getInstance().getSPValueByKey(Const.SUB_CATEGORY_ID));
        CommunicationHandler.getInstance(this).callForSubjectList(getContext(),params);
        System.out.println("======PARAM : " + params);

    }

    public  static String getSelectedNodes()
    {
        StringBuilder stringBuilder = new StringBuilder("You have selected: ");

        List<TreeNode> selectedNodes = treeView.getSelectedNodes();

        for (int i = 0; i < selectedNodes.size(); i++)
        {
            /*for (int j = 0; j < rootList.size(); j++)
            {
               String value =  rootList.get(j).getParentName();
               if (selectedNodes.get(i).getValue().toString().equals(value))
               {
                    treeView.deselectAll();
               }
            }*/

            if (i < 5)
            {
                stringBuilder.append(selectedNodes.get(i).getValue() + ",");
                String value = (String) selectedNodes.get(i).getParent().getValue();
                System.out.println("===value :"+value);
            } else
            {
                stringBuilder.append("...and " + (selectedNodes.size() - 5) + " more.");
                break;
            }

        }

        return stringBuilder.toString();
    }

    private void buildTree()
    {
        System.out.println("=========MAIN LIST: " + rootList);
        for (int i = 0; i < rootList.size(); i++)
        {
            TreeNode treeNode = new TreeNode(rootList.get(i).getParentName());
            treeNode.setLevel(0);

            for (int j = 0 ; j < rootChildList.size(); j++)
            {
                if (rootList.get(i).getParentId() == rootChildList.get(j).getParentId())
                {
                     treeNode1 = new TreeNode(rootChildList.get(j).getParentName());
                     treeNode1.setLevel(1);

                        for (int k = 0; k < rootSubChildList.size(); k++)
                        {
                            if (rootChildList.get(j).getChildId() == rootSubChildList.get(k).getParentId())
                            {
                                TreeNode treeNode2 = new TreeNode(rootSubChildList.get(k).getParentName());
                                treeNode2.setLevel(2);

                                for (int m = 0; m < mostChildList.size(); m++)
                                {

                                    if (rootSubChildList.get(k).getChildId() == mostChildList.get(m).getParentId())
                                    {
                                        TreeNode treeNode3 = new TreeNode(datalist.get(m));
                                        treeNode3.setLevel(3);
                                        treeNode2.addChild(treeNode3);
                                    }
                                }
                                treeNode1.addChild(treeNode2);
                            }
                         }
                    treeNode.addChild(treeNode1);
                }
            }

            root.addChild(treeNode);
        }

        //-------------------------------------

  /*      for (int i = 0; i < grandParentlist.size(); i++)
        {
            TreeNode treeNode = new TreeNode(grandParentlist.get(i));
            treeNode.setLevel(0);

            for (int j = 0; j < parentlist.size() ; j++)
            {

                TreeNode treeNode1 = new TreeNode(parentlist.get(j));
                treeNode1.setLevel(1);

                for (int k = 0; k < childlist.size(); k++) {
                    TreeNode treeNode2 = new TreeNode(childlist.get(k));
                    treeNode2.setLevel(2);

                    for (int m = 0; m < datalist.size(); m++) {

                        TreeNode treeNode3 = new TreeNode(datalist.get(m));
                        treeNode3.setLevel(3);
                        treeNode2.addChild(treeNode3);

                    }
                    treeNode1.addChild(treeNode2);


                }
                treeNode.addChild(treeNode1);

            }
            root.addChild(treeNode);
        }*/

     //---------------------------------------

    }

    private void setLightStatusBar(@NonNull View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int flags = view.getSystemUiVisibility();
            getActivity().getWindow().setStatusBarColor(Color.WHITE);
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }

    }

    public String prepareOwnDatabaseListdata()
    {
        rootList.clear();
        mostChildList.clear();
        rootSubChildList.clear();
        rootChildList.clear();
        datalist.clear();

        /*ReviseListData dataModel = new ReviseListData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRH_kaGZnbIYXKQC4drhCzDMQG5QlRdRot-tECnhxel2Xu4SB7d","Xyz");
        reviseDataList.add(dataModel);
        dataModel = new ReviseListData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStlR_TtB4kjP10FUc8VgNbM7mcm41-G6rIHSb_7kStGb8mNA3x","Xyz");
        reviseDataList.add(dataModel);
        dataModel = new ReviseListData("https://media.istockphoto.com/vectors/types-of-poliovirus-the-brunnhild-virus-virus-lansing-the-virus-leon-vector-id834276932","Xyz");
        reviseDataList.add(dataModel);*/

        try {
            audioTypeList.clear();
            audioList.clear();
            imagelist.clear();
            topicNameList.clear();
            contentList.clear();
            audioFileNameList.clear();
            userList.clear();
            blobList.clear();
            subjectnameList.clear();
            subtopicNameList.clear();


        }catch (NullPointerException e)
        {

            e.getMessage();
        }

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
              //  userList = db.GetRecordList();
               blobList = db.retreiveAudioFromDBByCategory(subjectName, categoryName);

            }
            if (userList != null && !userList.isEmpty() && userList.size() > 0)
            {
                System.out.println("===Database user list : " + userList);
                System.out.println("===Database user lis size: " + userList.size());

                for (int i = 0; i < userList.size(); i++)
                {
                    subject = userList.get(i).get("subject_name");
                    audioType = userList.get(i).get("audio_type");
                    topicName = userList.get(i).get("topic_name");
                    subTopicName = userList.get(i).get("sub_topic_name");
                    String file = userList.get(i).get("file_name");
                    contentList.add(file);
                    topicNameList.add(topicName);
                    subtopicNameList.add(subTopicName);
                    subjectnameList.add(subject);
                    audioTypeList.add(audioType);

                    System.out.println("=====TOPIC NAME "+ topicName);

               /*     byte[] byteFile = blobList.get(i).get("audio_file");
                    byte[] byteImage = blobList.get(i).get("image_file");
                    audioList.add(byteFile);
                    imagelist.add(byteImage);

                    reviseDatamodel = new ReviseListData(file,byteImage,byteFile);
                    reviseDataList.add(reviseDatamodel);*/
                }
            }

            System.out.println("=====Subject name list : " + subjectnameList);
            System.out.println("=====Audio Type list : " + audioTypeList);
            System.out.println("===File name list: " + contentList);
            System.out.println("===Audio name list: " + audioList);
            System.out.println("===Image name list: " + imagelist);
            System.out.println("===Topic name list: " + topicNameList);
            System.out.println("===Sub Topic name list: " + subtopicNameList);

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

            prepareownlistdata();
            //System.out.println("====FILE NAME LIST : " +audioFileNameList);
        }
        return null;
    }

    private void prepareownlistdata() {

      /*  subjectList = new ArrayList<>();
        subjectList.add("Biology");
        subjectList.add("Maths");
        subjectList.add("Physics");
        subjectList.add("English");*/

        datalist = audioFileNameList;

        System.out.println("=========Inside topicname list and size: "+ topicNameList.size());

        //topic name list
        for(int i = 0; i<topicNameList.size();i++)
        {
            String topicname = topicNameList.get(i);
            id =i+1;

            Logger.putLogError("Root Parent -->" , "topic ID :-  "+id);
            Logger.putLogError("Root Parent -->" , "topic Name :-  "+topicname);

            System.out.println("====NAME AND ID : " + topicname +id);
            ContentDataList dataList = new ContentDataList(topicname,id);
            rootList.add(dataList);
            System.out.println("====INSIDE ROOT LIST===" +rootList + rootList.size());

          /*  if(topicName.equals(topicNameList.get(i)))
            {
                rootList.remove(dataList);
            }*/
        }

        System.out.println("====ROOT LIST===" +rootList);

        id = 1;
        //sub topic name list
        for(int i = 0; i<subtopicNameList.size();i++)
        {
            String subtopicname = subtopicNameList.get(i);
            newId = i + 20;
            newChildId = 100;

            Logger.putLogError("Root Child Parent -->" , "sub topic ID :-  "+id);
            Logger.putLogError("Root Child Parent -->" , "sub topic Name :-  "+subtopicname);
            System.out.println("====SUB TOPIC NAME AND ID : " + subtopicname +id);
            ContentDataList childlist = new ContentDataList(subtopicname ,id,newId);
            rootChildList.add(childlist);

            id = id + 1;

            System.out.println("====INSIDE ROOT LIST===" +rootList + rootList.size());
        }

        newId = 20;
        for(int k =0;k<subtopicNameList.size();k++)
        {
            //audio type list
            ContentDataList subchildlist = new ContentDataList( "Definition" , newId,newChildId);
            rootSubChildList.add(subchildlist);
            subchildlist = new ContentDataList( "Explanations",newId ,newChildId + 1);
            rootSubChildList.add(subchildlist);
            subchildlist = new ContentDataList( "Summary",newId,newChildId + 2);
            rootSubChildList.add(subchildlist);
            subchildlist = new ContentDataList("Q & A",newId ,newChildId + 3);
            rootSubChildList.add(subchildlist);

            newId = k + 1 + 20;
        }
//        //audio type list
//        ContentDataList subchildlist = new ContentDataList( "Definition" , newId,newChildId);
//        rootSubChildList.add(subchildlist);
//        subchildlist = new ContentDataList( "Explanations",newId ,newChildId + 1);
//        rootSubChildList.add(subchildlist);
//        subchildlist = new ContentDataList( "Summary",newId,newChildId + 2);
//        rootSubChildList.add(subchildlist);
//        subchildlist = new ContentDataList("Q & A",newId ,newChildId + 3);
//        rootSubChildList.add(subchildlist);

        try {
            // audio name list
            for (int k = 0; k < audioFileNameList.size(); k++) {
                String audioname = audioFileNameList.get(k);
                if (!audioTypeList.isEmpty() && audioTypeList != null) {
                    String audioType = audioTypeList.get(k);

                    System.out.println("====Audio type== " + audioType);

                    if (audioType.equals("Definitions")) {
                        System.out.println("===Audio File name : " + audioname);
                        ContentDataList submchildlist = new ContentDataList(audioname, newChildId);
                        mostChildList.add(submchildlist);
                    } else if (audioType.equals("Explanation")) {
                        System.out.println("===Audio File name : " + audioname);
                        ContentDataList submchildlist = new ContentDataList(audioname, newChildId + 1);
                        mostChildList.add(submchildlist);
                    } else if (audioType.equals("Summary")) {
                        System.out.println("===Audio File name : " + audioname);
                        ContentDataList submchildlist = new ContentDataList(audioname, newChildId + 2);
                        mostChildList.add(submchildlist);
                    } else {
                        System.out.println("===Audio File name : " + audioname);
                        ContentDataList submchildlist = new ContentDataList(audioname, newChildId + 3);
                        mostChildList.add(submchildlist);
                    }

                }

            }
        }catch (NullPointerException e)
        {
            e.getMessage();
        }
    }

    public void preparedata()
    {
        subjectList = new ArrayList<>();
        subjectList.add("Biology");
        subjectList.add("Maths");
        subjectList.add("Physics");
        subjectList.add("English");

        grandParentlist.add("The Body");
        grandParentlist.add("Cells");
        grandParentlist.add("Where We Come From");
       // grandParentlist.add("Own");

        parentlist.add("Evolution");
        parentlist.add("Heredity");


        childlist.add("Definition");
        childlist.add("Explanations");
        childlist.add("Summary");
        childlist.add("Q & A");

        datalist.add("Structure of DNA");
        datalist.add("DNA Code");
        datalist.add("DNA explanation");

        rootList = new ArrayList<>();
        ContentDataList dataList = new ContentDataList("The Body" , 1);
        rootList .add(dataList);
        dataList = new ContentDataList("Cells", 2);
        rootList .add(dataList);
        dataList = new ContentDataList("Where We Come From", 3);
        rootList .add(dataList);
        dataList = new ContentDataList("Own", 5);
        rootList .add(dataList);
        //rootChildList = new ArrayList<>();

        ContentDataList childlist = new ContentDataList( "Evolution" ,3,31);
        rootChildList.add(childlist);
        childlist = new ContentDataList( "Heredity",3,32);
        rootChildList.add(childlist);
        childlist = new ContentDataList( "Pizza",4,21);
        rootChildList.add(childlist);
        childlist = new ContentDataList("Sendwitch",4,22);
        rootChildList.add(childlist);
        childlist = new ContentDataList( "Watermelon",4,13);
        rootChildList.add(childlist);
        childlist = new ContentDataList( "Grapes" ,4,124);
        rootChildList.add(childlist);

        rootSubChildList = new ArrayList<>();
        ContentDataList subchildlist = new ContentDataList( "Definition" , 31,331);
        rootSubChildList.add(subchildlist);
        subchildlist = new ContentDataList( "Explanations",31 ,332);
        rootSubChildList.add(subchildlist);
        subchildlist = new ContentDataList( "Summary",31 ,333);
        rootSubChildList.add(subchildlist);
        subchildlist = new ContentDataList("Q & A",31 ,334);
        rootSubChildList.add(subchildlist);
        subchildlist = new ContentDataList( "sub Watermelon",23);
        rootSubChildList.add(subchildlist);
        subchildlist = new ContentDataList( "sub Grapes" ,14);
        rootSubChildList.add(subchildlist);

        mostChildList = new ArrayList<>();
        ContentDataList mchildlist = new ContentDataList( "Structure of DNA" , 332);
        mostChildList.add(mchildlist);
        mchildlist = new ContentDataList( "DNA Code",332);
        mostChildList.add(mchildlist);
        mchildlist = new ContentDataList( "DNA Explanation",332);
        mostChildList.add(mchildlist);
        mchildlist = new ContentDataList("Q & A",234);
        mostChildList.add(mchildlist);
        mchildlist = new ContentDataList( "sub Watermelon",23);
        mostChildList.add(mchildlist);
        mchildlist = new ContentDataList( "sub Grapes" ,14);
        mostChildList.add(mchildlist);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.card_addSubject:
            {

                viewPager.setCurrentItem(1);
               /* rl_cardContent.setVisibility(View.GONE);
                rl_SelectSubject.setVisibility(View.VISIBLE);
                rl_container.setVisibility(View.VISIBLE);*/

                break;
            }
            case R.id.card_downloadContent :
            {

//                 rl_cardContent.setVisibility(View.GONE);
//                rl_SelectSubject.setVisibility(View.VISIBLE);
//                rl_container.setVisibility(View.VISIBLE);

                rl_cardContent.setVisibility(View.GONE);
                rl_container.setVisibility(View.GONE);
                viewGroup.setVisibility(View.GONE);
              // rl_downloadContainer.setVisibility(View.VISIBLE);

                AddContentFragment fragment = new AddContentFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_school, fragment);
                fragmentTransaction.commit();

                break;
            }
        }
    }

    @Override
    public void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess)
    {
        if (mMethod.equals(Const.SUBJECT_LIST)) {
            Logger.putLogInfo("SUBJECT_LIST_Response ==>", jsonObject.toString());

            try {
                BatchListDataModel dataList = new BatchListDataModel(jsonObject);
                subjectlist.clear();
                subjectnameList.clear();
                subjectlist.addAll(dataList.getSubjectList());
                subjectList.clear();

                if (subjectlist != null && !subjectlist.isEmpty())
                {
                    for (int i =0 ; i < subjectlist.size(); i++)
                    {
                        subjectList.add(subjectlist.get(i).getSubjectName());
                    }

                    Logger.putLogInfo("subject_List==>", subjectList.toString());
                }

                /*else{

                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getResources().getString(R.string.app_name));
                    alertDialog.setMessage("No Subject found");
                    alertDialog.setIcon(R.drawable.rc_logo);
                    alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface aDialogInterface, int aI) {
                            aDialogInterface.dismiss();
                        }

                    });
                    alertDialog.show();
                }*/

            } catch (JSONException aE)
            {
                aE.printStackTrace();
            }
        }


    }

    @Override
    public void failureCBWithMethod(String mMethod, String mError) {
        Logger.putLogError("SUBJECT_LIST_Response ==>", mError);

    }

    @Override
    public void cacheCBWithMethod(String mMethod, String mResponse) {
        Logger.putLogError("SUBJECT_LIST_Response ==>", mResponse);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        // Refresh tab data:

        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}
