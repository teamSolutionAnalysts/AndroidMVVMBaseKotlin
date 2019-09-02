package com.sa.baseproject.appview.coroutinedemo

import androidx.databinding.ViewDataBinding
import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import kotlinx.android.synthetic.main.activity_coroutine_scope.*
import kotlinx.coroutines.CoroutineScope

/**
 * This activity show how to call api at activity scope level
 *
 * If want to call api from viewmodel then pass activityScope / FragmentScope object to viewmodel using Factory
 */
class CoroutineScopeActivity : AppActivity() {

    //    Create coroutine activity scope
    var activityScope: CoroutineScope? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_coroutine_scope
    }

    override fun postDataBinding(binding: ViewDataBinding?) {

    }

    override fun initializeComponent() {
        activityScope = getActivityScope(this)


        btnApiCall.setOnClickListener {
            tvResult.text = ""
            /*ApiManager.getHomePayBills(object : ApiCallback<PayBillsItem> {
                override fun onSuccess(response: PayBillsItem) {
                    tvResult.text = "Success"
                    Log.d("result", "API Successfully call")
                }

                override fun onFailure(apiErrorModel: ApiErrorModel) {
                    tvResult.text = "Fail"
                    Log.d("result", "API Fail")
                }

            }, activityScope)*/
        }

        tvGlobleCall.setOnClickListener {
            tvResult.text = ""
            /*ApiManager.getHomePayBills(object : ApiCallback<PayBillsItem> {
                override fun onSuccess(response: PayBillsItem) {
                    tvResult.text = "Success"
                    Log.d("result", "API Successfully call")
                }

                override fun onFailure(apiErrorModel: ApiErrorModel) {
                    tvResult.text = "Fail"
                    Log.d("result", "API Fail")
                }
            })*/
        }
    }

    override fun isNetworkAvailable(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkNetworkAvailableWithError(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
