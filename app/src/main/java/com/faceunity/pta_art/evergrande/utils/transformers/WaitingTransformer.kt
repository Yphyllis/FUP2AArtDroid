package com.faceunity.pta_art.evergrande.utils.transformers

import io.reactivex.*
import io.reactivex.annotations.NonNull
import org.reactivestreams.Publisher

/**
 * Created by yanghe on 2019-06-01.
 * <p>
 */
class WaitingTransformer<T> : ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>,
    MaybeTransformer<T, T>, CompletableTransformer {

    override fun apply(@NonNull upstream: Observable<T>): ObservableSource<T> {
        return upstream.doOnSubscribe { Waiting.show() }.doFinally { Waiting.hide() }
    }

    override fun apply(@NonNull upstream: Flowable<T>): Publisher<T> {
        return upstream.doOnSubscribe { Waiting.show() }.doFinally { Waiting.hide() }
    }

    override fun apply(@NonNull upstream: Completable): CompletableSource {
        return upstream.doOnSubscribe { Waiting.show() }.doFinally { Waiting.hide() }
    }

    override fun apply(@NonNull upstream: Maybe<T>): MaybeSource<T> {
        return upstream.doOnSubscribe { Waiting.show() }.doFinally { Waiting.hide() }
    }

    override fun apply(@NonNull upstream: Single<T>): SingleSource<T> {
        return upstream.doOnSubscribe { Waiting.show() }.doFinally { Waiting.hide() }
    }
}