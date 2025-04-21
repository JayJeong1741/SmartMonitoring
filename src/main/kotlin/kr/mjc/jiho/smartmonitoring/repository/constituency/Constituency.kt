package kr.mjc.jiho.smartmonitoring.repository.constituency

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serializable


@Entity
class Constituency : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
    lateinit var cName: String


    override fun toString(): String =
        "User(id=$id, cName=$cName)"

}