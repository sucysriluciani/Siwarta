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
import go.id.payakumbuh.siwarta.db.models.object.Opd;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static go.id.payakumbuh.siwarta.db.models.login.BaseUser.LEVEL_OPD;
import static go.id.payakumbuh.siwarta.db.models.login.BaseUser.SESSION;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class UserOpd extends RealmObject implements BaseLogin {

    private static final OkHttpClient client = new OkHttpClient();

    @PrimaryKey
    private String username;
    private String nama;
    private int level;
    public Date waktu;
    public String domain;
    public boolean lock;

    public Opd opd;

    @Ignore
    public int id_opd;

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

    static UserOpd fromJSON(JSONObject o) throws JSONException {
        UserOpd userOpd = new UserOpd();
        userOpd.username = o.getString("username");
        userOpd.nama = o.getString("nama");
        userOpd.level = LEVEL_OPD;
        userOpd.domain = o.getString("domain");
        userOpd.lock = o.getBoolean("lock");
        userOpd.waktu = new Date(o.getLong("waktu"));
        userOpd.opd = Opd.fromJSON(o.getJSONObject("opd"));
        return userOpd;
    }

    static void saveSession(Context context, UserOpd userOpd){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        preferences.edit()
                .putInt("id_opd", userOpd.opd.id)
                .putInt("level", userOpd.level)
                .putLong("waktu", userOpd.waktu.getTime())
                .putString("nama", userOpd.nama)
                .putString("username", userOpd.username)
                .putString("domain", userOpd.domain)
                .putBoolean("lock", userOpd.lock)
                .apply();
    }

    static UserOpd getSession(SharedPreferences preferences){
        UserOpd opd = new UserOpd();
        opd.level = preferences.getInt("level", LEVEL_OPD);
        opd.waktu = new Date(preferences.getLong("waktu", System.currentTimeMillis()));
        opd.nama = preferences.getString("nama", null);
        opd.username = preferences.getString("username", null);
        opd.domain = preferences.getString("domain", null);
        opd.lock = preferences.getBoolean("lock", true);
        opd.id_opd = preferences.getInt("id_opd", 0);
        return opd;
    }

    public static List<UserOpd> getUserOpds(){
        String url = App.HOST_URL + "user_opd.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<UserOpd> users = new ArrayList<>();
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
