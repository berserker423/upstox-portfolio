package com.example.shared_test.di

import android.content.Context
import androidx.room.Room
import com.example.stocks.data.local.StockDatabase
import com.example.stocks.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseTestModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): StockDatabase {
        return Room
            .inMemoryDatabaseBuilder(context.applicationContext, StockDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}