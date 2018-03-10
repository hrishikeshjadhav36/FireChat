package com.appnerds.hrishikesh.firechat.main.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.appnerds.hrishikesh.firechat.main.database.CloudMessagingDatabase;
import com.appnerds.hrishikesh.firechat.user.data_model.User;
import com.appnerds.hrishikesh.firechat.user.database.UserDatabase;

import rx.Subscriber;

/**
 * Created by marco on 16/08/16.
 */

public class PersistedMainService implements MainService {

    private final FirebaseAuth firebaseAuth;
    private final UserDatabase userDatabase;
    private final CloudMessagingDatabase cloudMessagingDatabase;

    public PersistedMainService(FirebaseAuth firebaseAuth, UserDatabase userDatabase, CloudMessagingDatabase cloudMessagingDatabase) {
        this.firebaseAuth = firebaseAuth;
        this.userDatabase = userDatabase;
        this.cloudMessagingDatabase = cloudMessagingDatabase;
    }

    @Override
    public String getLoginProvider() throws Exception {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null)
            return firebaseUser.getProviders().get(0);
        else
            throw new Exception("Couldn't get the provider");
    }

    @Override
    public void logout() {
        cloudMessagingDatabase.disableToken(firebaseAuth.getCurrentUser().getUid());
        firebaseAuth.signOut();
    }


    @Override
    public void initLastSeen(final User user) {
        userDatabase.initUserLastSeen()
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result.equals(Boolean.TRUE))
                            userDatabase.setUserLastSeen(user.getUid());
                    }
                });
    }

}
