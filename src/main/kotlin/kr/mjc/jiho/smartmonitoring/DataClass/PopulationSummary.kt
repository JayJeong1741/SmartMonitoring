package kr.mjc.jiho.smartmonitoring.DataClass

import java.sql.Date

data class PopulationSummary(
    val day: Date?, // NULL 가능성 대비
    val totalPopulation: Long // decimal(32,0) 매핑
)