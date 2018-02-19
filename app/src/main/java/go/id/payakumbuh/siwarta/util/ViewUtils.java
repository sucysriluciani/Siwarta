package go.id.payakumbuh.siwarta.util;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by anggrayudi on 06/02/18.
 */
public final class ViewUtils {

    public static Drawable setDrawableTint(Drawable drawable, int tint){
        drawable.setColorFilter(tint, PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    public static void setupRecyclerView(RecyclerView recyclerView, RecyclerView.OnItemTouchListener touchListener){
        if (touchListener != null) recyclerView.addOnItemTouchListener(touchListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL));
    }
}
