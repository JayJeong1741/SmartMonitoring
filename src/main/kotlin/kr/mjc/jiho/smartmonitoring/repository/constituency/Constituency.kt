package kr.mjc.jiho.smartmonitoring.repository.constituency

import jakarta.persistence.*
import java.io.Serializable


@Entity
class Constituency : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
    @Column(name = "c_name")
    lateinit var cName: String

    @Column(name = "c_name_h")  // <- 요거 추가
    lateinit var cNameH: String

    override fun toString(): String =
        "User(id=$id, cName=$cName, cNameH='$cNameH')"
}