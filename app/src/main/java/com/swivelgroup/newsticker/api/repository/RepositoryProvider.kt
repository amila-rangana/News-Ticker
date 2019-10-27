package com.swivelgroup.newsticker.api.repository

object RepositoryProvider {

    fun provideNewsRepository(): NewsRepository{
        return NewsRepositoryImpl()
    }
}