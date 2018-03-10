package com.appnerds.hrishikesh.firechat.global.database;

import com.appnerds.hrishikesh.firechat.global.data_model.Chat;
import com.appnerds.hrishikesh.firechat.global.data_model.Message;

import rx.Observable;

/**
 * Created by marco on 08/08/16.
 */

public interface GlobalDatabase {

    Observable<Message> observeAddMessage();

    Observable<Chat> observeChat();

    void sendMessage(Message message);

}

