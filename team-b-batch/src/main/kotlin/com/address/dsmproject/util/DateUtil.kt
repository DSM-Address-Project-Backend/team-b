package com.address.dsmproject.util

import java.time.LocalDateTime

// 9월 같은 경우 0을 붙여 09로 변경해 처리하기 위한 코드
fun Int.padStartValue(): Int = this.toString().padStart(2, '0').toInt()

fun getCurrentYearAndMonth(): Pair<Int, Int> {
    val currentDateTime = LocalDateTime.now()
    val year = currentDateTime.year
    val month = currentDateTime.monthValue.padStartValue()
    return Pair(year, month)
}
