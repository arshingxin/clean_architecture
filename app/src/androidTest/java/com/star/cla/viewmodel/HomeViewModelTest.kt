package com.star.cla.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.star.cla.base.BaseUITest
import com.star.cla.helpers.RxImmediateSchedulerRule
import com.star.cla.helpers.getOrAwaitValue
import com.star.cla.network.bus.NetStatusBus
import com.star.cla.ui.home.HomeViewModel
import com.star.data.di.*
import com.star.domain.model.DeviceInfoModel
import com.star.extension.fromJson
import io.mockk.every
import io.mockk.spyk
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : BaseUITest() {
    private val TEST_SN = "SMR000275"
    private val SHOW_ERROR_MSG = "無法取得最新設備資訊!"
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

    @Test
    fun test_net_disconnected_resume_local_success() {
        NetStatusBus.isConnected(false)
        //fake response
        val deviceInfoResponse = getDeviceInfoSuccessJson()
        var deviceInfo: DeviceInfoModel = Gson().fromJson(deviceInfoResponse)
        every { homeViewModel.deviceInfoUseCase.getLocalDeviceInfo() } returns Observable.just(
            deviceInfo
        )
        every { homeViewModel.deviceInfoUseCase.getRemoteDeviceInfo(TEST_SN) } returns Observable.just(
            deviceInfo
        )
        homeViewModel.deviceInfoModelLiveData.observeForever(spyk(Observer { }))
        homeViewModel.resume()
        assert(homeViewModel.deviceInfoModelLiveData.value != null)
        assertEquals(
            HomeViewModel.ResponseStatus.ShowError("${if (!NetStatusBus.peek()) "[網路未連線]" else ""}$SHOW_ERROR_MSG"),
            homeViewModel.deviceInfoModelLiveData.getOrAwaitValue()
        )
    }

    @Test
    fun test_net_disconnected_resume_local_fail() {
        NetStatusBus.isConnected(false)
        //fake response
        val deviceInfoResponse = getDeviceInfoSuccessJson()
        var deviceInfo: DeviceInfoModel = Gson().fromJson(deviceInfoResponse)
        every { homeViewModel.deviceInfoUseCase.getLocalDeviceInfo() } returns Observable.just(
            DeviceInfoModel()
        )
        every { homeViewModel.deviceInfoUseCase.getRemoteDeviceInfo(TEST_SN) } returns Observable.just(
            deviceInfo
        )
        homeViewModel.deviceInfoModelLiveData.observeForever(spyk(Observer { }))
        homeViewModel.resume()
        assert(homeViewModel.deviceInfoModelLiveData.value != null)
        assertEquals(
            HomeViewModel.ResponseStatus.Retry,
            homeViewModel.deviceInfoModelLiveData.getOrAwaitValue()
        )
    }

    @Test
    fun test_net_connected_resume_local_success_remote_success() {
        NetStatusBus.isConnected(true)
        //fake response
        val deviceInfoResponse = getDeviceInfoSuccessJson()
        var deviceInfo: DeviceInfoModel = Gson().fromJson(deviceInfoResponse)
        every { homeViewModel.deviceInfoUseCase.getLocalDeviceInfo() } returns Observable.just(
            deviceInfo
        )
        every { homeViewModel.deviceInfoUseCase.getRemoteDeviceInfo(TEST_SN) } returns Observable.just(
            deviceInfo
        )
        homeViewModel.deviceInfoModelLiveData.observeForever(spyk(Observer { }))
        homeViewModel.resume()
        assert(homeViewModel.deviceInfoModelLiveData.value != null)
        assertEquals(
            HomeViewModel.ResponseStatus.Success(deviceInfo),
            homeViewModel.deviceInfoModelLiveData.getOrAwaitValue()
        )
    }

    @Test
    fun test_net_connected_resume_local_success_remote_fail() {
        NetStatusBus.isConnected(true)
        //fake response
        val deviceInfoResponse = getDeviceInfoSuccessJson()
        var deviceInfo: DeviceInfoModel = Gson().fromJson(deviceInfoResponse)
        every { homeViewModel.deviceInfoUseCase.getLocalDeviceInfo() } returns Observable.just(
            deviceInfo
        )
        every { homeViewModel.deviceInfoUseCase.getRemoteDeviceInfo(TEST_SN) } returns Observable.just(
            DeviceInfoModel()
        )
        homeViewModel.deviceInfoModelLiveData.observeForever(spyk(Observer { }))
        homeViewModel.resume()
        assert(homeViewModel.deviceInfoModelLiveData.value != null)
        assertEquals(
            HomeViewModel.ResponseStatus.ShowError("${if (!NetStatusBus.peek()) "[網路未連線]" else ""}$SHOW_ERROR_MSG"),
            homeViewModel.deviceInfoModelLiveData.getOrAwaitValue()
        )
    }

    @Test
    fun test_net_connected_resume_local_fail_remote_success() {
        NetStatusBus.isConnected(true)
        //fake response
        val deviceInfoResponse = getDeviceInfoSuccessJson()
        var deviceInfo: DeviceInfoModel = Gson().fromJson(deviceInfoResponse)
        every { homeViewModel.deviceInfoUseCase.getLocalDeviceInfo() } returns Observable.just(
            DeviceInfoModel()
        )
        every { homeViewModel.deviceInfoUseCase.getRemoteDeviceInfo(TEST_SN) } returns Observable.just(
            deviceInfo
        )
        homeViewModel.deviceInfoModelLiveData.observeForever(spyk(Observer { }))
        homeViewModel.resume()
        assert(homeViewModel.deviceInfoModelLiveData.value != null)
        assertEquals(
            HomeViewModel.ResponseStatus.Success(deviceInfo),
            homeViewModel.deviceInfoModelLiveData.getOrAwaitValue()
        )
    }

    @Test
    fun test_net_connected_resume_local_fail_remote_fail() {
        NetStatusBus.isConnected(true)
        every { homeViewModel.deviceInfoUseCase.getLocalDeviceInfo() } returns Observable.just(
            DeviceInfoModel()
        )
        every { homeViewModel.deviceInfoUseCase.getRemoteDeviceInfo(TEST_SN) } returns Observable.just(
            DeviceInfoModel()
        )
        homeViewModel.deviceInfoModelLiveData.observeForever(spyk(Observer { }))
        homeViewModel.resume()
        assert(homeViewModel.deviceInfoModelLiveData.value != null)
        assertEquals(
            HomeViewModel.ResponseStatus.Retry,
            homeViewModel.deviceInfoModelLiveData.getOrAwaitValue()
        )
    }
    
    private fun getDeviceInfoSuccessJson() = getJson("device_info_success.json")
}