package recapp.com.recapp.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



import recapp.com.recapp.R;


public class CustomDialogBox extends Dialog implements View.OnClickListener
{

    public static final int DT_PROGRESS = 0;
    public static final int DT_PROGRESS_MSG = 1;
    public static final int DT_MSG_ERROR = 2;
    public static final int DT_NO_INTERNET = 3;
    public static final int DT_FORGOT_PASSWORD = 4;
    public static final int DT_DELETE = 5;
    public static final int DT_LOADING = 6;
    public static final int DT_LOGOUT =7;

    public Activity context;
    private TextView btnOk,btnCancel;
    private TextView tvProgressMessage,tvNote;
    private ProgressBar progressBar;
    private ImageButton imgbtn_status;
    private LinearLayout linearLayout;
    private Button btnPositive;
    private Button btnNegative;
    private DialogClickListener dialogClickListener;
    private int dialogType;
    String message = "";
    int img = 0;
    String action = "";

    public CustomDialogBox(Activity a, int dialogType)
    {
        super(a);
        this.context = a;
        this.dialogType = dialogType;
    }

    public CustomDialogBox(Context context, int dtDelete) {
        super(context, dtDelete);

    }

    public void setOkButtonClickListener(View.OnClickListener onClickListener)
    {
        btnPositive.setOnClickListener(onClickListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_customdialog);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        btn_positive =  findViewById(R.id.btn_dialog_positive);
        btn_positive.setOnClickListener(this);*/

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        switch (dialogType)
        {
            case DT_PROGRESS:

            case DT_PROGRESS_MSG:

                setContentView(R.layout.custom_dialog_progress);
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                progressBar = findViewById(R.id.customProgress);

            /*    progressBar.getIndeterminateDrawable().
                        setColorFilter(ContextCompat.getColor(AppController.getContext(),R.color.colorPrimary),
                                PorterDuff.Mode.MULTIPLY);*/
                tvProgressMessage = findViewById(R.id.tvProgressMessage);
                tvProgressMessage.setVisibility(View.GONE);
                if(dialogType == DT_PROGRESS_MSG)
                {
                    tvProgressMessage.setVisibility(View.VISIBLE);
                    tvProgressMessage.setText(message);
                }
                break;

            case DT_LOADING:

                setContentView(R.layout.progress_layout);
                getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                progressBar = findViewById(R.id.progressbar_loading);

            /*    progressBar.getIndeterminateDrawable().
                        setColorFilter(ContextCompat.getColor(AppController.getContext(),R.color.colorPrimary),
                                PorterDuff.Mode.MULTIPLY);*/
            /*
                tvProgressMessage = findViewById(R.id.tvProgressMessage);
                tvProgressMessage.setVisibility(View.GONE);
                if(dialogType == DT_PROGRESS_MSG)
                {
                    tvProgressMessage.setVisibility(View.VISIBLE);
                    tvProgressMessage.setText(message);
                }*/
                break;

          /*  case DT_MSG_ERROR:

                setContentView(R.layout.custom_dialog_no_internet);
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                tvProgressMessage = findViewById(R.id.tvProgressMessage);
                tvProgressMessage.setVisibility(View.VISIBLE);
                tvProgressMessage.setText(message);

                btnCancel = findViewById(R.id.btnCancel);
                btnOk = findViewById(R.id.btnOk);
                btnOk.setOnClickListener(this);
                btnCancel.setOnClickListener(this);

                btnOk.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                break;*/

            case DT_NO_INTERNET:

                setContentView(R.layout.custom_dialog_no_internet);
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvProgressMessage = findViewById(R.id.tvProgressMessage);
                tvProgressMessage.setVisibility(View.VISIBLE);
                tvProgressMessage.setText(context.getResources().getString(R.string.no_internet));
                tvProgressMessage.setGravity(Gravity.CENTER);
                btnCancel = findViewById(R.id.btnCancel);
                btnOk = findViewById(R.id.btnOk);
                btnOk.setOnClickListener(this);
                btnCancel.setOnClickListener(this);

                btnOk.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);

                break;

           /* case DT_FORGOT_PASSWORD:

                setContentView(R.layout.layout_customdialog);
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                imgbtn_status = findViewById(R.id.imgbtn_dialog_status);
                imgbtn_status.setBackgroundResource(R.drawable.ic_approve_tick);
                tvNote = findViewById(R.id.tv_dialog_msg);
                tvNote.setText(getContext().getResources().getString(R.string.reset_password_send_link_msg));
                btnOk = findViewById(R.id.btn_dialog_positive);
                btnOk.setVisibility(View.VISIBLE);
                String send = getContext().getResources().getString(R.string.action_done);
                btnOk.setText(send);
                btnOk.setOnClickListener(this);

                if(dialogType == DT_FORGOT_PASSWORD)
                {
                    tvNote.setText(message);
                    imgbtn_status.setBackgroundResource(img);
                    btnOk.setText(action);
                }

                break;

            case DT_DELETE:

                setContentView(R.layout.layout_customdialog);
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                imgbtn_status = findViewById(R.id.imgbtn_dialog_status);
                imgbtn_status.setVisibility(View.GONE);
                tvNote = findViewById(R.id.tv_dialog_msg);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tvNote.getLayoutParams();
                params.setMargins(90, 150, 90, 50);//substitute parameters for left, top, right, bottom
                tvNote.setLayoutParams(params);
                tvNote.setText(getContext().getResources().getString(R.string.cancle_booking_dialog));
                linearLayout = findViewById(R.id.ll_dialog_actionbtn);
                btnPositive = findViewById(R.id.btn_dialog_actionpositive);
                btnNegative = findViewById(R.id.btn_dialog_actionnegative);
                linearLayout.setVisibility(View.VISIBLE);
                btnNegative.setOnClickListener(this);
                btnPositive.setOnClickListener(this);

               //  btnPositive.setOnClickListener(this);
               // setOkButtonClickListener(this);
                break;

            case DT_LOGOUT:

                setContentView(R.layout.layout_customdialog);
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                imgbtn_status = findViewById(R.id.imgbtn_dialog_status);
                imgbtn_status.setVisibility(View.GONE);
                tvNote = findViewById(R.id.tv_dialog_msg);
                params = (LinearLayout.LayoutParams)tvNote.getLayoutParams();
                params.setMargins(90, 150, 90, 50);//substitute parameters for left, top, right, bottom
                tvNote.setLayoutParams(params);
                tvNote.setText(getContext().getResources().getString(R.string.logout_dialog));
                linearLayout = findViewById(R.id.ll_dialog_actionbtn);
                btnPositive = findViewById(R.id.btn_dialog_actionpositive);
                btnNegative = findViewById(R.id.btn_dialog_actionnegative);
                linearLayout.setVisibility(View.VISIBLE);
                btnNegative.setOnClickListener(this);
                btnPositive.setOnClickListener(this);

                //  btnPositive.setOnClickListener(this);
                // setOkButtonClickListener(this);
                break;*/

        }

    }


    @Override
    public void onClick(View v)
    {
        dismiss();

    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setIcon(int img)
    {
        this.img=img;
    }

    public void setActionMessage(String action)
    {
        this.action = action;
    }



}
