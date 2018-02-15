package go.id.payakumbuh.siwarta;

import android.support.multidex.MultiDexApplication;

import go.id.payakumbuh.siwarta.db.DataMigration;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class App extends MultiDexApplication {

    public static final String HOST_URL = "http://10.0.3.2/siwarta/";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                //versi database
                .schemaVersion(DataMigration.DATABASE_VERSION)
                .migration(new DataMigration())
                .build());
    }
}
