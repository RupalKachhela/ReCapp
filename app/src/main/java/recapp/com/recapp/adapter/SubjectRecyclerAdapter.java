package recapp.com.recapp.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import recapp.com.recapp.R;
import recapp.com.recapp.interfaces.ItemClickListener;
import recapp.com.recapp.model.SubjectListData;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.SubjectViewHolder>
{

    private WeakReference<Activity> mActivityWeakReference;
    private List<SubjectListData> subjectDataList;
    private ItemClickListener mListeners;


    public SubjectRecyclerAdapter(Activity aActivity, List<SubjectListData> aProductsDataList)
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

        SubjectViewHolder vh;

        if (viewType == R.layout.item_row_subject_list)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_subject_list, parent, false);
                  vh  =   new SubjectViewHolder(view , this,R.layout.item_row_subject_list);

        }else {

            System.out.println("===item change=====");
            View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_last_element, parent, false);
            vh = new SubjectViewHolder(view ,this, R.layout.layout_add_last_element);
        }

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position)
    {

       if (holder.getItemViewType() == R.layout.item_row_subject_list) {
           if (!subjectDataList.isEmpty()) {
               SubjectListData pckdata = subjectDataList.get(position);

               if (pckdata.getShortName() != null && !pckdata.getShortName().isEmpty()) {

                   System.out.println("=====Image one===" + pckdata.getShortName());

                   holder.btnShortName.setText(pckdata.getShortName());
                   holder.txtSubjectName.setText(pckdata.getSubjectName());

               }
           }
       }
        else {

        }
    }


    @Override
    public int getItemCount()
    {
        return subjectDataList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == subjectDataList.size()) ? R.layout.layout_add_last_element : R.layout.item_row_subject_list;
    }

    public void setOnListener(ItemClickListener listener)
    {
        mListeners = listener;
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {

         Button btnShortName;
         TextView txtSubjectName;
         SubjectRecyclerAdapter adapter;
         CardView cardNewSubject;

         RelativeLayout cardSubject;

        SubjectViewHolder(View itemView , SubjectRecyclerAdapter subjectAdapter, int viewType)
        {
            super(itemView);
            adapter = subjectAdapter;

            if (viewType == R.layout.item_row_subject_list)
            {
                btnShortName = itemView.findViewById(R.id.btn_subjectShortName_list);
                txtSubjectName = itemView.findViewById(R.id.txt_subjectName_list);
                cardSubject = itemView.findViewById(R.id.rl_subject);

                cardSubject.setOnClickListener(this);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    itemView.setClipToOutline(true);
                }
            }
            else {
                cardNewSubject = itemView.findViewById(R.id.card_addNewSubject);
                cardNewSubject.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View aView)
        {
            switch (aView.getId())
            {
                case R.id.btn_back:
                {
                    break;
                }

                case R.id.rl_subject:
                {
                    adapter.mListeners.listItemClick(getAdapterPosition() , "SubjectName");
                     break;

                }
                case R.id.card_addNewSubject:
                {
                    adapter.mListeners.listItemClick(getAdapterPosition() ,"AddSubject");
                    break;

                }
            }
        }
    }
}



