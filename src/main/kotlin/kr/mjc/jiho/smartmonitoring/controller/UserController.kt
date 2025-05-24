package kr.mjc.jiho.smartmonitoring.controller

import jakarta.servlet.http.HttpSession
import kr.mjc.jiho.smartmonitoring.repository.user.User
import kr.mjc.jiho.smartmonitoring.repository.user.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping


@Controller
class UserController(val userRepository: UserRepository,
                     val passwordEncoder: PasswordEncoder) {

    companion object {
        private const val LANDING_PAGE = "/trafficLight/dashboard"
    }

    @GetMapping("/user/signup")
    fun pass(){}


    /*@PostMapping("/user/login")
    fun login(@RequestParam("username") username:String,
              @RequestParam("password") password:String, session: HttpSession):String{
        val user = userRepository.findByUsername(username)

        if(user != null && passwordEncoder.matches(password,user.password)){
            session.setAttribute("user", user)
            return "redirect:$LANDING_PAGE"
        }else{
            return "redirect:/user/signup?error"
        }
    }
    @GetMapping("/user/login")
    fun login():String{
        return "redirect:/"
    }*/

    /*@PostMapping("/user/logout")
    fun logout(session: HttpSession):String{
        session.invalidate()
        return "redirect:/"
    }*/
    @PostMapping("/user/signup")
    fun signup(user: User, session: HttpSession): String {
        val exists = userRepository.existsByUsername(user.username)
        if (!exists) {   // 이메일이 없음. 등록 진행
            user.apply {
                password = passwordEncoder.encode(user.password)
            }
            userRepository.save(user) // 등록 성공
            session.setAttribute("user", user)  // 로그인
            return "redirect:$LANDING_PAGE"
        } else {  // 이메일 존재. 등록 실패
            return "redirect:/user/signup?error"
        }
    }

}