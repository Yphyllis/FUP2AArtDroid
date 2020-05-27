package com.faceunity.pta_art.evergrande.store

import android.os.Build
import android.os.Parcelable
import com.tencent.mmkv.MMKV

class EVGStore(private val mmkv: MMKV) {

    companion object {
        internal fun withID(mmapID: String): EVGStore {
            val mmkv = MMKV.mmkvWithID(mmapID)
            return EVGStore(mmkv)
        }
    }

    fun put(key: String, value: Any) {
        when (value) {
            is Int -> mmkv.encode(key, value)
            is Boolean -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is Double -> mmkv.encode(key, value)
            is ByteArray -> mmkv.encode(key, value)
            is String -> mmkv.encode(key, value)
            is Parcelable -> mmkv.encode(key, value)
            else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                throw Exception("do not support type ${value.javaClass.typeName}")
            } else {
                throw Exception("do not support type ${value.javaClass.componentType}")
            }
        }
    }

    fun putSet(key: String, sets: MutableSet<String>) {
        mmkv.putStringSet(key, sets)
    }

    fun getInt(key: String, defaultValue: Int = 0) = mmkv.decodeInt(key, defaultValue)

    fun getBool(key: String, defaultValue: Boolean = false) = mmkv.decodeBool(key, defaultValue)

    fun getLong(key: String, defaultValue: Long = 0L) = mmkv.decodeLong(key, defaultValue)

    fun getFloat(key: String, defaultValue: Float = 0.0F) = mmkv.decodeFloat(key, defaultValue)

    fun getDouble(key: String, defaultValue: Double = 0.0) = mmkv.decodeDouble(key, defaultValue)

    fun getString(key: String, defaultValue: String = "") = mmkv.decodeString(key, defaultValue)

    fun getByteArray(key: String) = mmkv.decodeBytes(key)

    fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T? = mmkv.decodeParcelable(key, tClass)

    fun <T : Parcelable> getParcelableDefault(key: String, tClass: Class<T>, defaultValue: T): T? =
        mmkv.decodeParcelable(key, tClass, defaultValue)

    fun getSet(key: String): MutableSet<String> = mmkv.decodeStringSet(key, mutableSetOf())

    fun remove(key: String) {
        mmkv.remove(key)
    }

    fun removeValueForKey(key: String) {
        mmkv.removeValueForKey(key)
    }

    fun removeValuesForKeys(keys: Array<String>) {
        mmkv.removeValuesForKeys(keys)
    }

    fun clearAll() {
        mmkv.clearMemoryCache()
        mmkv.clearAll()
    }

}