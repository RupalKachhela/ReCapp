package recapp.com.recapp.helper;

import android.view.View;
import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


/**
 * Created by zxy on 17/4/23.
 */

public class MyNodeViewFactory extends BaseNodeViewFactory
{

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewBinder(view);
            case 1:
                return new SecondLevelNodeViewBinder(view);
            case 2:
                return new ThrirdLevelNodeViewBinder(view);
            case 3:
                return new FourthLevelNodeViewBinder(view);
            default:
                return null;
        }
    }
}
