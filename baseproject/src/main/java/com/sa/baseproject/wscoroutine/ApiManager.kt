package com.sa.baseproject.wscoroutine

import com.sa.baseproject.model.PayBillsItem
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.wscoroutine.ApiHandle.createCoroutineCall
import com.sa.baseproject.wscoroutine.ApiService.apiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

object ApiManager {

    fun getHomePayBills(apiCallback: ApiCallback<PayBillsItem>, scope: CoroutineScope?=GlobalScope) {

        val result = apiInterface?.getHomePayBills()
        createCoroutineCall(result, apiCallback,scope)

    }

}