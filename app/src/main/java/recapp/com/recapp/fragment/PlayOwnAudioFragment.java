package recapp.com.recapp.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import recapp.com.recapp.R;
import recapp.com.recapp.application.RecappApplication;
import recapp.com.recapp.database.DBHelper;
import recapp.com.recapp.helper.Const;


public class PlayOwnAudioFragment extends android.support.v4.app.Fragment {

    TextView tvtopicname;
    Button btnplay,btnstop,btnprevious,btnnext;
    ImageView image;
    String subject;
    ArrayList<HashMap<String, byte[]>> blobList;
    ArrayList<HashMap<String, String>> userList;
    ArrayList<byte[]>  imagelist = new ArrayList<>();
    ArrayList<byte[]>  audioList = new ArrayList<>();
    ArrayList<String>  fileList = new ArrayList<>();
    MediaPlayer mediaPlayer;
    byte[] byteFile;
    int count = 0;
    String file,topicname;
    ArrayList<String>  audioFileNameList = new ArrayList<>();
    String newFileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_own_audio, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorOrange));

        mediaPlayer = new MediaPlayer();

        btnplay = view.findViewById(R.id.btn_play);
        btnstop = view.findViewById(R.id.btn_stop);
        btnprevious = view.findViewById(R.id.btn_prevoius);
        btnnext = view.findViewById(R.id.btn_next);
        image = view.findViewById(R.id.iv_img);
        tvtopicname = view.findViewById(R.id.tvTopicname);

        if (getArguments() != null) {

            subject = getArguments().getString("subject");
        }

        String categoryName = RecappApplication.getInstance().getSPValueByKey(Const.RC_USER_ID);

        DBHelper db = new DBHelper(getActivity());

        if (db != null) {
            blobList = db.retreiveAudioFromDBByCategory(subject, categoryName);
            userList = db.GetRecordListByCategory(subject, categoryName);

            for(int i = 0;i<blobList.size();i++) {
                byteFile = blobList.get(i).get("audio_file");
                byte[] byteImage = blobList.get(i).get("image_file");
                file = userList.get(i).get("file_name");
                audioList.add(byteFile);
                imagelist.add(byteImage);
                fileList.add(file);

                String audioType = userList.get(i).get("audio_type");
                System.out.println("=============AUDIO TYPE :::::::::::: " + audioType);

             //   System.out.println("======FILE lIST : " + fileList);

            }

            System.out.println("===========AUDIO LIST : " + audioList  + "Image List : " + imagelist + "File List : " + fileList);

            //get only file name from full path
            for(int j =0;j<fileList.size();j++)
            {
                System.out.println("====File name: " + fileList.get(j).endsWith(".3gp"));
                String path = fileList.get(j);
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
        }

        if(!userList.isEmpty() && !blobList.isEmpty())
        {
            try {
                Glide.with(getActivity())
                        .load(imagelist.get(0))
                        .fitCenter()
                        .into(image);

                file = fileList.get(0);

                topicname = audioFileNameList.get(0);
                tvtopicname.setText("Playing " + topicname);
            }
            catch (IndexOutOfBoundsException e)
            {
                e.getMessage();
            }

            catch (NullPointerException e)
            {
                e.getMessage();
            }
        }

       // byteFile = audioList.get(0);

        //playMp3(audioList.get(0));

        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (count > 0) {
                        count--;
                        Glide.with(getActivity())
                                .load(imagelist.get(count))
                                .fitCenter()
                                .into(image);

                        byteFile = blobList.get(count).get("audio_file");
                        file = fileList.get(count);
                        //file = userList.get(count).get("file_name");

                        topicname = audioFileNameList.get(count);
                        //topicname = userList.get(count).get("topic_name");
                        tvtopicname.setText("Playing " + topicname);
                        System.out.println("===========previous COUNT : " + count);
                       // playMp3(byteFile);
                    }
                    else {

                    }
                }
                catch (IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }

            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (count < audioList.size()-1)
                    {
                        count++;
                        Glide.with(getActivity())
                                .load(imagelist.get(count))
                                .fitCenter()
                                .into(image);

                        byteFile = blobList.get(count).get("audio_file");
                        file = fileList.get(count);
                        //file = userList.get(count).get("file_name");

                        topicname = audioFileNameList.get(count);
                        //topicname = userList.get(count).get("topic_name");
                        tvtopicname.setText("Playing " + topicname);

                        System.out.println("===========next COUNT : " + count);

                        //playMp3(byteFile);
                    }else
                    {

                    }
                }
                catch (IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
            }
        });


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // playMp3(audioList.get(count));
                playMp3(byteFile);   // using byte file

                //using file path to media player

             /*   try
                {
                    System.out.println("=========GET FILE NAME : " +file);
                    mediaPlayer.setDataSource(file);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer.setScreenOnWhilePlaying(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }*/

                Toast.makeText(getActivity(),"Recording start playing",Toast.LENGTH_SHORT).show();
                System.out.println("=========Recording play : " + byteFile +file);
                //System.out.println("=========Recording play : " + byteFile);

               /* for(int j = 0; j<audioList.size();j++) {

                    playMp3(audioList.get(j));
                    Toast.makeText(getActivity(),"Recording start playing" + j,Toast.LENGTH_SHORT).show();

                }*/
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                if(count<audioList.size()){
                    mp.stop();
                   //Play next sound
                    playMp3(audioList.get(count));
                    mp.start();
                    count++;
                }
            }

        });


        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer == null)
                {
                    Toast.makeText(getActivity(),"First do Start Play Recording", Toast.LENGTH_SHORT).show();
                }

                else {

                    mediaPlayer.release();
                    mediaPlayer = null;
                    Toast.makeText(getActivity(), "Recording Stop", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void playMp3(byte[] mp3SoundByteArray) {
        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("Recapp", ".mp3", getActivity().getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);

            System.out.println("============TEMP FILE : " + fos + tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            // resetting mediaplayer instance to evade problems
            mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();

            // In case you run into issues with threading consider new instance like:
            // MediaPlayer mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
