package com.appnerds.hrishikesh.firechat.global.view;

import com.appnerds.hrishikesh.firechat.global.data_model.Chat;
import com.appnerds.hrishikesh.firechat.global.data_model.Message;
import com.appnerds.hrishikesh.firechat.user.data_model.User;
import com.appnerds.hrishikesh.firechat.user.data_model.Users;

/**
 * Created by marco on 08/08/16.
 */

// TODO open conversation on click
public interface GlobalDisplayer {

    void display(Chat chat, Users users, User user);

    void addToDisplay(Message message, User sender, User user);

    void attach(GlobalActionListener globalActionListener);

    void detach(GlobalActionListener globalActionListener);

    void enableInteraction();

    void disableInteraction();

    interface GlobalActionListener {

        void onUpPressed();

        void onMessageLengthChanged(int messageLength);

        void onSubmitMessage(String message);

    }

    interface GlobalMessageListener {

        void onUserSelected();

    }

}
