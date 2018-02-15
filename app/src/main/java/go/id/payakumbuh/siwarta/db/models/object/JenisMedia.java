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
public class JenisMedia extends RealmObject {

    private static final OkHttpClient client = new OkHttpClient();

    @PrimaryKey
    public int id;
    public String nama;

    /**
     * Mengambil semua jenis media dari database
     */
    public static List<JenisMedia> getJenisMedia(){
        String url = App.HOST_URL + "jenis_media.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<JenisMedia> medias = new ArrayList<>();
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

    static JenisMedia fromJSON(JSONObject o) throws JSONException {
        JenisMedia jenisMedia = new JenisMedia();
        jenisMedia.id = o.getInt("id");
        jenisMedia.nama = o.getString("nama");
        return jenisMedia;
    }
}
