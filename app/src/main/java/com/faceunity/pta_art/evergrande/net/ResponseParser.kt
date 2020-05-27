package com.faceunity.pta_art.evergrande.net

import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.parse.AbstractParser
import java.lang.reflect.Type

@Parser(name = "Response")
open class ResponseParser<T> : AbstractParser<T> {

    protected constructor() : super()
    constructor(type: Type) : super(type)

    override fun onParse(response: Response): T {
        val type: Type = ParameterizedTypeImpl[BaseResponse::class.java, mType]
        val data: BaseResponse<T> = convert(response, type)
        var t = data.data
//        if (t == null && mType == String::class.java) {
//            t = data.msg as T
//        }
        if (data.msgCode != 200 || t == null) {
            throw NetException(data.msgCode, data.msg)
        }
        return t
    }
}