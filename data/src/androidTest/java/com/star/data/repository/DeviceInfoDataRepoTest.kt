package com.star.data.repository

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.star.data.base.BaseUITest
import com.star.data.customconst.PrefsConst
import com.star.data.di.*
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

@RunWith(AndroidJUnit4::class)
class DeviceInfoDataRepoTest : BaseUITest() {
    private lateinit var deviceInfoDataRepo: DeviceInfoDataRepo
    private val appSharedPreferences : SharedPreferences by inject(named(PrefsConst.App.NAME))

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val verifyId = 6425

    @Before
    fun start() {
        super.setUp()
        //Start Koin with required dependencies
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(listOf(
                networkModule,
                databaseModule,
                remoteRepoModule,
                preferencesModule,
                localCacheModule
            ))
        }
    }

    @Test
    fun test_remote_device_info_returns_success() = runBlocking {
        deviceInfoDataRepo = DeviceInfoDataRepo()
        val dataReceived = deviceInfoDataRepo.getDeviceInfo("SMR000275").blockingFirst()
        TestCase.assertNotNull(dataReceived)
        TestCase.assertEquals(dataReceived.id, verifyId)
    }

    @Test
    fun test_remote_device_info_returns_fail() = runBlocking {
        deviceInfoDataRepo = DeviceInfoDataRepo()
        val dataReceived = deviceInfoDataRepo.getDeviceInfo("").blockingFirst()
        TestCase.assertNotNull(dataReceived)
        TestCase.assertEquals(dataReceived.id, -1)
    }
}