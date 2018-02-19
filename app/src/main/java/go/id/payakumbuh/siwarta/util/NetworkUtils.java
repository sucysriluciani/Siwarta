package go.id.payakumbuh.siwarta.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import go.id.payakumbuh.siwarta.db.models.login.BaseLogin;
import go.id.payakumbuh.siwarta.db.models.login.BaseUser;
import go.id.payakumbuh.siwarta.db.models.login.UserOpd;
import go.id.payakumbuh.siwarta.db.models.login.Wartawan;
import go.id.payakumbuh.siwarta.db.models.object.JenisMedia;
import go.id.payakumbuh.siwarta.db.models.object.Media;
import go.id.payakumbuh.siwarta.db.models.object.Opd;
import go.id.payakumbuh.siwarta.db.models.object.Pejabat;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by anggrayudi on 06/02/18.
 */
public final class NetworkUtils {

    public interface OnUpdateDatabaseFinished{
        void onFinished();
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.getState() == NetworkInfo.State.CONNECTED;
    }

    public static void refreshDatabaseMainObjects(final BaseLogin login, final OnUpdateDatabaseFinished listener){
        final int level = login.getLevel();
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                switch (level){
                    case BaseUser.LEVEL_SUPERADMIN:
                        RealmList<BaseUser> users = new RealmList<>();
                        users.addAll(BaseUser.getAdmins());
                        realm.insertOrUpdate(users);

                    case BaseUser.LEVEL_ADMIN:
                        RealmList<JenisMedia> jenisMedia = new RealmList<>();
                        jenisMedia.addAll(JenisMedia.getJenisMedia());
                        realm.insertOrUpdate(jenisMedia);

                        RealmList<Media> medias = new RealmList<>();
                        medias.addAll(Media.getMedia());
                        realm.insertOrUpdate(medias);

                        RealmList<Opd> opds = new RealmList<>();
                        opds.addAll(Opd.getOpd());
                        realm.insertOrUpdate(opds);

                        RealmList<Pejabat> pejabats = new RealmList<>();
                        pejabats.addAll(Pejabat.getPejabat());
                        realm.insertOrUpdate(pejabats);

                        RealmList<UserOpd> userOpds = new RealmList<>();
                        userOpds.addAll(UserOpd.getUserOpds());
                        realm.insertOrUpdate(userOpds);
                        System.out.println(userOpds.size());

                        RealmList<Wartawan> wartawans = new RealmList<>();
                        wartawans.addAll(Wartawan.getWartawans());
                        realm.insertOrUpdate(wartawans);
                        break;

                    case BaseUser.LEVEL_WARTAWAN:
                        break;

                    default:
                        break;
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
                if (listener != null)
                    listener.onFinished();
            }
        });
    }
}
