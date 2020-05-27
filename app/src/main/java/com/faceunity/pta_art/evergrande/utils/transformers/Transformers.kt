package com.faceunity.pta_art.evergrande.utils.transformers

import io.reactivex.Completable
import java.util.concurrent.CancellationException

object Transformers {

    /**
     * 异步 do not use in Dao [data] layer
     */
    fun <T> async(): AsyncTransformer<T> {
        return AsyncTransformer()
    }

    fun <T> waiting(): WaitingTransformer<T> {
        return WaitingTransformer()
    }

    fun <T> asyncAndWaiting(): AsyncAndWaitingTransformer<T> {
        return AsyncAndWaitingTransformer()
    }

    /** 统一处理允许取消的流，防止 rxlifecycle 自动解绑抛出 cancellation exp 没有捕获发生崩溃 */
    fun Completable.allowCancel(): Completable {
        return this.onErrorComplete { it is CancellationException }
    }

}

