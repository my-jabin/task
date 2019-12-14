package com.jiujiu.task.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.jiujiu.task.TaskApp
import com.jiujiu.task.data.local.AppDatabase
import com.jiujiu.task.data.local.dao.TripDao
import com.jiujiu.task.data.remote.MockRemoteService
import com.jiujiu.task.data.remote.RemoteService
import com.jiujiu.task.di.scope.DatabaseInfo
import com.jiujiu.task.di.scope.PreferenceInfo
import com.jiujiu.task.util.AppConstant
import com.jiujiu.task.worker.PrePopulateDataWorker
import com.jiujiu.task.worker.RemoteSyncWorker
import dagger.Module
import dagger.Provides
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, WorkerBindingModule::class])
class AppModule {

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstant.DATABASENAME
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstant.PREFERENCENAME
    }

    @Provides
    @Singleton
    fun provideContext(application: TaskApp): Context {
        return application
    }

    @Provides
    fun provideCurrentLocal(context: Context): Locale {
        return context.resources.configuration.locales[0]
    }

    @Provides
    @Singleton
    fun providePlace(context: Context): PlacesClient {
        return Places.createClient(context)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context, @DatabaseInfo databaseName: String): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, databaseName).addCallback(
                // in memory database
//        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        OneTimeWorkRequest.Builder(PrePopulateDataWorker::class.java).build().apply {
                            WorkManager.getInstance(context).enqueue(this)
                        }
                    }
                }
        )
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): TripDao {
        return database.tripDao()
    }

    @Provides
    @Singleton
    fun provideRemoteService(): RemoteService {
        return MockRemoteService()
    }

    @Provides
    @Singleton
    fun provideRemoteSyncWorker(): WorkRequest {
        val constrains = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        return PeriodicWorkRequest.Builder(RemoteSyncWorker::class.java, 1, TimeUnit.HOURS)
                .setConstraints(constrains)
                .build()
    }


}

