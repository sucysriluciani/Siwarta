package go.id.payakumbuh.siwarta.db.models.login;

/**
 * Created by anggrayudi on 14/02/18.
 */

public interface BaseLogin {

    int getLevel();
    String getNama();
    String getUsername();

    void setLevel(int level);
    void setNama(String nama);
    void setUsername(String username);
}
