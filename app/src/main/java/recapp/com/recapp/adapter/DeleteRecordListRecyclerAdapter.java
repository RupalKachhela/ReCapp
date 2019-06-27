package recapp.com.recapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.List;

import recapp.com.recapp.R;
import recapp.com.recapp.fragment.DeleteFragment;
import recapp.com.recapp.interfaces.ItemClickListener;
import recapp.com.recapp.model.DeleteRecordListData;
import recapp.com.recapp.model.ReviseListData;

import static java.security.AccessController.getContext;

public class DeleteRecordListRecyclerAdapter extends RecyclerView.Adapter<DeleteRecordListRecyclerAdapter.SubjectViewHolder>
{

    private WeakReference<Activity> mActivityWeakReference;
    private List<DeleteRecordListData> subjectDataList;
    private ItemClickListener mListeners;



    public DeleteRecordListRecyclerAdapter(Activity aActivity, List<DeleteRecordListData> aProductsDataList)
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_delete, parent, false);
        SubjectViewHolder  vh=   new SubjectViewHolder(view , this);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, final int position)
    {
           if (!subjectDataList.isEmpty())
           {
               DeleteRecordListData pckdata = subjectDataList.get(position);

                   System.out.println("=====Image one===" + pckdata.getChapterName());

               holder.tvChapter.setText(pckdata.getChapterName());
               holder.tvTopic.setText(pckdata.getTopicName());
               holder.tvRecording.setText(pckdata.getFileName());

           }


    }
    public void setListener(ItemClickListener listener)
    {
        mListeners = listener;
    }

    @Override
    public int getItemCount()
    {
        return subjectDataList.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder
    {

         TextView  tvChapter , tvTopic , tvRecording;
         DeleteRecordListRecyclerAdapter adapter;
         RelativeLayout cardRelative;

        SubjectViewHolder(final View itemView , DeleteRecordListRecyclerAdapter subjectAdapter)
        {
            super(itemView);

                 adapter = subjectAdapter;
                 tvChapter = itemView.findViewById(R.id.txt_chapter_item);
                 tvTopic = itemView.findViewById(R.id.txt_topic_item);
                 tvRecording = itemView.findViewById(R.id.txt_record_item);
                 cardRelative = itemView.findViewById(R.id.card_delete);

                 itemView.setOnLongClickListener(new View.OnLongClickListener()
                 {
                     @SuppressLint("ResourceAsColor")
                     @Override
                     public boolean onLongClick(View v)
                     {
                         int pos = getAdapterPosition();

                         if(itemView.isSelected())
                         {
                             cardRelative.setBackgroundResource(R.drawable.bg_btn_roundcorner_white);
                             itemView.setSelected(false);

                                 if (DeleteFragment.deleteArray != null) {
                                     DeleteFragment.deleteArray.remove(pos);
                                 }


                         }
                         else{
                             cardRelative.setBackgroundResource(R.drawable.bg_btn_roundcorner_trasparetnorange);
                             itemView.setSelected(true);
                             DeleteFragment.deleteArray.add(pos);
                         }
                         adapter.mListeners.listItemClick(pos);
                         notifyDataSetChanged();
                         return false;
                     }
                 });
        }

    }

    private void removeItem(int position) {
        subjectDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, subjectDataList.size());
    }
}



