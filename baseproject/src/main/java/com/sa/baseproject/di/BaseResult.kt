package com.sa.baseproject.di

open class BaseResult {
    var status: Boolean = false
    var error: Error? = null
}

data class Error(val code: String?, val message: String?)