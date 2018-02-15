package go.id.payakumbuh.siwarta.db.models.server;

import io.realm.annotations.PrimaryKey;

/**
 * Created by anggrayudi on 25/01/18.
 */

public class SetupSistem {

    @PrimaryKey
    public int id;
    public String nama, nilai;
}
