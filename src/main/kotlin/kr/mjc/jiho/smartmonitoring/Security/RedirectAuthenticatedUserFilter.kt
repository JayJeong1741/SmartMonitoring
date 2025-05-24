package kr.mjc.jiho.smartmonitoring.Security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class RedirectAuthenticatedUserFilter : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        if(httpRequest.requestURI == "/main/"){

            try {
                val authentication = SecurityContextHolder.getContext().authentication
                // 인증된 사용자가 anonymousUser가 아닌 경우
                if (authentication != null && authentication.isAuthenticated && authentication.principal != "anonymousUser") {
                    httpResponse.sendRedirect("/main/trafficLight/dashboard")
                    return
                }
            }catch (e: Exception){
                println(e.message)
            }

        }
        chain.doFilter(request, response)
        }
}