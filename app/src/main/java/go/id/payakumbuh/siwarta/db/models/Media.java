package go.id.payakumbuh.siwarta.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import go.id.payakumbuh.siwarta.App;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by anggrayudi on 25/01/18.
 */
public class Media implements Parcelable {

    private static final OkHttpClient client = new OkHttpClient();

    public int id_media;
    public int id_jn;
    public String nm_media, alamat;

    private Media(){
    }

    /**
     * Mengambil satu {@link Media} dari database. URL yang diminta seperti:<br>
     *     {@code http://siwarta.payakumbuhkota.go.id/getMedia.php?id=45}
     */
    public static Media getMedia(int id){
        String url = App.HOST_URL + "getMedia.php";
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
                JSONArray a = new JSONArray(responseBody.toString());
                List<Media> medias = new ArrayList<>(a.length());
                for (int i = 0; i < a.length(); i++) {
                    medias.add(fromJSON(a.getJSONObject(i)));
                }
                if (!medias.isEmpty()){
                    return medias.get(0);
                }
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
        String url = App.HOST_URL + "getMedia.php";
        HttpUrl urlBuilder = HttpUrl.parse(url).newBuilder().build();
        Request request = new Request.Builder()
                .url(urlBuilder.url())
                .build();
        List<Media> medias = new ArrayList<>();
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null && response.isSuccessful()) {
                JSONArray a = new JSONArray(responseBody.toString());
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

    private static Media fromJSON(JSONObject o) throws JSONException {
        Media media = new Media();
        media.id_media = o.getInt("id_media");
        media.id_jn = o.getInt("id_jn");
        media.nm_media = o.getString("nm_media");
        media.alamat = o.getString("alamar");
        return media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_media);
        dest.writeInt(id_jn);
        dest.writeString(nm_media);
        dest.writeString(alamat);
    }

    private Media(Parcel in) {
        id_media = in.readInt();
        id_jn = in.readInt();
        nm_media = in.readString();
        alamat = in.readString();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
