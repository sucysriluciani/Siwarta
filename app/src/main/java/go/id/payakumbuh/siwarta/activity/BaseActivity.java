package go.id.payakumbuh.siwarta.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

/**
 * Created by anggrayudi on 07/02/18.
 */
@SuppressLint("Registered")
class BaseActivity extends AppCompatActivity {

    public static final String ACTION_FINISH_ALL_ACTIVITIES = "go.id.payakumbuh.siwarta.activity.FINISH_ACTIVITIES";

    private BroadcastReceiver receiver;
    protected Realm realm;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction() == null)
                        return;

                    switch (intent.getAction()){
                        case ACTION_FINISH_ALL_ACTIVITIES:
                            finish();
                            break;
                    }
                }
            };
            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ACTION_FINISH_ALL_ACTIVITIES));
        }
    }

    @Override
    protected void onDestroy() {
        realm.close();
        if (receiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    public final void finishAllActivities(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            finishAffinity();
        else
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_FINISH_ALL_ACTIVITIES));
    }
}
