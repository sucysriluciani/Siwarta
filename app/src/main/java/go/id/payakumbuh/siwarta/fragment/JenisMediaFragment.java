package go.id.payakumbuh.siwarta.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by anggrayudi on 15/02/18.
 */

public class JenisMediaFragment extends Fragment {

    public static JenisMediaFragment newInstance() {
        Bundle args = new Bundle();
        JenisMediaFragment fragment = new JenisMediaFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
