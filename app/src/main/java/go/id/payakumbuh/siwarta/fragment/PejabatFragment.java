package go.id.payakumbuh.siwarta.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by anggrayudi on 15/02/18.
 */

public class PejabatFragment extends Fragment {

    public static PejabatFragment newInstance() {
        Bundle args = new Bundle();
        PejabatFragment fragment = new PejabatFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
