package com.star.cla.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.star.cla.base.BaseUITest
import com.star.cla.helpers.RxImmediateSchedulerRule
import com.star.cla.ui.home.HomeViewModel
import com.star.data.di.*
import io.mockk.spyk
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeFragmentTest : BaseUITest() {
    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun start() {
        super.setUp()
        homeViewModel = spyk(HomeViewModel(), recordPrivateCalls = true)
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                networkModule,
                databaseModule,
                remoteRepoModule,
                preferencesModule,
                localCacheModule
            )
        }
    }
}