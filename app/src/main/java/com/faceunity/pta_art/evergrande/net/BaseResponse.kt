package com.faceunity.pta_art.evergrande.net

import java.io.Serializable

data class BaseResponse<T>(val msgCode: Int = 0, val msg: String = "", var data: T? = null) : Serializable

data class BaseResult(val result: Boolean) : Serializable

data class ModelDO(val modelId: String) : Serializable
