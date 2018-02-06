package go.id.payakumbuh.siwarta.db.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SizeF;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static go.id.payakumbuh.siwarta.App.HOST_URL;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class User implements Parcelable {

    public static final String SESSION = "user_session";

    public static final int LEVEL_ADMIN = 1;
    public static final int LEVEL_OPD = 2;
    public static final int LEVEL_WARTAWAN_OFFLINE = 3;
    public static final int LEVEL_WARTAWAN_ONLINE = 4;

    private static final OkHttpClient client = new OkHttpClient();

    public int id;
    public int id_skpd, level;
    public Date waktu;
    public String nama, username, password, domain;
    public boolean lock;

    private User(){
    }

    public static void logout(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    public static User loginOffline(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        if (preferences.getAll().isEmpty())
            return null;
        User user = new User();
        user.id = preferences.getInt("id", 0);
        user.id_skpd = preferences.getInt("id_skpd", 0);
        user.lock = preferences.getBoolean("lock", false);
        user.nama = preferences.getString("nama", "");
        user.username = preferences.getString("username", "");
        user.domain = preferences.getString("domain", null);
        user.waktu = new Date(preferences.getLong("waktu", System.currentTimeMillis()));
        user.level = preferences.getInt("level", 0);
        return user;
    }

    /**
     * Login pada server Siwarta. Nantinya username dan password akan dimasukkan ke URL menjadi seperti:<br>
     *     <code>https://siwarta.payakumbuhkota.go.id/login.php?username=nicko&password=ajdf8sadf8bw82r72</code>
     * @return <code>true</code> jika login sukses
     */
    public static boolean loginOnline(Context context, String username, String password){
        String baseUrl = HOST_URL + "login.php";
        HashCode hashPassword = Hashing.md5().hashString(password, Charset.defaultCharset());
        HttpUrl urlBuilder = HttpUrl.parse(baseUrl).newBuilder()
                .addQueryParameter("username", username)
                .addQueryParameter("password", hashPassword.toString())
                .build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                saveSession(context, fromJSON(new JSONObject(responseBody.toString())));
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static User fromJSON(JSONObject o) throws JSONException {
        User user = new User();
        user.username = o.getString("username");
        user.domain = o.getString("domain");
        user.nama = o.getString("nama");
        user.id = o.getInt("id");
        user.id_skpd = o.getInt("id_skpd");
        user.lock = o.getBoolean("lock");
        user.waktu = new Date(o.getLong("waktu"));
        user.level = o.getInt("level");
        return user;
    }

    private static void saveSession(Context context, User user){
        SharedPreferences preferences = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        preferences.edit()
                .putInt("id", user.id)
                .putInt("id_skpd", user.id_skpd)
                .putInt("level", user.level)
                .putLong("waktu", user.waktu.getTime())
                .putString("nama", user.nama)
                .putString("username", user.username)
                .putString("domain", user.domain)
                .putBoolean("lock", user.lock)
                .apply();
    }

    private User(Parcel in) {
        id = in.readInt();
        id_skpd = in.readInt();
        level = in.readInt();
        nama = in.readString();
        username = in.readString();
        password = in.readString();
        domain = in.readString();
        lock = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_skpd);
        dest.writeInt(level);
        dest.writeString(nama);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(domain);
        dest.writeByte((byte) (lock ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
