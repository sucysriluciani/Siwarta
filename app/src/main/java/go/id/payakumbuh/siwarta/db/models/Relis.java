package go.id.payakumbuh.siwarta.db.models;

import java.util.Date;
import java.util.Set;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class Relis {

    public int id;
    public int id_user;
    public Date tgl_kegiatan, tgl_entri;
    public String nama_kegiatan, dihadiri, rangkuman, kontak;
    public boolean lock;
    public Set<Integer> dists;
    public Set<String> files;
}
