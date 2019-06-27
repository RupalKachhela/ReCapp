package recapp.com.recapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import recapp.com.recapp.R;
import recapp.com.recapp.interfaces.ItemClickListener;
import recapp.com.recapp.model.ReviseListData;


public class ReviseListRecyclerAdapter extends RecyclerView.Adapter<ReviseListRecyclerAdapter.SubjectViewHolder>
{

    private WeakReference<Activity> mActivityWeakReference;
    private List<ReviseListData> subjectDataList;
    private ItemClickListener mListeners;
    MediaPlayer mPlayer;
    Button btnPlay , btnStop;
    ImageButton btnPlayBack;

    public ReviseListRecyclerAdapter(Activity aActivity, List<ReviseListData> aProductsDataList)
    {
        mActivityWeakReference = new WeakReference<>(aActivity);
        subjectDataList = aProductsDataList;

        System.out.println("==list : "+subjectDataList);
    }

    private Activity getActivity()
    {
        return mActivityWeakReference != null ? mActivityWeakReference.get() : null;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ite_row_revise_list, parent, false);
        SubjectViewHolder  vh=   new SubjectViewHolder(view , this);
        btnPlay = view.findViewById(R.id.btn_rstart_revise);
        btnStop = view.findViewById(R.id.btn_rpause_revise);
        btnPlayBack = view.findViewById(R.id.btn_rprevious_revise);

        System.out.println("==btn play adapter id : "+btnPlay);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position)
    {
           if (!subjectDataList.isEmpty())
           {
               final ReviseListData pckdata = subjectDataList.get(position);

               if (pckdata.getTopicName() != null && !pckdata.getTopicName().isEmpty())
               {

                   System.out.println("=====Image one===" + pckdata.getUrl());
                    /*  Glide.with(getActivity())
                           .load(pckdata.getUrl())
                           .fitCenter()
                           .into(holder.imgTopic);
                      */

                   Bitmap bmp = BitmapFactory.decodeByteArray(pckdata.getUrl(), 0, pckdata.getUrl().length);

                   holder.imgTopic.setImageBitmap(bmp);

                      btnPlay.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              mPlayer = new MediaPlayer();
                              playMp3(pckdata.getAudio());
                              Toast.makeText(getActivity(), "Start playing", Toast.LENGTH_SHORT).show();
                          }
                      });

                   btnStop.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           mPlayer.release();
                           mPlayer = null;
                           Toast.makeText(getActivity(), "Stop playing", Toast.LENGTH_SHORT).show();
                       }
                   });


                   btnPlayBack.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Toast.makeText(getActivity(), "PlayBack playing", Toast.LENGTH_SHORT).show();
                       }
                   });

               }

           }
    }

    @Override
    public int getItemCount()
    {
        return subjectDataList.size();
    }


    public class SubjectViewHolder extends RecyclerView.ViewHolder
    {

         ImageView imgTopic;
         ReviseListRecyclerAdapter adapter;

        SubjectViewHolder(View itemView , ReviseListRecyclerAdapter subjectAdapter)
        {
            super(itemView);

                 adapter = subjectAdapter;
                 imgTopic = itemView.findViewById(R.id.img_revise_item);

        }

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
}



