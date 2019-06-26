package com.sa.baseproject.eventbus

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

object RxBus {

        private val publisher = PublishSubject.create<Any>()

        /*
         * event  : specific class for listen
        * error :  it used for handle error when data publish  For ex. this error :  io.reactivex.exceptions.OnErrorNotImplementedException
        * Consumer :   io.reactivex.functions.Consumer
          */
        fun publish(event : Any, error : Consumer<Throwable>) {
                publisher.onNext(event)
                publisher.doOnError(error)
                publisher.doOnError {

                }
        }

        /*
          *  Listen should return an Observable and not the publisher
          * Using ofType we filter only events that match that class type
          */
        fun <T> listen(eventType : Class<T>) : Observable<T> = publisher.ofType(eventType)

}