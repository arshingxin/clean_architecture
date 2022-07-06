package com.star.domain.usecase

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.star.data.customconst.PrefsConst
import com.star.data.di.*
import com.star.domain.base.BaseUITest
import com.star.domain.di.useCaseModule
import com.star.extension.set
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
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
class DeviceInfoUseCaseTest : BaseUITest() {
    private lateinit var deviceInfoUseCase: DeviceInfoUseCase
    private val appSharedPreferences : SharedPreferences by inject(named(PrefsConst.App.NAME))

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val verifyId = 6425

    @Before
    fun start() {
        super.setUp()
        //Start Koin with required dependencies
        startKoin {
            androidContext(getApplicationContext())
            modules(listOf(
                networkModule,
                databaseModule,
                remoteRepoModule,
                preferencesModule,
                localCacheModule,
                useCaseModule
            ))
        }
    }

    @Test
    fun test_local_device_info_returns_expected_value() = runBlocking {
        appSharedPreferences[PrefsConst.App.DEVICE_INFO_CONTENT] = getJson("device_info_success.json")
        deviceInfoUseCase = DeviceInfoUseCase()
        val dataReceived = deviceInfoUseCase.getLocalDeviceInfo().blockingFirst()
        assertNotNull(dataReceived)
        assertEquals(dataReceived.id, verifyId)
    }

    @Test
    fun test_local_device_info_returns_fail() = runBlocking {
        appSharedPreferences[PrefsConst.App.DEVICE_INFO_CONTENT] = ""
        deviceInfoUseCase = DeviceInfoUseCase()
        val dataReceived = deviceInfoUseCase.getLocalDeviceInfo().blockingFirst()
        assertNotNull(dataReceived)
        assertEquals(dataReceived.id, -1)
    }

    @Test
    fun test_remote_device_info_returns_success() = runBlocking {
        deviceInfoUseCase = DeviceInfoUseCase()
        val dataReceived = deviceInfoUseCase.getRemoteDeviceInfo("SMR000275").blockingFirst()
        assertNotNull(dataReceived)
        assertEquals(dataReceived.id, verifyId)
    }

    @Test
    fun test_remote_device_info_returns_fail() = runBlocking {
        deviceInfoUseCase = DeviceInfoUseCase()
        val dataReceived = deviceInfoUseCase.getRemoteDeviceInfo("").blockingFirst()
        assertNotNull(dataReceived)
        assertEquals(dataReceived.id, -1)
    }
}