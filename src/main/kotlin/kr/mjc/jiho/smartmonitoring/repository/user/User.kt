package kr.mjc.jiho.smartmonitoring.repository.user
import jakarta.persistence.*
import kr.mjc.jiho.smartmonitoring.repository.constituency.Constituency
import java.io.Serializable

@Entity
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var id : Long = 0
    lateinit var username: String
    lateinit var password: String
    @ManyToOne
    @JoinColumn(name = "constituency_id") lateinit var constituencyId: Constituency


    override fun toString(): String =
        "User(id=$id, username='$username', constituencyId=$constituencyId)"
}