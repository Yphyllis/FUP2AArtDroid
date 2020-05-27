package com.faceunity.pta_art.evergrande.module.login.data

import com.faceunity.pta_art.evergrande.net.BaseResult
import com.faceunity.pta_art.evergrande.utils.md5
import io.reactivex.Observable
import rxhttp.wrapper.param.RxHttp

object UserService {

    fun login(username: String, password: String): Observable<UserDO> {
        return RxHttp.postJson("/userCenter/login")
                .add("username", username)
                .add("password", password.md5())
                .asResponse(UserDO::class.java)
                .doOnNext {
                    UserStore.saveUserInfo(it)
                }
    }

    fun register(username: String, password: String): Observable<Boolean> {
        return RxHttp.postJson("/userCenter/register")
                .add("username", username)
                .add("password", password.md5())
                .add("vin", "winwin$username")
                .asResponse(BaseResult::class.java)
                .map { it.result }
    }

}