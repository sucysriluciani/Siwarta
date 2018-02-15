package go.id.payakumbuh.siwarta.util;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by anggrayudi on 06/02/18.
 */
public final class ViewUtils {

    public static Drawable setDrawableTint(Drawable drawable, int tint){
        drawable.setColorFilter(tint, PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }
}
