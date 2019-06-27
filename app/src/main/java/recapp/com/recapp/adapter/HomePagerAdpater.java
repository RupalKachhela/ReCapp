package recapp.com.recapp.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;

import recapp.com.recapp.fragment.HomeFragment;

public class HomePagerAdpater extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public HomePagerAdpater(FragmentManager manager)
    {
        super(manager);
    }

    @Override
    public Fragment getItem(int position)
    {


        for (int i =0 ; i < HomeFragment.fragmentList.size(); i++)
        {
            if (position == i)
            {
                return  HomeFragment.fragmentList.get(i);

            }
        }
        return null;

       /* switch (position)
        {
            case 0:

                return new SchoolFragment();

            case 1:
                return new AddNewSubjectFragment();
                //return null;
            case 2:
                return new TutionFragment();

            default:
                return null;

        }*/
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public Parcelable saveState()
    {
        return null;
    }

    public String addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        return title;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
