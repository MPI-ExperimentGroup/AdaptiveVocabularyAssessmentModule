/*
 * Copyright (C) 2014 Language In Interaction
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.ru.languageininteraction.language.client.service;

import com.google.gwt.core.client.GWT;
import nl.ru.languageininteraction.language.client.model.UserResults;
import com.google.gwt.storage.client.Storage;
import java.util.ArrayList;
import java.util.List;
import nl.ru.languageininteraction.language.client.Messages;
import nl.ru.languageininteraction.language.client.model.MetadataField;
import nl.ru.languageininteraction.language.client.model.UserData;
import nl.ru.languageininteraction.language.client.model.UserId;

/**
 * @since Oct 24, 2014 3:01:35 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class LocalStorage {

    private Storage dataStore = null;
    private static final String USER_RESULTS = "UserResults.";
    private static final String LAST_USER_ID = "LastUserId.";
    protected static final String MAX_SCORE = "maxScore";
    final MetadataFieldProvider metadataFieldProvider = new MetadataFieldProvider();

    private Storage loadStorage() {
        if (dataStore == null) {
            dataStore = Storage.getLocalStorageIfSupported();
        }
        return dataStore;
    }

    public UserData getStoredData(UserId userId) {
        UserData userData = new UserData(userId);
        loadStorage();
        if (dataStore != null) {
            for (MetadataField metadataField : metadataFieldProvider.metadataFieldArray) {
                userData.setMetadataValue(metadataField, getCleanStoredData(USER_RESULTS + userData.getUserId().toString() + "." + metadataField.getPostName()));
            }
        }
        userData.updateBestScore(getCleanStoredInt(USER_RESULTS + userData.getUserId().toString() + "." + MAX_SCORE));
        return userData;
    }

    private int getCleanStoredInt(String keyString) {
        final String cleanStoredData = getCleanStoredData(keyString);
        try {
            return Integer.parseInt(cleanStoredData);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

    private String getCleanStoredData(String keyString) {
        final String storedValue = dataStore.getItem(keyString);
        return (storedValue == null || "undefined".equals(storedValue)) ? "" : storedValue;
    }

    public void clear() {
        loadStorage();
        if (dataStore != null) {
            dataStore.clear();
        }
    }

    public void storeData(UserResults userResults) {
        loadStorage();
        if (dataStore != null) {
            for (MetadataField metadataField : metadataFieldProvider.metadataFieldArray) {
                dataStore.setItem(USER_RESULTS + userResults.getUserData().getUserId().toString() + "." + metadataField.getPostName(), userResults.getUserData().getMetadataValue(metadataField));
            }
        }
        dataStore.setItem(USER_RESULTS + userResults.getUserData().getUserId().toString() + "." + MAX_SCORE, Integer.toString(userResults.getUserData().getBestScore()));
        dataStore.setItem(LAST_USER_ID, userResults.getUserData().getUserId().toString());
    }

    public UserId getLastUserData() {
        loadStorage();
        if (dataStore != null) {
            final String storedUserId = dataStore.getItem(LAST_USER_ID);
            return new UserId(storedUserId);
        } else {
            return new UserId();
        }
    }

    private final Messages messages = GWT.create(Messages.class);

    public List<UserData> getUserIdList() {
        ArrayList<UserData> userIdList = new ArrayList<>();
        userIdList.add(new UserData(messages.defaultUserName() + 1));
        userIdList.add(new UserData(messages.defaultUserName() + 2));
        userIdList.add(new UserData(messages.defaultUserName() + 3));
        userIdList.add(new UserData(messages.defaultUserName() + 4));
        userIdList.add(new UserData(messages.defaultUserName() + 5));
        userIdList.add(new UserData(messages.defaultUserName() + 6));
        userIdList.add(new UserData(messages.defaultUserName() + 7));
        userIdList.add(new UserData(messages.defaultUserName() + 8));
        return userIdList;
    }
}
