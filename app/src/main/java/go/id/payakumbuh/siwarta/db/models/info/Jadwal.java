package go.id.payakumbuh.siwarta.db.models.info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import go.id.payakumbuh.siwarta.App;
import go.id.payakumbuh.siwarta.db.models.object.Opd;
import go.id.payakumbuh.siwarta.db.models.object.Pejabat;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by anggrayudi on 25/01/18.
 */

public class Jadwal extends RealmObject {

    private static final OkHttpClient client = new OkHttpClient();

    @PrimaryKey
    public int id;
    public String kegiatan, tempat, kontak;
    public Date tgl_kegiatan;

    @Ignore
    public int id_opd;
    @Ignore
    public int id_pejabat;

    public Opd opd;
    public Pejabat pejabat;

    static Jadwal fromJSON(JSONObject o) throws JSONException {
        Jadwal jadwal = new Jadwal();
        jadwal.id = o.getInt("id_jadwal");
        jadwal.kegiatan = o.getString("kegiatan");
        jadwal.tempat = o.getString("tempat");
        jadwal.kontak = o.getString("kontak");
        jadwal.tgl_kegiatan = new Date(o.getLong("tgl_kegiatan"));
        jadwal.opd = Opd.fromJSON(o.getJSONObject("opd"));
        jadwal.pejabat = Pejabat.fromJSON(o.getJSONObject("pejabat"));
        jadwal.id_opd = jadwal.opd.id;
        jadwal.id_pejabat = jadwal.pejabat.id;
        return jadwal;
    }

    /**
     * Mengambil satu {@link Jadwal} dari database.
     */
    public static Jadwal getJadwal(int id){
        String url = App.HOST_URL + "jadwal.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("id", id + "")
                .build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                return fromJSON(new JSONObject(responseBody.string()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil semua Jadwal dari database
     */
    public static List<Jadwal> getJadwal(){
        String url = App.HOST_URL + "jadwal.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<Jadwal> jadwals = new ArrayList<>();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                JSONArray a = new JSONArray(responseBody.string());
                for (int i = 0; i < a.length(); i++) {
                    jadwals.add(fromJSON(a.getJSONObject(i)));
                }
                return jadwals;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jadwals;
    }
}
