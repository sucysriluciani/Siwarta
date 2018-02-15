package go.id.payakumbuh.siwarta.db.models.object;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import go.id.payakumbuh.siwarta.App;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by anggrayudi on 25/01/18.
 */

public class Opd extends RealmObject {

    private static final OkHttpClient client = new OkHttpClient();

    @PrimaryKey
    public int id;
    public String nama, alamat, kontak;

    public static Opd fromJSON(JSONObject o) throws JSONException {
        Opd opd = new Opd();
        opd.id = o.getInt("id");
        opd.nama = o.getString("nama");
        opd.alamat = o.getString("alamat");
        opd.kontak = o.getString("kontak");
        return opd;
    }

    /**
     * Mengambil satu {@link Opd} dari database. URL yang diminta seperti:<br>
     *     {@code http://siwarta.payakumbuhkota.go.id/getMedia.php?id=45}
     */
    public static Opd getOpd(int id){
        String url = App.HOST_URL + "opd.php";
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
     * Mengambil semua OPD dari database
     */
    public static List<Opd> getOpd(){
        String url = App.HOST_URL + "opd.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<Opd> opds = new ArrayList<>();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                JSONArray a = new JSONArray(responseBody.string());
                for (int i = 0; i < a.length(); i++) {
                    opds.add(fromJSON(a.getJSONObject(i)));
                }
                return opds;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return opds;
    }
}
