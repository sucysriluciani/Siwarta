package go.id.payakumbuh.siwarta.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by anggrayudi on 15/02/18.
 */

public class AdminAccountFragment extends Fragment {

    public static AdminAccountFragment newInstance() {
        Bundle args = new Bundle();
        AdminAccountFragment fragment = new AdminAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
