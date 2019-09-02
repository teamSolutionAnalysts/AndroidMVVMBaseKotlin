package com.sa.baseproject.appview.authentication.login.view

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.sa.baseproject.R
import com.sa.baseproject.appview.authentication.AuthenticationActivity
import com.sa.baseproject.appview.authentication.login.viewmodel.SignInViewModel
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.base.fragment.addFragment
import com.sa.baseproject.databinding.FragmentSigninBinding
import com.sa.baseproject.model.network.Resource
import com.sa.baseproject.utils.DialogUtils
import com.sa.baseproject.utils.ToastUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInFragment : AppFragment() {

    lateinit var mFragmentBinding: FragmentSigninBinding

    private val viewModel by viewModel<SignInViewModel>()


    override fun getLayoutId(): Int {
        return R.layout.fragment_signin
    }

    override fun preDataBinding(arguments: Bundle?) {

    }

    override fun postDataBinding(binding: ViewDataBinding): ViewDataBinding {
        mFragmentBinding = binding as FragmentSigninBinding
        mFragmentBinding.viewModel = viewModel
        mFragmentBinding.lifecycleOwner = this
        return mFragmentBinding
    }


    override fun initializeComponent(view: View?) {

        viewModel.userLiveData.observe(this, Observer { resources ->
            resources?.let {
                when (it) {
                    is Resource.Success -> {
                        (activity as AuthenticationActivity).hideProgressBar()
                        // success code...
                    }
                    is Resource.Error -> {
                        (activity as AuthenticationActivity).hideProgressBar()
                        ToastUtils.shortToast(stringText = it.error.message)
                    }
                    is Resource.Loading -> {
                        (activity as AuthenticationActivity).showProgressBar()
                    }
                }
            }
        })

        viewModel.showDialog.observe(this, Observer {
            if (it != "") {
                if (activity != null)
                    DialogUtils.dialog(activity!!, it)
            }
        })

        viewModel.showProgress.observe(this, Observer {
            if (it) {
                (activity as AuthenticationActivity).showProgressBar()
            } else {
                (activity as AuthenticationActivity).hideProgressBar()
            }
        })

        viewModel.addFragment.observe(this, Observer {
            addFragment<Any>(it.first, it.second)
        })

        viewModel.showError.observe(this, Observer {
            if (it.isNotEmpty()) {
                DialogUtils.dialog(activity!!, it)
            }
        })
    }

    override fun pageVisible() {

    }


}
