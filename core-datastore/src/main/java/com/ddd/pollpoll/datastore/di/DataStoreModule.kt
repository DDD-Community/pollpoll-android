package com.ddd.pollpoll.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ddd.pollpoll.datastore.PollPollDataStore
import com.ddd.pollpoll.datastore.PollPollDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindPollPollDataStore(impl: PollPollDataStoreImpl): PollPollDataStore

    internal companion object {
        @Provides
        @Singleton
        fun providePollPollPreferences(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(PollPollDataStoreImpl.PREFERENCE_DATA_STORE_NAME)
        }
    }
}
