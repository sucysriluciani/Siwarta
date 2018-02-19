package go.id.payakumbuh.siwarta.db.models.object;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import go.id.payakumbuh.siwarta.App;
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
public class Pejabat extends RealmObject {

    private static final OkHttpClient client = new OkHttpClient();

    @PrimaryKey
    public int id;
    public String nama, nip, jabatan, warna, no_hp, eselon;

    @Ignore
    public int id_opd;

    public Opd opd;

    public static Pejabat fromJSON(JSONObject o) throws JSONException {
        Pejabat pejabat = new Pejabat();
        pejabat.id = o.getInt("id_pejabat");
        pejabat.nip = o.getString("nip");
        pejabat.nama = o.getString("nm_pejabat");
        pejabat.jabatan = o.getString("jabatan");
        pejabat.warna = o.getString("warna");
        pejabat.no_hp = o.getString("no_hp");
        pejabat.eselon = o.getString("eselon");
        if (o.has("opd")) {
            pejabat.opd = Opd.fromJSON(o.getJSONObject("opd"));
            pejabat.id_opd = pejabat.opd.id;
        }
        return pejabat;
    }

    public static List<Pejabat> getPejabat(){
        String url = App.HOST_URL + "pejabat.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<Pejabat> pejabats = new ArrayList<>();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                JSONArray a = new JSONArray(responseBody.string());
                for (int i = 0; i < a.length(); i++) {
                    pejabats.add(fromJSON(a.getJSONObject(i)));
                }
                return pejabats;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pejabats;
    }
}
