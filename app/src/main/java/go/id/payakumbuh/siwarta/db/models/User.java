package go.id.payakumbuh.siwarta.db.models;

import java.util.Date;

/**
 * Created by anggrayudi on 25/01/18.
 */

public class User {

    public int id;
    public int id_skpd;
    public Date waktu;
    public String nama, username, password, domain;
    public boolean lock;
}
