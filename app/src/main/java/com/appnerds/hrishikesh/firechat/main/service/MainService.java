package com.appnerds.hrishikesh.firechat.main.service;

import com.appnerds.hrishikesh.firechat.user.data_model.User;

/**
 * Created by marco on 16/08/16.
 */

public interface MainService {

    String getLoginProvider() throws Exception;

    void initLastSeen(User user);

    void logout();

}
