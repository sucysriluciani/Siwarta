package go.id.payakumbuh.siwarta.fragment;

import android.support.v7.view.ActionMode;
import android.view.Menu;

/**
 * Created by anggrayudi on 19/02/18.
 */

public interface ActionModeActivityCallback {

    void onCreateActionMode(ActionMode mode, Menu menu);

    void onDestroyActionMode(ActionMode mode);
}
