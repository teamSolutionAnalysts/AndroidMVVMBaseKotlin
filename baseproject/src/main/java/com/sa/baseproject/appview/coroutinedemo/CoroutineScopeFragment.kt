package com.sa.baseproject.appview.coroutinedemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sa.baseproject.R
import com.sa.baseproject.appview.MainActivity
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.model.PayBillsItem
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import com.sa.baseproject.wscoroutine.ApiManager
import kotlinx.android.synthetic.main.activity_coroutine_scope.*
import kotlinx.coroutines.CoroutineScope

class CoroutineScopeFragment : AppFragment() {

    var fragmentScope: CoroutineScope? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coroutine_scope, container, false)
    }

    override fun initializeComponent(view: View?) {
        fragmentScope = getFragmentScope(this)

        btnApiCall.setOnClickListener {
            tvResult.text = ""
            ApiManager.getHomePayBills(object : ApiCallback<PayBillsItem> {
                override fun onSuccess(response: PayBillsItem) {
                    tvResult.text = "Success"
                    Log.d("result", "API Successfully call")
                }

                override fun onFailure(apiErrorModel: ApiErrorModel) {
                    tvResult.text = "Fail"
                    Log.d("result", "API Fail")
                }

            }, fragmentScope)
//            (activity as MainActivity).appFragmentManager?.notifyFragment(false)
        }
    }

    override fun pageVisible() {
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("result", "onDestroy")
    }
}
