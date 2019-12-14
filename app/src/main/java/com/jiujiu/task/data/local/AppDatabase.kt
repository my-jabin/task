package com.jiujiu.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.jiujiu.task.data.local.dao.TripDao
import com.jiujiu.task.data.model.Converters
import com.jiujiu.task.data.model.Trip

@Database(entities = [Trip::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
}
