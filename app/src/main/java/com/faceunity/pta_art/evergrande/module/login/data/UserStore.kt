package com.faceunity.pta_art.evergrande.module.login.data

import com.faceunity.pta_art.evergrande.store.EVGStorage

object UserStore {

    private const val KEY_USER_DO = "key_user"

    var userBO: UserBO? = null

    fun saveUserInfo(userDO: UserDO) {
        userBO = UserBO(userDO)
        EVGStorage.getDefaultStore().put(KEY_USER_DO, userDO)
    }

    fun getUserInfo(): UserBO? {
        initUserInfo()
        return userBO
    }

    private fun initUserInfo() {
        userBO?.run {
            val userDO = EVGStorage.getDefaultStore().getParcelable(KEY_USER_DO, UserDO::class.java)
            userDO?.run {
                userBO = UserBO(this)
            }
        }
    }

    fun isUserHasLoged(): Boolean {
        return getUserInfo()?.run {
            true
        } ?: false
    }

}