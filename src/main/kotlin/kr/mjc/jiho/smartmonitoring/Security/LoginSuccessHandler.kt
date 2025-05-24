package kr.mjc.jiho.smartmonitoring.Security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import kr.mjc.jiho.smartmonitoring.repository.user.UserRepository


@Component
class LoginSuccessHandler(
    private val userRepository: UserRepository
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val userDetails = authentication.principal as UserDetails
        val user = userRepository.findByUsername(userDetails.username)

        // 세션에 사용자 정보 저장
        if (user != null) {
            request.session.setAttribute("user", user)
        }
        // 로그인 성공 후 리디렉션
        response.sendRedirect("/main/trafficLight/dashboard")
    }
}