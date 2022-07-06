package com.star.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.star.data.base.BaseUITest
import com.star.data.di.*
import com.star.data.repository_cache.LocalDeviceCache
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@RunWith(AndroidJUnit4::class)
class LocalDeviceCacheTest : BaseUITest() {
    private lateinit var localDeviceCache: LocalDeviceCache

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
    fun test_save_device_id_and_get_device_id() = runBlocking {
        localDeviceCache = LocalDeviceCache()
        localDeviceCache.saveDeviceId(verifyId)
        TestCase.assertEquals(localDeviceCache.getDeviceId(), verifyId.toString())
    }

    @Test
    fun test_save_device_content_and_get_device_content() = runBlocking {
        localDeviceCache = LocalDeviceCache()
        val fakeContent = getJson("device_info_success.json")
        localDeviceCache.saveDeviceInfo(fakeContent)
        val getContent = localDeviceCache.getDeviceInfo().blockingFirst()
        TestCase.assertNotNull(getContent)
        TestCase.assertEquals(getContent, fakeContent)
    }
}