package kr.mjc.jiho.smartmonitoring.Security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.mjc.jiho.smartmonitoring.repository.user.UserRepository
import org.springframework.security.authentication.RememberMeAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class RememberMeAuthenticationSuccessHandler(
    private val userRepository: UserRepository
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        // Remember-me 인증인지 확인
        if (authentication is RememberMeAuthenticationToken) {
            try {
                val username = authentication.name
                // DB에서 user 정보 조회
                val user = userRepository.findByUsername(username)
                // 세션에 user 정보 저장
                val session = request.session
                session.setAttribute("user", user)
                if (user != null) {
                    println("Remember-me 자동 로그인: ${user.username}, 세션에 저장 완료")
                }
            }catch (e:Exception){
                println(e.message)
            }
            response.sendRedirect("/main/trafficLight/dashboard")
        }
        // 기본 리다이렉트는 스프링 시큐리티가 처리
    }
}