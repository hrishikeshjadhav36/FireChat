package com.appnerds.hrishikesh.firechat.conversation_list.database;

import com.appnerds.hrishikesh.firechat.user.data_model.User;

import java.util.List;

import rx.Observable;

/**
 * Created by marco on 29/07/16.
 */

public interface ConversationListDatabase {

    Observable<List<String>> observeConversationsFor(User user);

}
