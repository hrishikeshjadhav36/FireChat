package com.appnerds.hrishikesh.firechat.login.service;


import com.appnerds.hrishikesh.firechat.login.data_model.Authentication;

import rx.Observable;

/**
 * Created by marco on 27/07/16.
 */

public interface LoginService {

    Observable<Authentication> getAuthentication();

    void loginWithGoogle(String idToken);

    void loginWithEmailAndPass(String email, String password);

    void sendPasswordResetEmail(String email);

}
