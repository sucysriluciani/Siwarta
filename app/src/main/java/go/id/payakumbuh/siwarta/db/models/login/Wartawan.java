package go.id.payakumbuh.siwarta.db.models.login;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import go.id.payakumbuh.siwarta.App;
import go.id.payakumbuh.siwarta.R;
import go.id.payakumbuh.siwarta.db.models.object.Media;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static go.id.payakumbuh.siwarta.db.models.login.BaseUser.LEVEL_OPD;
import static go.id.payakumbuh.siwarta.db.models.login.BaseUser.LEVEL_WARTAWAN;
import static go.id.payakumbuh.siwarta.db.models.login.BaseUser.SESSION;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class Wartawan extends RealmObject implements BaseLogin {

    private static final OkHttpClient client = new OkHttpClient();

    @PrimaryKey
    private int id;
    private String nama, username;
    private int level;
    public boolean lock, setuju;
    public Date waktu;
    public String alamat, kontak, email;

    @Ignore
    public int id_media;

    public Media media;

    @Override
    public int getIcon() {
        return R.drawable.ic_person_black_24dp;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public String getNama() {
        return nama;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    static Wartawan fromJSON(JSONObject o) throws JSONException {
        Wartawan wartawan = new Wartawan();
        wartawan.setId(o.getInt("id_user"));
        wartawan.username = o.getString("username");
        wartawan.nama = o.getString("nm_user");
        wartawan.lock = o.getBoolean("lock");
        wartawan.setuju = o.getBoolean("setuju");
        wartawan.alamat = o.getString("alamat");
        wartawan.email = o.getString("email");
        wartawan.kontak = o.getString("kontak");
        wartawan.waktu = new Date(o.getLong("waktu"));
        wartawan.level = LEVEL_WARTAWAN;
        wartawan.media = Media.fromJSON(o.getJSONObject("media"));
        wartawan.id_media = wartawan.media.id;
        return wartawan;
    }

    static void saveSession(Context context, Wartawan wartawan){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        preferences.edit()
                .putInt("id", wartawan.id)
                .putInt("level", wartawan.level)
                .putInt("id_media", wartawan.media.id)
                .putLong("waktu", wartawan.waktu.getTime())
                .putString("nama", wartawan.nama)
                .putString("username", wartawan.username)
                .putBoolean("lock", wartawan.lock)
                .putBoolean("setuju", wartawan.setuju)
                .putString("alamat", wartawan.alamat)
                .putString("email", wartawan.email)
                .putString("kontak", wartawan.kontak)
                .apply();
    }

    static Wartawan getSession(SharedPreferences preferences){
        Wartawan wartawan = new Wartawan();
        wartawan.setId(preferences.getInt("id", 0));
        wartawan.level = preferences.getInt("level", LEVEL_OPD);
        wartawan.id_media = preferences.getInt("id_media", 0);
        wartawan.waktu = new Date(preferences.getLong("waktu", System.currentTimeMillis()));
        wartawan.nama = preferences.getString("nama", null);
        wartawan.username = preferences.getString("username", null);
        wartawan.lock = preferences.getBoolean("lock", false);
        wartawan.setuju = preferences.getBoolean("setuju", false);
        wartawan.alamat = preferences.getString("alamat", null);
        wartawan.email = preferences.getString("email", null);
        wartawan.kontak = preferences.getString("kontak", null);
        return wartawan;
    }

    public static List<Wartawan> getWartawans(){
        String url = App.HOST_URL + "wartawan.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<Wartawan> users = new ArrayList<>();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                JSONArray a = new JSONArray(responseBody.string());
                for (int i = 0; i < a.length(); i++) {
                    users.add(fromJSON(a.getJSONObject(i)));
                }
                return users;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
}
