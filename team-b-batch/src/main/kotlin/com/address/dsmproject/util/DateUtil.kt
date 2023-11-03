package com.address.dsmproject.util

import java.time.LocalDate

// 9월 같은 경우 0을 붙여 09로 변경해 처리하기 위한 코드
fun Int.padStartValue(): String = this.toString().padStart(2, '0')

fun targetYearAndMonth(): Pair<Int, String> {
    val currentDate = LocalDate.now().minusMonths(2)
    val year = currentDate.year
    val month = currentDate.monthValue.padStartValue()
    return Pair(year, month)
}
