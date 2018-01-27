package tablayou.com.techno.tablayout;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.support.design.widget.TabLayout;
import android.widget.ImageView;
import android.widget.TextView;

import tablayou.com.techno.tablayout.fragment.TabOneFragment;
import tablayou.com.techno.tablayout.fragment.TabTwoFragment;
import tablayou.com.techno.tablayout.fragment.common.BaseFragment;
import tablayou.com.techno.tablayout.fragment.common.TabThreeFragment;
import tablayou.com.techno.tablayout.utils.FragNavController;


public class MainActivity extends AppCompatActivity implements
        TabLayout.OnTabSelectedListener,
        FragNavController.TransactionListener,
        FragNavController.RootFragmentListener,
        BaseFragment.FragmentNavigation,
        TabOneFragment.OnFragmentInteractionListener,
        TabTwoFragment.OnFragmentInteractionListener,
        TabThreeFragment.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private FrameLayout tabContainer;

    private FragNavController mNavController;
    private final int INDEX_ONE = FragNavController.TAB1;
    private final int INDEX_TWO = FragNavController.TAB2;
    private final int INDEX_THREE = FragNavController.TAB3;

    private String[] mTabsTitle;

    private int[] mTabsIcons = {
            R.drawable.tab_selector,
            R.drawable.tab_selector,
            R.drawable.tab_selector,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            //init views
            initViews();
            tabLayout.addOnTabSelectedListener(this);

            mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.tabContainer)
                    .transactionListener(this)
                    .rootFragmentListener(this, 3)
                    .build();
            mTabsTitle = new String[]{
                    getString(R.string.tab_title_one),
                    getString(R.string.tab_title_two),
                    getString(R.string.tab_title_three)
            };
            //set tabs here
            setTabs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initViews() {
        try {
            tabLayout = findViewById(R.id.tabLayout);
            tabContainer = findViewById(R.id.tabContainer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to set Tabs
    private void setTabs() {
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(0)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(1)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(2)));
        //Default tab selected
        tabLayout.getTabAt(INDEX_ONE).getCustomView().setSelected(true);
    }

    // Get Custom Tab
    public View getTabView(int position) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        if (Build.VERSION.SDK_INT > 23)
            title.setTextColor(getResources().getColorStateList(R.drawable.tab_text_selector, null));
        else
            title.setTextColor(getResources().getColorStateList(R.drawable.tab_text_selector));
        title.setText(mTabsTitle[position]);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageResource(mTabsIcons[position]);
        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mNavController.switchTab(INDEX_ONE);
                break;
            case 1:
                mNavController.switchTab(INDEX_TWO);
                break;
            case 2:
                mNavController.switchTab(INDEX_THREE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {

        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
            Drawable arrowBack = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_arrow_back);
//            if (fragment instanceof SettingsFragment) {
//                ColorFilter filter = new LightingColorFilter(Color.WHITE, Color.WHITE);
//                arrowBack.setColorFilter(filter);
//                getSupportActionBar().setHomeAsUpIndicator(arrowBack);
//            } else {
            ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.BLACK);
            arrowBack.setColorFilter(filter);
            getSupportActionBar().setHomeAsUpIndicator(arrowBack);
//            }


        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_ONE:
                return TabOneFragment.newInstance("", "");
            case INDEX_TWO:
                return TabTwoFragment.newInstance("", "");
            case INDEX_THREE:
                return TabThreeFragment.newInstance("", "");
            default:
                break;
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
