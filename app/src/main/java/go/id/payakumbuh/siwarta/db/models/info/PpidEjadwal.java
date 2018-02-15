package go.id.payakumbuh.siwarta.db.models.info;

import java.util.Date;

import go.id.payakumbuh.siwarta.db.models.login.UserOpd;
import go.id.payakumbuh.siwarta.db.models.object.Opd;
import go.id.payakumbuh.siwarta.db.models.object.Pejabat;
import go.id.payakumbuh.siwarta.db.models.server.PpidStat;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anggrayudi on 25/01/18.
 * RAGU
 */
public class PpidEjadwal extends RealmObject {

    @PrimaryKey
    public int id;
    public Date time, tgl_akhir, updatenya, entri;
    public String lokasi, instansi, kontak, judul_acara;

    public Opd opd;
    public Pejabat pejabat;
    public UserOpd userOpd;
    public PpidStat ppidStat;
}
