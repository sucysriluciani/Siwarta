package go.id.payakumbuh.siwarta.db.models.login;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import go.id.payakumbuh.siwarta.App;
import go.id.payakumbuh.siwarta.R;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by anggrayudi on 07/02/18.
 */
public class BaseUser extends RealmObject implements BaseLogin {

    private static final String TAG = "BaseUser";

    static final String SESSION = "user_session";

    public static final int LEVEL_SUPERADMIN = 1;
    public static final int LEVEL_ADMIN = 2;
    public static final int LEVEL_WARTAWAN = 3;
    public static final int LEVEL_OPD = 4;

    private static final OkHttpClient client = new OkHttpClient();

    @PrimaryKey
    private int id;
    private String username;
    private int level;
    private String nama;

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

    /**
     * Login pada server Siwarta. Nantinya username dan password akan dimasukkan ke URL menjadi seperti:<br>
     *     <code>http://localhost/siwarta/login.php?username=dodisyahputra&password=fdb7df4d0887a55bcf80a229d548487a</code>
     * @return <code>true</code> jika login sukses
     */
    public static boolean loginOnline(Context context, String username, String password, String table) {
        String baseUrl = App.HOST_URL + "login.php";
//        HashCode hashPassword = Hashing.md5().hashString(password, Charset.defaultCharset());
        HttpUrl urlBuilder = HttpUrl.parse(baseUrl).newBuilder()
                .addQueryParameter("username", username)
                .addQueryParameter("password", password)
                .addQueryParameter("table", table)
                .build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                JSONObject o = new JSONObject(responseBody.string());
                switch (o.getInt("level")) {
                    case LEVEL_SUPERADMIN:
                    case LEVEL_ADMIN:
                        saveSession(context, fromJSON(o));
                        break;
                    case LEVEL_WARTAWAN:
                        Wartawan.saveSession(context, Wartawan.fromJSON(o));
                        break;
                    default:
                        UserOpd.saveSession(context, UserOpd.fromJSON(o));
                        break;
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void saveSession(Context context, BaseUser user){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        preferences.edit()
                .putInt("id", user.getId())
                .putInt("level", user.level)
                .putString("username", user.username)
                .putString("nama", user.nama)
                .apply();
    }

    private static BaseUser getSession(SharedPreferences preferences){
        BaseUser user = new BaseUser();
        user.id = preferences.getInt("id", 0);
        user.level = preferences.getInt("level", LEVEL_OPD);
        user.username = preferences.getString("username", null);
        user.nama = preferences.getString("nama", null);
        return user;
    }

    private static BaseUser fromJSON(JSONObject o) throws JSONException {
        BaseUser user = new BaseUser();
        user.setId(o.has("id_admin") ? o.getInt("id_admin") : o.getInt("id_superadmin"));
        user.username = o.getString("username");
        user.nama = o.getString(o.has("nama_admin") ? "nama_admin" : "nama_superadmin");
        user.level = o.getInt("level");
        return user;
    }

    @SuppressWarnings("unchecked")
    public static BaseLogin getUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        switch (preferences.getInt("level", 0)){
            case LEVEL_SUPERADMIN:
            case LEVEL_ADMIN:
                return getSession(preferences);
            case LEVEL_WARTAWAN:
                return Wartawan.getSession(preferences);
            case LEVEL_OPD:
                return UserOpd.getSession(preferences);
            default:
                return null;
        }
    }

    public static void logout(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    public static int getLevel(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        return preferences.getInt("level", LEVEL_OPD);
    }

    /**
     * Mengambil semua Admin (bukan Superadmin) dari server
     */
    public static List<BaseUser> getAdmins(){
        String url = App.HOST_URL + "admin.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<BaseUser> users = new ArrayList<>();
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
