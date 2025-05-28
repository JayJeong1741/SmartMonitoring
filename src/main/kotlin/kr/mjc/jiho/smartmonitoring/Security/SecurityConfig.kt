package kr.mjc.jiho.smartmonitoring.Security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val loginSuccessHandler: LoginSuccessHandler,
                     private val rememberMeAuthenticationSuccessHandler: RememberMeAuthenticationSuccessHandler) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers("/", "/css/**", "/js/**").permitAll()
                it.requestMatchers("/trafficLight/**").authenticated()
                it.anyRequest().permitAll()
            }
            .formLogin { form ->
                form
                    .loginPage("/")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/trafficLight/dashboard", true)
                    .successHandler(loginSuccessHandler)
                    .failureUrl("/main?error")
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("remember-me")
                    .permitAll()
            }
            .rememberMe { rememberMe ->
                rememberMe
                    .key("secret-key")
                    .tokenValiditySeconds(7 * 24 * 60 * 60) // 7일
                    .authenticationSuccessHandler(rememberMeAuthenticationSuccessHandler)
            }
            .addFilterBefore(RedirectAuthenticatedUserFilter(), DefaultLoginPageGeneratingFilter::class.java)
            .csrf{csrf -> csrf.disable()};

        return http.build()
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}