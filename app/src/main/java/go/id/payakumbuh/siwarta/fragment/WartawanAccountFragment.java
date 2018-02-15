package go.id.payakumbuh.siwarta.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by anggrayudi on 15/02/18.
 */

public class WartawanAccountFragment extends Fragment {

    public static WartawanAccountFragment newInstance() {
        Bundle args = new Bundle();
        WartawanAccountFragment fragment = new WartawanAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
