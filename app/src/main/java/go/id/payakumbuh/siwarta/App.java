package go.id.payakumbuh.siwarta;

import android.support.multidex.MultiDexApplication;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class App extends MultiDexApplication {

    public static final String BASE_TABLE_URL = "https://siwarta-b5e5c.firebaseio.com/siwarta-b5e5c/";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
