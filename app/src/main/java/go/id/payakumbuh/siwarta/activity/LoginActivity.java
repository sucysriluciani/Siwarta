package go.id.payakumbuh.siwarta.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import go.id.payakumbuh.siwarta.R;
import go.id.payakumbuh.siwarta.db.models.login.BaseUser;
import go.id.payakumbuh.siwarta.util.NetworkUtils;
import go.id.payakumbuh.siwarta.util.ViewUtils;

/**
 * Created by anggrayudi on 06/02/18.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.til_username) TextInputLayout inputUsername;
    @BindView(R.id.til_password) TextInputLayout inputPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BaseUser.getUser(this) != null) {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            inputUsername.getEditText().setCompoundDrawablesWithIntrinsicBounds(null, null, ViewUtils.setDrawableTint(
                    ContextCompat.getDrawable(this, R.drawable.ic_person_black_24dp),
                    ContextCompat.getColor(this, R.color.colorPrimary)), null);
            inputPassword.getEditText().setCompoundDrawablesWithIntrinsicBounds(null, null, ViewUtils.setDrawableTint(
                    ContextCompat.getDrawable(this, R.drawable.ic_lock_black_24dp),
                    ContextCompat.getColor(this, R.color.colorPrimary)), null);
        }
    }

    @OnClick(R.id.btn_login)
    void login(){
        final String username = inputUsername.getEditText().getText().toString().trim();
        final String password = inputPassword.getEditText().getText().toString().trim();
        inputUsername.setError(username.isEmpty() ? getString(R.string.empty_warning) : null);
        inputPassword.setError(password.isEmpty() ? getString(R.string.empty_warning) : null);
        if (inputPassword.getError() != null || inputUsername.getError() != null)
            return;
        if (!NetworkUtils.isNetworkAvailable(this)){
            Toast.makeText(this, "Jaringan tidak tersedia", Toast.LENGTH_SHORT).show();
            return;
        }
        final MaterialDialog loading = new MaterialDialog.Builder(this)
                .content("Logging in...")
                .progress(true, 0)
                .cancelable(false)
                .show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean sukses = BaseUser.loginOnline(LoginActivity.this, username, password);
                inputPassword.post(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        if (sukses)
                            startActivity(new Intent(getBaseContext(), MainActivity.class));
                        else {
                            inputPassword.getEditText().setText("");
                            new MaterialDialog.Builder(LoginActivity.this)
                                    .title("Login gagal")
                                    .content("Username atau password yang Anda masukkan salah.")
                                    .positiveText(android.R.string.ok)
                                    .show();
                        }
                    }
                });

            }
        }).start();
    }
}
