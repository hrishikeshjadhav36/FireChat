package com.appnerds.hrishikesh.firechat.main.service;

import com.appnerds.hrishikesh.firechat.user.data_model.User;

import rx.Observable;

/**
 * Created by marco on 17/08/16.
 */

public interface CloudMessagingService {

    Observable<String> readToken(User user);

    void setToken(User user);

}
