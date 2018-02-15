package go.id.payakumbuh.siwarta.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by anggrayudi on 15/02/18.
 */

public class MediaFragment extends Fragment {

    public static MediaFragment newInstance() {
        Bundle args = new Bundle();
        MediaFragment fragment = new MediaFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
