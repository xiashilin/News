package com.kaku.colorfulnews.mvp.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;
import com.kaku.colorfulnews.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.avos.avoscloud.AVUser.*;


/**
 * Created by gy on 2017/12/8.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_et_email)
    EditText emailET;
    @BindView(R.id.login_et_username)
    EditText usernameET;
    @BindView(R.id.login_et_password)
    EditText passwordET;
    @BindView(R.id.login_et_rpassword)
    EditText rpasswordET;
    @BindView(R.id.login_tv_sign)
    TextView signTV;
    @BindView(R.id.login_btn_login)
    Button loginBtn;
    @BindView(R.id.login_progress)
    View mProgressView;
    @BindView(R.id.login_tv_forget)
    TextView forgetTV;

    @OnClick(R.id.login_tv_sign)
    public void onclick() {
        goActivity(RegisterActivity.class);
    }

    private void attemptLogin() {
        usernameET.setError(null);
        passwordET.setError(null);

        final String username = usernameET.getText().toString();
        final String password = passwordET.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordET.setError(getString(R.string.error_invalid_password));
            focusView = passwordET;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            usernameET.setError(getString(R.string.error_field_required));
            focusView = usernameET;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            signTV.setVisibility(View.GONE);
            forgetTV.setVisibility(View.GONE);
            logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        setResult(RESULT_OK);
                        LoginActivity.this.finish();
                    } else {
                        showProgress(false);
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginBtn.setVisibility(show ? View.GONE : View.VISIBLE);
            loginBtn.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginBtn.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginBtn.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.ay_login;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {

        usernameET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }


}
