package com.sa.baseproject.webservice

/**
 * Created by sa on 31/03/17.
 *
 * Web service related constant should be here.
 *
 */

object ApiConstant {

    val NO_INTERNET = "No Internet Connection Available"
    val TIMEOUT = "Connection Timeout"
    val SERVER_NOT_RESPONDING = "Server is not responding.Please try again later"
    val PARSE_ERROR = "Unable to parse response from server"
    val SOMETHING_WRONG = "Something went wrong.Please try again later"


//    val HTTP_BASE_URL = "https://newsapi.org/v2/"

    val HTTP_BASE_URL = "http://192.168.1.178:9090/apis/firstdemo/"

    val HEADER_AUTHORIZATION_NAME: String = "x-auth-token"
    val HEADER_NEWS_API_KEY: String = "x-api-key"
    val NEWS_KEY = "4d91953e6d8e4cde947a0099cbe35cf9"

    val HEADER_CONTENT_TYPE_NAME = ""
    val HEADER_CONTENT_TYPE_VALUE = ""


    val PARAMS_USER_NAME = "username"
    val PARAMS_PASSWORD = "password"
    val PARAMS_GRANT_TYPE = "grant_type"
    val PARAMS_CLIENT_ID = "client_id"
    val PARAMS_CLIENT_SECRET = "client_secret"
    val PARAMS_SCOPE = "scope"

    val VALUE_CLIENT_SECRET = "67DPdYb9o9PjcS3sLH0NzXxRPVtPOcignJjdCKnR"
    val VALUE_CLIENT_ID = "1"
    val VALUE_GRANT_TYPE = "password"

}
