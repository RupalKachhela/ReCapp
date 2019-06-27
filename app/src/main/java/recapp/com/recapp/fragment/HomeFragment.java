package recapp.com.recapp.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import recapp.com.recapp.R;
import recapp.com.recapp.activity.MainActivity;
import recapp.com.recapp.activity.MakePaymentDetailActivity;
import recapp.com.recapp.activity.Utils;
import recapp.com.recapp.adapter.CustomAdapter;
import recapp.com.recapp.adapter.HomePagerAdpater;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.database.DBHelper;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.model.LoginDataModel;
import recapp.com.recapp.model.SubjectListData;

public class HomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener
{
    public static String TAG = "HomeFragment";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    ImageView btnAdd;
    RelativeLayout rlSelectSubject;
    EditText edtSubject;
    public static List<String> subjectList;
    public  static List<String> tabList ;
    public  static List<Fragment> fragmentList ;
    public static ArrayList<String> audioTypesSelectedList = new ArrayList<>();
    TextView title;
    RelativeLayout relative;
    Button backBtn,btnHelp;
    ImageButton btnRecord, btnDelete , btnBook , btnPaper;
    RelativeLayout rl_pink , rl_purple, rl_orange,rl_green;
    //public  byte[] byteFile , byteImage;

    public static boolean isAdded = false;
    ArrayList<String>  contentList = new ArrayList<>();
    ArrayList<byte[]>  imagelist = new ArrayList<>();
    ArrayList<byte[]>  AUDIOLIST = new ArrayList<>();
    LoginDataModel dataModel;

    public static String selectedAudioType;

    String categoryName;
    public static List<SubjectListData>  audioList;

    String subject,first;
    SubjectListData data;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragmnet_home,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

        categoryName = RecappApplication.getInstance().getSPValueByKey(Const.RC_CATEGORY_NAME);
        System.out.println("=====Category name===" + categoryName);

        try {

            String isfrom = getArguments().getString("isFrom");

            System.out.println("====Is from : " + isfrom);

            if(isfrom.equals(null)) {

            }
            else {

                subject = getArguments().getString("subject");

                System.out.println("====GET string : " + subject);
                assert subject != null;
                first = subject.substring(0,2);

                System.out.println("====GET string : " + first);

                data = new SubjectListData(first,subject);
                audioList.add(data);

                System.out.println("===AUDIO LIST : " + audioList);

                if(audioList != null)
                {
                    viewPager.setCurrentItem(1);
                }

            }

        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        prepareData();

        DBHelper db = new DBHelper(getActivity());
        if (db != null)
        {
            ArrayList<HashMap<String, String>> userList = db.GetRecordList();
            ArrayList<HashMap<String, byte[]>> audioList = db.retreiveAudioFromDB();

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

//                    byte[] byteFile = audioList.get(i).get("audio_file");
//                    byte[] byteImage = audioList.get(i).get("image_file");
                   /* AUDIOLIST.add(byteFile);
                    imagelist.add(byteImage);*/
                }
            }
            if (audioList != null && !audioList.isEmpty() && audioList.size() > 0)
            {
                System.out.println("===audioList list : " + audioList);
                System.out.println("===audioList size: " + audioList.size());

                for (int i = 0; i < audioList.size(); i++)
                {

                    byte[] byteFile = audioList.get(i).get("audio_file");
                    byte[] byteImage = audioList.get(i).get("image_file");
                    AUDIOLIST.add(byteFile);
                    imagelist.add(byteImage);

                }
            }
          /*  if (userList != null && !userList.isEmpty() && userList.size() > 1) {
                System.out.println("===user lis : " + userList);
                Toast.makeText(getActivity(), "list " + userList, Toast.LENGTH_SHORT).show();
                String value = userList.get(1).get("subject_name");
                String audioType = userList.get(1).get("audio_type");
                String file = userList.get(1).get("file_name");

                ArrayList<HashMap<String, byte[]>> audioList = db.retreiveAudioFromDB("11");
                byteFile = audioList.get(1).get("audio_file");
                byteImage = audioList.get(1).get("image_file");

                System.out.println("==valur : " + value + " : " + audioType + " : " + file + " : " + byteFile+ " : "+byteImage);

            }*/

            System.out.println("===File name list: " + contentList);
            System.out.println("===Audio name list: " + AUDIOLIST);
            System.out.println("===Image name list: " + imagelist);

        }

        title = getActivity().findViewById(R.id.txt_actionbar_title);
        relative = getActivity().findViewById(R.id.rl_action_bar);
        backBtn = getActivity().findViewById(R.id.btn_back);
        btnHelp = getActivity().findViewById(R.id.btn_help);
        rlSelectSubject = view.findViewById(R.id.rl_selectSubject);
        edtSubject = view.findViewById(R.id.edt_select_subject);
        btnAdd = view.findViewById(R.id.img_add);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);

        btnBook = getActivity().findViewById(R.id.btnBook);
        btnRecord = getActivity().findViewById(R.id.btnRecord);
        btnPaper = getActivity().findViewById(R.id.btnPaper);
        btnDelete = getActivity().findViewById(R.id.btnDelete);
        rl_pink = getActivity().findViewById(R.id.rl_pink);
        rl_purple = getActivity().findViewById(R.id.rl_purple);
        rl_green = getActivity().findViewById(R.id.rl_green);
        rl_orange = getActivity().findViewById(R.id.rl_orange);

        title.setText("Home Page");
        backBtn.setVisibility(View.GONE);
        btnHelp.setVisibility(View.VISIBLE);
        relative.setBackground(getResources().getDrawable(R.drawable.bg_orange_gradient_90));
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        edtSubject.setInputType(InputType.TYPE_NULL);
        btnAdd.setOnClickListener(this);

        btnRecord.setBackgroundResource(R.drawable.tab1_circle_pink);
        btnRecord.setImageResource(R.drawable.rc_unselected_redio);
        btnBook.setBackgroundResource(R.drawable.tab2_circle_green);
        btnBook.setImageResource(R.drawable.rc_unselected_recap);
        btnPaper.setBackgroundResource(R.drawable.tab3_circle_purple);
        btnPaper.setImageResource(R.drawable.rc_unselected_revise);
        btnDelete.setBackgroundResource(R.drawable.tab4_circle_orange);
        btnDelete.setImageResource(R.drawable.rc_unselected_delete);
        rl_pink.setBackgroundColor(getResources().getColor(R.color.colorPink));
        rl_green.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        rl_purple.setBackgroundColor(getResources().getColor(R.color.colorPurple));
        rl_orange.setBackgroundColor(getResources().getColor(R.color.colorOrange));

        selectSubject();
    }

    public void selectSubject()
    {
        final ArrayAdapter<String> spinner_breed = new  ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item, subjectList);
        {
            edtSubject.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    new AlertDialog.Builder(getActivity())

                            .setAdapter(spinner_breed, new DialogInterface.OnClickListener()
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
    }

    public void prepareData()
    {
      /*  subjectList = new ArrayList<>();
        subjectList.add("Biology");
        subjectList.add("Maths");
        subjectList.add("Physics");
        subjectList.add("English");*/

        if(RecappApplication.getInstance().getSPValueByKey(Const.RC_PROCESS_CODE).isEmpty() || RecappApplication.getInstance().getSPValueByKey(Const.RC_PROCESS_CODE) == null)
        {
            tabList = new ArrayList<>();
            // tabList.add("School");
            tabList.add("Own");

            fragmentList = new ArrayList<>();
            fragmentList.add(new AddNewSubjectFragment());
        }

        else{

            tabList = new ArrayList<>();
            tabList.add(categoryName);
            //tabList.add("School");
            tabList.add("Own");

            fragmentList = new ArrayList<>();
            fragmentList.add(new SchoolFragment());
            fragmentList.add(new AddNewSubjectFragment());
        }

       /* if (MakePaymentDetailActivity.ownReg == true)
        {
            tabList = new ArrayList<>();
           // tabList.add("School");
            tabList.add("Own");

            fragmentList = new ArrayList<>();
            fragmentList.add(new AddNewSubjectFragment());
        }else {

            tabList = new ArrayList<>();
            tabList.add(categoryName);
            //tabList.add("School");
            tabList.add("Own");

            fragmentList = new ArrayList<>();
            fragmentList.add(new SchoolFragment());
            fragmentList.add(new AddNewSubjectFragment());
        }*/

    }

    private void setupViewPager(ViewPager viewPager)
    {

        HomePagerAdpater adapter = new HomePagerAdpater(getActivity().getSupportFragmentManager());

        for (int i = 0 ; i < tabList.size(); i++ )
        {
            adapter.addFrag(fragmentList.get(i), tabList.get(i));
        }

        RecappApplication.getInstance().setSPValueByKey(Const.SELECTED_CATEGORY , tabList.get(0));

       /* adapter.addFrag(new SchoolFragment(), "School");
        adapter.addFrag(new AddNewSubjectFragment(), "Own");
        //adapter.addFrag(new TutionFragment(), "Tuition");*/

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.img_add:
            {

                isAdded = true;

                if(RecappApplication.getInstance().getSPValueByKey(Const.RC_PROCESS_CODE).isEmpty() || RecappApplication.getInstance().getSPValueByKey(Const.RC_PROCESS_CODE) == null)
                {
                    btnAdd.setClickable(false);
                }

                else {
                    AddContentFragment fragment = new AddContentFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            /*    isAdded = true;

                AddContentFragment fragment = new AddContentFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_main_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
*/
                break;
            }
            default:
            {
                break;
            }
        }

    }

  /*  public static void showPopup(View anchorView , final Activity aActivity)
    {

        View popupView = aActivity.getLayoutInflater().inflate(R.layout.popup_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        final CheckBox cbOne = popupView.findViewById(R.id.cb_first);
        final CheckBox cbTwo = popupView.findViewById(R.id.cb_second);
        final CheckBox cbThrid = popupView.findViewById(R.id.cb_third);
        final CheckBox cbFour = popupView.findViewById(R.id.cb_four);

        //cbOne.setChecked(true);
        //selectedAudioType = cbOne.getText().toString();

        cbOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked)
                {
                    cbTwo.setChecked(false);
                    cbThrid.setChecked(false);
                    cbFour.setChecked(false);
                    selectedAudioType = cbOne.getText().toString();

                  *//*  audioTypesSelectedList.add(cbOne.getText().toString());
                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {
                        listString += s + "\n";
                    }
*//*
                    //Toast.makeText(aActivity, "Selected value :\n"+listString, Toast.LENGTH_SHORT).show();

                }else {
                  *//*  audioTypesSelectedList.remove(cbOne.getText().toString());
                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {
                        listString += s + "\n";
                    }*//*

                   // Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    cbOne.setChecked(false);
                    cbThrid.setChecked(false);
                    cbFour.setChecked(false);

                    selectedAudioType = cbTwo.getText().toString();

                 *//*   audioTypesSelectedList.add(cbTwo.getText().toString());
                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {
                        listString += s + "\n";
                    }*//*

                 //   Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();

                }else {
                    audioTypesSelectedList.remove(cbTwo.getText().toString());
                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {
                        listString += s + "\n";
                    }

                 //   Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbThrid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked)
                {
                    cbTwo.setChecked(false);
                    cbOne.setChecked(false);
                    cbFour.setChecked(false);
                    selectedAudioType = cbThrid.getText().toString();

                *//*    audioTypesSelectedList.add(cbThrid.getText().toString());

                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {

                        listString += s + "\n";
                    }*//*

                  //  Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();


                }else {
                  *//*  audioTypesSelectedList.remove(cbThrid.getText().toString());
                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {
                        listString += s + "\n";
                    }*//*

                   // Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {

                    cbTwo.setChecked(false);
                    cbThrid.setChecked(false);
                    cbOne.setChecked(false);
                    selectedAudioType = cbFour.getText().toString();

                   *//* audioTypesSelectedList.add(cbFour.getText().toString());
                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {
                        listString += s + "\n";
                    }*//*

                 //   Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();


                }else
                {
                  *//*  audioTypesSelectedList.remove(cbFour.getText().toString());
                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {
                        listString += s + "\n";
                    }*//*

                  //  Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Initialize more widgets from `popup_layout.xml`
        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());

    }*/



    public static void showPopup(View anchorView , final Activity aActivity)
    {
        Log.i("HOME==>","SelectAudio==>"+selectedAudioType);
        View popupView = aActivity.getLayoutInflater().inflate(R.layout.popup_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

       // Example: If you have a TextView inside `popup_layout.xml`

        final CheckBox cbOne = popupView.findViewById(R.id.cb_first);
        final CheckBox cbTwo = popupView.findViewById(R.id.cb_second);
        final CheckBox cbThrid = popupView.findViewById(R.id.cb_third);
        final CheckBox cbFour = popupView.findViewById(R.id.cb_four);

        // Log.i("SelectAudioType==>","AudioType=>"+selectedAudioType);
        // cbOne.setChecked(true);
        //cbOne.setChecked(true);
        //selectedAudioType = cbOne.getText().toString();

        Log.i("VALUE==>","AudioType==>"+ Utils.audioType);

        if(Utils.audioType != null && selectedAudioType != null) {

            if (Utils.audioType.equals("Question and Answer")) {
                cbOne.setChecked(true);
                selectedAudioType = "Question and Answer";
            } else if (Utils.audioType.equals("Definitions")) {
                cbTwo.setChecked(true);
                selectedAudioType = "Definitions";
            } else if (Utils.audioType.equals("Explanation")) {
                cbThrid.setChecked(true);
                selectedAudioType = "Explanation";
            } else if (Utils.audioType.equals("Summary")) {
                cbFour.setChecked(true);
                selectedAudioType = "Summary";
            }
        }

        else {

            cbOne.setChecked(true);
            selectedAudioType = "Question and Answer";
        }

        cbOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked)
                {
                    cbTwo.setChecked(false);
                    cbThrid.setChecked(false);
                    cbFour.setChecked(false);
                    selectedAudioType = cbOne.getText().toString();
                    Utils.audioType = selectedAudioType;

/* audioTypesSelectedList.add(cbOne.getText().toString());
String listString = "";

for (String s : audioTypesSelectedList)
{
listString += s + "\n";
}
*/
//Toast.makeText(aActivity, "Selected value :\n"+listString, Toast.LENGTH_SHORT).show();

                }else {
/* audioTypesSelectedList.remove(cbOne.getText().toString());
String listString = "";

for (String s : audioTypesSelectedList)
{
listString += s + "\n";
}*/

// Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    cbOne.setChecked(false);
                    cbThrid.setChecked(false);
                    cbFour.setChecked(false);

                    selectedAudioType = cbTwo.getText().toString();
                    Utils.audioType=selectedAudioType;

/* audioTypesSelectedList.add(cbTwo.getText().toString());
String listString = "";

for (String s : audioTypesSelectedList)
{
listString += s + "\n";
}*/

// Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();

                }else {
                    audioTypesSelectedList.remove(cbTwo.getText().toString());
                    String listString = "";

                    for (String s : audioTypesSelectedList)
                    {
                        listString += s + "\n";
                    }

// Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbThrid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked)
                {
                    cbTwo.setChecked(false);
                    cbOne.setChecked(false);
                    cbFour.setChecked(false);
                    selectedAudioType = cbThrid.getText().toString();
                    Utils.audioType=selectedAudioType;

/* audioTypesSelectedList.add(cbThrid.getText().toString());

String listString = "";

for (String s : audioTypesSelectedList)
{

listString += s + "\n";
}*/

// Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();


                }else {
/* audioTypesSelectedList.remove(cbThrid.getText().toString());
String listString = "";

for (String s : audioTypesSelectedList)
{
listString += s + "\n";
}*/

// Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {

                    cbTwo.setChecked(false);
                    cbThrid.setChecked(false);
                    cbOne.setChecked(false);
                    selectedAudioType = cbFour.getText().toString();
                    Utils.audioType=selectedAudioType;

/* audioTypesSelectedList.add(cbFour.getText().toString());
String listString = "";

for (String s : audioTypesSelectedList)
{
listString += s + "\n";
}*/

// Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();


                }else
                {
/* audioTypesSelectedList.remove(cbFour.getText().toString());
String listString = "";

for (String s : audioTypesSelectedList)
{
listString += s + "\n";
}*/

// Toast.makeText(aActivity, "Selected value :\n "+listString, Toast.LENGTH_SHORT).show();
                }

            }
        });
// Initialize more widgets from `popup_layout.xml`
// If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

// If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

// Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

// Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());

    }
}
