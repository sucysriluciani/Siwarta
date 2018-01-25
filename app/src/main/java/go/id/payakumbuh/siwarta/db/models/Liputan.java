package go.id.payakumbuh.siwarta.db.models;

import java.util.Date;
import java.util.Set;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class Liputan {

    public int id_relis;
    public Date tgl_kegiatan;
    public String nama, lokasi;
    public Set<Integer> teams;
}
