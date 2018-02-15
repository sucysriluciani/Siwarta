package go.id.payakumbuh.siwarta.db.models.info;

import java.util.Date;
import java.util.Set;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class Liputan extends RealmObject {

    @PrimaryKey
    public int id;
    public Date tgl_kegiatan;
    public String nama, lokasi;
    public Set<Integer> teams;

    public Relis relis;
}
