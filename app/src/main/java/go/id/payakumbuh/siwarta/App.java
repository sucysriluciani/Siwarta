package go.id.payakumbuh.siwarta;

import android.support.multidex.MultiDexApplication;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class App extends MultiDexApplication {

    public static final String HOST_URL = "http://siwarta.payakumbuhkota.go.id/";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
