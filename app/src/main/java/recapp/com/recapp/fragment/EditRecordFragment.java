package recapp.com.recapp.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import recapp.com.recapp.R;


public class EditRecordFragment extends android.support.v4.app.Fragment implements View.OnClickListener
{

    EditText edtSubject;
    TextView title;
    RelativeLayout relative ,rl_nextAction;
    Button backBtn,btnHelp ;
    ImageView imgAddContentPhoto;
    ImageButton imgbtnStart ,imgbtnRestart ,imgbtnPause , imgbtnPlay ,imgbtnDone;
    private int GALLERY = 1;
    private static final int REQUEST_CAMERA = 0;
    Bitmap bitmap;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static String mFileName = null;
    RelativeLayout rl_edit , rl_delete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragmnet_edit_record,container,false);
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
         rl_nextAction = view.findViewById(R.id.rl_next_editrecord);
         edtSubject = view.findViewById(R.id.edt_selcet_subject_record);
        edtSubject = view.findViewById(R.id.edt_selcet_subject_record);
        imgbtnRestart = view.findViewById(R.id.btn_rstart_record);
        imgbtnStart = view.findViewById(R.id.btn_rprevious_record);
        imgbtnPlay = view.findViewById(R.id.btn_rpause_record);
        imgbtnPause = view.findViewById(R.id.btn_rstop_record);
        imgbtnDone = view.findViewById(R.id.btn_rplay_record);
        imgAddContentPhoto = view.findViewById(R.id.img_addphoto_record);
       rl_edit= view.findViewById(R.id.img_edit_record);
       rl_delete = view.findViewById(R.id.img_delete_record);

        backBtn.setVisibility(View.VISIBLE);
        btnHelp.setVisibility(View.INVISIBLE);
        relative.setBackgroundColor(getResources().getColor(R.color.colorPink));
        title.setText("Record");

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/AudioRecording.3gp";

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
                            .setAdapter(spinner_breed, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {

                                    edtSubject.setText(HomeFragment.subjectList.get(which));
                                    dialog.dismiss();

                                }
                            }).create().show();

                }
            });
        }

        rl_nextAction.setOnClickListener(this);
        imgbtnRestart.setOnClickListener(this);
        imgbtnStart.setOnClickListener(this);
        imgbtnPlay.setOnClickListener(this);
        imgbtnPause.setOnClickListener(this);
        imgbtnDone.setOnClickListener(this);
        rl_edit.setOnClickListener(this);
        rl_delete.setOnClickListener(this);
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.img_edit_record:
            {
                requestMultiplePermissions();
                showPictureDialog();

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

            case R.id.img_delete_record:
            {
                Toast.makeText(getContext(), "Delete ", Toast.LENGTH_SHORT).show();
                break;

            }

            case  R.id.rl_next_editrecord:
            {
                System.out.println("==click next===");
                RecordConfirmFragment fragment = new RecordConfirmFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_main_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            }

            case  R.id.btn_rprevious_record:
            {
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile(mFileName);
                try {
                    mRecorder.prepare();

                } catch (IOException e) {
                    Log.e("AudioCapture", "prepare() failed");
                }
                mRecorder.start();
                Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_LONG).show();
                break;

            }


            case  R.id.btn_rstop_record:
            {
                mPlayer.release();
                mPlayer = null;
                Toast.makeText(getActivity(),"Playing Audio Stopped", Toast.LENGTH_SHORT).show();

                break;
            }
            case  R.id.btn_rstart_record:
            {

                break;
            }
            case  R.id.btn_rpause_record:
            {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getActivity(), "Recording Stopped", Toast.LENGTH_LONG).show();
                break;

            }
            case  R.id.btn_rplay_record:
            {
                mPlayer = new MediaPlayer();
                try
                {

                    mPlayer.setDataSource(mFileName);
                    mPlayer.prepare();
                    mPlayer.start();
                    Toast.makeText(getActivity(), "Recording Started Playing", Toast.LENGTH_LONG).show();

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

                try
                {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImage);

                    //  encodedImage = saveImage(bitmap);

                    imgAddContentPhoto.setImageBitmap(bitmap);

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
            onCaptureImageResult(data);
        }

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
}
