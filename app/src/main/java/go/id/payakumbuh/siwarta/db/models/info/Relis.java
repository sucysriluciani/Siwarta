package go.id.payakumbuh.siwarta.db.models.info;

import java.util.Date;
import java.util.Set;

import go.id.payakumbuh.siwarta.db.models.login.UserOpd;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class Relis extends RealmObject {

    @PrimaryKey
    public int id;
    public Date tgl_kegiatan, tgl_entri;
    public String nama_kegiatan, dihadiri, rangkuman, kontak;
    public boolean lock;
    public RealmList<Integer> dists;
    public RealmList<String> files;

    @Ignore
    public int id_user_opd;

    public UserOpd userOpd;
}
