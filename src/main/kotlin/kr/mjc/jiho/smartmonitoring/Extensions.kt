package kr.mjc.jiho.smartmonitoring

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.thymeleaf.context.WebContext
import org.thymeleaf.web.servlet.JakartaServletWebApplication
import java.net.URLEncoder
import java.nio.charset.Charset
import java.security.SecureRandom
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Base64
import kotlin.math.*


/** queryString을 포함한 full url */
val HttpServletRequest.fullUrl: String
    get() = if (queryString == null) requestURL.toString() else "$requestURL?$queryString"

/** encode url */
val String.urlEncoded: String
    get() = URLEncoder.encode(this, Charset.defaultCharset())

/** 날짜를 "yyyy-MM-dd HH:mm:ss"로 포맷하는 formatter */
val formatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

/** 날짜를 formatter로 포맷한다. */
val LocalDateTime.formatted: String get() = this.format(formatter)

/** request와 response로 Thymeleaf webContext build */
fun HttpServlet.webContext(req: HttpServletRequest, resp: HttpServletResponse) =
    WebContext(JakartaServletWebApplication.buildApplication(servletContext)
        .buildExchange(req, resp))

private val secureRandom = SecureRandom()
private val base64Encoder = Base64.getEncoder()


fun generateRandomString(length: Int): String {
    val bytes = ByteArray(length)
    secureRandom.nextBytes(bytes)
    return base64Encoder.encodeToString(bytes)
}