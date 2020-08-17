package com.kotsu.malvina.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kotsu.malvina.model.data.account.source.AccountDataSource
import com.kotsu.malvina.model.data.account.source.AccountRepository
import com.kotsu.malvina.model.data.account.source.local.AccountLocalDataSource
import com.kotsu.malvina.model.data.account.source.remote.AccountRemoteDataSource
import com.kotsu.malvina.model.data.orders.source.OrdersDataSource
import com.kotsu.malvina.model.data.orders.source.OrdersRepository
import com.kotsu.malvina.model.data.orders.source.local.OrdersLocalDataSource
import com.kotsu.malvina.model.data.orders.source.remote.OrdersRemoteDataSource
import com.kotsu.malvina.model.data.storage.source.StorageDataSource
import com.kotsu.malvina.model.data.storage.source.StorageRepository
import com.kotsu.malvina.model.data.storage.source.remote.StorageRemoteDataSource
import com.kotsu.malvina.model.rest.ApiService
import com.kotsu.malvina.model.rest.AuthTokenInterceptor
import com.kotsu.malvina.model.rest.stub.StubApiService
import com.kotsu.malvina.model.room.AppDatabase
import com.kotsu.malvina.model.room.dao.AccountDao
import com.kotsu.malvina.model.schedulers.AppSchedulers
import com.kotsu.malvina.model.schedulers.Schedulers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

// TODO: consider move bindings for different data sources into different modules or files
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class RepositoryOrders

    @Qualifier
    @Retention(RUNTIME)
    annotation class LocalOrdersDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class RemoteOrdersDataSource

    @Singleton
    @RepositoryOrders
    @Provides
    fun provideOrdersRepository(
        @LocalOrdersDataSource localDataSource: OrdersDataSource,
        @RemoteOrdersDataSource remoteDataSource: OrdersDataSource
    ): OrdersDataSource {
        return OrdersRepository(
            localDataSource,
            remoteDataSource)
    }

    @Singleton
    @LocalOrdersDataSource
    @Provides
    fun provideOrdersLocalDataSource(): OrdersDataSource {
        return OrdersLocalDataSource()
    }

    @Singleton
    @RemoteOrdersDataSource
    @Provides
    fun provideOrdersRemoteDataSource(stubApiService: StubApiService): OrdersDataSource {
        return OrdersRemoteDataSource(stubApiService)
    }

    @Qualifier
    @Retention(RUNTIME)
    annotation class RepositoryStorage

    @Qualifier
    @Retention(RUNTIME)
    annotation class RemoteStorageDataSource

    @Singleton
    @RepositoryStorage
    @Provides
    fun provideStorageRepository(
        @RemoteStorageDataSource remoteDataSource: StorageDataSource
    ): StorageDataSource {
        return StorageRepository(
            remoteDataSource)
    }

    @Singleton
    @RemoteStorageDataSource
    @Provides
    fun provideStorageRemoteDataSource(stubApiService: StubApiService): StorageDataSource {
        return StorageRemoteDataSource(stubApiService)
    }

    @Qualifier
    @Retention(RUNTIME)
    annotation class RepositoryAccount

    @Qualifier
    @Retention(RUNTIME)
    annotation class LocalAccountDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class RemoteAccountDataSource

    @Singleton
    @RepositoryAccount
    @Provides
    fun provideAccountRepository(
        @LocalAccountDataSource localDataSource: AccountDataSource,
        @RemoteAccountDataSource remoteDataSource: AccountDataSource
    ): AccountDataSource {
        return AccountRepository(
            localDataSource,
            remoteDataSource)
    }

    @Singleton
    @LocalAccountDataSource
    @Provides
    fun provideAccountLocalDataSource(accountDao: AccountDao): AccountDataSource {
        return AccountLocalDataSource(accountDao)
    }

    @Singleton
    @RemoteAccountDataSource
    @Provides
    fun provideAccountRemoteDataSource(stubApiService: StubApiService): AccountDataSource {
        return AccountRemoteDataSource(stubApiService)
    }

    @Singleton
    @Provides
    fun provideSchedulers(): Schedulers {
        return AppSchedulers()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "malvina.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao {
        return appDatabase.accountDao()
    }

    @Singleton
    @Provides
    fun provideApiService(authTokenInterceptor: AuthTokenInterceptor): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(authTokenInterceptor)
            .build()

        return Retrofit.Builder()
            // add real base url here
            .baseUrl("https://kotsu.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideAuthTokenInterceptor(@ApplicationContext context: Context): AuthTokenInterceptor {
        return AuthTokenInterceptor(context)
    }

    @Provides
    fun provideStubApiService(): StubApiService {
        return StubApiService()
    }
}