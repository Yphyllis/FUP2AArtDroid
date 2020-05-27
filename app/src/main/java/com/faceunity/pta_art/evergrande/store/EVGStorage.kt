package com.faceunity.pta_art.evergrande.store

import android.app.Application
import com.tencent.mmkv.MMKV

object EVGStorage {

    private val evgStores = mutableMapOf<String, EVGStore>()
    private val noClearHfStores = mutableMapOf<String, EVGStore>()

    fun init(application: Application) {
        MMKV.initialize(application)
    }

    fun getNoClearStore(mmapID: String = "noClear"): EVGStore {
        val store = noClearHfStores[mmapID]
        return if (store == null) {
            val newStore = EVGStore.withID(mmapID)
            noClearHfStores[mmapID] = newStore
            newStore
        } else {
            store
        }
    }

    fun getStore(mmapID: String): EVGStore {
        val store = evgStores[mmapID]
        return if (store == null) {
            val newStore = EVGStore.withID(mmapID)
            evgStores[mmapID] = newStore
            newStore
        } else store
    }

    fun getDefaultStore(): EVGStore {
        return getStore("default")
    }

    fun clearAll() {
        for (store in evgStores.values) {
            store.clearAll()
        }
    }

}