package go.id.payakumbuh.siwarta.db.models;

import java.util.Date;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class Wartawan {

    public int id;
    public int id_media;
    public boolean lock, setuju;
    public Date waktu;
    public String media, nama, alamat, kontak, username, password, email;
}
