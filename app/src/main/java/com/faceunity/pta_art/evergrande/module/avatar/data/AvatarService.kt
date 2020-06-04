package com.faceunity.pta_art.evergrande.module.avatar.data

import android.util.Log
import com.faceunity.pta_art.evergrande.module.login.data.UserManager
import com.faceunity.pta_art.evergrande.net.BaseResult
import com.faceunity.pta_art.evergrande.net.ModelDO
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rxhttp.wrapper.param.RxHttp
import java.io.File

class AvatarService {

    fun postHeaBundle(file: String, fileName: String): Completable {
        return postAvatarFile(file, fileName, AvatarFileType.HEAD)
    }

    fun postHairBundle(file: String, fileName: String): Completable {
        return postAvatarFile(file, fileName, AvatarFileType.HAIR)
    }

    fun postAvatar(file: String, fileName: String): Completable {
        return postAvatarFile(file, fileName, AvatarFileType.AVATAR)
    }

    fun postBodyImage(file: String, fileName: String): Completable {
        return postAvatarFile(file, fileName, AvatarFileType.BODYIMAGE)
    }

    private fun postAvatarFile(file: String, fileName: String, avatarFileType: AvatarFileType): Completable {
        return postAvatarFile(file, avatarFileType.type, avatarFileType.materialType, fileName, UserManager.getUserId()).ignoreElements()
                .doOnComplete {
                    Log.d("AvatarService", "post avatar file $avatarFileType complete")
                }.doOnError {
                    Log.d("AvatarService", "post avatar file $avatarFileType error", it)
                }
    }

    private fun postAvatarFile(file: String, type: String, materialType: String, fileName: String, userId: String): Observable<BaseResult> {
        return RxHttp.postForm("/virtual/uploadMaterial")
                .addFile("file", File(file))
                .add("materialType", materialType, true)
                .add("type", type, true)
                .add("name", fileName, true)
                .add("userId", userId, true)
                .asResponse(BaseResult::class.java)
    }

    fun postAvatarConfig(file: String, bodyImage: String, modelId: String): Single<String> {
        return postAvatarConfig(file, bodyImage, modelId, UserManager.getUserId()).map { it.modelId }.singleOrError()
    }

    private fun postAvatarConfig(file: String, bodyImage: String, modelId: String, userId: String): Observable<ModelDO> {
        val param = RxHttp.postForm("/virtual/upload")
                .addFile("file", File(file))
                .add("userId", userId, true)
        if (File(bodyImage).exists()) {
            param.addFile("picFile", File(bodyImage))
        }
        if (modelId.isNotEmpty()) {
            param.add("modelId", modelId, true)
        }
        return param
                .asResponse(ModelDO::class.java)
    }

}