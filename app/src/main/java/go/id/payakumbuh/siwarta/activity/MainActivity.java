package go.id.payakumbuh.siwarta.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.id.payakumbuh.siwarta.R;
import go.id.payakumbuh.siwarta.db.models.login.BaseLogin;
import go.id.payakumbuh.siwarta.db.models.login.BaseUser;
import go.id.payakumbuh.siwarta.db.models.object.Opd;
import go.id.payakumbuh.siwarta.util.NetworkUtils;

import static go.id.payakumbuh.siwarta.db.models.login.BaseUser.LEVEL_ADMIN;
import static go.id.payakumbuh.siwarta.db.models.login.BaseUser.LEVEL_SUPERADMIN;
import static go.id.payakumbuh.siwarta.db.models.login.BaseUser.LEVEL_WARTAWAN;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    private BaseLogin user;
    private MaterialDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        user = BaseUser.getUser(this);
        switch (user.getLevel()) {
            case LEVEL_SUPERADMIN:
                navigationView.inflateMenu(R.menu.nav_menu_superadmin);
                break;
            case LEVEL_ADMIN:
                navigationView.inflateMenu(R.menu.nav_menu_admin);
                break;
            case LEVEL_WARTAWAN:
                navigationView.inflateMenu(R.menu.nav_menu_wartawan);
                break;
            default:
                navigationView.inflateMenu(R.menu.nav_menu_user_opd);
                break;
        }
        if (savedInstanceState == null && realm.where(Opd.class).count() == 0)
            updateDatabase();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.action_refresh:
                updateDatabase();
                break;
            case R.id.action_logout:
                BaseUser.logout(this);
                finishAllActivities();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            finishAllActivities();
    }

    private void updateDatabase(){
        if (NetworkUtils.isNetworkAvailable(this)) {
            loading = new MaterialDialog.Builder(this)
                    .content("Updating data...")
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            NetworkUtils.refreshDatabaseMainObjects(user, new NetworkUtils.OnUpdateDatabaseFinished() {
                @Override
                public void onFinished() {
                    loading.dismiss();
                    loading = null;
                }
            });
        } else
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
    }
}
