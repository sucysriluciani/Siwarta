package go.id.payakumbuh.siwarta.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.util.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.id.payakumbuh.siwarta.R;
import go.id.payakumbuh.siwarta.db.models.login.BaseLogin;
import go.id.payakumbuh.siwarta.db.models.login.BaseUser;
import go.id.payakumbuh.siwarta.util.NetworkUtils;

/**
 * Created by anggrayudi on 15/02/18.
 */
public class BaseListFragment extends Fragment {

    @BindView(android.R.id.list) ListView listView;
    @BindView(R.id.txt_empty) TextView txtEmpty;

    protected BaseLogin user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_list_view, container, false);
        ButterKnife.bind(this, view);
        user = BaseUser.getUser(getContext());
        return view;
    }
}
