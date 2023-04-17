package com.ddd.pollpoll.core.network.api

import com.ddd.pollpoll.core.network.ApiAbstract
import com.ddd.pollpoll.core.network.handle.executeHandle
import com.ddd.pollpoll.core.network.model.LoginRequest
import com.ddd.pollpoll.core.network.model.LoginResponse
import com.ddd.pollpoll.core.network.retrofit.PollAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LoginApiTest : ApiAbstract<PollAPI>() {
    private lateinit var service: PollAPI

    @BeforeEach
    fun initService() {
        service = createService(PollAPI::class.java)
    }

    @Nested
    @DisplayName("Poll API  Login 데이터가 200 떨어졌을때")
    inner class LoginDataSuccess {
        @BeforeEach
        fun setup() {
            enqueueResponse("/login/LoginResponse.json")
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        @DisplayName("데이터 파싱과 응답코드가 정상적으로 반환")
        fun verifyLoggedIn() = runTest {
            val response = service.loginGoogle(token = LoginRequest(idToken = "laudem"))
            Assertions.assertTrue(response.code() == 200)
            val parsingData = response.executeHandle()

            Assertions.assertEquals(
                parsingData,
                LoginResponse(accessToken = "오빤 강남스따일"),
            )
        }
    }
}
