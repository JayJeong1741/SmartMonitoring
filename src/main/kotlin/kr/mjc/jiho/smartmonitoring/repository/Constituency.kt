package kr.mjc.jiho.smartmonitoring.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serializable

@Entity
class Constituency : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) var cName:String? = null
    var tdyEmergency : Int? = 0
}