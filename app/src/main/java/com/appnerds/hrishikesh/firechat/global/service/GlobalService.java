package com.appnerds.hrishikesh.firechat.global.service;

import com.appnerds.hrishikesh.firechat.database.DatabaseResult;
import com.appnerds.hrishikesh.firechat.global.data_model.Chat;
import com.appnerds.hrishikesh.firechat.global.data_model.Message;

import rx.Observable;

/**
 * Created by marco on 08/08/16.
 */

public interface GlobalService {

    Observable<Message> syncMessages();

    Observable<DatabaseResult<Chat>> getChat();

    void sendMessage(Message message);

}
