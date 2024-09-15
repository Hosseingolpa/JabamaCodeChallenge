package com.jabama.challenge.data.cache.auth

import android.content.SharedPreferences
import com.jabama.challenge.data.cache.ACCESS_TOKEN
import com.jabama.challenge.data.mock.MockUtil
import com.jabama.challenge.data.test.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class AuthenticationCacheImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var sharedPreferences: SharedPreferences

    private fun createAuthenticationCache(): AuthenticationCacheImpl {
        return AuthenticationCacheImpl(
            sharedPreferences = sharedPreferences
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when saveAccessToken call with value, then should putString method call with same value`() {
        val fakeAccessToken = MockUtil.getRandomString()

        val cache = createAuthenticationCache()
        cache.saveAccessToken(value = fakeAccessToken)

        verify {
            sharedPreferences.edit().apply {
                putString(ACCESS_TOKEN, fakeAccessToken)
            }.apply()
        }
    }

    @Test
    fun `when getAccessToken call and return value, then cache should return same value`() {
        val fakeAccessToken = MockUtil.getRandomString()
        mockGetString(key = ACCESS_TOKEN, returnValue = fakeAccessToken)

        val cache = createAuthenticationCache()
        val result = cache.getAccessToken()

        assertEquals(fakeAccessToken, result)
    }

    @Test
    fun `when getAccessToken call and return null, then cache should return same null`() {
        val fakeAccessToken: String? = null
        mockGetString(key = ACCESS_TOKEN, returnValue = fakeAccessToken)

        val cache = createAuthenticationCache()
        val result = cache.getAccessToken()

        assertEquals(fakeAccessToken, result)
    }

    private fun mockGetString(
        key: String,
        returnValue: String?
    ) {
        every { sharedPreferences.getString(key, null) } returns returnValue
    }
}