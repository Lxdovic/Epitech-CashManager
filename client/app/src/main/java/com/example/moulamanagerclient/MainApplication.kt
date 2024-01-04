package com.example.moulamanagerclient

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.stripe.android.PaymentConfiguration

@HiltAndroidApp
class MainApplication: Application() {
	override fun onCreate() {
		super.onCreate()
		PaymentConfiguration.init(
			applicationContext,
			BuildConfig.STRIPE_PUBLISHABLE_KEY
		)
	}
}