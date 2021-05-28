package com.trista.kotlintest.http

data class BaseResult<T>(
    val `data`: T,
    val errorCode: Int,
    val errorMsg: String
)