package go.id.payakumbuh.siwarta.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.util.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.id.payakumbuh.siwarta.R;

/**
 * Created by anggrayudi on 15/02/18.
 */
public class OpdFragment extends Fragment {

    public static OpdFragment newInstance() {
        Bundle args = new Bundle();
        OpdFragment fragment = new OpdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }
}
