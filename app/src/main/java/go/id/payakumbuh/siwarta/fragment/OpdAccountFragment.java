package go.id.payakumbuh.siwarta.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by anggrayudi on 15/02/18.
 */
public class OpdAccountFragment extends Fragment {

    public static OpdAccountFragment newInstance() {
        Bundle args = new Bundle();
        OpdAccountFragment fragment = new OpdAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
