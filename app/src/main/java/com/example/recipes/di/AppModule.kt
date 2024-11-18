package com.example.recipes.di

import com.example.recipes.navigation.NavigationSubGraphs
import com.example.search.ui.navigation.SearchFeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNavigationSubGraphs(
        searchFeatureApi: SearchFeatureApi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(searchFeatureApi)
    }

}