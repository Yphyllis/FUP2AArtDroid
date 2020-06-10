package com.faceunity.pta_art.evergrande.module.avatar.data

import java.io.Serializable

class AvatarDO(var modelName: String = "", val userDefault: Int, val modelId: String, val picFile: String) : Serializable

class AvatarItem(val virtualModel: List<AvatarDO>) : Serializable