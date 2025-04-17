package kr.mjc.jiho.smartmonitoring.repository.user
import jakarta.persistence.*
import kr.mjc.jiho.smartmonitoring.repository.Constituency
import java.io.Serializable

@Entity
class User : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var id : Long = 0
    lateinit var username: String
    lateinit var password: String
    @ManyToOne
    @JoinColumn(name = "c_name") lateinit var constituency: Constituency


    override fun toString(): String =
        "User(id=$id, username='$username', constituency=$constituency)"
}