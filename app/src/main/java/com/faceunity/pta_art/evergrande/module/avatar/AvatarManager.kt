package com.faceunity.pta_art.evergrande.module.avatar

import com.faceunity.pta_art.entity.AvatarPTA
import com.faceunity.pta_art.evergrande.module.avatar.data.AvatarFileType
import com.faceunity.pta_art.evergrande.module.avatar.data.AvatarService
import com.faceunity.pta_art.evergrande.module.login.data.UserManager
import com.faceunity.pta_art.evergrande.utils.ConfigFileUtil
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

object AvatarManager {

    val service = AvatarService()

    var newAvatarFileDir = ""   // 简单搞搞，创建新角色时，生成的文件先全部放到此文件夹下

    fun createAvatarStart() {
        newAvatarFileDir = UserManager.getUserId() + System.currentTimeMillis()
    }

    fun createAvatarComplete() {
        newAvatarFileDir = ""
    }

    fun createOrUpdateAvatar(avatarPTA: AvatarPTA): Single<AvatarPTA> {
        // 文件前缀，userid 首末字符 +时间戳，保证文件名唯一(为啥要userId首末字符串，接口貌似因为文件名太长不收？？)
        val fileSuffix = getShortUserId() + System.currentTimeMillis()
        val newAvatar = avatarPTA.clone()
        return postHeadBundle(newAvatar, fileSuffix)
                .andThen(postHairBundle(newAvatar, fileSuffix))
//                .andThen(postAvatar(newAvatar, fileSuffix))
                .andThen(postConfig(newAvatar))
                .doOnSuccess {
                    avatarPTA.updateNewServerAvatar(newAvatar)
                }
                .map {
                    newAvatar.modelId = it
                    newAvatar
                }
    }

    private fun postConfig(avatarPTA: AvatarPTA): Single<String> {
        val configPath = avatarPTA.bundleDir + "/config.json"
        return Completable.create {
            val fileJson = Gson().toJson(avatarPTA)
            ConfigFileUtil.saveConfigFile(configPath, fileJson)
            val content = ConfigFileUtil.readConfigFile(configPath)
            println("content is $content")
            it.onComplete()
        }.andThen(service.postAvatarConfig(configPath, avatarPTA.originPhoto, avatarPTA.modelId))
                .doOnSuccess { avatarPTA.configPath = configPath }
    }

    private fun postHeadBundle(avatarPTA: AvatarPTA, fileSuffix: String): Completable {
        val headFileName = fileSuffix + AvatarFileType.HEAD.fileNameSuffix
        val newFile = avatarPTA.bundleDir + File.separator + headFileName
        val headFile = avatarPTA.headFile
        return service.postHeaBundle(headFile, headFileName)
                .doOnComplete {
                    avatarPTA.headFile = headFileName
                }
        // todo 应该在下载新的 bundle 时去更新它
    }

    private fun postHairBundle(avatarPTA: AvatarPTA, filePrefix: String): Completable {
        val hairFileName = filePrefix + AvatarFileType.HAIR.fileNameSuffix
        val hairFile = avatarPTA.hairFile
        return service.postHairBundle(hairFile, hairFileName)
                // todo 应该在下载新的 bundle 时去更新它
                .doOnComplete {
                    avatarPTA.hairFile = hairFileName
                }
    }

    private fun postAvatar(avatarPTA: AvatarPTA, filePrefix: String): Completable {
        val avatarFileName = filePrefix + AvatarFileType.AVATAR.fileNameSuffix
        val avatarFile = avatarPTA.originPhoto
        return service.postAvatar(avatarFile, avatarFileName)
//                .doOnComplete { avatarPTA.originPhoto = avatarFileName }
    }

    fun getUserId(): String {
        return UserManager.getUserId()
    }

    fun getShortUserId(): String {
        return getUserId().first().toString() + getUserId().last().toString()
    }

}