package com.appnerds.hrishikesh.firechat.main.service;

import com.appnerds.hrishikesh.firechat.main.database.CloudMessagingDatabase;
import com.appnerds.hrishikesh.firechat.user.data_model.User;

import rx.Observable;

/**
 * Created by marco on 17/08/16.
 */

public class FirebaseCloudMessagingService implements CloudMessagingService {

    private CloudMessagingDatabase messagingDatabase;

    public FirebaseCloudMessagingService(CloudMessagingDatabase messagingDatabase) {
        this.messagingDatabase = messagingDatabase;
    }

    @Override
    public Observable<String> readToken(User user) {
        return messagingDatabase.readToken(user);
    }

    @Override
    public void setToken(User user) {
        messagingDatabase.setToken(user);
    }
}
