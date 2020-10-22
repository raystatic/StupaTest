package com.example.stupatest.di

import android.content.Context
import androidx.room.Room
import com.example.stupatest.db.StupdDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        StupdDB::class.java,
        "STUPA_DB"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideUserDao(db:StupdDB) = db.getUserDao()

    @Singleton
    @Provides
    fun provideShapeDao(db: StupdDB) = db.getShapeDao()

}