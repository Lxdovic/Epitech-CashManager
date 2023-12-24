package com.example.moulamanagerclient.di

import com.example.moulamanagerclient.data.network.ApiService
import com.example.moulamanagerclient.data.repositories.products.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {
	@Provides
	@Singleton
	fun provideProductRepository(apiService: ApiService): ProductRepository {
		return ProductRepository(apiService)
	}
}