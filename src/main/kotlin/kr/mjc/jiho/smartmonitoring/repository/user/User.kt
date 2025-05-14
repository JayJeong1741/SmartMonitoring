package kr.mjc.jiho.smartmonitoring.repository.user
import jakarta.persistence.*
import kr.mjc.jiho.smartmonitoring.repository.constituency.Constituency
import java.io.Serializable
import java.math.BigDecimal

@Entity
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var id : Long = 0
    lateinit var username: String
    lateinit var password: String
    @ManyToOne
    @JoinColumn(name = "constituency_id") lateinit var constituencyId: Constituency
    val userCode : Int = 0
    val lat: BigDecimal? = null // decimal(9,6), nullable
    val lng: BigDecimal? = null // decimal(9,6), nullable


    override fun toString(): String =
        "User(id=$id, username='$username', constituencyId=$constituencyId, rangeCode=$userCode ,lat=$lat, lng=$lng)"
}