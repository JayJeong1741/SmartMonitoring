package kr.mjc.jiho.smartmonitoring.DataClass

import java.time.LocalDate

data class PopulationSummary(
    val day : LocalDate,
    val totalPopulation : Int
)
