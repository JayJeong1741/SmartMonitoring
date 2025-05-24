package kr.mjc.jiho.smartmonitoring.cookie

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class UsernameCookieService {

    companion object {
        private const val COOKIE_NAME = "savedUsername"
        private const val COOKIE_MAX_AGE = 30 * 24 * 60 * 60 // 30일
    }

    fun saveUsername(response: HttpServletResponse, username: String) {
        val cookie = Cookie(COOKIE_NAME, username).apply {
            maxAge = COOKIE_MAX_AGE
            isHttpOnly = true
            secure = true // HTTPS 환경에서만
            path = "/"
        }
        response.addCookie(cookie)
    }

    fun getSavedUsername(request: HttpServletRequest): String? {
        return request.cookies?.find { it.name == COOKIE_NAME }?.value
    }

    fun clearUsername(response: HttpServletResponse) {
        val cookie = Cookie(COOKIE_NAME, "").apply {
            maxAge = 0
            path = "/"
        }
        response.addCookie(cookie)
    }
}