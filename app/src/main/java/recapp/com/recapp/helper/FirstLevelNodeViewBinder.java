package recapp.com.recapp.helper;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableNodeViewBinder;
import recapp.com.recapp.R;

/**
 * Created by zxy on 17/4/23.
 */

public class FirstLevelNodeViewBinder extends CheckableNodeViewBinder
{
    TextView textView;
    ImageView imageView;

    public FirstLevelNodeViewBinder(View itemView)
    {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
        imageView = (ImageView) itemView.findViewById(R.id.arrow_img);

    }

    @Override
    public int getCheckableViewId()
    {
        return R.id.checkBox;
    }


    @Override
    public int getLayoutId()
    {
        return R.layout.item_first_level;
    }

    @Override
    public void bindView(final TreeNode treeNode)
    {
        textView.setText(treeNode.getValue().toString());
        imageView.setImageResource(treeNode.isExpanded() ? R.drawable.rc_minus : R.drawable.rc_plus);
      //  imageView.setRotation(treeNode.isExpanded() ? 90 : 0);
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand)
    {
       /* if (expand) {
            imageView.animate().rotation(90).setDuration(200).start();
        } else {
            imageView.animate().rotation(0).setDuration(200).start();
        }*/

        if (expand) {
            imageView.setImageResource(R.drawable.rc_minus);
        } else {
            imageView.setImageResource(R.drawable.rc_plus);
        }
    }


}
