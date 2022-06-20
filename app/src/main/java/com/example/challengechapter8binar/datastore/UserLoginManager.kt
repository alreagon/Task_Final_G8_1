package com.example.challengechapter8binar.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLoginManager(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore("login-prefs")

    companion object {
        val ADDRESS = preferencesKey<String>("ADDRESS")
        val EMAIL = preferencesKey<String>("EMAIL")
        val NAME = preferencesKey<String>("NAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val TANGGAL_LAHIR = preferencesKey<String>("TANGGAL_LAHIR")
        val USERNAME = preferencesKey<String>("USERNAME")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveDataLogin(
        address: String,
        email: String,
        name: String,
        password: String,
        tanggalLahir: String,
        username: String
    ) {
        dataStore.edit {
            it[ADDRESS] = address
            it[NAME] = name
            it[PASSWORD] = password
            it[EMAIL] = email
            it[TANGGAL_LAHIR] = tanggalLahir
            it[USERNAME] = username
        }
    }

    suspend fun setBoolean(boolean: Boolean) {
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

    suspend fun clearDataLogin() {
        dataStore.edit {
            it.clear()
        }
    }

    val name: Flow<String> = dataStore.data.map {
        it[NAME] ?: ""
    }

    val username: Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val boolean: Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }
}