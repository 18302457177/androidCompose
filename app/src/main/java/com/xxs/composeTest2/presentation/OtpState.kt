package com.xxs.composeTest2.presentation

data class OtpState(
    val code:List<Int?> = (1..4).map {null},
    val focusedIndex:Int? = null
)