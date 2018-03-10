package com.appnerds.hrishikesh.firechat.conversation.view;

import com.appnerds.hrishikesh.firechat.conversation.data_model.Chat;
import com.appnerds.hrishikesh.firechat.conversation.data_model.Message;

/**
 * Created by marco on 29/07/16.
 */

public interface ConversationDisplayer {

    void display(Chat chat, String user);

    void addToDisplay(Message message, String user);

    void setupToolbar(String user, String image, long lastSeen);

    void showTyping();

    void hideTyping();

    void attach(ConversationActionListener conversationInteractionListener);

    void detach(ConversationActionListener conversationInteractionListener);

    void enableInteraction();

    void disableInteraction();

    interface ConversationActionListener {

        void onUpPressed();

        void onMessageLengthChanged(int messageLength);

        void onSubmitMessage(String message);

    }

}
