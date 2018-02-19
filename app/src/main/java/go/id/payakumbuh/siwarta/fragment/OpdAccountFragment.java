package go.id.payakumbuh.siwarta.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.id.payakumbuh.siwarta.R;
import go.id.payakumbuh.siwarta.activity.MainActivity;
import go.id.payakumbuh.siwarta.activity.OpdAccountActivityDetail;
import go.id.payakumbuh.siwarta.adapter.BaseListAdapter;
import go.id.payakumbuh.siwarta.db.models.login.UserOpd;
import go.id.payakumbuh.siwarta.model.Model;
import go.id.payakumbuh.siwarta.util.NetworkUtils;
import go.id.payakumbuh.siwarta.util.ViewUtils;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by anggrayudi on 15/02/18.
 */
public class OpdAccountFragment extends Fragment implements
        FragmentActionImpl, OrderedRealmCollectionChangeListener<RealmResults<UserOpd>>,
        RecyclerView.OnItemTouchListener, GestureDetector.OnGestureListener, ActionMode.Callback {

    public static OpdAccountFragment newInstance() {
        Bundle args = new Bundle();
        OpdAccountFragment fragment = new OpdAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(android.R.id.list) RecyclerView recyclerView;
    @BindView(R.id.txt_empty) TextView txtEmpty;

    private Realm realm;
    private BaseListAdapter adapter;
    private RealmResults<UserOpd> userOpds;
    private GestureDetectorCompat detectorCompat;
    private ActionMode actionMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_recycler_view, container, false);
        ButterKnife.bind(this, view);
        ViewUtils.setupRecyclerView(recyclerView, this);
        detectorCompat = new GestureDetectorCompat(getContext(), this);
        realm = Realm.getDefaultInstance();
        userOpds = realm.where(UserOpd.class).sort("nama").findAllAsync();
        userOpds.addChangeListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userOpds.removeChangeListener(this);
        realm.close();
    }

    @Override
    public void delete() {
    }

    @Override
    public void selectAll() {

    }

    @Override
    public void sort(boolean descending) {
        userOpds.removeChangeListener(this);
        userOpds = realm.where(UserOpd.class)
                .sort("nama", descending ? Sort.DESCENDING : Sort.ASCENDING)
                .findAllAsync();
        userOpds.addChangeListener(this);
    }

    @Override
    public void onChange(@NonNull RealmResults<UserOpd> userOpds, @Nullable OrderedCollectionChangeSet changeSet) {
        List<Model> models = new ArrayList<>(userOpds.size());
        for (UserOpd opd : userOpds)
            models.add(new Model(opd.getId(), opd.getNama(), opd.getUsername()));

        if (changeSet == null){
            adapter = new BaseListAdapter(recyclerView, models);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.clearList();
            adapter.addAll(models);
            adapter.notifyDataSetChanged();
        }
        txtEmpty.setVisibility(userOpds.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        detectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        int position = recyclerView.getChildAdapterPosition(recyclerView.findChildViewUnder(e.getX(), e.getY()));
        if (position < 0 || adapter.getItemCount() < 1) return false;

        if (actionMode != null){
            adapter.toggleSelection(position);
            actionMode.setTitle(getString(R.string.selected_count, adapter.getSelectedItemCount()));
        } else {
            Model model = adapter.getValueAt(position);
            startActivity(new Intent(getContext(), OpdAccountActivityDetail.class).putExtra("id", model.id));
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        int position = recyclerView.getChildAdapterPosition(recyclerView.findChildViewUnder(e.getX(), e.getY()));
        if (position >= 0){
            actionMode = ((MainActivity) getActivity()).startSupportActionMode(this);
            onSingleTapUp(e);
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.am_base, menu);
        ((ActionModeActivityCallback) getActivity()).onCreateActionMode(mode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_select_all:
                adapter.selectAll();
                break;
            case R.id.action_delete:
                showDeleteDialog();
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        ((ActionModeActivityCallback) getActivity()).onDestroyActionMode(mode);
        actionMode = null;
        adapter.clearSelections();
    }

    private void showDeleteDialog(){
        new MaterialDialog.Builder(getContext())
                .content(R.string.content_delete, adapter.getSelectedItemCount())
                .positiveText(R.string.delete)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (NetworkUtils.isNetworkAvailable(getContext()))
                            Toast.makeText(getContext(), R.string.no_network, Toast.LENGTH_SHORT).show();
                        else {

                        }
                    }
                }).show();
    }
}
