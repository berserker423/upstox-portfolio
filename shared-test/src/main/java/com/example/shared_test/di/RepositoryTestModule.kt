package com.example.shared_test.di

import com.example.shared_test.data.FakeRepo
import com.example.stocks.data.HoldingRepo
import com.example.stocks.di.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object RepositoryTestModule {

    @Singleton
    @Provides
    fun provideTasksRepository(): HoldingRepo {
        return FakeRepo()
    }
}