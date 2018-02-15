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
public class Media extends RealmObject {

    private static final String TAG = "Media";
    private static final OkHttpClient client = new OkHttpClient();

    @PrimaryKey
    public int id;
    public String nama, alamat;

    public JenisMedia jenisMedia;

    public static Media fromJSON(JSONObject o) throws JSONException {
        Media media = new Media();
        media.id = o.getInt("id");
        media.nama = o.getString("nama");
        media.alamat = o.getString("alamat");
        media.jenisMedia = JenisMedia.fromJSON(o.getJSONObject("jenis_media"));
        return media;
    }

    /**
     * Mengambil satu {@link Media} dari database. URL yang diminta seperti:<br>
     *     {@code http://siwarta.payakumbuhkota.go.id/getMedia.php?id=45}
     */
    public static Media getMedia(int id){
        String url = App.HOST_URL + "media.php";
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
     * Mengambil semua media dari database
     */
    public static List<Media> getMedia(){
        String url = App.HOST_URL + "media.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<Media> medias = new ArrayList<>();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                JSONArray a = new JSONArray(responseBody.string());
                for (int i = 0; i < a.length(); i++) {
                    medias.add(fromJSON(a.getJSONObject(i)));
                }
                return medias;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return medias;
    }
}
