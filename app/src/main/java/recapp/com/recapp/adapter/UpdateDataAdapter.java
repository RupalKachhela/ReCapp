package recapp.com.recapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import recapp.com.recapp.R;
import recapp.com.recapp.model.DeleteRecordListData;

/**
 * Created by javierg on 30/10/2017.
 */

public class UpdateDataAdapter extends RecyclerView
        .Adapter<UpdateDataAdapter
        .DataObjectHolder>
{

    private ArrayList<DeleteRecordListData> mDataSet;
    private static Context sContext;
    private static int sPosition;
    public static SparseBooleanArray sSelectedItems;
    private static UpdateDataClickListener sClickListener;
    private static final int MULTIPLE = 0;
    private static final int SINGLE = 1;
    private static int sModo = 0;

    static class DataObjectHolder extends RecyclerView.ViewHolder  implements View
            .OnClickListener
    {
//        TextView mLabel;
//        TextView mDateTime;
//        LinearLayout mBackground;
        TextView  tvChapter , tvTopic , tvRecording;
        DeleteRecordListRecyclerAdapter adapter;
        RelativeLayout cardRelative;

        DataObjectHolder(View itemView)
        {
            super(itemView);

//            mLabel = (TextView) itemView.findViewById(R.id.vertical_list_item_title);
//            mDateTime = (TextView) itemView.findViewById(R.id.vertical_list_item_subtitle);
//            mBackground = (LinearLayout) itemView.findViewById(R.id.vertical_list_item_background);

            tvChapter = itemView.findViewById(R.id.txt_chapter_item);
            tvTopic = itemView.findViewById(R.id.txt_topic_item);
            tvRecording = itemView.findViewById(R.id.txt_record_item);
            cardRelative = itemView.findViewById(R.id.card_delete);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            if (sSelectedItems.get(getAdapterPosition(), false))
            {
                sSelectedItems.delete(getAdapterPosition());
                cardRelative.setSelected(false);
                cardRelative.setBackgroundResource(R.drawable.bg_btn_roundcorner_trasparetnorange);

            } else
            {
                switch (sModo)
                {
                    case SINGLE:
                        sSelectedItems.put(sPosition, false);
                        break;
                    case MULTIPLE:
                    default:
                        break;
                }

                cardRelative.setBackgroundResource(R.drawable.bg_btn_roundcorner_green);
                sSelectedItems.put(getAdapterPosition(), true);
                cardRelative.setSelected(true);
            }
            sClickListener.onItemClick(getAdapterPosition());
        }
    }

   public void setOnemClickListener(UpdateDataClickListener clickListener) {
        sClickListener = clickListener;
    }

    public UpdateDataAdapter(ArrayList<DeleteRecordListData> myDataset, Context context, int modo) {
        mDataSet = myDataset;
        sContext = context;
        sSelectedItems = new SparseBooleanArray();
        sModo = modo;
    }

    @Override
    public UpdateDataAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_delete, parent, false);

        UpdateDataAdapter.DataObjectHolder dataObjectHolder = new UpdateDataAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(UpdateDataAdapter.DataObjectHolder holder, int position) {
        holder.tvChapter.setText(mDataSet.get(position).getChapterName());
        holder.tvTopic.setText(mDataSet.get(position).getTopicName());
        holder.tvRecording.setText(mDataSet.get(position).getFileName());

        if (sSelectedItems.get(position))
        {
           holder.cardRelative.setBackgroundResource(R.drawable.bg_btn_roundcorner_trasparetnorange);

        } else {
            holder.cardRelative.setBackgroundResource(R.drawable.bg_btn_roundcorner_white);
        }
        holder.cardRelative.setSelected(sSelectedItems.get(position, false));

    }

    @Override
    public int getItemCount()
    {
        return mDataSet.size();
    }

    public void selected(int position)
    {
        switch (sModo)
        {
            case SINGLE:
                sPosition = position;
                notifyDataSetChanged();
                break;
            case MULTIPLE:
                sPosition = position;
                notifyDataSetChanged();
            default:
                break;
        }
    }

    public void changeMode(int modo) {
        sModo = modo;
        sSelectedItems.clear();
        notifyDataSetChanged();
    }

   public interface UpdateDataClickListener {
        void onItemClick(int position);
    }

}
