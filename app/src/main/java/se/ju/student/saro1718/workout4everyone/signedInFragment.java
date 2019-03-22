package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class signedInFragment extends Fragment{

    private Button signOutButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public signedInFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_is_signed_in, container, false);
        super.onCreate(savedInstanceState);


        ViewPager viewpager = (ViewPager) view.findViewById(R.id.profileViewPager);
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appBarId);

        Adapter adapter = new Adapter(getChildFragmentManager());

        adapter.addFragment(new profileFragment(), getString(R.string.profile));
        adapter.addFragment(new myCreatedWorkoutsFragment(), getString(R.string.myWorkouts));

        viewpager.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.profileTabBar);
        tabLayout.setupWithViewPager(viewpager);

        return view;
    }



    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

