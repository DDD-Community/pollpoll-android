/*
 * Designed and developed by 2022 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ddd.pollpoll.core.network

import com.ddd.pollpoll.core.network.retrofit.PollAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class PollPollApiTest : ApiAbstract<PollAPI>() {

    private lateinit var service: PollAPI

//    @Before
//    fun initService() {
//        service = createService(PollAPI::class.java)
//    }

//    @Throws(IOException::class)
//    @Test
//    fun `로그인 API 제대로 파싱되는지 테스트`() = runTest {
//        enqueueResponse("/LoginResponse.json")
//        val response = service.loginGoogle("테스뚜")
//        val responseBody = requireNotNull((response.data))
//        assertThat(responseBody.accessToken, `is`("오빤 강남스따일"))
//    }
//}
