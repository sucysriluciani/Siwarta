package go.id.payakumbuh.siwarta.db.models;

import java.util.Date;

/**
 * Created by anggrayudi on 25/01/18.
 */

public class Jadwal {

    public int id;
    public int id_skpd, id_pejabat;
    public String kegiatan, tempat, kontak;
    public Date tgl_kegiatan;
}
