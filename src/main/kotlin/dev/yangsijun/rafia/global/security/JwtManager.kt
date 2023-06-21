package dev.yangsijun.rafia.global.security

import dev.yangsijun.rafia.domain.user.domain.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtManager {
    @Value("\${jwt.secret-key}")
    val secretKey: String = ""

    @Value("\${jwt.expiration}")
    val expiration: Long = 0L

    val algorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    @PostConstruct
    fun check() {
        if ((secretKey == "") or (expiration == 0L)) throw IllegalArgumentException("expiration must number")
        // 근데 이거 동작 안함 ㅋㅋ;;
    }

    fun generateJwtToken(user: User): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(user.id.toString())
            .setHeader(createHeader())
            .setClaims(createClaims(user))
            .setExpiration(Date(now.time + this.expiration)) // 만료일
            .signWith(getSignInKey(), algorithm)
            .compact()
    }

    private fun createHeader(): Map<String, Any>? {
        val header: MutableMap<String, Any> = HashMap()
        header["typ"] = "JWT"
        header["alg"] = algorithm.name
        header["regDate"] = System.currentTimeMillis()
        return header
    }

    private fun getSignInKey(): Key? {
        val keyBytes = Decoders.BASE64.decode(this.secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    /**
     * 클레임(Claim)을 생성한다.
     *
     * @param user 토큰을 생성하기 위한 계정 정보를 담은 객체
     * @return Map<String></String>, Object> 클레임(Claim)
     */
    private fun createClaims(user: User): Map<String, Any>? {
        val claims: MutableMap<String, Any> = HashMap()
        claims["id"] = user.id
        claims["name"] = user.name
        return claims
    }


}