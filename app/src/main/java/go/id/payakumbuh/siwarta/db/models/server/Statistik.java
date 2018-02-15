package go.id.payakumbuh.siwarta.db.models.server;

import java.util.Date;

import io.realm.annotations.PrimaryKey;

/**
 * Created by anggrayudi on 25/01/18.
 */

public class Statistik {

    @PrimaryKey
    public int id;
    public int id_user;
    public Date date_created;
    public String ip, location, domain;
}
