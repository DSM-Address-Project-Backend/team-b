package com.address.dsmproject.util

import java.time.LocalDateTime

// 9월 같은 경우 0을 붙여 09로 변경해 처리하기 위한 코드
fun Int.padStartValue(): String = this.toString().padStart(2, '0')

fun getCurrentYearAndMonth(): Pair<Int, String> {
    val currentDateTime = LocalDateTime.now()
    val year = currentDateTime.year
    val month = currentDateTime.monthValue.padStartValue()
    return Pair(year, month)
}
