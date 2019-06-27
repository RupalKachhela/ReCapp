package recapp.com.recapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import recapp.com.recapp.R;
import recapp.com.recapp.activity.MainActivity;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.fragment.HomeFragment;
import recapp.com.recapp.helper.Const;

public class AddContentFragment extends Fragment
{

    EditText edtSubject;
    RelativeLayout rl_container;
    RelativeLayout rl_downloadContainer,rl_cardContent;
    public  boolean isDownloaded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragmnet_add_content,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

        TextView title = getActivity().findViewById(R.id.txt_actionbar_title);
        RelativeLayout relative = getActivity().findViewById(R.id.rl_action_bar);
        Button backBtn = getActivity().findViewById(R.id.btn_back);
        Button btnHelp = getActivity().findViewById(R.id.btn_help);
        final EditText edtCode = getActivity().findViewById(R.id.edt_code_download);
        Button btnDownload = getActivity().findViewById(R.id.btn_download);
        rl_container = getActivity().findViewById(R.id.container);
        rl_downloadContainer = getActivity().findViewById(R.id.rl_downloadcontainer);
        rl_cardContent = getActivity().findViewById(R.id.rl_addContent);


//        backBtn.setVisibility(View.VISIBLE);
//        relative.setBackgroundColor(getResources().getColor(R.color.colorOrange));
//        title.setText("Add Content");

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

        btnDownload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                /*if (edtCode.getText().toString().length() == 0)
                {
                    Toast.makeText(getActivity(), "Enter code", Toast.LENGTH_SHORT).show();

                }else
                {*/

                if (HomeFragment.isAdded == true)
                {
                    hideInputSoftKey();
                  /*  rl_container.setVisibility(View.VISIBLE);
                    rl_downloadContainer.setVisibility(View.GONE);
                    rl_cardContent.setVisibility(View.GONE);*/

                    RecappApplication.getInstance().setSPValueByKey(Const.IS_DOWNlODED , true);
                    HomeFragment fragment = new HomeFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main_container, fragment);
                    fragmentTransaction.commit();

                }else {
                    isDownloaded = true;

                    hideInputSoftKey();
                  /*  rl_container.setVisibility(View.VISIBLE);
                    rl_downloadContainer.setVisibility(View.GONE);
                    rl_cardContent.setVisibility(View.GONE);*/

                    RecappApplication.getInstance().setSPValueByKey(Const.IS_DOWNlODED , isDownloaded);
                    SchoolFragment fragment = new SchoolFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_school, fragment);
                    fragmentTransaction.commit();

                }

               // }
            }
        });

    }

    private void hideInputSoftKey()
    {
        try
        {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        }
        catch (Exception aE)
        {
            aE.printStackTrace();
        }

    }

}
