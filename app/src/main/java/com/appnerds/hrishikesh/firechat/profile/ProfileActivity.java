package com.appnerds.hrishikesh.firechat.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.appnerds.hrishikesh.firechat.BaseActivity;
import com.appnerds.hrishikesh.firechat.Dependencies;
import com.appnerds.hrishikesh.firechat.R;
import com.appnerds.hrishikesh.firechat.navigation.AndroidProfileNavigator;
import com.appnerds.hrishikesh.firechat.profile.presenter.ProfilePresenter;
import com.appnerds.hrishikesh.firechat.profile.view.ProfileDisplayer;

/**
 * Created by marco on 19/08/16.
 */

public class ProfileActivity extends BaseActivity {

    private ProfilePresenter presenter;
    private AndroidProfileNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.profile_toolbar_title);
        ProfileDisplayer profileDisplayer = (ProfileDisplayer) findViewById(R.id.profileView);

        navigator = new AndroidProfileNavigator(this);
        presenter = new ProfilePresenter(
                Dependencies.INSTANCE.getLoginService(),
                Dependencies.INSTANCE.getUserService(),
                Dependencies.INSTANCE.getProfileService(),
                Dependencies.INSTANCE.getStorageService(),
                profileDisplayer,
                navigator
        );
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopPresenting();
    }

}