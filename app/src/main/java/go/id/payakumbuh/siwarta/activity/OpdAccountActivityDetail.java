package go.id.payakumbuh.siwarta.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import go.id.payakumbuh.siwarta.R;
import go.id.payakumbuh.siwarta.db.models.login.UserOpd;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by anggrayudi on 19/02/18.
 */
public class OpdAccountActivityDetail extends AppCompatActivity implements
        RealmChangeListener<UserOpd> {

    private Realm realm;
    private UserOpd userOpd;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
//        setContentView(R.layout.);
        int id = savedState == null ? getIntent().getIntExtra("id", 0) : savedState.getInt("id");
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onChange(@NonNull UserOpd userOpd) {

    }
}
