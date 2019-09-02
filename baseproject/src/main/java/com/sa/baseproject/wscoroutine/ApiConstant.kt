package com.sa.baseproject.wscoroutine

/**
 * Created by sa on 31/03/17.
 *
 * Web service related constant should be here.
 *
 */

object ApiConstant {


    const val NO_INTERNET_CONNECTION = "No internet connection."
    const val NO_INTERNET_CONNECTION_STATUS = "503"

    const val TIME_OUT_CONNECTION = "Cannot connect to server.\nPlease try again later."
    const val TIME_OUT_CONNECTION_STATUS = "504"

    const val SOMETHING_WRONG_ERROR = "Something went wrong!!\nPlease try again later."
    const val SOMETHING_WRONG_ERROR_STATUS = "505"

    const val OTHER_EXCEPTION = "Something went wrong!!\nPlease try again later."
    const val OTHER_EXCEPTION_STATUS = "505"

    const val IO_EXCEPTION = "We could not complete your request."
    const val IO_EXCEPTION_STATUS = "403"

    const val HEADER_AUTHORIZATION: String = "Authorization"
    const val HEADER_TOKEN: String = "token"
    const  val HTTP_BASE_URL = ""

    const  val HEADER_AUTHORIZATION_NAME: String = "x-auth-token"

    const  val HEADER_CONTENT_TYPE_NAME = ""
    const  val HEADER_CONTENT_TYPE_VALUE = ""


    val NO_INTERNET = "No Internet Connection Available"
    val TIMEOUT = "Connection Timeout"
    val SERVER_NOT_RESPONDING = "Server is not responding.Please try again later"
    val PARSE_ERROR = "Unable to parse response from server"
    val SOMETHING_WRONG = "Something went wrong.Please try again later"


    val HEADER_NEWS_API_KEY: String = "x-api-key"
    val NEWS_KEY = "4d91953e6d8e4cde947a0099cbe35cf9"


}
