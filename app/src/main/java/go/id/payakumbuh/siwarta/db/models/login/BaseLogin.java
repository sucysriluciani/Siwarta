package go.id.payakumbuh.siwarta.db.models.login;

/**
 * Created by anggrayudi on 14/02/18.
 */

public interface BaseLogin {

    String[] TABLE_HAK_AKSES = {
            "tb_user",
            "tb_wartawan",
            "user_admin",
            "user_superadmin"
    };

    int getIcon();
    int getId();
    int getLevel();
    String getNama();
    String getUsername();

    void setId(int id);
    void setLevel(int level);
    void setNama(String nama);
    void setUsername(String username);
}
