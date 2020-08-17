package com.kotsu.malvina.model.schedulers

import io.reactivex.Scheduler


interface Schedulers {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}