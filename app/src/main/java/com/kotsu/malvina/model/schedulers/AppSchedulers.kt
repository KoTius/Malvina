package com.kotsu.malvina.model.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers


class AppSchedulers : Schedulers {

    override fun computation(): Scheduler = io.reactivex.schedulers.Schedulers.computation()

    override fun io(): Scheduler = io.reactivex.schedulers.Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    companion object {

        private var INSTANCE: AppSchedulers? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(AppSchedulers::class.java) {
                INSTANCE ?: AppSchedulers()
                    .also {
                        INSTANCE = it
                    }
            }
    }
}