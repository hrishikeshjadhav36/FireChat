package com.appnerds.hrishikesh.firechat.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.appnerds.hrishikesh.firechat.BaseActivity;
import com.appnerds.hrishikesh.firechat.Dependencies;
import com.appnerds.hrishikesh.firechat.R;
import com.appnerds.hrishikesh.firechat.login.presenter.LoginPresenter;
import com.appnerds.hrishikesh.firechat.login.view.LoginDisplayer;
import com.appnerds.hrishikesh.firechat.navigation.AndroidLoginNavigator;
import com.appnerds.hrishikesh.firechat.navigation.AndroidNavigator;

/**
 * Created by marco on 27/07/16.
 */

public class LoginActivity extends BaseActivity {

    private LoginPresenter presenter;
    private AndroidLoginNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        LoginDisplayer loginDisplayer = (LoginDisplayer) findViewById(R.id.loginView);
        LoginGoogleApiClient loginGoogleApiClient = new LoginGoogleApiClient(this);
        loginGoogleApiClient.setupGoogleApiClient();
        navigator = new AndroidLoginNavigator(this, loginGoogleApiClient, new AndroidNavigator(this));
        presenter = new LoginPresenter(
                Dependencies.INSTANCE.getLoginService(),
                loginDisplayer,
                navigator);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!navigator.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startPresenting();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopPresenting();
    }

}

