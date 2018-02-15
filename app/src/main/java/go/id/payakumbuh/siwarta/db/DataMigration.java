package go.id.payakumbuh.siwarta.db;

import android.support.annotation.NonNull;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class DataMigration implements RealmMigration {

    public static final int DATABASE_VERSION = 0;

    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {

    }
}
