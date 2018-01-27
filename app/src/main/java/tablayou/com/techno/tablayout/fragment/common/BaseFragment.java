package tablayou.com.techno.tablayout.fragment.common;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Button;

/**
 * Created by arbaz on 21/8/17.
 */

public class BaseFragment extends Fragment implements

        ActivityCompat.OnRequestPermissionsResultCallback {

    Button mButton;
    public FragmentNavigation mFragmentNavigation;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        public void pushFragment(Fragment fragment);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();



    }



}

