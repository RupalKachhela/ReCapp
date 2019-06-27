package recapp.com.recapp.helper;

import android.view.View;
import android.widget.TextView;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableNodeViewBinder;
import recapp.com.recapp.R;

/**
 * Created by zxy on 17/4/23.
 */

public class FourthLevelNodeViewBinder extends CheckableNodeViewBinder
{
    TextView textView;
    public FourthLevelNodeViewBinder(View itemView)
    {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
    }

    @Override
    public int getCheckableViewId()
    {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_fourth_level;
    }

    @Override
    public void bindView(TreeNode treeNode)
    {
        textView.setText(treeNode.getValue().toString());
    }
}
