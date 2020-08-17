package com.kotsu.malvina.model.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable


abstract class BaseDao<T> {

    @Insert
    abstract fun insert(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertIgnoreConflicts(items: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertIgnoreConflicts(item: T): Long

    @Insert()
    abstract fun insertReactive(item: T): Completable

    @Update
    abstract fun update(item: T)

    @Update
    abstract fun updateReactive(item: T): Completable

    @Update
    abstract fun update(items: List<T>)

    open fun upsert(item: T) {
        val id = insertIgnoreConflicts(item)
        if (id == -1L) {
            update(item)
        }
    }

    open fun upsert(items: List<T>) {

        val insertResult = insertIgnoreConflicts(items)
        val updateList = arrayListOf<T>()

        insertResult.forEachIndexed { index, rowInsertResult ->
            if (rowInsertResult == -1L) {
                updateList.add(items.get(index))
            }
        }

        if (updateList.isNotEmpty()) {
            update(updateList)
        }
    }
}