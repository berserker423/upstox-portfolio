package com.example.stocks.di

import android.content.Context
import androidx.room.Room
import com.example.stocks.data.DefaultHoldingRepo
import com.example.stocks.data.HoldingRepo
import com.example.stocks.data.local.HoldingDao
import com.example.stocks.data.local.StockDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: DefaultHoldingRepo): HoldingRepo
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app.applicationContext,
        StockDatabase::class.java,
        "Stocks"
    ).build()

    @Provides
    fun provideTaskDao(database: StockDatabase): HoldingDao = database.holdingDao()
}