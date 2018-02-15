package go.id.payakumbuh.siwarta.db.models.server;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anggrayudi on 25/01/18.
 */

public class PpidStat extends RealmObject {

    @PrimaryKey
    public int id;
    public String stats, color;
}
