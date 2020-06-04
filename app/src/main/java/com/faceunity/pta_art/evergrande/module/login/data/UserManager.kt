package com.faceunity.pta_art.evergrande.module.login.data

import io.reactivex.Completable

object UserManager {

    fun login(username: String, password: String): Completable {
        return UserService.login(username, password).ignoreElements()
    }

    fun register(username: String, password: String): Completable {
        return UserService.register(username, password).ignoreElements()
    }

    fun isUserHasLogged(): Boolean {
        return UserStore.getUserInfo()?.run {
            true
        } ?: false
    }

    fun getUserId(): String {
        return UserStore.getUserInfo()?.userDO?.userId ?: ""
    }

    fun getToken(): String {
        return UserStore.getUserInfo()?.userDO?.token ?: ""
    }

    fun logout(): Completable {
        return UserService.logout()
                .ignoreElements()
                .onErrorComplete()
                .doOnComplete {
                    UserStore.clear()
                }
    }

}