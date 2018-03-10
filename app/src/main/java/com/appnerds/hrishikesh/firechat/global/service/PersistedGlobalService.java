package com.appnerds.hrishikesh.firechat.global.service;

import com.appnerds.hrishikesh.firechat.database.DatabaseResult;
import com.appnerds.hrishikesh.firechat.global.data_model.Chat;
import com.appnerds.hrishikesh.firechat.global.data_model.Message;
import com.appnerds.hrishikesh.firechat.global.database.GlobalDatabase;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by marco on 08/08/16.
 */

public class PersistedGlobalService implements GlobalService {

    private final GlobalDatabase globalDatabase;

    public PersistedGlobalService(GlobalDatabase globalDatabase) {
        this.globalDatabase = globalDatabase;
    }

    @Override
    public Observable<Message> syncMessages() {
        return globalDatabase.observeAddMessage();
    }

    @Override
    public Observable<DatabaseResult<Chat>> getChat() {
        return globalDatabase.observeChat()
                .map(asDatabaseResult())
                .onErrorReturn(DatabaseResult.<Chat>errorAsDatabaseResult());
    }

    private static Func1<Chat, DatabaseResult<Chat>> asDatabaseResult() {
        return new Func1<Chat, DatabaseResult<Chat>>() {
            @Override
            public DatabaseResult<Chat> call(Chat chat) {
                return new DatabaseResult<>(chat);
            }
        };
    }

    @Override
    public void sendMessage(Message message) {
        globalDatabase.sendMessage(message);
    }

}
