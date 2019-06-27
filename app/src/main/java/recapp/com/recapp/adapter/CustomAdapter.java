package recapp.com.recapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;

import recapp.com.recapp.R;

public class CustomAdapter extends BaseExpandableListAdapter
{

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;


    public CustomAdapter(Context context, List<String> aExpandableListTitle, LinkedHashMap<String, List<String>> aExpandableListDetail)
    {
        this.context = context;
        this.expandableListTitle = aExpandableListTitle;
        this.expandableListDetail = aExpandableListDetail;
    }

    @Override
    public Object getChild(int aI, int aI1)
    {
        return this.expandableListDetail.get(this.expandableListTitle.get(aI)).get(aI1);
    }

    @Override
    public View getChildView(int listPosition, int aexpandedListPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final String expandedListText = (String) getChild(listPosition, aexpandedListPosition);
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_items, null);
        }

        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.txt_listitem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int aI)
    {
        return this.expandableListDetail.get(this.expandableListTitle.get(aI)).size();
    }


    @Override
    public Object getGroup(int aI) {

        return this.expandableListTitle.get(aI);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int aI)
    {
        return aI;
    }

    @Override
    public View getGroupView(int listPosition, boolean aB, View convertView, ViewGroup aViewGroup)
    {
        String listTitle = (String) getGroup(listPosition);
      
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_items, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.txt_group);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public long getChildId(int aI, int aI1) {

        return aI1;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int aI, int aI1)
    {
        return true;
    }


}


