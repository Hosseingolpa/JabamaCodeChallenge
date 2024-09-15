package com.jabama.challenge.data.mock

object MockUtil {
    // todo (this file should remove and this module should use MockUtil in Domain module)
    fun getRandomString(extra: String = ""): String {
        return "randomString$extra"
    }
}