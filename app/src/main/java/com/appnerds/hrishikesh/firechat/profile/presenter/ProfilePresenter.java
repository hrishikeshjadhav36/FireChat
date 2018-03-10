package com.appnerds.hrishikesh.firechat.profile.presenter;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.appnerds.hrishikesh.firechat.login.data_model.Authentication;
import com.appnerds.hrishikesh.firechat.login.service.LoginService;
import com.appnerds.hrishikesh.firechat.navigation.ProfileNavigator;
import com.appnerds.hrishikesh.firechat.profile.service.ProfileService;
import com.appnerds.hrishikesh.firechat.profile.view.ProfileDisplayer;
import com.appnerds.hrishikesh.firechat.storage.StorageService;
import com.appnerds.hrishikesh.firechat.user.data_model.User;
import com.appnerds.hrishikesh.firechat.user.service.UserService;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by marco on 09/09/16.
 */

public class ProfilePresenter {

    private final LoginService loginService;
    private final UserService userService;
    private final ProfileService profileService;
    private final StorageService storageService;
    private final ProfileDisplayer profileDisplayer;
    private final ProfileNavigator navigator;

    private User self;
    private Subscription loginSubscription;
    private Subscription userSubscription;

    public ProfilePresenter(LoginService loginService,
                            UserService userService,
                            ProfileService profileService,
                            StorageService storageService,
                            ProfileDisplayer loginDisplayer,
                            ProfileNavigator navigator) {
        this.loginService = loginService;
        this.userService = userService;
        this.profileService = profileService;
        this.storageService = storageService;
        this.profileDisplayer = loginDisplayer;
        this.navigator = navigator;
    }

    public void startPresenting() {
        navigator.attach(dialogListener);
        profileDisplayer.attach(actionListener);
        loginSubscription = loginService.getAuthentication()
                .subscribe(new Action1<Authentication>() {
                    @Override
                    public void call(final Authentication authentication) {
                        if (authentication.isSuccess()) {
                            userService.getUser(authentication.getUser().getUid())
                                    .subscribe(new Subscriber<User>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(User user) {
                                            self = user;
                                            profileDisplayer.display(user);
                                        }
                                    });
                        } else {
                            navigator.toParent();
                        }
                    }
                });
    }

    public void stopPresenting() {
        navigator.detach(dialogListener);
        profileDisplayer.detach(actionListener);
        loginSubscription.unsubscribe();
        if (userSubscription != null)
            userSubscription.unsubscribe();
    }

    private ProfileDisplayer.ProfileActionListener actionListener = new ProfileDisplayer.ProfileActionListener() {

        @Override
        public void onUpPressed() {
            navigator.toParent();
        }

        @Override
        public void onNamePressed(String hint, TextView textView) {
            if (self != null)
                navigator.showInputTextDialog(hint,textView,self);
        }

        @Override
        public void onPasswordPressed(String hint) {
            navigator.showInputPasswordDialog(hint,self);
        }

        @Override
        public void onImagePressed() {
            navigator.showImagePicker();
        }

        @Override
        public void onRemovePressed() {
            navigator.showRemoveDialog();
        }
    };

    private ProfileNavigator.ProfileDialogListener dialogListener = new ProfileNavigator.ProfileDialogListener() {

        @Override
        public void onNameSelected(String text, User user) {
            userService.setName(user,text);
        }

        @Override
        public void onPasswordSelected(String text) {
            profileService.setPassword(text);
        }

        @Override
        public void onRemoveSelected() {
            profileService.removeUser();
        }

        @Override
        public void onImageSelected(final Bitmap bitmap) {
            storageService.uploadImage(bitmap)
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String image) {
                            if (image != null) {
                                userService.setProfileImage(self,image);
                                profileDisplayer.updateProfileImage(bitmap);
                            }
                        }
                    });
        }

    };

}
