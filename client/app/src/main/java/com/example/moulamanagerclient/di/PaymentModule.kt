package com.example.moulamanagerclient.di

import com.example.moulamanagerclient.data.network.ApiService
import com.example.moulamanagerclient.data.repositories.payment.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentModule {

	@Provides
	@Singleton
	fun providePaymentRepository(apiService: ApiService): PaymentRepository {
		return PaymentRepository(apiService)
	}
}