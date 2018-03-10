package com.appnerds.hrishikesh.firechat.global.presenter;

import com.appnerds.hrishikesh.firechat.database.DatabaseResult;
import com.appnerds.hrishikesh.firechat.global.data_model.Chat;
import com.appnerds.hrishikesh.firechat.global.data_model.Message;
import com.appnerds.hrishikesh.firechat.global.service.GlobalService;
import com.appnerds.hrishikesh.firechat.global.view.GlobalDisplayer;
import com.appnerds.hrishikesh.firechat.login.data_model.Authentication;
import com.appnerds.hrishikesh.firechat.login.service.LoginService;
import com.appnerds.hrishikesh.firechat.navigation.Navigator;
import com.appnerds.hrishikesh.firechat.user.data_model.User;
import com.appnerds.hrishikesh.firechat.user.data_model.Users;
import com.appnerds.hrishikesh.firechat.user.service.UserService;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * Created by marco on 08/08/16.
 */

public class GlobalPresenter {

    private final LoginService loginService;
    private final GlobalService globalService;
    private final GlobalDisplayer globalDisplayer;
    private final UserService userService;
    private final Navigator navigator;

    private User user;

    private Subscription subscription;

    public GlobalPresenter(
            LoginService loginService,
            GlobalService globalService,
            GlobalDisplayer globalDisplayer,
            UserService userService,
            Navigator navigator
    ) {
        this.loginService = loginService;
        this.globalService = globalService;
        this.globalDisplayer = globalDisplayer;
        this.userService = userService;
        this.navigator = navigator;
    }

    public void startPresenting() {
        globalDisplayer.attach(actionListener);
        globalDisplayer.disableInteraction();

        final Subscriber messagesSubscriber = new Subscriber<Message>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final Message message) {
                userService.getUser(message.getUid())
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(User sender) {
                                if (sender != null)
                                    globalDisplayer.addToDisplay(message,sender,user);
                            }
                        });
            }
        };

        final Subscriber chatSubscriber = new Subscriber<DatabaseResult<Chat>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final DatabaseResult<Chat> chatDatabaseResult) {
                final Chat chat = chatDatabaseResult.getData();
                userService.getUsers()
                        .subscribe(new Action1<Users>() {
                            @Override
                            public void call(Users users) {
                                globalDisplayer.display(chat,users,user);
                                globalService.syncMessages()
                                    .subscribe(messagesSubscriber);
                            }
                        });
            }
        };

        loginService.getAuthentication()
                .subscribe(new Subscriber<Authentication>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Authentication authentication) {
                        if (authentication.isSuccess()) {
                            user = authentication.getUser();
                            subscription = globalService.getChat()
                                        .subscribe(chatSubscriber);
                        }
                    }
                });

    }

    public void stopPresenting() {
        globalDisplayer.detach(actionListener);
        subscription.unsubscribe();
    }

    private final GlobalDisplayer.GlobalActionListener actionListener = new GlobalDisplayer.GlobalActionListener() {

        @Override
        public void onUpPressed() {
            navigator.toParent();
        }

        @Override
        public void onMessageLengthChanged(int messageLength) {
            if (messageLength > 0) {
                globalDisplayer.enableInteraction();
            } else {
                globalDisplayer.disableInteraction();
            }
        }

        @Override
        public void onSubmitMessage(String message) {
            if (user != null)
                globalService.sendMessage(new Message(user.getUid(),message));
        }

    };

    static class Pair {

        public final DatabaseResult<Chat> conversationDatabaseResult;
        public final Authentication auth;

        private Pair(DatabaseResult<Chat> conversationDatabaseResult, Authentication auth) {
            this.conversationDatabaseResult = conversationDatabaseResult;
            this.auth = auth;
        }

        static Func2<DatabaseResult<Chat>, Authentication, Pair> asPair() {
            return new Func2<DatabaseResult<Chat>, Authentication, Pair>() {
                @Override
                public GlobalPresenter.Pair call(DatabaseResult<Chat> chatDatabaseResult, Authentication authentication) {
                    return new GlobalPresenter.Pair(chatDatabaseResult, authentication);
                }
            };
        }

    }

}
