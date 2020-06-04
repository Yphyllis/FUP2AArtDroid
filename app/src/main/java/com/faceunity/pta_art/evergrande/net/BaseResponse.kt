package com.faceunity.pta_art.evergrande.net

data class BaseResponse<T>(val msgCode: Int = 0, val msg: String = "", var data: T? = null)

data class BaseResult(val result: Boolean)

data class ModelDO(val modelId: String)
