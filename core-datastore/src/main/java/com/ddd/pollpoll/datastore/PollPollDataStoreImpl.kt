package com.ddd.pollpoll.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PollPollDataStoreImpl @Inject constructor(
    private val pollPollPreferences: DataStore<Preferences>
) : PollPollDataStore {

    override fun getToken(): Flow<String> = pollPollPreferences.data.map { preferences ->
        preferences[KEY_TOKEN] ?: ""
    }

    override suspend fun updateToken(name: String) {
        pollPollPreferences.edit { preferences ->
            preferences[KEY_TOKEN] = name
        }
    }

    override fun getNickName(): Flow<String> = pollPollPreferences.data.map { preferences ->
        preferences[KEY_TOKEN] ?: ""
    }

    override suspend fun updateNickName(nickName: String) {
        pollPollPreferences.edit { preferences ->
            preferences[NICKNAME_TOKEN] = nickName
        }
    }

    override fun clear() {
        // TODO
    }

    companion object {
        const val PREFERENCE_DATA_STORE_NAME = "pollpoll_preferences.pb"
        private val KEY_TOKEN = "key_token".toKey()
        private val NICKNAME_TOKEN = "nickname_token".toKey()

        private fun String.toKey(): Preferences.Key<String> = stringPreferencesKey(this)
    }
}
